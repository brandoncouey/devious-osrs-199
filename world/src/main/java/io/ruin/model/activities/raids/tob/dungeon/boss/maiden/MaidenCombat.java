package io.ruin.model.activities.raids.tob.dungeon.boss.maiden;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Utils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.cache.NpcID.*;


public class MaidenCombat extends NPCCombat {

    public TheatreParty party;

    @Getter
    private List<BloodSpawnNPC> bloodSpawnNPCS = new ArrayList<>();

    private int bloodSpawns;

    private final int bloodPitGFX = 1579;
    private final int bloodPitAnim = 8091;

    private final Projectile bloodProjectile = new Projectile(1578, 20, 31, 20, 15, 12, 15, 10);

    private final int bloodSplash = 1576;

    private final int TORNADO_ANIM = 8092;
    private final Projectile tornadoProjectile = new Projectile(1577, 20, 31, 20, 15, 12, 15, 10);
    private int stage = 0;

    private boolean spawnedNyclosStage1 = false;
    private boolean spawnedNyclosStage2 = false;
    private boolean spawnedNyclosStage3 = false;

    public int bloodHpDmgModifier = 0;

    public int bloodSpawnChance = 200;

    @Override
    public void init() {
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
        npc.deathEndListener = (DeathListener.Simple) () -> {
            for (Player p : npc.getPosition().getRegion().players) {
                Nurse.restoreOnly(p);
            }
            for (BloodSpawnNPC spawnNPC : bloodSpawnNPCS) {
                if (spawnNPC != null)
                    spawnNPC.remove();
            }
            bloodSpawnNPCS.clear();
        };
        npc.hitListener = new HitListener().postDamage(hit -> {
            if ((npc.getHp() <= Utils.getPercent(70, npc.getMaxHp())) && stage == 0 && !spawnedNyclosStage1) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8361);
                spawnedNyclosStage1 = true;
                nylocas();
            } else if ((npc.getHp() <= Utils.getPercent(50, npc.getMaxHp())) && stage == 1 && !spawnedNyclosStage2) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8362);
                spawnedNyclosStage2 = true;
                nylocas();
            } else if ((npc.getHp() <= Utils.getPercent(30, npc.getMaxHp())) && stage == 2 && !spawnedNyclosStage3) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8363);
                spawnedNyclosStage3 = true;
                nylocas();
            } else if (npc.getHp() <= 0) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8364);

            }
        });
        npc.hitsUpdate.hpBarType = 2;
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {

        if (Utils.random(0, bloodSpawnChance) < 10) {
            bloodSpawns();
        }
        if (Random.rollDie(15, 1)) {
            tornadoAttack(target, npc);
        } else {
            bloodSpatAttack(npc);
        }
        return true;
    }

    private void bloodSpatAttack(NPC npc) {
        for (Player p : npc.getPosition().getRegion().players) {
            if (p.getPosition().isWithinDistance(npc.getPosition(), 20)) {
                npc.animate(bloodPitAnim);
                Position pos = p.getPosition().copy();
                int delay = bloodProjectile.send(npc, pos);
                World.sendGraphics(1579, 0, delay, pos);
                npc.addEvent(event -> {
                    event.delay(2);
                    if (p.getPosition().isWithinDistance(pos, 0))
                        p.hit(new Hit(HitType.DAMAGE).fixedDamage(20).ignoreDefence().ignorePrayer());
                });
            }
        }
    }

    private void bloodSpawns() {
        if (bloodSpawns >= getMaxBloodSpawns()) return;
        bloodSpawns++;
        Position position = npc.getPosition().copy();
        if (Utils.random(2) == 3) {
            position.translate(Utils.random(5, 7), Utils.random(5, 10));
        } else if (Utils.random(2) == 1) {
            position.translate(0, Utils.random(5, 9));
        } else {
            position.translate(0, Utils.random(5, 9));
        }
        BloodSpawnNPC blood = (BloodSpawnNPC) new BloodSpawnNPC(BLOOD_SPAWN).spawn(position);
        blood.setHp(120);
        blood.setMaxHp(120);
        blood.setMaidenCombat(this);
        bloodSpawnNPCS.add(blood);
        blood.getCombat().setAllowRetaliate(false);
        blood.getCombat().setAllowRespawn(false);
    }

    private void nylocas() {
        for (int i = 0; i < npc.localPlayers().size() * 2; i++) {
            NPC nylocas = new NPC(NYLOCAS_MATOMENOS).spawn(getAbsolute(48, Random.get(19, 42)));
            nylocas.setMaxHp(getMyLocusHP());
            nylocas.setHp(getMyLocusHP());
            nylocas.getCombat().setAllowRetaliate(false);
            nylocas.getCombat().setAllowRespawn(false);
            stage++;
            nylocas.startEvent(e -> {
                nylocas.getRouteFinder().routeEntity(npc);
                nylocas.getCombat().setAllowRetaliate(false);
                nylocas.getCombat().setTarget(npc);
                while (true) {
                    if (nylocas.getPosition().isWithinDistance(npc.getPosition(), 7)) {
                        int hp = nylocas.getHp();
                        nylocas.hit(new Hit().fixedDamage(hp));
                        npc.hit(new Hit(HitType.HEAL).fixedDamage(hp * 2));
                        npc.getCombat().getInfo().max_damage += Utils.random(8, 15);
                        bloodHpDmgModifier += Utils.random(8, 12);
                    }
                    e.delay(1);
                }
            }).setCancelCondition(nylocas::dead);
        }
    }

    public Position getAbsolute(int localX, int localY) {
        return new Position(npc.getPosition().getRegion().baseX + localX, npc.getPosition().getRegion().baseY + localY, npc.getPosition().getZ());
    }


    private void tornadoAttack(Entity target, NPC npc) {

        if (target.getPosition().isWithinDistance(npc.getPosition(), 20)) {
            int maxDamage = 36;
            npc.animate(TORNADO_ANIM);
            Position pos = target.getPosition().copy();
            bloodProjectile.send(npc, pos);
            npc.addEvent(event -> {
                event.delay(2);
                int delay = tornadoProjectile.send(npc, target);
                event.delay(1);
                Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay).ignorePrayer();
                target.hit(hit);
            });
        }
    }

    public int getMyLocusHP() {
        switch (npc.getPosition().getRegion().players.size()) {
            case 5:
                return 200;
            case 4:
                return 175;
        }
        return 150;
    }

    public int getMaxBloodSpawns() {
        switch (npc.getPosition().getRegion().players.size()) {
            case 5:
                return 8;
            case 4:
                return 6;
            case 3:
                return 5;
            case 2:
                return 4;
        }
        return 3;
    }

    public NPC getNpc() {
        return npc;
    }

    public MaidenNPC asMaiden() {
        return (MaidenNPC) npc;
    }
}
