package io.ruin.model.activities.raids.tob.dungeon.boss.maiden;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.raids.tob.party.TheatreParty;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;

import static io.ruin.cache.NpcID.*;


public class MaidenCombat extends NPCCombat {

    public TheatreParty party;

    private final int bloodPitGFX = 1579;
    private final int bloodPitAnim = 8091;

    private final Projectile bloodProjectile = new Projectile(1578, 20, 31, 20, 15, 12, 15, 10);
    private final int bloodSplash = 1576;

    private final int TORNADO_ANIM = 8092;
    private final Projectile tornadoProjectile = new Projectile(1577, 20, 31, 20, 15, 12, 15, 10);
    private int stage = 0;

    public boolean scanning = true;

    @Override
    public void init() {
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
        npc.hitListener = new HitListener().postDamage(hit -> {
            double ratio = ((double) npc.getHp() / npc.getMaxHp());
            if (ratio <= .70 && stage == 0) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8361);
                nylocas();
            } else if (ratio <= .50 && stage == 1) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8362);
                nylocas();
            } else if (ratio <= .3 && stage == 2) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8363);
                nylocas();
            } else if (ratio == 0) {
                npc.transform(THE_MAIDEN_OF_SUGADINTI_8364);
            }
        });
        npc.deathStartListener = (DeathListener.Simple) () -> npc.remove();
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (Random.rollDie(15, 1)) {
            tornadoAttack(target, npc);
        } else {
            bloodSpatAttack(npc);
        }
        return true;
    }

    private void createBlood(Player player, NPC npc) {

    }

    private void bloodSpatAttack(NPC npc) {
        for (Player p : npc.getPosition().getRegion().players) {
            if (p.getPosition().isWithinDistance(npc.getPosition(), 20)) {
                npc.animate(bloodPitAnim);
                Position pos = p.getPosition().copy();
                bloodProjectile.send(npc, pos);
                npc.addEvent(event -> {
                    event.delay(2); // Send 1 blood splat to each player
                    GameObject pool = GameObject.spawn(30032, pos, 10, 0);
                    if (p.getAbsX() >= pool.x && p.getAbsX() <= pool.x + 1 && p.getAbsY() >= pool.y && p.getAbsY() <= pool.y + 1)
                        p.hit(new Hit(HitType.VENOM).fixedDamage(20).ignoreDefence());
                    pool.remove();
                    event.delay(6);
                });
            }
        }
    }

    private void nylocas() {
        for (int i = 0; i < npc.localPlayers().size() * 2; i++) {
            NPC nylocas = new NPC(NYLOCAS_MATOMENOS).spawn(getAbsolute(48, Random.get(19, 42)));
            nylocas.getCombat().setAllowRetaliate(false);
            nylocas.getCombat().setAllowRespawn(false);
            nylocas.startEvent(e -> {
                nylocas.setHp(50);
                nylocas.getRouteFinder().routeEntity(npc);
                nylocas.getCombat().setAllowRetaliate(false);
                nylocas.getCombat().setTarget(npc);
                e.waitForMovement(nylocas);
                nylocas.hit(new Hit().fixedDamage(nylocas.getHp()));
                npc.hit(new Hit(HitType.HEAL).fixedDamage(nylocas.getHp()));
                nylocas.remove();
                npc.unlock();
            }).setCancelCondition(nylocas::dead);
        }
        stage++;
    }

    public Position getAbsolute(int localX, int localY) {
        return new Position(npc.getPosition().getRegion().baseX + localX, npc.getPosition().getRegion().baseY + localY, npc.getPosition().getZ());
    }

    private void sendBloodPit(Player target) {

    }

    private void tornadoAttack(Entity target, NPC npc) {

        if (target.getPosition().isWithinDistance(npc.getPosition(), 20)) {
            int maxDamage = 36;
            npc.animate(TORNADO_ANIM);
            Position pos = target.getPosition().copy();
            bloodProjectile.send(npc, pos);
            npc.addEvent(event -> {
                int delay = tornadoProjectile.send(npc, target);
                event.delay(3);
                Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay);
                target.hit(hit);
            });
        }
    }

    private MaidenNPC asMaiden() {
        return (MaidenNPC) npc;
    }
}
