package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class WesternBanner {

    private static boolean teleport(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(2336, 3686, 0);
        });
    }

    static {
        ItemAction.registerInventory(13144, "teleport", WesternBanner::teleport);
        ItemAction.registerEquipment(13144, "teleport", WesternBanner::teleport);
    }

}