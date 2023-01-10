package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Energy Drain: Deal an attack with 25% increased
//accuracy that siphons 10% of your target's run energy. (50%)
public class BloodWhip implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("bloody tentacle");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1658);
        player.publicSound(2713);
        target.graphics(1809, 96, 0);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostAttack(0.25));
        player.incrementHp(maxDamage);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}