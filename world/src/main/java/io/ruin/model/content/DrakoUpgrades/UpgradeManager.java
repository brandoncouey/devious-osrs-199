package io.ruin.model.content.DrakoUpgrades;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.activities.duelarena.Duel;
import io.ruin.model.activities.wellofgoodwill.WellofGoodwill;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UpgradeManager {


    @Expose
    @Setter
    @Getter
    private static Category lastCategory;

    @Expose
    @Getter
    @Setter
    private static UpgradableItems selectedItem;

    @Expose
    @Getter
    @Setter
    private UpgradableItems item;

    @Setter
    @Getter
    private transient Player player;

    private static final int[] ITEM_COMPONENTS = {
            55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94
    };

    private static final int[] REQUIRED_COMPONENTS = {
            112, 116, 120
    };

    private static final int UPGRADE_BUTTON = 107;
    private static final int component = 10983;

    public enum UpgradableItems {


        // Weapon

        W1(0, Category.WEAPON, "Bloody Tentacle Whip (i)", 30426, 50, new Item[]{new Item(30276, 1), new Item(995, 250000000)}),

        W2(1, Category.WEAPON, "Bloodline Chainmace", 30266, 50, new Item[]{new Item(22542, 1), new Item(21820, 10000)}),

        W3(2, Category.WEAPON, "Bloodline Bow", 30265, 50, new Item[]{new Item(22547, 1), new Item(21820, 10000)}),

        W4(3, Category.WEAPON, "Magic Shortbow (i)", 12788, 75, new Item[]{new Item(861), new Item(12786, 1), new Item(995, 5000000)}),

        W5(4, Category.WEAPON, "Ivandis Flail (i)", 24699, 80, new Item[]{new Item(24691), new Item(22398), new Item(995, 1000000)}),

        W8(5, Category.WEAPON, "Infernal Dinh's", 30245, 80, new Item[]{new Item(21015), new Item(6529, 100000)}),

        A11(0, Category.ARMOUR, "Virtus Mask (i)", 30431, 85, new Item[]{new Item(30006), new Item(13307, 75000000)}),

        A12(1, Category.ARMOUR, "Virtus Robe Top (i)", 30430, 85, new Item[]{new Item(30007), new Item(13307, 75000000)}),

        A13(2, Category.ARMOUR, "Virtus Robe Bottoms (i)", 30429, 85, new Item[]{new Item(30008), new Item(13307, 75000000)}),

        A31(3, Category.ARMOUR, "Eternal boots (g)", 30443, 90, new Item[]{new Item(13235), new Item(995, 150000000)}),

        A22(4, Category.ARMOUR, "Fighter Torso (e)", 30428, 75, new Item[]{new Item(10551), new Item(13307, 25000000)}),

        A17(5, Category.ARMOUR, "Torva Full Helm (i)", 30447, 85, new Item[]{new Item(30000), new Item(13307, 75000000)}),

        A18(6, Category.ARMOUR, "Torva Platebody (i)", 30449, 85, new Item[]{new Item(30001), new Item(13307, 75000000)}),

        A19(7, Category.ARMOUR, "Torva Platelegs (i)", 30448, 85, new Item[]{new Item(30002), new Item(13307, 75000000)}),

        A30(8, Category.ARMOUR, "Primordial boots (g)", 30442, 90, new Item[]{new Item(13239), new Item(995, 150000000)}),

        A1(9, Category.ARMOUR, "Vampyric Faceguard", 30275, 50, new Item[]{new Item(24271), new Item(24777)}),

        A20(10, Category.ARMOUR, "Pernix Cowl (i)", 30450, 85, new Item[]{new Item(30003), new Item(13307, 75000000)}),

        A21(11, Category.ARMOUR, "Pernix Body (i)", 30451, 85, new Item[]{new Item(30004), new Item(13307, 75000000)}),

        A23(12, Category.ARMOUR, "Pernix Chaps (i)", 30452, 85, new Item[]{new Item(30005), new Item(13307, 75000000)}),

        A32(13, Category.ARMOUR, "Pegasian boots (g)", 30444, 90, new Item[]{new Item(13237), new Item(995, 150000000)}),


        A24(14, Category.ARMOUR, "Bandos Chestplate (or)", 30435, 90, new Item[]{new Item(11832), new Item(30445, 1)}),

        A25(15, Category.ARMOUR, "Bandos Tassets (or)", 30436, 90, new Item[]{new Item(11834), new Item(30445, 1)}),

        A26(16, Category.ARMOUR, "Bandos Boots (or)", 30440, 90, new Item[]{new Item(11836), new Item(30445, 1)}),

        A27(17, Category.ARMOUR, "Armadyl Helmet (or)", 30437, 90, new Item[]{new Item(11826), new Item(30446, 1)}),

        A28(18, Category.ARMOUR, "Armadyl Chainskirt (or)", 30438, 90, new Item[]{new Item(11830), new Item(30446, 1)}),

        A29(19, Category.ARMOUR, "Armadyl Chestplate (or)", 30439, 90, new Item[]{new Item(11828), new Item(30446, 1)}),

        A33(20, Category.ARMOUR, "Ferocious Gloves (i)", 30455, 70, new Item[]{new Item(22981), new Item(995, 450000000)}),

        // Jewellery
        J1(0, Category.JEWELLERY, "Ring of Suffering (i)", 19710, 99, new Item[]{new Item(19550), new Item(995, 15000000),}),

        J2(1, Category.JEWELLERY, "Tormented Bracelet (or)", 23444, 99, new Item[]{new Item(19544, 2), new Item(995, 25000000)}),

        J5(2, Category.JEWELLERY, "Occult Necklace (or)", 19720, 99, new Item[]{new Item(12002, 2), new Item(995, 25000000)}),

        J11(3, Category.JEWELLERY, "Salve Amulet (i)", 12017, 99, new Item[]{new Item(4081), new Item(995, 5000000)}),

        J12(4, Category.JEWELLERY, "Salve Amulet (ei)", 12018, 99, new Item[]{new Item(12017), new Item(995, 10000000)}),

        J13(5, Category.JEWELLERY, "Ring of the Gods (i)", 13202, 99, new Item[]{new Item(12601), new Item(995, 5000000)}),

        J14(6, Category.JEWELLERY, "Treasonous Ring (i)", 12692, 99, new Item[]{new Item(12605), new Item(995, 5000000)}),

        J15(7, Category.JEWELLERY, "Tyrannical Ring (i)", 12691, 99, new Item[]{new Item(12603), new Item(995, 5000000)}),

        J16(8, Category.JEWELLERY, "Berserker Ring (i)", 11773, 99, new Item[]{new Item(6737), new Item(995, 5000000)}),

        J17(9, Category.JEWELLERY, "Archers Ring (i)", 11771, 99, new Item[]{new Item(6733), new Item(995, 5000000)}),

        J18(10, Category.JEWELLERY, "Seers Ring (i)", 11770, 99, new Item[]{new Item(6731), new Item(995, 5000000)}),

        J19(11, Category.JEWELLERY, "Warrior Ring (i)", 11772, 99, new Item[]{new Item(6735), new Item(995, 5000000)}),

        J20(12, Category.JEWELLERY, "Arcane Stream Necklace", 27502, 90, new Item[]{new Item(12002), new Item(13307, 75000000)}),

        J21(13, Category.JEWELLERY, "Brimstone Ring (i)", 30427, 90, new Item[]{new Item(22975), new Item(13307, 75000000)}),

        J22(14, Category.JEWELLERY, "Ring of Endless Recoil (i)", 30456, 60, new Item[]{new Item(30318), new Item(995, 500000000)}),


// Pets


        // OTHER
        M1(0, Category.MISC, "Imbued Guthix Cape", 21793, 99, new Item[]{new Item(2413), new Item(21798), new Item(995, 5000000)}),

        M2(1, Category.MISC, "Imbued Zamorak Cape", 21795, 99, new Item[]{new Item(2414), new Item(21799), new Item(995, 5000000)}),

        M3(2, Category.MISC, "Imbued Saradomin Cape", 21791, 99, new Item[]{new Item(2412), new Item(21797), new Item(995, 5000000)}),

        M4(3, Category.MISC, "Overload Heart", 30441, 65, new Item[]{new Item(20724), new Item(20996), new Item(995, 450000000)}),

        M5(4, Category.MISC, "Enhanced looting bag", 30453, 75, new Item[]{new Item(11941), new Item(13307, 15000000)}),

        M6(5, Category.MISC, "Ferocious Gloves (i)", 30455, 70, new Item[]{new Item(22981), new Item(995, 450000000)}),

        ;

        private final int component;
        private final Category catagory;
        private final String name;
        private final int itemid;
        private final int chance;
        private final Item[] required;

        UpgradableItems(int component, Category category, String name, int itemid, int chance, Item[] required) {
            this.component = component;
            this.catagory = category;
            this.name = name;
            this.itemid = itemid;
            this.chance = chance;
            this.required = required;
        }

        public int getComponent() {
            return component;
        }

        public Category getCategory() {
            return catagory;
        }

        public String getName() {
            return name;
        }

        public int getItemid() {
            return itemid;
        }

        public int getChance() {
            return chance;
        }

        public Item[] getRequired() {
            return required;
        }

        public static ArrayList<UpgradableItems> forCategory(Category category) {
            ArrayList<UpgradableItems> items = Lists.newArrayList();
            for (UpgradableItems item : UpgradableItems.values()) {
                if (item.catagory.equals(category)) {
                    items.add(item);
                }
            }
            return items;
        }
    }


    public enum Category {
        WEAPON,
        ARMOUR,
        JEWELLERY,
        PETS,
        MISC
    }

    public void sendInterface(Category category, Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.UPGRADE_INTERFACE);
        UpgradeManager.setLastCategory(category);
        UpgradeManager.setSelectedItem(null);
        sendItems(player);
        sendInfo(true, player);
        clearRequired(player);
    }

    public static void sendInterface(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.UPGRADE_INTERFACE);
        UpgradeManager.setLastCategory(lastCategory != null ? lastCategory : Category.WEAPON);
        UpgradeManager.setSelectedItem(null);
        sendItems(player);
        sendInfo(true, player);
        clearRequired(player);
    }

    private static void sendItems(Player player) {
        List<UpgradableItems> items = UpgradableItems.forCategory(UpgradeManager.getLastCategory());
        IntStream.of(ITEM_COMPONENTS).forEach((i -> {
            boolean hide = items.stream().noneMatch(item -> i == ITEM_COMPONENTS[item.getComponent()]);
            if (hide) {
                player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, i, 0, new Item(-1));
            }
        }));
        items.forEach(i -> {
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, ITEM_COMPONENTS[i.getComponent()], 0, new Item(i.itemid));
        });
    }

    private static void selectCategory(int index, Player player) {
        Category category = Category.values()[index];
        UpgradeManager.setLastCategory(category);
        UpgradeManager.setSelectedItem(null);
        sendItems(player);
        sendInfo(true, player);
        clearRequired(player);
    }

    private static void sendInfo(boolean clear, Player player) {
        if (player.getInventory().contains(component)) {
            player.getPacketSender().sendString(Interface.UPGRADE_INTERFACE, 106, clear ? "0%" : "100%");
        } else {
            player.getPacketSender().sendString(Interface.UPGRADE_INTERFACE, 106, clear ? "0%" : UpgradeManager.getSelectedItem().getChance() + "%");
        }
    }

    private static void clearRequired(Player player) {
        player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 112, 0, new Item(-1));
        player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 116, 0, new Item(-1));
        player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 120, 0, new Item(-1));

        player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 104, 0, new Item(-1));
    }

    private static void sendRequired(Player player) {
        int component = 0;
        Item[] requiredIds = selectedItem.getRequired();


/*        IntStream.of(ITEM_COMPONENTS).forEach((i -> {
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, i, 0, new Item(-1));
        }));*/

        player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 104, 0, new Item(selectedItem.itemid));

        if (requiredIds.length == 1) {
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 112, 0, requiredIds[0]);
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 116, 0, new Item(-1));
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 120, 0, new Item(-1));
        } else if (requiredIds.length == 2) {
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 112, 0, requiredIds[0]);
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 116, 0, requiredIds[1]);
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 120, 0, new Item(-1));
        } else if (requiredIds.length == 3) {
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 112, 0, requiredIds[0]);
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 116, 0, requiredIds[1]);
            player.getPacketSender().sendItems(Interface.UPGRADE_INTERFACE, 120, 0, requiredIds[2]);
        } else {
            System.out.println("Ben fucked up...");
        }
    }

    private static void clickItem(int component, Player player) {
        List<UpgradableItems> itemList = UpgradableItems.forCategory(UpgradeManager.getLastCategory());
        for (UpgradableItems item : itemList) {
            int button = ITEM_COMPONENTS[item.getComponent()];
            if (button == component) {
                UpgradeManager.setSelectedItem(item);
                sendInfo(false, player);
                sendRequired(player);
            }
        }
    }


    public static boolean componentCheck(Player player) {
        if (player.getInventory().contains(component)) {
            return true;
        } else {
            return false;
        }
    }

    private static void upgrade(Player player) {

        if (player.upgrading) {
            player.sendMessage("You are already trying to upgrade an item!");
            return;
        }

        if (selectedItem == null) {
            return;
        }


        Item[] requiredIds = selectedItem.getRequired();
        if (!player.getInventory().containsAll(true, requiredIds)) {
            player.sendMessage(Color.RED.wrap("You do not have the required items to do this."));
            return;
        }
        if (player.getInventory().containsAll(true, requiredIds)) {
            player.getInventory().removeAll(true, requiredIds);
            player.upgrading = true;
            player.lock();
            if (Random.rollPercent(selectedItem.chance) && !componentCheck(player)) {
                if (!player.SuccessfulUpgrades.containsKey(selectedItem.itemid)) {
                    player.SuccessfulUpgrades.put(selectedItem.itemid, 1);
                } else if (player.SuccessfulUpgrades.containsKey(selectedItem.itemid)) {
                    player.SuccessfulUpgrades.put(selectedItem.itemid, player.SuccessfulUpgrades.get(selectedItem.itemid) + 1);
                }
                player.getInventory().add(getSelectedItem().itemid);
                player.sendMessage(Color.DARK_GREEN.wrap("You have successfully upgraded a " + getSelectedItem().name + "."));
                Broadcast.WORLD.sendNews(player.getName() + " has just successfully upgraded a " + getSelectedItem().name + "!");
                player.unlock();
            } else if (componentCheck(player)) {
                player.getInventory().add(getSelectedItem().itemid);
                player.getInventory().remove(component, 1);
                player.sendMessage(Color.DARK_GREEN.wrap("You have successfully upgraded a " + getSelectedItem().name + "."));
                Broadcast.WORLD.sendNews(player.getName() + " has just successfully upgraded a " + getSelectedItem().name + "!");
                player.unlock();
            } else if (!player.FailedUpgrades.containsKey(selectedItem.itemid)) {
                player.FailedUpgrades.put(selectedItem.itemid, 1);
            } else {
                player.FailedUpgrades.put(selectedItem.itemid, player.FailedUpgrades.get(selectedItem.itemid) + 1);
                player.unlock();
                player.sendMessage(Color.RED.wrap("You have failed to upgrade a " + getSelectedItem().name + "."));
            }
        }

        player.upgrading = false;
        player.unlock();
    }

    public static void checkInterface(Player player, int itemID) {
        List<UpgradableItems> itemList = UpgradableItems.forCategory(UpgradeManager.getLastCategory());
        for (UpgradableItems item : itemList) {
            if (itemID == item.itemid) {
                UpgradeManager.setSelectedItem(item);
                sendInfo(false, player);
                sendRequired(player);
                return;
            }
        }
        ItemDef def = ItemDef.get(itemID);
        if (def == null)
            return;
        player.sendMessage(def.examine == null ? "This item has no examine" : def.examine + "<br> <col=6f0000> Low Alchemy Value: " + Duel.formatPrice(def.lowAlchValue) + ", High Alchemy Value: " + Duel.formatPrice(def.highAlchValue) + ".");
    }

    static {
        NPCAction.register(14761,2,(player, npc) -> {
            UpgradeManager.sendInterface(player);
        });

        InterfaceHandler.register(Interface.UPGRADE_INTERFACE, interfaceHandler -> {
            interfaceHandler.actions[23] = (SimpleAction) p -> selectCategory(0, p);
            interfaceHandler.actions[28] = (SimpleAction) p -> selectCategory(1, p);
            interfaceHandler.actions[33] = (SimpleAction) p -> selectCategory(2, p);
            interfaceHandler.actions[38] = (SimpleAction) p -> selectCategory(3, p);
            interfaceHandler.actions[43] = (SimpleAction) p -> selectCategory(4, p);
            interfaceHandler.actions[UPGRADE_BUTTON] = (SimpleAction) UpgradeManager::upgrade;
        });
    }

}
