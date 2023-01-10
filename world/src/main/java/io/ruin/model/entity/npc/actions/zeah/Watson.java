package io.ruin.model.entity.npc.actions.zeah;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

public class Watson {
    static {
        NPCAction.register(7303, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Hello, adventurer. How may I help you?"),
                    new OptionsDialogue(
                            new Option("What do you do here?", () -> {
                                player.dialogue(
                                        new NPCDialogue(npc, "Players can bring me one of each type of clue scroll and I will exchange them for a master clue!")
                                );
                            }),
                            new Option("I'd like to exchange my clue boxes for a master clue!", plr -> exchangeClues(player, npc)),
                            new Option("I'd like to exchange my clue scrolls for a master clue!", plr -> exchangeClues2(player, npc)),
                            new Option("Nevermind")
                    ));
        });
    }
    public static void exchangeClues2(Player player, NPC npc) {
        if (player.getInventory().contains(2677, 1) &&
                player.getInventory().contains(2801, 1) &&
                player.getInventory().contains(2722, 1) &&
                player.getInventory().contains(12073, 1)) {
            player.getInventory().remove(2677, 1);
            player.getInventory().remove(2801, 1);
            player.getInventory().remove(2722, 1);
            player.getInventory().remove(12073, 1);
            player.getInventory().add(24366, 1);
            player.dialogue(
                    new MessageDialogue("Watson exchanges your clues for a master clue!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required clues to do this! You need one easy, medium, hard, and elite clue to trade for a master clue.")
            );
        }
    }

    public static void exchangeClues(Player player, NPC npc) {
        if (player.getInventory().contains(24365, 1) &&
                player.getInventory().contains(24364, 1) &&
                player.getInventory().contains(24363, 1) &&
                player.getInventory().contains(24362, 1)) {
            player.getInventory().remove(24365, 1);
            player.getInventory().remove(24364, 1);
            player.getInventory().remove(24363, 1);
            player.getInventory().remove(24362, 1);
            player.getInventory().add(24366, 1);
            player.dialogue(
                    new MessageDialogue("Watson exchanges your clues for a master clue!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have the required clues to do this! You need one easy, medium, hard, and elite clue to trade for a master clue.")
            );
        }
    }
}