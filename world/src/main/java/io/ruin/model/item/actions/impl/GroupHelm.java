package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class GroupHelm {

    private static boolean teleport(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(3105, 3030, 0);
        });
    }

    static {
        ItemAction.registerInventory(26156, "teleport", GroupHelm::teleport);
        ItemAction.registerEquipment(26170, "teleport", GroupHelm::teleport);
    }

}