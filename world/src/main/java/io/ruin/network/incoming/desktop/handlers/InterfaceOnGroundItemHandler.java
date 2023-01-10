package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

public class InterfaceOnGroundItemHandler {

    @IdHolder(ids = {20})//@IdHolder(ids = {39}) //todo@@
    public static final class FromItem implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int y = in.readLEShort();
            int interfaceHash = in.readInt1();
            int x = in.readLEShortA();
            int slot = in.readLEShortA();
            int groundItemId = in.readLEShort();
            int itemId = in.readShort();
            int ctrlRun = in.readByteC();
            handleAction(player, interfaceHash, slot, itemId, groundItemId, x, y, ctrlRun);
        }
    }

    @IdHolder(ids = {3})//@IdHolder(ids = {0}) //todo@@
    public static final class FromInterface implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int slot = in.readLEShortA();
            int interfaceHash = in.readInt();
            int groundItemId = in.readLEShort();
            int y = in.readShort();
            int x = in.readLEShort();
            int ctrlRun = in.readByteS();
            int itemId = in.readLEShort();
            handleAction(player, interfaceHash, slot, itemId, groundItemId, x, y, ctrlRun);
        }
    }

    private static void handleAction(Player player, int interfaceHash, int slot, int itemId, int groundItemId, int x, int y, int ctrlRun) {
        if (player.isLocked())
            return;
        player.resetActions(true, true, true);
        int z = player.getHeight();
        Tile tile = Tile.get(x, y, z, false);
        if (tile == null)
            return;
        GroundItem groundItem = tile.getPickupItem(groundItemId, player.getUserId());
        if (groundItem == null)
            return;
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeGroundItem(groundItem, distance -> action(player, interfaceHash, slot, itemId, groundItem, distance));
    }

    private static void action(Player player, int interfaceHash, int slot, int itemId, GroundItem groundItem, int distance) {
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceHash);
        if (action == null)
            return;
        if (slot == 65535)
            slot = -1;
        if (itemId == 65535)
            itemId = -1;
        action.handleOnGroundItem(player, slot, itemId, groundItem, distance);
    }

}