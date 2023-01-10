package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class NardahAmulet {

    private static boolean nardah(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3872);
            player.graphics(284, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(3427, 2927, 0);
        });
    }

    private static boolean kalphitecave(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3872);
            player.graphics(284, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(3323, 3122, 0);
        });
    }

    static {
        ItemAction.registerInventory(13136, "nardah", NardahAmulet::nardah);
        ItemAction.registerEquipment(13136, "nardah", NardahAmulet::nardah);

        ItemAction.registerInventory(13136, "kalphite cave", NardahAmulet::kalphitecave);
        ItemAction.registerEquipment(13136, "kalphite cave", NardahAmulet::kalphitecave);
    }

}