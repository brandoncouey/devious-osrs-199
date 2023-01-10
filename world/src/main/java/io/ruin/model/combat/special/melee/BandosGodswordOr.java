package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class BandosGodswordOr implements Special {

    private static final StatType[] DRAIN_ORDER = {StatType.Defence, StatType.Strength, StatType.Attack, StatType.Prayer, StatType.Magic, StatType.Ranged};

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equalsIgnoreCase("bandos godsword (or)");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 20370 ? 7642 : 7643);
        if (player.goldenBandosGodsword == true) {
            player.graphics(1748);
        } else {
            player.graphics(1212);
        }
        player.publicSound(3869);
        int damage = target.hit(new Hit(player, attackStyle, attackType)
                .randDamage(maxDamage)
                .boostDamage(0.21)
                .boostAttack(0.5)
        );
        if (damage > 0) {
            if (target.player != null) {
                for (StatType t : DRAIN_ORDER) {
                    if (t == StatType.Prayer)
                        damage -= target.player.getPrayer().drain(damage);
                    else
                        damage -= target.player.getStats().get(t).drain(damage);
                    if (damage <= 0)
                        break;
                }
                target.player.sendMessage("You feel drained!");
            } else {
                for (StatType t : DRAIN_ORDER) {
                    Stat stat = target.npc.getCombat().getStat(t);
                    if (stat == null)
                        continue;
                    damage -= stat.drain(damage);
                    if (damage <= 0)
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 40;
    }

}
