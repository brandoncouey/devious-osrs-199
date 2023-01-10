package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;

public class ZamorakGodswordOr implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equalsIgnoreCase("zamorak godsword (or)");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 20374 ? 7638 : 7639);
        if (player.goldenZamorakGodsword == true) {
            player.graphics(1746);
        } else {
            player.graphics(1210);
        }
        player.publicSound(3869);
        int damage = target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10).boostAttack(1.0));
        if (damage > 0) {
            target.graphics(369);
            target.freeze(30, player);
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 55;
    }

}
