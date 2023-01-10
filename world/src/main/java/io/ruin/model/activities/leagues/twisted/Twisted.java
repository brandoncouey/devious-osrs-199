package io.ruin.model.activities.leagues.twisted;

import io.ruin.model.World;
import io.ruin.model.activities.leagues.LeagueType;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceHandler;

public class Twisted {

    public static int LEAGUE_FIRST = 676;

    public static int LEAGUES_REWARD_SHOP = 658;

    static {

        InterfaceHandler.register(LEAGUE_FIRST, h -> {

        });

        InterfaceHandler.register(LEAGUES_REWARD_SHOP, h -> {

        });

        LoginListener.register(player -> {
            player.startEvent(e -> {
                if (World.leagueType == LeagueType.TWISTED_LEAGUE) {
                    player.sendMessage("<col=8B0000>Welcome to Devious Twisted League!");
                }
            });
        });

    }
}
