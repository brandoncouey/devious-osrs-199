package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

public class MilkingCow {

    static {
        ObjectAction.register(8689, 1, (player, obj) -> milkCow(player));
    }

    private static void milkCow(Player player) {
        if (!player.getInventory().contains(1925)) {
            player.sendMessage("You need a bucket to do this!");
        } else {
            player.startEvent(event -> {
                player.sendFilteredMessage("You milk the cow and fill your bucket.");
                player.lock();
                player.animate(832);
                event.delay(1);
                player.getInventory().remove(1925, 1);
                player.getInventory().add(1927, 1);
                player.unlock();
            });
        }
    }

}