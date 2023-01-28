package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.utility.Broadcast;

public class StaffLoginHandler {

    static {
        LoginListener.register(player -> {
            if (player.isOwner()) {
                Broadcast.WORLD.sendMessage("<shad=000000>" + Color.PURPLE.wrap("<img=" + player.getPrimaryGroup().clientImgId + "> " + StringUtils.fixCaps(player.getPrimaryGroup().name().replace("_", " ").toLowerCase()  + " " + StringUtils.fixCaps(player.getName() + " has logged in."))));
            } else if (player.isAdmin()) {
                Broadcast.WORLD.sendMessage("<shad=000000>" + Color.YELLOW.wrap("<img=" + player.getPrimaryGroup().clientImgId + "> " + StringUtils.fixCaps(player.getPrimaryGroup().name().replace("_", " ").toLowerCase()  + " " + StringUtils.fixCaps(player.getName() + " has logged in."))));
            } else if (player.isModerator()) {
                Broadcast.WORLD.sendMessage("<shad=000000>" + Color.SILVER.wrap("<img=" + player.getPrimaryGroup().clientImgId + "> " + StringUtils.fixCaps(player.getPrimaryGroup().name().replace("_", " ").toLowerCase()  + " " + StringUtils.fixCaps(player.getName() + " has logged in."))));
            } else if (player.isSupport()) {
                Broadcast.WORLD.sendMessage("<shad=000000>" + Color.CYAN.wrap("<img=" + player.getPrimaryGroup().clientImgId + "> " + StringUtils.fixCaps(player.getPrimaryGroup().name().replace("_", " ").toLowerCase()  + " " + StringUtils.fixCaps(player.getName() + " has logged in."))));
            } else if (player.isZenyteDonator()) {
                Broadcast.WORLD.sendMessage("<shad=000000>" + Color.ORANGE.wrap("<img=" + player.getSecondaryGroup().clientImgId + "> " + StringUtils.fixCaps(player.getPrimaryGroup().name().replace("_", " ").toLowerCase()  + " " + StringUtils.fixCaps(player.getName() + " has logged in."))));
            }
        });
    }

}