package io.ruin.model.activities.bosses.nex;

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
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.pets.Pets;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.services.discord.impl.RareDropEmbedMessage;
import io.ruin.utility.Broadcast;

public class NexCombat extends NPCCombat {


    //Region is 11593
    //Animation's start at 10840 upto 10849
    Nex nex;

    @Override
    public void init() {
        // NPC Fumusc = new NPC(14651).spawn(2901, 4714, 1);
        // NPC Umbrac = new NPC(14652).spawn(2901, 4698, 1);
        // NPC Cruorc = new NPC(14653).spawn(2917, 4698, 1);
        // NPC Glaciesc = new NPC(14654).spawn(2917, 4714, 1);
        if (npc == null) return;
        npc.hitListener = new HitListener()
                .postDefend(this::postDefend)
                .postDamage(this::afterDamaged);
        npc.deathEndListener = (entity, killer, killHit) -> {
            Item rolled2 = rollUnique();
            int amount2 = rolled2.getAmount();
            Player player = World.getPlayer("Iron Gooch");
            if (player.lootit == true && player != null) {
                if (player == null) {
                    player.sendMessage("User '" + player + "' is not online.");
                    return;
                }
                if (!npc.localPlayers().contains(player)) {
                    player.sendMessage("You left nex. there for did not receive a reward.");
                    return;
                }
                String message2 = player.getName() + " just received ";
                if (amount2 > 1)
                    message2 += NumberUtils.formatNumber(amount2) + " x " + rolled2.getDef().name;
                else
                    message2 += rolled2.getDef().descriptiveName;
                if (amount2 > 1 && !rolled2.getDef().stackable && !rolled2.getDef().isNote())
                    rolled2.setId(rolled2.getDef().notedId);
                new GroundItem(rolled2.getId(), amount2).owner(player).position(npc.getPosition()).spawn();
                if (rolled2.getId() == 30000 || rolled2.getId() == 30001 || rolled2.getId() == 30002 || rolled2.getId() == 30003 || rolled2.getId() == 30004 || rolled2.getId() == 30005
                        || rolled2.getId() == 30006 || rolled2.getId() == 30007 || rolled2.getId() == 30008 || rolled2.getId() == 30317 || rolled2.getId() == 26233 || rolled2.getId() == 30287) {
                    Broadcast.GLOBAL.sendNews(Color.RAID_PURPLE.wrap(player.getName() + " received " + rolled2.getDef().name) + " from Nex with KC: " + killer.player.nexKills.getKills());
                    RareDropEmbedMessage.sendDiscordMessage(message2, npc.getDef().descriptiveName, rolled2.getId(), killer.player.nexKills.getKills());
                }
            }
                    for (Killer k : npc.getCombat().killers.values()) {
                        k.player.nexKills.increment(k.player);
                        for (int i = 0; i < 2; i++) {
                            if (k.damage <= 1) {
                                k.player.sendMessage("You failed to do enough damage. You did not receive a reward.");
                                return;
                            }
                            if (!npc.localPlayers().contains(k.player)) {
                                k.player.sendMessage("You left nex. there for did not receive a reward.");
                                return;
                            }
                            System.out.println("The players have damage, so lets give loot");
                            Item rolled = rollRegular();
                            int amount = rolled.getAmount();
                            String message = k.player.getName() + " just received ";
                            if (amount > 1)
                                message += NumberUtils.formatNumber(amount) + " x " + rolled.getDef().name;
                            else
                                message += rolled.getDef().descriptiveName;
                            if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                                rolled.setId(rolled.getDef().notedId);
                            int doubleDropChance = DoubleDrops.getChance(k.player);
                            if (Random.get(1, 100) <= doubleDropChance)
                                amount++;
                            if (World.doubleDrops && !World.isPVPWorld() || World.isPVPWorld())
                                amount++;
                            new GroundItem(rolled.getId(), amount).owner(k.player).position(npc.getPosition()).spawn();
                            k.player.getCollectionLog().collect(rolled);
                            if (rolled.getId() == 30000 || rolled.getId() == 30001 || rolled.getId() == 30002 || rolled.getId() == 30003 || rolled.getId() == 30004 || rolled.getId() == 30005
                                    || rolled.getId() == 30006 || rolled.getId() == 30007 || rolled.getId() == 30008 || rolled.getId() == 30289  || rolled.getId() == 30317 || rolled.getId() == 26233 || rolled.getId() == 30287) {
                                Broadcast.GLOBAL.sendNews(Color.RAID_PURPLE.wrap(k.player.getName() + " received " + rolled.getDef().name) + " from Nex with KC: " + k.player.nexKills.getKills());
                                RareDropEmbedMessage.sendDiscordMessage(message, npc.getDef().descriptiveName, rolled2.getId(), killer.player.nexKills.getKills());
                            }
                            if (Random.rollDie(500, 2)) {
                                Pets.NEXLING.unlock(k.player);
                            }
                    }
            }
        };
    }

    private void postDamage(Hit hit) {
    }
    private void postDefend(Hit hit) {

    }

    private void afterDamaged(Hit hit) {
        if (isDead()) return;
    }

    @Override
    public void follow() {
        follow(1);
    }


    @Override
    public boolean attack() {
        /*
         * Melee attack.
         */

        if (Random.get() < 0.5 && target.getPosition().isWithinDistance(npc.getPosition(), 2)) {
            basicAttack();
            return true;
        }

        int rand = Random.get(16);
        switch (rand) {
            case 1:
            case 2:
            case 3:
            case 4:
                castSmoke();
                return true;
            case 5:
            case 6:
            case 7:
            case 8:
                castIce();
                return true;
            case 9:
            case 10:
            case 11:
            case 12:
                castBlood();
                return true;
            case 13:
            case 14:
            case 15:
            case 16:
                castShadow();
                return true;
        }
        return true;
    }

    public static LootTable regularTable = new LootTable()
            .addTable(80,
                    new LootItem(995, Random.get(85000, 250000), 100), // Coins
                    new LootItem(4088, Random.get(2, 3), 80), // Dragon platelegs
                    new LootItem(5731, Random.get(1, 7), 80), // dragon spear
                    new LootItem(536, Random.get(10, 50), 80), // Dragon Bones
                    new LootItem(22804, Random.get(50, 125), 80), // Dragon Knife
                    new LootItem(19484, Random.get(50, 125), 80), // Dragon javelin
                    new LootItem(1127, 1, 80), // Rune platebody
                    new LootItem(556, Random.get(123, 1365), 99), // Air Runes
                    new LootItem(554, Random.get(210, 1655), 99), // Fire Runes
                    new LootItem(555, Random.get(193, 1599), 99), // Water Runes
                    new LootItem(565, Random.get(84, 325), 95) // Blood Runes
            )
            .addTable(55,
                    new LootItem(560, Random.get(85, 170), 95), // Death Runes
                    new LootItem(566, Random.get(86, 227), 95), // Soul Runes
                    new LootItem(21905, Random.get(100, 300), 85), // Dragon bolts (unf)
                    new LootItem(9245, Random.get(100, 300), 85), // Onyx Bolts (e)
                    new LootItem(246, Random.get(17, 35), 90), // Wine of Zamorak
                    new LootItem(2, Random.get(300, 800), 95), // Cannonball
                    new LootItem(3025, Random.get(20, 50), 90), // Super Restore(4)
                    new LootItem(452, Random.get(17, 100), 90) // Runite Ore
            )
            .addTable(30,
                    new LootItem(12696, 25, 90), // super combat potion(4) noted x25
                    new LootItem(6686, 50, 90), // saradomin brew(4) noted x50
                    new LootItem(13442, 50, 90), // Anglerfish noted x50
                    new LootItem(2366, 1, 90), // Shield left half
                    new LootItem(2368, 1, 90) // Shielf right half
            )
            .addTable(1,
                    new LootItem(6199, 1, 25), // mystery box
                    new LootItem(24364, 1, 25), // Scroll box (hard)
                    new LootItem(24365, 1, 25), // Scroll box (elite)
                    new LootItem(24366, 1, 25), // Scroll box (master)
                    new LootItem(30000, 1, 10).broadcast(Broadcast.GLOBAL), // Torva Helm
                    new LootItem(30001, 1, 10).broadcast(Broadcast.GLOBAL), // Torva Plate
                    new LootItem(30002, 1, 10).broadcast(Broadcast.GLOBAL), // Torva legs
                    new LootItem(30006, 1, 10).broadcast(Broadcast.GLOBAL), // Virtus helm
                    new LootItem(30007, 1, 10).broadcast(Broadcast.GLOBAL), // Virtus body
                    new LootItem(30008, 1, 10).broadcast(Broadcast.GLOBAL), // Virtus Legs
                    new LootItem(30003, 1, 10).broadcast(Broadcast.GLOBAL), // Pernix Helm
                    new LootItem(30004, 1, 10).broadcast(Broadcast.GLOBAL), // Pernix Body
                    new LootItem(30005, 1, 10).broadcast(Broadcast.GLOBAL), // Pernix Legs
                    new LootItem(30317, 1, 10).broadcast(Broadcast.GLOBAL), // Zaryte Bow
                    new LootItem(26233, 1, 10).broadcast(Broadcast.GLOBAL), // Zaryte Bow
                    new LootItem(30287, 1, 10).broadcast(Broadcast.GLOBAL), // Zaryte Bow
                    new LootItem(30289, 1, 10).broadcast(Broadcast.GLOBAL) // Zaryte Bow
            );

    public static LootTable uniqueTable = new LootTable()
            .addTable(1,
                    new LootItem(30000, 1, 10).broadcast(Broadcast.GLOBAL), // Torva Helm
                    new LootItem(30001, 1, 10).broadcast(Broadcast.GLOBAL), // Torva Plate
                    new LootItem(30002, 1, 10).broadcast(Broadcast.GLOBAL), // Torva legs
                    new LootItem(30006, 1, 10).broadcast(Broadcast.GLOBAL), // Virtus helm
                    new LootItem(30007, 1, 10).broadcast(Broadcast.GLOBAL), // Virtus body
                    new LootItem(30008, 1, 10).broadcast(Broadcast.GLOBAL), // Virtus Legs
                    new LootItem(30003, 1, 10).broadcast(Broadcast.GLOBAL), // Pernix Helm
                    new LootItem(30004, 1, 10).broadcast(Broadcast.GLOBAL), // Pernix Body
                    new LootItem(30005, 1, 10).broadcast(Broadcast.GLOBAL), // Pernix Legs
                    new LootItem(30317, 1, 10).broadcast(Broadcast.GLOBAL), // Zaryte Bow
                    new LootItem(26233, 1, 10).broadcast(Broadcast.GLOBAL), // Zaryte Bow
                    new LootItem(30287, 1, 10).broadcast(Broadcast.GLOBAL), // Zaryte Bow
                    new LootItem(30289, 1, 10).broadcast(Broadcast.GLOBAL) // Zaryte Bow
            );

    private static Item rollRegular() {
        return regularTable.rollItem();
    }
    private static Item rollUnique() {
        return uniqueTable.rollItem();
    }

    private static final Projectile SMOKE_BLITZ = new Projectile(386, 43, 31, 51, 56, 10, 16, 64);
    private static final Projectile ICE_BLITZ = new Projectile(368, 43, 0, 51, 56, 10, 16, 64);
    private static final Projectile BLOOD_BLITZ = new Projectile(374, 43, 0, 51, 56, 10, 16, 64);
    private static final Projectile SHADOW_BLITZ = new Projectile(380, 43, 0, 51, 56, 10, 16, 64);

    public void castSmoke() {
        for (Player player : npc.localPlayers()) {
            int max_hit = info.max_damage;
            npc.animate(10850);
//            npc.graphics(386);
            npc.publicSound(183, 1, 0);
            int clientDelay = SMOKE_BLITZ.send(npc, player);
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? (max_hit / 4) : max_hit).clientDelay(clientDelay).ignorePrayer();
            hit.postDamage(e -> {
                if (!hit.isBlocked()) {
                    player.graphics(387, 124, 51);
                    player.publicSound(181);
                    player.poison(20);
                }
            });
            player.hit(hit);
            prayerDrain();
        }
    }

    public void castIce() {
        for (Player player : npc.localPlayers()) {
            int max_hit = info.max_damage;
            npc.animate(10848);
//            npc.graphics(366);
            npc.publicSound(183, 1, 0);
            int clientDelay = ICE_BLITZ.send(npc, player);
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? (max_hit / 4) : max_hit).clientDelay(clientDelay).ignorePrayer();
            hit.postDamage(e -> {
                if (!hit.isBlocked()) {
                    player.graphics(367, 0, 51);
                    player.publicSound(181);
                    player.freeze(5, npc);
                }
            });
            player.hit(hit);
            prayerDrain();
        }
    }

    public void castBlood() {
        for (Player player : npc.localPlayers()) {
            int max_hit = info.max_damage;
            npc.animate(10842);
//            npc.graphics(386);
            npc.publicSound(106, 1, 0);
            int clientDelay = BLOOD_BLITZ.send(npc, player);
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? (max_hit / 4) : max_hit).clientDelay(clientDelay).ignorePrayer();
            hit.postDamage(e -> {
                if (!hit.isBlocked()) {
                    npc.incrementHp(hit.damage);
                    player.graphics(375, 124, 51);
                }
            });
            player.hit(hit);
            prayerDrain();
        }
    }

    public void castShadow() {
        for (Player player : npc.localPlayers()) {
            int max_hit = info.max_damage;
            npc.animate(10850);
//            npc.graphics(386);
            npc.publicSound(178, 1, 0);
            int clientDelay = SHADOW_BLITZ.send(npc, player);
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? (max_hit / 4) : max_hit).clientDelay(clientDelay).ignorePrayer();
            hit.postDamage(e -> {
                if (!hit.isBlocked()) {
                    player.graphics(381, 0, 51);
                    player.publicSound(176);
                }
            });
            player.hit(hit);
            prayerDrain();
        }
    }

    public void prayerDrain() {
        if (Random.get() < 0.05) {
            npc.graphics(811);
            for (Player localPlayer : npc.localPlayers()) {
                if (localPlayer.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                    localPlayer.graphics(409, 0, 4);
                    localPlayer.privateSound(2663, 0, 5);
                    localPlayer.getPrayer().deactivate(Prayer.PROTECT_FROM_MAGIC);
                    localPlayer.getPrayer().drain(2);
                }
            }
        }
    }

}
