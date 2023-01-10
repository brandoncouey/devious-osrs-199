package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;


public class CleaningStation {

    static {
        ObjectAction.register(31428, 1, (player, obj) -> {
            if (player.getInventory().hasItem(960, 5)) {
                player.getInventory().hasItem(960, 5);
                player.animate(3676);
                Config.CLEANING_TABLE.set(player, 1);
                player.getStats().addXp(StatType.Construction, 5, false);
            }
        });
    }
}