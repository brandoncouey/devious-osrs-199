package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {67})
public class CloseInterfaceHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        player.closeInterfaces();
        player.getPacketSender().sendClientScript(2158);
    }

}