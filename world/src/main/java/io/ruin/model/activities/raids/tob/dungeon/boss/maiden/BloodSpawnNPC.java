package io.ruin.model.activities.raids.tob.dungeon.boss.maiden;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.process.event.EventWorker;
import io.ruin.utility.Misc;
import io.ruin.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BloodSpawnNPC extends NPC {

    private List<GameObject> toSpawn = new ArrayList<>();

    private MaidenCombat maidenCombat;

    private List<GameObject> bloodSpawns = new CopyOnWriteArrayList<>();

    public BloodSpawnNPC(int id) {
        super(id);

        npc.deathStartListener = (entity, killer, killHit) -> {
            if (maidenCombat != null) {
                maidenCombat.getBloodSpawnNPCS().remove(this);
            }
            for (GameObject pool : bloodSpawns) {
                pool.remove();
            }
        };
    }

    @Override
    public void process() {
        super.process();
        if (dead()) {
            if (bloodSpawns.size() > 0) {
                for (GameObject pools : bloodSpawns) {
                    if (pools != null) {
                        pools.remove();
                    }
                    remove();
                }
            }
            return;
        }
        final Position pos = getPosition();
        boolean spawn = true;
        for (GameObject obj : bloodSpawns) {
            if (obj.getPosition().equals(pos))
                spawn = false;
        }
        if (spawn) {
            spawnBlood(pos);
        }
        if (maidenCombat != null) {
            for (Player player : maidenCombat.getNpc().getPosition().getRegion().players) {
                if (player == null) continue;
                for (GameObject pools : bloodSpawns) {
                    if (player.getPosition().equals(pools.getPosition())) {
                        int damage = Utils.random(5, 10) + maidenCombat.bloodHpDmgModifier;
                        player.hit(new Hit(HitType.DAMAGE).fixedDamage(damage).ignoreDefence().ignorePrayer());
                        maidenCombat.getNpc().hit(new Hit(HitType.HEAL).fixedDamage(damage));
                        player.getPrayer().drain(Utils.random(2, 5));
                        maidenCombat.bloodSpawnChance -= 5;
                    }
                }
            }
        }
        if (!npc.dead()) {
            if (Utils.random(0, 3) == 0 || !npc.getMovement().hasMoved()) {
                int x = npc.getPosition().getX() + Utils.random(-10, 10);
                int y = npc.getPosition().getY() + Utils.random(-10, 10);
                npc.getRouteFinder().routeAbsolute(x, y);
            }
        }
    }

    public void spawnBlood(Position position) {
        EventWorker.startEvent(event -> {
            event.delay(2);
            bloodSpawns.add(GameObject.spawn(32984, position, 10, 0));
        });
        if (bloodSpawns.size() > 20) {
            EventWorker.startEvent(event -> {
                event.delay(Utils.random(1, 3));
                GameObject obj = bloodSpawns.get(0);
                if (obj != null) {
                    bloodSpawns.remove(obj);
                    obj.remove();
                }
            });

        }
    }

    public void setMaidenCombat(MaidenCombat combat) {
        this.maidenCombat = combat;
    }
}
