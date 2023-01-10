package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.TickDelay;

import java.util.List;
import java.util.stream.Collectors;

public class JudgeOfYama extends NPCCombat {

    private static final int THROW_FIRE = 4679;
    private static final int BASIC_MELEE = 4680;
    private static final int CLEAVE = 4680;
    private static final Projectile FIRE_PROJ = new Projectile(1839, 350, 0, 30, 56, 10, 32, 32).tileOffset(1);
    private static final TickDelay FLIES_DELAY = new TickDelay();

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
        FLIES_DELAY.delay(50);
        npc.setIgnoreMulti(true);
    }

    @Override
    public void follow() {
        follow(1);
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle == null)
            return;
        else if (hit.attackStyle.isRanged())
            hit.block();
        else if (hit.attackStyle.isMagic())
            hit.block();
    }

    @Override
    public boolean attack() {
        int random = Random.get(1, 10);
        switch (random) {
            case 1:
            case 2:
                return fireAttack();
            case 3:
            case 4:
            case 5:
            case 6:
                return bigCleave();
            case 7:
            case 8:
            case 9:
            case 10:
                return smallCleave();
        }
        return true;
    }

    /**
     * Throwing Fire attack
     *
     * @return
     */
    private boolean fireAttack() {
        npc.animate(THROW_FIRE);
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        for (Player player : targets) {
            final Position firePosition = player.getPosition().copy();
            int clientDelay = FIRE_PROJ.send(npc, firePosition);
            int tickDelay = ((clientDelay * 25) / 600) - 1;
            World.sendGraphics(1668, 0, 0, firePosition);
            World.startEvent(e -> {
                try {
                    e.delay(tickDelay);
                    if (player.getPosition().equals(firePosition)) {
                        List<Position> tiles = firePosition.area(1);
                        for (int i = 0; i < 3; i++) {
                            World.sendGraphics(1668, 0, 0, Random.get(tiles));
                        }
                        player.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(15));
                        player.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(15));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        return !targets.isEmpty();
    }

    /**
     * Cleave attack
     *
     * @return
     */
    private boolean bigCleave() {
        npc.animate(CLEAVE);
        npc.graphics(1894, 350, 0);
        List<Player> targets = npc.localPlayers().stream().filter(t ->
                ProjectileRoute.allow(npc, t) && t.getPosition().isWithinDistance(target.getPosition(), 3)
        ).collect(Collectors.toList());
        for (Player player : targets) {
            player.hit(new Hit(npc, AttackStyle.SLASH).randDamage(info.max_damage));
        }
        return !targets.isEmpty();
    }

    /**
     * flies attack
     *
     * @return
     */
    private boolean flies() {
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        for (Player player : targets) {
            NPC tornado = new NPC(10561).spawn(npc.getPosition().copy().center(npc.getSize()));
            tornado.setIgnoreMulti(true);
            tornado.getCombat().setAllowRespawn(false);
            tornado.getCombat().setTarget(player);
            World.startEvent(e -> {
                int hits = 0;
                int ticks = 0;
                while (ticks++ < 25 && hits < 4) {
                    if (tornado.getPosition().isWithinDistance(player.getPosition(), 1)) {
                        player.hit(new Hit(tornado, AttackStyle.MAGIC).randDamage(20));
                        hits++;
                    }
                    e.delay(1);
                }
                tornado.remove();
            });
        }
        FLIES_DELAY.delay(100);
        return !targets.isEmpty();
    }

    /**
     * Basic melee attack
     *
     * @return
     */
    private boolean smallCleave() {
        basicAttack(BASIC_MELEE, AttackStyle.CRUSH, info.max_damage);
        if (!FLIES_DELAY.isDelayed()) {
            flies();
        }
        return true;
    }

    static {
        if (false) {
            NPCDef.get(10938).ignoreOccupiedTiles = true;
            NPCDef.get(10561).ignoreOccupiedTiles = true;
        }
    }

}
