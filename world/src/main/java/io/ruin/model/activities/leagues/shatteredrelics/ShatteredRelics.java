package io.ruin.model.activities.leagues.shatteredrelics;

import io.ruin.model.World;
import io.ruin.model.activities.leagues.LeagueType;
import io.ruin.model.entity.shared.listeners.LoginListener;

public class ShatteredRelics {

    static {

        LoginListener.register(player -> {
            player.startEvent(e -> {
                if (World.leagueType == LeagueType.SHATTERED_RELICS) {
                    player.sendMessage("<col=8B0000>Welcome to Devious Shattered Relics League!");
                }
            });
        });

    }
}
