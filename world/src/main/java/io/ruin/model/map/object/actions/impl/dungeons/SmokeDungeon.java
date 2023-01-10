package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;

public class SmokeDungeon {

    private static final Bounds DUNGEON_BOUNDS = new Bounds(3168, 9345, 3327, 9407, -1);

    static {
        MapListener.registerBounds(DUNGEON_BOUNDS).onEnter(player -> {
                    player.openInterface(InterfaceType.PRIMARY_OVERLAY, 313);
                })
                .onExit((player, logout) -> {
                    player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
                });
    }

}
