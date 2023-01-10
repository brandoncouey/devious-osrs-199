package io.ruin.model.item.containers.collectionlog;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.actions.ItemAction;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CollectionLog extends ItemContainerG<CollectionLogItem> {

    @Setter
    private int currentTab;
    @Setter
    private int currentStruct;
    @Setter
    private int currentEntry;

    @Expose
    @Getter
    private Map<Integer, Integer> collected = new HashMap<>();

    public int getCollected(int itemId) {
        return collected.getOrDefault(itemId, 0);
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.COLLECTION_LOG);
        sendTab(player, CollectionLogInfo.BOSS);
    }

    public void sendTab(Player player, CollectionLogInfo info) {
        player.getPacketSender().sendClientScript(2389, "i", currentTab = info.ordinal());
        selectEntry(player, 0, info);
        info.sendAccessMasks(player);
    }

    public void collect(int id) {
        collect(id, 1);
    }

    /**
     * Collects an item into the collection log.
     *
     * @param id     The item id.
     * @param amount The amount.
     */
    public void collect(int id, int amount) {
        collect(new Item(id, amount));
    }

    /**
     * Collects the items into the collection log.
     *
     * @param items The items.
     */
    public void collect(Item... items) {
        for (Item item : items) {
            collect(item);
        }
    }

    /**
     * Collects an item into the collection log.
     *
     * @param item The item being collected.
     */
    private void collect(Item item) {
        Arrays.stream(COLLECTION_LOG_ITEMS).forEach(i -> {
            if (item.getId() == i) {
                int amount = collected.getOrDefault(item.getId(), 0);
                collected.put(item.getId(), amount + item.getAmount());
                collected.forEach((integer, integer2) -> {
                    if (item.getId() == integer) {
                        if (amount >= 1)
                            return;
                        player.getInventory().addOrDrop(13307, 75000);
                        player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
                        player.sendMessage("New Collection log item: " + Color.WHITE.wrap(item.getDef().name));
                        player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Collection log", "New item:" + Color.WHITE.wrap(item.getDef().name));
                    }
                });
            }
        });
    }

    public void clearCollectedItems() {
        collected.clear();
        CollectionLogInfo.TOTAL_COLLECTABLES = 0;
        //    Config.COLLECTION_COUNT.set(player, 0);
    }

    private void selectEntry(Player player, int slot, CollectionLogInfo info) {
        info.sendKillCount(player, slot);
        info.sendItems(player, slot);
        player.getPacketSender().sendClientScript(2730, "iiiiii", info.getParams()[0], info.getParams()[1], info.getParams()[2], info.getParams()[3], currentStruct = info.getCategoryStruct(), currentEntry = slot);
    }

    private static OptionsDialogue get(Player player, NPC npc) {
        return new OptionsDialogue("Would you like a Collection log?",
                new Option("Yes", () -> {
                    player.dialogue(
                            new PlayerDialogue("Yes, that sounds helpful!"),
                            new NPCDialogue(npc, "There! Now you will be able to see the true beauty of everything you collect on your adventures.").onDialogueOpened(() -> player.getInventory().add(ItemID.COLLECTION_LOG)),
                            new PlayerDialogue("Thanks!")
                    );
                }),
                new Option("No thanks.", () -> {
                    player.dialogue(
                            new PlayerDialogue("No thanks. I think I'll pass."),
                            new NPCDialogue(npc, "I should've guessed you wouldn't understand the true beauty of a good collection!")
                    );
                })
        );
    }

    static {
        NPCAction.register(8491, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "It's beautiful, isn't it?"),
                    new PlayerDialogue("What is?"),
                    new NPCDialogue(npc, "Everything! The wonders right here in this museum, collected from all corners of the land."),
                    new PlayerDialogue("I guess it is, you're right."),
                    new NPCDialogue(npc, "Matter of fact, I consider myself quite the collector. I keep a record of just about everything that I find!"),
                    get(player, npc));
        });

        NPCAction.register(8491, "get log", (player, npc) -> player.dialogue(get(player, npc)));

        ItemAction.registerInventory(ItemID.COLLECTION_LOG, 1, (player, item) -> {
            player.getCollectionLog().open(player);
            return;
        });

        InterfaceHandler.register(Interface.COLLECTION_LOG, h -> {
            h.actions[4] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogInfo.BOSS);
            h.actions[5] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogInfo.RAIDS);
            h.actions[6] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogInfo.CLUES);
            h.actions[7] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogInfo.MINIGAMES);
            h.actions[8] = (SimpleAction) p -> p.getCollectionLog().sendTab(p, CollectionLogInfo.OTHER);

            h.actions[11] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogInfo.BOSS);
            };

            h.actions[15] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogInfo.RAIDS);
            };

            h.actions[31] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogInfo.CLUES);
            };

            h.actions[26] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogInfo.MINIGAMES);
            };

            h.actions[33] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getCollectionLog().selectEntry(player, slot, CollectionLogInfo.OTHER);
            };

            h.actions[20] = (SimpleAction) p -> handleCombatAchievementsButton(p);

            h.actions[79] = (SimpleAction) p -> CollectionLog.handleClose(p);

            h.closedAction = (p, i) -> {
                p.getPacketSender().sendClientScript(101, "i", 11);
            };
        });
    }

    /**
     * Variables.
     */

    /* collection log interface info */
    public static int COLLECTION_LOG_ITEM_CONTAINER = 620;
    public static int COLLECTION_LOG_ID = 621;

    /* collection log item id */
    public static int COLLECTION_LOG_ITEM_ID = 22711;

    /* collection log client script tab info int params */
    /* item container, container, container, scroll bar */
    public static int[] bossParams = {40697866, 40697867, 40697868, 40697869};
    public static int[] raidParams = {40697870, 40697871, 40697872, 40697878};
    public static int[] clueParams = {40697879, 40697887, 40697888, 40697880};
    public static int[] minigameParams = {40697881, 40697882, 40697891, 40697883};
    public static int[] otherParams = {40697884, 40697889, 40697890, 40697885};

    /* collection log structures */
    public static int BOSSES_STRUCT = 471;
    public static int RAIDS_STRUCT = 472;
    public static int CLUES_STRUCT = 473;
    public static int MINIGAMES_STRUCT = 474;
    public static int OTHER_STRUCT = 475;

    /* collection log child ids */
    public static int BOSS_CHILD_ID = 11;
    public static int RAIDS_CHILD_ID = 15;
    public static int CLUES_CHILD_ID = 30;
    public static int MINIGAMES_CHILD_ID = 25;
    public static int OTHER_CHILD_ID = 32;

/*    @Expose
    @Getter
    private Map<Integer, Integer> collected = new HashMap<>();*/

    public static int TOTAL_COLLECTABLES;

    public static final int BLANK_ID = -1, FILLER_ID = 20594;

    public static int[] COLLECTION_LOG_ITEMS = new int[]{13262, 13273, 7979, 13274, 13275, 13276, 13277, 13265, 4151,
            4732, 4708, 4716, 4724, 4745, 4753, 4736, 4712, 4720, 4728, 4749, 4757, 4738, 4717, 4722, 4730, 4751, 4759, 4734, 4710, 4718, 4726, 4727, 4755, 4740,
            22372, 13178, 12603, 11920, 7158, 13227, 13229, 13231, 13245, 13233, 13249, 11995, 11928, 11931, 12651, 11785, 11814, 11838, 13256, 11818,
            11820, 11822, 12816, 12819, 12823, 12827, 12833, 12829, 11929, 11932, 11990, 12644, 12643, 12645, 6737, 6733, 6731, 6735, 6739, 6724, 6562, 12650, 11832, 11834, 11836, 11812,
            11818, 11820, 11822, 12646, 7418, 7416, 21748, 21730, 21736, 21739, 21742, 21745, 21726, 12647, 7981, 12885, 7158, 3140, 12653, 7980, 11286, 12655, 12004, 11905, 12007, 12649,
            11826, 11828, 11830, 11810, 11818, 11820, 11822, 12652, 11791, 11824, 11787, 11816, 20756, 13181, 11930, 11933, 21273, 19701, 21275, 19685, 6571, 19677,
            12648, 12002, 11998, 3140, 21291, 21295, 13225, 6570, 13177, 12605, 13179, 12601, 21992, 21907, 22006, 22106, 22111, 20693, 20716, 20718, 20704, 20708,
            20706, 20710, 20712, 20714, 20720, 12921, 13200, 13201, 12936, 12932, 12927, 12922, 12938, 12934, 20851, 22386, 20997, 21003, 21043, 13652, 21018, 21021, 21024, 21015, 21034,
            21079, 21012, 21000, 21047, 21027, 6573, 24670, 22388, 22390, 22392, 22394, 22396, 22473, 22486, 22324, 22481, 22326, 22327, 22328, 22477, 22446, 22494, 22496,
            22498, 22500, 22502, 20211, 20217, 20214, 23351, 20205, 20208, 20166, 2587, 2583, 2585, 3472, 2589, 2595, 2591, 2593, 3473, 2597, 7332, 7338, 7344, 7350, 7356,
            10306, 10308, 10310, 10312, 10314, 23366, 23369, 23372, 23378, 20193, 20184, 20187, 20190, 20196, 20178, 20169, 20172, 20175, 20181, 12225, 12227, 12229, 12233, 12231,
            12235, 12237, 12239, 12241, 12243, 12215, 12217, 12219, 12223, 12221, 12205, 12207, 12209, 12213, 12211, 7362, 7366, 7364, 7368, 23381, 23384, 7394, 7390, 7386, 7396,
            7392, 7388, 12453, 12449, 12445, 12455, 12451, 12447, 20199, 20202, 10458, 10464, 10462, 10460, 10468, 12193, 12195, 12253, 12255, 12265, 12267, 10316, 10320,
            10318, 10322, 10324, 2631, 2633, 2637, 12247, 10392, 12245, 12249, 12251, 10398, 10394, 10396, 12375, 23363, 10404, 10424, 10406, 10426, 10412, 10432, 10414, 10434, 10408,
            10428, 10410, 10430, 10366, 23354, 12297, 23360, 23357, 10280, 2577, 2579, 12598, 23413, 23389, 2605, 2599, 2601, 3474, 2603, 2613, 2607, 2609, 3475, 2611, 7334, 7340, 7346, 7352, 7358, 10296, 10298,
            10300, 10302, 10304, 23392, 23398, 23401, 23404, 12283, 12277, 12279, 12285, 12281, 12293, 12287, 12289, 12295, 12291, 7370, 7372, 7378, 7380, 10452, 10446, 10454, 10448,
            10456, 10450, 12203, 12197, 12201, 12199, 12259, 12261, 12257, 12263, 12271, 12273, 12269, 12275, 7319, 7323, 7321, 7327, 7325, 12309, 12311, 12313, 2645, 2647, 2649,
            12299, 12301, 12305, 12307, 12303, 12319, 20240, 20243, 12377, 20251, 20260, 20254, 20263, 20257, 20272, 20266, 20269, 12361, 12428, 12359, 20247, 23407, 23410, 10416, 10436,
            10418, 10438, 10400, 10420, 10402, 10422, 12315, 12339, 12317, 12341, 12347, 12343, 12345, 20275, 10364, 10282, 10334, 10330, 10332, 10336, 10338, 10340, 10342, 10344, 23242, 10346, 10348, 10350,
            10352, 3481, 3483, 3485, 3486, 3488, 20146, 20149, 20152, 20155, 20158, 20161, 2581, 22231, 23227, 23232, 23237, 2627, 2623, 2625, 3477, 2629, 2619, 2615, 2617, 3476, 2621, 2657, 2653, 2655, 3478, 2659, 2673, 2669,
            2671, 3480, 2675, 2665, 2661, 2663, 3479, 2667, 12466, 12460, 12462, 12464, 12468, 12476, 12470, 12472, 12474, 12478, 12486, 12480, 12482, 12484, 7336, 7342, 7348, 7360, 10286, 10288, 10290, 10292, 10294, 23209, 23212, 23215, 23218, 23221, 10390,
            10386, 10388, 10384, 19933, 23191, 10382, 10378, 10380, 10376, 19927, 23188, 10374, 10370, 10372, 10368, 19936, 23194, 12504, 12500, 12502, 12498, 19924, 23203, 12512, 12508, 12510, 12506, 19930, 23200, 12496, 12492, 12494, 12490, 11921, 23197,
            12331, 12333, 12327, 12329, 7376, 7384, 7374, 7382, 7400, 7399, 7398, 10470, 10440, 10472, 10442, 10474, 10444, 19912, 19915, 2651, 12323, 12321, 12325, 2639, 2641, 12516, 12514, 23224, 12518, 12520, 12522, 12524, 19918, 23206, 10354, 10284,
            12426, 12422, 12437, 12424, 10334, 10330, 10332, 10336, 10338, 10340, 10342, 10344, 23242, 10346, 10348, 10350, 10352, 23185, 12389, 12391, 3481, 3483, 3485, 3486, 3488, 20146, 20149, 20152, 20155, 20158, 20161, 23258, 23261, 23264, 23267, 23276, 23282, 12526, 12534, 12536,
            12532, 12538, 20002, 1250, 12528, 19997, 19994, 12596, 23249, 12381, 12383, 12385, 12387, 12397, 12439, 12393, 12395, 12351, 12441, 12443, 19958, 19964, 19967, 19961, 19970, 19973, 19979, 19982, 19976, 19985,
            19943, 19946, 19952, 19955, 19949, 12363, 12365, 12367, 12369, 23270, 23273, 12371, 20005, 12357, 12373, 12335, 19991, 19988, 12540, 12430, 12355, 12432, 12353, 12337, 23246, 23252, 23255,
            19730, 20014, 20011, 12426, 12422, 12437, 12424, 10334, 10330, 10332, 10336, 10338, 10340, 10342, 10344, 23242, 10346, 10348, 10350, 10352, 23339, 23336, 23342, 23345, 23185, 12389,
            12391, 3481, 3483, 3485, 3486, 3488, 20146, 20152, 20155, 20158, 20161, 23258, 23261, 23264, 23267, 23276, 23279, 23282, 20059, 20017, 20068, 20071, 20074, 20077, 20065, 20062, 22246, 20143, 22239, 22236, 23348,
            20128, 20131, 20137, 20134, 20140, 20035, 20038, 20044, 20047, 20041, 20095, 20098, 20101, 20107, 20104, 20080, 20092, 20086, 20089, 20083, 20125, 20116, 20113, 20122, 20119, 20020, 20023, 20026, 20032, 20029, 19724,
            20110, 20056, 20050, 20053, 20008, 3827, 3831, 3835, 12613, 12617, 12621, 3828, 23285, 23288, 23291, 23294, 23297, 23300, 23303, 23306, 23309, 23312, 23315, 23318, 23321, 23324, 23327, 12297,
            3827, 3831, 3835, 12613, 12617, 12621, 3828, 3832, 3836, 12614, 12618, 12622, 3829, 3833, 3837, 12615, 12619, 12623, 3830, 3834, 3838, 12616, 12620, 12624, 20220, 20223, 20226, 20232, 20229, 20235, 12402, 12411, 12406, 12404, 12405, 12403, 12408, 12407, 12409, 12642, 12410,
            21387, 7329, 7330, 7331, 10326, 10327, 20238, 10476, 4071, 25165, 4069, 4068, 4072, 4070, 11893, 25163, 4506, 25169, 4504, 4503, 4507, 4505, 11894, 25167, 4511, 25174, 4509, 4508, 4512, 4510, 11895, 25171, 4513, 4514, 4515, 4516, 11891, 11892, 11898, 11896, 11897, 11899, 11900, 11901, 12637, 12638, 12639,
            6908, 6912, 6914, 6918, 6916, 6924, 6920, 6922, 6889, 6926, 12703, 10548, 10550, 10549, 10547, 10551, 10555, 10552, 10553, 10589, 10564, 9469, 9470, 9472, 9475, 12851, 12854, 3470, 25442, 25445, 25448, 25451, 25454, 25438, 25434, 25436, 25440, 25474, 25476, 8841, 8839, 8840, 8842, 11663, 11665, 11664, 11666, 13072, 13073,
            5554, 5553, 5555, 5557, 5556, 10941, 10939, 10940, 10933, 13646, 13642, 13640, 13644, 13639, 13353, 13226, 8952, 8959, 8991, 8953, 8960, 8992, 8954, 8961, 8993, 8955, 8962, 8994, 8956, 8963, 8995, 8957, 8964, 8996, 8958, 8965, 8997, 8966, 8967, 8968, 8969, 8970, 8971, 8988, 8940, 8941, 13258, 13259, 13260, 13261,
            13262, 22746, 13178, 13247, 11995, 12651, 12816, 12644, 12643, 12645, 13225, 12650, 12646, 21748, 21291, 12647, 12653, 12655, 12649, 12652, 13181, 21273, 12648, 13177, 13179, 21992, 20693, 12921, 20851, 22473, 19730, 12703, 13320, 13321, 13322, 13324, 20659, 20661, 20663, 20665, 21509, 13071, 23495, 23760, 23757, 24491, 25348,
            20517, 20520, 20595, 22542, 22547, 22552, 22557, 21817, 21804, 22305, 22302, 22299, 21813, 21810, 21807, 21802, 21820, 19529, 19586, 19589, 19592, 19610, 19601, 7975, 7976, 7977, 7978, 7979, 20724, 21270, 20736, 20730, 4151, 4153, 6665, 6666, 11037, 11902, 20727, 8901, 21646, 21643, 21637, 6809, 10589, 11286, 4119, 4121, 4123,
            4125, 4127, 4129, 4131, 11840, 13265, 11908, 12004, 11235, 12002, 3140, 20849, 21028, 21009, 22804, 22963, 22960, 22957, 22988, 22971, 22973, 22969, 4109, 4111, 4113, 4115, 4117, 4099, 4101, 4103, 4105, 4107, 23047, 23050, 23053, 23056, 23059, 24268, 24288, 24291, 24294, 24777, 6798, 6799, 6800, 6801, 6802, 6803, 6804, 6805, 6806, 6807, 21439,
            8844, 8845, 8846, 8847, 8848, 8849, 8850, 12954, 13320, 13321, 13322, 13324, 20659, 20661, 20663, 20665, 12019, 12020, 12013, 12014, 12015, 12016, 13357, 13358, 13359, 13360, 13361, 13362, 13363, 13364, 13365, 13366, 13367, 13368, 13369, 13370, 13371, 13372, 13373, 13374, 13375, 13376, 13377, 13378, 13379, 13380, 13381, 6568, 6524, 6528, 6523, 6525,
            6526, 6522, 21298, 21301, 21304, 21509, 13071, 13576, 7991, 7993, 7989, 10976, 10977, 11942, 9044, 19679, 19681, 19683, 11338, 11335, 2366, 22100, 22103, 21918, 1249, 19707, 2997, 21838, 20439, 20436, 20442, 20433, 21343, 21345, 21392, 9007, 9008, 9010, 9011, 22374, 20754, 22875, 7536, 7538, 13392, 23522, 23943, 24000, 23959, 24034, 24037, 24040, 24046, 24043, 6571,
            13071, 2978, 2979, 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988, 2989, 2990, 2991, 2992, 2993, 2994, 2995, 6654, 6655, 6656, 6180, 6181, 6182, 7592, 7593, 7594, 7595, 7596, 3057, 3058, 3059, 3060, 3061, 6183, 20590, 25129, 25131, 25133, 25135, 25137,
            22746, 22966, 22988, 22983, 22971, 22973, 22969, 22804, 20849, 23064, 23077, 22840, 22846, 22844, 22842, 22838, 13258, 13259, 13260, 13261, 22994, 22883, 22885, 22881, 2425
    };

    /* opens the collection log */
    public void openCollectionLog(Player player) {

        //Achievements.Achievement.increase(player, AchievementType._2, 1);

        /* send collection log item container */
        player.getPacketSender().sendItems(-1, -1, 620, player.getCollectionLog().getItems());

        /* collection log interface */
        player.openInterface(InterfaceType.MAIN, COLLECTION_LOG_ID);

        /* bosses */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 10, 0, 37, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 11, 0, 37, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 12, 0, 37, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 29, 0, 37, 2);

        /* raids */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 14, 0, 2, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 15, 0, 2, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 16, 0, 2, 2);

        /* clues */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 23, 0, 9, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 31, 0, 9, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 32, 0, 9, 2);

        /* minigames */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 25, 0, 16, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 26, 0, 16, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 35, 0, 16, 2);

        /* other */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 28, 0, 21, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 33, 0, 21, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 34, 0, 21, 2);

        /* client scripts */
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab);//= 0
        if (player.collection_log_current_struct == 471) {
            player.getPacketSender().sendClientScript(2730, "iiiiii", 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct, player.collection_log_current_entry);
        } else if (player.collection_log_current_struct == 472) {
            player.getPacketSender().sendClientScript(2730, "iiiiii", 40697870, 40697871, 40697872, 40697878, player.collection_log_current_struct, player.collection_log_current_entry);
        } else if (player.collection_log_current_struct == 473) {
            player.getPacketSender().sendClientScript(2730, "iiiiii", 40697879, 40697887, 40697888, 40697880, player.collection_log_current_struct, player.collection_log_current_entry);
        } else if (player.collection_log_current_struct == 474) {
            player.getPacketSender().sendClientScript(2730, "iiiiii", 40697881, 40697882, 40697891, 40697883, player.collection_log_current_struct, player.collection_log_current_entry);
        } else if (player.collection_log_current_struct == 475) {
            player.getPacketSender().sendClientScript(2730, "iiiiii", 40697884, 40697889, 40697890, 40697885, player.collection_log_current_struct, player.collection_log_current_entry);
        } else {
            player.getPacketSender().sendClientScript(2730, "iiiiii", 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = 0);
        }
        //   player.getPacketSender().sendClientScript(2730, "iiiiii", 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = 0);//player.collection_log_current_struct = 471, player.collection_log_current_entry = 0

        sendAll = true;

    }

    /**
     * info about the collection log.
     */

    /* client script info - id: 2389, params: category value 0-4.*/
    /* values: 0=bosses, 1=raids, 2=clues, 3=minigames, 4=other */
    /* client script info - id: 2730, params: hashedId int params, structureId 471-475, slotId. */
    /* structureIds: 471=bosses, 472=raids, 473=clues, 474=minigames, 475=other */


    /* struct param 682, string. main category name. bosses, raids, clues, minigames, other. */
    /* struct param 683, enum id. enum id stores struct id info for selected item categories.*/
    /* struct param 683, int id. main category int value 0-4. */

    /* struct param 689, string name. the name for the item category. tob, xeric, easy trails, barrows, etc.*/
    /* struct param 690, id. id = enum id that stores that item categories*/




    /* sending the bosses tab */
    public static void sendBossesTab(Player player) {
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 0);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = 0);
    }

    /* sending the raids tab */
    public static void sendRaidsTab(Player player) {
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 1);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697870, 40697871, 40697872, 40697878, player.collection_log_current_struct = 472, player.collection_log_current_entry = 0);
    }

    /* sending the clues tab */
    public static void sendCluesTab(Player player) {
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 2);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697879, 40697887, 40697888, 40697880, player.collection_log_current_struct = 473, player.collection_log_current_entry = 0);
    }

    /* sending the minigames tab */
    public static void sendMinigamesTab(Player player) {
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 3);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697881, 40697882, 40697891, 40697883, player.collection_log_current_struct = 474, player.collection_log_current_entry = 0);
    }

    /* sending the other tab */
    public static void sendOtherTab(Player player) {
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 4);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697884, 40697889, 40697890, 40697885, player.collection_log_current_struct = 475, player.collection_log_current_entry = 0);
    }

    /* sending specific category tab */
    public static void sendCategoryTab(Player player, int structId, int categoryId, int subCategoryId, int[] params) {
        player.getPacketSender().sendClientScript(2389, "i", categoryId);
        player.getPacketSender().sendClientScript(2730, "iii", params.length, structId, subCategoryId);
    }

    public void handleLogTab(Player player, int childId, int tab) {
        tab = player.collection_log_current_tab;
        switch (childId) {
            case 4://bosses
                player.getPacketSender().sendClientScript(2389, "i", tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 471);
                return;
            case 5://raids
                player.getPacketSender().sendClientScript(2389, "i", tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 472);
                return;
            case 6://clues
                player.getPacketSender().sendClientScript(2389, "i", tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 473);
                return;
            case 7://minigames
                player.getPacketSender().sendClientScript(2389, "i", tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 474);
                return;
            case 8://other
                player.getPacketSender().sendClientScript(2389, "i", tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 475);
                return;
        }
    }

    public void handleLogStructure(Player player, int struct) {
        struct = player.collection_log_current_struct;
        switch (struct) {
            case 471://bosses
                player.getPacketSender().sendClientScript(2730, "iiiiii", 40697866, 40697867, 40697868, 40697869, struct, player.collection_log_current_entry);
                return;
            case 472://raids
                player.getPacketSender().sendClientScript(2730, "iiiiii", 40697870, 40697871, 40697872, 40697878, struct, player.collection_log_current_entry);
                return;
            case 473://clues
                player.getPacketSender().sendClientScript(2730, "iiiiii", 40697879, 40697887, 40697888, 40697880, struct, player.collection_log_current_entry);
                return;
            case 474://minigames
                player.getPacketSender().sendClientScript(2730, "iiiiii", 40697881, 40697882, 40697891, 40697883, struct, player.collection_log_current_entry);
                return;
            case 475://other
                player.getPacketSender().sendClientScript(2730, "iiiiii", 40697884, 40697889, 40697890, 40697885, struct, player.collection_log_current_entry);
                return;

        }
    }

    public void handleKillCountSlot(Player player, CollectionLogInfo info, int slot) {
        //      player.collection_log_current_entry = slot;
        Config.COLLECTION_LOG_KC_TAB.set(player, player.getCollectionLog().getCollectionLogKillCount(player, info, slot));
    }

    /* gets the kill count for the collection log entries */
    public int getCollectionLogKillCount(Player player, CollectionLogInfo info, int entry) {
        entry = player.collection_log_current_entry;//final int
        switch (info) {
            case BOSS:
                switch (entry) {
                    case 0://abyssal sire
                        return player.abyssalSireKills.getKills();
                    case 1://alchemical hydra
                        return player.alchemicalHydraKills.getKills();
                    case 2://barrows
                        return player.barrowsChestsLooted.getKills();
                    case 3://bryophyta
                        return player.bryophytaKills.getKills();
                    case 4://callisto
                        return player.callistoKills.getKills();
                    case 5://cerberus
                        return player.cerberusKills.getKills();
                    case 6://chaos ele
                        return player.chaosElementalKills.getKills();
                    case 7://chaos fanatic
                        return player.chaosFanaticKills.getKills();
                    case 8://commander zilyana
                        return player.commanderZilyanaKills.getKills();
                    case 9://corporeal beast
                        return player.corporealBeastKills.getKills();
                    case 10://crazy archaeologist
                        return player.crazyArchaeologistKills.getKills();
                    case 11://dagannoth kings
                        return CollectionLog.addSumMultipleDagKC(player);
                    case 12://the fight caves
                        return 0;
                    //return player.theFightCaveKills.getKills();
                    case 13://the gauntlet
                        return 0;
                    //return player.theGauntletKills.getKills();
                    case 14://general graardor
                        return player.generalGraardorKills.getKills();
                    case 15://giant mole
                        return player.giantMoleKills.getKills();
                    case 16://grotesque guardians
                        return player.grotesqueGuardianKills.getKills();
                    case 17://hespori
                        return player.hesporiKills.getKills();
                    case 18://inferno
                        return player.zukKills.getKills();
                    case 19://kalphite queen
                        return player.kalphiteQueenKills.getKills();
                    case 20://king black dragon
                        return player.kingBlackDragonKills.getKills();
                    case 21://kraken
                        return player.krakenKills.getKills();
                    case 22://kree'ara
                        return player.kreeArraKills.getKills();
                    case 23://k'ril
                        return player.krilTsutsarothKills.getKills();
                    case 24://obor
                        return player.oborKills.getKills();
                    case 25://sarachnis
                        return player.sarachnisKills.getKills();
                    case 26://scorpia
                        return player.scorpiaKills.getKills();
                    case 27://skotizo
                        return player.skotizoKills.getKills();
                    case 28://thermo devil
                        return player.thermonuclearSmokeDevilKills.getKills();
                    case 29://venenatis
                        return player.venenatisKills.getKills();
                    case 30://vet'ion
                        return player.vetionKills.getKills();
                    case 31://vorkath
                        return player.vorkathKills.getKills();
                    case 32://wintertodt
                        return player.wintertodtKills.getKills();
                    case 33://zalcano
                        return 0;
                    // return player.zalcanoKills.getKills();
                    case 34://zulrah
                        return player.zulrahKills.getKills();
                    default:
                        return 0;
                }
            case RAIDS:
                switch (entry) {
                    case 0://cox
                        return player.chambersofXericKills.getKills();
                    case 1://tob
                        return player.theatreOfBloodKills.getKills();
                }
            case CLUES:
                switch (entry) {
                    case 0://beginner treasure trails
                        return player.beginnerClueCount;
                    case 1://easy treasure trails
                        return player.easyClueCount;
                    case 2://medium treasure trails
                        return player.medClueCount;
                    case 3://hard treasure trails
                    case 6://shared hard
                        return player.hardClueCount;
                    case 4://elite treasure trails
                    case 7://shared elite
                        return player.eliteClueCount;
                    case 5://master treasure trails
                    case 8://shared master
                        return player.masterClueCount;
                    case 9://shared treasure trail rewards
                        return addSumMultipleClues(player);
                    default:
                        return 0;
                }
            case MINIGAMES:
                switch (entry) {
                    case 0://barbarian assault high-level gambles
                        return player.barbAssaultHighLevelGambles;
                    case 6://last man standing
                        return addLastManStandingKillcounts(player);
                    case 12://soul wars
                        return player.SoulWarsSpoilsofWarOpened;

                    default:
                        return 0;
                }
            case OTHER:
                switch (entry) {
                    case 8://glough's experiments kills
                        return 0;
                    case 12://revenant kills
                        return 0;
                    default:
                        return 0;
                }
        }
        return entry;
    }

    public static int addLastManStandingKillcounts(Player player) {
        int kills = player.lmsKills;
        int wins = player.lmsWins;
        int gamesPlayed = player.lmsGamesPlayed;
        return kills + wins + gamesPlayed;
    }

    /* adds up the killcount for dagannoth rex, supreme and prime */
    public static int addSumMultipleDagKC(Player player) {
        int sum1 = player.dagannothRexKills.getKills();
        int sum2 = player.dagannothSupremeKills.getKills();
        int sum3 = player.dagannothPrimeKills.getKills();
        //    System.out.println("Sum for dag kc = " + sum);
        return sum1 + sum2 + sum3;
    }

    /* adds up all the clue count from beginner -> master */
    public static int addSumMultipleClues(Player player) {
        int sum = 0;
        int sum1 = player.beginnerClueCount;
        int sum2 = player.easyClueCount;
        int sum3 = player.medClueCount;
        int sum4 = player.hardClueCount;
        int sum5 = player.eliteClueCount;
        int sum6 = player.masterClueCount;
        //    System.out.println("Sum for clue kc = " + sum);
        return sum1 + sum2 + sum3 + sum4 + sum5 + sum6;
    }

    /* adds up all the raids kc */
    public static int addRaidsKC(Player player) {
        int sum = 0;
        int sumCox = player.chambersofXericKills.getKills();
        int sumToB = player.theatreOfBloodKills.getKills();
        return sumCox + sumToB;
    }

    public static void addAllLogLoop(Player p) {
        for (int collectionLogItems = 0; collectionLogItems < CollectionLog.COLLECTION_LOG_ITEMS.length; collectionLogItems++) {
            if (p != null) {
                p.getCollectionLog().collect(CollectionLog.COLLECTION_LOG_ITEMS[collectionLogItems], 1);
            }
        }
    }

    public static void addAllLogLoopToBank(Player p) {
        for (int collectionLogItems = 0; collectionLogItems < CollectionLog.COLLECTION_LOG_ITEMS.length; collectionLogItems++) {
            if (p != null) {
                p.getBank().add(CollectionLog.COLLECTION_LOG_ITEMS[collectionLogItems], 1);
            }
        }
    }

    public static void addAllLogLoopToGIMStorage(Player p) {
        for (int collectionLogItems = 0; collectionLogItems < CollectionLog.COLLECTION_LOG_ITEMS.length; collectionLogItems++) {
            if (p != null) {
                p.getGIMStorage().add(CollectionLog.COLLECTION_LOG_ITEMS[collectionLogItems], 1);
            }
        }
    }

    public static void addAllLogItems(Player player) {

        addAllLogLoop(player);

       /* addLogItemAbyssalSire(player);
        addLogItemFarmersSet(player);
        addLogItemAlchemicalHydra(player);
        addLogItemBarrows(player);
        addLogItemBryophyta(player);
        addLogItemCallisto(player);
        addLogItemCerberus(player);
        addLogItemChaosElemental(player);
        addLogItemChaosFanatic(player);
        addLogItemCoX(player);
        addLogItemToB(player);
        addLogItemVorkath(player);
        addLogItemCommanderZilyana(player);
        addLogItemCorp(player);
        addLogItemCrazyArch(player);*/
    }

    /* adds farmers set log items to collection log */
    public static void addLogItemFarmersSet(Player player) {
        if (player.getAppearance().isMale()) {
            player.getCollectionLog().collect(13646, 1);//farmers strawhat
            player.getCollectionLog().collect(13642, 1);//farmers shirt
            player.getCollectionLog().collect(13640, 1);//farmers boro trousers
            player.getCollectionLog().collect(13644, 1);//farmers boots
        } else {
            player.getCollectionLog().collect(13647, 1);//farmers strawhat
            player.getCollectionLog().collect(13643, 1);//farmers shirt
            player.getCollectionLog().collect(13641, 1);//farmers boro trousers
            player.getCollectionLog().collect(13645, 1);//farmers boots
        }
    }

    /* adds abyssal sire log items to collection log */
    public static void addLogItemAbyssalSire(Player player) {
        player.getCollectionLog().collect(13262, 1);//abysal orphan
        player.getCollectionLog().collect(25624, 1);//unsired
        player.getCollectionLog().collect(7979, 1);//abyssal head
        player.getCollectionLog().collect(13274, 1);//bludgeon spine
        player.getCollectionLog().collect(13275, 1);//bludgeon claw
        player.getCollectionLog().collect(13276, 1);//bludgeon axon
        player.getCollectionLog().collect(13277, 1);//jar of miasma
        player.getCollectionLog().collect(13265, 1);//abyssal dagger
        player.getCollectionLog().collect(4151, 1);//abyssal whip
    }

    public static void addLogItemAbyssalSireAndInventory(Player player) {
        addLogItemAbyssalSire(player);

        player.getInventory().add(13262, 1);
        player.getInventory().add(25624, 1);
        player.getInventory().add(7979, 1);
        player.getInventory().add(13274, 1);
        player.getInventory().add(13275, 1);
        player.getInventory().add(13276, 1);
        player.getInventory().add(13277, 1);
        player.getInventory().add(13265, 1);
        player.getInventory().add(4151, 1);
    }

    /* adds alchemical hydra log items to collection log */
    public static void addLogItemAlchemicalHydra(Player player) {
        player.getCollectionLog().collect(22746, 1);//ikkle hydra
        player.getCollectionLog().collect(22966, 1);//hydra's claw
        player.getCollectionLog().collect(22988, 1);//hydra's tail
        player.getCollectionLog().collect(22983, 1);//hydra's leather
        player.getCollectionLog().collect(22971, 1);//hydra's fang
        player.getCollectionLog().collect(22973, 1);//hydra's eye
        player.getCollectionLog().collect(22969, 1);//hydra's heart
        player.getCollectionLog().collect(22804, 1);//dragon knife
        player.getCollectionLog().collect(20849, 1);//dragon throwing axe
        player.getCollectionLog().collect(23064, 1);//jar of chemicals
        player.getCollectionLog().collect(23077, 1);//alchemical hydra head
    }

    /* adds alchemical hydra log items to collection log and to inventory */
    public static void addLogItemAlchemicalHydraAndInventory(Player player) {
        addLogItemAlchemicalHydra(player);

        player.getInventory().add(22746, 1);//ikkle hydra
        player.getInventory().add(22966, 1);//hydra's claw
        player.getInventory().add(22988, 1);//hydra's tail
        player.getInventory().add(22983, 1);//hydra's leather
        player.getInventory().add(22971, 1);//hydra's fang
        player.getInventory().add(22973, 1);//hydra's eye
        player.getInventory().add(22969, 1);//hydra's heart
        player.getInventory().add(22804, 1);//dragon knife
        player.getInventory().add(20849, 1);//dragon throwing axe
        player.getInventory().add(23064, 1);//jar of chemicals
        player.getInventory().add(23077, 1);//alchemical hydra head
    }


    /* adds barrows log items to collection log */
    public static void addLogItemBarrows(Player player) {
        player.getCollectionLog().collect(4732, 1);//karils coif
        player.getCollectionLog().collect(4708, 1);//ahrims hood
        player.getCollectionLog().collect(4716, 1);//dharoks helm
        player.getCollectionLog().collect(4724, 1);//guthans helm
        player.getCollectionLog().collect(4745, 1);//torags helm
        player.getCollectionLog().collect(4753, 1);//veracs helm
        player.getCollectionLog().collect(4736, 1);//karils leathertop
        player.getCollectionLog().collect(4712, 1);//ahrims robetop
        player.getCollectionLog().collect(4720, 1);//dharoks platebody
        player.getCollectionLog().collect(4728, 1);//guthans platebody
        player.getCollectionLog().collect(4749, 1);//torags platebody
        player.getCollectionLog().collect(4757, 1);//veracs brassard
        player.getCollectionLog().collect(4738, 1);//karils leatheskirt
        player.getCollectionLog().collect(4714, 1);//ahrims robeskirt
        player.getCollectionLog().collect(4722, 1);//dharoks platelegs
        player.getCollectionLog().collect(4730, 1);//guthans chainskirt
        player.getCollectionLog().collect(4751, 1);//torags platelegs
        player.getCollectionLog().collect(4759, 1);//veracs plateskirt
        player.getCollectionLog().collect(4734, 1);//karils crossbow
        player.getCollectionLog().collect(4710, 1);//ahrims staff
        player.getCollectionLog().collect(4718, 1);//dharoks greataxe
        player.getCollectionLog().collect(4726, 1);//guthans warspear
        player.getCollectionLog().collect(4747, 1);//torags hammers
        player.getCollectionLog().collect(4755, 1);//veracs flail
        player.getCollectionLog().collect(4740, 1);//bolt rack
    }

    /* adds barrows log items to collection log and to inventory */
    public static void addLogItemBarrowsAndInventory(Player player) {
        addLogItemBarrows(player);

        player.getInventory().add(4732, 1);//karils coif
        player.getInventory().add(4708, 1);//ahrims hood
        player.getInventory().add(4716, 1);//dharoks helm
        player.getInventory().add(4724, 1);//guthans helm
        player.getInventory().add(4745, 1);//torags helm
        player.getInventory().add(4753, 1);//veracs helm
        player.getInventory().add(4736, 1);//karils leathertop
        player.getInventory().add(4712, 1);//ahrims robetop
        player.getInventory().add(4720, 1);//dharoks platebody
        player.getInventory().add(4728, 1);//guthans platebody
        player.getInventory().add(4749, 1);//torags platebody
        player.getInventory().add(4757, 1);//veracs brassard
        player.getInventory().add(4738, 1);//karils leatheskirt
        player.getInventory().add(4714, 1);//ahrims robeskirt
        player.getInventory().add(4722, 1);//dharoks platelegs
        player.getInventory().add(4730, 1);//guthans chainskirt
        player.getInventory().add(4751, 1);//torags platelegs
        player.getInventory().add(4759, 1);//veracs plateskirt
        player.getInventory().add(4734, 1);//karils crossbow
        player.getInventory().add(4710, 1);//ahrims staff
        player.getInventory().add(4718, 1);//dharoks greataxe
        player.getInventory().add(4726, 1);//guthans warspear
        player.getInventory().add(4747, 1);//torags hammers
        player.getInventory().add(4755, 1);//veracs flail
        player.getInventory().add(4740, 1);//bolt rack
    }

    /* adds bryophyta log items to collection log */
    public static void addLogItemBryophyta(Player player) {
        player.getCollectionLog().collect(22372, 1);//Bryophyta's essence
    }

    /* adds bryophyta log items to collection log and to inventory */
    public static void addLogItemBryophytaAndInventory(Player player) {
        CollectionLog.addLogItemBryophyta(player);

        player.getInventory().add(22372, 1);//Bryophyta's essence
    }

    /* adds callisto log items to collection log */
    public static void addLogItemCallisto(Player player) {
        player.getCollectionLog().collect(13178, 1);//callisto cub
        player.getCollectionLog().collect(12603, 1);//tyrannical ring
        player.getCollectionLog().collect(11920, 1);//dragon pickaxe
        player.getCollectionLog().collect(7158, 1);//dragon 2h sword
    }

    /* adds callisto log items to collection log and to inventory */
    public static void addLogItemCallistoAndInventory(Player player) {
        CollectionLog.addLogItemCallisto(player);

        player.getInventory().add(13178, 1);//callisto cub
        player.getInventory().add(12603, 1);//tyrannical ring
        player.getInventory().add(11920, 1);//dragon pickaxe
        player.getInventory().add(7158, 1);//dragon 2h sword
    }

    /* adds cerberus' log items to collection log */
    public static void addLogItemCerberus(Player player) {
        player.getCollectionLog().collect(13247, 1);//hellpuppy
        player.getCollectionLog().collect(13227, 1);//eternal crystal
        player.getCollectionLog().collect(13229, 1);//pegasian crystal
        player.getCollectionLog().collect(13231, 1);//primordial crystal
        player.getCollectionLog().collect(13245, 1);//jar of souls
        player.getCollectionLog().collect(13233, 1);//smouldering stone
        player.getCollectionLog().collect(13249, 1);//key master teleport
    }

    /* adds cerberus' log items to collection log and to inventory */
    public static void addLogItemCerberusAndInventory(Player player) {
        CollectionLog.addLogItemCerberus(player);

        player.getInventory().add(13247, 1);//hellpuppy
        player.getInventory().add(13227, 1);//eternal crystal
        player.getInventory().add(13229, 1);//pegasian crystal
        player.getInventory().add(13231, 1);//primordial crystal
        player.getInventory().add(13245, 1);//jar of souls
        player.getInventory().add(13233, 1);//smouldering stone
        player.getInventory().add(13249, 1);//key master teleport
    }

    /* adds chaos elemental log items to collection log */
    public static void addLogItemChaosElemental(Player player) {
        player.getCollectionLog().collect(11995, 1);//chaos ele pet
        player.getCollectionLog().collect(11920, 1);//dragon pickaxe
        player.getCollectionLog().collect(7158, 1);//dragon 2h sword
    }

    /* adds chaos elemental log items to collection log and to inventory */
    public static void addLogItemChaosElementalAndInventory(Player player) {
        CollectionLog.addLogItemChaosElemental(player);

        player.getInventory().add(11995, 1);//chaos ele pet
        player.getInventory().add(11920, 1);//dragon pickaxe
        player.getInventory().add(7158, 1);//dragon 2h sword
    }

    /* adds chaos fanatic log items to collection log */
    public static void addLogItemChaosFanatic(Player player) {
        player.getCollectionLog().collect(11995, 1);//chaos ele pet
        player.getCollectionLog().collect(11928, 1);//odium shard 1
        player.getCollectionLog().collect(11931, 1);//malediction shard 1
    }

    /* adds chaos fanatic log items to collection log and to inventory */
    public static void addLogItemChaosFanaticAndInventory(Player player) {
        CollectionLog.addLogItemChaosFanatic(player);

        player.getInventory().add(11995, 1);//chaos ele pet
        player.getInventory().add(11928, 1);//odium shard 1
        player.getInventory().add(11931, 1);//malediction shard 1
    }

    /* adds CoX log items to collection log */
    public static void addLogItemCoX(Player player) {
        player.getCollectionLog().collect(20851, 1);//olmlet
        player.getCollectionLog().collect(22386, 1);//metamorphic dust
        player.getCollectionLog().collect(20997, 1);//twisted bow
        player.getCollectionLog().collect(21003, 1);//elder maul
        player.getCollectionLog().collect(21043, 1);//kodai insignia
        player.getCollectionLog().collect(13652, 1);//dragon claws
        player.getCollectionLog().collect(21018, 1);//ancestral hat
        player.getCollectionLog().collect(21021, 1);//ancestral robe top
        player.getCollectionLog().collect(21024, 1);//ancestral robe bottom
        player.getCollectionLog().collect(21015, 1);//dinhs bulwark
        player.getCollectionLog().collect(21034, 1);//dex pray scroll
        player.getCollectionLog().collect(21079, 1);//arcane pray scroll
        player.getCollectionLog().collect(21012, 1);//dragon hunter crossbow
        player.getCollectionLog().collect(21000, 1);//twisted buckler
        player.getCollectionLog().collect(21047, 1);//torn prayer scroll
        player.getCollectionLog().collect(21027, 1);//dark relic
        player.getCollectionLog().collect(6573, 1);//onyx
        player.getCollectionLog().collect(24670, 1);//twisted ancestral colour kit
        player.getCollectionLog().collect(22388, 1);//xerics guard
        player.getCollectionLog().collect(22390, 1);//xerics warrior
        player.getCollectionLog().collect(22392, 1);//xerics sentinel
        player.getCollectionLog().collect(22394, 1);//xerics general
        player.getCollectionLog().collect(22396, 1);//xerics champion
    }

    /* adds CoX log items to collection log and inventory */
    public static void addLogItemCoXAndInventory(Player player) {
        CollectionLog.addLogItemCoX(player);

        player.getInventory().add(20851, 1);//olmlet
        player.getInventory().add(22386, 1);//metamorphic dust
        player.getInventory().add(20997, 1);//twisted bow
        player.getInventory().add(21003, 1);//elder maul
        player.getInventory().add(21043, 1);//kodai insignia
        player.getInventory().add(13652, 1);//dragon claws
        player.getInventory().add(21018, 1);//ancestral hat
        player.getInventory().add(21021, 1);//ancestral robe top
        player.getInventory().add(21024, 1);//ancestral robe bottom
        player.getInventory().add(21015, 1);//dinhs bulwark
        player.getInventory().add(21034, 1);//dex pray scroll
        player.getInventory().add(21079, 1);//arcane pray scroll
        player.getInventory().add(21012, 1);//dragon hunter crossbow
        player.getInventory().add(21000, 1);//twisted buckler
        player.getInventory().add(21047, 1);//torn prayer scroll
        player.getInventory().add(21027, 1);//dark relic
        player.getInventory().add(6573, 1);//onyx
        player.getInventory().add(24670, 1);//twisted ancestral colour kit
        player.getInventory().add(22388, 1);//xerics guard
        player.getInventory().add(22390, 1);//xerics warrior
        player.getInventory().add(22392, 1);//xerics sentinel
        player.getInventory().add(22394, 1);//xerics general
        player.getInventory().add(22396, 1);//xerics champion
    }

    /* adds ToB log items to collection log */
    public static void addLogItemToB(Player player) {
        player.getCollectionLog().collect(22473, 1);//lil zik
        player.getCollectionLog().collect(22486, 1);//scythe uncharged
        player.getCollectionLog().collect(22324, 1);//ghrazi rapier
        player.getCollectionLog().collect(22481, 1);//sanguinesti staff
        player.getCollectionLog().collect(22326, 1);//justiciar faceguard
        player.getCollectionLog().collect(22327, 1);//justiciar chestguard
        player.getCollectionLog().collect(22328, 1);//justiciar legguards
        player.getCollectionLog().collect(22477, 1);//avernic defender hilt
        player.getCollectionLog().collect(22446, 1);//vial of blood
        player.getCollectionLog().collect(22494, 1);//sinhaza shroud tier 1
        player.getCollectionLog().collect(22496, 1);//sinhaza shroud tier 2
        player.getCollectionLog().collect(22498, 1);//sinhaza shroud tier 3
        player.getCollectionLog().collect(22500, 1);//sinhaza shroud tier 4
        player.getCollectionLog().collect(22502, 1);//sinhaza shroud tier 5
        player.getCollectionLog().collect(25745, 1);//sanguine dust
        player.getCollectionLog().collect(25742, 1);//holy ornament kit
        player.getCollectionLog().collect(25744, 1);//sanguine ornament kit
    }

    /* adds ToB log items to collection log and inventory */
    public static void addLogItemToBAndInventory(Player player) {
        CollectionLog.addLogItemToB(player);

        player.getInventory().add(22473, 1);//lil zik
        player.getInventory().add(22486, 1);//scythe uncharged
        player.getInventory().add(22324, 1);//ghrazi rapier
        player.getInventory().add(22481, 1);//sanguinesti staff
        player.getInventory().add(22326, 1);//justiciar faceguard
        player.getInventory().add(22327, 1);//justiciar chestguard
        player.getInventory().add(22328, 1);//justiciar legguards
        player.getInventory().add(22477, 1);//avernic defender hilt
        player.getInventory().add(22446, 1);//vial of blood
        player.getInventory().add(22494, 1);//sinhaza shroud tier 1
        player.getInventory().add(22496, 1);//sinhaza shroud tier 2
        player.getInventory().add(22498, 1);//sinhaza shroud tier 3
        player.getInventory().add(22500, 1);//sinhaza shroud tier 4
        player.getInventory().add(22502, 1);//sinhaza shroud tier 5
        player.getInventory().add(25745, 1);//sanguine dust
        player.getInventory().add(25742, 1);//holy ornament kit
        player.getInventory().add(25744, 1);//sanguine ornament kit
    }

    /* adds vorkath log items to collection log */
    public static void addLogItemVorkath(Player player) {
        player.getCollectionLog().collect(21992, 1);//Vorki
        player.getCollectionLog().collect(21907, 1);//vorkaths head
        player.getCollectionLog().collect(11286, 1);//dragonic visage
        player.getCollectionLog().collect(22006, 1);//skeletal visage
        player.getCollectionLog().collect(22106, 1);//jar of decay
        player.getCollectionLog().collect(22111, 1);//dragonbone necklace
    }

    /* adds vorkath log items to collection log and to inventory */
    public static void addLogItemVorkathAndInventory(Player player) {
        CollectionLog.addLogItemVorkath(player);

        player.getInventory().add(21992, 1);//Vorki
        player.getInventory().add(21907, 1);//vorkaths head
        player.getInventory().add(11286, 1);//dragonic visage
        player.getInventory().add(22006, 1);//skeletal visage
        player.getInventory().add(22106, 1);//jar of decay
        player.getInventory().add(22111, 1);//dragonbone necklace
    }

    /* adds commander zilyana log items to collection log */
    public static void addLogItemCommanderZilyana(Player player) {
        player.getCollectionLog().collect(12651, 1);//pet zilyana
        player.getCollectionLog().collect(11785, 1);//armadyl crossbow
        player.getCollectionLog().collect(11814, 1);//saradomin hilt
        player.getCollectionLog().collect(11838, 1);//saradomin sword
        player.getCollectionLog().collect(13256, 1);//saradomins light
        player.getCollectionLog().collect(11818, 1);//godsword shard 1
        player.getCollectionLog().collect(11820, 1);//godsword shard 2
        player.getCollectionLog().collect(11822, 1);//godsword shard 3
    }

    /* adds commander zilyana log items to collection log and to inventory */
    public static void addLogItemCommanderZilanaAndInventory(Player player) {
        addLogItemCommanderZilyana(player);

        player.getInventory().add(12651, 1);//pet zilyana
        player.getInventory().add(11785, 1);//armadyl crossbow
        player.getInventory().add(11814, 1);//saradomin hilt
        player.getInventory().add(11838, 1);//saradomin sword
        player.getInventory().add(13256, 1);//saradomins light
        player.getInventory().add(11818, 1);//godsword shard 1
        player.getInventory().add(11820, 1);//godsword shard 2
        player.getInventory().add(11822, 1);//godsword shard 3
    }

    public static void addLogItemCorp(Player player) {
        player.getCollectionLog().collect(12816, 1);//pet dark core
        player.getCollectionLog().collect(12819, 1);//elysian sigil
        player.getCollectionLog().collect(12823, 1);//spectral sigil
        player.getCollectionLog().collect(12827, 1);//arcane sigil
        player.getCollectionLog().collect(30239, 1);//divine sigil
        player.getCollectionLog().collect(12833, 1);//holy elixir
        player.getCollectionLog().collect(12829, 1);//spirit shield
        player.getCollectionLog().collect(25521, 1);//jar of spirits
    }

    public static void addLogItemCorpAndInventory(Player player) {
        addLogItemCorp(player);

        player.getInventory().add(12816, 1);//pet dark core
        player.getInventory().add(12819, 1);//elysian sigil
        player.getInventory().add(12823, 1);//spectral sigil
        player.getInventory().add(12827, 1);//arcane sigil
        player.getInventory().add(30239, 1);//divine sigil
        player.getInventory().add(12833, 1);//holy elixir
        player.getInventory().add(12829, 1);//spirit shield
        player.getInventory().add(25521, 1);//jar of spirits
    }

    public static void addLogItemCrazyArch(Player player) {
        player.getCollectionLog().collect(11929, 1);//odium shard 2
        player.getCollectionLog().collect(12819, 1);//malediction shard 2
        player.getCollectionLog().collect(12823, 1);//fedora
    }

    public static void addLogItemCrazyArchAndInventory(Player player) {
        addLogItemCrazyArch(player);

        player.getInventory().add(11929, 1);//odium shard 2
        player.getInventory().add(12819, 1);//malediction shard 2
        player.getInventory().add(12823, 1);//fedora
    }

    public static void handleBossesButtonSlots(Player player, int slot) {
        player.getCollectionLog().handleKillCountSlot(player, CollectionLogInfo.BOSS, slot);
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 0);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = slot);
    }

    public static void handleRaidsButtonSlots(Player player, int slot) {
        player.getCollectionLog().handleKillCountSlot(player, CollectionLogInfo.RAIDS, slot);
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 1);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697870, 40697871, 40697872, 40697878, player.collection_log_current_struct = 472, player.collection_log_current_entry = slot);
    }

    public static void handleCluesButtonSlots(Player player, int slot) {
        player.getCollectionLog().handleKillCountSlot(player, CollectionLogInfo.CLUES, slot);
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 2);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697879, 40697887, 40697888, 40697880, player.collection_log_current_struct = 473, player.collection_log_current_entry = slot);
    }

    public static void handleMinigamesButtonSlots(Player player, int slot) {
        player.getCollectionLog().handleKillCountSlot(player, CollectionLogInfo.MINIGAMES, slot);
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 3);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697881, 40697882, 40697891, 40697883, player.collection_log_current_struct = 474, player.collection_log_current_entry = slot);
    }

    public static void handleOtherButtonSlots(Player player, int slot) {
        player.getCollectionLog().handleKillCountSlot(player, CollectionLogInfo.OTHER, slot);
        player.getPacketSender().sendClientScript(2389, "i", player.collection_log_current_tab = 4);
        player.getPacketSender().sendClientScript(2730, "iiiiii", 40697884, 40697889, 40697890, 40697885, player.collection_log_current_struct = 475, player.collection_log_current_entry = slot);
    }

    public static void handleClose(Player player) {
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
    }

    public static void handleCombatAchievementsButton(Player player) {
        player.sendMessage("This feature is not added yet.");
    }

    static {
        //   try {
        /*ItemAction.registerInventory(COLLECTION_LOG_ITEM_ID, 1, (player, item) -> {
            player.getCollectionLog().openCollectionLog(player);
            return;
        });*/

        //s  InterfaceHandler.register(COLLECTION_LOG_ID, h -> {

        /* handles the bosses button slots */
        //      h.actions[11] = (DefaultAction) (player, option, slot, itemId) -> handleBossesButtonSlots(player, slot);

        /* handles the raids button slots */
        //    h.actions[15] = (DefaultAction) (player, option, slot, itemId) -> handleRaidsButtonSlots(player, slot);

        /* handles the clues button slots */
        //    h.actions[31] = (DefaultAction) (player, option, slot, itemId) -> handleCluesButtonSlots(player, slot);

        /* handles the minigames button slots */
        //  h.actions[26] = (DefaultAction) (player, option, slot, itemId) -> handleMinigamesButtonSlots(player, slot);

        /* handles the other button slots */
        //  h.actions[33] = (DefaultAction) (player, option, slot, itemId) -> handleOtherButtonSlots(player, slot);

        /* handles the switching of tabs */
           /* h.actions[4] = (SimpleAction) p -> sendBossesTab(p);
            h.actions[5] = (SimpleAction) p -> sendRaidsTab(p);
            h.actions[6] = (SimpleAction) p -> sendCluesTab(p);
            h.actions[7] = (SimpleAction) p -> sendMinigamesTab(p);
            h.actions[8] = (SimpleAction) p -> sendOtherTab(p);

            h.actions[27] = (SimpleAction) p -> sendOtherTab(p);

            h.actions[20] = (SimpleAction) p -> handleCombatAchievementsButton(p);

            h.actions[79] = (SimpleAction) p -> CollectionLog.handleClose(p);*/

        /* on interface close */
            /*h.closedAction = (p, i) -> {
                p.getPacketSender().sendClientScript(101, "i", 11);
            };*/
        //  });

        //    }catch (Throwable e) {
        //         System.out.println(e);
        //     }
    }

    @Override
    protected CollectionLogItem newItem(int id, int amount, Map<String, String> attributes) {
        return new CollectionLogItem(id, amount, attributes);
    }

    @Override
    protected CollectionLogItem[] newArray(int size) {
        return new CollectionLogItem[size];
    }

    public boolean sendUpdates() {
        return sendUpdates(null);
    }

}