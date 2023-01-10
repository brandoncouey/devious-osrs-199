package io.ruin.model.activities.godwars.combat.bandos;

import io.ruin.model.entity.npc.NPCCombat;

public class BasicBandosFollowers extends NPCCombat {

    private int attackStyleRange;

    @Override
    public void init() {
        attackStyleRange = getAttackStyle().isMelee() ? 1 : getAttackStyle().isMagicalMelee() ? 1 : 8;

//        npc.npcAttacksNPCListener = (attacker, victim) -> {
//            if (attacker.getDef().combatHandlerClass != victim.getDef().combatHandlerClass) {
//                if (victim.getDef().combatHandlerClass != null) {
//                    if (victim.getDef().combatHandlerClass.getCanonicalName().contains("bandos")) {
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
        basicAttack();
        return true;
    }

}
