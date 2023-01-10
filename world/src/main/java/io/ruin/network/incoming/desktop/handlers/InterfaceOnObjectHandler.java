package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

public class InterfaceOnObjectHandler {

    @IdHolder(ids = {43})//@IdHolder(ids = {46})
    public static final class FromItem implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readInt();
            int itemSlot = in.readShort();
            int objectX = in.readLEShortA();
            int objectY = in.readShort();
            int objectId = in.readShort();
            int ctrlRun = in.readByteS();
            int itemId = in.readShort();
            handleAction(player, interfaceHash, itemSlot, itemId, objectId, objectX, objectY, ctrlRun);
        }
    }

    @IdHolder(ids = {102})//@IdHolder(ids = {68})
    public static final class FromInterface implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int objectId = in.readShortA();
            int objectY = in.readShort();
            int objectX = in.readLEShort();
            int slot = in.readLEShort();
            int ctrlRun = in.readByte();
            int itemId = in.readLEShort();
            int interfaceHash = in.readInt();
            handleAction(player, interfaceHash, slot, itemId, objectId, objectX, objectY, ctrlRun);
        }
    }

    private static void handleAction(Player player, int interfaceHash, int slot, int itemId, int objectId, int objectX, int objectY, int ctrlRun) {
        if (player.isLocked())
            return;
        player.resetActions(true, true, true);
        if (objectId == -1)
            return;
        GameObject gameObject = Tile.getObject(objectId, objectX, objectY, player.getPosition().getZ());
        if (gameObject == null)
            return;
        if (player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("interfaceHash", interfaceHash)
                    .add("slot", slot)
                    .add("itemId", itemId)
                    .add("objectId", objectId)
                    .add("objectX", objectX)
                    .add("objectY", objectY);
            player.sendFilteredMessage("[ObjectAction] " + debug.toString());
        }
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeObject(gameObject, () -> action(player, interfaceHash, slot, itemId, gameObject));
    }

    private static void action(Player player, int interfaceHash, int slot, int itemId, GameObject gameObject) {
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceHash);
        if (action == null)
            return;
        if (slot == 65535)
            slot = -1;
        if (itemId == 65535)
            itemId = -1;
        action.handleOnObject(player, slot, itemId, gameObject);
    }

}