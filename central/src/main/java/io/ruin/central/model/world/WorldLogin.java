package io.ruin.central.model.world;

import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.protocol.login.LoginRequest;
import io.ruin.api.utils.IPBans;
import io.ruin.api.utils.MACBan;
import io.ruin.api.utils.UUIDBan;
import io.ruin.central.CentralServer;
import io.ruin.central.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
public class WorldLogin extends LoginRequest {

    private World world;

    public static String capitalize(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("_", " ");
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i+1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i+2));
                }
            }
        }
        return s;
    }

    public WorldLogin(World world, LoginInfo info) {
        super(info);
        this.world = world;

        if (UUIDBan.isUUIDBanned(info.uuid) || IPBans.isIPBanned(info.ipAddress) || MACBan.isMACBanned(info.macAddress)) {
            this.deny(Response.DISABLED_ACCOUNT);
            return;
        }
        try {
            String username = capitalize(info.name);
            int index = -1;
            for(int i = 1; i < 2048; i++) {
                Player player = CentralServer.getPlayer(i);
                if(player == null) {
                    index = i;
                    break;
                }
            }
            if(index != -1) {
                String saved = Player.load(username, world);
                info.update(index, username, saved, Collections.singletonList(5), 0);
                this.success();
            } else {
                this.deny(Response.WORLD_FULL);
            }
        } catch (Exception e) {
            CentralServer.logError(e.getMessage());
            this.deny(Response.ERROR_LOADING_ACCOUNT);
        }
        //  }
        //  );
    }

    @Override
    public void success() {
        this.world.logins.offer(this.info);
    }

    @Override
    public void deny(Response response) {
        this.world.sendLoginFailed(this.info.name, response);
        super.deny(response);
    }
}