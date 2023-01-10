package io.ruin.model.map.object.actions.impl.prifddinas.towerofvoices;

import io.ruin.model.map.object.actions.ObjectAction;

public class TowerStairsDown {

    static {
        ObjectAction.register(36390, 1, (player, obj) -> {
            player.lock();
            //        player.getPacketSender().priffadeOut();
            player.getMovement().teleport(3263, 6078, 0);
            //        player.getPacketSender().priffadeIn();
            player.unlock();
        });

    }

}
