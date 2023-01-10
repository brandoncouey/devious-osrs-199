package io.ruin.model.activities;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;

public class GroupIronIsland {

    public static final Bounds BOUNDS = new Bounds(3092, 3008, 3117, 3049, 0);

    private static void entered(Player player) {
        player.setAction(1, PlayerAction.GIM_Invite);
    }

    private static void exited(Player player, boolean logout) {
        if (!logout) {
            player.setAction(1, null);
        }
    }


    static {
        MapListener.registerBounds(BOUNDS)
                .onEnter(GroupIronIsland::entered)
                .onExit(GroupIronIsland::exited);
    }

}