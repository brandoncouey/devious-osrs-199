package io.ruin.model.inter.handlers.shopinterface;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.Item;
import io.ruin.model.shop.Currency;

import java.util.Arrays;

public class CustomShopInterface {
    static {
        InterfaceHandler.register(Interface.CUSTOM_SHOP, h -> {
            h.actions[16] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //      player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Melee Store");
                    player.setShopIdentifier(1);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.MELEE_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[18] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Ranged Store");
                    player.setShopIdentifier(2);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.RANGED_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[20] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Magic Store");
                    player.setShopIdentifier(3);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.MAGIC_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[22] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Jewelry Store");
                    player.setShopIdentifier(4);
                    handleEnteringShop(player, CustomShop.JEWELRY_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[24] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Untradeable Store");
                    player.setShopIdentifier(5);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.UNTRADEABLE_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[26] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "PK Point Store");
                    player.setShopIdentifier(6);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.PK_POINT_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[28] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Misc Store");
                    player.setShopIdentifier(7);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.MISC_STORE);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[30] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getGameMode().isIronMan()) {
                    //    player.closeInterface(InterfaceType.MAIN);
                    player.sendMessage("You can't access this shop.");
                    return;
                } else {
                    player.getPacketSender().sendString(836, 14, "Skilling Supplies");
                    player.setShopIdentifier(8);
                    handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.SKILLING_SUPPLIES);
                    open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));
                }
            };
            h.actions[35] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPacketSender().sendString(836, 14, "Vote Store");
                player.setShopIdentifier(10);
                handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.VOTE_STORE);
                open(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.getItemsFromShop(player));

            };
            h.actions[41] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (option == 1) {
                    priceCheck(player, slot, itemId);
                } else {
                    attemptBuy(player, option, slot, itemId);
                }
            };

            h.closedAction = (p, i) -> {
                if (p.getActiveCustomShop() != null) {
                    p.getActiveCustomShop().removePlayerFromShop(p);
                    p.setActiveCustomShop(null);
                }
            };
        });

       /* InterfaceHandler.register(Interface.PLAYER_SHOP_INVENTORY, h -> h.actions[0] = (DefaultAction) (player, childId, option, slot, itemId) -> {
            if(player.isVisibleInterface(Interface.CUSTOM_SHOP)) {
                attemptSell(player, childId, option, slot, itemId);
            }
        });*/
    }

    public static void handleEnteringShop(Player player, io.ruin.model.inter.handlers.shopinterface.CustomShop customShop) {
        customShop.addPlayerToShop(player);
        player.setActiveCustomShop(customShop);
    }

    private static void attemptBuy(Player player, int option, int slot, int itemId) {
        io.ruin.model.inter.handlers.shopinterface.CustomShop customShop = io.ruin.model.inter.handlers.shopinterface.CustomShop.get(player.getShopIdentifier());
        if (customShop == null) {
            return;
        }

        ShopItem shopItem = customShop.getShopItem(itemId);
        if (shopItem == null) {
            return;
        }

        Item item = new Item(shopItem.getItemId());

        ItemDef itemDef = item.getDef();
        if (itemDef == null) {
            return;
        }

        if (ItemDef.get(shopItem.getItemId()) != null && itemId != shopItem.getItemId()
                && itemId != (ItemDef.get(shopItem.getItemId()) != null ? itemDef.placeholderMainId : 0))
            return;

        if (option == 10) {
            item.examine(player);
            return;
        }

        if (!itemInShop(itemId, customShop)) {
            player.sendMessage("");
            return;
        }

        ShopItem customShopItem = Arrays.stream(customShop.getShopItems())
                .filter(si -> si.getItemId() == itemId)
                .findFirst()
                .orElse(null);

        if (customShopItem == null) {
            return;
        }

        if (option == 2) {
            buy(player, customShopItem, customShop, itemId, 1);
        } else if (option == 3) {
            buy(player, customShopItem, customShop, itemId, 5);
        } else if (option == 4) {
            buy(player, customShopItem, customShop, itemId, 10);
        } else if (option == 5) {
            player.integerInput("How many do you want to buy?",
                    amt -> buy(player, customShopItem, customShop, itemId, amt));
        }
    }

    private static void buy(Player player, ShopItem customShopItem, io.ruin.model.inter.handlers.shopinterface.CustomShop customShop, int itemId, int amount) {
        if (amount <= 0 || customShop == null || customShopItem == null || itemId <= 0)
            return;


        /**
         * Container check
         */
        Item item = new Item(customShopItem.getItemId());
        ItemDef itemDef = item.getDef();
        if (itemDef == null) {
            return;
        }
        int freeSlots = player.getInventory().getFreeSlots();
        if (amount > freeSlots) {
            /**
             * Attempt to note the given item.
             */
            if (!itemDef.isNote() && itemDef.notedId != -1)
                itemDef = itemDef.fromNote();
        }
        if (itemDef.stackable) {
            /**
             * 'Free' a slot if necessary.
             */
            if (freeSlots == 0 && player.getInventory().findItem(itemDef.id) != null)
                freeSlots++;
        } else if (amount > freeSlots) {
            /**
             * Set amount equal to free slots.
             */
            amount = freeSlots;
        }
        if (freeSlots == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }

        /**
         * Currency check
         */
        int price = customShopItem.getPrice();
        Currency currency = customShop.getCurrency();
        if (price != 0) {
            long buyPrice = (long) price * amount;
            int currencyAmount = currency.getCurrencyHandler().getCurrencyCount(player);
            if (currencyAmount < buyPrice) {
                if (price > currencyAmount) {
                    /* not enough currency to even buy 1 */
                    amount = 0;
                } else {
                    amount = currencyAmount / price;
                    buyPrice = (long) price * amount;
                }

                if (amount <= 0) {
                    player.sendMessage("You did not bring enough " + StringUtils.fixCaps(currency.name().replace("_", " ")) + " to make this purchase.");
                    return;
                }
            }
            if (buyPrice <= 0L || buyPrice < price) {
                player.sendMessage("Please buy this item in a smaller quantity.");
                return;
            }
            currency.getCurrencyHandler().removeCurrency(player, (int) buyPrice);
        }

        player.getInventory().add(itemDef.id, amount);

        player.getCollectionLog().collect(itemDef.id, amount);

        customShop.refreshShop();
    }

    public static void attemptSell(Player player, int option, int slot, int itemId) {
        if (slot < 0 || slot > 28)
            return;

        Item item = player.getInventory().get(slot);

        if (item == null || (itemId != item.getId() && itemId != item.getDef().placeholderMainId))
            return;

        if (option == 10) {
            item.examine(player);
            return;
        }

        if (!item.getDef().tradeable) {
            player.sendMessage("You can't sell that item.");
            return;
        }

        io.ruin.model.inter.handlers.shopinterface.CustomShop customShop = io.ruin.model.inter.handlers.shopinterface.CustomShop.get(player.getShopIdentifier());

        if (customShop == null) {
            return;
        }

        if (!itemInShop(itemId, customShop)) {
            player.sendMessage("This item is not available right now.");
            return;
        }

        ShopItem customShopItem = Arrays.stream(customShop.getShopItems())
                .filter(si -> si.getItemId() == itemId)
                .findFirst()
                .orElse(null);

        if (customShopItem == null) {
            return;
        }

        int price = (int) (customShopItem.getPrice() * 0.70);

        if (option == 1) {
            if (customShopItem.getPrice() <= 0) {
                player.sendMessage("You can't sell that item to this store.");
            } else {
                player.sendMessage(item.getDef().name + " can be sold for "
                        + (NumberUtils.formatNumber(price)
                        + " " + customShop.getCurrency().name()) + ".");
            }
            return;
        } else if (option == 2) {
            sell(player, customShopItem, customShop, price, itemId, 1);
        } else if (option == 3) {
            sell(player, customShopItem, customShop, price, itemId, 5);
        } else if (option == 4) {
            sell(player, customShopItem, customShop, price, itemId, 10);
        } else if (option == 5) {
            player.integerInput("How many do you want to sell?",
                    amt -> sell(player, customShopItem, customShop, price, itemId, amt));
        }
    }

    public static void sell(Player player, ShopItem customShopItem, io.ruin.model.inter.handlers.shopinterface.CustomShop customShop,
                            int price, int itemId, int amount) {

        if (customShop.getCurrency() != Currency.BLOOD_MONEY) {
            player.sendMessage("You can't sell any items to this store.");
            return;
        }

        if (amount > player.getInventory().getAmount(itemId)) {
            amount = player.getInventory().getAmount(itemId);
        }

        if (amount <= 0)
            return;

        int totalPrice = amount * price;

        player.getInventory().add(13307, totalPrice);
        player.getInventory().remove(itemId, amount);
        customShop.refreshShop();
    }

    private static boolean itemInShop(int itemId, io.ruin.model.inter.handlers.shopinterface.CustomShop customShop) {
        if (customShop != null) {
            return Arrays.stream(customShop.getShopItems()).anyMatch(i -> i.getItemId() == itemId);
        }
        return false;
    }

    private static void priceCheck(Player player, int slot, int itemId) {
        int shopId = player.getShopIdentifier();
        if (shopId < 0) {
            player.sendMessage("Something is wrong with this shop. Please contact a staff member.");
            return;
        }

        io.ruin.model.inter.handlers.shopinterface.CustomShop customShop = io.ruin.model.inter.handlers.shopinterface.CustomShop.get(shopId);

        if (customShop != null) {
            ShopItem selectedItem = Arrays.stream(customShop.getShopItems())
                    .filter(si -> si.getItemId() == itemId)
                    .findFirst()
                    .orElse(null);

            if (selectedItem == null) {
                return;
            }

            ItemDef def = ItemDef.get(selectedItem.getItemId());

            if (def != null) {
                player.sendMessage(def.name + " costs " + NumberUtils.formatNumber(selectedItem.getPrice())
                        + " " + StringUtils.fixCaps(customShop.getCurrency().toString().replace("_", " ")) + "."
                );
            }
        }
    }

    public static void open(Player player, Item[] shopItems) {
        player.getPacketSender().sendItems(-1, 1, 93, player.getInventory().getItems());
        player.getPacketSender().sendItems(10005, shopItems);
        if (player.getShopIdentifier() < 1) {
            player.setShopIdentifier(1);
        }
        handleEnteringShop(player, io.ruin.model.inter.handlers.shopinterface.CustomShop.MELEE_STORE);
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.getPacketSender().sendClientScript(10198, 11802, 11235, 12904, 4736, 21295, 20528, 23685, 6739);
        player.getPacketSender().sendClientScript(10197);
        player.getPacketSender().sendClientScript(149, "iiiiiisssss", 19726336, 93, 4, 7, 0, -1,
                "Value<col=ff9040>", "Sell 1<col=ff9040>", "Sell 5<col=ff9040>",
                "Sell 10<col=ff9040>", "Sell X<col=ff9040>");
        player.getPacketSender().sendAccessMask(836, 41, 0,
                127, 1150);
        player.getPacketSender().sendAccessMask(301, 0, 0, 27, 1086);
        if (!player.isVisibleInterface(Interface.CUSTOM_SHOP)) {
            player.openInterface(InterfaceType.MAIN, Interface.CUSTOM_SHOP);
            player.openInterface(InterfaceType.INVENTORY, Interface.PLAYER_SHOP_INVENTORY);
        }
    }
}
