package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;

public class SigmundTheMerch {

    private final static int Sigmund = 3894;

    static {
        NPCAction.register(Sigmund, "trade", (player, npc) -> {
            player.getTrade().tradeSigmund();

        });
    }

}
