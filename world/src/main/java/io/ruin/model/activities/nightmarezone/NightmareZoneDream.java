package io.ruin.model.activities.nightmarezone;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.ruin.process.event.EventWorker.startEvent;

public final class NightmareZoneDream {

    // Dream vial:
    // Proceed.
    // Not just now.

    private static final Position START = new Position(2275, 4680, 0);

    private static final Position EXIT = new Position(2608, 3115, 0);

    /* As far as I know the NMZ monsters can spawn pretty much anywhere in the arena, although the arena is not a perfect square. */
    private static final Bounds SPAWN_BOUNDS = new Bounds(2256, 4680, 2287, 4711, 0);

    private static final List<Integer> ABSORPTION_POTION = Arrays.asList(11734, 11735, 11736, 11737);

    private static final int[] NORMAL_MONSTERS = { // Add Attributes
            102, // Rock Crabs
            5935, // Sand Crabs
            5816, // Yaks
            2791, // Cows
            695, // Bandits
            6386, // Moss Giant
            421, // Rock Slugs
            480, // Cave Slime
            658, // Goblins
            4501, // Brine Rats
            6357, // Black Demon
    };

    private static final int[] HARD_MONSTERS = { // Add Attributes
            6740, // Shade
            6741, // Zombie MISSING COMBAT SCRIPT
            2527, // Ghost
            2515, // Ankou
            2916, // Waterfiend
            1539, // Skeleton Warlord
            5565, // Barbarian Spirit
            1541, // Skeleton Thug
            249, // Red Dragon
            4005, // Dark Beast
            6295, // Black Demon
            498, // Smoke Devils
    };

    private final Player player;

    private final NightmareZoneDreamDifficulty difficulty;

    private DynamicMap map;

    private int npcsRemaining;

    private int rewardPointsGained;

    public NightmareZoneDream(Player player, NightmareZoneDreamDifficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
    }

    public static void check(Player player, Hit hit) {
        if (player.absorptionPoints > 0 && player.get("nmz") != null && player.nmz) {
            if (hit.damage > 0 && !hit.absorptionIgnored) {
                if (hit.damage > player.getHp())
                    player.absorptionPoints = Math.max(0, player.absorptionPoints - player.getHp());
                else
                    player.absorptionPoints = Math.max(0, player.absorptionPoints - hit.damage);
                hit.block();
                Config.NMZ_ABSORPTION.set(player, player.absorptionPoints);
                player.sendMessage(Color.DARK_GREEN.wrap("You now have " + player.absorptionPoints + " hitpoints of damage absorption left."));
            }
        }
    }

    public void enter() {
        map = new DynamicMap().build(9033, 0);
        player.absorptionPoints = 0;

        player.nmz = true;
        player.deathEndListener = (DeathListener.Simple) () -> {
            player.getMovement().teleport(EXIT);
            player.getPacketSender().fadeIn();
            player.absorptionPoints = 0;
            player.sendMessage("You wake up feeling refreshed.");
            player.nmzRewardPoints += rewardPointsGained;
            player.sendMessage(Color.DARK_GREEN.wrap("You have earned " + NumberUtils.formatNumber(rewardPointsGained) + " reward points. New total: " + NumberUtils.formatNumber(player.nmzRewardPoints)));
            player.set("nmz", null);
            player.teleportListener = null;
            player.deathEndListener = null;
        };

        player.teleportListener = p -> {
            p.sendMessage("Drink from the vial at the south of the arena to wake up.");
            return false;
        };

        player.set("nmz", this);
        prepareMap();

        AtomicBoolean spawned = new AtomicBoolean(false);
        player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(map.convertPosition(START));
            event.delay(1);
            player.getPacketSender().fadeIn();
            prepareInterface();
            player.sendMessage("Welcome to The Nightmare Zone.");
            player.unlock();
        });
        startEvent(e -> {
            while (!spawned.get()) {
                e.delay(30);
                spawned.set(true);
                spawnMonsters();
            }
        });

    }

    private void prepareMap() {
        /* Remove KBD stalagmite, add dream potion */
        GameObject potion = new GameObject(26276, map.convertX(2276), map.convertY(4679), 0, 10, 0);
        Tile.getObject(12576, map.convertX(2276), map.convertY(4679), 0).setId(26267);
        Tile.get(map.convertX(2276), map.convertY(4679), 0).addObject(potion.spawn());

        /* Remove KBD lever */
        Tile.getObject(1817, map.convertX(2271), map.convertY(4680), 0).remove();
    }

    private void prepareInterface() {
        player.openInterface(InterfaceType.SECONDARY_OVERLAY, 202);
        int hash = map.convertY(4680) + (map.convertX(2256) << 14);
        player.getPacketSender().sendClientScript(255, "cs", hash, "");
    }


    public static int npccount;

    private void spawnMonsters() {
        if (map == null) {
            System.out.println("oh no");return;
        }
        if (difficulty == NightmareZoneDreamDifficulty.HARD) {
            npccount = 10;
        } else {
            npccount = 4;
        }
        for (int i = 0; i < npccount; i++) {
            NPC npc = new NPC(randomMonster());

            Position spawn = map.convertPosition(SPAWN_BOUNDS.randomPosition());
            npc.spawn(spawn);
            map.addNpc(npc);
            npc.face(player);

            npc.deathEndListener = (DeathListener.Simple) () -> {
                rewardPointsGained += Random.get(100, 250) * (difficulty == NightmareZoneDreamDifficulty.NORMAL ? 1.0 : 5);
                Config.NMZ_POINTS.set(player, rewardPointsGained);
                npcsRemaining--;
                map.removeNpc(npc);
                if (npcsRemaining == 0) {
                    spawnMonsters();
                }
            };

            npc.getCombat().setAllowRespawn(false);
            npc.targetPlayer(player, false);
            npc.attackTargetPlayer();

            npcsRemaining++;
        }
    }

    private int randomMonster() {
        return Random.get(difficulty == NightmareZoneDreamDifficulty.NORMAL ? NORMAL_MONSTERS : HARD_MONSTERS);
    }

    private void end(boolean logout) {
        player.nmzRewardPoints += rewardPointsGained;

        if (!logout) {
            player.getPacketSender().fadeIn();
            player.sendMessage("You wake up feeling refreshed.");
            player.sendMessage(Color.DARK_GREEN.wrap("You have earned " + NumberUtils.formatNumber(rewardPointsGained) + " reward points. New total: " + NumberUtils.formatNumber(player.nmzRewardPoints)));
        }
        player.nmz = false;
        player.set("nmz", null);
        player.absorptionPoints = 0;
        player.teleportListener = null;
        player.deathEndListener = null;
        dispose();
    }

    private void dispose() {
        map.destroy();
        map = null;
    }

    static {
        ABSORPTION_POTION.forEach(id -> {
            ItemAction.registerInventory(id, "drink", NightmareZoneDream::drinkAbsorption);
        });
        ObjectAction.register(26276, 1, (player, obj) -> {
            player.getMovement().teleport(EXIT);
            NightmareZoneDream dream = player.get("nmz");
            dream.end(false);
        });
    }

    public static void drinkAbsorption(Player player, Item item) {
        if (player.get("nmz") == null) {
            player.sendMessage("Absorption Potions can only be drunk in your dreams...");
            return;
        }
        if (player.absorptionPoints >= 1000) {
            player.sendMessage("You can't absorb anymore.");
            return;
        }
        if (player.potDelay.isDelayed())
            return;
        item.setId(item.getId() == 11737 ? 229 : item.getId() + 1);
        player.animate(829);
        player.privateSound(2401);
        player.absorptionPoints = player.absorptionPoints + 50;
        Config.NMZ_ABSORPTION.set(player, player.absorptionPoints);
        player.sendMessage(Color.DARK_GREEN.wrap("You now have " + player.absorptionPoints + " hitpoints of damage absorption left."));
        player.potDelay.delay(1);
    }
}