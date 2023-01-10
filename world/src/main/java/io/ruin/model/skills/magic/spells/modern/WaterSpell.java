package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.item.actions.impl.chargable.TomeOfWater;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WaterSpell extends TargetSpell {

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        if (hit.attacker != null && hit.attacker.player != null) {
            //Tome of Water Damage Boost
            if (TomeOfWater.consumeCharge(hit.attacker.player)) {
                hit.boostDamage(0.5);
            }
        }
    }
}