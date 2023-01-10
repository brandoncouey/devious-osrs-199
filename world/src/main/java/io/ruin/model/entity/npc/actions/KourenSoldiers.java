package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.listeners.SpawnListener;

public class KourenSoldiers {

    static {
        SpawnListener.register(6885, npc -> {
            npc.addEvent(e -> {
                while (true) {
                    e.delay(Random.get(2, 4));
                    npc.animate(2763);
                }
            });
        });
    }
}
