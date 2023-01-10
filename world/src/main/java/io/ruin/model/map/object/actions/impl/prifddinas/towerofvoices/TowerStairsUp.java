package io.ruin.model.map.object.actions.impl.prifddinas.towerofvoices;

import io.ruin.model.map.object.actions.ObjectAction;

public class TowerStairsUp {

    static {
        ObjectAction.register(36387, 1, (player, obj) -> {
            player.lock();
            //        player.getPacketSender().priffadeOut();
            player.getMovement().teleport(3268, 6082, 2);
            //        player.getPacketSender().priffadeIn();
            player.unlock();
        });

    }

}
