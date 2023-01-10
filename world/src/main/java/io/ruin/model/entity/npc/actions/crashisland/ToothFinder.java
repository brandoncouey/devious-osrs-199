package io.ruin.model.entity.npc.actions.crashisland;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;

public class ToothFinder {


    private static final LootTable SMALL_TOOTH = new LootTable().addTable(1,
            new LootItem(995, Random.get(5000, 25000), 100), // Coins
            new LootItem(222, Random.get(3, 10), 40), //Secondaries
            new LootItem(226, Random.get(3, 10), 40), //Secondaries
            new LootItem(224, Random.get(3, 10), 40), //Secondaries
            new LootItem(232, Random.get(3, 10), 40), //Secondaries
            new LootItem(240, Random.get(3, 10), 40), //Secondaries
            new LootItem(1512, 15, 30), // Noted Logs
            new LootItem(1522, 25, 30), // Noted Oak Logs
            new LootItem(380, 10, 30), // Noted Lobster
            new LootItem(23129, 1, 30) // Beginner Clue Bottle
    );

    private static final LootTable MEDIUM_TOOTH = new LootTable().addTable(1,
            new LootItem(995, Random.get(8000, 40000), 100), // Coins
            new LootItem(222, Random.get(7, 15), 40), //Secondaries
            new LootItem(226, Random.get(7, 15), 40), //Secondaries
            new LootItem(224, Random.get(7, 15), 40), //Secondaries
            new LootItem(232, Random.get(7, 15), 40), //Secondaries
            new LootItem(240, Random.get(7, 15), 40), //Secondaries
            new LootItem(1522, 30, 30), // Noted Oak Logs
            new LootItem(1520, 15, 30), // Noted Willow Logs
            new LootItem(533, 10, 20), // Big bones
            new LootItem(13648, 1, 15), // Easy Clue bottle
            new LootItem(11738, 1, 5), // Herb Box
            new LootItem(985, 1, 1), // Tooth Half
            new LootItem(987, 1, 1) // Loop Half
    );

    private static final LootTable LARGE_TOOTH = new LootTable().addTable(1,
            new LootItem(995, Random.get(10000, 60000), 100), // Coins
            new LootItem(222, Random.get(10, 20), 40), //Secondaries
            new LootItem(226, Random.get(10, 20), 40), //Secondaries
            new LootItem(224, Random.get(10, 20), 40), //Secondaries
            new LootItem(232, Random.get(10, 20), 40), //Secondaries
            new LootItem(240, Random.get(10, 20), 40), //Secondaries
            new LootItem(1520, 50, 30), // Noted Willow Logs
            new LootItem(533, 20, 20), // Big bones
            new LootItem(13649, 1, 10), // Medium Clue Bottle
            new LootItem(11738, 1, 7), // Herb Box
            new LootItem(985, 1, 3), // Tooth Half
            new LootItem(987, 1, 3) // Loop Half
    );

    public static Item reward;

    static {
        ObjectAction.register(25385, "open", (player, obj) -> {
            Item tooth = player.getInventory().findFirst(30258, 30259, 30260);
            if (tooth == null) {
                player.sendFilteredMessage("You need a tooth before you can open this magical chest.");
                return;
            }
            player.startEvent(e -> {
                player.lock();
                player.closeDialogue();
                player.privateSound(51);
                player.animate(536);
                World.startEvent(we -> {
                    obj.setId(25386);
                    we.delay(2);
                    obj.setId(25385);
                });
                if (tooth.getId() == 30258) {
                    reward = SMALL_TOOTH.rollItem();
                } else if (tooth.getId() == 30259) {
                    reward = MEDIUM_TOOTH.rollItem();
                } else if (tooth.getId() == 30260) {
                    reward = LARGE_TOOTH.rollItem();
                }
                tooth.remove(1);
                player.getInventory().add(reward);
                e.delay(1);
                player.unlock();
            });
        });

    }


}