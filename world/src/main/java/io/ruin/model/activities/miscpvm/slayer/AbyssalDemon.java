package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.entity.npc.NPCCombat;

public class AbyssalDemon extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        else
            basicAttack();
        return true;
    }
}
