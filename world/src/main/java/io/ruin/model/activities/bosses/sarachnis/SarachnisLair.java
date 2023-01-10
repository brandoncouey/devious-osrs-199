package io.ruin.model.activities.bosses.sarachnis;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

public class SarachnisLair {

    private static void goThroughPassage(Player player) {
        /**
         * Sarachnis entrance
         */
        int x = player.getAbsX();
        player.getMovement().teleport(1842, 9911, 0);
    }

    private static void enterCave(Player player) {
        if (player.getCombat().isDefending(5)) {
            player.sendMessage("You cannot enter the cave whilst in combat.");
            return;
        }

        player.getMovement().teleport(1842, 9911, 0);
    }


    static {
        ObjectAction.register(34858, "Enter-crypt", (player, obj) -> goThroughPassage(player));
        ObjectAction.register(34858, "Quick-enter", (player, obj) -> enterCave(player));

    }
}
