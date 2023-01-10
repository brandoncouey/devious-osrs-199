package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class SirRebral {

    private static final int REBRAL = 5524;

    static {
//        NPCAction.register(REBRAL, "talk-to", SirRebral::rebralDialogue);
    }

    private static void rebralDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hello there, adventurer. How can I assist you?"),
                new OptionsDialogue("Choose an option",
                        new Option("Ask about Falador Diary", () -> getQuest(player, npc)),
                        new Option("How can I earn reputation here?", () -> askRep(player, npc))

                )
        );
    }

    public static void askRep(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("How can I gain reputation here in Falador?"),
                new NPCDialogue(npc, "There are many ways to earn reputation here. You can talk to various people around Falador to see if they have any tasks " +
                        "for you, you can train your agility on the rooftop course, and you can even"),
                new NPCDialogue(npc, "slay giant moles by venturing into the mole hills in the Falador Park, as they are constantly ruining our beautiful park! " +
                        "You can return to me for rewards once you've gained enough reputation here in Falador!")
        );
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area.")
        );
    }

}