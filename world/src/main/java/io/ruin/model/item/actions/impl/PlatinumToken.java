package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import static io.ruin.cache.ItemID.COINS_995;

public class PlatinumToken {

    private static final int COINS = COINS_995;

    private static final int PLATINUM_TOKENS = 13204;

    public static boolean exchange(Player player, Item item) {
        if (item.getId() == COINS) {
            fromCoins(player, item);
            return true;
        }
        if (item.getId() == PLATINUM_TOKENS) {
            toCoins(player, item);
            return true;
        }
        return false;
    }

    private static void fromCoins(Player player, Item coins) {
        if (coins.getAmount() < 1000) {
            player.dialogue(new MessageDialogue("You need at least 1,000 coins to exchange them for platinum tokens."));
            return;
        }
        player.dialogue(
                new OptionsDialogue("Exchange your coins for platinum tokens?",
                        new Option("Yes", () -> {
                            int tokensAmount = coins.getAmount() / 1000;
                            int tokens = player.getInventory().count(PLATINUM_TOKENS);
//                            if(tokens != null) {
                            int removeAmount = (tokensAmount * 1000);
                            if (tokens >= 2147480000 || (tokens + tokensAmount) >= Integer.MAX_VALUE) {
                                player.dialogue(new MessageDialogue("Your Platinum tokens stack is too large!"));
                                return;
                            }
                            int freeSlots = player.getInventory().getFreeSlots();
                            if (removeAmount == coins.getAmount())           // Whoever Coded this before I've fixed it, You're a prick. Love Luke!
                                freeSlots++;
                            if (freeSlots == 0) {
                                player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                                return;
                            }

                            removeAmount = Math.min(coins.getAmount(), removeAmount);
                            player.getInventory().remove(995, removeAmount);
                            player.getInventory().add(PLATINUM_TOKENS, tokensAmount);
                            System.err.println(removeAmount);
//                            }
                            System.err.println(tokensAmount);
                            player.dialogue(new ItemDialogue().two(COINS_995, 13204, "The bank exchanges your coins for platinum tokens."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

    private static void toCoins(Player player, Item tokens) {
        player.dialogue(
                new OptionsDialogue("Exchange your platinum tokens for coins?",
                        new Option("Yes", () -> {
                            Item coins = player.getInventory().findItem(COINS);
                            if (coins != null) {
                                int removeAmount = (Integer.MAX_VALUE - coins.getAmount()) / 1000;
                                if (removeAmount == 0) {
                                    player.dialogue(new MessageDialogue("Your coin stack is too large!"));
                                    return;
                                }
                                removeAmount = Math.min(tokens.getAmount(), removeAmount);
                                tokens.incrementAmount(-removeAmount);
                                coins.incrementAmount(removeAmount * 1000);
                            } else {
                                int removeAmount = Integer.MAX_VALUE / 1000;
                                removeAmount = Math.min(tokens.getAmount(), removeAmount);
                                int freeSlots = player.getInventory().getFreeSlots();
                                if (removeAmount == tokens.getAmount())
                                    freeSlots++;
                                if (freeSlots == 0) {
                                    player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                                    return;
                                }
                                tokens.incrementAmount(-removeAmount);
                                player.getInventory().add(COINS, removeAmount * 1000);
                            }
                            player.dialogue(new ItemDialogue().two(COINS_995, 13204, "The bank exchanges your platinum tokens for coins."));
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

}
