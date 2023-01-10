package io.ruin.model.skills.mining;

import io.ruin.model.map.object.actions.ObjectAction;

public class DaeyaltEssence {

    // 39095 Essence "Mine"
    // 17962 Rocks "Mine"

    static {
        ObjectAction.register(39093, "climb-up", (player, obj) -> {
            player.startEvent(e -> {
                player.animate(828);
                e.delay(1);
                player.getMovement().teleport(3633, 3339, 0);
            });
        });
        ObjectAction.register(39092, "climb-down", ((player, obj) -> {
            player.startEvent(e -> {
                player.animate(827);
                e.delay(1);
                player.getMovement().teleport(3697, 9764, 2);
            });
        }));
    }
}
