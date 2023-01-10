package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.spells.TargetSpell;

public abstract class BloodSpell extends TargetSpell {
    @Override
    protected void afterHit(Hit hit, Entity target) {
        if (hit.damage > 0) {
            int healAmount = hit.damage / 4;
            if (hit.attacker.player != null
                    && hit.attacker.player.getEquipment().hasId(25413)
                    && hit.attacker.player.getEquipment().hasId(25407)
                    && hit.attacker.player.getEquipment().hasId(25404)
                    && hit.attacker.player.getEquipment().hasId(25416)
                    && hit.attacker.player.getEquipment().hasId(25410)) {
                healAmount *= 2.0;
            } else if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) {
                healAmount *= 1.5;
            }
            hit.attacker.incrementHp(healAmount);
        }
    }

}
