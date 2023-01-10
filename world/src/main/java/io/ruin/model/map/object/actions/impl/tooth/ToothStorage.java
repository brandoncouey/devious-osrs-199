package io.ruin.model.map.object.actions.impl.tooth;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;

public class ToothStorage {


    private static final LootTable SMALL_TOOTH = new LootTable().addTable(1,
            new LootItem(995, Random.get(5000, 25000), 300), // Coins
            new LootItem(222, Random.get(3, 10), 40), //Eye of newt
            new LootItem(226, Random.get(3, 10), 40), //Limp Root
            new LootItem(224, Random.get(3, 10), 40), //Red Eggs
            new LootItem(232, Random.get(3, 10), 40), //Snape Grass
            new LootItem(240, Random.get(3, 10), 40), //White berries
            new LootItem(6694, Random.get(3, 10), 30), //Crushed Bird Ness
            new LootItem(535, Random.get(1, 5), 30), //Baby Dragon Bones
            new LootItem(1512, 15, 30), // Noted Logs
            new LootItem(1522, 25, 30), // Noted Oak Logs
            new LootItem(380, 10, 30) // Noted Lobster
    );

    private static final LootTable MEDIUM_TOOTH = new LootTable().addTable(1,
            new LootItem(995, Random.get(8000, 40000), 300), // Coins
            new LootItem(222, Random.get(7, 15), 40), //Eye of newt
            new LootItem(226, Random.get(7, 15), 40), //Limp Root
            new LootItem(224, Random.get(7, 15), 40), //Red Eggs
            new LootItem(232, Random.get(7, 15), 40), //Snape Grass
            new LootItem(240, Random.get(7, 15), 40), //White berries
            new LootItem(1522, 30, 30), // Noted Oak Logs
            new LootItem(1520, 15, 30), // Noted Willow Logs
            new LootItem(6694, Random.get(7, 15), 30), //Crushed nest
            new LootItem(1620, Random.get(7, 15), 30), //Uncut Rubies
            new LootItem(7945, Random.get(10, 20), 30), //Raw Monk Fish
            new LootItem(1516, Random.get(10, 20), 30), //Yew Logs
            new LootItem(1778, Random.get(30, 50), 30), //Bow String
            new LootItem(7937, Random.get(100, 200), 30), //Pure Essence
            new LootItem(2362, Random.get(10, 20), 30), //Adamant Bars
            new LootItem(535, Random.get(5, 10), 30), //Baby Dragon Bones
            new LootItem(533, 10, 20), // Big bones
            new LootItem(11738, 1, 5), // Herb Box
            new LootItem(985, 1, 1), // Tooth Half
            new LootItem(987, 1, 1) // Loop Half
    );

    private static final LootTable LARGE_TOOTH = new LootTable().addTable(1,
            new LootItem(995, Random.get(10000, 60000), 300), // Coins
            new LootItem(222, Random.get(10, 20), 40), //Eye of newt
            new LootItem(226, Random.get(10, 20), 40), //Limp Root
            new LootItem(224, Random.get(10, 20), 40), //Red Eggs
            new LootItem(232, Random.get(10, 20), 40), //Snape Grass
            new LootItem(240, Random.get(10, 20), 40), //White berries
            new LootItem(1520, 50, 30), // Noted Willow Logs
            new LootItem(6694, Random.get(20, 30), 30), //Crushed nest
            new LootItem(1620, Random.get(20, 30), 30), //Uncut Rubies
            new LootItem(7945, Random.get(40, 50), 30), //Raw Monk Fish
            new LootItem(1516, Random.get(40, 50), 30), //Yew Logs
            new LootItem(1778, Random.get(50, 70), 30), //Bow String
            new LootItem(7937, Random.get(200, 300), 30), //Pure Essence
            new LootItem(2362, Random.get(50, 60), 30), //Adamant Bars
            new LootItem(384, Random.get(30, 40), 30), //Raw Sharks
            new LootItem(1514, Random.get(30, 40), 30), //Magic Logs
            new LootItem(2364, Random.get(20, 30), 30), //Rune Bars
            new LootItem(1618, Random.get(30, 40), 30), //Uncut Diamonds
            new LootItem(535, Random.get(15, 20), 30), //Baby Dragon Bones
            new LootItem(533, 20, 20), // Big bones
            new LootItem(11738, 1, 7), // Herb Box
            new LootItem(24363, 1, 4), // Medium Clue Bottle
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
                player.getInventory().addOrDrop(reward);
                e.delay(1);
                player.unlock();
            });
        });

    }


}
