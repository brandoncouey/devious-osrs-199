package io.ruin.model.activities.raids.tob.dungeon.boss.xarpus;


import io.ruin.model.activities.raids.tob.dungeon.boss.xarpus.attacks.PoisonPoolAttack;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;


public class XarpusCombat extends NPCCombat {

    @Override
    public void init() {
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
        npc.deathEndListener = (DeathListener.Simple) () -> {
            for (Player p : npc.getPosition().getRegion().players) {
                Nurse.restoreOnly(p);
            }
        };
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
