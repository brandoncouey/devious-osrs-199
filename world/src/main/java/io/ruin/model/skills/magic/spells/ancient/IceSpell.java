package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class IceSpell extends TargetSpell {
    @Override
    protected void afterHit(Hit hit, Entity target) {
        if (hit.attacker.player != null
                && hit.attacker.player.getEquipment().hasId(27513))
            hit.boostDamage(0.8);
    }
}
