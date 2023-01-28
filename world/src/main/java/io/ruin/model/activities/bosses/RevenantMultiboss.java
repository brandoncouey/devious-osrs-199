package io.ruin.model.activities.bosses;

import io.ruin.api.utils.ArrayUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import java.util.List;

public class RevenantMultiboss extends NPCCombat {

  //  private static final int MELEE_PRAYER = 7144;
   // private static final int RANGED_PRAYER = 7145;
  //  private static final int MAGIC_PRAYER = 7146;
//
    private static final int DAMAGE_THRESHOLD = 50;

    private int damageTaken = 0;
    private int misses = 0;
    private AttackStyle style = AttackStyle.MAGIC;
    private final TickDelay boulderCooldown = new TickDelay();

    private static final Projectile STANDARD_PROJECTILE = new Projectile(134, 60, 40, 61, 20, 15, 15, 127);
    private static final Projectile FIRE_PROJECTILE =new Projectile(1415, 43, 31, 41, 56, 10, 15, 64);
    private static final Projectile FIRE_SECONDARY_PROJECTILE = new Projectile(660, 30, 0, 0, 75, 0, 55, 0);
    private static final Projectile ICE_PROJECTILE = new Projectile(2033, 30, 31, 41, 51, 10, 15, 11);

    @Override
    public void init() {
            SpawnListener.register(ArrayUtils.of("Maledictus"), npc -> {
                Broadcast.WORLD.sendNews("A superior revenant has been awoken in the north of the Revenant Cave");

            });
        npc.hitListener = new HitListener().preDefend(this::preDefend).postDefend(this::postDefend);
        npc.deathEndListener = (DeathListener.Simple) () -> {
            for (Killer k : npc.getCombat().killers.values()) {
                for (int i = 0; i < 2; i++) {
                    if (!npc.localPlayers().contains(k.player)) {
                        k.player.sendMessage("You are no longer able to get rewarded from Maledictus. there for did not receive a reward.");
                        return;
                    }
                    Item rolled = rollRegular();
                    int amount = rolled.getAmount();
                    if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                        rolled.setId(rolled.getDef().notedId);
                    new GroundItem(rolled).owner(k.player).position(k.player.getPosition().getX(), k.player.getPosition().getY(), k.player.getPosition().getZ()).spawn();
                    k.player.sendMessage("<shad=000000>" + Color.COOL_BLUE.wrap("You got " + rolled.getDef().name + " X " + rolled.getAmount()) + "</shad>");
                    if (rolled.getId() == 30256 || rolled.getId() == 6829 || rolled.getId() == 30307 || rolled.getId() == 30307 || rolled.getId() == 30307 || rolled.getId() == 11944) {
                        Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.RAID_PURPLE.wrap("[RARE DROP] ") + k.player.getName() + " has just received " + Color.DARK_RED.wrap(rolled.getDef().name) + " from The Vote Boss!" + "</shad>");
                    }
                }
            }
            npc.remove();
        };
    }
    private void restoreDefaults() {
        boulderCooldown.reset();
        style = Random.rollPercent(50) ? AttackStyle.CRUSH : (Random.rollPercent(50) ? AttackStyle.RANGED : AttackStyle.MAGIC);
        misses = damageTaken = 0;
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
            damageTaken += hit.damage;
            if (damageTaken >= DAMAGE_THRESHOLD && hit.attackStyle != null) {
                damageTaken = 0;
                if (target != null && target.getCombat().getTarget() == npc)
                    target.getCombat().reset();
        }
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        if (attacker.player != null && !attacker.player.getCombat().isSkulled()) {
            attacker.player.getCombat().skullHighRisk();
            attacker.player.sendMessage("<col=6f0000>You've been marked with a high risk skull for attacking the Revenant Boss!");
        }
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (Random.rollPercent(30))
            fireFire();
        else if (Random.rollPercent(30))
            fireIce();
        else
            fireStandard();
        return true;
    }
    private void preDefend(Hit hit) {
        if (hit.attackStyle == null)
            return;
    }

    private void fireStandard() {
        npc.graphics(9277);
        projectileAttack(STANDARD_PROJECTILE, 9277, AttackStyle.RANGED, info.max_damage);
    }

    private void fireIce() {
        npc.animate(9278);
        npc.graphics(1454, 250, 0);
        int delay = ICE_PROJECTILE.send(npc, target);
        target.graphics(1312, 0, delay);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay).postDamage(entity -> {
            entity.freeze(5, npc);
            entity.player.sendMessage("The ice arrow freezes you to the bone!");
            entity.localPlayers().forEach(p -> {
                if (p == entity)
                    return;
                if (Misc.getDistance(entity.getPosition(), p.getPosition()) <= 1) {
                    p.graphics(1312, 0, 0);
                    p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage((int) (info.max_damage * 2.0 / 3)));
                }
            });
        }));
    }

    private void fireFire() {
        npc.animate(9279);
        npc.graphics(1454, 250, 0);
        int delay = FIRE_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay).postDamage(entity -> {
            List<Position> area = entity.getPosition().area(2, pos -> !pos.equals(entity.getPosition()));
            for (int i = 0; i < 6; i++) {
                Position pos = Random.get(area);
                FIRE_SECONDARY_PROJECTILE.send(entity, pos.getX(), pos.getY());
                World.sendGraphics(659, 0, 75, pos);
                World.startEvent(event -> {
                    event.delay(3);
                    npc.localPlayers().forEach(p -> {
                        if (p == entity)
                            return;
                        if (p.getPosition().equals(pos))
                            p.hit(new Hit(npc, null).randDamage(20, 30));
                    });
                });
                area.remove(pos);
            }
        }));

    }

    public static LootTable regularTable = new LootTable()
            .addTable(25,
                    new LootItem(2, 3000, 3), //cannonballs x3000
                    new LootItem(6686, 50, 3), //saradomin brew noted x50
                    new LootItem(12696, 25, 2), //super combat pooition noted x25
                    new LootItem(995, Random.get(100_000, 1_000_000), 3), // Coins
                    new LootItem(13307, Random.get(100_000, 500_000), 3) // Blood Money
            )
            .addTable(20,
                    new LootItem(6829, Random.get(4, 8), 1).broadcast(Broadcast.GLOBAL), // Vote box
                    new LootItem(30256, Random.get(10, 20), 2).broadcast(Broadcast.GLOBAL), //Vote ticket
                    new LootItem(4585, 1, 1, 30), //D Skirt
                    new LootItem(1215, 1, 1, 30), //D Dagger
                    new LootItem(1305, 1, 1, 30) //D long
            )
            .addTable(10,
                    new LootItem(21905, 500, 3), // Dragon Bolts
                    new LootItem(989, Random.get(10, 15), 3), //Crystal Key
                    new LootItem(23951, Random.get(5, 9), 3), //Enhanced Crystal Key
                    new LootItem(24366, 1, 2).broadcast(Broadcast.GLOBAL), // Master Scroll box
                    new LootItem(12073, 1, 2), // Elite clue scroll
                    new LootItem(21807, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Ancient emblem
                    new LootItem(21810, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Ancient totem
                    new LootItem(21813, 1, 1, 1).broadcast(Broadcast.GLOBAL) //Ancient statuette
            )
            .addTable(5,
                    new LootItem(22547, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
                    new LootItem(30307, 5, 1).broadcast(Broadcast.GLOBAL), // Membership tokens
                    new LootItem(22552, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
                    new LootItem(22622, 1, 1, 1).broadcast(Broadcast.GLOBAL), //statius warhammer
                    new LootItem(22634, 50, 100, 1).broadcast(Broadcast.GLOBAL), //Morrigans axe
                    new LootItem(22636, 50, 100, 1).broadcast(Broadcast.GLOBAL), //morrigans javelin
                    new LootItem(22647, 1, 1, 1).broadcast(Broadcast.GLOBAL), //zuriels staff
                    new LootItem(30250, 1, 5, 1).broadcast(Broadcast.GLOBAL), //$10 bond
                    new LootItem(22650, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriel Hood
                    new LootItem(22653, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriel Robe top
                    new LootItem(22656, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriel Robe bottom
                    new LootItem(22625, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Statius full helm
                    new LootItem(22628, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Statius platebody
                    new LootItem(22631, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Statius platelegs
                    new LootItem(22616, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Vesta Chainbody
                    new LootItem(22619, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Vesta chainskirt
                    new LootItem(22638, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigan Coif
                    new LootItem(22641, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigan Leather Body
                    new LootItem(22644, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigan leather chaps
                    new LootItem(22299, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Ancient medallion
                    new LootItem(22302, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Ancient effigy
                    new LootItem(22305, 1, 1, 1).broadcast(Broadcast.GLOBAL) //Ancient relic
            );

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

}
