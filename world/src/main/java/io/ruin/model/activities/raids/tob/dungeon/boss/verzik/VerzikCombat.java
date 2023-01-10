package io.ruin.model.activities.raids.tob.dungeon.boss.verzik;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;

public class VerzikCombat extends NPCCombat {

    private static final Projectile ELECTRIC = new Projectile(1580, 100, 0, 0, 220, 0, 50, 0);
    private static final Projectile SPIDER_PROJ = new Projectile(1596, 100, 30, 50, 130, 0, 15, 255);

//    private VerzikNPC.Form currentForm = VerzikNPC.Form.RANGED;

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage((hit -> {
            for (Killer k : npc.getCombat().killers.values()) {
                k.player.tobDamage++;
            }
        }));
//        npc.deathStartListener = (entity, killer, killHit) -> verzikStartEnd();
        npc.setMaxHp((int) ((double) npc.getMaxHp() * 0.75));
    }

    private void verzikStartEnd() {
        npc.startEvent(event -> {
            npc.animate(8128);
            npc.transform(8375);
            event.delay(6);
            npc.remove();
        });
    }

    @Override
    public void follow() {
        follow(50);
    }

    @Override
    public boolean attack() {
        if (Random.rollDie(15, 1) && target.getPosition().isWithinDistance(npc.getPosition(), 10))
            electricBall(target, npc);
        else if (target.getPosition().isWithinDistance(npc.getPosition(), 10))
            spiderBall(target, npc);
        return true;
    }

    private VerzikNPC asVerzik() {
        return (VerzikNPC) npc;
    }

    private void spiderBall(Entity target, NPC npc) {
        npc.animate(8125);
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = SPIDER_PROJ.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(25, 55)
                        .clientDelay(delay);
                hit.postDamage(t -> {
                    if (hit.damage > 0) {
                        t.graphics(1599, 124, 0);
                    }
                });
                p.hit(hit);
            }
        });
    }

    private void electricBall(Entity target, NPC npc) {
        npc.animate(8123);
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = ELECTRIC.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(45, 75)
                        .clientDelay(delay);
                hit.postDamage(t -> {
                    if (hit.damage > 0) {
                        t.graphics(1581, 0, 0);
                    }
                });
                p.hit(hit);
            }
        });
    }
}

