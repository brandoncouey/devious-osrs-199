package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.List;

public class Bryophyta extends NPCCombat {

    private static final Projectile MAGIC_ATTACK = new Projectile(1686, 20, 31, 35, 35, 10, 0, 32);

    private static final Bounds BRYOPHYTA_WOODS = new Bounds(3205, 9923, 3231, 9947, 0);

    private static final Position START = new Position(3214, 9937, 0);


    private static final int RANGED_ANIM = 4410;
    private static final int SPAWN_GFX = 580;

    private static int attacks;
    boolean spawned = false;
    boolean spawnedSecond = false;

    static {
        ObjectAction.register(32534, "open", (player, obj) -> {
            player.dialogue(new OptionsDialogue("Go to Bryphyta's woods?",
                    new Option("Yes", () -> enter(player)),
                    new Option("No", () -> {
                    })));
        });

        ObjectAction.register(32535, "clamber", (player, obj) -> {
            player.dialogue(new OptionsDialogue("Return to Varrock sewers?",
                    new Option("Yes", () -> {
                        player.getMovement().startTeleport(event -> {
                            player.animate(839);
                            event.delay(3);
                            player.getMovement().teleport(3174, 9897, 0);
                        });
                    }),
                    new Option("No", () -> {
                    })));
        });
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDefend(hit -> {
            if (npc.getHp() <= 85)
                npc.graphics(SPAWN_GFX);
            spawnFirstMinions();
            if (npc.getHp() <= 45)
                npc.graphics(SPAWN_GFX);
            spawnSecondMinions();
        });
        npc.deathEndListener = (entity, killer, killHit) -> {
            for (NPC n : entity.localNpcs()) {
                if (n.getDef().id == 8194 || n.getDef().id == 8194) {
                    n.hit(new Hit(entity).fixedDamage(n.getHp()).delay(0));
                }
            }
        };

    }

    public void spawnFirstMinions() {
        final Entity e = target;
        List<Position> positions = e.getPosition().area(3, pos -> !pos.equals(e.getPosition()) && (pos.getTile() == null || pos.getTile().clipping == 0));
        if (!spawned) {
            NPC melee = new NPC(8194).spawn(Random.get(positions));
            Player p = Random.get(npc.localPlayers());
            melee.face(p);
            target = p;
            spawned = true;
        }
    }


    private void spawnSecondMinions() {
        final Entity e = target;
        List<Position> positions = e.getPosition().area(3, pos -> !pos.equals(e.getPosition()) && (pos.getTile() == null || pos.getTile().clipping == 0));
        if (!spawnedSecond) {
            NPC melee = new NPC(8194).spawn(Random.get(positions));
            Player p = Random.get(npc.localPlayers());
            melee.face(p);
            spawnedSecond = true;
        }
    }

    @Override
    public void follow() {
        follow(16);
    }

    @Override
    public boolean attack() {
        if (!target.getPosition().inBounds(BRYOPHYTA_WOODS)) {
            target = null;
            npc.faceNone(false);
            return false;
        }
        if (withinDistance(1)) {
            meleeAttack();
        } else {
            mageattack();
        }
        attacks++;
        return true;
    }

    public void meleeAttack() {
        npc.face(target);
        npc.animate(8147);
        Hit hit = new Hit(npc, AttackStyle.CRUSH)
                .randDamage(info.max_damage);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                npc.hit(new Hit(HitType.HEAL).fixedDamage(10));
            }
        });
        target.hit(hit);
    }

    public void mageattack() {
        npc.face(target);
        npc.animate(RANGED_ANIM);
        npc.localPlayers().forEach(plr -> {
            int delay = MAGIC_ATTACK.send(npc, target);
            Hit hit = new Hit(npc, AttackStyle.MAGICAL_MELEE)
                    .randDamage(info.max_damage)
                    .clientDelay(delay);
            hit.postDamage(t -> {
                if (hit.damage > 0) {
                    npc.hit(new Hit(HitType.HEAL).fixedDamage(10));
                }
            });
            target.hit(hit);
        });

    }

    public static void enter(Player player) {
        player.getInventory().remove(22374, 1);
        DynamicMap map = new DynamicMap().build(12955, 3);
        NPC bryophyta = new NPC(8195);
        bryophyta.spawn(map.swRegion.baseX + 21, map.swRegion.baseY + 13, 0);
        map.addNpc(bryophyta);

        player.addEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(map.convertPosition(START));
            map.assignListener(player).onExit((p, logout) -> {
                if (logout) {
                    p.getMovement().teleport(3086, 3499, 0);
                }
                map.destroy();
            });
            event.delay(1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

}
