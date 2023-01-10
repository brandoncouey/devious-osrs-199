package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class EllamariaRep {

    private static final int ELLAMARIA = 2886;

    static {
        NPCAction.register(ELLAMARIA, "talk-to", EllamariaRep::ellamariaDialogue);
    }

    private static void ellamariaDialogue(Player player, NPC npc) {
        if (PlayerCounter.ELLAMARIA_REP.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Hello there, traveler. What brings you to the royal gardens?"),
                    new OptionsDialogue("Choose an option",
                            new Option("Do you have any quests for me?", () -> getQuest(player, npc)),
                            new Option("Nevermind")

                    )
            );
        }
        if (PlayerCounter.ELLAMARIA_REP.get(player) == 1) {
            player.dialogue(
                    new OptionsDialogue("Choose an option",
                            new Option("I've brought 25 watermelon seeds", () -> watermelonSeeds(player, npc)),
                            new Option("I've brought 10 torstol seeds", () -> torstolSeeds(player, npc)),
                            new Option("I've brought 10 willow seeds", () -> willowSeeds(player, npc)),
                            new Option("I've brought 5 snapdragon seeds", () -> getQuest(player, npc))

                    )
            );
        }
    }

    public static void watermelonSeeds(Player player, NPC npc) {
        if (player.getInventory().contains(5321, 25)) {
            player.getInventory().remove(5321, 25);
            player.varrockRep = +10;
            player.sendMessage("+10 Varrock Reputation");
            player.dialogue(
                    new MessageDialogue("You hand the watermelon seeds over to Ellamaria"),
                    new NPCDialogue(npc, "Thank you for your contribution!")
            );
        }
    }

    public static void torstolSeeds(Player player, NPC npc) {
        if (player.getInventory().contains(5304, 10)) {
            player.getInventory().remove(5304, 10);
            player.varrockRep = +10;
            player.sendMessage("+10 Varrock Reputation");
            player.dialogue(
                    new MessageDialogue("You hand the torstol seeds over to Ellamaria"),
                    new NPCDialogue(npc, "Thank you for your contribution!")
            );
        }
    }

    public static void willowSeeds(Player player, NPC npc) {
        if (player.getInventory().contains(5313, 10)) {
            player.getInventory().remove(5313, 10);
            player.varrockRep = +10;
            player.sendMessage("+10 Varrock Reputation");
            player.dialogue(
                    new MessageDialogue("You hand the willow seeds over to Ellamaria"),
                    new NPCDialogue(npc, "Thank you for your contribution!")
            );
        }
    }

    public static void snapdragonSeeds(Player player, NPC npc) {
        if (player.getInventory().contains(5300, 5)) {
            player.getInventory().remove(5300, 5);
            player.varrockRep = +10;
            player.sendMessage("+10 Varrock Reputation");
            player.dialogue(
                    new MessageDialogue("You hand the snapdragon seeds over to Ellamaria"),
                    new NPCDialogue(npc, "Thank you for your contribution!")
            );
        }
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "I could surely use some assistance! I am planning a surprise for the King's birthday with a wonderful royal garden!" +
                        " I want it to be spectacular, however I am having trouble"),
                new NPCDialogue(npc, "finding some of the supplies for it.. I need 25 watermelon seeds, 10 torstol seeds, 10 willow seeds, and 5 snapdragon seeds!" +
                        " If you bring me these, it would help to give"),
                new NPCDialogue(npc, "the King a great surprise for his birthday!"),
                new PlayerDialogue("I will see which seeds I can locate for you!"),
                new MessageDialogue("Bring Ellamaria either 25 watermelon seeds, 10 torstol seeds, 10 willow seeds, or 5 snapdragon seeds. This can be repeated."),
                new ActionDialogue(() -> {
                    PlayerCounter.ELLAMARIA_REP.increment(player, 1);//stage 1
                })
        );
    }

}