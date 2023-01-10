package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class ExplorersRing {

    private static boolean teleport(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3869);
            player.graphics(285, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(3053, 3290, 0);
        });
    }

    static {
        ItemAction.registerInventory(13128, "teleport", ExplorersRing::teleport);
        ItemAction.registerEquipment(13128, "teleport", ExplorersRing::teleport);
    }

}
