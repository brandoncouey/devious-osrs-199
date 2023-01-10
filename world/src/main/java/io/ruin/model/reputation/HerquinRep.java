package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class HerquinRep {

    private static final int HERQUIN = 2886;

    static {
        NPCAction.register(HERQUIN, "talk-to", HerquinRep::herquinDialogue);
    }

    private static void herquinDialogue(Player player, NPC npc) {
        if (PlayerCounter.HERQUIN_REP.get(player) == 0) {
            player.dialogue(
                    new PlayerDialogue("Hello, I'm in search of adventure!"),
                    new NPCDialogue(npc, "Aren't we all! I'm afraid I don't have any exciting adventures that I require, however, I am in need of more uncut gems!" +
                            " I don't have much to offer in terms of money, but your reputation in this part of town would"),
                    new NPCDialogue(npc, "certainly catch the eye of others who could offer you more exciting journies! Feel free to bring me 10 uncut rubies, " +
                            "diamonds, or dragonstones. I appreciate the help, young lad!"),
                    new MessageDialogue("Bring Herquin either 10 uncut rubies, diamonds, or dragonstones. This can be repeated."),
                    new ActionDialogue(() -> {
                        PlayerCounter.HERQUIN_REP.increment(player, 1);//stage 1
                    })
            );
        }
        if (PlayerCounter.HERQUIN_REP.get(player) == 1 && player.faladorRep < 10000) {
            player.dialogue(
                    new OptionsDialogue("Choose an option",
                            new Option("Offer 10 uncut rubies", () -> uncutRubies(player, npc)),
                            new Option("Offer 10 uncut diamonds", () -> uncutDiamonds(player, npc)),
                            new Option("Offer 10 uncut dragonstones", () -> uncutDragonstones(player, npc)),
                            new Option("Nevermind")

                    )
            );
        }
    }

    public static void uncutRubies(Player player, NPC npc) {
        if (player.getInventory().contains(1619, 10)) {
            player.getInventory().remove(1619, 10);
            player.faladorRep = +10;
            player.sendMessage("+10 Falador Reputation");
            player.dialogue(
                    new MessageDialogue("You offer 10 uncut rubies to Herquin"),
                    new NPCDialogue(npc, "Your offering is very much appreciated adventurer!")
            );
        }
    }

    public static void uncutDiamonds(Player player, NPC npc) {
        if (player.getInventory().contains(1617, 10)) {
            player.getInventory().remove(1617, 10);
            player.faladorRep = +12;
            player.sendMessage("+12 Falador Reputation");
            player.dialogue(
                    new MessageDialogue("You offer 10 uncut diamonds to Herquin"),
                    new NPCDialogue(npc, "Your offering is very much appreciated adventurer!")
            );
        }
    }

    public static void uncutDragonstones(Player player, NPC npc) {
        if (player.getInventory().contains(1631, 10)) {
            player.getInventory().remove(1631, 10);
            player.faladorRep = +15;
            player.sendMessage("+15 Falador Reputation");
            player.dialogue(
                    new MessageDialogue("You offer 10 uncut dragonstones to Herquin"),
                    new NPCDialogue(npc, "Your offering is very much appreciated adventurer!")
            );
        }
    }
}