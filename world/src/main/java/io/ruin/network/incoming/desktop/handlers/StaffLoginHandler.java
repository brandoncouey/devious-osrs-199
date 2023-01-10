package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.utility.Broadcast;

public class StaffLoginHandler {

    static {
        LoginListener.register(player -> {
            if (player.isStaff()) {
                Broadcast.WORLD.sendMessage(Color.RED.wrap("<img=" + player.getPrimaryGroup().clientImgId + "> " + StringUtils.fixCaps(player.getPrimaryGroup().name().replace("_", " ").toLowerCase()  + " " + StringUtils.fixCaps(player.getName() + " has logged in."))));
            }
        });
    }

}