package io.ruin.model.activities.pvminstances;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class DonatorStairs {

    private static void climbMiddle(Player player, GameObject object) {

        player.getMovement().teleport(3810, 2845, 1);
    }

    static {
        ObjectAction.register(32543, "Climb-up", DonatorStairs::climbMiddle);
    }

}
