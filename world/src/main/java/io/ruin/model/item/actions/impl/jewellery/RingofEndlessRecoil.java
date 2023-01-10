package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

public class RingofEndlessRecoil {
    public enum RingofEndlessRecoil2 {
        RINGOF_ENDLESS_RECOIL_I(30456),
    RINGOF_ENDLESS_RECOIL(30318);

        private final int id;

        RingofEndlessRecoil2(int id) {
            this.id = id;
        }
    }
    public static final int RING_OF_ER = 30318;

    public static void check(Player player, Hit hit) {
        if (hit.attacker == null || hit.attackStyle == null)
            return;
        for (RingofEndlessRecoil2 ring : RingofEndlessRecoil2.values()) {
            if (ring == null)
                return;
            int damage = (int) Math.ceil(hit.damage * 0.10);
            if (damage > hit.attacker.getHp())
                damage = hit.attacker.getHp();
            if (damage == 0)
                return;
            hit.attacker.hit(new Hit(player, null, null).fixedDamage(damage));
        }
    }

    public static boolean wearingEndlessI(Player player) {
        for (RingofEndlessRecoil2 ring : RingofEndlessRecoil2.values()) {
            if (player.getEquipment().hasId(ring.id))
                return true;
        }
        return false;
    }

}
