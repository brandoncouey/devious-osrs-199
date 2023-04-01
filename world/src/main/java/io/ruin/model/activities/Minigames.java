package io.ruin.model.activities;

import io.ruin.model.entity.player.Player;

public class Minigames {

    public static void checkLogin(Player p) {
        if (p.inferno != null) {
            p.inferno.player = p;
            p.inferno.start(true);
        }
        if (p.fightCaves != null) {
            p.fightCaves.player = p;
            p.fightCaves.start(true);
        }
    }
}
