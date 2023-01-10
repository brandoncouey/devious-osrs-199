package io.ruin.model.map.object.actions.impl.prifddinas.trahaearndistrict;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.List;

public class ZalcanoCombat extends NPCCombat {
    //
//
    //Zalcano GFX
    private static final Projectile MAGIC_BALL_GOLEM_SPAWN = new Projectile(1729, 25, 31, 25, 35, 10, 0, 32);
    private static final Projectile PEBBLE_PROJECTILE = new Projectile(1302, 25, 31, 25, 35, 10, 0, 32);

    private static final int GOLEM_EXPLOSION = 1730;

    //Zalcano Rocks
    private static final int GLOWING_ROCK = 36192;
    private static final int STANDARD_ROCK = 36193;

    public static final Bounds BOUNDS = new Bounds(3022, 6035, 3046, 6061, 0);

    public static final Bounds GOLEMNORTHBOUNDS = new Bounds(3028, 6058, 3036, 6060, 0);
    //
    private static GameObject ROCK_NE = GameObject.spawn(STANDARD_ROCK, 3040, 6057, 0, 10, 3);
    private static GameObject ROCK_SE = GameObject.spawn(STANDARD_ROCK, 3040, 6040, 0, 10, 0);
    private static GameObject ROCK_SW = GameObject.spawn(STANDARD_ROCK, 3025, 6040, 0, 10, 1);
    private static GameObject ROCK_NW = GameObject.spawn(STANDARD_ROCK, 3025, 6057, 0, 10, 2);
    //
    private TickDelay boulderCooldown = new TickDelay();
    //
    private TickDelay golemCooldown = new TickDelay();
    //
    private static TickDelay zalcanoPhase = new TickDelay();
    //
    public static boolean miningPhase;
    //
    public static int health = 1000;

    //
    @Override
    public void init() {
        ROCK_NE.spawn();
        ROCK_SE.spawn();
        ROCK_SW.spawn();
        ROCK_NW.spawn();
        npc.hitListener = new HitListener().postDamage(this::postDamage);
        rockFormation();
    }

//

    private void postDamage(Hit hit) {
        // npc.getCombat().setAllowRetaliate(true);
        if (npc.getId() == 9049 && hit.attacker.player.getInventory().hasId(23907)) {
            if (hit.attacker.player.getInventory().hasId(23907)) {
                hit.damage = (int) (hit.damage * 0.25);
                hit.attacker.player.getInventory().remove(23907, 1);
            }
            int remaining = npc.getHp() - hit.damage;
        } else {
            hit.hide();
            hit.block();
            hit.damage = 0;
            hit.maxDamage = 0;
            hit.attacker.player.hit(new Hit().randDamage(25));
            if (hit.attacker.player != null)
                hit.attacker.player.sendMessage("<col=880000>Zalcano resists your attack.");
            return;
        }
    }

    //
    public void spawnGolem() {
        npc.startEvent(e -> {
            if (golemCooldown.finished()) {
                golemCooldown.delay(25);
                NPC golem = new NPC(1).spawn(GOLEMNORTHBOUNDS.randomPosition());
                golem.getCombat().setAllowRetaliate(false);
                golem.getCombat().setAllowRespawn(false);
                golem.startEvent(ev -> {
                    golem.getRouteFinder().routeEntity(npc);
                    e.waitForMovement(golem);
                    golem.hit(new Hit().fixedDamage(golem.getHp()));
                    npc.incrementHp(golem.getHp());
                }).setCancelCondition(golem::dead);
            }
        });
    }

    //
    @Override
    public void follow() {
        follow(100);
    }

    @Override
    public boolean attack() {
        if (npc.localPlayers().size() > 0) {
            for (Player p : npc.localPlayers()) {
                if (!withinDistance(25))
                    return false;
                if (Random.rollDie(5, 1) && boulderCooldown.finished())
                    dropBoulder(p);
                else if (Random.rollDie(5, 1))
                    magicBall(p);
                else
                    stoneThrow(p);
                return true;
            }
        }
        return true;
    }


//                    e.delay(15);
//                    System.out.println("Searching for idiots within range!");
//                    attackMode();



    private void stoneThrow(Entity target) {
        npc.animate(8431);
        int delay = PEBBLE_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
        if (Random.rollPercent(5))
            spawnGolem();
    }

    private void magicBall(Entity target) {
        npc.animate(8432);
        int delay = MAGIC_BALL_GOLEM_SPAWN.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        target.graphics(1730);
        target.hit(hit);
        if (Random.rollPercent(5))
            spawnGolem();
    }
    //
    private void dropBoulder(Entity target) {
        boulderCooldown.delay(30);
        npc.animate(8433);
        Position pos = target.getPosition().copy();
        int clientDelay = 5;
        int tickDelay = (((25 * clientDelay)) / 600) - 2;
        World.sendGraphics(1727, 35, clientDelay, pos.getX(), pos.getY(), pos.getZ());
        if (Random.rollPercent(5))
            spawnGolem();
        npc.addEvent(event -> {
            event.delay(tickDelay);
            if(target.getPosition().equals(pos)) {
                event.delay(4);
                target.hit(new Hit(npc, null, null).fixedDamage(target.getHp() / 3).ignoreDefence().ignorePrayer());
            }
        });
    }
    //
    public int check = 0;
    private void rockFormation() {
        npc.startEvent(e -> {
            e.delay(15);
            if (Random.rollPercent(90) && check != 1) {
                ROCK_NE.setId(GLOWING_ROCK);
                ROCK_SE.setId(STANDARD_ROCK);
                ROCK_SW.setId(STANDARD_ROCK);
                ROCK_NW.setId(STANDARD_ROCK);
                check = 1;
            } else if (Random.rollPercent(90) && check != 2) {
                ROCK_NE.setId(STANDARD_ROCK);
                ROCK_SE.setId(GLOWING_ROCK);
                ROCK_SW.setId(STANDARD_ROCK);
                ROCK_NW.setId(STANDARD_ROCK);
                check = 2;
            } else if (Random.rollPercent(90) && check != 3) {
                ROCK_NE.setId(STANDARD_ROCK);
                ROCK_SE.setId(STANDARD_ROCK);
                ROCK_SW.setId(GLOWING_ROCK);
                ROCK_NW.setId(STANDARD_ROCK);
                check = 3;
            } else if (Random.rollPercent(90) && check != 4) {
                ROCK_NE.setId(STANDARD_ROCK);
                ROCK_SE.setId(STANDARD_ROCK);
                ROCK_SW.setId(STANDARD_ROCK);
                ROCK_NW.setId(GLOWING_ROCK);
                check = 4;
            } else {
                rockFormation();
            }
        });
    }
    //
    static {
        ObjectAction.register(36195, 1, (player, obj) -> {
            player.startEvent(e -> {
                while (player.getInventory().hasId(23905)) {
                    player.animate(899);
                    e.delay(3);
                    player.getInventory().remove(23905, 1);
                    player.getInventory().add(23906, 1);
                    player.getStats().addXp(StatType.Smithing, 5, true);
                }
            });
        });

        ObjectAction.register(36196, 1, (player, obj) -> {
            player.animate(8444);
            int amount = player.getInventory().count(23906);
            player.getInventory().remove(23906, amount);
            player.getInventory().add(23907, amount);
            player.getStats().addXp(StatType.Runecrafting, 5 * amount, true);
        });
    }

}