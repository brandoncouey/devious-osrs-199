package io.ruin.model.map.object.actions.impl.prifddinas;

import io.ruin.model.map.object.actions.ObjectAction;

public class TeleportPlatforms {

    static {
        ObjectAction.register(36490, 1, (player, obj) -> {
            if (player.getHeight() == 0) {
                player.lock();
                //        player.getPacketSender().priffadeOut();
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), 2);
                //        player.getPacketSender().priffadeIn();
                player.unlock();
            } else if (player.getHeight() == 2) {
                player.lock();
                //        player.getPacketSender().priffadeOut();
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), 0);
                //        player.getPacketSender().priffadeIn();
                player.unlock();
            }
        });
    }

}
