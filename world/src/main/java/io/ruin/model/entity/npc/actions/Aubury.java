package io.ruin.model.entity.npc.actions;

import io.ruin.model.diaries.varrock.VarrockDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;

public class Aubury {

    private static final int AUBURY = 2886;

    static {
        NPCAction.register(AUBURY, "teleport", ((player, npc) -> {
            player.sendMessage("Aubury teleports you to the essence mine.");
            player.getMovement().teleport(2910, 4830, 0);
            player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.TELEPORT_ESSENCE_VAR);
        }));
    }

}