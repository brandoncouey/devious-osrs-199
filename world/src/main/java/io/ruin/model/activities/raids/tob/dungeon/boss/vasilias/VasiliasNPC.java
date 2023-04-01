package io.ruin.model.activities.raids.tob.dungeon.boss.vasilias;

import io.ruin.model.activities.raids.tob.dungeon.TheatreBoss;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VasiliasNPC extends TheatreBoss {

    private int stage = 0;

    private final VasiliasCombat vasiliasCombat = (VasiliasCombat) this.getCombat();

    /**
     * Creates a new theatre boss object.
     *
     * @param id    the npc id.
     * @param party
     */
    public VasiliasNPC(int id, TheatreParty party) {
        super(id, party);
    }


    @Override
    public int hit(Hit... hits) {
        List<Hit> newHits = new ArrayList<>();
        for (Hit h : hits) {
            if (h.attacker instanceof Player) {
                Player pa = (Player) h.attacker;
                switch (vasiliasCombat.getPhase()) {
                    case MAGIC:
                        if (!h.attackStyle.isMagical() || !h.attackStyle.isMagic()) {
                            npc.hit(new Hit(HitType.HEAL).fixedDamage(h.damage));
                            pa.hit(new Hit(h.type).fixedDamage(h.damage));
                        } else {
                            newHits.add(h);
                        }
                        break;
                    case MELEE:
                        if (!h.attackStyle.isMelee()) {
                            npc.hit(new Hit(HitType.HEAL).fixedDamage(h.damage));
                            pa.hit(new Hit(h.type).fixedDamage(h.damage));
                        } else {
                            newHits.add(h);
                        }
                        break;
                    case RANGE:
                        if (!h.attackStyle.isRanged()) {
                            npc.hit(new Hit(HitType.HEAL).fixedDamage(h.damage));
                            pa.hit(new Hit(h.type).fixedDamage(h.damage));
                        } else {
                            newHits.add(h);
                        }
                        break;
                }
            }
        }
        return super.hit(newHits);
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
