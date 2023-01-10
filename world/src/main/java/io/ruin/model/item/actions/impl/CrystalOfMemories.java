package io.ruin.model.item.actions.impl;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class CrystalOfMemories {

    private static void teleportBack(Player player, Item item) {
        if (player.lastTeleport != null) {
            player.sendMessage(Color.BABY_BLUE.wrap("You used your stored teleport"));
            player.getMovement().startTeleport(event -> {
                player.animate(3864);
                player.graphics(1039);
                player.privateSound(200, 0, 10);
                event.delay(2);
                player.getMovement().teleport(player.lastTeleport);
                player.lastTeleport = null;
            });
        } else {
            player.sendMessage(Color.BABY_BLUE.wrap("You dont have any teleports stored"));

        }
    }

    private static void checkMemories(Player player, Item item) {
        if (player.lastTeleport != null) {
            player.sendMessage(Color.BABY_BLUE.wrap("You have a teleports stored"));
        } else {
            player.sendMessage(Color.BABY_BLUE.wrap("You dont have any teleports stored"));

        }
    }

    static {
        ItemAction.registerInventory(25104, "Teleport-back", CrystalOfMemories::teleportBack);
        ItemAction.registerInventory(25104, "Check-memories", CrystalOfMemories::checkMemories);
    }
}
