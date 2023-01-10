package io.ruin.model.item.actions.impl.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class RadasBlessing {

    private static boolean mountkaruulm(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(1311, 3795, 0);
        });
    }

    private static boolean kourendwoodland(Player player, Item item) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(1552, 3454, 0);
        });
    }

    static {
        ItemAction.registerInventory(22947, "Mount karuulm", RadasBlessing::mountkaruulm);
        ItemAction.registerEquipment(22947, "Mount karuulm", RadasBlessing::mountkaruulm);

        ItemAction.registerInventory(22947, "Kourend Woodland", RadasBlessing::kourendwoodland);
        ItemAction.registerEquipment(22947, "Kourend Woodland", RadasBlessing::kourendwoodland);
    }

}