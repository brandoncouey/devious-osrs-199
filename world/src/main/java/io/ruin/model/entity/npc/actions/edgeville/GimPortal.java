package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.map.object.actions.ObjectAction;

public class GimPortal {

    static {

        ObjectAction.register(42819, "enter", (player, obj) -> {
            if (player.getGameMode() == GameMode.GROUP_IRONMAN || player.getGameMode() == GameMode.HARDCORE_GROUP_IRONMAN) {
                if (obj.getPosition().equals(3093, 3038)) {
                    player.getMovement().teleport(3086, 3485, 0);
                } else {
                    player.getMovement().teleport(3094, 3040, 0);
                }
            } else {
                player.sendMessage(Color.BABY_BLUE, "You're not a group ironman, where are you trying to go?");
            }
        });

    }
}
