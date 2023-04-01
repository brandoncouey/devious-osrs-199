package io.ruin.model.object.owned;

import io.ruin.model.map.object.actions.ObjectAction;

public class ObjectImpl {


    static {
        ObjectAction.register(26281, 1, (player, obj) -> {
            if (!player.lootedAdfs) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("You do not have enough inventory space.");
                    return;
                }
                player.lootedAdfs = true;
                player.getInventory().add(1540);
                player.sendMessage("You pick up an anti-dragon fire shield.");
            } else {
                player.sendMessage("You have already taken a shield.");
            }
        });

    }
}
