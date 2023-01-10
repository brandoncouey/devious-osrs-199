package io.ruin.model.activities.bosses.worldboss;

import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;

public class bossSafeDeath {

    private static final Bounds HOME = new Bounds(1726, 1559, 1795, 3632, 0);
    private static final Bounds GLOBAL_BOSS = new Bounds(1759, 3560, 1792, 3585, 0);

    static {
        MapListener.registerBounds(GLOBAL_BOSS).onEnter(player -> {
            player.deathEndListener = (DeathListener.Simple) () -> {
                player.getMovement().teleport(1760, 3572, 0);
                player.sendMessage("Oh dear, you have died!");
            };
        }).onExit((player, logout) -> {
            player.deathEndListener = null;
        });

        MapListener.registerBounds(HOME).onEnter(player -> {
            player.deathEndListener = (DeathListener.Simple) () -> {
                player.getMovement().teleport(3171, 5726, 0);
                player.sendMessage("Oh dear, you have died!");
            };
        }).onExit((player, logout) -> {
            player.deathEndListener = null;
        });
    }

}
