package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class ThessaliaRep {

    private static final int THESSALIA = 10477;

    static {
        NPCAction.register(THESSALIA, "talk-to", ThessaliaRep::thessaliaDialogue);
    }

    private static void thessaliaDialogue(Player player, NPC npc) {
        if (PlayerCounter.THESSALIA_REP.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Hello there, adventurer. How may I help you?"),
                    new OptionsDialogue("Choose an option",
                            new Option("Do you have any quests for me?", () -> getQuest(player, npc)),
                            new Option("Nevermind")

                    )
            );
        }
        if (PlayerCounter.THESSALIA_REP.get(player) == 1 && player.getInventory().contains(950, 28)) {
            player.dialogue(
                    new PlayerDialogue("I've brought you some silk cloth, Thessalia!"),
                    new NPCDialogue(npc, "Thank you, " + player.getName() + "! The King will be very pleased!"),
                    new ActionDialogue(() -> {
                        player.getInventory().remove(950, 28);
                        player.varrockRep = +10;
                        player.sendMessage("+10 Varrock Reputation");
                    })
            );
        }
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "As a matter of fact, yes! My family helps the king by providing clothes for his guards and knights, however, " +
                        "we are running on a very low supply of silk! If you could bring"),
                new NPCDialogue(npc, "me about 28 silk cloth, the King would be very grateful for your help!"),
                new PlayerDialogue("28 silk cloth, sure thing!"),
                new MessageDialogue("Bring Thessalia 28 unnoted silk cloth. This can be repeated."),
                new ActionDialogue(() -> {
                    PlayerCounter.THESSALIA_REP.increment(player, 1);//stage 1
                })
        );
    }

}