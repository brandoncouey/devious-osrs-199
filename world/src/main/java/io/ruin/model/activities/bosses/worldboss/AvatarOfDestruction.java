package io.ruin.model.activities.bosses.worldboss;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.DoubleDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Broadcast;

import java.util.function.Consumer;

public class AvatarOfDestruction extends NPCCombat {

    private static final StatType[] DRAIN = {StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1714, 100, 10, 25, 70, 10, 12, 64);
    private static final Projectile RANGE_ATTACK = new Projectile(1713, 100, 10, 50, 70, 10, 12, 64);
    private static final Projectile CRYSTAL_BOMB_PROJECTILE = new Projectile(1357, 90, 0, 30, 100, 0, 16, 0);

    @Override
    public void init() {
        npc.setIgnoreMulti(true);

        npc.setHp((int) (500 * (World.players.count() / 2 / 5 * 2.50)));

        npc.deathStartListener = (entity, killer, killHit) -> {
            for (Killer k : npc.getCombat().killers.values()) {
                for (int i = 0; i < 2; i++) {
                    if (!npc.localPlayers().contains(k.player)) {
                        k.player.sendMessage("You left Donation boss. there for did not receive a reward.");
                        return;
                    }
                    Item rolled = rollRegular();
                    int amount = rolled.getAmount();
                    if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                        rolled.setId(rolled.getDef().notedId);
                    if (World.weekendExpBoost)
                        if (Random.rollDie(250, 1)) {
                            amount++;
                        }
                    int doubleDropChance = DoubleDrops.getChance(k.player);
                    if (Random.get(1, 100) <= doubleDropChance)
                        amount++;
                    new GroundItem(rolled.getId(), amount).owner(k.player).position(npc.getPosition()).spawn();
                    if (rolled.getId() == 6199 || rolled.getId() == 290 || rolled.getId() == 30307 || rolled.getId() == 11944) {
                        Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.ORANGE.wrap(k.player.getName() + " has just received " + Color.DARK_RED.wrap(rolled.getDef().name) + " from The Donator Boss!" + "</shad>"));
                    }
                }
            }
            npc.remove();
        };
    }

    @Override
    public void follow() {
        follow(8);
    }

    private void forAllTargets(Consumer<Player> action) {
        npc.getPosition().getRegion().players.stream()
                .filter(p -> p.getHeight() == npc.getPosition().getZ()) // on olm floor, past the barrier
                .forEach(action);
    }

/*    public void meteorAttack() {
        npc.forceText("May the crystals bomb you!");
        npc.addEvent(event -> {
            Position bombPos = target.getPosition().copy();
            CRYSTAL_BOMB_PROJECTILE.send(npc, bombPos);
            event.delay(3);
            GameObject bomb = GameObject.spawn(29766, bombPos, 10, 0);
            event.delay(8);
            bomb.remove();
            World.sendGraphics(40, 0, 0, bombPos);
            forAllTargets(p -> {
                int distance = p.getPosition().distance(bombPos);
                if (distance > 5)
                    return;
                p.hit(new Hit(npc).fixedDamage(p.getMaxHp() - (distance * 10)));
            });
            if(npc.getPosition().distance(bombPos) <= 1) {
                npc.hit(new Hit(npc).fixedDamage(73));
            }
        });

    };*/

    @Override
    public boolean attack() {
        if (!withinDistance(8)) {
            return false;
        }
/*        boolean healed = false;
        if (!HealDelay.isDelayed()) {
            healed = true;
            heal();
        }*/
        if (withinDistance(1)) {
            int random = Random.get(1, 10);
            switch (random) {
                case 1:
                case 2:
                case 3:
                case 4:
                    RangeAttack();
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    basicAttack();
                    break;
            }
            return true;
        }
        int random = Random.get(1, 10);
        switch (random) {
            case 1:
            case 2:
            case 3:
            case 4:
                RangeAttack();
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                magicAttack();
                break;
        }

        return true;
    }

    private void magicAttack() {
        npc.animate(8840);
        npc.localPlayers().forEach(p -> {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = MAGIC_PROJECTILE.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(info.max_damage)
                        .clientDelay(delay).ignoreDefence();
                hit.postDamage(t -> {
                    if (hit.damage > 0) {
                        t.graphics(1716, 124, 0);
                    } else {
                        t.graphics(85, 124, 0);
                    }
                });
                p.hit(hit);
            }
        });
    }

    private void RangeAttack() {
        npc.animate(8840);
        npc.localPlayers().forEach(p -> {

            if (ProjectileRoute.allow(npc, p)) {
                int delay = RANGE_ATTACK.send(npc, p);
                Hit hit = new Hit(npc, AttackStyle.RANGED)
                        .randDamage((int) (info.max_damage * 0.25))
                        .clientDelay(delay).ignoreDefence();
                hit.postDamage(t -> {
                    if (hit.damage > 0) {
                        t.graphics(1715, 124, 0);
                    } else {
                        t.graphics(85, 124, 0);
                    }
                });
                p.hit(hit);
            }
        });
    }

    public static LootTable regularTable = new LootTable()
            .addTable(25,
                    new LootItem(2, 3000, 3), //cannonballs x3000
                    new LootItem(6686, 50, 3), //saradomin brew noted x50
                    new LootItem(12696, 25, 2), //super combat pooition noted x25
                    new LootItem(5730, 1, 3) //dragon spear
            )
            .addTable(20,
                    new LootItem(1128, 5, 3), //Rune Platebody
                    new LootItem(4088, Random.get(2, 3), 3), //Dragon platelegs
                    new LootItem(22804, Random.get(50, 125), 3), // Dragon Knife
                    new LootItem(19484, Random.get(50, 125), 3), // Dragon Javelin
                    new LootItem(5300, 1, 3), //Snapdragon Seed
                    new LootItem(1514, Random.get(15, 20), 3), // Magic Logs
                    new LootItem(3024, 3, 2), // Super Restore(4)
                    new LootItem(3052, 3, 2) // Grimy Snapdragon
            )
            .addTable(10,
                    new LootItem(12877, 1, 2).broadcast(Broadcast.GLOBAL), // Dharok Armour set
                    new LootItem(12881, 1, 2).broadcast(Broadcast.GLOBAL), // Ahrims Armour set
                    new LootItem(12883, 1, 2).broadcast(Broadcast.GLOBAL), // Karils Armour set
                    new LootItem(12073, 1, 2) // Elite Clue scroll
            )
            .addTable(5,
                    new LootItem(30307, 3, 3).broadcast(Broadcast.GLOBAL), //Membership Tokens
                    new LootItem(30294, 10, 3).broadcast(Broadcast.GLOBAL), //Scratch Offs
                    new LootItem(11773, 3, 3).broadcast(Broadcast.GLOBAL), //Berserker Ring (i)
                    new LootItem(11770, 3, 3).broadcast(Broadcast.GLOBAL), //Seers Ring (i)
                    new LootItem(11771, 3, 3).broadcast(Broadcast.GLOBAL), //Archers Ring (i)
                    new LootItem(30308, 5, 2).broadcast(Broadcast.GLOBAL), //Donator Coin
                    new LootItem(30314, 1, 1).broadcast(Broadcast.GLOBAL) // Toxic Blood blowpipe (empty)
            )
            .addTable(2,
                    new LootItem(20724, 1, 2).broadcast(Broadcast.GLOBAL), //Imbued heart
                    new LootItem(13239, 1, 2).broadcast(Broadcast.GLOBAL), //Primordial Boots
                    new LootItem(13235, 1, 2).broadcast(Broadcast.GLOBAL), //Eternal Boots
                    new LootItem(13237, 1, 2).broadcast(Broadcast.GLOBAL), // Pegasian Boots
                    new LootItem(30319, 1, 1).broadcast(Broadcast.GLOBAL) // Colossal Blade
            );

    private static Item rollRegular() {
        return regularTable.rollItem();
    }
}
