package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class ExperimentsCave {

    static {
        ObjectAction.register(5167, 3578, 3527, 0, "push", ((player, obj) -> TeleportInterface.teleport(player, new Position(3577, 9927, 0))));
    }
}
