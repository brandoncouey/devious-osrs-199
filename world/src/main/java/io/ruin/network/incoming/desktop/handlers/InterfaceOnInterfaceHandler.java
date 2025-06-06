package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

public class InterfaceOnInterfaceHandler {

    @IdHolder(ids = {12})//@IdHolder(ids = {85})
    public static final class OnItem implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int toSlot = in.readLEShort();
            int toInterfaceHash = in.readLEInt();
            int fromItemId = in.readLEShort();
            int fromInterfaceHash = in.readInt();
            int fromSlot = in.readShortA();
            int toItemId = in.readLEShortA();
            handleAction(player, fromInterfaceHash, fromSlot, fromItemId, toInterfaceHash, toSlot, toItemId);
        }
    }

    @IdHolder(ids = {75})//@IdHolder(ids = {62})
    public static final class OnInterface implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int fromInterfaceHash = in.readIntME();
            int fromSlot = in.readShort();
            int toInterfaceHash = in.readInt();
            int toItemId = in.readLEShortA();
            int toSlot = in.readLEShort();
            handleAction(player, fromInterfaceHash, fromSlot, -1, toInterfaceHash, toSlot, toItemId);
        }
    }

    private static void handleAction(Player player, int fromInterfaceHash, int fromSlot, int fromItemId, int toInterfaceHash, int toSlot, int toItemId) {
        if (player.isLocked())
            return;
        player.resetActions(true, false, true);

        int fromInterfaceId = fromInterfaceHash >> 16;
        int fromChildId = fromInterfaceHash & 0xffff;
        if (fromChildId == 65535)
            fromChildId = -1;
        if (fromSlot == 65535)
            fromSlot = -1;
        if (fromItemId == 65535)
            fromItemId = -1;

        int toInterfaceId = toInterfaceHash >> 16;
        int toChildId = toInterfaceHash & 0xffff;
        if (toChildId == 65535)
            toChildId = -1;
        if (toSlot == 65535)
            toSlot = -1;
        if (toItemId == 65535)
            toItemId = -1;

        if (player.debug) {
            player.sendFilteredMessage("[DragItem]");
            DebugMessage debug = new DebugMessage()
                    .add("inter", fromInterfaceId)
                    .add("child", fromChildId)
                    .add("slot", fromSlot)
                    .add("item", fromItemId);
            player.sendFilteredMessage("    From: " + debug.toString());
            debug = new DebugMessage()
                    .add("inter", toInterfaceId)
                    .add("child", toChildId)
                    .add("slot", toSlot)
                    .add("item", toItemId);
            player.sendFilteredMessage("    To: " + debug.toString());
        }

        InterfaceAction action = InterfaceHandler.getAction(player, fromInterfaceId, fromChildId);
        if (action != null)
            action.handleOnInterface(player, fromSlot, fromItemId, toInterfaceId, toChildId, toSlot, toItemId);
    }

}