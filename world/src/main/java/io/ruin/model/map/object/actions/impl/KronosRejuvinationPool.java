package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.SkipLoad;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

@SkipLoad
public class KronosRejuvinationPool {

    static {
        ObjectAction.register(29241, 1, ((player, obj) -> heal(player)));
    }

    private static void heal(Player player) {
        player.animate(833);
        player.graphics(1039);
        player.getStats().restore(true);
        player.getMovement().restoreEnergy(100);
        player.curePoison(1);
        player.cureVenom(1);
        player.getCombat().restore();
    }

}
