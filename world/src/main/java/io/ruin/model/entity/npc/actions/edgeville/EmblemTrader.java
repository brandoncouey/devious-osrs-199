package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.shop.Shop;
import io.ruin.model.shop.ShopItem;
import io.ruin.model.shop.ShopManager;

import static io.ruin.cache.ItemID.*;

public class EmblemTrader {

    static {
        //Wilderness Emblem Trader
        NPCAction.register(308, "talk-to", (player, npc) -> {
            String currencyName = "coins";
            player.dialogue(new NPCDialogue(npc, "If you find an ancient emblem, totem, or statuette, use it on me and I'll exchange it for " + currencyName + "."));
        });
        int[][] ancientArtifacts = {
                {21807, 500_000},   //Emblem
                {21810, 1_000_000},  //Totem
                {21813, 2_000_000},  //Statuette
                {22299, 4_000_000},  //Medallion
                {22302, 8_000_000},  //Effigy
                {22305, 10_000_000}  //Relic
        };
        for (int[] artifact : ancientArtifacts) {
            int id = artifact[0];
            ItemDef.get(id).sigmundBuyPrice = artifact[1];
            ItemNPCAction.register(id, 308, (player, item, npc) -> player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sell your " + ItemDef.get(id).name +
                    " for " + artifact[1] + " coins?", item, () -> {
                item.remove();
                player.getInventory().add(COINS_995, artifact[1]);
                player.dialogue(new NPCDialogue(npc, "Excellent find, " + player.getName() + "! If you find anymore artifacts you know where to find me!"));
            })));
        }
        //Devious Emblem Trader
/*        ItemNPCAction.register(BLOOD_FRAGMENT, 308, (player, item, npc) -> player.dialogue(new MessageDialogue("<col=ff0000>Warning:</col> you are about to swap all your blood fragments for " +
                "blood money. Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> item.setId(BLOOD_MONEY)),
                        new Option("No")
                )));*/
    /*    ItemNPCAction.register(BLOOD_MONEY, 308, (player, item, npc) -> player.dialogue(new MessageDialogue("<col=ff0000>Warning:</col> you are about to swap all your blood money for " +
                        "Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> fromBloodMoneyToPlatinum(player, npc)),
                        new Option("No")
                )));
        ItemNPCAction.register(COINS_995, 308, (player, item, npc) -> player.dialogue(new MessageDialogue("<col=ff0000>Warning:</col> you are about to swap all your coins for " +
                        "Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> fromCoinsToBloodTokens(player, npc)),
                        new Option("No")
                )));*/
       ItemNPCAction.register(30308, 308, (player, item, npc) -> player.dialogue(new MessageDialogue("<col=ff0000>Warning:</col> you are about to swap all your donator coins for " +
                        "Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> fromDonatorToBloodMoney(player, item)),
                        new Option("No")
                )));
        NPCAction.register(308, 1, (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Hello there, are you interested in converting your currencies?"),
                    new OptionsDialogue(
                  new Option("I'd like to exchange my Blood Money for Platinum Tokens", () -> {
                      if (!player.getInventory().hasId(BLOOD_MONEY)) { // If they don't have any blood money, return
                          player.dialogue(new NPCDialogue(npc, "You don't have any Blood Money to exchange."));
                          return;
                      }
                      int exchangeRate = 100;
                      int bloodMoney = player.getInventory().findItem(BLOOD_MONEY).getAmount();
                      long platTokens = 0;

                      if (player.getInventory().hasId(PLATINUM_TOKEN)) // make platTokens equal to how many they have
                          platTokens = player.getInventory().findItem(PLATINUM_TOKEN).getAmount();

                          if (platTokens + bloodMoney <= Integer.MAX_VALUE) { // Make sure they can accept the amount of tokens
                              int freeSlots = player.getInventory().getFreeSlots();
                              if ((player.getInventory().hasId(PLATINUM_TOKEN) && player.getInventory().hasId(BLOOD_MONEY)) || freeSlots > 0) { // If they already have plat tokens and blood money, don't need to check freeSlots
                                  player.getInventory().remove(BLOOD_MONEY, bloodMoney);
                                  player.getInventory().add(PLATINUM_TOKEN, bloodMoney / exchangeRate);
                              } else {
                                  player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space."));
                                  return;
                              }
                          }
                          else {
                              player.dialogue(new NPCDialogue(npc, "You're carrying too many Platinum Tokens to make this transaction."));
                              return;
                          }
                      player.dialogue(new ItemDialogue().two(BLOOD_MONEY, PLATINUM_TOKEN, "The bank exchanges your blood money for platinum tokens."));
                  }),
                  new Option("I'd like to exchange my Platinum Tokens for Blood Money", () -> {
                      if (!player.getInventory().hasId(PLATINUM_TOKEN)) { // If they don't have any blood money, return
                          player.dialogue(new NPCDialogue(npc, "You don't have any Platinum Tokens to exchange."));
                          return;
                      }
                      int exchangeRate = 100;
                      int platTokens = player.getInventory().findItem(PLATINUM_TOKEN).getAmount();
                      long bloodMoney = 0;

                      if (player.getInventory().hasId(BLOOD_MONEY)) // make bloodMoney equal to how many they have
                          bloodMoney = player.getInventory().findItem(BLOOD_MONEY).getAmount();

                      if (bloodMoney + ((long) platTokens * exchangeRate) <= Integer.MAX_VALUE) { // Make sure they can accept the amount of tokens
                          int freeSlots = player.getInventory().getFreeSlots();
                          if ((player.getInventory().hasId(PLATINUM_TOKEN) && player.getInventory().hasId(BLOOD_MONEY)) || freeSlots > 0) { // If they already have plat tokens and blood money, don't need to check freeSlots
                              player.getInventory().remove(PLATINUM_TOKEN, platTokens);
                              player.getInventory().add(BLOOD_MONEY, platTokens * exchangeRate);
                          } else {
                              player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space."));
                              return;
                          }
                      }
                      else {
                          player.dialogue(new NPCDialogue(npc, "You won't be able to hold the amount of Blood Money you'd receive after completing this transaction"));
                          return;
                      }
                      player.dialogue(new ItemDialogue().two(BLOOD_MONEY, PLATINUM_TOKEN, "The bank exchanges your Platinum Tokens for Blood Money."));
                  }),
                  new Option("No", player::closeDialogue)
          ));
        });
        NPCAction.register(308, 5, (player, npc) -> skull(player));
        NPCAction.register(308, "reset-kdr", (player, npc) -> player.dialogue(
                new MessageDialogue("<col=ff0000>Warning:</col> You are about to reset your kills & deaths. All " +
                        "statistics related to kills will also be reset. Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> {
                            Config.PVP_KILLS.set(player, 0);
                            Config.PVP_DEATHS.set(player, 0);
                            player.currentKillSpree = 0;
                            player.highestKillSpree = 0;
                            player.highestShutdown = 0;
                            player.dialogue(new NPCDialogue(npc, "Your kills, deaths, sprees and highest shutdown has been reset."));
                        }),
                        new Option("No")
                )
        ));
        SpawnListener.forEach(npc -> {
            if (npc.getId() == 308 && npc.walkBounds != null)
                npc.walkBounds = new Bounds(3088, 3500, 3097, 3505, 0);
        });


    }


    public static final String SHOP_UUID = "";//TODO fill this out

    public static int getResellPrices(Item itemSelling) {
        if (itemSelling.getDef().id == 21034 || itemSelling.getDef().id == 21079)
            return 0;
        Shop shop = ShopManager.getByUUID(SHOP_UUID);
        if (shop == null)
            return 0;
        for (ShopItem item : shop.defaultStock) {
            int price;
            ItemDef def = item.getDef();
            if (def.id == itemSelling.getDef().id) {
                price = +item.price;
                return price / 4;
            }
        }
        return 0;
    }

    public static void skull(Player player) {
        player.dialogue(new OptionsDialogue(
                new Option("<img=46> Regular <img=46>", () -> player.getCombat().skullNormal()),
                new Option("<img=93> High-Risk <img=93>", () -> player.dialogue(new OptionsDialogue("This skull will prevent you from using the Protect Item prayer.",
                        new Option("Give me the high risk skull!", () -> player.getCombat().skullHighRisk()),
                        new Option("No, I want to use the Protect Item prayer.")))),
                new Option("No Skull", () -> player.getCombat().resetSkull())
        ));
    }

    private static void fromBloodMoneyToPlatinum(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue("Exchange your bloody money for platinum tokens?",
                        new Option("I'd like to exchange my Blood Money for Platinum Tokens", () -> {
                            if (!player.getInventory().hasId(BLOOD_MONEY)) { // If they don't have any blood money, return
                                player.dialogue(new NPCDialogue(npc, "You don't have any Blood Money to exchange."));
                                return;
                            }
                            int exchangeRate = 10000;
                            int bloodMoney = player.getInventory().findItem(BLOOD_MONEY).getAmount();
                            long platTokens = 0;

                            if (player.getInventory().hasId(PLATINUM_TOKEN)) // make platTokens equal to how many they have
                                platTokens = player.getInventory().findItem(PLATINUM_TOKEN).getAmount();

                            int totalBloodExchanges = Math.floorDiv(bloodMoney, exchangeRate);

                            if (totalBloodExchanges > 0) { // Make sure they have enough blood money to complete the exchange

                                if (platTokens + totalBloodExchanges <= Integer.MAX_VALUE) { // Make sure they can accept the amount of tokens
                                    int freeSlots = player.getInventory().getFreeSlots();
                                    if ((player.getInventory().hasId(PLATINUM_TOKEN) && player.getInventory().hasId(BLOOD_MONEY)) || freeSlots > 0) { // If they already have plat tokens and blood money, don't need to check freeSlots
                                        player.getInventory().remove(BLOOD_MONEY, totalBloodExchanges * exchangeRate);
                                        player.getInventory().add(PLATINUM_TOKEN, totalBloodExchanges);
                                    } else {
                                        player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space."));
                                        return;
                                    }
                                }
                                else {
                                    player.dialogue(new NPCDialogue(npc, "You're carrying too many Platinum Tokens to make this transaction."));
                                    return;
                                }
                            }
                            else {
                                player.dialogue(new NPCDialogue(npc, "You need at least " + exchangeRate + " Blood Money to exchange for Platinum Tokens."));
                                return;
                            }
                            player.dialogue(new ItemDialogue().two(BLOOD_MONEY, PLATINUM_TOKEN, "The bank exchanges your blood money for platinum tokens."));
                        }),
                        new Option("I'd like to exchange my Platinum Tokens for Blood Money", () -> {
                            if (!player.getInventory().hasId(PLATINUM_TOKEN)) { // If they don't have any blood money, return
                                player.dialogue(new NPCDialogue(npc, "You don't have any Platinum Tokens to exchange."));
                                return;
                            }
                            int exchangeRate = 10000;
                            int platTokens = player.getInventory().findItem(PLATINUM_TOKEN).getAmount();
                            long bloodMoney = 0;

                            if (player.getInventory().hasId(BLOOD_MONEY)) // make bloodMoney equal to how many they have
                                bloodMoney = player.getInventory().findItem(BLOOD_MONEY).getAmount();

                            if (bloodMoney + ((long) platTokens * exchangeRate) <= Integer.MAX_VALUE) { // Make sure they can accept the amount of tokens
                                int freeSlots = player.getInventory().getFreeSlots();
                                if ((player.getInventory().hasId(PLATINUM_TOKEN) && player.getInventory().hasId(BLOOD_MONEY)) || freeSlots > 0) { // If they already have plat tokens and blood money, don't need to check freeSlots
                                    player.getInventory().remove(PLATINUM_TOKEN, platTokens);
                                    player.getInventory().add(BLOOD_MONEY, platTokens * exchangeRate);
                                } else {
                                    player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space."));
                                    return;
                                }
                            }
                            else {
                                player.dialogue(new NPCDialogue(npc, "You won't be able to hold the amount of Blood Money you'd receive after completing this transaction"));
                                return;
                            }
                            player.dialogue(new ItemDialogue().two(BLOOD_MONEY, PLATINUM_TOKEN, "The bank exchanges your Platinum Tokens for Blood Money."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

    private static void fromDonatorToBloodMoney(Player player, Item donatorCoin) {
        player.dialogue(
                new OptionsDialogue("Exchange your bloody money for platinum tokens?",
                        new Option("Yes", () -> {
                            if (donatorCoin.getAmount() > 100) {
                                player.dialogue(new MessageDialogue("You can only convert 100 Donator Coins at a time."));
                                return;
                            }
                            long tokensAmount = donatorCoin.getAmount() * 10000000 / 1000;
                            Item tokens = player.getInventory().findItem(13307);
                            if (tokens != null) {
                                donatorCoin.incrementAmount(-tokensAmount);
                                tokens.incrementAmount(tokensAmount);
                            } else {
                                int freeSlots = player.getInventory().getFreeSlots();
                                if (tokensAmount == donatorCoin.getAmount())
                                    freeSlots++;
                                if (freeSlots == 0) {
                                    player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                                    return;
                                }
                                donatorCoin.incrementAmount(-tokensAmount);
                                player.getInventory().add(13307, Math.toIntExact(tokensAmount));
                            }
                            player.dialogue(new ItemDialogue().two(30255, 13307, "The bank exchanges your donator coins for blood money."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

    private static void fromCoinsToBloodTokens(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue("Exchange your coins for blood tokens?",
                        new Option("I'd like to exchange my Coins for Blood Tokens", () -> {
                            if (!player.getInventory().hasId(COINS_995)) { // If they don't have any blood money, return
                                player.dialogue(new NPCDialogue(npc, "You don't have any Blood Money to exchange."));
                                return;
                            }
                            int exchangeRate = 10000;
                            int bloodMoney = player.getInventory().findItem(COINS_995).getAmount();
                            long platTokens = 0;

                            if (player.getInventory().hasId(30309)) // make platTokens equal to how many they have
                                platTokens = player.getInventory().findItem(30309).getAmount();

                            int totalBloodExchanges = Math.floorDiv(bloodMoney, exchangeRate);

                            if (totalBloodExchanges > 0) { // Make sure they have enough blood money to complete the exchange

                                if (platTokens + totalBloodExchanges <= Integer.MAX_VALUE) { // Make sure they can accept the amount of tokens
                                    int freeSlots = player.getInventory().getFreeSlots();
                                    if ((player.getInventory().hasId(30309) && player.getInventory().hasId(COINS_995)) || freeSlots > 0) { // If they already have plat tokens and blood money, don't need to check freeSlots
                                        player.getInventory().remove(COINS_995, totalBloodExchanges * exchangeRate);
                                        player.getInventory().add(30309, totalBloodExchanges);
                                    } else {
                                        player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space."));
                                        return;
                                    }
                                } else {
                                    player.dialogue(new NPCDialogue(npc, "You're carrying too many Blood Tokens to make this transaction."));
                                    return;
                                }
                            } else {
                                player.dialogue(new NPCDialogue(npc, "You need at least " + exchangeRate + " Coins to exchange for Blood Tokens."));
                                return;
                            }
                            player.dialogue(new ItemDialogue().two(COINS_995, 30309, "The bank exchanges your coins for blood tokens."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

}