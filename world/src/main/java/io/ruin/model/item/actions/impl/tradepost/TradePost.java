package io.ruin.model.item.actions.impl.tradepost;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.ItemSet;
import io.ruin.services.discord.impl.TradePostEmbedMessage;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.PLATINUM_TOKEN;

/**
 * @author <a href="https://github.com/kLeptO">Augustinas R.</a>
 */
public class TradePost {

    private static final int MAX_MY_OFFERS = 6;
    private static final int MAX_VIEW_OFFERS = 50;

    private static final int VIEW_OFFERS_WIDGET = 839;
    private static final int MY_OFFERS_WIDGET = 840;
    private static final int MY_OFFERS_INVENTORY_WIDGET = 467;
    private static final long TRADING_POST_CHANNEL = 872490306883453008L;

    private Player player;
    private String searchItem;
    private String searchPlayer;
    private TradePostSort sort = TradePostSort.AGE_ASCENDING;

    private static int adsIndex;
    private static final List<TradePostOffer> ads = new ArrayList<>();

    private List<TradePostOffer> viewOffers = new ArrayList<>();

    public void init(Player player) {
        this.player = player;
    }

    public void openViewOffers() {
        player.openInterface(InterfaceType.MAIN, VIEW_OFFERS_WIDGET);
        player.closeInterface(InterfaceType.INVENTORY);
        resetSearch();
    }

    public void openMyOffers() {
        player.openInterface(InterfaceType.MAIN, MY_OFFERS_WIDGET);
        changeInventoryAccess();
        updateMyOffers();
    }

    public TradePostContainer container() {
        return TradePostManager.getContainer(player);
    }

    private void advertise() {
        player.dialogue(new OptionsDialogue(
                new Option("How does it work?", () -> {
                    player.dialogue(new MessageDialogue("You can hire a clerk to shout out your listing for you for nearby players. It's limited to 1 ad per player and it costs 1M/hour. The ads are rotated through all other listings."));
                }),
                new Option(container().advOffer().isPresent() ? "Remove my advertisement" : "Create an advertisement", () -> {
                    if (container().advOffer().isPresent()) {
                        player.dialogue(new OptionsDialogue("Remove advertisement (<col=ff0000>Warning: </col>No refund)?",
                                new Option("Yes, remove.", p -> {
                                    TradePostOffer offer = container().advOffer().get();

                                    if (offer != null) {
                                        offer.setAdEnd(0);
                                        ads.remove(offer);
                                        player.sendMessage("Your advertisement has been removed.");
                                    }
                                }),
                                new Option("Cancel."))
                        );
                    } else {
                        player.integerInput("Enter the slot to advertise (1-6):", slot -> {
                            if (slot < 1 || slot > 6) {
                                player.sendMessage("Please specify a valid slot from the range of 1 to 6.");
                                return;
                            }

                            slot -= 1;

                            TradePostOffer offer = container().get(slot);

                            if (offer == null) {
                                player.sendMessage("No active offer found in the specified slot.");
                                return;
                            }

                            player.sendMessage("Selected offer: " + offer.getItem().getId());

                            player.integerInput("Enter the duration (1-12h) (1M/hour):", duration -> {
                                if (duration < 1 || duration > 12) {
                                    player.sendMessage("Please specify a valid duration from 1-12h.");
                                    return;
                                }

                                int cost = duration * 1_000_000;

                                if (player.getInventory().getAmount(995) < cost) {
                                    player.sendMessage("You don't have enough coins in inventory to continue.");
                                    return;
                                }

                                //sendEmbed(offer);
                                player.getInventory().remove(995, cost);
                                player.sendMessage("Your advertisement for " + offer.getItem().getDef().name + " is now active.");
                                offer.setAdEnd(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(duration));
                                ads.add(offer);
                            });
                        });
                    }
                }))
        );
    }

//    private void sendEmbed(TradePostOffer offer) {
//        MessageEmbed embed = new EmbedBuilder().setTitle(":shopping_cart: [Trading Post]: " + player.getName()).setDescription(
//            "**Item:** " + offer.getMaxAmount() + "x " + offer.getItem().getDef().name + "\n" +
//            "**Price:** " + Misc.currency(offer.getPricePerItem())
//        ).setThumbnail("https://static.runelite.net/cache/item/icon/" + offer.getItem().getId() + ".png").build();
//        DiscordConnection.post(TRADING_POST_CHANNEL, embed, (message) -> offer.setEmbedId(message.getIdLong()));
//    }

    private void viewHistory() {
        List<HistoryEntry> history = TradePostManager.getHistory(player);

        if (history == null) {
            player.sendMessage("Loading...");
            TradePostManager.loadHistory(player, () -> viewHistory());
            return;
        }

        player.openInterface(InterfaceType.MAIN, 383);

        boolean empty = history.isEmpty();

        if (empty) {
            player.getPacketSender().sendClientScript(1646);
        } else {
            player.getPacketSender().sendClientScript(1644);

            int slot = 0;

            for (HistoryEntry entry : history) {
                player.getPacketSender().sendClientScript(1645, "ioiii", slot++, entry.getItem().getId(), entry.getOfferType(), entry.getItem().getAmount(), entry.getPrice());
            }

            player.getPacketSender().sendClientScript(1646);
        }
    }

    private void promptCreateOffer(int itemId) {
        if (container().getFreeSlot() == -1) {
            player.sendMessage("You cannot create more offers.");
            return;
        }

        ItemDef itemDef = ItemDef.get(itemId);
        if (itemDef == null || !itemDef.tradeable || itemId == COINS_995) {
            player.sendMessage("You cannot trade this item.");
            return;
        }

        final int unnotedId = !itemDef.isNote() ? itemId : itemDef.fromNote().id;

        if (container().getValidOffers().stream().anyMatch(offer -> offer.getItem().getId() == unnotedId)) {
            player.sendMessage("You already have offer for this item.");
            return;
        }

        if (player.getInventory().getAmount(itemId) > 1) {
            player.integerInput("Enter item amount you would like to sell:", amount -> {
                if (!player.getInventory().contains(itemId, amount)) {
                    player.sendMessage("You do not have that many of this item!");
                    player.closeDialogue();
                    return;
                }

                promptPrice(itemId, unnotedId, amount);
            });
        } else {
            promptPrice(itemId, unnotedId, 1);
        }
    }

    private void promptPrice(int itemId, int unnotedId, int amount) {
        player.integerInput("Enter price per item:", price -> {
            if (container().add(new TradePostOffer(String.valueOf(player.getName()), new Item(unnotedId, amount), price, System.currentTimeMillis()))) {
                player.getInventory().remove(itemId, amount);
                updateMyOffers();
                String message = player.getName() + " just posted ";
                if (amount > 1)
                    message += NumberUtils.formatNumber(amount) + " x " + ItemDef.get(itemId).name + " for the price of " + price + " coins each";
                else
                    message += ItemDef.get(itemId).descriptiveName + " for the price of " + price + " coins each";
                TradePostEmbedMessage.sendDiscordMessage(message, ItemDef.get(itemId).id);
            }
        });
    }

    private void promptAdjustOffer(int index) {
        player.integerInput("Enter price per item:", price -> {
            TradePostOffer offer = container().get(index);
            if (offer == null) {
                return;
            }

            offer.setPrice(price);
            updateMyOffers();
        });
    }

    private void resetOffer(int index) {
        TradePostOffer offer = container().get(index);

        if (offer == null) {
            return;
        }

        if (container().remove(offer)) {
            if (player.getInventory().hasRoomFor(offer.getItem())) {
                player.getInventory().add(offer.getItem().getId(), offer.getItem().getAmount());
            } else {
                player.getBank().add(offer.getItem().getId(), offer.getItem().getAmount());
                player.sendMessage(offer.getItem().getAmount() + "x " + offer.getItem().getDef().name + " has been sent to your bank!");
            }
            updateMyOffers();
        }
    }

    private void changeInventoryAccess() {
        if (player.isVisibleInterface(MY_OFFERS_INVENTORY_WIDGET)) {
            return;
        }

        player.openInterface(InterfaceType.INVENTORY, MY_OFFERS_INVENTORY_WIDGET);
        /*player.getPacketSender().sendClientScript(149, "IviiiIsssss", MY_OFFERS_INVENTORY_WIDGET << 16, 93, 4, 7, 0, -1,
                "Select", "", "", "", "");*/
        player.getPacketSender().sendAccessMask(MY_OFFERS_INVENTORY_WIDGET, 0, 0, 27, 1086);
    }

    private void updateMyOffers() {
        for (int index = 0; index < MAX_MY_OFFERS; index++) {
            updateMyOffer(index, container().get(index));
        }
    }

    private void updateMyOffer(int index, TradePostOffer offer) {
        String price = "Price: <col=ffffff>" + (offer == null ? "-" : formatPrice(offer.getPricePerItem()) + " ea");
        String totalPrice = offer == null ? ""
                : "<col=999999>=" + formatPrice((long) offer.getPricePerItem() *
                offer.getItem().getAmount()) + " total";

        int titleWidgetId = 19 + (17 * index);
        int priceWidgetId = 29 + (17 * index);
        int totalPriceWidgetId = 30 + (17 * index);
        int adjustButtonWidgetId = 21 + (17 * index);
        int resetButtonWidgetId = 25 + (17 * index);
        int containerWidgetId = 31 + (17 * index);
        int itemContainerId = 1000 + index;
        player.getPacketSender().sendClientScript(
                149, "IviiiIsssss",
                MY_OFFERS_WIDGET << 16 | containerWidgetId, itemContainerId,
                4, 7, 1, -1, "", "", "", "", ""
        );

        player.getPacketSender().sendItems(
                MY_OFFERS_WIDGET,
                containerWidgetId,
                itemContainerId,
                offer == null ? null : offer.getItem()
        );

        player.getPacketSender().sendString(
                MY_OFFERS_WIDGET,
                titleWidgetId, offer == null ? "Empty" : offer.getItem().getDef().name
        );
        player.getPacketSender().sendString(MY_OFFERS_WIDGET, priceWidgetId, price);
        player.getPacketSender().sendString(MY_OFFERS_WIDGET, totalPriceWidgetId, totalPrice);
        player.getPacketSender().sendClientScript(69, "ii", offer != null ? 0 : 1, MY_OFFERS_WIDGET << 16 | adjustButtonWidgetId);
        player.getPacketSender().sendClientScript(69, "ii", offer != null ? 0 : 1, MY_OFFERS_WIDGET << 16 | resetButtonWidgetId);
        player.getPacketSender().sendClientScript(10195, "i", index);

        int progress = 0;

        if (offer != null) {
            double completed = 100D - (offer.getItem().getAmount() * 100 / offer.getMaxAmount());
            progress = (int) (completed / 100 * (double) 141);
        }
        player.getPacketSender().sendClientScript(10196, "ii", index, progress);
    }

    private List<TradePostOffer> findViewOffers() {
        return TradePostManager.CONTAINERS.values().stream()
                .flatMap(c -> c.getValidOffers().stream())
                .filter(offer -> {
                    if (searchItem == null && searchPlayer == null) {
                        return true;
                    }

                    if (searchItem == null) {
                        return offer.getUsername().equalsIgnoreCase(searchPlayer);
                    }

                    return offer.getItem().getDef().name.toLowerCase().contains(searchItem);
                }).sorted((offerA, offerB) -> {
                    switch (sort) {
                        case PRICE_DESCENDING:
                            return offerB.getPricePerItem() - offerA.getPricePerItem();
                        case PRICE_ASCENDING:
                            return offerA.getPricePerItem() - offerB.getPricePerItem();
                        case AGE_DESCENDING:
                            return (int) (offerA.getTimestamp() - offerB.getTimestamp());
                        case AGE_ASCENDING:
                        default:
                            return (int) (offerB.getTimestamp() - offerA.getTimestamp());
                    }
                }).limit(MAX_VIEW_OFFERS)
                .collect(Collectors.toList());
    }

    private void sortByPrice() {
        if (sort == TradePostSort.PRICE_ASCENDING) {
            sort = TradePostSort.PRICE_DESCENDING;
        } else {
            sort = TradePostSort.PRICE_ASCENDING;
        }
        updateViewOffers();
    }

    private void sortByAge() {
        if (sort == TradePostSort.AGE_ASCENDING) {
            sort = TradePostSort.AGE_DESCENDING;
        } else {
            sort = TradePostSort.AGE_ASCENDING;
        }
        updateViewOffers();
    }

    private void promptSearch(boolean searchPlayer) {
        if (searchPlayer) {
            player.stringInput("Enter player name to search for:", string -> {
                this.searchItem = null;
                this.searchPlayer = string;
                updateSearch();
            });
        } else {
            player.stringInput("Enter item name to search for:", string -> {
                this.searchPlayer = null;
                this.searchItem = string;
                updateSearch();
            });
        }
    }

    private void resetSearch() {
        this.searchItem = null;
        this.searchPlayer = null;
        updateSearch();
    }

    private void updateSearch() {
        updateViewOffers();

        String search = searchItem == null ? (searchPlayer == null ? "-" : searchPlayer) : searchItem;
        if (search.length() > 50) {
            search = search.substring(0, 48) + "...";
        }

        player.getPacketSender().sendString(VIEW_OFFERS_WIDGET, 32, search);
    }

    private void buy(int index) {
        if (index >= viewOffers.size()) {
            return;
        }

        player.integerInput("Amount to Buy:", amount -> {
            TradePostOffer offer = viewOffers.get(index);

            if (offer.getUsername().equalsIgnoreCase(String.valueOf(player.getName()))) {
                player.sendMessage("You cannot buy your own items.");
                return;
            }

            if (amount < 1) {
                player.sendMessage("Please enter a valid amount.");
                return;
            }

            if (amount > offer.getItem().getAmount()) {
                amount = offer.getItem().getAmount();
            }

            long price = (long) offer.getPricePerItem() * amount;

            if (price > Integer.MAX_VALUE) {
                player.sendMessage("You can't buy this many, do a lower amount");
                return;
            }

            if (!player.getInventory().contains(COINS_995, (int) price)) {
                player.sendMessage("You do not have enough to purchase this.");
                return;
            }

            int finalAmount = amount;
            player.dialogue(
                    new MessageDialogue("Are you sure you want to purchase: " + finalAmount + "x " + offer.getItem().getDef().name + " for a price of: " + formatPrice(price) + "?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> {

                                TradePostContainer container = TradePostManager.forId(offer.getUsername());

                                if (!player.getInventory().contains(COINS_995, (int) price)) {
                                    player.sendMessage("You do not have enough to purchase this amount.");
                                    player.closeDialogue();
                                    return;
                                }

                                Optional<TradePostOffer> sellersOffer = container.find(offer.getItem().getId(), finalAmount);

                                if (!sellersOffer.isPresent()) {
                                    player.sendMessage("This item has been removed or sold already!");
                                    player.closeDialogue();
                                    return;
                                }

                                sellersOffer.ifPresent(tradePostOffer -> {

                                    int amountLeft = tradePostOffer.getItem().getAmount() - finalAmount;

                                    if (amountLeft < 0) {
                                        player.sendMessage("TRADING POST [ERROR: 1] Contact a Developer!");
                                        player.closeDialogue();
                                        return;
                                    }

                                    player.getInventory().remove(COINS_995, (int) price);

                                    boolean bank = !player.getInventory().hasRoomFor(offer.getItem().getId(), finalAmount);

                                    if (bank) {
                                        player.getBank().add(offer.getItem().getId(), finalAmount);
                                    } else {
                                        player.getInventory().add(offer.getItem().getId(), finalAmount);
                                    }

                                    container.addToCoffer((int) price);

                                    boolean outOfStock = amountLeft == 0;

                                    int offerIndex = container.getSlot(tradePostOffer);

                                    if (outOfStock) {
                                        container.remove(tradePostOffer);
                                    } else {
                                        tradePostOffer.getItem().setAmount(amountLeft);
                                    }

                                    TradePostManager.saveHistory(1, new Item(offer.getItem().getId(), finalAmount), (int) price, container);

                                    Optional<Player> seller = World.getPlayerByName(container.getUsername());

                                    seller.ifPresent(p -> {
                                        if (outOfStock) {
                                            p.sendMessage("<col=00c203>" + "Trading Post: Finished selling all of " + offer.getItem().getDef().name + ".</col>");
                                        } else {
                                            p.sendMessage("<col=00c203>" + "Trading Post: " + finalAmount + "/" + offer.getItem().getAmount() + " of " + offer.getItem().getDef().name + " sold.</col>");
                                        }

                                        if (offerIndex != -1 && p.isVisibleInterface(MY_OFFERS_WIDGET)) {
                                            p.getTradePost().updateMyOffer(offerIndex, outOfStock ? null : tradePostOffer);
                                        }
                                    });


                                    player.sendMessage("<col=ff0000>" + "You have purchased " + finalAmount + "x " + offer.getItem().getDef().name + " for a price of " + formatPrice(price) + ".</col>");
                                    if (bank) {
                                        player.sendMessage(offer.getItem().getAmount() + "x " + offer.getItem().getDef().name + " has been sent to your bank!");
                                    }
                                    TradePostManager.saveHistory(0, new Item(offer.getItem().getId(), finalAmount), offer.getPricePerItem(), container());
                                    player.getTradePost().openViewOffers();
                                });
                            }),
                            new Option("No", player::closeDialogue)
                    )
            );
        });
    }

    private void updateViewOffers() {
        viewOffers = findViewOffers();

        for (int index = 0; index < MAX_VIEW_OFFERS; index++) {
            hideViewOffer(index, true);
        }

        for (int index = 0; index < viewOffers.size(); index++) {
            updateViewOffer(index, viewOffers.get(index));
            hideViewOffer(index, false);
        }

        int scrollBarWidgetId = 481;
        int scrollContainerWidgetId = 30;
        player.getPacketSender().sendClientScript(
                30, "ii",
                VIEW_OFFERS_WIDGET << 16 | scrollBarWidgetId,
                VIEW_OFFERS_WIDGET << 16 | scrollContainerWidgetId
        );

    }

    private void hideViewOffer(int index, boolean hidden) {
        player.getPacketSender().sendClientScript(69, "ii", hidden ? 1 : 0, VIEW_OFFERS_WIDGET << 16 | (31 + (9 * index)));
    }

    private void updateViewOffer(int index, TradePostOffer offer) {
        String price = formatPrice(offer.getPricePerItem()) + " ea";
        String totalPrice = "=" + formatPrice((long) offer.getPricePerItem() *
                offer.getItem().getAmount()) + " total";

        int containerWidgetId = 33 + (index * 9);
        int priceWidgetId = 34 + (index * 9);
        int totalPriceWidgetId = 35 + (index * 9);
        int usernameWidgetId = 36 + (index * 9);
        int ageWidgetId = 37 + (index * 9);

        player.getPacketSender().sendItems(VIEW_OFFERS_WIDGET, containerWidgetId, 0, offer.getItem());
        player.getPacketSender().sendString(VIEW_OFFERS_WIDGET, priceWidgetId, price);
        player.getPacketSender().sendString(VIEW_OFFERS_WIDGET, totalPriceWidgetId, totalPrice);
        player.getPacketSender().sendString(VIEW_OFFERS_WIDGET, usernameWidgetId, offer.getUsername());
        player.getPacketSender().sendString(VIEW_OFFERS_WIDGET, ageWidgetId, formatAge(offer.getTimestamp()));
    }

    private static String formatPrice(long price) {
        if (price > 9_999_999) {
            return NumberUtils.formatNumber(price / 1000_000) + "M";
        } else if (price > 99_999) {
            return NumberUtils.formatNumber(price / 1000) + "K";
        }

        return NumberUtils.formatNumber(price);
    }

    private String formatAge(long timestamp) {
        long elapsed = System.currentTimeMillis() - timestamp;
        long days = TimeUnit.MILLISECONDS.toDays(elapsed);
        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);

        return days > 0 ? days + "d"
                : hours > 0 ? hours + "h"
                : minutes + "min";
    }

    public static void openCoffer(Player player) {
        if (player.getGameMode().isIronMan()) {
            player.sendMessage("Your gamemode prevents you from accessing the trading post!");
            return;
        }
        long coffer = player.getTradePost().container().getCoffer();
        player.integerInput("How much would you like to withdraw from coffer: " + formatPrice(coffer), amount -> {

            if (coffer == 0) {
                player.sendMessage("You have no coins to collect.");
                return;
            }

            if (amount < 1) {
                return;
            }

            if (coffer < amount) {
                amount = (int) coffer;
            }

            long inventoryAmount = player.getInventory().getAmount(PLATINUM_TOKEN);
            if (inventoryAmount + amount >= Integer.MAX_VALUE) {
                player.sendMessage("Please bank the coins in your inventory before doing this!");
                return;
            }
            int tokensAmount = player.getInventory().getAmount(COINS_995) / 1000;
            int tokens = player.getInventory().count(PLATINUM_TOKEN);

            int removeAmount = (tokensAmount * 1000);
            if (tokens >= 2147480000 || (tokens + tokensAmount) >= Integer.MAX_VALUE) {
                player.dialogue(new MessageDialogue("Your Platinum tokens stack is too large!"));
                return;
            }
            int freeSlots = player.getInventory().getFreeSlots();
            if (removeAmount == player.getInventory().getAmount(COINS_995))           // Whoever Coded this before I've fixed it, You're a prick. Love Luke!
                freeSlots++;
            if (freeSlots == 0) {
                player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                return;
            }

            player.getInventory().add(PLATINUM_TOKEN, amount / 1000);

            player.getTradePost().container().removeFromCoffer(amount);

        });
    }

    static {
        InterfaceHandler.register(MY_OFFERS_INVENTORY_WIDGET, handler -> {
            handler.actions[0] = new InterfaceAction() {
                public void handleClick(Player player, int option, int slot, int itemId) {
                    Item item = player.getInventory().get(slot);
                    if (item.getUniqueValue() != 0) {
                        player.sendMessage("You can't offer upgraded items on the trading post.");
                        return;
                    }
                    if (item.getId() == 13307 || item.getId() == 995) {
                        player.sendMessage("You can't offer currency on the trading post.");
                        return;
                    }
                    player.getTradePost().promptCreateOffer(itemId);
                }
            };
        });

        InterfaceHandler.register(383, handler -> {
            handler.actions[2] = (SimpleAction) player -> player.getTradePost().openMyOffers();
        });

        InterfaceHandler.register(MY_OFFERS_WIDGET, handler -> {
            handler.actions[120] = (SimpleAction) player -> {
                player.getTradePost().advertise();
            };
            handler.actions[15] = (SimpleAction) player -> {
                player.getTradePost().openViewOffers();
            };
            handler.actions[16] = (SimpleAction) player -> {
                player.getTradePost().viewHistory();
            };
            for (int i = 0; i < MAX_MY_OFFERS; i++) {
                final int index = i;
                handler.actions[23 + (index * 17)] = (SimpleAction) player -> {
                    player.getTradePost().promptAdjustOffer(index);
                };
                handler.actions[27 + (index * 17)] = (SimpleAction) player -> {
                    player.getTradePost().resetOffer(index);
                };
            }
        });

        InterfaceHandler.register(VIEW_OFFERS_WIDGET, handler -> {
            handler.actions[485] = (SimpleAction) player -> {
                player.getTradePost().openMyOffers();
            };
            handler.actions[482] = (SimpleAction) player -> {
                player.getTradePost().promptSearch(true);
            };
            handler.actions[28] = (SimpleAction) player -> {
                player.getTradePost().viewHistory();
            };
            handler.actions[29] = (SimpleAction) player -> {
                player.getTradePost().openMyOffers();
            };
            handler.actions[17] = (SimpleAction) player -> {
                player.getTradePost().sortByPrice();
            };
            handler.actions[20] = (SimpleAction) player -> {
                player.getTradePost().sortByAge();
            };
            handler.actions[25] = (SimpleAction) player -> {
                player.getTradePost().promptSearch(false);
            };
            for (int i = 0; i < MAX_VIEW_OFFERS; i++) {
                final int index = i;
                handler.actions[38 + (index * 9)] = (SimpleAction) player -> {
                    player.getTradePost().buy(index);
                };
            }
        });

        int[] clerks = {2148, 2149};

        for (int id : clerks) {
            NPCAction.register(id, "history", (player, npc) -> {
                if (player.getGameMode().isIronMan()) {
                    player.sendMessage("Your game mode prevents you from accessing the trading post!");
                    return;
                }
                player.getTradePost().viewHistory();
            });

            NPCAction.register(id, "talk-to", (player, npc) -> {
                player.dialogue(new NPCDialogue(npc, "Welcome to the Trading Post! Would you like to trade now, or exchange item sets?"),
                        new OptionsDialogue(
                                new Option("I'd like to setup trade offers please.", p -> {
                                    p.dialogue(new PlayerDialogue("I'd like to setup trade offers please."), p1 -> {
                                        if (p1.getGameMode().isIronMan()) {
                                            p1.sendMessage("Your gamemode prevents you from accessing the trading post!");
                                            return;
                                        }
                                        if (World.isPVPWorld()) {
                                            p1.sendMessage("You cannot do this on a PVP World");
                                            return;
                                        }

                                        p1.getTradePost().openMyOffers();
                                    });
                                }),
                                new Option("Can you help me with item sets?", p -> {
                                    p.dialogue(new PlayerDialogue("Can you help me with item sets?"), ItemSet::open);
                                }),
                                new Option("I'm fine, thanks.", p -> p.dialogue(new PlayerDialogue("I'm fine, thanks.")))
                        ));

            });
        }

        SpawnListener.find(2148).forEach(npc -> {
            if (npc.getPosition().getX() == 2734 && npc.getPosition().getY() == 3470) {
                npc.addEvent((e) -> {
                    while (true) {
                        if (ads.isEmpty()) {
                            e.delay(10);
                            continue;
                        }

                        TradePostOffer offer = ads.get(adsIndex);

                        if (offer.isRemoved() || System.currentTimeMillis() > offer.getAdEnd()) { // Expired
                            offer.setAdEnd(0);
                            ads.remove(offer);
                            //  DiscordConnection.removeMessage(TRADING_POST_CHANNEL, offer.getEmbedId());
                        } else {
                            npc.forceText("[S] " + offer.getItem().getDef().name + " x " + offer.getItem().getAmount() + " for " + Misc.currency(offer.getPricePerItem()) + "ea --" + offer.getUsername());
                            e.delay(10);
                        }

                        if (adsIndex < ads.size() - 1) {
                            adsIndex++;
                        } else {
                            adsIndex = 0;
                        }
                    }
                });
            }
        });
    }

    static {

        for (int trader : new int[]{2149, 2148}) {
            SpawnListener.register(trader, npc -> npc.startEvent(event -> {
                if (npc.getPosition().getX() == 1746 && npc.getPosition().getY() == 3599 || npc.getPosition().getX() == 1746 && npc.getPosition().getY() == 3597) {
                    npc.addEvent((e) -> {
                        while (true) {
                            if (ads.isEmpty()) {
                                npc.forceText("Come check out the trading post!");
                                e.delay(150);
                                continue;
                            }

                            TradePostOffer offer = ads.get(adsIndex);

                            if (offer.isRemoved() || System.currentTimeMillis() > offer.getAdEnd()) { // Expired
                                offer.setAdEnd(0);
                                ads.remove(offer);
                                //  DiscordConnection.removeMessage(TRADING_POST_CHANNEL, offer.getEmbedId());
                            } else {
                                npc.forceText("[S] " + offer.getItem().getDef().name + " x " + offer.getItem().getAmount() + " for " + Misc.currency(offer.getPricePerItem()) + "ea --" + offer.getUsername());
                                e.delay(10);
                            }

                            if (adsIndex < ads.size() - 1) {
                                adsIndex++;
                            } else {
                                adsIndex = 0;
                            }
                        }
                    });
                }
            }));
            NPCAction.register(trader, "sets", (player, npc) -> ItemSet.open(player));
            NPCAction.register(trader, "exchange", ((player, npc) -> {
                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.GE);
                if (player.getGameMode().isIronMan()) {
                    player.sendMessage("Your gamemode prevents you from accessing the trading post!");
                    return;
                }
                if (World.isPVPWorld()) {
                    player.sendMessage("Trade post has been disabled for the PVP World.");
                    return;
                }
                player.getTradePost().openViewOffers();
            }));
        }
    }

}