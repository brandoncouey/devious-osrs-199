package io.ruin.model.entity.npc.actions;

import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;

public class Sedridor {

    static {
        NPCAction.register(5034, "teleport", ((player, npc) -> {
            player.sendMessage("Sedridor teleports you to the essence mine.");
            player.getMovement().teleport(2910, 4830, 0);
            player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.TELEPORT_ESSENCE_LUM);
        }));
    }

}