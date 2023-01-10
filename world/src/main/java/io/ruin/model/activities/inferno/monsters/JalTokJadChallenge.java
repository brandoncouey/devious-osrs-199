package io.ruin.model.activities.inferno.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.minigames.JadChallenge;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.skills.prayer.Prayer;

import java.util.LinkedList;
import java.util.List;

public class JalTokJadChallenge extends NPCCombat { // Challenge jad

    private static final int MAX_DISTANCE = 16;

    private static final Projectile[] MAGIC_PROJECTILES = {
            new Projectile(448, 128, 31, 2, 10, 8, 16, 32),
            new Projectile(449, 128, 31, 6, 14, 8, 16, 32),
            new Projectile(450, 128, 31, 10, 20, 8, 16, 32)
    };

    private boolean spawnedHealers = false;
    private final List<NPC> healers = new LinkedList<>();

    private JadChallenge getChallenge() {
        return JadChallenge.getInstance(npc);
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(this::postDamage);
        npc.deathStartListener = (entity, killer, killHit) -> healers.forEach(healer -> {
            getChallenge().getMap().removeNpc(healer);
            healer.remove();
        });
    }

    private void postDamage(Hit hit) {
        if (!spawnedHealers && npc.getHp() < (npc.getMaxHp() / 2)) {
            spawnHealers();
            spawnedHealers = true;
        }
    }

    private void spawnHealers() {
        if (isDead())
            return;
        List<Position> spawnPoints = getHealerSpawnPoints();
        for (int i = 0; i < 2; i++) {
            Position position = Random.get(spawnPoints);
            NPC healer = new NPC(7701).spawn(position.getX(), position.getY(), 0);
            healers.add(healer);
            healer.face(npc);
            healer.startEvent(e -> { //when attacked, this event will stop.
                int healTicks = 4;
                while (!npc.getCombat().isDead()) {
                    DumbRoute.step(healer, npc, 1);
                    if (++healTicks >= 4 && DumbRoute.withinDistance(healer, npc, 1)) {
                        healTicks = 0;
                        healer.animate(2639);
                        npc.graphics(444, 250, 0);
                        npc.incrementHp(Random.get(1, 10));
                    }
                    e.delay(1);
                }
            });
        }
    }

    private List<Position> getHealerSpawnPoints() {
        Position base = npc.getPosition().relative(2, 2);
        return Random.get(base.area(6, p -> !p.isWithinDistance(base, 3) && p.getTile().clipping == 0)).area(1);
    }

    @Override
    public void follow() {
        follow(MAX_DISTANCE);
    }

    @Override
    public boolean attack() {
        if (withinDistance(1)) {
            if (Random.rollDie(3)) {
                int maxdmg = 97;
                if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                    maxdmg = 1;
                }
                basicAttack(info.attack_animation, info.attack_style, maxdmg);
                return true;
            }
        } else if (!withinDistance(MAX_DISTANCE)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        if (Random.rollPercent(50)) {
            /**
             * Magic attack
             */

            npc.addEvent(e -> {
                if (npc.isAnimating()) {
                    e.delay(3);
                }
                npc.animate(7592);
                e.delay(3);
                if (target == null)
                    return;
                npc.graphics(447, 500, 0);
                e.delay(1);
                int delay = MAGIC_PROJECTILES[0].send(npc, target);
                for (int i = 1; i < MAGIC_PROJECTILES.length; i++)
                    MAGIC_PROJECTILES[i].send(npc, target);
                int maxdmg = 97;
                if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                    maxdmg = 1;
                }
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(maxdmg)
                        .clientDelay(delay);
                hit.postDamage(t -> {
                    t.graphics(157);
                    t.privateSound(163);
                });
                target.hit(hit);
            });
        } else {
            /**
             * Ranged attack
             */

            npc.addEvent(e -> {
                if (npc.isAnimating()) {
                    e.delay(3);
                }
                npc.animate(7593);
                e.delay(3);
                if (target == null)
                    return;
                World.sendGraphics(451, 0, 0, target.getPosition());
                e.delay(1);
                int maxdmg = 97;
                if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                    maxdmg = 1;
                }
                Hit hit = new Hit(npc, AttackStyle.RANGED)
                        .randDamage(maxdmg)
                        .delay(2);
                hit.postDamage(t -> {
                    t.graphics(157);
                    t.privateSound(163);
                });
                target.hit(hit);
            });
        }
        return true;
    }
}