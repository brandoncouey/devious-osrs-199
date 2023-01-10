package io.ruin.model.map.object.actions.impl.edgeville;


import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import io.ruin.utility.TimedList;

import java.util.concurrent.TimeUnit;

public class DailyRewardChest {

    public static int chest = 31944;
    private static final TimedList recentPlayers = new TimedList();
    private static final long LOYALTY_TIMER = TimeUnit.HOURS.toMillis(24);
    private static final long LOYALTY_SPREE_TIMER = TimeUnit.HOURS.toMillis(36);

    public static int[] rareLoots = {
            11808,
            11806,
            11804,
            11802,
            2577,
            2581,
            6585,
            4151,
            4151,
            2528,
            23071,
            6737,
            6585,
            4151,
            2528,
            23071,
            23071
    };

    public static int[] ultraLoots = {
            2528,
            23071,
            2528,
            2581,
            12785,
            23071,
            2528,
            23071,
            23071,
            6739
    };

    public static int[] amazingLoots = {
            2528,
            2801,
            12785,
            2801,
            23071,
            2528
    };

    public static int[] commonLoots = {
            2677,
            2581,
            12785,
            23071
    };


    private static final LootTable DAILY_REWARDS_CHEST_TABLE = new LootTable().addTable(1,
            /*
            Rares 1/20 chance
             */
            new LootItem(995, 250000, 500000, 4),
            new LootItem(11808, 1, 4),
            new LootItem(11806, 1, 4),
            new LootItem(11804, 1, 4),
            new LootItem(11802, 1, 4),
            new LootItem(2577, 1, 4),
            new LootItem(2581, 1, 4),
            new LootItem(6585, 1, 4),
            new LootItem(4151, 1, 4),
            new LootItem(4151, 1, 4),
            new LootItem(2528, 1, 4),
            new LootItem(23071, 1, 4),
            new LootItem(6737, 1, 4),
            new LootItem(6585, 1, 4),
            new LootItem(4151, 1, 4),
            new LootItem(2528, 1, 4),
            new LootItem(23071, 1, 4),
            new LootItem(23071, 1, 4),

            /*
            Ultra Rares 1/147 chance
             */
            new LootItem(995, 500000, 750000, 3),
            new LootItem(2528, 1, 3),
            new LootItem(23071, 1, 3),
            new LootItem(2528, 1, 3),
            new LootItem(2581, 1, 3),
            new LootItem(12785, 1, 3),
            new LootItem(23071, 1, 3),
            new LootItem(2528, 1, 3),
            new LootItem(995, 3000000, 3),
            new LootItem(23071, 1, 3),
            new LootItem(6739, 1, 3),


            /*
            Amazing Loots 1/388 chance
             */
            new LootItem(995, 750000, 1500000, 2),
            new LootItem(2528, 1, 2),
            new LootItem(23071, 1, 2),
            new LootItem(2528, 1, 2),
            new LootItem(2801, 1, 2),
            new LootItem(12785, 1, 2),
            new LootItem(2801, 1, 2),
            new LootItem(995, 1000000, 10),
            new LootItem(23071, 1, 2),
            new LootItem(2528, 1, 2),



            /*
            Common Loots
             */
            new LootItem(995, 125000, 250000, 10),
            new LootItem(2677, 1, 10),
            new LootItem(6199, 1, 10),
            new LootItem(995, 500000, 10),
            new LootItem(12785, 1, 10),
            new LootItem(23071, 1, 10)
    );

    private static void showLoot(Player player) {
        //player.sendMessage(Color.DARK_GREEN, "<img=15> Showing Daily Login Chest rewards...");
        /**
         * sends the drop table interface
         */
        // player.openInterface(InterfaceType.MAIN, Bestiary.DROP_TABLE_INTERFACE);

        /**
         * sends clientscript #id 10004, handles the popular searches on the left hand side
         * params are int, string "is", amount of text, text seperated by a divider |.
         */
        player.getPacketSender().sendClientScript(10004, "is", 0, "");
        //   player.getPacketSender().sendClientScript(10004, "is", 10, "Abyssal Demon|Green Dragon|Cow|Goblin|Elvarg|General Graa'dor|Kree'arra|Vorkath|Zulrah|Great olm");

        /**
         * gets the npc's loot table and checks / verifies the items to then display onto the interface.
         */
        LootTable table = DAILY_REWARDS_CHEST_TABLE;

        table.calculateWeight();

        int index = 0;
        for (LootTable.ItemsTable it : table.tables) {
            for (LootItem tableItem : it.items) {
                /**
                 * drop weight percentage
                 */
                double percentage = (it.weight / table.totalWeight) * (tableItem.weight / it.totalWeight) * 100;
                /**
                 * sends clientscript #id 10006, paramTypes: int int int string string string, gets the container index, item ids + drops and formating them.
                 */
                player.getPacketSender().sendClientScript(10006, "iiisss", index, tableItem.id, tableItem.max, tableItem.max + "", String.format("%.2f", percentage) + "%", "");
                index++;
            }
        }

        /**
         * sends clientscript 10085, handling of component 25 & 26 for interface 801.
         */
        //player.getPacketSender().sendClientScript(10085, "iii", (Bestiary.DROP_TABLE_INTERFACE << 16) | 25, (Bestiary.DROP_TABLE_INTERFACE << 16) | 26, index * 40);

    }

    /*
     * Start methods below
     */

    /*
     * Grabs a random item from aray
     */
    public static int getRandomItem(int[] array) {
        return array[Random.get(array.length - 1)];
    }

    static {
        ObjectAction.register(31944, "search", (player, obj) -> openChest(player));
        ObjectAction.register(31944, "examine", (player, obj) -> showLoot(player));
        ObjectAction.register(31944, 4, (player, obj) -> showLoot(player));
    }

    private static String getRemainingTime(Player player) {
        long ms = player.loyaltyTimer - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        return (hours >= 1 ? (hours + " hour" + (hours > 1 ? "s" : "") + " and ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " minute" +
                ((minutes - TimeUnit.HOURS.toMinutes(hours)) > 1 ? "s" : "");
    }

    /*
     * Opening the chest with suspense, give reward
     */
    public static void openChest(Player player) {
        if (player.loyaltyTimer > System.currentTimeMillis()) {
            player.dialogue(new ItemDialogue().one(744, "You have to wait " + Color.DARK_RED.wrap(getRemainingTime(player)) + " until you're able to open the chest again.").lineHeight(24));
            return;
        }
        if (recentPlayers.contains(player.getIpInt(), System.currentTimeMillis(), 720L)) {
            player.dialogue(new ItemDialogue().one(744, "You have already claimed your reward in the past 24 hours.").lineHeight(24));
            return;
        }
        if (!player.getInventory().hasFreeSlots(1)) {
            player.dialogue(new ItemDialogue().one(744, "You need at least 1 free inventory slot to claim your rewards."));
            return;
        }
        player.lock();
        player.animate(6387);
        player.addEvent(event -> {
            event.setCancelCondition(() -> player.getCombat().isDead());
            event.delay(3);
            player.loyaltyTimer = System.currentTimeMillis() + LOYALTY_TIMER;
            player.loyaltySpreeTimer = System.currentTimeMillis() + LOYALTY_SPREE_TIMER;
            recentPlayers.add(player.getIpInt(), System.currentTimeMillis());
            giveReward(player);
            player.unlock();
        });
    }

    public static void giveReward(Player player) {
/*        if(!player.getAchievements().isComplete(AchievementTier.MEDIUM.ordinal(), Achievements.Achievement.LOYALTY.getId())) {
            player.getAchievements().setComplete(AchievementTier.MEDIUM.ordinal(), Achievements.Achievement.LOYALTY.getId(), true);
            player.getInventory().addOrBank(22521, 1);
        }*/
        if (Random.get(20) == 5) { //Rare Item
            int rareDrops = getRandomItem(rareLoots);
            player.getInventory().add(rareDrops, 1);
            Broadcast.WORLD.sendNews("" + Misc.capitalize(player.getName()) + " has received a rare item from the Daily Chest: " + ItemDef.get(rareDrops).descriptiveName + "!");
            player.dialogue(new ItemDialogue().one(rareDrops, "You received " + (ItemDef.get(rareDrops) != null ? ItemDef.get(rareDrops).descriptiveName : "an item") + " from the Daily Rewards Chest!"));
        } else if (Random.get(225) == 147) {//Ultra Rare items
            Broadcast.WORLD.sendNews("" + Misc.capitalize(player.getName()) + " has received a very rare item from the Daily Rewards Chest!");
            int ultraDrops = getRandomItem(ultraLoots);
            player.getInventory().add(ultraDrops, 1);
            player.dialogue(new ItemDialogue().one(ultraDrops, "You received " + (ItemDef.get(ultraDrops) != null ? ItemDef.get(ultraDrops).descriptiveName : "an item") + " from the Daily Rewards Chest!"));
        } else if (Random.get(400) == 388) {//Amazing items
            Broadcast.WORLD.sendNews("" + Misc.capitalize(player.getName()) + " has received an epic item from the Daily Rewards Chest!");
            int amazingDrops = getRandomItem(amazingLoots);
            player.getInventory().add(amazingDrops, 1);
            player.dialogue(new ItemDialogue().one(amazingDrops, "You received " + (ItemDef.get(amazingDrops) != null ? ItemDef.get(amazingDrops).descriptiveName : "an item") + " from the Daily Rewards Chest!"));
        } else {//Common items
            int commonDrops = getRandomItem(commonLoots);
            player.getInventory().add(commonDrops, 1);
            player.dialogue(new ItemDialogue().one(commonDrops, "You received " + (ItemDef.get(commonDrops) != null ? ItemDef.get(commonDrops).descriptiveName : "an item") + " from the Daily Rewards Chest!"));
        }

    }
}