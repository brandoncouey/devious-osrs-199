package io.ruin.model.activities.raids;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;

import static io.ruin.model.activities.raids.newtob.arenaBounds;

public class VerzikCombat extends NPCCombat {

    private static final Projectile ELECTRIC = new Projectile(1580, 100, 0, 0, 220, 0, 50, 0);
    private static final Projectile SPIDER_PROJ = new Projectile(1596, 100, 30, 50, 130, 0, 15, 255);
    private static final Projectile ACID_POOL_PROJECTILE = new Projectile(1354, 90, 0, 30, 100, 0, 16, 0);
    private static final Projectile LAVA_PROJECTILE = new Projectile(1247, 65, 0, 25, 86, 0, 15, 220);

    private static int special = 0;

    @Override
    public void init() {
        npc.deathEndListener = (DeathListener.Simple) () -> {
            for (Player localPlayer : npc.localPlayers()) {
                localPlayer.TheatreOfBloodCompletions += 1;
                localPlayer.sendMessage("You killed Verzik, you now have: " + localPlayer.TheatreOfBloodCompletions + " total points to spend.");
            }
            npc.startEvent(e -> {
                npc.animate(-1);
                npc.transform(8375);
                e.delay(6);
                npc.remove();
                newtob.spawned = false;
            });
        };
        npc.setIgnoreMulti(true);
        startAcidPoolEvent();
        removeAcid();
    }

    public void removeAcid() {
        npc.getPosition().getRegion().bounds.forEachPos(position -> {
            GameObject obj = Tile.getObject(32000, position.getX(), position.getY(), 0, 10, -1);
            if (obj != null) {
                obj.remove();
            }
        });
    }

    @Override
    public void follow() {
        follow(50);
    }

    @Override
    public boolean attack() {
        if (special >= 100) {
            acidPoolsAttack();
            special = 0;
            return true;
        }
        if (Random.rollDie(15, 1))
            electricBall(npc);
        else
            spiderBall(npc);
        return true;
    }

    private void spiderBall(NPC npc) {
        npc.animate(8125);
        special += 10;
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = SPIDER_PROJ.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 5 : 25, p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 10 : 55)
                        .clientDelay(delay).ignorePrayer().ignoreDefence();
                hit.postDamage(t -> {
                    if (hit.damage > 0) {
                        t.graphics(1599, 124, 0);
                    }
                });
                p.hit(hit);
                p.getPrayer().drain(2);
            }
        });
    }

    private void electricBall(NPC npc) {
        npc.animate(8123);
        special += 10;
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = ELECTRIC.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 5 : 25, p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? 10 : 55)
                        .clientDelay(delay).ignorePrayer().ignoreDefence();
                hit.postDamage(t -> {
                    if (hit.damage > 0) {
                        t.graphics(1581, 0, 0);
                    }
                });
                p.hit(hit);
                p.getPrayer().drain(2);
            }
        });
    }

    private void startAcidPoolEvent() {
        npc.addEvent(event -> {
            while (!isDead()) {
                npc.localPlayers().forEach(p -> {
                    if (Tile.getObject(32000, p.getAbsX(), p.getAbsY(), p.getHeight(), 10, -1) != null) {
                        p.hit(new Hit(HitType.POISON).randDamage(1, 3));
                        p.poison(4);
                        p.getPrayer().drain(1);
                    }
                });
                event.delay(1);
            }
        });
    }

    private void acidPoolsAttack() {
        npc.animate(8126);
        int poisonPools = (Random.get(10, 40) * npc.localPlayers().size());
        npc.localPlayers().forEach(p -> {
            npc.startEvent(e -> {
                GameObject pool = GameObject.spawn(32000, p.getPosition(), 10, 0);
                e.delay(6);
                pool.remove();
            });
        });

        for (int i = 0; i < poisonPools; i++) {
            Position pos = arenaBounds.randomPosition();
            ACID_POOL_PROJECTILE.send(npc, pos);
            npc.addEvent(event -> {
                event.delay(3);
                GameObject pool = GameObject.spawn(32000, pos, 10, 0);
                event.delay(18);
                pool.remove();
            });
        }
    }

}
