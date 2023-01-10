package io.ruin.model.entity.npc.actions;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.stat.StatType;

public class Grace {
    static {
        NPCAction.register(5919, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Hello, " + player.getName() + ". Would you like to see the mark exchange, or learn more about marks of grace?"),
                new OptionsDialogue(
                        new Option("Open shop", () -> ShopManager.openIfExists(player, "")),
                        new Option("Learn more", () -> {
                            if (!player.insideWildernessAgilityCourse) {
                                player.dialogue(new NPCDialogue(npc, "While practicing on rooftop agility courses, you will occasionally encounter marks of grace.<br>" +
                                        "Bring them to me and we can trade.<br>My outfit will allow you to run longer distances and recover more quickly."));
                            } else {
                                player.dialogue(new NPCDialogue(npc, "While completing this wilderness course, you'll be rewarded anywhere from 1 to 3 marks of grace along with a little blood money."),
                                        new NPCDialogue(npc, "You can spend those marks inside my shop for Graceful clothing. If you kill somebody in here, I'll reward you with 50k agility experience and"),
                                        new NPCDialogue(npc, "10 marks of grace. Be careful though, as this place gets quite dangerous!"),
                                        new PlayerDialogue("Thank you, Grace! I'll keep an eye out."));
                            }
                        }),
                        new Option("Trade in marks of grace", () -> {
                            player.integerInput("How many marks of grace would you like exchange for XP (100xp Ea)", integer -> {
                                int amount = player.getInventory().count(ItemID.MARK_OF_GRACE);

                                if (integer >= amount)
                                    integer = amount;

                                if (integer <= 0)
                                    return;

                                player.getStats().addXp(StatType.Agility, 100.0 * integer, true);
                                player.getInventory().remove(ItemID.MARK_OF_GRACE, integer);
                            });
                        }))
        ));
    }
}
