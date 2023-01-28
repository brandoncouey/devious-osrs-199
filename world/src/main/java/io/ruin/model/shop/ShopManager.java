package io.ruin.model.shop;

import com.google.common.collect.Maps;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.handlers.shopinterface.CustomShopInterface;
import io.ruin.model.item.Item;
import io.ruin.process.event.Event;
import io.ruin.process.event.EventConsumer;
import io.ruin.process.event.EventType;
import io.ruin.process.event.EventWorker;
import kilim.Pausable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.util.Map;

@Slf4j
public class ShopManager {

    public static final int SHOP_MAX_CAPACITY = 40;
    @Getter
    private static final Map<String, Shop> shops = Maps.newConcurrentMap();


    public static void registerShop(Shop shop) {
        if (shops.containsKey(shop.identifier)) {
            shops.computeIfPresent(shop.identifier, (shopIdentifier, existingShop) -> existingShop.replace(shop));
            return;
        }

        shops.put(shop.identifier, shop);

        shop.init();
        shop.populate();

        EventConsumer eventConsumer = event -> shopTick(event, shop);
        Event event = EventWorker.startEvent(eventConsumer);
        event.eventType = EventType.PERSISTENT;
    }

    public static Shop getByUUID(String uuid) {
        return shops.get(uuid);
    }

    private static final int PRICE_CHECK = 1;
    private static final int ONE = 2;
    private static final int TEN = 3;
    private static final int FIFTY = 4;
    private static final int X = 5;
    private static final int EXAMINE = 6;

    public static void uiShopClose(Player player, int i) {
        if (player.viewingShop != null) {
            player.viewingShop.close(player);
            player.viewingShop = null;
            player.getPacketSender().sendClientScript(299, "ii", 1, 1);
        }

    }

    public static void registerUI() {

        InterfaceHandler.register(301, h -> { //Player inventory
            h.actions[0] = (DefaultAction) (player, option, slot, itemId) -> {

                if (player.isVisibleInterface(Interface.CUSTOM_SHOP)) {
                    CustomShopInterface.attemptSell(player, option, slot, itemId);
                } else {
                    Shop shop = player.viewingShop;
                    if (shop == null) {
                        log.warn("{} tried action {} but has no shop open! | {} {}", player.getName(), option, slot, itemId);
                        return;
                    }

                    int buyAmt = 0;
                    Item playerItem = player.getInventory().getSafe(slot);
                    if (playerItem == null)
                        return;
                    switch (option) {
                        case PRICE_CHECK:
                            int sellToShopPrice = shop.getBuyPrice(playerItem);
                            if (sellToShopPrice < 0) {
                                player.sendMessage(ShopManager.CANNOT_SELL_TO_SHOP);
                            } else
                                player.sendMessage("Shop will buy " + playerItem.getDef().name + " for " + sellToShopPrice + " " + shop.currencyHandler.name());
                            return;
                        case ONE:
                            buyAmt = 1;
                            break;
                        case TEN:
                            buyAmt = 10;
                            break;
                        case FIFTY:
                            buyAmt = 50;
                            break;
                        case X:
                            player.integerInput("Enter amount to buy:", amt -> shop.sellToShop(player, new Item(playerItem.getId(), amt)));
                            break;
                        case EXAMINE:
                            playerItem.examine(player);
                            break;
                    }

                    if (buyAmt > 0) {
                        shop.sellToShop(player, new Item(playerItem.getId(), buyAmt));
                    }
                }
            };


            h.closedAction = ShopManager::uiShopClose;
        });


        InterfaceHandler.register(300, h -> {
            h.actions[16] = (DefaultAction) (player, option, slot, itemId) -> {
                Shop shop = player.viewingShop;
                if (shop == null) {
                    log.warn(player.getName() + " attempted to perform action on shop UI but none were open!");
                    return;
                }

                int buyAmt = 0;
                ShopItem itemForSlot = shop.shopItems.getSafe(slot);
                //log.debug("itemforslot {}", itemForSlot);
                if (itemForSlot == null)
                    return;
                switch (option) {
                    case PRICE_CHECK:
                        player.sendMessage("Shop sells " + itemForSlot.getDef().name + " for " + NumberFormat.getIntegerInstance().format(shop.getSellPrice(itemForSlot)) + " " + shop.currencyHandler.name());
                        if (!itemForSlot.hasRequirements(player)) {
                            itemForSlot.printRequirements(player);
                            return;
                        }
                        return;
                    case ONE:
                        buyAmt = 1;
                        break;
                    case TEN:
                        buyAmt = 10;
                        break;
                    case FIFTY:
                        buyAmt = 50;
                        break;
                    case X:
                        player.integerInput("Enter amount to buy:", amt -> shop.buyFromShop(player, new Item(itemForSlot.getId(), amt)));
                        break;
                    case EXAMINE:
                        itemForSlot.examine(player);
                        break;

                }
                //log.debug("Buy amt {}", buyAmt);
                if (buyAmt > 0) {
                    shop.buyFromShop(player, new Item(itemForSlot.getId(), buyAmt));
                }

            };
            h.closedAction = ShopManager::uiShopClose;
        });
    }

    //TODO Fix these messages
    public static final String CANNOT_SELL_TO_SHOP = "You can't sell that item to this shop!";
    public static final String SHOP_FULL = "The shop is full!";

    public static void openIfExists(Player player, String uuid) {
        Shop shop = getByUUID(uuid);
        if (shop != null)
            shop.open(player);
        if (uuid.equalsIgnoreCase("aecef817-39c1-4a22-9ee9-2815da9dd3df")) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.VOTE_STORE);
        }
        if (uuid.equalsIgnoreCase("1woleAXnpl2ZwTVj7hmvcHvgmSRiYGX3FHtr") || uuid.equalsIgnoreCase("IRONMANDONATOR") || uuid.equalsIgnoreCase("DONATOR_STORE")) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.DONATION_STORE);
        }
    }

    public static void shopTick(Event event, Shop shop) throws Pausable {
        while (true) {
            RestockRules restockRules = shop.restockRules;
            event.delay(restockRules.restockTicks);
            shop.shopItems.forEach(shopItem -> {
                ShopItem original = shopItem.getSlot() >= shop.defaultStock.size() ? null : shop.defaultStock.get(shopItem.getSlot());
                if (original != null) {
                    int difference = Integer.compare(shopItem.getAmount(), original.getAmount());
                    if (difference != 0) {
                        shopItem.setAmount(shopItem.getAmount() - difference);
                        shopItem.update();
                    }
                } else {
                    shopItem.setAmount(shopItem.getAmount() - 1);
                    shopItem.update();
                    if (!shopItem.defaultStockItem && shopItem.getAmount() <= 0) {
                        shopItem.remove();
                    }
                }
            });

            shop.sendUpdates();

            if (shop.onTick != null) {
                shop.onTick.accept(shop);
            }

        }
    }
}


