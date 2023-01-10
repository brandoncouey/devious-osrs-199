package io.ruin.model.activities.soulwars;

import io.ruin.model.map.object.actions.ObjectAction;

public class SoulWarsObjects {

    static {

        //to soul wars lobby from edgeville
        ObjectAction.register(40474, 1, (player, obj) -> {
            player.getMovement().teleport(2206, 2857, player.getHeight());
        });

        //from soul wars lobby to edgeville
        ObjectAction.register(40476, 1, (player, obj) -> {
            player.getMovement().teleport(3082, 3476, player.getHeight());
        });

    }
}
