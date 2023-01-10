package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class KandarinHelm {

    private static boolean teleport(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(2730, 3412, 0);
        });
    }

    static {
        ItemAction.registerInventory(13140, "teleport", KandarinHelm::teleport);
        ItemAction.registerEquipment(13140, "teleport", KandarinHelm::teleport);
    }

}