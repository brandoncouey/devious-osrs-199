package io.ruin.model.activities.pvminstances;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class DonatorSteppingStones {

    private static void jumpStone(Player player, GameObject object) {

        int dirX = 0;
        int dirY = 0;
        int dx = object.x - player.getAbsX();
        int dy = object.y - player.getAbsY();

        if (dx == -1 && dy == 0) {
            dirX = -2;
        } else if (dx == 1 && dy == 0) {
            dirX = 2;
        } else if (dx == 0 && dy == 1) {
            dirY = 2;
        } else if (dx == 0 && dy == -1) {
            dirY = -2;
        } else {
            return;
        }

        player.getMovement().teleport(player.getAbsX() + dirX, player.getAbsY() + dirY, 0);
    }

    public static void openStore(Player player) {
        player.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "/store");
    }

    static {
        ObjectAction.register(4411, "Jump-to", DonatorSteppingStones::jumpStone);
    }

}
