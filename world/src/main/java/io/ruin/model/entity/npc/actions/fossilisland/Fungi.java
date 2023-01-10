package io.ruin.model.entity.npc.actions.fossilisland;

import io.ruin.model.entity.npc.NPCAction;

public class Fungi {
    private static final int FUNGI = 471;
    private static final int ZYGOMITE = 7797;

    static {
        NPCAction.register(FUNGI, 1, (player, npc) -> {
            player.animate(3335);
            npc.lock();
            npc.addEvent(event -> {
                npc.animate(3329);
                npc.graphics(1391);
                event.delay(2);
                npc.transform(ZYGOMITE);
                event.delay(2);
                npc.unlock();
                npc.targetPlayer(player, false);
                npc.face(player);
            });
        });
    }
}
