package io.ruin.model.inter.handlers.shopinterface;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.shop.Currency;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum CustomShop {
    MELEE_STORE(
            1,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(1155, 50),
                    new ShopItem(1153, 150),
                    new ShopItem(1157, 550),
                    new ShopItem(1165, 1050),
                    new ShopItem(1159, 1540),
                    new ShopItem(1161, 3530),
                    new ShopItem(1163, 35050),
                    new ShopItem(5574, 5000),
                    new ShopItem(9672, 6000),
                    new ShopItem(10828, 60000),
                    new ShopItem(1117, 155),
                    new ShopItem(1115, 450),
                    new ShopItem(1119, 2000),
                    new ShopItem(1125, 3740),
                    new ShopItem(1121, 5100),
                    new ShopItem(1123, 12600),
                    new ShopItem(1127, 60000),
                    new ShopItem(5575, 10000),
                    new ShopItem(9674, 12000),
                    new ShopItem(544, 40),
                    new ShopItem(1075, 10000),
                    new ShopItem(1067, 80),
                    new ShopItem(1069, 270),
                    new ShopItem(1077, 1000),
                    new ShopItem(1071, 1910),
                    new ShopItem(1073, 2560),
                    new ShopItem(1079, 6400),
                    new ShopItem(5576, 58000),
                    new ShopItem(9676, 8000),
                    new ShopItem(542, 10000),
                    new ShopItem(1189, 70),
                    new ShopItem(1191, 235),
                    new ShopItem(1193, 800),
                    new ShopItem(1195, 1612),
                    new ShopItem(1197, 2205),
                    new ShopItem(1199, 5410),
                    new ShopItem(1201, 52000),
                    new ShopItem(10564, 65000),
                    new ShopItem(6809, 50000),
                    new ShopItem(3751, 45000),
                    new ShopItem(4119, 25),
                    new ShopItem(4121, 86),
                    new ShopItem(4123, 320),
                    new ShopItem(4125, 565),
                    new ShopItem(4127, 790),
                    new ShopItem(4129, 1910),
                    new ShopItem(4131, 12250),
                    new ShopItem(3105, 5000),
                    new ShopItem(1205, 10),
                    new ShopItem(1203, 40),
                    new ShopItem(1207, 125),
                    new ShopItem(1217, 245),
                    new ShopItem(1209, 330),
                    new ShopItem(1211, 820),
                    new ShopItem(1213, 8000),
                    new ShopItem(1321, 33),
                    new ShopItem(1323, 110),
                    new ShopItem(1325, 400),
                    new ShopItem(1327, 772),
                    new ShopItem(1329, 1020),
                    new ShopItem(1331, 2540),
                    new ShopItem(1333, 25700),
                    new ShopItem(1307, 80),
                    new ShopItem(1309, 280),
                    new ShopItem(1311, 1000),
                    new ShopItem(1313, 1910),
                    new ShopItem(1315, 2600),
                    new ShopItem(1317, 6350),
                    new ShopItem(1319, 62000),
                    new ShopItem(1215, 30000),
                    new ShopItem(1305, 95000),
                    new ShopItem(1377, 195000),
                    new ShopItem(4587, 100000),
                    new ShopItem(1434, 50000),
                    new ShopItem(11037, 26500)
            }
    ),
    RANGED_STORE(
            2,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(884, 3),
                    new ShopItem(886, 14),
                    new ShopItem(888, 37),
                    new ShopItem(890, 82),
                    new ShopItem(892, 400),
                    new ShopItem(841, 50),
                    new ShopItem(843, 120),
                    new ShopItem(849, 200),
                    new ShopItem(853, 400),
                    new ShopItem(861, 1540),
                    new ShopItem(9140, 0),
                    new ShopItem(9141, 0),
                    new ShopItem(9142, 0),
                    new ShopItem(9143, 0),
                    new ShopItem(9144, 0),
                    new ShopItem(9177, 0),
                    new ShopItem(9179, 0),
                    new ShopItem(9181, 0),
                    new ShopItem(9183, 0),
                    new ShopItem(9185, 0),
                    new ShopItem(863, 0),
                    new ShopItem(865, 0),
                    new ShopItem(866, 0),
                    new ShopItem(867, 0),
                    new ShopItem(868, 0),
                    new ShopItem(807, 0),
                    new ShopItem(808, 0),
                    new ShopItem(809, 0),
                    new ShopItem(810, 0),
                    new ShopItem(811, 0)
            }
    ),
    MAGIC_STORE(
            3,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(558, 85),
                    new ShopItem(556, 40),
                    new ShopItem(557, 40),
                    new ShopItem(559, 180),
                    new ShopItem(555, 40),
                    new ShopItem(554, 40),
                    new ShopItem(562, 350),
                    new ShopItem(560, 540),
                    new ShopItem(565, 850),
                    new ShopItem(561, 770),
                    new ShopItem(563, 500),
                    new ShopItem(564, 400),
                    new ShopItem(9075, 500),
                    new ShopItem(566, 300),
                    new ShopItem(21880, 1920),
                    new ShopItem(1381, 1500),
                    new ShopItem(1383, 1500),
                    new ShopItem(1385, 1500),
                    new ShopItem(1387, 1500),
                    new ShopItem(1391, 6820),
                    new ShopItem(4675, 78000),
                    new ShopItem(2415, 75000),
                    new ShopItem(2416, 75000),
                    new ShopItem(2417, 75000),
                    new ShopItem(4089, 15000),
                    new ShopItem(4091, 80000),
                    new ShopItem(4093, 80000),
                    new ShopItem(4095, 10000),
                    new ShopItem(4097, 10000),
                    new ShopItem(4099, 15000),
                    new ShopItem(4101, 80000),
                    new ShopItem(4103, 10000),
                    new ShopItem(4105, 10000),
                    new ShopItem(4107, 10000),
                    new ShopItem(4109, 15000),
                    new ShopItem(4111, 80000),
                    new ShopItem(4113, 80000),
                    new ShopItem(4115, 10000),
                    new ShopItem(4117, 10000),
                    new ShopItem(6109, 750),
                    new ShopItem(6107, 750),
                    new ShopItem(6108, 750),
                    new ShopItem(6110, 750),
                    new ShopItem(6106, 750),
                    new ShopItem(6111, 450)
            }
    ),
    JEWELRY_STORE(
            4,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(1478, 750),
                    new ShopItem(1712, 14500),
                    new ShopItem(1725, 2850),
                    new ShopItem(1727, 750),
                    new ShopItem(1729, 5000),
                    new ShopItem(1731, 2550),
                    new ShopItem(2550, 2275)

            }
    ),
    UNTRADEABLE_STORE(
            5,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(7458, 47500),
                    new ShopItem(7459, 75000),
                    new ShopItem(7460, 100000),
                    new ShopItem(7461, 195000),
                    new ShopItem(7462, 220000),
                    new ShopItem(10498, 7550),
                    new ShopItem(10499, 15000)
            }
    ),
    PK_POINT_STORE(
            6,
            Currency.PVP_TOKENS,
            new ShopItem[]{
                    new ShopItem(6758, 30),
                    new ShopItem(608, 30),
                    new ShopItem(607, 30),
                    new ShopItem(24207, 1),
                    new ShopItem(24209, 10),
                    new ShopItem(24211, 50),
                    new ShopItem(24213, 100),
                    new ShopItem(24215, 500),
                    new ShopItem(19564, 75),
                    new ShopItem(12849, 15),
                    new ShopItem(12804, 30),
                    new ShopItem(13317, 15),
                    new ShopItem(13318, 15),
                    new ShopItem(13319, 15),
                    new ShopItem(24219, 100),
                    new ShopItem(24204, 100),
                    new ShopItem(24198, 100),
                    new ShopItem(24195, 100),
                    new ShopItem(24192, 100),
                    new ShopItem(12791, 250)
            }
    ),
    MISC_STORE(
            7,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(2436, 2275),
                    new ShopItem(2440, 3000),
                    new ShopItem(2442, 2125),
                    new ShopItem(3040, 450),
                    new ShopItem(2444, 3610),
                    new ShopItem(2434, 5125),
                    new ShopItem(3024, 9750),
                    new ShopItem(10925, 12000),
                    new ShopItem(6685, 14775),
                    new ShopItem(2446, 340),
                    new ShopItem(2452, 340),
                    new ShopItem(12905, 25),
                    new ShopItem(4417, 2250),
                    new ShopItem(333, 49),
                    new ShopItem(365, 414),
                    new ShopItem(379, 287),
                    new ShopItem(373, 596),
                    new ShopItem(385, 2105),
                    new ShopItem(227, 8),
                    new ShopItem(8013, 1237),
                    new ShopItem(8008, 1237),
                    new ShopItem(8007, 1237),
                    new ShopItem(8009, 1237),
                    new ShopItem(8010, 1237)

            }
    ),
    SKILLING_SUPPLIES(
            8,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(314, 36),
                    new ShopItem(590, 213),
                    new ShopItem(2347, 110),
                    new ShopItem(946, 34),
                    new ShopItem(1755, 86),
                    new ShopItem(1735, 27),
                    new ShopItem(1733, 100),
                    new ShopItem(1734, 9),
                    new ShopItem(1785, 42),
                    new ShopItem(1592, 10),
                    new ShopItem(1595, 10),
                    new ShopItem(1597, 10),
                    new ShopItem(11065, 511),
                    new ShopItem(5523, 175),
                    new ShopItem(1267, 245),
                    new ShopItem(1269, 2275),
                    new ShopItem(1271, 5050),
                    new ShopItem(1275, 32000),
                    new ShopItem(1349, 98),
                    new ShopItem(1353, 325),
                    new ShopItem(1355, 900),
                    new ShopItem(1357, 2260),
                    new ShopItem(1359, 21000),
                    new ShopItem(1540, 1000),
                    new ShopItem(952, 1)
            }
    ),

    VOTE_STORE(
            10,
            Currency.VOTE_TICKETS,
            new ShopItem[]{
                    new ShopItem(989, 4),
                    new ShopItem(19564, 25),
                    new ShopItem(13071, 100),
                    new ShopItem(11738, 5),
                    new ShopItem(19473, 5),
                    new ShopItem(11941, 20),
                    new ShopItem(12791, 50),
                    new ShopItem(12846, 100),
                    new ShopItem(30248, 300),
                    new ShopItem(6829, 25),
                    new ShopItem(12637, 250),
                    new ShopItem(12638, 250),
                    new ShopItem(12639, 250),
                    new ShopItem(20017, 400),
                    new ShopItem(20005, 400),
                    new ShopItem(1564, 50),
                    new ShopItem(1561, 50),
                    new ShopItem(1562, 50),
                    new ShopItem(1565, 50),
                    new ShopItem(1566, 50),
                    new ShopItem(1563, 50),
                    new ShopItem(7582, 100),
                    new ShopItem(13256, 150),
                    new ShopItem(4069, 20),
                    new ShopItem(4070, 20),
                    new ShopItem(4504, 50),
                    new ShopItem(4505, 50),
                    new ShopItem(4509, 150),
                    new ShopItem(4510, 150),
                    new ShopItem(11899, 50),
                    new ShopItem(11900, 50),
                    new ShopItem(11901, 25),
                    new ShopItem(11898, 50),
                    new ShopItem(11896, 50),
                    new ShopItem(11897, 50)
            }
    ),

    AFK_STORE(
            11,
            Currency.AFK_TOKENS,
            new ShopItem[]{
                    new ShopItem(7409, 100000),
                    new ShopItem(11920, 1000000),
                    new ShopItem(6739, 1000000),
                    new ShopItem(21028, 1000000),
                    new ShopItem(12800, 200000),
                    new ShopItem(22994, 250000),
                    new ShopItem(13226, 500000),

                    new ShopItem(10941, 100000),
                    new ShopItem(10939, 100000),
                    new ShopItem(10940, 100000),
                    new ShopItem(10933, 100000),

                    new ShopItem(13258, 100000),
                    new ShopItem(13259, 100000),
                    new ShopItem(13260, 100000),
                    new ShopItem(13261, 100000),

                    new ShopItem(25592, 100000),
                    new ShopItem(25594, 100000),
                    new ShopItem(25596, 100000),
                    new ShopItem(25598, 100000),

                    new ShopItem(13646, 100000),
                    new ShopItem(13642, 100000),
                    new ShopItem(13640, 100000),
                    new ShopItem(13644, 100000),

                    new ShopItem(21343, 100000),
                    new ShopItem(21345, 200000),
                    new ShopItem(21392, 300000),

            }
    ),

    IRONMAN_SHOP(
            12,
            Currency.COINS,
            new ShopItem[]{
                    new ShopItem(314, 1),
                    new ShopItem(590, 1),
                    new ShopItem(2347, 1),
                    new ShopItem(946, 1),
                    new ShopItem(1755, 1),
                    new ShopItem(1735, 1),
                    new ShopItem(1733, 1),
                    new ShopItem(1734, 3),
                    new ShopItem(1785, 5),
                    new ShopItem(1592, 10),
                    new ShopItem(1595, 10),
                    new ShopItem(1597, 10),
                    new ShopItem(11065, 10),
                    new ShopItem(5523, 175),
                    new ShopItem(1267, 245),
                    new ShopItem(1269, 2275),
                    new ShopItem(1271, 5050),
                    new ShopItem(1275, 32000),
                    new ShopItem(1349, 98),
                    new ShopItem(1353, 325),
                    new ShopItem(1355, 900),
                    new ShopItem(1357, 2260),
                    new ShopItem(1359, 21000),

                    new ShopItem(303, 1),
                    new ShopItem(309, 1),
                    new ShopItem(301, 1),
                    new ShopItem(311, 1),

                    new ShopItem(1540, 1000),
                    new ShopItem(952, 1),
                    new ShopItem(5341, 3),
                    new ShopItem(5343, 3),
                    new ShopItem(5343, 3),
                    new ShopItem(5325, 3),
                    new ShopItem(5329, 3),

                    new ShopItem(10006, 1),
                    new ShopItem(10008, 1),
                    new ShopItem(10012, 0),
                    new ShopItem(11260, 0),
                    new ShopItem(10010, 12),
                    new ShopItem(954, 10),
                    new ShopItem(227, 1),
                    new ShopItem(233, 2),
                    new ShopItem(2446, 216),
                    new ShopItem(9419, 1),
                    new ShopItem(10499, 600),
                    new ShopItem(544, 24),
                    new ShopItem(542, 18),
                    new ShopItem(3105, 7),
                    new ShopItem(8880, 1200),
                    new ShopItem(8882, 1),
                    new ShopItem(2417, 48000),
                    new ShopItem(2415, 48000),
                    new ShopItem(2416, 48000),
                    new ShopItem(9672, 4800),
                    new ShopItem(9674, 7200),
                    new ShopItem(9676, 6000)
            }
    );

    private final ShopItem[] shopItems;
    private final Currency currency;
    private final int shopId;
    @Getter
    private final List<Player> playersInShop;

    CustomShop(final int SHOP_ID, final Currency currency, ShopItem[] shopItems) {
        this.shopId = SHOP_ID;
        this.currency = currency;
        this.shopItems = shopItems;
        playersInShop = new ArrayList<>();
    }

    public static Item[] getItemsFromShop(Player player) {
        int shopId = player.getShopIdentifier();
        if (shopId < 0) {
            player.sendMessage("Something is wrong with this shop. Please contact a staff member.");
            return new Item[0];
        }
        CustomShop shop = Arrays.stream(CustomShop.values())
                .filter(s -> s.shopId == shopId)
                .findFirst()
                .orElse(null);

        if (shop != null) {
            return toItemArray(shop.shopItems);
        }
        return new Item[0];
    }

    public ShopItem[] getShopItems() {
        return shopItems;
    }

    private static Item[] toItemArray(ShopItem[] shopItems) {
        Item[] items = new Item[shopItems.length];
        for (int index = 0; index < shopItems.length; index++) {
            ShopItem shopItem = shopItems[index];
            if (shopItem != null) {
                items[index] = new Item(shopItem.getItemId(), shopItem.getQuantity());
            }
        }
        return items;
    }

    public Item[] getItems() {
        Item[] items = new Item[shopItems.length];
        for (int index = 0; index < shopItems.length; index++) {
            ShopItem shopItem = shopItems[index];
            if (shopItem != null) {
                items[index] = new Item(shopItem.getItemId(), shopItem.getQuantity());
            }
        }
        return items;
    }

    public static CustomShop get(int shopId) {
        return Arrays.stream(CustomShop.values())
                .filter(s -> s.shopId == shopId)
                .findFirst()
                .orElse(null);
    }

    public void addPlayerToShop(Player player) {
        if (!playersInShop.contains(player)) {
            playersInShop.add(player);
        }
    }

    public void removePlayerFromShop(Player player) {
        playersInShop.remove(player);
    }

    public Currency getCurrency() {
        return currency;
    }

    public ShopItem getShopItem(int itemId) {
        return Arrays.stream(shopItems)
                .filter(i -> i.getItemId() == itemId)
                .findFirst()
                .orElse(null);
    }

    public void refreshShop() {
        List<Player> playersToRemove = new ArrayList<>();
        for (Player player : playersInShop) {
            if (player != null) {
                if (!player.hasInterfaceOpen(Interface.CUSTOM_SHOP, InterfaceType.MAIN)) {
                    playersToRemove.add(player);
                    continue;
                }
                player.getPacketSender().sendItems(10005, getItems());
                player.setShopIdentifier(player.getShopIdentifier());
                player.getPacketSender().sendClientScript(917, "ii", -1, -1);
                player.getPacketSender().sendClientScript(10197);
                player.getPacketSender().sendAccessMask(836, 39, 0,
                        127, 1150);
            }
        }
        playersToRemove.stream().filter(Objects::nonNull).forEach(this::removePlayerFromShop);
    }

    public static void openCustomShopViaCmd(Player player) {
        player.getPacketSender().sendString(836, 14, "Melee Store");
        CustomShopInterface.open(player, CustomShop.get(1).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.MELEE_STORE);
        player.setShopIdentifier(1);
    }

    public static void openAFKShop(Player player) {
        player.getPacketSender().sendString(836, 14, "AFK Store");
        CustomShopInterface.open(player, CustomShop.get(11).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.AFK_STORE);
        player.setShopIdentifier(11);
    }

    public static void openIronmanShop(Player player) {
        player.getPacketSender().sendString(836, 14, "Ironman Store");
        CustomShopInterface.open(player, CustomShop.get(12).getItems());
        CustomShopInterface.handleEnteringShop(player, CustomShop.IRONMAN_SHOP);
        player.setShopIdentifier(12);
    }

    static {
//        NPCAction.register(2108, 1, (player, npc) -> {
//            player.getPacketSender().sendString(836, 14, "Vote Store");
//            CustomShopInterface.open(player, CustomShop.get(10).getItems());
//            CustomShopInterface.handleEnteringShop(player, CustomShop.VOTE_STORE);
//            player.setShopIdentifier(10);
//        });
    }
}
