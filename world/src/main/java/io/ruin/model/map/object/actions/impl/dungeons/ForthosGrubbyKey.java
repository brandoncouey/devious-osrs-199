package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class ForthosGrubbyKey {

    public static void handlePositionDOOR(Player player) {
        if (player.getPosition().getX() == 1798 && player.getPosition().getY() == 9925) {
            TeleportInterface.teleport(player, new Position(1797, 9925, 0));
        } else {
            if (player.getPosition().getX() == 1797 && player.getPosition().getY() == 9925) {
                TeleportInterface.teleport(player, new Position(1798, 9925, 0));
            }
        }
    }

    static {
        ObjectAction.register(34840, "pick-lock", (player, obj) -> handlePositionDOOR(player));
        ObjectAction.register(34840, "pick-lock", (player, obj) -> handlePositionDOOR(player));


    }
}