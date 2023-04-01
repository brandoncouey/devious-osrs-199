package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//The Judgement: Deal an attack that inflicts
//37.5% more damage with double accuracy. (50%)
public class AncientGodsword implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("ancient godsword");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(9171);
        target.graphics(1962);
        int damage = target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.15).boostAttack(1.0)); //100% aka double
        if (damage > 0) {
            target.addEvent(event -> {
                event.setCancelCondition(target::dead);
                event.delay(8);
                if (target.getPosition().isWithinDistance(player.getPosition(), 6)) {
                    target.hit(new Hit().fixedDamage(25).ignorePrayer().ignoreDefence());
                    player.hit(new Hit(HitType.HEAL).fixedDamage(25));
                    target.graphics(377);
                    event.delay(8);
                }
            });
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}