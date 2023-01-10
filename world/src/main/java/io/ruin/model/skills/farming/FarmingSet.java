package io.ruin.model.skills.farming;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

public class FarmingSet {

    public static double FarmingSet(Player player) {
        double bonus = 1.0;
        Item hood = player.getEquipment().get(Equipment.SLOT_HAT);
        Item garb = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item robe = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hood != null && hood.getId() == 13646)
            bonus += 0.9;
        if (garb != null && garb.getId() == 13642)
            bonus += 0.9;
        if (robe != null && robe.getId() == 13640)
            bonus += 0.9;
        if (boots != null && boots.getId() == 13644)
            bonus += 0.9;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.6;

        return bonus;
    }
}
