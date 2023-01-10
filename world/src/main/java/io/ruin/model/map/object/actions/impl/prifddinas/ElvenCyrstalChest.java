package io.ruin.model.map.object.actions.impl.prifddinas;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

public class ElvenCyrstalChest {

    private static final Item[] COMMON_LOOT = {
            new Item(1632, 25), //Uncut dragon stones
            new Item(21905, 50), //Dragon bolts
            new Item(11230, 35), //Dragon dart
            new Item(811, 500), //Rune dart
            new Item(23962, 10), //Crystal shards
            new Item(990, 2), //Crystal keys
            new Item(1128, 5), //Rune platebody
            new Item(1080, 5), //Rune platelegs
            new Item(5316, 3), //Magic seed
            new Item(537, 50), //Dragon bones
            new Item(3025, 25), //Super restores
            new Item(6686, 25), //Saradomin Brews
            new Item(1392, 50), //Battlestaff
            new Item(570, 50), //Fire orb
            new Item(572, 50), //Water orb
            new Item(392, 100), //Manta ray
            new Item(3145, 150), //Cooked Karambwan
            new Item(2362, 100), //Adamant bars
            new Item(2364, 50), //Runite bars
            new Item(1988, 500), //Grapes
            new Item(5300, 10), //Snapdragon seed
            new Item(5295, 15), //Ranarr seed
            new Item(220, 10), //Grimy Torstol
            new Item(21880, 500), //Wrath rune
            new Item(24363, 5) //Medium clue reward casket
    };
    private static final Item[][] RUNE_LOOTS = {
            new Item[]{
                    new Item(560, 1000),
                    new Item(565, 1000),
                    new Item(566, 1000)
            }
    };
    private static final Item[] UNCOMMON_LOOT = {
            new Item(11840, 1), //Dragon boots
            new Item(8783, 100), //Mahogany planks
            new Item(11944, 50), //Lava dragon bones
            new Item(6739, 1), //Dragon axe
            new Item(12914, 10), //Anti venom+
            new Item(13442, 50), //Crystal shards
            new Item(4207, 1), //Dragon platelegs
            new Item(23962, 50), //Dragon chainbody
            new Item(3205, 3), //Dragon Haberd
            new Item(9193, 500), //Dragonstone bolt tips
            new Item(21905, 200), //Dragon bolts
            new Item(11230, 150), //Dragon darts
            new Item(19484, 100), //Dragon javelins
            new Item(24364, 5), //Hard clue reward casket
            new Item(12831, 1), //Blessed spirit shield
            new Item(2510, 150), //Black dragon leather
            new Item(21880, 1000), //Wrath runes
    };
    public static final Item[] RARE_LOOT = {
            new Item(6571, 1),//Uncut onyx
            new Item(11335, 1),//Dragon full helm
            new Item(2577, 1),//Ranger boots
            new Item(12598, 1),//Holy sandals
            new Item(23962, 100),//Crystal shards
            new Item(24365, 5),//Elite clue reward casket
            new Item(22125, 25),//Superior dragon bones
            new Item(23962, 1),//Crystal armour seed
            new Item(11230, 500),//Dragon dart
            new Item(21905, 500)//Dragon bolts
    };

    public static final Item[] VERY_RARE_LOOT = {
            new Item(24366, 1),//Master clue reward casket
            new Item(24034, 1),//Dragonstone full helm
            new Item(24037, 1),//Dragonstone platebody
            new Item(24040, 1),//Dragonstone platelegs
            new Item(24043, 1),//Dragonstone boots
            new Item(24046, 1)//Dragonstone gauntlets
    };

    static {
        ObjectAction.register(37342, 1, (player, obj) -> {
            Item enchancedKey = player.getInventory().findItem(23951);
            if (enchancedKey == null) {
                player.sendFilteredMessage("You need a Enhanced Crystal key to open this chest.");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                enchancedKey.remove(1);
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(36581);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                int amount = Random.get(25000, 75000);
                player.getInventory().addOrDrop(995, amount);
                player.sendMessage("Your Elven Crystal chest count is: <col=FF0000>" + (++player.elvenCrystalChestsOpened) + "</col>.");
                for (int i = 0; i <= 1; i++) {
                    if (Random.rollDie(400, 1)) {
                        /**
                         * Very rare loot
                         */
                        Item loot = VERY_RARE_LOOT[Random.get(VERY_RARE_LOOT.length - 1)];
                        player.getInventory().addOrDrop(loot.getId(), loot.getAmount());
                        if (loot.getAmount() > 1)
                            Broadcast.WORLD.sendNews(player.getName() + " just received " + loot.getDef().name + " from the Elven Crystal Chest!");
                    } else if (Random.rollDie(150, 1)) {
                        /**
                         * Rare loot
                         */
                        Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                        player.getInventory().addOrDrop(loot.getId(), loot.getAmount());
                        Broadcast.WORLD.sendNews(player.getName() + " just received " + loot.getDef().name + " from the Elven Crystal Chest!");
                    } else if (Random.rollDie(75, 1)) {
                        /**
                         * Uncommon loot
                         */
                        Item loot = UNCOMMON_LOOT[Random.get(UNCOMMON_LOOT.length - 1)];
                        player.getInventory().addOrDrop(loot.getId(), loot.getAmount());

                    } else if (Random.rollDie(50, 1)) {
                        /**
                         * Rune loot
                         */
                        Item[] loot = RUNE_LOOTS[0];
                        for (Item item : loot)
                            player.getInventory().addOrDrop(item.getId(), item.getAmount());

                    } else {
                        /**
                         * Common loot
                         */
                        Item loot = COMMON_LOOT[Random.get(COMMON_LOOT.length - 1)];
                        player.getInventory().addOrDrop(loot.getId(), loot.getAmount());

                    }
                }
                event.delay(1);
                player.unlock();
            });
        });
    }
}
