package io.ruin.model.shop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.ShopItemContainer;
import io.ruin.utility.PlayerLog;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@ToString(exclude = {"viewingPlayers", "shopItems", "currencyHandler"})
@JsonPropertyOrder({"identifier", "title", "currency", "accessibleByIronMan", "generalStore", "canSellToStore", "restockRules", "defaultStock", "requiredAchievements", "requiredLevels"})
@JsonIgnoreProperties({"currencyHandler", "viewingPlayers", "shopItems", "generatedByBuilder", "onTick"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Shop {

    public Shop() {

    }

    @Builder
    private Shop(String identifier, String title, Currency currency, CurrencyHandler currencyHandler, boolean generalStore, boolean canSellToStore, RestockRules restockRules, PriceRules priceRules, List<ShopItem> defaultStock, boolean accessibleByIronMan, boolean accesibleByRegular, boolean generatedByBuilder, boolean mentalOnly, Consumer<Shop> onTick) {
        this.identifier = identifier;
        this.title = title;
        this.currency = currency;
        this.currencyHandler = currencyHandler;
        this.generalStore = generalStore;
        this.canSellToStore = canSellToStore;
        this.restockRules = restockRules;
        this.priceRules = priceRules;
        this.defaultStock = defaultStock;
        this.accessibleByIronMan = accessibleByIronMan;
        this.accessibleByRegular = accesibleByRegular;
        this.mentalOnly = mentalOnly;
        this.generatedByBuilder = generatedByBuilder;
        this.onTick = onTick;
    }


    //TODO Adjust price based on stock
    public List<ShopContainerListener> viewingPlayers = Lists.newArrayList();
    public String identifier;
    public String title;

    public boolean generatedByBuilder;

    /**
     * Represents the shops currency from the pre-defined enum of Currency.
     * This value can be null if the shop was created via code instead of deserialized
     */
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public Currency currency;

    public CurrencyHandler currencyHandler;
    public boolean generalStore;
    public boolean canSellToStore = true;
    public RestockRules restockRules;
    public PriceRules priceRules;
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public List<ShopItem> defaultStock;
    public ShopItemContainer shopItems;
    public boolean accessibleByIronMan;
    public boolean accessibleByRegular;
    public boolean mentalOnly;

    public void init() {
        if (restockRules == null)
            restockRules = RestockRules.generateDefault();
        if (priceRules == null)
            priceRules = PriceRules.generateDefault();

        shopItems = new ShopItemContainer();
        shopItems.init(null, ShopManager.SHOP_MAX_CAPACITY, 300, 16, 24, true);
        if (currency != null)
            currencyHandler = currency.getCurrencyHandler();
    }

    public void populate() {
        shopItems.clear();
        defaultStock.forEach(shopItem -> shopItems.add(shopItem));
        shopItems.forEach(shopItem -> shopItem.defaultStockItem = true);
    }

    public Shop replace(Shop shop) {
        this.title = shop.title;
        this.defaultStock = shop.defaultStock;

        init();
        populate();
        replaceListeners();

        return this;
    }

    public void replaceListeners() {
        viewingPlayers.forEach(listener -> {
            listener.setShopContainer(shopItems);
            listener.open();
        });

    }

    public void open(Player player) {

        if (player.getGameMode().isIronMan() && !accessibleByIronMan) {
            player.sendMessage("You can't access this shop as an ironman!");
            return;
        }
        if (player.getGameMode().isRegular() && !accessibleByRegular) {
            player.sendMessage("You can't access this shop as a normal player!");
            return;
        }
        if (player.xpMode != XpMode.REALISTIC && mentalOnly) {
            player.sendMessage("Only the Mental game mode can use this shop");
            return;
        }
        if (player.isVisibleInterface(301))
            player.closeInterface(InterfaceType.MAIN);


        player.openInterface(InterfaceType.MAIN, 300);
        player.openInterface(InterfaceType.INVENTORY, 301);

        player.getPacketSender().sendClientScript(1074, "isii", 4, title, -1, 0);
        player.getPacketSender().sendClientScript(149, "iiiiiisssss", 301 << 16, 93, 4, 7, 0, -1, "Value<col=ff9040>", "Sell 1<col=ff9040>", "Sell 10<col=ff9040>", "Sell 50<col=ff9040>", "Sell X<col=ff9040>");
        player.getPacketSender().sendClientScript(149, "iiiiiisssss", 300 << 16 | 16, 24, 8, 5, 0, -1, "Value<col=ff9040>", "Buy 1<col=ff9040>", "Buy 10<col=ff9040>", "Buy 50<col=ff9040>", "Buy X<col=ff9040>");

        player.getPacketSender().sendAccessMask(300, 16, 0, ShopManager.SHOP_MAX_CAPACITY, 1086);
        player.getPacketSender().sendAccessMask(301, 0, 0, 27, 1086);

        if (!currency.name().equalsIgnoreCase("coins")) {
            player.sendMessage("You currently have " + NumberUtils.formatNumber(currencyHandler.getCurrencyCount(player)) + " " + currencyHandler.name + ".");
        }

        ShopContainerListener containerListener = new ShopContainerListener(player);

        containerListener.setShopContainer(shopItems);
        containerListener.open();

        viewingPlayers.add(containerListener);
        player.viewingShop = this;
    }


    public void close(Player player) {
        viewingPlayers.removeIf(listener -> listener.getPlayer() == null || listener.getPlayer().equals(player));
        player.viewingShop = null;
    }


    public void buyFromShop(Player player, Item requestedItem) {
        if (requestedItem.getAmount() == 0)
            return;
        if (!shopItems.hasId(requestedItem.getId())) {
            //TODO Logging
            return;
        }
        if (player.getInventory().getFreeSlots() <= 0)
            return;

        ShopItem shopItem = shopItems.findItem(requestedItem.getId());


        int stockAmount = Math.min(requestedItem.getAmount(), shopItem.getAmount());

        if (player.getGameMode().isIronMan()) {
            if (!shopItem.defaultStockItem) {
                player.sendMessage("You cannot purchase an item somebody else has sold!");
                return;
            } /*else if(shopItem.getAmount() > defaultStock.get(shopItem.getSlot()).getAmount()) {
                player.sendMessage("You cannot purchase this as it is currently overstocked!");
                return;
            }*/
        }

        if (shopItem.requirementCheckType == RequirementCheckType.REQUIRED_TO_BUY) {
            if (!shopItem.hasRequirements(player)) {
                shopItem.printRequirements(player);
                return;
            }
        }

        if (stockAmount == 0) {
            return;
        }

        int possibleBuyAmount = currencyHandler.getPossibleBuyAmount(player, shopItem);

        if (possibleBuyAmount <= 1) {
            possibleBuyAmount = 50;
        }
        int buyAmount = Math.min(possibleBuyAmount, stockAmount);
        log.debug("Attempting to buy {} x {}", buyAmount, shopItem);
        if (buyAmount > 0) {
            int pricePer = getSellPrice(shopItem);
            int requiredCurrency = buyAmount * pricePer;
            int removedCurrency = currencyHandler.removeCurrency(player, requiredCurrency);//Should result in 0 if actual currency < requiredCurrency
            if (removedCurrency == requiredCurrency) {
                Item bought = new Item(shopItem.getId(), buyAmount);
                player.getInventory().add(bought);
                if (currency == Currency.PEST_CONTROL_POINTS || currency == Currency.MAGE_ARENA_POINTS || currency == Currency.STARDUST || currency == Currency.GOLDEN_NUGGETS || currency == Currency.MARKS_OF_GRACE) {
                    player.getCollectionLog().collect(shopItem.getId(), buyAmount);
                }
                if (shopItem.getAdditionalItems() != null && !shopItem.getAdditionalItems().isEmpty()) {
                    shopItem.getAdditionalItems().forEach(additional -> player.getInventory().add(new Item(additional.getId(), buyAmount * additional.getAmount())));
                }
                if (!shopItem.defaultStockItem) {
                    shopItems.remove(bought);
                }
                PlayerLog.log(PlayerLog.Type.NORMAL_SHOP_BUY, player.getName(), "Purchased=[" + bought + "].");
                sendUpdates();
            } else {
                log.debug("Failed to buy {} x {} | removedCurrency {}", buyAmount, shopItem.getId(), removedCurrency);
            }
        } else if (buyAmount == -2) {
            player.sendMessage("You don't have enough space to do that!");
        } else {
            player.sendMessage("You don't have enough " + currencyHandler.name() + " to buy that.");
        }
    }

    public void sendUpdates() {
        viewingPlayers.forEach(ShopContainerListener::update);
    }

    public void sellToShop(Player player, Item requestedItem) {

        int scamcheck = getBuyPrice(requestedItem);

        /*if (scamcheck == 1) {
            requestedItem.remove();
            player.sendMessage("You threw all your " + requestedItem.getDef().name + " away for nothing.");
            return;
        }*/

        if (requestedItem.getDef().free) {
            player.sendMessage(ShopManager.CANNOT_SELL_TO_SHOP);
            return;
        }

        if (requestedItem.getDef().isCurrency()) {
            player.sendMessage(ShopManager.CANNOT_SELL_TO_SHOP);
            return;
        }


        if (!canSellToStore) {
            player.sendMessage(ShopManager.CANNOT_SELL_TO_SHOP);
            return;
        }

        ShopItem matchingItem = shopItems.findItem(requestedItem.getId(), true);
        if (generalStore && (matchingItem == null || !matchingItem.defaultStockItem)) {
            if (shopItems.getFreeSlots() == 0 && matchingItem == null) {
                player.sendMessage(ShopManager.SHOP_FULL);
                return;
            }

            int inventoryCount = player.getInventory().count(requestedItem.getId());
            int maxInventory = Math.min(requestedItem.getAmount(), inventoryCount);

            int maxSell = Math.min(Integer.MAX_VALUE - (matchingItem != null ? matchingItem.getAmount() : 0), maxInventory);

            int pricePer = getBuyPrice(requestedItem);


            boolean allSold = maxSell == inventoryCount;//guarantees that 1 slot will be open

            boolean hasCurrency = currencyHandler.getCurrencyCount(player) != 0;

            log.debug("{} {} {}", maxSell, maxInventory, currencyHandler.getCurrencyCount(player));
            if (!hasCurrency && !allSold) {
                if (currencyHandler instanceof ItemCurrencyHandler) {
                    if (player.getInventory().getFreeSlots() == 0) {
                        player.sendMessage("You have no space for your " + currencyHandler.name());
                        return;
                    }
                }
            }
            int removed = player.getInventory().remove(requestedItem.getId(), maxSell);


            if (removed > 0) {
                int unnoted = requestedItem.getDef().isNote() ? requestedItem.getDef().fromNote().id : requestedItem.getId();
                int givenCurrency = currencyHandler.addCurrency(player, pricePer * removed);
                log.debug("Sold {} x {}, given currency {}", requestedItem.getDef().name, removed, pricePer * removed);
                shopItems.add(unnoted, removed, -1, null);
                PlayerLog.log(PlayerLog.Type.NORMAL_SHOP_SELL, player.getName(), "Sold=[" + ItemDef.get(unnoted).name + ", Amount=" + removed +"] for " + (pricePer * removed) + "gp.");
                sendUpdates();
            }

            return;
        }
        log.debug("Trying to sell to store {}", requestedItem);
        if (matchingItem == null) {
            player.sendMessage(ShopManager.CANNOT_SELL_TO_SHOP);
            return;
        }

        int maxInventory = Math.min(requestedItem.getAmount(), player.getInventory().count(requestedItem.getId()));

        int maxSell = Math.min(Integer.MAX_VALUE - matchingItem.getAmount(), maxInventory);

        int pricePer = getBuyPrice(requestedItem);
        int removed = player.getInventory().remove(requestedItem.getId(), maxSell);
        if (removed > 0) {
            int unnoted = requestedItem.getDef().isNote() ? requestedItem.getDef().fromNote().id : requestedItem.getId();
            currencyHandler.addCurrency(player, pricePer * removed);
            log.debug("Sold {} x {}, given currency {}", requestedItem.getDef().name, removed, pricePer * removed);
            PlayerLog.log(PlayerLog.Type.NORMAL_SHOP_SELL, player.getName(), "Sold=[" + ItemDef.get(unnoted).name + ", Amount=" + removed +"] for " + (pricePer * removed) + "gp.");
            shopItems.add(unnoted, removed, -1, null);
            sendUpdates();
        }

    }

    public int getBuyPrice(Item itemForSlot) {
        ItemDef itemDef = itemForSlot.getDef().isNote() ? itemForSlot.getDef().fromNote() : itemForSlot.getDef();
        if (!itemDef.tradeable) {
            return -1;
        }
        if (itemDef.free) {
            return -1;
        }

        if (itemDef.isCurrency()) {
            return -1;
        }

        if (generalStore) {
            return Math.max(itemDef.lowAlchValue, 1);
        }
        if (!canSellToStore) {
            return -1;
        }
        ShopItem matchingItem = shopItems.findItem(itemForSlot.getId(), true);
        if (matchingItem == null && !generalStore) {
            return -1;
        }

        if (matchingItem != null && matchingItem.getPrice() > 0) {
            return Math.max((int) (matchingItem.getPrice() * 0.75), 1);
        }

        return 1;
    }

    public int getSellPrice(ShopItem itemForSlot) {

        if (itemForSlot != null) {
            ItemDef itemDef = itemForSlot.getDef().isNote() ? itemForSlot.getDef().fromNote() : itemForSlot.getDef();
            if (itemForSlot.getPrice() <= 0) {
                return itemDef.value;
            }
            return itemForSlot.getPrice();//Can this ever be null?
        }
        return Integer.MAX_VALUE;//TODO
    }

    public void save(File saveFile) {
        try (FileWriter fw = new FileWriter(saveFile)) {

            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            objectMapper.writeValue(fw, this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public Consumer<Shop> onTick;

}
