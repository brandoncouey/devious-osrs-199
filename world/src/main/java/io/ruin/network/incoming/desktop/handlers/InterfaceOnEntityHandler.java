package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

public class InterfaceOnEntityHandler {

    @IdHolder(ids = {53})//@IdHolder(ids = {59})
    public static final class ItemOnPlayer implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int targetIndex = in.readLEShort();
            int slot = in.readShort();
            int itemId = in.readShortA();
            int interfaceHash = in.readIntME();
            int ctrlRun = in.readByteC();
            handleAction(player, World.getPlayer(targetIndex), interfaceHash, slot, itemId, ctrlRun);
        }

    }

    @IdHolder(ids = {105})//@IdHolder(ids = {55})
    public static final class InterfaceOnPlayer implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int itemId = in.readShort();
            int ctrlRun = in.readByte();
            int interfaceHash = in.readInt();
            int slot = in.readLEShortA();
            int targetIndex = in.readShort();
            handleAction(player, World.getPlayer(targetIndex), interfaceHash, slot, itemId/*-1*/, ctrlRun);
        }

    }

    @IdHolder(ids = {40})//@IdHolder(ids = {11})
    public static final class ItemOnNpc implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int targetIndex = in.readShort();
            int ctrlRun = in.readByteA();
            int itemId = in.readLEShortA();
            int interfaceHash = in.readInt1();
            int slot = in.readLEShortA();
            handleAction(player, World.getNpc(targetIndex), interfaceHash, slot, itemId, ctrlRun);
        }

    }

    @IdHolder(ids = {103})
    public static final class InterfaceOnNpc implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readIntME();
            int targetIndex = in.readShortA();
            int ctrlRun = in.readShortA();
            int slot = in.readShort();
            handleAction(player, World.getNpc(targetIndex), interfaceHash, slot, -1, ctrlRun);
        }

    }

    protected static void handleAction(Player player, Entity target, int interfaceHash, int slot, int itemId, int ctrlRun) {
        if (target == null || player.isLocked())
            return;
        player.resetActions(true, true, true);
        player.face(target);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        if (itemId == -1)
            action(player, target, interfaceHash, slot, itemId);
        else
            TargetRoute.set(player, target, () -> action(player, target, interfaceHash, slot, itemId));
    }

    private static void action(Player player, Entity target, int interfaceHash, int slot, int itemId) {
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceHash);
        if (action == null)
            return;
        if (slot == 65535)
            slot = -1;
        if (itemId == 65535)
            itemId = -1;
        action.handleOnEntity(player, target, slot, itemId);
    }

}