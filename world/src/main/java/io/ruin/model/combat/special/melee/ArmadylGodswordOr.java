package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;

public class ArmadylGodswordOr implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equalsIgnoreCase("armadyl godsword (or)");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 20368 ? 7644 : 7645);
        if (player.goldenArmadylGodsword) {
            player.graphics(1747);
        } else {
            player.graphics(1211);
        }
        player.publicSound(3869);
        target.hit(new Hit(player, attackStyle, attackType)
                .randDamage(maxDamage)
                .boostDamage(0.375)
                .boostAttack(1.0) //100% aka double
        );
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}
