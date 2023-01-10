package io.ruin.model.activities.gauntlet;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;

/**
 * @author Greco
 * @since 04/08/2021
 */
public class GauntletPrepRoom {
    public static final Position ROOM_ENTRY = new Position(3032, 6118, 1);

    public static void enterRoom(Player player) {
        player.getMovement().teleport(3032, 6118, 1);
        sendChest(player);
    }

    public static void sendChest(Player player) {
        //   player.getPacketSender().sendVarp(2322, player.isGaunletLootAvailable() ? 5 : 0);
    }

}