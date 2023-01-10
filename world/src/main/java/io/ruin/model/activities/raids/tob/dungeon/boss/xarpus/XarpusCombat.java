package io.ruin.model.activities.raids.tob.dungeon.boss.xarpus;


import io.ruin.model.activities.raids.tob.dungeon.boss.xarpus.attacks.PoisonPoolAttack;
import io.ruin.model.entity.npc.NPCCombat;


public class XarpusCombat extends NPCCombat {

    @Override
    public void init() {
//        npc.getCombat().setAllowRespawn(false);
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
    }

    @Override
    public void follow() {
        //leave empty so npc does not walk
    }

    @Override
    public boolean attack() {
        new PoisonPoolAttack(target, npc);
        return true;
    }

    private XarpusNPC asXarpus() {
        return (XarpusNPC) npc;
    }
}
