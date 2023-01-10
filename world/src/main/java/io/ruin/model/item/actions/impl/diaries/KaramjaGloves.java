package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class KaramjaGloves {

    private static boolean gemmine(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(865);
            e.delay(2);
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(2825, 2998, 0);
        });
    }

    private static boolean duradel(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(865);
            e.delay(2);
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(2869, 2980, 1);
        });
    }

    static {
        ItemAction.registerInventory(13103, "gem mine", KaramjaGloves::gemmine);
        ItemAction.registerEquipment(13103, "gem mine", KaramjaGloves::gemmine);

        ItemAction.registerInventory(13103, "duradel", KaramjaGloves::duradel);
        ItemAction.registerEquipment(13103, "duradel", KaramjaGloves::duradel);
    }

}