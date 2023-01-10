package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;


public class FossilIslandDungeon {

    public static void handlePosition31485(Player player) {
        if (player.getPosition().getX() == 3607 && player.getPosition().getY() == 10291) {
            TeleportInterface.teleport(player, new Position(3603, 10291, 0));
        } else {
            if (player.getPosition().getX() == 3603 && player.getPosition().getY() == 10291) {
                TeleportInterface.teleport(player, new Position(3607, 10291, 0));
            }
        }
    }

    static {
        /**
         * Task only cave
         */
        ObjectAction.register(30842, 1, (player, obj) -> player.getMovement().teleport(3596, 10291, 0));
        ObjectAction.register(30844, 1, (player, obj) -> player.getMovement().teleport(3680, 3854, 0));
        ObjectAction.register(31485, 1, (player, obj) -> handlePosition31485(player));
        ObjectAction.register(30847, 1, (player, obj) -> player.getMovement().teleport(3633, 10261, 0));
        ObjectAction.register(30849, 1, (player, obj) -> player.getMovement().teleport(3633, 10264, 0));


        /**
         * Off task cave
         */
        ObjectAction.register(30878, 3603, 10232, 0, "exit", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3746, 3779, 0);
            player.sendFilteredMessage("You exit the cave.");
            player.unlock();
        }));
        ObjectAction.register(30869, 3745, 3777, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3604, 10231, 0);
            player.sendFilteredMessage("You enter the cave.");
            player.unlock();
        }));
    }
}
