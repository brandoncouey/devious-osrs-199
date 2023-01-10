package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class MorytaniaLegs {

    private static boolean ectofuntuspit(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3874);
            player.graphics(1236, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(3683, 9888, 0);
        });
    }

    private static boolean burghderott(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3874);
            player.graphics(1236, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(3486, 3248, 0);
        });
    }

    static {
        ItemAction.registerInventory(13115, "ecto teleport", MorytaniaLegs::ectofuntuspit);
        ItemAction.registerEquipment(13115, "ectofuntus pit", MorytaniaLegs::ectofuntuspit);

        ItemAction.registerInventory(13115, "burgh teleport", MorytaniaLegs::burghderott);
        ItemAction.registerEquipment(13115, "burgh de rott", MorytaniaLegs::burghderott);
    }

}