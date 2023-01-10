package io.ruin.model.activities.raids.xeric;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.diaries.kourend.KourendDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.services.Loggers;
import io.ruin.utility.Broadcast;

public class XericRewards {

    static {
        ObjectAction.register(30028, "search", (player, obj) -> { // reward chest
            openRewards(player);
            player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.COX);
        });
        InterfaceHandler.register(Interface.RAID_REWARDS, h -> {
            h.actions[5] = (DefaultAction) (p, option, slot, itemId) -> {
                if (slot < 0 || slot >= p.getRaidRewards().getItems().length)
                    return;
                if (option == 1)
                    withdrawReward(p, slot);
                else {
                    Item item = p.getRaidRewards().get(slot);
                    if (item != null)
                        item.examine(p);
                }
            };
        });
    }

    private static void withdrawReward(Player p, int slot) {
        if (slot < 0 || slot >= p.getRaidRewards().getItems().length)
            return;
        Item item = p.getRaidRewards().get(slot);
        if (item == null)
            return;
        if (item.move(item.getId(), item.getAmount(), p.getInventory()) > 0) {
            p.getCollectionLog().collect(item);
            p.getRaidRewards().sendUpdates();
        } else {
            p.sendMessage("Not enough space in your inventory.");
        }
    }

    private static void openRewards(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAID_REWARDS);
        player.getRaidRewards().sendUpdates();
        new Unlock(Interface.RAID_REWARDS, 5, 0, 2).unlockMultiple(player, 0, 9);
    }

    public static LootTable uniqueTable = new LootTable()
            .addTable(1,
                    new LootItem(21034, 1, 20), // dexterous scroll
                    new LootItem(21079, 1, 20), // arcane scroll
                    new LootItem(21000, 1, 3), // twisted buckler
                    new LootItem(21012, 1, 3), // dragon hunter crossbow
                    new LootItem(21015, 1, 2), // dinh's bulwark
                    new LootItem(21018, 1, 2), // ancestral hat
                    new LootItem(21021, 1, 2), // ancestral top
                    new LootItem(21024, 1, 2), // ancestral bottom
                    new LootItem(13652, 1, 2), // dragon claws
                    new LootItem(30376, 1, 1), // lightbearer
                    new LootItem(21003, 1, 1), // elder maul
                    new LootItem(21043, 1, 1), // kodai insignia
                    new LootItem(20997, 1, 1), // twisted bow
                    new LootItem(30385, 1, 1), // elder maul
                    new LootItem(30386, 1, 1), // elder maul
                    new LootItem(30387, 1, 1), // elder maul
                    new LootItem(24444, 1, 2),
                    new LootItem(30324, 1, 4)
            );

    private static final LootTable regularTable = new LootTable() // regular table. the "amount" here is the number used to determine the amount given to players based on how many points they have, for example 1 soul rune per 20 points
            .addTable(1,
                    new LootItem(560, 20, 1), // death rune
                    new LootItem(565, 16, 1), // blood rune
                    new LootItem(566, 10, 1), // soul rune
                    new LootItem(892, 7, 1), // rune arrow
                    new LootItem(11212, 70, 1), // dragon arrow

                    new LootItem(3050, 185, 1), // grimy toadflax
                    new LootItem(208, 400, 1), // grimy ranarr weed
                    new LootItem(210, 98, 1), // grimy irit
                    new LootItem(212, 185, 1), // grimy avantoe
                    new LootItem(214, 205, 1), // grimy kwuarm
                    new LootItem(3052, 500, 1), // grimy snapdragon
                    new LootItem(216, 200, 1), // grimy cadantine
                    new LootItem(2486, 150, 1), // grimy lantadyme
                    new LootItem(218, 106, 1), // grimy dwarf weed
                    new LootItem(220, 428, 1), // grimy torstol
                    new LootItem(10506, 5, 50, 5),

                    // new LootItem(443, 20, 1), // silver ore
                    new LootItem(454, 20, 1), // coal
                    new LootItem(445, 45, 1), // gold ore
                    new LootItem(448, 45, 1), // mithril ore
                    new LootItem(450, 100, 1), // adamantite ore
                    new LootItem(452, 750, 1), // runite ore

                    new LootItem(1624, 100, 1), // uncut sapphire
                    new LootItem(1622, 170, 1), // uncut emerald
                    new LootItem(1620, 125, 1), // uncut ruby
                    new LootItem(1618, 260, 1), // uncut diamond

//                    new LootItem(13391, 25, 1), // lizardman fang
                    //new LootItem(7937, 200, 100), // pure essence
//                    new LootItem(13422, 24, 1), // saltpetre
                    new LootItem(8781, 100, 10), // teak plank
                    new LootItem(8783, 240, 10), // mahogany plank
//                    new LootItem(13574, 55, 1), // dynamite


                    new LootItem(21047, 131071, 1), // torn prayer scroll
                    new LootItem(21027, 131071, 1) // dark relic

            );

    public static void giveRewards(ChambersOfXeric raid) {
        raid.getParty().forPlayers(p -> p.getRaidRewards().clear()); // clear previous loot (if any)
        //uniques
        int uniqueBudget = raid.getParty().getPoints();
        int uniques = 0;
        for (int i = 0; i < 3; i++) { // up to 3 uniques
            if (uniqueBudget <= 0)
                break;
            int pointsToUse = Math.min(350000, uniqueBudget); // max of 350k points per unique attempt
            uniqueBudget -= pointsToUse;
            double chance = pointsToUse / 2000 / 100.0; // 1% chance per 4200 points - OSRS = 8675
            if (Random.get() < chance) {
                uniques++;
                Player lucker = getPlayerToReceiveUnique(raid);
                Item item = rollUnique();
                int amount = item.getAmount();
                if (World.doubleRaids)
                    amount++;
                lucker.getRaidRewards().add(item.getId(), amount);
                Loggers.logRaidsUnique(lucker.getName(), item.getDef().name, lucker.chambersofXericKills.getKills());
                Config.RAIDS_BEAM.set(lucker, 1);
                if (uniques >= 1) {
                    Config.RAIDS_BEAM.set(lucker, 2);
                    raid.getParty().forPlayers(p -> p.sendMessage(Color.RAID_PURPLE.wrap("Special loot:")));
                }
                Broadcast.GLOBAL.sendNews(Color.RED.wrap("[COX]-[WORLD" + World.id +  "] ") + lucker.getName() +
                        " received " + Color.DARK_RED.wrap(item.getDef().name) +
                        " from COX at KC: " + lucker.chambersofXericKills.getKills());
            }
        }
        //regular drops
        raid.getParty().getMembers().stream().filter(p -> p.getRaidRewards().isEmpty()).forEach(p -> {
            int playerPoints = Math.max(131071, Config.RAIDS_PERSONAL_POINTS.get(p));
            if (playerPoints == 0)
                return;
            if (p.lootit == true) {
                Item rolled2 = rollUnique();
                int amount = 1;
                p.getRaidRewards().add(rolled2.getId(), amount);
                Loggers.logRaidsUnique(p.getName(), rolled2.getDef().name, p.chambersofXericKills.getKills());
                Config.RAIDS_BEAM.set(p, 1);
                Broadcast.GLOBAL.sendNews(Color.RED.wrap("[COX]-[WORLD" + World.id +  "] ") + p.getName() +
                        " received " + Color.DARK_RED.wrap(rolled2.getDef().name) +
                        " from COX at KC: " + p.chambersofXericKills.getKills());
            }
            for (int i = 0; i < 2; i++) {
                Item rolled = rollRegular();
                double pointsPerItem = rolled.getAmount();
                int amount = (int) Math.ceil(playerPoints / pointsPerItem);
                rolled.setAmount(amount);
                if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                    rolled.setId(rolled.getDef().notedId);
                p.getRaidRewards().add(rolled);
            }
        });

    }

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollUnique() {
        return uniqueTable.rollItem();
    }


    private static Player getPlayerToReceiveUnique(ChambersOfXeric raid) {
        int roll = Random.get(raid.getParty().getPoints());
        for (Player player : raid.getParty().getMembers()) {
            roll -= Config.RAIDS_PERSONAL_POINTS.get(player);
            if (roll <= 0) {
                return player;
            }
        }
        return Random.get(raid.getParty().getMembers()); // shouldn't happen, but just in case
    }
}
