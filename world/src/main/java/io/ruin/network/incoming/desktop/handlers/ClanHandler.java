package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {87, 74})//@IdHolder(ids = {53, 22})
public class ClanHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String username = in.readString();
        switch (opcode) {
            case 87:
                /*
                 * Join / Leave
                 */
                CentralClient.sendClanRequest(player.getUserId(), username);
                return;
            case 74:
                /*
                 * Kick
                 */
                CentralClient.sendClanKick(player.getUserId(), username);
                return;
        }
    }

}