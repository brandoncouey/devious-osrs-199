package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.utils.IPMute;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.actions.impl.pets.Pets;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;
import io.ruin.utility.IdHolder;
import io.ruin.utility.PlayerLog;

@IdHolder(ids = {14})
public class ChatHandler implements Incoming {


    static {
        InterfaceHandler.register(162, h -> {
            h.actions[4] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //All
                player.currentTab = 0;
            };
            h.actions[7] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Game
                player.currentTab = 1;
            };
            h.actions[11] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Public
                player.currentTab = 2;
            };
            h.actions[15] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Private
                player.currentTab = 3;
            };
            h.actions[19] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Channel
                player.currentTab = 4;
            };
            h.actions[23] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Clan
                player.currentTab = 5;
            };
            h.actions[27] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Trade
                player.currentTab = 6;
            };
        });
    }

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        boolean shadow = false;

        if (IPMute.isIPMuted(player.getIp()) || Punishment.isMuted(player)) {
            if (!player.shadowMute) {
                player.sendMessage("You're muted and can't talk.");
                return;
            }
            shadow = true;
        }

        int type = in.readByte();
        int effects = in.readShort();
        String message = Huffman.decrypt(in, 100);

        if (message.isEmpty()) {
            /* how does this even happen? */
            return;
        }

        if (type == 2 || player.currentTab == 4) {
            if (message.startsWith("/"))
                message = message.substring(1);
            CentralClient.sendClanMessage(player.getUserId(), player.getClientGroupId(), message);
            PlayerLog.log(PlayerLog.Type.FRIENDS_CHAT, player.getName(), "IP=" + player.getIp() + ", Message=" + message);
            return;
        }

        if (message.length() >= 3) {
            char c1 = message.charAt(0);
            char c2 = message.charAt(1);

            //Clan
            if (message.startsWith("//") || player.currentTab == 5) {
                if (player.getClanManager() == null) {
                    player.sendMessage("You are currently not in a clan.");
                    return;
                }
                if (message.startsWith("//"))
                    player.getClanManager().sendClanMessage(player, message.substring(2));
                else
                    player.getClanManager().sendClanMessage(player, message);
                PlayerLog.log(PlayerLog.Type.CLAN_CHAT, player.getName(), "IP=" + player.getIp() + ", Message=" + message);
                return;
            }

            if (message.startsWith("/")) {
                CentralClient.sendClanMessage(player.getUserId(), player.getClientGroupId(), message.substring(1));
                PlayerLog.log(PlayerLog.Type.FRIENDS_CHAT, player.getName(), "IP=" + player.getIp() + ", Message=" + message);
                return;
            }
            if ((c1 == ':' && c2 == ':') || (c1 == ';' && c2 == ';')) {
                CommandHandler.handle(player, message.substring(2));
                return;
            }
            if (c1 == '!' && player.isAdmin()) {
                player.forceText(message.substring(1));
                return;
            }
        }

        player.getChatUpdate().set(shadow, effects, player.getClientGroupId(), type, message);
        PlayerLog.log(PlayerLog.Type.PUBLIC_CHAT, player.getName(), "IP=" + player.getIp() + ", Type=" + type + ", Effects=" + effects + ", Message=" + message);
    }
}