package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class GeKeldagrim {

    static {
        ObjectAction.register(16168, "travel", (player, obj) -> TeleportInterface.teleport(player, new Position(2909, 10174, 0)));
    }
}
