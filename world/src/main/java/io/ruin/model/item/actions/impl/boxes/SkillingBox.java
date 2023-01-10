package io.ruin.model.item.actions.impl.boxes;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.stat.StatType;

/**
 * @author Greco
 * @since 01/11/2021
 */
public class SkillingBox {

    public static int SKILLING_BOX = 6828;

    public static final LootTable table = new LootTable().addTable(1,
            new LootItem(1516, Random.get(50, 250), 15),   //Yew Logs Noted
            new LootItem(1514, Random.get(50, 200), 9),   //Magic Logs Noted
            new LootItem(8781, Random.get(30, 200), 20),   //Teak Planks Noted
            new LootItem(8783, Random.get(30, 200), 9),   //Mahogany Planks Noted
            new LootItem(450, Random.get(50, 200), 14),   //Adamantite ores Noted
            new LootItem(452, Random.get(30, 100), 7),   //Runite ores Noted
            new LootItem(384, Random.get(50, 200), 8),   //Raw Sharks Noted
            new LootItem(7945, Random.get(50, 200), 13),   //Raw Monkfish Noted
            new LootItem(13440, Random.get(50, 200), 5),   //Raw Anglerfish Noted
            new LootItem(1618, Random.get(30, 100), 14),   //Uncut diamond Noted
            new LootItem(1632, Random.get(30, 100), 7),   //Uncut dragonstone Noted
            new LootItem(6571, 1, 2)   //Uncut Onyx
    );

    public static final LootTable tablePrayer = new LootTable().addTable(1,
            new LootItem(537, Random.get(50, 250), 15), //dragon bones noted
            new LootItem(535, Random.get(50, 300), 20), //baby dragon bones noted
            new LootItem(6730, Random.get(50, 200), 8), //dagannoth bones noted
            new LootItem(22781, Random.get(50, 200), 6), //wyrm bones noted
            new LootItem(22125, Random.get(50, 200), 4) //superior dragon bones noted
    );

    public static final LootTable tableHunter = new LootTable().addTable(1,
            new LootItem(10033, Random.get(50, 300), 15), //chinchompa
            new LootItem(10034, Random.get(50, 250), 5), //red chimchompa
            new LootItem(11959, Random.get(50, 150), 8) //black chinchompa
    );

    public static final LootTable tableMining = new LootTable().addTable(1,
            new LootItem(441, Random.get(50, 500), 20), //iron ore
            new LootItem(443, Random.get(50, 500), 20), //silver ore
            new LootItem(445, Random.get(50, 500), 20), //gold ore
            new LootItem(454, Random.get(50, 500), 20), //coal
            new LootItem(448, Random.get(50, 400), 15), //mithril ore
            new LootItem(450, Random.get(50, 300), 11), //addy ore
            new LootItem(452, Random.get(50, 200), 5) //runite ore
    );

    public static final LootTable tableAgility = new LootTable().addTable(1,
            new LootItem(11849, Random.get(5, 25), 15), //mark of grace
            new LootItem(12640, Random.get(20, 100), 7) //amylase crystal
    );

    public static final LootTable tableCooking = new LootTable().addTable(1,
            new LootItem(378, Random.get(50, 300), 22), //raw lobster noted
            new LootItem(372, Random.get(50, 300), 17), //raw swordfish noted
            new LootItem(7945, Random.get(50, 300), 13), //raw monkfish noted
            new LootItem(384, Random.get(50, 300), 9), //raw shark noted
            new LootItem(390, Random.get(50, 200), 6), //raw manta ray noted
            new LootItem(396, Random.get(50, 200), 6), //raw sea turtle noted
            new LootItem(11935, Random.get(50, 200), 7), //raw dark crab noted
            new LootItem(13440, Random.get(50, 200), 4) //raw anglerfish noted
    );

    public static final LootTable tableFarming = new LootTable().addTable(1,
            new LootItem(5295, Random.get(3, 10), 10), //ranarr seed
            new LootItem(5296, Random.get(3, 10), 13), //toadflax seed
            new LootItem(5297, Random.get(3, 10), 15),//irit seed
            new LootItem(5298, Random.get(3, 10), 14), //avantoe seed
            new LootItem(5299, Random.get(3, 10), 12), //kwuarm seed
            new LootItem(5300, Random.get(3, 10), 8), //snapdragon seed
            new LootItem(5301, Random.get(3, 10), 10), //cadantine seed
            new LootItem(5302, Random.get(3, 10), 7), //lantadyme seed
            new LootItem(5303, Random.get(3, 10), 8), //dwarf weed seed
            new LootItem(5304, Random.get(3, 10), 6), //torstol seed seed
            new LootItem(5315, Random.get(3, 10), 5), //yew seed
            new LootItem(5316, Random.get(3, 10), 3), //magic seed
            new LootItem(5314, Random.get(3, 10), 11) //maple seed
    );

    public static final LootTable tableFishing = new LootTable().addTable(1,
            new LootItem(378, Random.get(50, 300), 22), //raw lobster noted
            new LootItem(372, Random.get(50, 300), 17), //raw swordfish noted
            new LootItem(7945, Random.get(50, 300), 13), //raw monkfish noted
            new LootItem(384, Random.get(50, 300), 9), //raw shark noted
            new LootItem(390, Random.get(50, 200), 6), //raw manta ray noted
            new LootItem(396, Random.get(50, 200), 6), //raw sea turtle noted
            new LootItem(11935, Random.get(50, 200), 7), //raw dark crab noted
            new LootItem(13440, Random.get(50, 200), 4) //raw anglerfish noted
    );

    public static final LootTable tableCrafting = new LootTable().addTable(1,
            new LootItem(1754, Random.get(30, 150), 18), //green dragonhide
            new LootItem(1752, Random.get(30, 150), 13), //blue dragonhide
            new LootItem(1750, Random.get(30, 150), 13), //red dragonhide
            new LootItem(1748, Random.get(30, 150), 8), //black dragonhide
            new LootItem(1618, Random.get(20, 100), 11), //uncut diamond
            new LootItem(1620, Random.get(20, 100), 15), //uncut ruby
            new LootItem(1622, Random.get(20, 100), 18), //uncut emerald
            new LootItem(1625, Random.get(20, 100), 22), //uncut sapphire
            new LootItem(1632, Random.get(20, 100), 6), //uncut dragonstone
            new LootItem(6571, 1, 2) //uncut onyx
    );

    public static final LootTable tableHerblore = new LootTable().addTable(1,
            new LootItem(208, Random.get(20, 50), 10), //grimy ranarr weed
            new LootItem(210, Random.get(20, 50), 13), //grimy irit leaf
            new LootItem(212, Random.get(20, 50), 12), //grimy avantoe
            new LootItem(214, Random.get(20, 50), 11), //grimy kwuarm
            new LootItem(216, Random.get(20, 50), 10), //grimy cadentine
            new LootItem(218, Random.get(20, 50), 9), //grimy dwarf weed
            new LootItem(220, Random.get(20, 50), 6), //grimy torstol
            new LootItem(2486, Random.get(20, 50), 9) //grimy lantadyme
    );

    public static final LootTable tableSmithing = new LootTable().addTable(1,
            new LootItem(2354, Random.get(50, 250), 16), //steel bar
            new LootItem(2360, Random.get(50, 200), 14), //mithril bar
            new LootItem(2362, Random.get(50, 200), 11), //addy bar
            new LootItem(2364, Random.get(50, 100), 8), //runite bar
            new LootItem(2358, Random.get(50, 300), 18) //gold bar
    );

    public static final LootTable tableFletching = new LootTable().addTable(1,
            new LootItem(1520, Random.get(100, 500), 20), //willow log
            new LootItem(1518, Random.get(100, 500), 15), //maple log
            new LootItem(1516, Random.get(100, 300), 10), //yew log
            new LootItem(1514, Random.get(75, 200), 7),//magic log
            new LootItem(19670, Random.get(50, 200), 3)//redwood log
    );

    public static final LootTable tableFiremaking = new LootTable().addTable(1,
            new LootItem(1520, Random.get(100, 500), 20), //willow log
            new LootItem(1518, Random.get(100, 500), 15), //maple log
            new LootItem(1516, Random.get(100, 300), 10), //yew log
            new LootItem(1514, Random.get(75, 200), 7), //magic log
            new LootItem(19670, Random.get(50, 200), 3)//redwood log
    );

    public static final LootTable tableWoodcutting = new LootTable().addTable(1,
            new LootItem(1520, Random.get(100, 500), 20), //willow log
            new LootItem(1518, Random.get(100, 500), 15), //maple log
            new LootItem(1516, Random.get(100, 300), 10), //yew log
            new LootItem(1514, Random.get(75, 200), 7), //magic log
            new LootItem(19670, Random.get(50, 200), 3)//redwood log
    );

    public static final LootTable tableConstruction = new LootTable().addTable(1,
            new LootItem(8779, Random.get(50, 150), 20), //oak plank
            new LootItem(8780, Random.get(40, 150), 15),//teak plank
            new LootItem(8783, Random.get(30, 125), 10)//mahog plank
    );

    public static final LootTable tableRunecrafting = new LootTable().addTable(1,
            new LootItem(7937, Random.get(2000, 10000), 18), //pure essence
            new LootItem(564, Random.get(250, 1000), 13), //cosmic rune
            new LootItem(565, Random.get(250, 1000), 7), //blood rune
            new LootItem(566, Random.get(250, 1000), 5), //soul rune
            new LootItem(561, Random.get(250, 1000), 11), //nature rune
            new LootItem(560, Random.get(250, 1000), 9) //death rune
    );

    public static void openPrayerBox(Player player) {

        int prayer = tablePrayer.rollItem().getId();

        if (prayer == 537) {
            player.getInventory().addOrDrop(prayer, Random.get(50, 250));
        } else if (prayer == 535) {
            player.getInventory().addOrDrop(prayer, Random.get(50, 300));
        } else if (prayer == 6730) {
            player.getInventory().addOrDrop(prayer, Random.get(50, 200));
        } else if (prayer == 22781) {
            player.getInventory().addOrDrop(prayer, Random.get(50, 200));
        } else if (prayer == 22125) {
            player.getInventory().addOrDrop(prayer, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training prayer, you find a prayer box and it contains: " + ItemDef.get(prayer).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(prayer).name + " was dropped to the floor.");
        }

    }

    public static void open(Player player, Item skillingBox) {

        int skillBox = table.rollItem().getId();

        if (skillBox == 1516) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 250));
        } else if (skillBox == 1514) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 8781) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 200));
        } else if (skillBox == 8783) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 200));
        } else if (skillBox == 450) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 452) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 100));
        } else if (skillBox == 384) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 7945) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 13440) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 1618) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 100));
        } else if (skillBox == 1632) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 100));
        } else if (skillBox == 6571) {
            player.getInventory().addOrDrop(skillBox, 1);
        }

        skillingBox.remove(1);

        player.sendMessage("You open the skilling box and find " + ItemDef.get(skillBox).name + ".");

    }

    public static void open(Player player) {

        int skillBox = table.rollItem().getId();

        if (skillBox == 1516) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 250));
        } else if (skillBox == 1514) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 8781) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 200));
        } else if (skillBox == 8783) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 200));
        } else if (skillBox == 450) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 452) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 100));
        } else if (skillBox == 384) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 7945) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 13440) {
            player.getInventory().addOrDrop(skillBox, Random.get(50, 200));
        } else if (skillBox == 1618) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 100));
        } else if (skillBox == 1632) {
            player.getInventory().addOrDrop(skillBox, Random.get(30, 100));
        } else if (skillBox == 6571) {
            player.getInventory().addOrDrop(skillBox, 1);
        }
        player.sendMessage("<col=8B0000>While skilling, you find a skilling box and it contains: " + ItemDef.get(skillBox).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(skillBox).name + " was dropped to the floor.");
        }

    }

    public static void openHunterBox(Player player) {
        int hunter = tableHunter.rollItem().getId();
        if (hunter == 10033) {
            player.getInventory().addOrDrop(hunter, Random.get(50, 300));
        } else if (hunter == 10034) {
            player.getInventory().addOrDrop(hunter, Random.get(50, 250));
        } else if (hunter == 11959) {
            player.getInventory().addOrDrop(hunter, Random.get(50, 150));
        }

        player.sendMessage("<col=8B0000>While training hunter, you find a hunter box and it contains: " + ItemDef.get(hunter).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(hunter).name + " was dropped to the floor.");
        }
    }

    public static void openMiningBox(Player player) {

        int mining = tableMining.rollItem().getId();

        if (mining == 441) {
            player.getInventory().addOrDrop(mining, Random.get(50, 500));
        } else if (mining == 443) {
            player.getInventory().addOrDrop(mining, Random.get(50, 500));
        } else if (mining == 454) {
            player.getInventory().addOrDrop(mining, Random.get(50, 500));
        } else if (mining == 448) {
            player.getInventory().addOrDrop(mining, Random.get(50, 400));
        } else if (mining == 450) {
            player.getInventory().addOrDrop(mining, Random.get(50, 300));
        } else if (mining == 452) {
            player.getInventory().addOrDrop(mining, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training mining, you find a mining box and it contains: " + ItemDef.get(mining).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(mining).name + " was dropped to the floor.");
        }
    }

    public static void openAgilityBox(Player player) {

        int agility = tableAgility.rollItem().getId();

        if (agility == 11849) {
            player.getInventory().addOrDrop(agility, Random.get(5, 25));
        } else if (agility == 12640) {
            player.getInventory().addOrDrop(agility, Random.get(20, 100));
        }

        player.sendMessage("<col=8B0000>While training agility, you find a agility box and it contains: " + ItemDef.get(agility).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(agility).name + " was dropped to the floor.");
        }
    }

    public static void openCookingBox(Player player) {

        int cooking = tableCooking.rollItem().getId();

        if (cooking == 378) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 300));
        } else if (cooking == 372) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 300));
        } else if (cooking == 7945) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 300));
        } else if (cooking == 384) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 300));
        } else if (cooking == 390) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 200));
        } else if (cooking == 396) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 200));
        } else if (cooking == 11935) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 200));
        } else if (cooking == 13440) {
            player.getInventory().addOrDrop(cooking, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training cooking, you find a cooking box and it contains: " + ItemDef.get(cooking).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(cooking).name + " was dropped to the floor.");
        }
    }

    public static void openFarmingBox(Player player) {

        int farming = tableFarming.rollItem().getId();

        if (farming == 5295) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5296) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5297) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5298) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5299) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5300) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5301) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5302) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5303) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5304) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5314) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5315) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        } else if (farming == 5316) {
            player.getInventory().addOrDrop(farming, Random.get(3, 10));
        }

        player.sendMessage("<col=8B0000>While training farming, you find a farming box and it contains: " + ItemDef.get(farming).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(farming).name + " was dropped to the floor.");
        }

    }

    public static void openFishingBox(Player player) {

        int fishing = tableFishing.rollItem().getId();

        if (fishing == 378) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 300));
        } else if (fishing == 372) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 300));
        } else if (fishing == 7945) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 300));
        } else if (fishing == 384) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 300));
        } else if (fishing == 390) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 200));
        } else if (fishing == 396) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 200));
        } else if (fishing == 11935) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 200));
        } else if (fishing == 13440) {
            player.getInventory().addOrDrop(fishing, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training fishing, you find a fishing box and it contains: " + ItemDef.get(fishing).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(fishing).name + " was dropped to the floor.");
        }

    }

    public static void openCraftingBox(Player player) {

        int crafting = tableCrafting.rollItem().getId();

        if (crafting == 1754) {
            player.getInventory().addOrDrop(crafting, Random.get(30, 150));
        } else if (crafting == 1752) {
            player.getInventory().addOrDrop(crafting, Random.get(30, 150));
        } else if (crafting == 1750) {
            player.getInventory().addOrDrop(crafting, Random.get(30, 150));
        } else if (crafting == 1748) {
            player.getInventory().addOrDrop(crafting, Random.get(30, 150));
        } else if (crafting == 1618) {
            player.getInventory().addOrDrop(crafting, Random.get(20, 100));
        } else if (crafting == 1620) {
            player.getInventory().addOrDrop(crafting, Random.get(20, 100));
        } else if (crafting == 1622) {
            player.getInventory().addOrDrop(crafting, Random.get(20, 100));
        } else if (crafting == 1625) {
            player.getInventory().addOrDrop(crafting, Random.get(20, 100));
        } else if (crafting == 1632) {
            player.getInventory().addOrDrop(crafting, Random.get(20, 100));
        } else if (crafting == 6571) {
            player.getInventory().addOrDrop(crafting, 1);
        }

        player.sendMessage("<col=8B0000>While training crafting, you find a crafting box and it contains: " + ItemDef.get(crafting).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(crafting).name + " was dropped to the floor.");
        }

    }

    public static void openHerbloreBox(Player player) {

        int herblore = tableHerblore.rollItem().getId();

        if (herblore == 208) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 210) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 212) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 214) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 216) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 218) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 220) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        } else if (herblore == 2486) {
            player.getInventory().addOrDrop(herblore, Random.get(20, 50));
        }

        player.sendMessage("<col=8B0000>While training herblore, you find a herblore box and it contains: " + ItemDef.get(herblore).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(herblore).name + " was dropped to the floor.");
        }

    }

    public static void openSmithingBox(Player player) {

        int smithing = tableSmithing.rollItem().getId();

        if (smithing == 2354) {
            player.getInventory().addOrDrop(smithing, Random.get(50, 250));
        } else if (smithing == 2360) {
            player.getInventory().addOrDrop(smithing, Random.get(50, 200));
        } else if (smithing == 2362) {
            player.getInventory().addOrDrop(smithing, Random.get(50, 200));
        } else if (smithing == 2364) {
            player.getInventory().addOrDrop(smithing, Random.get(50, 100));
        } else if (smithing == 2358) {
            player.getInventory().addOrDrop(smithing, Random.get(50, 300));
        }

        player.sendMessage("<col=8B0000>While training smithing, you find a smithing box and it contains: " + ItemDef.get(smithing).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(smithing).name + " was dropped to the floor.");
        }
    }

    public static void openThievingBox(Player player) {

        int coins = 995;

        player.getInventory().addOrDrop(coins, Random.get(100000, 500000));

        player.sendMessage("<col=8B0000>While training thieving, you find a thieving box and it contains: " + ItemDef.get(coins).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(coins).name + " was dropped to the floor.");
        }
    }

    public static void openFletchingBox(Player player) {

        int fletching = tableFletching.rollItem().getId();

        if (fletching == 1520) {
            player.getInventory().addOrDrop(fletching, Random.get(100, 500));
        } else if (fletching == 1518) {
            player.getInventory().addOrDrop(fletching, Random.get(100, 500));
        } else if (fletching == 1516) {
            player.getInventory().addOrDrop(fletching, Random.get(100, 300));
        } else if (fletching == 1514) {
            player.getInventory().addOrDrop(fletching, Random.get(75, 200));
        } else if (fletching == 19670) {
            player.getInventory().addOrDrop(fletching, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training fletching, you find a fletching box and it contains: " + ItemDef.get(fletching).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(fletching).name + " was dropped to the floor.");
        }

    }

    public static void openFiremakingBox(Player player) {

        int firemaking = tableFiremaking.rollItem().getId();

        if (firemaking == 1520) {
            player.getInventory().addOrDrop(firemaking, Random.get(100, 500));
        } else if (firemaking == 1518) {
            player.getInventory().addOrDrop(firemaking, Random.get(100, 500));
        } else if (firemaking == 1516) {
            player.getInventory().addOrDrop(firemaking, Random.get(100, 300));
        } else if (firemaking == 1514) {
            player.getInventory().addOrDrop(firemaking, Random.get(75, 200));
        } else if (firemaking == 19670) {
            player.getInventory().addOrDrop(firemaking, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training firemaking, you find a firemaking box and it contains: " + ItemDef.get(firemaking).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(firemaking).name + " was dropped to the floor.");
        }

    }

    public static void openWoodcuttingBox(Player player) {

        int woodcutting = tableWoodcutting.rollItem().getId();

        if (woodcutting == 1520) {
            player.getInventory().addOrDrop(woodcutting, Random.get(100, 500));
        } else if (woodcutting == 1518) {
            player.getInventory().addOrDrop(woodcutting, Random.get(100, 500));
        } else if (woodcutting == 1516) {
            player.getInventory().addOrDrop(woodcutting, Random.get(100, 300));
        } else if (woodcutting == 1514) {
            player.getInventory().addOrDrop(woodcutting, Random.get(75, 200));
        } else if (woodcutting == 19670) {
            player.getInventory().addOrDrop(woodcutting, Random.get(50, 200));
        }

        player.sendMessage("<col=8B0000>While training woodcutting, you find a woodcutting box and it contains: " + ItemDef.get(woodcutting).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(woodcutting).name + " was dropped to the floor.");
        }

    }

    public static void openConstructionBox(Player player) {

        int construction = tableConstruction.rollItem().getId();

        if (construction == 8779) {
            player.getInventory().addOrDrop(construction, Random.get(50, 150));
        } else if (construction == 8780) {
            player.getInventory().addOrDrop(construction, Random.get(40, 150));
        } else if (construction == 8783) {
            player.getInventory().addOrDrop(construction, Random.get(30, 125));
        }

        player.sendMessage("<col=8B0000>While training construction, you find a construction box and it contains: " + ItemDef.get(construction).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(construction).name + " was dropped to the floor.");
        }
    }

    public static void openRunecraftingBox(Player player) {

        int runecrafting = tableRunecrafting.rollItem().getId();

        if (runecrafting == 7937) {
            player.getInventory().addOrDrop(runecrafting, Random.get(2000, 10000));
        } else if (runecrafting == 564) {
            player.getInventory().addOrDrop(runecrafting, Random.get(250, 1000));
        } else if (runecrafting == 565) {
            player.getInventory().addOrDrop(runecrafting, Random.get(250, 1000));
        } else if (runecrafting == 566) {
            player.getInventory().addOrDrop(runecrafting, Random.get(250, 1000));
        } else if (runecrafting == 561) {
            player.getInventory().addOrDrop(runecrafting, Random.get(250, 1000));
        } else if (runecrafting == 560) {
            player.getInventory().addOrDrop(runecrafting, Random.get(250, 1000));
        }

        player.sendMessage("<col=8B0000>While training runecrafting, you find a runecrafting box and it contains: " + ItemDef.get(runecrafting).name + ".");
        if (player.getInventory().isFull()) {
            player.sendMessage("<col=8B0000>" + ItemDef.get(runecrafting).name + " was dropped to the floor.");
        }
    }

    public static void addBoxWhileSkilling(Player player, StatType statType) {
        switch (statType) {
            case Prayer:
                if (Random.get(1, 50) == 50) {
                    openPrayerBox(player);
                }
            case Hunter:
                if (Random.get(1, 50) == 50) {
                    openHunterBox(player);
                }
                break;
            case Mining:
                if (Random.get(1, 50) == 50) {
                    openMiningBox(player);
                }
                break;
            case Agility:
                if (Random.get(1, 50) == 50) {
                    openAgilityBox(player);
                }
                break;
            case Cooking:
                if (Random.get(1, 50) == 50) {
                    openCookingBox(player);
                }
                break;
            case Farming:
                if (Random.get(1, 50) == 50) {
                    openFarmingBox(player);
                }
                break;
            case Fishing:
                if (Random.get(1, 50) == 50) {
                    openFishingBox(player);
                }
                break;
            case Crafting:
                if (Random.get(1, 50) == 50) {
                    openCraftingBox(player);
                }
                break;
            case Herblore:
                if (Random.get(1, 50) == 50) {
                    openHerbloreBox(player);
                }
                break;
            case Smithing:
                if (Random.get(1, 50) == 50) {
                    openSmithingBox(player);
                }
                break;
            case Thieving:
                if (Random.get(1, 50) == 50) {
                    openThievingBox(player);
                }
                break;
            case Fletching:
                if (Random.get(1, 50) == 50) {
                    openFletchingBox(player);
                }
                break;
            case Firemaking:
                if (Random.get(1, 50) == 50) {
                    openFiremakingBox(player);
                }
                break;
            case Woodcutting:
                if (Random.get(1, 50) == 50) {
                    openWoodcuttingBox(player);
                }
                break;
            case Construction:
                if (Random.get(1, 50) == 50) {
                    openConstructionBox(player);
                }
                break;
            case Runecrafting:
                if (Random.get(1, 50) == 50) {
                    openRunecraftingBox(player);
                }
                break;
        }
    }

    static {

        ItemAction.registerInventory(SKILLING_BOX, 1, SkillingBox::open);

    }
}
