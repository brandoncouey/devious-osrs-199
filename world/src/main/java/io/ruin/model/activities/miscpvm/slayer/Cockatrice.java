package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

public class Cockatrice extends NPCCombat {

    private static final StatType[] DRAIN = {StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1))
            basicAttack();
        if (withinDistance(2))
            if (target.player != null && !shieldcheck(target.player)) {
                for (StatType statType : DRAIN) {
                    target.player.getStats().get(statType).drain(8);
                }
                target.player.sendMessage("<col=ff0000>The cockatrice's piercing gaze drains your stats!");
                target.player.sendMessage("<col=ff0000>A mirror shield can protect you from this attack.");
            }
        return true;
    }

    private boolean shieldcheck(Player player) {
        if (player.getEquipment().get(Equipment.SLOT_SHIELD).getId() == 4156)
            return true;
        else return player.getEquipment().get(Equipment.SLOT_SHIELD).getId() == 24266;
    }
}
