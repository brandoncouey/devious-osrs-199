package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/22/2020
 */
public class AdventureJon {

    private static void changeMode(Player player, NPC npc) {
        /*player.dialogue(new NPCDialogue(npc, "Hi, "+player.getName()+"! I'm Jon.<br>" +
                "I can help you with your <col=ff0000>XP Mode</col> if you're<br>" +
                "having a hard time leveling up in Devious"),
                new OptionsDialogue("Would you like to increase your XP Rate?",
                        new Option("Sure!", () -> XpMode.changeMode(player, npc)),
                        new Option("No, I like the challenge!", player::closeDialogue)
                ));*/
    }

    static {
        //  NPCAction.register(9244, "Talk-to", ((player, npc) -> changeMode(player, npc)));
    }

}
