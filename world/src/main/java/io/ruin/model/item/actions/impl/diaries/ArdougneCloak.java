package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class ArdougneCloak {

    private static boolean farm(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3872);
            player.graphics(1238, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(2673, 3375, 0);
        });
    }

    private static boolean monastery(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3872);
            player.graphics(1237, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(2606, 3222, 0);
        });
    }

    static {
        ItemAction.registerInventory(13124, "farm teleport", ArdougneCloak::farm);
        ItemAction.registerEquipment(13124, "ardougne farm", ArdougneCloak::farm);

        ItemAction.registerInventory(13124, "monastery teleport", ArdougneCloak::monastery);
        ItemAction.registerEquipment(13124, "kandarin monastery", ArdougneCloak::monastery);
    }

}