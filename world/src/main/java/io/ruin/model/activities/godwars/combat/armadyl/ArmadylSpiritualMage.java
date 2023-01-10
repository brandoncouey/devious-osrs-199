package io.ruin.model.activities.godwars.combat.armadyl;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class ArmadylSpiritualMage extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(159, 83, 31, 51, 56, 10, 16, 64);

    private int attackStyleRange;

    @Override
    public void init() {
        attackStyleRange = getAttackStyle().isMelee() ? 1 : getAttackStyle().isMagicalMelee() ? 1 : 10;

        npc.attackNpcListener = (player, n, message) -> {
            if (player.getCombat().getAttackStyle().isMelee()) {
                if (message)
                    player.sendMessage("The aviansie is flying too high for you to hit with melee.");
                return false;
            }
            return true;
        };

//        npc.npcAttacksNPCListener = (attacker, victim) -> {
//            if (attacker.getDef().combatHandlerClass != victim.getDef().combatHandlerClass) {
//                if (victim.getDef().combatHandlerClass != null) {
//                    if (victim.getDef().combatHandlerClass.getCanonicalName().contains("armadyl")
//                            || victim.getDef().combatHandlerClass.getCanonicalName().contains("saradomin")) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//            return false;
//        };
    }

    @Override
    public void follow() {
        follow(attackStyleRange);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(attackStyleRange))
            return false;
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay));
        target.graphics(160, 124, delay);
        return true;
    }
}
