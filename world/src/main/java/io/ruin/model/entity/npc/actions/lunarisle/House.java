package io.ruin.model.entity.npc.actions.lunarisle;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.map.object.actions.ObjectAction;

public class House {
    static {
        NPCAction.register(3836, "Go-inside", (player, npc) -> player.getMovement().teleport(2451, 4646, 0));
        ObjectAction.register(16774, "Open", (player, obj) -> player.getMovement().teleport(2089, 3931, 0));
    }
}
