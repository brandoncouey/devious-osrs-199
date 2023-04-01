package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.protocol.Protocol;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;
import io.ruin.utility.IdHolder;
import io.ruin.utility.PlayerLog;

@IdHolder(ids = {5, 93, 55, 27, 91, 79})//@IdHolder(ids = {84, 80, 48, 56, 93, 67})
public class FriendsHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String name;
        if (opcode == 79) {
            /**
             * Rank friend
             */
            name = in.readString();
            int rank = in.readByteC();
            CentralClient.sendClanRank(player.getUserId(), name, rank);
            return;
        }
        name = in.readString();
        if (opcode == 93) {
            /**
             * Add friend
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 1);
            return;
        }
        if (opcode == 55) {
            /**
             * Delete friend
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 2);
            return;
        }
        if (opcode == 5) {
            /**
             * Add ignore
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 3);
            return;
        }
        if (opcode == 27) {
            /**
             * Delete ignore
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 4);
            return;
        }
        if (opcode == 91) {
            /**
             * Private message
             */
            String message = Huffman.decrypt(in, 100);
            if (Punishment.isMuted(player)) {
                if (player.shadowMute)
                    player.getPacketSender().write(Protocol.outgoingPm(name, message));
                else
                    player.sendMessage("You're muted and can't talk.");
                return;
            }
            CentralClient.sendPrivateMessage(player.getUserId(), player.getClientGroupId(), name, message);
            PlayerLog.log(PlayerLog.Type.PRIVATE_MESSAGE, player.getName(), "IP=" + player.getIp() + ", To=" + name + ", Message=" + message);
        }
    }

}