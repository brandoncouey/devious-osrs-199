package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import java.util.Arrays;
import java.util.Map;

import static io.ruin.model.skills.construction.actions.Costume.*;

public enum CostumeStorage {
    FANCY_DRESS_BOX(33405, new int[]{
            10630,
            10631,
            10722,
            10632,
            10633,
            10634,
            10635,
    }, MIME_OUTFIT, ROYAL_FROG_OUTFIT, FROG_MASK, ZOMBIE_OUTFIT, CAMO_OUTFIT, LEDERHOSEN_OUTFIT, SHADE_ROBES),

    ARMOUR_CASE(33405, new int[]{
            10611,
            10612,
            13073,
            11677,
            11676,
            11675,
            10613,
            10615,
            10614,
            10616,
            10617,
            10618,
            10619,
            10620,
            10621,
            10622,
            10625,
            10624,
            10623,
            10629,
            10627,
            10626,
            10628,
            10884,
            10946,
            11136,
            11275,
            12014,
            13259,
            13360,
            13365,
            13370,
            13375,
            13380,
            13386,
            13647,
            19690,
            20839,
            21019,
            21299,
            19688,
            10552,
            10553,
            10554,
            10556,
            10549,
            10551,
            10550,
            10548,
            22327,
    },
            CASTLE_WARS_ARMOUR,
            VOID_KNIGHT_ARMOUR,
            ELITE_VOID_ARMOUR,
            VOID_MELEE_HELM,
            VOID_RANGER_HELM,
            VOID_MAGE_HELM,
            ROGUE_ARMOUR,
            SPINED_HELM,
            ROCKSHELL_ARMOUR,
            TRIBAL_MASK_POISON,
            TRIBAL_MASK_DISEASE,
            TRIBAL_MASK_COMBAT,
            WHITE_KNIGHT_ARMOUR,
            INITIATE_ARMOUR,
            PROSELYTE_ARMOUR,
            MOURNER_GEAR,
            GRAAHK_GEAR,
            LARUPIA_GEAR,
            KYATT_GEAR,
            POLAR_CAMO,
            JUNGLE_CAMO,
            WOODLAND_CAMO,
            DESERT_CAMO,
            BUILDERS_COSTUME,
            LUMBERJACK_COSTUME,
            BOMBER_JACKET_COSTUME,
            HAM_ROBES,
            PROSPECTOR_KIT,
            ANGLERS_OUTFIT,
            SHAYZIEN_ARMOUR_1,
            SHAYZIEN_ARMOUR_2,
            SHAYZIEN_ARMOUR_3,
            SHAYZIEN_ARMOUR_4,
            SHAYZIEN_ARMOUR_5,
            XERICIAN_ROBES,
            FARMERS_OUTFIT,
            CLUE_HUNTER_OUTFIT,
            CORRUPTED_ARMOUR,
            ANCESTRAL_ROBES,
            OBSIDIAN_ARMOUR,
            HELM_OF_RAEDWALD,
            FIGHTER_TORSO,
            PENANCE_BOOTS,
            PENANCE_GLOVES,
            PENANCE_SKIRT,
            PENANACE_FIGHTER_HAT,
            PENANCE_RANGER_HAT,
            PENANCE_RUNNER_HAT,
            PENANCE_HEALER_HAT,
            JUSTICIAR_ARMOUR),

    MAGIC_WARDROBE(33405, new int[]{
            10602,
            10603,
            10604,
            10605,
            10606,
            10607,
            10608,
            10609,
            10610,
            11851,
            13580,
            13628,
            13604,
            13592,
            13616,
            13668,
            21062,
            8960,
            8961,
            8962,
            8963,
            8964,
            8965,
            8966,
            20596,
            20440,
            20709,
    },
            BLUE_MYSTIC,
            DARK_MYSTIC,
            LIGHT_MYSTIC,
            SKELETAL,
            INFINITY,
            SPLITBARK,
            GHOSTLY,
            MOONCLAN_ROBES,
            LUNAR_ROBES,
            GRACEFUL,
            GRACEFUL_ARCEUUS,
            GRACEFUL_HOSIDIUS,
            GRACEFUL_LOVAKENGJ,
            GRACEFUL_PISCARILIUS,
            GRACEFUL_SHAYZIEN,
            GRACEFUL_KOUREND,
            GRACEFUL_AGILITY_ARENA,
            BLUE_NAVAL,
            GREEN_NAVAL,
            RED_NAVAL,
            BROWN_NAVAL,
            BLACK_NAVAL,
            PURPLE_NAVAL,
            GREY_NAVAL,
            ELDER_CHAOS_DRUID,
            EVIL_CHICKEN_COSTUME,
            PYROMANCER_OUTFIT),

    CAPE_RACK(33405, new int[]{
            10636,
            10637,
            10638,
            10639,
            2413,
            2414,
            2415,
            10640,
            10642,
            10641,
            10648,
            10649,
            10659,
            10655,
            10652,
            10662,
            10660,
            10658,
            10653,
            10650,
            10645,
            10656,
            10644,
            10643,
            10646,
            10654,
            10657,
            10651,
            10661,
            10647,
            10663,
            19477,
            13225,
            10664,
            10665,
            13283,
            21296,
            21440,
            21792,
            21794,
            21796,
            22115,
            22389,
            22391,
            22393,
            22395,
            22397,
            22495,
            22497,
            22499,
            22501,
            22503,
    },
            CAPE_OF_LEGENDS,
            OBSIDIAN_CAPE,
            FIRE_CAPE,
            WILDERNESS_CAPES,
            SARADOMIN_CAPE,
            GUTHIX_CAPE,
            ZAMORAK_CAPE,
            ATTACK_CAPE,
            DEFENCE_CAPE,
            STRENGTH_CAPE,
            HITPOINTS_CAPE,
            AGILITY_CAPE,
            COOKING_CAPE,
            CONSTRUCTION_CAPE,
            CRAFTING_CAPE,
            FARMING_CAPE,
            FIREMAKING_CAPE,
            FISHING_CAPE,
            FLETCHING_CAPE,
            HERBLORE_CAPE,
            MAGIC_CAPE,
            MINING_CAPE,
            PRAYER_CAPE,
            RANGED_CAPE,
            RUNECRAFTING_CAPE,
            SLAYER_CAPE,
            SMITHING_CAPE,
            THIEVING_CAPE,
            WOODCUTTING_CAPE,
            HUNTER_CAPE,
            QUEST_CAPE,
            ACHIEVEMENT_CAPE,
            MUSIC_CAPE,
            SPOTTED_CAPE,
            SPOTTIER_CAPE,
            MAX_CAPE,
            INFERNAL_CAPE,
            CHAMPIONS_CAPE,
            IMBUED_SARADOMIN_CAPE,
            IMBUED_GUTHIX_CAPE,
            IMBUED_ZAMORAK_CAPE,
            MYTHICAL_CAPE,
            XERICS_GUARD,
            XERICS_WARRIOR,
            XERICS_SENTINEL,
            XERICS_GENERAL,
            XERICS_CHAMPION,
            SINHAZA_SHROUD_1,
            SINHAZA_SHROUD_2,
            SINHAZA_SHROUD_3,
            SINHAZA_SHROUD_4,
            SINHAZA_SHROUD_5
    ),

    BEGINNER_TREASURE_TRAILS(33405,
            new int[]{
                    23285, 23288, 23291, 23294, 23297, 23300, 23303, 23306, 23309, 23312, 23315, 23318, 23330, 23334, 23332
            },
            SANDWICH_LADY_COSTUME,
            MONKS_ROBE_TRIM
    ),

    EASY_TREASURE_TRAILS(33405,
            new int[]{
                    10666,
                    10669,
                    10672,
                    10675,
                    10678,
                    10681,
                    10682,
                    10687,
                    10688,
                    10691,
                    10692,
                    10693,
                    10694,
                    10695,
                    10696,
                    10700,
                    10701,
                    10702,
                    10703,
                    10704,
                    10739,
                    10745,
                    10741,
                    10743,
                    10747,
                    10715,
                    10716,
                    10717,
                    10718,
                    10719,
                    10751,
                    10755,
                    10753,
                    11279,
                    12216,
                    12206,
                    12226,
                    12236,
                    20185,
                    20170,
                    12246,
                    12248,
                    12250,
                    12252,
                    12376,
                    12298,
                    12454,
                    12456,
                    20165,
                    20167,
                    20206,
                    20209,
                    20200
            },
            BLACK_HERALD_SHIELD_1,
            BLACK_HERALD_SHIELD_2,
            BLACK_HERALD_SHIELD_3,
            BLACK_HERALD_SHIELD_4,
            BLACK_HERALD_SHIELD_5,
            GOLD_STUDDED_LEATHER,
            TRIMMED_STUDDED_LEATHER,
            GOLD_WIZARD_ROBES,
            TRIMMED_WIZARD_ROBES,
            TRIMMED_BLACK_ARMOUR,
            GOLD_BLACK_ARMOUR,
            HIGHWAYMAN_MASK,
            BLUE_BERET,
            BLACK_BERET,
            WHITE_BERET,
            BLACK_HERALD_HELM_1,
            BLACK_HERALD_HELM_2,
            BLACK_HERALD_HELM_3,
            BLACK_HERALD_HELM_4,
            BLACK_HERALD_HELM_5,
            TRIMMED_AMULET_OF_MAGIC,
            PANTALOONS,
            WIG,
            FLARED_TROUSERS,
            SLEEPING_CAP,
            BOBS_RED_SHIRT,
            BOBS_BLUE_SHIRT,
            BOBS_GREEN_SHIRT,
            BOBS_BLACK_SHIRT,
            BOBS_PURPLE_SHIRT,
            RED_ELEGANT,
            GREEN_ELEGANT,
            BLUE_ELEGANT,
            BERET_MASK,
            TRIMMED_BRONZE,
            GOLD_BRONZE,
            TRIMMED_IRON,
            GOLD_IRON,
            TRIMMED_STEEL,
            GOLD_STEEL,
            BEANIE,
            RED_BERET,
            IMP_MASK,
            GOBLIN_MASK,
            BLACK_CANE,
            BLACK_PICKAXE,
            BLACK_WIZARD_GOLD,
            BLACK_WIZARD_TRIM,
            LARGE_SPADE,
            WOODEN_SHIELD_G,
            GOLDEN_CHEFS_HAT,
            GOLDEN_APRON,
            MONK_ROBES_GOLD
    ),

    MEDIUM_TREASURE_TRAILS(33405,
            new int[]{
                    10759,
                    10761,
                    10763,
                    10765,
                    10767,
                    10667,
                    10670,
                    10673,
                    10676,
                    10679,
                    10683,
                    10684,
                    10697,
                    10698,
                    10699,
                    10769,
                    10771,
                    10773,
                    10710,
                    10711,
                    10712,
                    10713,
                    10714,
                    10737,
                    10749,
                    10757,
                    12316,
                    12348,
                    10690,
                    12278,
                    12288,
                    12360,
                    20247,
                    12300,
                    12302,
                    12304,
                    12306,
                    12308,
                    12310,
                    12312,
                    12314,
                    12362,
                    12429,
                    20267,
                    20270,
                    12254,
                    12194,
                    12266,
                    12320,
                    20244,
                    20241,
                    12378,
                    12599,
                    20250,
                    20252,
                    20255,
                    20258,
                    20261,
                    20264,
                    20273,
            },
            RED_STRAW_BOATER,
            ORANGE_STRAW_BOATER,
            GREEN_STRAW_BOATER,
            BLUE_STRAW_BOATER,
            BLACK_STRAW_BOATER,
            ADAMANT_HERALDIC_KITESHIELD_1,
            ADAMANT_HERALDIC_KITESHIELD_2,
            ADAMANT_HERALDIC_KITESHIELD_3,
            ADAMANT_HERALDIC_KITESHIELD_4,
            ADAMANT_HERALDIC_KITESHIELD_5,
            GREEN_DRAGONHIDE_GOLD,
            GREEN_DRAGONHIDE_TRIM,
            RANGER_BOOTS,
            TRIMMED_ADAMANTITE_ARMOUR,
            GOLD_TRIMMED_ADAMANTITE_ARMOUR,
            RED_HEADBAND,
            BLACK_HEADBAND,
            BROWN_HEADBAND,
            ADAMANT_HERALDIC_HELM_1,
            ADAMANT_HERALDIC_HELM_2,
            ADAMANT_HERALDIC_HELM_3,
            ADAMANT_HERALDIC_HELM_4,
            ADAMANT_HERALDIC_HELM_5,
            TRIMMED_AMULET_OF_STRENGTH,
            ELEGANT_CLOTHING_BLACK_WHITE,
            ELEGANT_CLOTHING_PURPLE,
            WIZARD_BOOTS,
            MITHRIL_ARMOUR_GOLD,
            MITHRIL_ARMOUR_TRIM,
            LEPRECHAUN_HAT,
            BLACK_LEPRECHAUN_HAT,
            WHITE_HEADBAND,
            BLUE_HEADBAND,
            GOLD_HEADBAND,
            PINK_HEADBAND,
            GREEN_HEADBAND,
            PINK_BOATER,
            PURPLE_BOATER,
            WHITE_BOATER,
            CAT_MASK,
            PENGUIN_MASK,
            BLACK_UNICORN_MASK,
            WHITE_UNICORN_MASK,
            PINK_ELEGANT,
            GOLD_ELEGANT,
            ARMADYL_VESTMENTS,
            ANCIENT_VESTMENTS,
            BANDOS_VESTMENTS,
            TOWN_CRIER_HAT,
            TOWN_CRIER_BELL,
            TOWN_CRIER_COAT,
            ADAMANT_CANE,
            HOLY_SANDALS,
            CLUELESS_SCROLL,
            ARCEUUS_BANNER,
            HOSIDIUS_BANNER,
            LOVAKENGJ_BANNER,
            PISCARILIUS_BANNER,
            SHAYZIEN_BANNER,
            CABBAGE_ROUND_SHIELD
    ),

    HARD_TREASURE_TRAILS_1(33405, new int[]{
            10668,
            10671,
            10674,
            10677,
            10680,
            10685,
            10686,
            10689,
            10797,
            10799,
            10801,
            10803,
            10805,
            10807,
            10775,
            10777,
            10779,
            10781,
            10783,
            10705,
            10706,
            10707,
            10708,
            10709,
            10720,
            10785,
            10789,
            10787,
            10793,
            10795,
            10791,
            11278,
            12328,
            12332,
            12324,
            12326,
            12322,
            12517,
            12515,
            12471,
            12481,
            12461,
            12509,
            12501,
            12493,
            12519,
            12521,
            12523,
            12525,
            12380,
            19913,
            19916,
            20147,
            20150,
            20153,
            20156,
            20159,
            20162,
            19919,
            10166,
    },
            RUNE_HERALDIC_KITESHIELD_1,
            RUNE_HERALDIC_KITESHIELD_2,
            RUNE_HERALDIC_KITESHIELD_3,
            RUNE_HERALDIC_KITESHIELD_4,
            RUNE_HERALDIC_KITESHIELD_5,
            BLUE_DRAGONHIDE_GOLD,
            BLUE_DRAGONHIDE_TRIM,
            ENCHANTED_ROBES,
            ROBIN_HOOD_HAT,
            GOLDTRIMMED_RUNE_ARMOUR,
            TRIMMED_RUNE_ARMOUR,
            TAN_CAVALIER,
            DARK_CAVALIER,
            BLACK_CAVALIER,
            PIRATES_HAT,
            ZAMORAK_RUNE_ARMOUR,
            SARADOMIN_RUNE_ARMOUR,
            GUTHIX_RUNE_ARMOUR,
            GILDED_RUNE_ARMOUR,
            RUNE_HERALDIC_HELM_1,
            RUNE_HERALDIC_HELM_2,
            RUNE_HERALDIC_HELM_3,
            RUNE_HERALDIC_HELM_4,
            RUNE_HERALDIC_HELM_5,
            TRIMMED_AMULET_OF_GLORY,
            SARADOMIN_VESTMENTS,
            GUTHIX_VESTMENTS,
            ZAMORAK_VESTMENT_SET,
            SARADOMIN_BLESSED_DHIDE_ARMOUR,
            GUTHIX_BLESSED_DHIDE_ARMOUR,
            ZAMORAK_BLESSED_DHIDE_ARMOUR,
            CAVALIER_MASK,
            RED_DRAGONHIDE_GOLD,
            RED_DRAGONHIDE_TRIM,
            RED_CAVALIER,
            NAVY_CAVALIER,
            WHITE_CAVALIER,
            PITH_HELMET,
            EXPLORER_BACKPACK,
            ARMADYL_RUNE_ARMOUR,
            BANDOS_RUNE_ARMOUR,
            ANCIENT_RUNE_ARMOUR,
            ARMADYL_BLESSED_DHIDE_ARMOUR,
            BANDOS_BLESSED_DHIDE_ARMOUR,
            ANCIENT_BLESSED_DHIDE_ARMOUR,
            GREEN_DRAGON_MASK,
            BLUE_DRAGON_MASK,
            RED_DRAGON_MASK,
            BLACK_DRAGON_MASK,
            RUNE_CANE,
            ZOMBIE_HEAD_TT,
            CYCLOPS_HEAD,
            GILDED_RUNE_MED_HELM,
            GILDED_RUNE_CHAINBODY,
            GILDED_RUNE_SQ_SHIELD,
            GILDED_RUNE_2H_SWORD,
            GILDED_RUNE_SPEAR,
            GILDED_RUNE_HASTA,
            NUNCHAKU),
    HARD_TREASURE_TRAILS_2(33405, new int[]{
            10167,
            19934,
            19925,
            19931,
            19928,
            19937,
            19922,
            10331,
            10339,
            10349,
    },
            SARADOMIN_DHIDE_BOOTS,
            BANDOS_DHIDE_BOOTS,
            ARMADYL_DHIDE_BOOTS,
            GUTHIX_DHIDE_BOOTS,
            ZAMORAK_DHIDE_BOOTS,
            ANCIENT_DHIDE_BOOTS,
            THIRD_AGE_RANGER_ARMOUR,
            THIRD_AGE_MAGE_ARMOUR,
            THIRD_AGE_MELEE_ARMOUR),

    ELITE_TREASURE_TRAILS(33405, new int[]{
            12374,
            12336,
            12338,
            12394,
            12364,
            12366,
            12368,
            12370,
            12372,
            12431,
            12358,
            12356,
            12433,
            12354,
            12382,
            12386,
            12442,
            12400,
            12413,
            12435,
            12541,
            19942,
            19944,
            19947,
            19950,
            19953,
            19956,
            19989,
            19992,
            19995,
            19998,
            20006,
            12423,
            12425,
            12427,
            19971,
            19986,
    },
            DRAGON_CANE,
            BRIEFCASE,
            SAGACIOUS_SPECTACLES,
            ROYAL_OUTFIT,
            BRONZE_DRAGON_MASK,
            IRON_DRAGON_MASK,
            STEEL_DRAGON_MASK,
            MITHRIL_DRAGON_MASK,
            LAVA_DRAGON_MASK,
            AFRO,
            KATANA,
            BIG_PIRATE_HAT,
            TOP_HAT,
            MONOCLE,
            BLACK_DRAGONHIDE_GOLD,
            BLACK_DRAGONHIDE_TRIM,
            MUSKETEER_OUTFIT,
            PARTYHAT_AND_SPECS,
            PIRATE_HAT_AND_PATCH,
            TOP_HAT_AND_MONOCLE,
            DEERSTALKER,
            HEAVY_CASKET,
            ARCEUUS_HOUSE_SCARF,
            HOSIDIUS_HOUSE_SCARF,
            LOVAKENGJ_HOUSE_SCARF,
            PISCARILIUS_HOUSE_SCARF,
            SHAYZIEN_HOUSE_SCARF,
            BLACKSMITHS_HELM,
            BUCKET_HELM,
            RANGER_GLOVES,
            HOLY_WRAPS,
            RING_OF_NATURE,
            THIRD_AGE_WAND,
            THIRD_AGE_BOW,
            THIRD_AGE_LONGSWORD,
            DARK_TUXEDO_OUTFIT,
            LIGHT_TUXEDO_OUTFIT
    ),

    MASTER_TREASURE_TRAILS(33405, new int[]{
            20009,
            20012,
            20015,
            20018,
            20021,
            20024,
            20027,
            20030,
            20033,
            20051,
            20054,
            20057,
            20060,
            20111,
            20126,
            20117,
            20114,
            20123,
            20120,
            20039,
            20081,
            20096,
            20129,
    },
            FANCY_TIARA,
            THIRD_AGE_AXE,
            THIRD_AGE_PICKAXE,
            RING_OF_COINS,
            LESSER_DEMON_MASK,
            GREATER_DEMON_MASK,
            BLACK_DEMON_MASK,
            OLD_DEMON_MASK,
            JUNGLE_DEMON_MASK,
            OBSIDIAN_CAPE_R,
            HALF_MOON_SPECTACLES,
            ALE_OF_THE_GODS,
            BUCKET_HELM_G,
            BOWL_WIG,
            SHAYZIEN_HOUSE_HOOD,
            HOSIDIUS_HOUSE_HOOD,
            ARCEUUS_HOUSE_HOOD,
            PISCARILIUS_HOUSE_HOOD,
            LOVAKENGJ_HOUSE_HOOD,
            SAMURAI_OUTFIT,
            MUMMY_OUTFIT,
            ANKOU_OUTFIT,
            ROBES_OF_DARKNESS
    ),

    TOY_BOX_1(33405, new int[]{
            10735,
            10736,
            796,
            10734,
            10733,
            10732,
            10731,
            6868,
            6867,
            9816,
            9817,
            6859,
            6860,
            6861,
            6862,
            6863,
            6864,
            10730,
            10724,
            10725,
            10726,
            10727,
            10728,
            10729,
            10723,
            11016,
            11017,
            11018,
            11019,
            11848,
            11863,
            11864,
            11920,
            12957,
            12958,
            12959,
            12960,
            4566,
            12601,
            12846,
            12888,
            12889,
            12890,
            12891,
            12892,
            12893,
            12894,
            12895,
            12896,
            12897,
            13183,
            13204,
            13216,
            13217,
            13218,
            13219,
            13289,
            10166,
    },
            BUNNY_EARS,
            SCYTHE,
            WAR_SHIP,
            YOYO,
            RUBBER_CHICKEN,
            ZOMBIE_HEAD,
            BLUE_MARIONETTE,
            RED_MARIONETTE,
            GREEN_MARIONETTE,
            BOBBLE_HAT,
            BOBBLE_SCARF,
            JESTER_HAT,
            JESTER_SCARF,
            TRIJESTER_HAT,
            TRIJESTER_SCARF,
            WOOLLY_HAT,
            WOOLLY_SCARF,
            EASTER_RING,
            JACK_LANTERN_MASK,
            SPOOKY_BOOTS,
            SPOOKY_GLOVES,
            SPOOKY_LEGS,
            SPOOKY_BODY,
            SPOOKY_HEAD,
            REINDEER_HAT,
            CHICKEN_HEAD,
            CHICKEN_FEET,
            CHICKEN_WINGS,
            CHICKEN_LEGS,
            BLACK_HWEEN_MASK,
            BLACK_PARTYHAT,
            RAINBOW_PARTYHAT,
            COW_MASK,
            COW_TOP,
            COW_LEGS,
            COW_GLOVES,
            COW_SHOES,
            EASTER_BASKET,
            DRUIDIC_WREATH,
            GRIM_REAPER_HOOD,
            SANTA_MASK,
            SANTA_JACKET,
            SANTA_PANTALOONS,
            SANTA_GLOVES,
            SANTA_BOOTS,
            ANTISANTA_MASK,
            ANTISANTA_JACKET,
            ANTISANTA_PANTALOONS,
            ANTISANTA_GLOVES,
            ANTISANTA_BOOTS,
            BUNNY_FEET,
            MASK_OF_BALANCE,
            TIGER_TOY,
            LION_TOY,
            SNOW_LEOPARD_TOY,
            AMUR_LEOPARD_TOY,
            ANTIPANTIES
    ),
    TOY_BOX_2(33405, new int[]{
            10167,
            13284,
            13285,
            13286,
            13287,
            13288,
            13344,
            13345,
            13656,
            13664,
            13665,
            13666,
            13680,
            13682,
            19700,
            20774,
            20776,
            20778,
            20780,
            20833,
            20835,
            20837,
            21212,
            21210,
            21215,
            21315,
            21355,
            21696,
            21721,
            21848,
            21850,
            21852,
            21854,
            21856,
            21858,
            21860,
            22317,
            22352,
            22354,
    },
            GRAVEDIGGER_HAT,
            GRAVEDIGGER_TOP,
            GRAVEDIGGER_LEGS,
            GRAVEDIGGER_BOOTS,
            GRAVEDIGGER_GLOVES,
            BLACK_SANTA_HAT,
            INVERTED_SANTA_HAT,
            GNOME_CHILD_HAT,
            BUNNY_TOP,
            BUNNY_LEGS,
            BUNNY_PAWS,
            CABBAGE_CAPE,
            CRUCIFEROUS_CODEX,
            HORNWOOD_HELM,
            BANSHEE_MASK,
            BANSHEE_TOP,
            BANSHEE_ROBE,
            HUNTING_KNIFE,
            SNOW_GLOBE,
            SACK_OF_PRESENTS,
            GIANT_PRESENT,
            FOURTH_BIRTHDAY_HAT,
            BIRTHDAY_BALLOONS,
            EASTER_EGG_HELM,
            RAINBOW_SCARF,
            HAND_FAN,
            RUNEFEST_SHIELD,
            JONAS_MASK,
            SNOW_IMP_HEAD,
            SNOW_IMP_BODY,
            SNOW_IMP_LEGS,
            SNOW_IMP_TAIL,
            SNOW_IMP_GLOVES,
            SNOW_IMP_FEET,
            WISE_OLD_MANS_SANTA_HAT,
            PROP_SWORD,
            EGGSHELL_PLATEBODY,
            EGGSHELL_PLATELEGS
    );

    CostumeStorage(int containerId, int[] display, Costume... costumes) {
        this.containerId = containerId;
        this.display = Arrays.stream(display).mapToObj(i -> new Item(i - 1)).toArray(Item[]::new);
        this.setCostumes(costumes);
    }

    private void changeInventoryAccess(Player player) {
        if (player.isVisibleInterface(674)) {
            return;
        }

        player.openInterface(InterfaceType.INVENTORY, 674);
        player.getPacketSender().sendClientScript(149, "iiiiiisssss", 44171264, 93, 4, 7, 1, -1, "Store<col=ff9040>", "", "", "", "");
        player.getPacketSender().sendAccessMask(674, 0, 0, 27, 1086);
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_COSTUME_STORAGE);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }
        player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff),
                (int) (owned >> 32), 1);
        player.getPacketSender().sendItems(-1, 400, containerId, display);
        player.getPacketSender().sendAccessMask(592, 2, 0, 239, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openFancyDress(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3291, 1, 0);
        player.getPacketSender().sendAccessMask(675, 4, 0, 287, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openTreasureChestBeginner(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3293, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 287, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openTreasureChestEasy(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3294, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 287, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openTreasureChestMedium(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3295, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 287, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openTreasureChestHard(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3296, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 287, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openTreasureChestElite(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3297, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 287, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openTreasureChestMaster(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3298, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 1043, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openArmourCase(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3290, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 1043, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openMagicCase(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3289, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 1043, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openCapeRack(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3292, 1, 0);
        player.getPacketSender().sendAccessMask(675, 4, 0, 1043, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public void openToyBox(Player player) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        long owned = 0;
        Map<Costume, int[]> stored = getSets(player);
        int slot = 0;
        if (display[slot].getId() == 10166) { // back...
            owned |= 1;
            slot++;
        }
        for (int i = 0; i < getCostumes().length; i++) {
            int shift = slot;
            if (shift > 30)
                shift++;
            if (stored.get(getCostumes()[i]) != null) {
                owned |= 1L << shift;
            }
            slot++;
        }
        if (slot < display.length && display[slot].getId() == 10165) { // more...
            owned |= 1L << (slot + 1);
        }

        //  player.getPacketSender().sendClientScript(417, "svii1", StringUtils.fixCaps(name().replace("_", " ")), containerId, (int) (owned & 0xffffffff), (int)(owned >> 32), 1);

        player.getPacketSender().sendItems(-1, -1, containerId, display);
        player.getPacketSender().sendClientScript(3532, "iii", 3299, 1, 1);
        player.getPacketSender().sendAccessMask(675, 4, 0, 1043, 1026);
        player.set("COSTUME_STORAGE", this);
    }

    public static void handleClueScrollTiers(Player player, int option) {
        switch (option) {
            case 1:
                OptionScroll.open(player, "Select a tier",
                        new Option("Beginner", () -> CostumeStorage.BEGINNER_TREASURE_TRAILS.openTreasureChestBeginner(player)),
                        new Option("Easy", () -> CostumeStorage.EASY_TREASURE_TRAILS.openTreasureChestEasy(player)),
                        new Option("Medium", () -> CostumeStorage.MEDIUM_TREASURE_TRAILS.openTreasureChestMedium(player)),
                        new Option("Hard", () -> CostumeStorage.HARD_TREASURE_TRAILS_1.openTreasureChestHard(player)),
                        new Option("Elite", () -> CostumeStorage.BEGINNER_TREASURE_TRAILS.openTreasureChestElite(player)),
                        new Option("Master", () -> CostumeStorage.BEGINNER_TREASURE_TRAILS.openTreasureChestMaster(player))
                );
                break;
            case 2:
                CostumeStorage.BEGINNER_TREASURE_TRAILS.openTreasureChestBeginner(player);
                break;
        }

    }

    static {
        InterfaceHandler.register(675, h -> {
            h.actions[7] = (DefaultAction) (player, childId, option, slot, itemId) -> handleClueScrollTiers(player, option);

            //8 = deposit mode

            //10 = deposit inventory
            return;
        });

        InterfaceHandler.register(674, h -> {

            return;
        });
    }

    int containerId;
    Item[] display;
    private Costume[] costumes;

    public Costume getByItem(int id) {
        for (Costume costume : getCostumes()) {
            for (int[] piece : costume.pieces) {
                for (int option : piece) {
                    if (option == id) {
                        return costume;
                    }
                }
            }
        }
        return null;
    }

    public Map<Costume, int[]> getSets(Player player) {
        switch (this) {
            case FANCY_DRESS_BOX:
                return player.house.getFancyDressStorage();
            case ARMOUR_CASE:
                return player.house.getArmourCaseStorage();
            case MAGIC_WARDROBE:
                return player.house.getMagicWardrobeStorage();
            case CAPE_RACK:
                return player.house.getCapeRackStorage();
            case BEGINNER_TREASURE_TRAILS:
                return player.house.getBeginnerTreasureTrailsStorage();
            case EASY_TREASURE_TRAILS:
                return player.house.getEasyTreasureTrailsStorage();
            case MEDIUM_TREASURE_TRAILS:
                return player.house.getMediumTreasureTrailsStorage();
            case HARD_TREASURE_TRAILS_1:
            case HARD_TREASURE_TRAILS_2:
                return player.house.getHardTreasureTrailsStorage();
            case ELITE_TREASURE_TRAILS:
                return player.house.getEliteTreasureTrailsStorage();
            case MASTER_TREASURE_TRAILS:
                return player.house.getMasterTreasureTrailsStorage();
            case TOY_BOX_1:
            case TOY_BOX_2:
                return player.house.getToyBoxStorage();
            default:
                throw new IllegalArgumentException();
        }
    }

    public int countSpaceUsed(Player player) {
        Map<Costume, int[]> stored = getSets(player);
        switch (this) {
            case FANCY_DRESS_BOX:
            case ARMOUR_CASE:
            case MAGIC_WARDROBE:
            default:
                return stored.size();
            case CAPE_RACK:
                return (int) stored.keySet().stream().filter(costume -> costume.ordinal() >= ATTACK_CAPE.ordinal() && costume.ordinal() <= MUSIC_CAPE.ordinal()).count();
            case BEGINNER_TREASURE_TRAILS:
            case EASY_TREASURE_TRAILS:
            case MEDIUM_TREASURE_TRAILS:
            case HARD_TREASURE_TRAILS_1:
            case HARD_TREASURE_TRAILS_2:
            case ELITE_TREASURE_TRAILS:
            case MASTER_TREASURE_TRAILS:
            case TOY_BOX_1:
            case TOY_BOX_2:
                return 0;
        }
    }

    public Costume[] getCostumes() {
        return costumes;
    }

    public void setCostumes(Costume[] costumes) {
        this.costumes = costumes;
    }
}
