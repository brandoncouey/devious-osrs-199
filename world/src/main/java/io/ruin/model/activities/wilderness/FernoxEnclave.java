package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;

public class FernoxEnclave {

    public static void teleport(Player player, int x, int y, int z) {
        player.resetActions(true, true, true);
        player.lock();
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(x, y, z);
            player.getPacketSender().clearFade();
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    private static void teleportPlayer(Player player, int x, int y) {
        if (player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            event.delay(1);
            player.unlock();
        });
    }

    private static void heal(Player player) {
        player.animate(833);
        player.graphics(1039);
        player.getStats().restore(false);
        player.getMovement().restoreEnergy(100);
        player.curePoison(1);
        player.cureVenom(1);
        if (player.isStaff()) {
            player.getCombat().restore();
            player.sendMessage("Your stats and special attack have been restored!");
        } else {
            player.sendMessage("Your stats have been restored!");
        }

    }

    static {
        /**
         * Fernox enclave pool
         */

        ObjectAction.register(39651, "Drink", ((player, obj) -> heal(player)));

        /**
         * Fernox enclave entrance barriers
         */

        final int[] BARRIER = {39653, 39652};
        for (int barriers : BARRIER) {
            ObjectAction.register(barriers, "pass-through", (player, obj) -> {
                boolean atObjX = obj.x == player.getAbsX();
                boolean atObjY = obj.y == player.getAbsY();
                if (obj.direction == 0 && atObjX)
                    teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
                else if (obj.direction == 1 && atObjY)
                    teleportPlayer(player, obj.x, obj.y + 1);
                else if (obj.direction == 2 && atObjX)
                    teleportPlayer(player, obj.x + 1, obj.y);
                else if (obj.direction == 3 && atObjY)
                    teleportPlayer(player, obj.x, obj.y - 1);
                else
                    teleportPlayer(player, obj.x, obj.y);
            });
        }

        /**
         * LMS statistics board
         */

        ObjectAction.register(29064, "view", (player, obj) -> {

            player.getPacketSender().sendString(395, 3, "Wins: " + player.lmsWins);
            player.getPacketSender().sendString(395, 4, "Kills: " + player.lmsKills);
            player.getPacketSender().sendString(395, 5, "Games played: " + player.lmsGamesPlayed);
            player.openInterface(InterfaceType.CHATBOX, 395);
        });

        /**
         * Cave entrance
         */
        ObjectAction.register(39647, "walk-down", (player, obj) -> {
            if (!obj.getPosition().equals(3204, 4018)) {
                teleport(player, 3164, 10043, 0);
            }
        });
        ObjectAction.register(39648, "exit", (player, obj) -> {
            teleport(player, 3152, 3643, 0);
        });
        /**
         * Non-supported objects
         */
        ObjectAction.register(30386, "enter", (player, obj) -> {
            player.sendMessage("This feature is not yet supported");
        });
        ObjectAction.register(26642, "enter", (player, obj) -> {
            player.sendMessage("This feature is not yet supported");
        });
        ObjectAction.register(39637, "enter", (player, obj) -> {
            player.sendMessage("This feature is not yet supported");
        });
        ObjectAction.register(29087, "use", (player, obj) -> {
            player.sendMessage("This feature is not yet supported");
        });
        ObjectAction.register(39638, "chop-down", (player, obj) -> {
            player.sendMessage("This feature is not yet supported");
        });
    }
}
