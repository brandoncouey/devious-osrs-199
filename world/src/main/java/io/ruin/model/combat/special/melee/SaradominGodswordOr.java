package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

public class SaradominGodswordOr implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equalsIgnoreCase("saradomin godsword (or)");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 20372 ? 7640 : 7641);
        if (player.goldenSaradominGodsword == true) {
            player.graphics(1745);
        } else {
            player.graphics(1209);
        }
        player.publicSound(3869);
        int damage = target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10).boostAttack(1.0));
        if (damage > 0) {
            player.incrementHp((int) Math.ceil(damage));
            player.getStats().get(StatType.Prayer).restore((int) Math.ceil(damage * 0.50), 0);
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}
