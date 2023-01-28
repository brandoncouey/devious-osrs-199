package io.ruin.model.inter.teleports;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.map.Position;
import io.ruin.utility.TeleportConstants;

public class TeleportList {

    public static Category[] categories;
    public static Teleport[] teleports;

    static {
        categories = Category.values();
        teleports = Teleport.values();
    }

    public enum Category {
        FAVORITES("Favorites", 1131, 10, 10),
        CITIES("Cities", 37, 10, 10, Teleport.AL_KHARID, Teleport.ARDOUGNE, Teleport.BRIMHAVEN, Teleport.BURTHORPE, Teleport.CAMELOT, Teleport.CANIFIS, Teleport.CATHERBY, Teleport.DRAYNOR_VILLAGE, Teleport.EDGEVILLE, Teleport.ENTRANA, Teleport.FALADOR, Teleport.GREAT_KOUREND, Teleport.KEBOS_LOWLANDS, Teleport.KARAMJA_DOCKS, Teleport.LUMBRIDGE, Teleport.LLEYTA, Teleport.LUNAR_ISLE, Teleport.MOS_LE_HARMLESS, Teleport.NEITIZNOT, Teleport.POLLNIVNEACH, Teleport.PORT_SARIM, Teleport.PRIFDDINAS, Teleport.RELLEKKA, Teleport.SEERS_VILLAGE, Teleport.SHILO_VILLAGE, Teleport.TREE_GNOME_STRONGHOLD, Teleport.VARROCK, Teleport.DARKMEYER, Teleport.WATERBIRTH_ISLAND, Teleport.YANILLE),
        WILDERNESS("Wilderness", 1046, 10, 10, Teleport.CHAOS_ELEMENTAL, Teleport.EAST_DRAGONS, Teleport.FEROX_ENCLAVE, Teleport.GRAVEYARD_OF_SHADOWS, Teleport.GREATER_DEMONS, Teleport.ICE_PLATEAU, Teleport.LAVA_MAZE, Teleport.MAGE_BANK, Teleport.REVENANTS, Teleport.RESOURCE_AREA, Teleport.WEST_DRAGONS),
        MONSTERS("Training", 3571, 10, 10, Teleport.EASY_TRAINING, Teleport.MED_TRAINING, Teleport.HARD_TRAINING, Teleport.AMMONITE_CRABS, Teleport.APE_ATOLL, Teleport.BANDIT_CAMP, Teleport.EXPERIMENTS, Teleport.GOBLINS, Teleport.LIZARD_CANYON, Teleport.MORYTANIA_SLAYER_TOWER, Teleport.ROCK_CRABS, Teleport.SAND_CRABS, Teleport.STRONGHOLD_SECURITY, Teleport.TROLLHEIM_TROLLS, Teleport.MANIACAL_MONKIES),
        SKILLING("Skilling", 1087, 10, 10, Teleport.RED_CHINS, Teleport.ABYSS, Teleport.AERIAL_FISHING, Teleport.BARB_FISHING, Teleport.COOKING_GUILD, Teleport.CRAFTING_GUILD, Teleport.DENSE_ESSENCE, Teleport.DESERT_HUNTER, Teleport.FELDIP_HILLS, Teleport.FISHING_GUILD, Teleport.GEM_MINE, Teleport.GNOME_AGILITY, Teleport.JATIZSO_MINE, Teleport.KARAMJA_DOCKS, Teleport.KARAMBWAN_FISHING, Teleport.KOUREND_ANGLERS, Teleport.MINING_GUILD, Teleport.MISCELLANIA, Teleport.PISCATORIS_FISHING_COLONY, Teleport.PURE_ESSENCE_MINING, Teleport.RANGING_GUILD, Teleport.WOODCUTTING_GUILD, Teleport.WOODLAND_HUNTER),
        BOSSES("Bosses", 1524, 10, 10, Teleport.DONATION_BOSS, Teleport.VOTE_BOSS, Teleport.ABYSSAL_SIRE, Teleport.CERBERUS, Teleport.CORPOREAL_BEAST, Teleport.DAGGANOTH_LAIR, Teleport.GALVEK, Teleport.GIANT_MOLE, Teleport.GODWARS_DUNGEON, Teleport.KALPHITE_QUEEN, Teleport.TOB, Teleport.KING_BLACK_DRAGON, Teleport.KRAKEN, Teleport.NIGHTMARE_OF_ASHIHAMA, Teleport.SMOKE_DEVIL, Teleport.VORKATH, Teleport.NEX, Teleport.ZULRAH),
        MINIGAMES("Minigames", 1486, 10, 10, Teleport.BARROWS, Teleport.BLAST_FURNACE, Teleport.CASTLE_WARS, Teleport.CHAMBERS_OF_XERIC, Teleport.DUEL_ARENA, Teleport.FIGHT_CAVE, Teleport.INFERNO, Teleport.LAST_MAN_STANDING, Teleport.MOTHERLODE_MINE, Teleport.PEST_CONTROL, Teleport.PURO_PURO, Teleport.PYRAMID_PLUNDER, Teleport.WARRIORS_GUILD, Teleport.WINTERTODT),

        DUNGEONS("Dungeons", 1534, 10, 10, Teleport.ANCIENT_CAVERN, Teleport.ASGARNIA_ICE_CAVE, Teleport.BASILISK_KNIGHT_CAVE, Teleport.BRIMHAVEN_DUNGEON, Teleport.BRINE_RAT_CAVERN, Teleport.CATACOMBS_OF_KOUREND, Teleport.CRASH_SITE_CAVERN, Teleport.EDGEVILLE_DUNGEON, Teleport.FIRE_CHASM, Teleport.FORTHOS_DUNGEON, Teleport.GODWARS_DUNGEON, Teleport.LITHKREN_LABORATORY, Teleport.Lighthouse_Dungeon, Teleport.LUMBRIDGE_SWAMPCAVES, Teleport.MOS_LE_HARMLESS_DUNGEON, Teleport.MOUNT_KARUULM, Teleport.MOURNER_TUNNELS, Teleport.RELLEKA_SLAYER_CAVE, Teleport.TAVERLY_DUNGEON, Teleport.SMOKE_DUNGEON, Teleport.STRONGHOLD_SLAYER_CAVE, Teleport.SLAYER_TOWER, Teleport.JUNGLE_DEMONS, Teleport.Waterfall_Dungeon),

        MISC("Misc", 1503, 10, 10, Teleport.CHAMPIONS_GUILD, Teleport.GRAND_EXCHANGE, Teleport.MYTHS_GUILD, Teleport.BRIMSTAIL, Teleport.GNOME_GOLD);

        private final String name;
        private final Teleport[] teleports;
        private final int spriteId;

        private int sw = 24, sh = 24;

        Category(String name, int spriteId, Teleport... teleports) {
            this.name = name;
            this.spriteId = spriteId;
            this.teleports = teleports;
        }

        Category(String name, int spriteId, int sw, int sh, Teleport... teleports) {
            this.name = name;
            this.spriteId = spriteId;
            this.teleports = teleports;
            this.sw = sw;
            this.sh = sh;
        }

        public String getName() {
            return name;
        }

        public Teleport[] getTeleports() {
            return teleports;
        }

        public int getSpriteId() {
            return spriteId;
        }

        public int getSw() {
            return sw;
        }

        public int getSh() {
            return sh;
        }
    }

    // New Teleports 701 +
    public enum Teleport {
        //CITIES
        LUMBRIDGE(0, TeleportConstants.LUMBRIDGE),
        VARROCK(1, TeleportConstants.VARROCK),
        EDGEVILLE(2, TeleportConstants.EDGEVILLE),
        FALADOR(3, TeleportConstants.FALADOR),
        ARDOUGNE(4, TeleportConstants.ARDOUGNE),
        CAMELOT(5, TeleportConstants.CAMELOT),
        SEERS_VILLAGE(6, TeleportConstants.SEERS),
        YANILLE(7, TeleportConstants.YANILLE),
        TREE_GNOME_STRONGHOLD(8, TeleportConstants.TREE_GNOME_STRONGHOLD),
        BRIMHAVEN(9, TeleportConstants.BRIMHAVEN),
        PORT_SARIM(10, TeleportConstants.PORT_SARIM),
        AL_KHARID(11, TeleportConstants.AL_KHARID, false, false),
        CANIFIS(12, TeleportConstants.CANIFIS),
        BURTHORPE(13, TeleportConstants.BURTHORPE, false, false),
        CATHERBY(14, TeleportConstants.CATHERBY, false, false),
        DRAYNOR_VILLAGE(15, TeleportConstants.DRAYNOR_VILLAGE),
        RELLEKKA(16, TeleportConstants.RELLEKKA, false, false),
        SHILO_VILLAGE(17, TeleportConstants.SHILO_VILLAGE, false, false),
        MOS_LE_HARMLESS(18, TeleportConstants.MOS_LE_HARMLESS, false, false),
        POLLNIVNEACH(20, TeleportConstants.POLLNIVNEACH, false, false),
        WATERBIRTH_ISLAND(21, TeleportConstants.WATERBIRTH_ISLAND, false, false),
        NEITIZNOT(22, TeleportConstants.NEITIZNOT, false, false),
        LUNAR_ISLE(23, TeleportConstants.LUNAR_ISLE, false, false),
        GREAT_KOUREND(24, TeleportConstants.GREAT_KOUREND, false, false),
        KEBOS_LOWLANDS(25, TeleportConstants.KEBOS_LOWLANDS, false, false),
        PRIFDDINAS(26, TeleportConstants.PRIFDDINAS, false, false),
        LLEYTA(27, TeleportConstants.LLEYTA, false, false),
        DARKMEYER(28, TeleportConstants.DARKMEYER, false, false),
        ENTRANA(29, TeleportConstants.ENTRANA),

        //DUNGEONS
        TAVERLY_DUNGEON(30, TeleportConstants.TAVERLY_DUNGEON, "blue dragons", "black dragons", "chaos druids", "greater demons", "hellhounds"),
        BRIMHAVEN_DUNGEON(31, TeleportConstants.BRIMHAVEN_DUNGEON, "greater demons", "red dragons", "iron dragons", "steel dragons"),
        GODWARS_DUNGEON(32, TeleportConstants.GODWARS_DUNGEON, false, false, "bandos", "saradomin", "zamorak", "armadyl", "gwd"),
        EDGEVILLE_DUNGEON(33, TeleportConstants.EDGEVILLE_DUNGEON, "hill giants", "black demons", "earth warriors", "chaos druids"),
        STRONGHOLD_SLAYER_CAVE(34, TeleportConstants.STRONGHOLD_SLAYER_CAVE),
        RELLEKA_SLAYER_CAVE(35, TeleportConstants.RELLEKA_SLAYER_CAVE),
        CATACOMBS_OF_KOUREND(36, TeleportConstants.CATACOMBS, false, false),
        MOUNT_KARUULM(37, "Karuulm Slayer Dungeon", TeleportConstants.KARUULM_SLAYER, false, false),
        LITHKREN_LABORATORY(38, TeleportConstants.LITHKREN_LABORATORY),
        CRASH_SITE_CAVERN(39, TeleportConstants.CRASH_SITE_CAVERN, "demonic gorillas"),
        MOS_LE_HARMLESS_DUNGEON(40, TeleportConstants.MOS_LE_HARMLESS_DUNG, false, false),
        BASILISK_KNIGHT_CAVE(41, TeleportConstants.BASILISK_KNIGHT_CAVE, false, false),
        FIRE_CHASM(42, TeleportConstants.FIRE_CHASM, false, false),
        MOURNER_TUNNELS(43, TeleportConstants.MOURNER_TUNNELS, false, false),
        SMOKE_DUNGEON(44, TeleportConstants.SMOKE_DUNGEON, false, false),
        LIZARD_CAVERN(45, TeleportConstants.LIZARD_CAVERN, false, false),
        LUMBRIDGE_SWAMPCAVES(46, TeleportConstants.LUMBRIDGE_SWAMPCAVES, false, false),
        ASGARNIA_ICE_CAVE(47, TeleportConstants.ASGANIAN_ICEDUNGEON, false, false),
        ANCIENT_CAVERN(48, TeleportConstants.ANCIENT_CAVERN, false, false),
        FORTHOS_DUNGEON(49, TeleportConstants.FORTHOS_DUNGEON, false, false),
        BRINE_RAT_CAVERN(50, TeleportConstants.BRINE_RAT_CAVERN, false, false),
        JUNGLE_DEMONS(51, TeleportConstants.JUNGLE_DEMONS, false, false),
        SLAYER_TOWER(52, TeleportConstants.SLAYER_TOWER, false, false),
        Waterfall_Dungeon(53, TeleportConstants.Waterfall_Dungeon, false, false),
        Lighthouse_Dungeon(54, TeleportConstants.Lighthouse_Dungeon, false, false),


        //TRAINING
        EASY_TRAINING(55, "Easy Training", TeleportConstants.CHICKENS, false, false),
        MED_TRAINING(56, "Medium Training", TeleportConstants.COWS, false, false),
        HARD_TRAINING(57, "Hard Training", TeleportConstants.HARD_ZONE, false, false),
        ROCK_CRABS(58, TeleportConstants.ROCK_CRABS, false, false),
        EXPERIMENTS(59, TeleportConstants.EXPERIMENTS),
        APE_ATOLL(60, TeleportConstants.APE_ATOLL, false, false),
        SAND_CRABS(61, TeleportConstants.SAND_CRABS, false, false, "crabs", "sand", "crab"),
        AMMONITE_CRABS(62, TeleportConstants.AMMONITE_CRABS, false, false, "crabs", "ammy", "crab"),
        BANDIT_CAMP(63, TeleportConstants.BANDIT_CAMP, false, false),
        GOBLINS(64, TeleportConstants.GOBLINS, false, false),
        OGRES(65, TeleportConstants.OGRES, false, false),
        MORYTANIA_SLAYER_TOWER(66, TeleportConstants.MORYTANIA_SLAYER_TOWER, false, false),
        // YAKS(67, TeleportConstants.YAKS, false, false),
        MANIACAL_MONKIES(67, TeleportConstants.MANIACAL_MONKIES, false, true),
        STRONGHOLD_SECURITY(68, TeleportConstants.STRONGHOLD_SECURITY, false, false),
        TROLLHEIM_TROLLS(69, TeleportConstants.TROLLHEIM_TROLLS, false, false),


        //MINIGAMES
        DUEL_ARENA(70, TeleportConstants.DUEL_ARENA, false, false, "duel", "battle", "fight"),
        MOTHERLODE_MINE(71, TeleportConstants.MOTHERLODE_MINE, false, false, "mining", "motherload", "mother", "motherlode"),
        PURO_PURO(72, TeleportConstants.PURO_PURO, false, false, "puro", "puro puro", "implings", "imp", "hunter"),
        PYRAMID_PLUNDER(73, TeleportConstants.PYRAMID_PLUNDER, false, false, "pyramid"),
        WARRIORS_GUILD(74, TeleportConstants.WARRIORS_GUILD, false, false, "defender"),
        LAST_MAN_STANDING(75, TeleportConstants.LAST_MAN_STANDING, false, false, "lms"),
        PEST_CONTROL(76, TeleportConstants.PEST_CONTROL, false, false, "pc", "pest"),
        BARROWS(77, TeleportConstants.BARROWS, false, false, "barrow"),
        BLAST_FURNACE(78, TeleportConstants.BLAST_FURNACE, false, false, "blast"),
        CHAMBERS_OF_XERIC(79, TeleportConstants.CHAMBERS_OF_XERIC, false, false, "cox"),
        WINTERTODT(80, TeleportConstants.WINTERTODT, false, false, "fm", "firemaking"),
        CASTLE_WARS(81, TeleportConstants.CASTLE_WARS, false, false, "castle", "wars"),
        FIGHT_CAVE(83, TeleportConstants.FIGHT_CAVE, false, false, "fire", "fire cape"),
        INFERNO(84, TeleportConstants.INFERNO, false, false, "fire", "infernal cape"),


        //WILDERNESS
        AGILITY_COURSE(85, TeleportConstants.WILD_AGILITY, true, false),
        RESOURCE_AREA(86, TeleportConstants.RESOURCE_AREA, true, false),
        EAST_DRAGONS(87, TeleportConstants.EAST_DRAGONS, true, false, "easts", "green dragons"),
        REVENANTS(88, TeleportConstants.REVENANTS, true, false, "revs"),
        MAGE_BANK(89, TeleportConstants.MAGE_BANK, true, false, "mb"),
        GREATER_DEMONS(90, TeleportConstants.GREATER_DEMONS, true, false, "gdz"),
        ICE_PLATEAU(91, TeleportConstants.ICE_PLATEAU, true, false),
        GRAVEYARD_OF_SHADOWS(92, TeleportConstants.GRAVEYARD_OF_SHADOWS, true, false),
        LAVA_MAZE(93, TeleportConstants.LAVA_MAZE, true, false),
        CHAOS_ELEMENTAL(94, TeleportConstants.CHAOS_ELEMENTAL, true, false, "rogues"),
        WEST_DRAGONS(95, TeleportConstants.WEST_DRAGONS, true, false, "wests", "green dragons"),


        //SKILLING
        COOKING_GUILD(96, TeleportConstants.COOKING_GUILD, false, false),
        WOODCUTTING_GUILD(97, TeleportConstants.WOODCUTTING_GUILD, false, false),
        FISHING_GUILD(98, TeleportConstants.FISHING_GUILD, false, false),
        CRAFTING_GUILD(99, TeleportConstants.CRAFTING_GUILD, false, false),
        RANGING_GUILD(100, TeleportConstants.RANGING_GUILD, false, false),
        MINING_GUILD(101, TeleportConstants.MINING_GUILD, false, false),
        MISCELLANIA(102, TeleportConstants.MISCELLANIA, false, false),
        FARMING_GUILD(103, TeleportConstants.FARMING_GUILD, false, false, "farming"),
        PURE_ESSENCE_MINING(104, TeleportConstants.PURE_ESSENCE_MINING, false, false),
        PISCATORIS_FISHING_COLONY(105, TeleportConstants.PISCATORIS_FISHING_COLONY, false, false),
        RED_CHINS(106, "Red Chinchompas", TeleportConstants.RED_CHINS, false, false),
        AERIAL_FISHING(107, TeleportConstants.AERIAL_FISHING, false, false),
        KOUREND_ANGLERS(108, "Piscarilius anglers", TeleportConstants.KOUREND_ANGLERS, false, false),
        BARB_FISHING(109, TeleportConstants.BARB_FISHING, false, false),
        GEM_MINE(110, TeleportConstants.GEM_MINE, false, false),
        GNOME_AGILITY(111, TeleportConstants.GNOME_AGILITY, false, false),
        FELDIP_HILLS(112, TeleportConstants.FELDIP_HILLS, false, false),
        KARAMJA_DOCKS(113, TeleportConstants.KARAMJA_DOCKS, false, false),
        WOODLAND_HUNTER(114, TeleportConstants.WOODLAND_HUNTER, false, false),
        DESERT_HUNTER(115, TeleportConstants.DESERT_HUNTER, false, false),
        JATIZSO_MINE(116, TeleportConstants.JATIZSO_MINE, false, false),
        KARAMBWAN_FISHING(117, TeleportConstants.KARAMBWAN_FISHING, false, false),
        DENSE_ESSENCE(140, TeleportConstants.DENSE_ESSENCE, false, false),
        FEROX_ENCLAVE(141, TeleportConstants.FEROX_ENCLAVE, false, false),


        //BOSSES
        VORKATH(118, TeleportConstants.VORKATH, false, false),
        TOB(119, "Theatre Of Blood", TeleportConstants.TOB, false, false),
        CORPOREAL_BEAST(120, TeleportConstants.CORPOREAL_BEAST, false, false),
        CERBERUS(121, TeleportConstants.CERBERUS, false, false),
        KRAKEN(122, TeleportConstants.KRAKEN, false, false),
        KING_BLACK_DRAGON(123, TeleportConstants.KING_BLACK_DRAGON, true, false),
        ZULRAH(124, TeleportConstants.ZULRAH, false, false),
        GIANT_MOLE(125, TeleportConstants.GIANT_MOLE, false, false),
        KALPHITE_QUEEN(126, TeleportConstants.KALPHITE_QUEEN, false, false),
        SMOKE_DEVIL(127, TeleportConstants.SMOKE_DEVIL, false, false),
        ALCHEMICAL_HYDRA(128, TeleportConstants.ALCHEMICAL_HYDRA, false, false),
        DAGGANOTH_LAIR(129, TeleportConstants.DAGGANOTH_LAIR, false, false),
        GALVEK(130, TeleportConstants.GALVEK, true, false),
        LIZARD_CANYON(131, TeleportConstants.LIZARD_CANYON, false, false),
        ABYSSAL_SIRE(132, TeleportConstants.ABYSSAL_SIRE, false, false),
        NIGHTMARE_OF_ASHIHAMA(133, TeleportConstants.NIGHTMARE_OF_ASHIHAMA, false, false),
        VOTE_BOSS(134, "Vote Boss", TeleportConstants.VOTE_BOSS, false, false),
        DONATION_BOSS(135, "Donation Boss", TeleportConstants.DONATION_BOSS, false, false),

        //MISC
        GRAND_EXCHANGE(136, TeleportConstants.GRAND_EXCHANGE, false, false, "exchange", "grand", "grand exchange"),
        CHAMPIONS_GUILD(137, TeleportConstants.CHAMPIONS_GUILD, false, false),
        MYTHS_GUILD(138, TeleportConstants.MYTHS_GUILD, false, false),
        ABYSS(139, TeleportConstants.ABYSS, false, false),
        NEX(142, TeleportConstants.NEX, false, false),
        BRIMSTAIL(143, TeleportConstants.BRIMSTAIL, false, false),
        GNOME_GOLD(144, TeleportConstants.GNOME_GOLD, false, false),
        ;

        private final int id;
        private final String name;
        private final boolean wild;
        private final boolean multi;
        private final Position position;
        private final String[] tags;

        Teleport(int id, Position position, String... tags) {
            this.id = id;
            this.name = StringUtils.fixCaps(name().replace("_", " ").toLowerCase());
            wild = false;
            multi = false;
            this.tags = tags;
            this.position = position;
        }

        Teleport(int id, Position position, boolean wild, boolean multi, String... tags) {
            this.id = id;
            this.name = StringUtils.fixCaps(name().replace("_", " ").toLowerCase());
            this.wild = wild;
            this.multi = multi;
            this.tags = tags;
            this.position = position;
        }

        Teleport(int id, String name, Position position, boolean wild, boolean multi, String... tags) {
            this.id = id;
            this.name = name;
            this.wild = wild;
            this.multi = multi;
            this.tags = tags;
            this.position = position;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public boolean isWild() {
            return wild;
        }

        public boolean isMulti() {
            return multi;
        }

        public String[] getTags() {
            return tags;
        }

        public Position getPosition() {
            return position;
        }
    }

    public static Teleport getTeleportForId(int id) {
        for (Teleport tp : teleports) {
            if (tp.id == id)
                return tp;
        }

        return null;
    }
}