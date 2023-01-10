package io.ruin.model.activities.miscpvm;

import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;

public class PassiveCombat extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener()
                .postDefend(h -> {
                    if (h.isBlocked())
                        h.type = HitType.DAMAGE;
                    if (h.type == HitType.DAMAGE)

                        h.damage = h.maxDamage;
                    if (h.attackStyle.isCannon())
                        h.damage = 0;
                });
    }

    @Override
    public void follow() {
    }

    @Override
    public boolean attack() {
        return false;
    }

    @Override
    public boolean allowRetaliate(Entity attacker) {
        return false;
    }

}