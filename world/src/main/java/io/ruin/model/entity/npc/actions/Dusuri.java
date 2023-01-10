package io.ruin.model.entity.npc.actions;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.stat.StatType;

public class Dusuri {
    static {
        NPCAction.register(10631, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Hello, " + player.getName() + ". Would you like to see the Stardust shop, or exchange them for XP"),
                new OptionsDialogue(
                        new Option("Open shop", () -> ShopManager.openIfExists(player, "STARDUSTSHOP22")),
                        new Option("Trade in Stardust for Mining XP", () -> {
                            player.integerInput("How many stardust would you like exchange for XP (100xp Ea)", integer -> {
                                int amount = player.getInventory().count(ItemID.STARDUST);

                                if (integer >= amount)
                                    integer = amount;

                                if (integer <= 0)
                                    return;

                                player.getStats().addXp(StatType.Mining, 100.0 * integer, true);
                                player.getInventory().remove(ItemID.STARDUST, integer);
                            });
                        }),
                        new Option("Trade in Stardust for RC Xp", () -> {
                            player.integerInput("How many stardust would you like exchange for XP (100xp Ea)", integer -> {
                                int amount = player.getInventory().count(ItemID.STARDUST);

                                if (integer >= amount)
                                    integer = amount;

                                if (integer <= 0)
                                    return;

                                player.getStats().addXp(StatType.Runecrafting, 100.0 * integer, true);
                                player.getInventory().remove(ItemID.STARDUST, integer);
                            });
                        }))
        ));
    }
}
