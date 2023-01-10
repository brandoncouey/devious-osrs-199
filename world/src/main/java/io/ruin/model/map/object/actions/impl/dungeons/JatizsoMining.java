package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class JatizsoMining {
    static {
        ObjectAction.register(21578, 2406, 10188, 0, "climb-up", ((player, obj) -> TeleportInterface.teleport(player, new Position(2397, 3814, 0))));
        ObjectAction.register(21455, 2397, 3812, 0, "climb-down", ((player, obj) -> TeleportInterface.teleport(player, new Position(2406, 10190, 0))));
    }
}
