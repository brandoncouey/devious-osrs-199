package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;

public class ChromaticDragon extends NPCCombat {
    boolean fire;

    @Override
    public void init() {
        npc.deathStartListener = (entity, killer, killHit) -> {
            if (npc.getDef().name.contains("Black Dragon") || npc.getDef().name.contains("Black dragon")) {
                if (killer.player.blackDragon.getKills() <= 1 || killer.player.brutalBlackDragonKills.getKills() <= 1 || killer.player.kingBlackDragonKills.getKills() <= 1) {
                    killer.player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
                    killer.player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Combat Task Completed!", "Task Completed:" + Color.WHITE.wrap(" Big, Black, and Fiery"));
                    Config.BIG_BLACK_AND_FIERY.set(killer.player, 1);
                    Config.COMBAT_ACHIVEMENTS_OVERVIEW_EASY.increment(killer.player, 1);
                }
            }
        };
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (!fire && Random.rollDie(6, 1)) { // don't do dragon fire twice in a row
            meleeDragonfire();
        } else {
            basicAttack();
        }
        return true;
    }

    void meleeDragonfire() {
        fire = true;
        npc.animate(81);
        npc.graphics(1, 100, 0);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(50));
    }

    @Override
    public Hit basicAttack() {
        fire = false;
        return super.basicAttack();
    }

    @Override
    public double getDragonfireResistance() {
        return 0.9;
    }
}
