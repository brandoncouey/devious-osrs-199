package io.ruin.model.activities.bosses.nightmare;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.route.routes.DumbRoute;

public class Sleepwalker extends NPC {

    private Nightmare nm;

    public Sleepwalker(int id) {
        super(id);
    }

    @Override
    public int hit(Hit... hits) {
        for (Hit h : hits) {
            if (h.attacker instanceof Player) {
                Player pa = (Player) h.attacker;
                if (pa.getEquipment().get(Equipment.SLOT_WEAPON) != null) {
                    int weaponId = pa.getEquipment().get(Equipment.SLOT_WEAPON).getId();
                    if (weaponId == 23360) {
                        h.damage = 10;
                        h.minDamage = 10;
                        h.maxDamage = 10;
                    }
                }
            }
            if (h.damage > 10) {
                h.damage = 10;
                h.minDamage = 10;
                h.maxDamage = 10;
            }
        }
        return super.hit(hits);
    }

    @Override
    public void process() {
        super.process();
        if (nm != null) {
            DumbRoute.route(npc, nm.getCentrePosition().getX(), nm.getCentrePosition().getY());
            if (getPosition().distance(nm.getCentrePosition()) < 4) {
                hit(new Hit().fixedDamage(100));
                npc.remove();
                nm.setSleepwalkerCount(nm.getSleepwalkerCount() + 1);
            }
        }
    }

    @Override
    public boolean isMovementBlocked(boolean message, boolean ignoreFreeze) {
        return false;
    }

    public Nightmare getNm() {
        return nm;
    }

    public void setNm(Nightmare nm) {
        this.nm = nm;
    }

}
