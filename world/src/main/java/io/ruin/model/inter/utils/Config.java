package io.ruin.model.inter.utils;

import io.ruin.cache.Varpbit;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;

public class Config {

    public static final String GROUP_SAVE_DIRECTORY = "data/gim/";
   // private static final File GROUP_SAVE_DIRECTORY = new File("data//ip_mutes.txt");

    public static final String PVP_SAVES = "data//pvp/";

    private static final ArrayList<Config> CONFIGS = new ArrayList<>();

    private static final int[] SHIFTS = new int[32];

    static {
        int offset = 2;
        for (int i_4_ = 0; i_4_ < 32; i_4_++) {
            SHIFTS[i_4_] = offset - 1;
            offset += offset;
        }
    }

    public static final Config QUEST_ACTIVE_TAB = varpbit(8168, false);
    public static final Config QUEST_TOTAL_TABS = varpbit(9340, false);

    public static final Config HEALTH_HUD_NPC = varp(1683, false);
    public static final Config HEALTH_HUD_CURRENT = varpbit(6099, false);
    public static final Config HEALTH_HUD_MAX = varpbit(6100, false);
    public static final Config NIGHTMARE_HP_BAR = varpbit(12401, false);

    /**
     * Fossil Island
     */
    public static final Config CLEANING_TABLE = varpbit(5801, true);

    /**
     * Group iron
     */
    public static final Config GIM_SIDE_PANEL = varp(3172, true);

    /**
     * Combat achievements
     */
    public static final Config COMBAT_ACHIVEMENTS_TASKS_TIER = varpbit(12858, true);
    public static final Config COMBAT_ACHIVEMENTS_TASKS_TYPE = varpbit(12859, true);
    public static final Config COMBAT_ACHIVEMENTS_TASKS_MONSTER = varpbit(12860, true);
    public static final Config COMBAT_ACHIVEMENTS_TASKS_COMPLETED_TASKS = varpbit(12861, true);
    public static final Config COMBAT_ACHIVEMENTS_REWARDS = varpbit(12862, true);//max value is 49

    /**
     * Mage arena 261
     */
    public static final Config MAGE_ARENA_POINTS = varp(261, true);

    /**
     * Combat achieve Easy
     */
    public static final Config NOXIOUS_FOE = varpbit(12458, true);
    public static final Config BARROWS_NOVICE = varpbit(12482, true);
    public static final Config DEFENCE_WHAT_DEFENCE = varpbit(12486, true);
    public static final Config BIG_BLACK_AND_FIERY = varpbit(12490, true);
    public static final Config THE_DEMONIC_PUNCHING_BAG = varpbit(12491, true);
    public static final Config BRYOPHYTA_NOVICE = varpbit(12493, true);
    public static final Config PROTECTION_FROM_MOSS = varpbit(12495, true);
    public static final Config PROTECTION_IS_KEY = varpbit(12497, true);
    public static final Config A_SLOW_DEATH = varpbit(12498, true);
    public static final Config FIGHTING_AS_INTENTED_II = varpbit(12499, true);
    public static final Config DERANGED_ARCHAEOLOGIST_NOVICE = varpbit(12534, true);
    public static final Config THE_WALKING_VOLCANO = varpbit(12538, true);
    public static final Config A_GREATER_FOE = varpbit(12578, true);
    public static final Config NOT_SO_GREAT_AFTER_ALL = varpbit(12579, true);
    public static final Config A_DEMONS_BEST_FRIEND = varpbit(12580, true);
    public static final Config OBOR_NOVICE = varpbit(12587, true);
    public static final Config SLEEPING_GIANT = varpbit(12589, true);
    public static final Config FIGHTING_AS_INTENDED = varpbit(12592, true);
    public static final Config KING_BLACK_DRAGON_NOVICE = varpbit(12620, true);
    public static final Config A_SCALEY_ENCOUNTER = varpbit(12632, true);
    public static final Config GIANT_MOLE_NOVICE = varpbit(12635, true);
    public static final Config SARACHNIS_NOVICE = varpbit(12664, true);
    public static final Config WINTERTODT_NOVICE = varpbit(12737, true);
    public static final Config MUMMY = varpbit(12739, true);
    public static final Config HANDYMAN = varpbit(12740, true);
    public static final Config COSY = varpbit(12743, true);
    public static final Config A_SLITHERY_ENCOUNTER = varpbit(12745, true);
    public static final Config TEMPOROSS_NOVICE = varpbit(12811, true);
    public static final Config MASTER_OF_BUCKETS = varpbit(12814, true);
    public static final Config CALM_BEFORE_THE_STORM = varpbit(12815, true);
    public static final Config FIRE_IN_THE_HOLE = varpbit(12818, true);
    public static final Config INTO_THE_DEN_OF_GIANTS = varpbit(12856, true);

    /**
     * Combat achieve Medium
     */
    public static final Config BARROWS_CHAMPION = varpbit(12483, true);
    public static final Config CANT_TOUCH_ME = varpbit(12484, true);
    public static final Config PRAY_FOR_SUCCESS = varpbit(12485, true);
    public static final Config BRUTAL_BIG_BLACK_AND_FIERY = varpbit(12492, true);
    public static final Config BRYOPHYTA_CHAMPION = varpbit(12494, true);
    public static final Config QUICK_CUTTER = varpbit(12496, true);
    public static final Config SKOTIZO_ADEPT = varpbit(12502, true);
    public static final Config DEMONIC_WEAKENING = varpbit(12504, true);
    public static final Config DEMONBANE_WEAPONRY = varpbit(12506, true);
    public static final Config CHAOS_FANATIC_CHAMPION = varpbit(12519, true);
    public static final Config SORRY_WHAT_WAS_THAT = varpbit(12521, true);
    public static final Config CRAY_ARCHAEOLOGIST_CHAMPION = varpbit(12528, true);
    public static final Config MAGE_OF_THE_RUINS = varpbit(12530, true);
    public static final Config ID_RATHER_NOT_LEARN = varpbit(12531, true);
    public static final Config DERANGED_ARCHAEOLOGIST_CHAMPION = varpbit(12535, true);
    public static final Config MAGE_OF_THE_SWAMP = varpbit(12536, true);
    public static final Config ID_RATHER_BE_ILLITERATE = varpbit(12537, true);
    public static final Config A_SMASHING_TIME = varpbit(12555, true);
    public static final Config OBOR_CHAMPION = varpbit(12588, true);
    public static final Config BACK_TO_THE_WALL = varpbit(12590, true);
    public static final Config SQUASHING_THE_GIANT = varpbit(12591, true);
    public static final Config KING_BLACK_DRAGON_CHAMPION = varpbit(12621, true);
    public static final Config CLAW_CLIPPER = varpbit(12622, true);
    public static final Config HIDE_PENETRATION = varpbit(12623, true);
    public static final Config ANTIFIRE_PROTECTION = varpbit(12624, true);
    public static final Config MASTER_OF_BROAD_WEAPONRY = varpbit(12631, true);
    public static final Config GIANT_MOLE_CHAMPION = varpbit(12636, true);
    public static final Config AVOIDING_THOSE_LITTLE_ARMS = varpbit(12640, true);
    public static final Config DAGANNOTH_PRIME_CHAMPION = varpbit(12655, true);
    public static final Config DAGANNOTH_REX_CHAMPION = varpbit(12659, true);
    public static final Config A_FROZEN_KING = varpbit(12662, true);
    public static final Config SARACHNIS_CHAMPION = varpbit(12665, true);
    public static final Config NEWSPAPER_ENTHUSIAST = varpbit(12668, true);
    public static final Config A_FROZEN_FOE_FROM_THE_PAST = varpbit(12681, true);
    public static final Config DAGANNOTH_SUPREME_CHAMPION = varpbit(12691, true);
    public static final Config WINTERTODT_CHAMPION = varpbit(12738, true);
    public static final Config CAN_WE_FIX_IT = varpbit(12741, true);
    public static final Config LEAVING_NO_ONE_BEHIND = varpbit(12742, true);
    public static final Config TEMPOROSS_CHAMPION = varpbit(12812, true);
    public static final Config THE_LONE_ANGLER = varpbit(12817, true);
    public static final Config SIT_BACK_AND_RELAX = varpbit(12857, true);

    /**
     * Combat achieve Hard
     */
    public static final Config ABYSSAL_ADEPT = varpbit(12459, true);
    public static final Config THEY_GROW_UP_TOO_FAST = varpbit(12461, true);
    public static final Config DONT_WHIP_ME = varpbit(12463, true);
    public static final Config DONT_STOP_MOVING = varpbit(12465, true);
    public static final Config KREE_ARRA_ADEPT = varpbit(12467, true);
    public static final Config GENERAL_GRAARDOR_ADEPT = varpbit(12474, true);
    public static final Config OURG_FREEER = varpbit(12476, true);
    public static final Config GENERAL_SHOWDOWN = varpbit(12478, true);
    public static final Config JUST_LIKE_THAT = varpbit(12487, true);
    public static final Config FAITHLESS_CRYPT_RUN = varpbit(12488, true);
    public static final Config CALLISTO_ADEPT = varpbit(12500, true);
    public static final Config SKOTIZO_VETERAN = varpbit(12503, true);
    public static final Config CHAOS_ELEMENTAL_ADEPT = varpbit(12515, true);

    public static final Config CLANCHAT_TAB_ID = varpbit(13071, true).defaultValue(0).forceSend();
    public static final Config HOARDER = varpbit(12517, true);
    public static final Config THE_FLINCHER = varpbit(12518, true);
    public static final Config CHAOS_FANATIC_ADEPT = varpbit(12520, true);
    public static final Config PRAYING_TO_THE_GODS = varpbit(12522, true);
    public static final Config CRAZY_ARCHAEOLOGIST_ADEPT = varpbit(12529, true);
    public static final Config GROTESQUE_GUARDIANS_ADEPT = varpbit(12540, true);
    public static final Config DONT_LOOK_AT_THE_ECLIPSE = varpbit(12542, true);
    public static final Config PRISON_BREAK = varpbit(12543, true);
    public static final Config GRANITE_FOOTWORK = varpbit(12544, true);
    public static final Config HEAL_NO_MORE = varpbit(12545, true);
    public static final Config STATIC_AWARENESS = varpbit(12546, true);
    public static final Config HESPORI_ADEPT = varpbit(12581, true);
    public static final Config HESPORISNT = varpbit(12582, true);
    public static final Config WEED_WHACKER = varpbit(12583, true);
    public static final Config KALPHITE_QUEEN_ADEPT = varpbit(12615, true);
    public static final Config CHITIN_PENETRATOR = varpbit(12617, true);
    public static final Config WHO_IS_THE_KING_NOW = varpbit(12625, true);
    public static final Config KRAKEN_ADEPT = varpbit(12626, true);
    public static final Config UNNECESSARY_OPTIMIZATION = varpbit(12627, true);
    public static final Config KRAKANT_HURT_ME = varpbit(12623, true);
    public static final Config WHY_ARE_YOU_RUNNING = varpbit(12637, true);
    public static final Config WHACK_A_MOLE = varpbit(12639, true);
    public static final Config NIGHTMARE_CHAMPION = varpbit(12641, true);
    public static final Config DAGANNOTH_PRIME_ADEPT = varpbit(12656, true);
    public static final Config DAGANNOTH_REX_ADEPT = varpbit(12660, true);
    public static final Config READY_TO_POUNCE = varpbit(12666, true);
    public static final Config INSPECT_REPELLENT = varpbit(12667, true);
    public static final Config COMMANDER_ZILYANA_ADEPT = varpbit(12669, true);
    public static final Config COMMANDER_ZILYANA_SHOWDOWN = varpbit(12671, true);
    public static final Config SCORPIA_ADEPT = varpbit(12676, true);
    public static final Config I_CANT_REACH_THAT = varpbit(12678, true);
    public static final Config GUARDIANS_NO_MORE = varpbit(12679, true);
    public static final Config ZULRAH_ADEPT = varpbit(12682, true);
    public static final Config DAGANNOTH_SUPREME_ADEPT = varpbit(12692, true);
    public static final Config VENENATIS_ADEPT = varpbit(12722, true);
    public static final Config VETION_ADEPT = varpbit(12724, true);
    public static final Config WHY_FLETCH = varpbit(12744, true);
    public static final Config KRIL_TSUTSAROTH_ADEPT = varpbit(12790, true);
    public static final Config YARR_NO_MORE = varpbit(12792, true);
    public static final Config DEMONIC_SHOWDOWN = varpbit(12793, true);
    public static final Config DEMONBANE_WEAPONRY_II = varpbit(12797, true);
    public static final Config DRESS_LIKE_YOU_MEAN_IT = varpbit(12813, true);
    public static final Config WHY_COOK = varpbit(12816, true);
    public static final Config THEATRE_OF_BLOOD_SM_ADEPT = varpbit(12855, true);

    /**
     * Combat Achieve Elite
     */
    public static final Config ABYSSAL_VETERAN = varpbit(12460, true);
    public static final Config DEMONIC_REBOUND = varpbit(12464, true);
    public static final Config PERFECT_SIRE = varpbit(12466, true);
    public static final Config KREE_ARRA_VETERAN = varpbit(12468, true);
    public static final Config GENERAL_GRAARDOR_VETERAN = varpbit(12475, true);
    public static final Config RESPIRATORY_RUNNER = varpbit(12462, true);
    public static final Config OURG_FREEZER_TWO = varpbit(12477, true);
    public static final Config REFLECTING_ON_THIS_ENCOUNTER = varpbit(12489, true);
    public static final Config CALLISTO_VETERAN = varpbit(12501, true);
    public static final Config DEMON_VETERAN = varpbit(12505, true);
    public static final Config UP_FOR_THE_CHALLENGE = varpbit(12507, true);
    public static final Config CERBERUS_VETERAN = varpbit(12509, true);
    public static final Config GHOST_BUSTER = varpbit(12512, true);
    public static final Config UNREQUIRED_ANTIFIRE = varpbit(12513, true);
    public static final Config ANTI_BITE_MECHANICS = varpbit(12514, true);
    public static final Config CHAOS_ELEMENTAL_VETERAN = varpbit(12516, true);
    public static final Config CORPOREAL_BEAST_VETERAN = varpbit(12523, true);
    public static final Config HOT_ON_YOUR_FEET = varpbit(12525, true);
    public static final Config FINDING_THE_WEAK_SPOT = varpbit(12526, true);
    public static final Config CHICKEN_KILLER = varpbit(12527, true);
    public static final Config IF_GORILLAS_COULD_FLY = varpbit(12532, true);
    public static final Config HITTING_THEM_WHERE_IT_HURTS = varpbit(12533, true);
    public static final Config GALVEK_SPEED_TRIALIST = varpbit(12539, true);
    public static final Config GROTESQUE_GUARDIANS_VETERAN = varpbit(12541, true);
    public static final Config DONE_BEFORE_DUSK = varpbit(12547, true);
    public static final Config PERFECT_GROTESQUE_GUARDIANS = varpbit(12548, true);
    public static final Config GROTESQUE_GUARDIANS_SPEED_TRIALIST = varpbit(12550, true);
    public static final Config FROM_DUSK = varpbit(12553, true);
    public static final Config CORRUPTED_GAUNTLET_VETERAN = varpbit(12556, true);
    public static final Config THREE_TWO_ONE_MAGE = varpbit(12559, true);
    public static final Config GAUNTLET_ADEPT = varpbit(12567, true);
    public static final Config THREE_TWO_ONE_RANGE = varpbit(12569, true);
    public static final Config EGNIOL_DIET = varpbit(12572, true);
    public static final Config CRYSTALLINE_WARRIOR = varpbit(12573, true);
    public static final Config WOLF_PUNCHER = varpbit(12574, true);
    public static final Config GLOUGH_SPEED_TRIALIST = varpbit(12577, true);
    public static final Config PLANT_BASED_DIET = varpbit(12584, true);
    public static final Config HESPORI_SPEED_TRIALIST = varpbit(12585, true);
    public static final Config ALCHEMICAL_MASTER = varpbit(12593, true);
    public static final Config FIGHT_CAVES_VETERAN = varpbit(12605, true);
    public static final Config A_NEAR_MISS = varpbit(12607, true);
    public static final Config FACING_JAD_HEAD_ON = varpbit(12611, true);
    public static final Config KALPHITE_QUEEN_VETERAN = varpbit(12616, true);
    public static final Config INSECT_DEFLECTION = varpbit(12618, true);
    public static final Config PRAYER_SMASHER = varpbit(12619, true);
    public static final Config TEN_TACLES = varpbit(12629, true);
    public static final Config MIMIC_ADEPT = varpbit(12634, true);
    public static final Config HARD_HITTER = varpbit(12638, true);
    public static final Config NIGHTMARE_ADEPT = varpbit(12642, true);
    public static final Config EXPLOSION = varpbit(12645, true);
    public static final Config SLEEP_TIGHT = varpbit(12647, true);
    public static final Config OSRS_GP_AVAILABLE = varpbit(6347, true);
    public static final Config NIGHTMARE_5_SCALE_SPEED_TRIALIST = varpbit(12652, true);
    public static final Config DEATH_TO_THE_SEER_KING = varpbit(12657, true);
    public static final Config FROM_ONE_KING_TO_ANOTHER = varpbit(12658, true);
    public static final Config DEATH_TO_THE_WARRIOR_KING = varpbit(12661, true);
    public static final Config TOPPLING_THE_DIARCHY = varpbit(12663, true);
    public static final Config COMMANDER_ZILYANA_VETERAN = varpbit(12670, true);
    public static final Config REMINISCE = varpbit(12674, true);
    public static final Config SCORPIA_VETERAN = varpbit(12677, true);
    public static final Config FRAGMENT_OF_SEREN_SPEED_TRIALIST = varpbit(12680, true);
    public static final Config ZULRAH_VETERAN = varpbit(12683, true);
    public static final Config SNAKE_REBOUND = varpbit(12685, true);
    public static final Config SNAKE_SNAKE_SNAAAAAAAAKE = varpbit(12686, true);
    public static final Config ZULRAH_SPEED_TRIALIST = varpbit(12688, true);
    public static final Config DEATH_TO_THE_ARCHER_KING = varpbit(12693, true);
    public static final Config RAPID_SUCCESSION = varpbit(12694, true);
    public static final Config THEATRE_OF_BLOOD_VETERAN = varpbit(12695, true);
    public static final Config THERMONUCLEAR_ADEPT = varpbit(12719, true);
    public static final Config HAZARD_PREVENTION = varpbit(12720, true);
    public static final Config SPECD_OUT = varpbit(12721, true);
    public static final Config VENENATIS_VETERAN = varpbit(12723, true);
    public static final Config VETERAN = varpbit(12725, true);
    public static final Config VORKATH_VETERAN = varpbit(12726, true);
    public static final Config ZOMBIE_DESTROYER = varpbit(12729, true);
    public static final Config STICK_EM_WITH_THE_POINTY_END = varpbit(12731, true);
    public static final Config DUST_SEEKER = varpbit(12750, true);
    public static final Config CHAMBERS_OF_XERIC_VETERAN = varpbit(12757, true);
    public static final Config RAIDS_BEAM = varpbit(5456, false);
    public static final Config PERFECTLY_BALANCED = varpbit(12760, true);
    public static final Config TOGETHER_WE_FALL = varpbit(12761, true);
    public static final Config MUTTA_DIET = varpbit(12765, true);
    public static final Config REDEMPTION_ENTHUSIAST = varpbit(12766, true);
    public static final Config DANCING_WITH_STATUES = varpbit(12769, true);
    public static final Config UNDYING_RAID_TEAM = varpbit(12770, true);
    public static final Config SHAYZIEN_SPECIALIST = varpbit(12772, true);
    public static final Config CRYO_NO_MORE = varpbit(12774, true);
    public static final Config BLIZZARD_DODGER = varpbit(12778, true);
    public static final Config KILL_IT_WITH_FIRE = varpbit(12779, true);
    public static final Config ZALCANO_VETERAN = varpbit(12786, true);
    public static final Config PERFECT_ZALCANO = varpbit(12787, true);
    public static final Config TEAM_PLAYER = varpbit(12788, true);
    public static final Config THE_SPURNED_HERO = varpbit(12789, true);
    public static final Config KRIL_TSUTSAROTH_VETERAN = varpbit(12791, true);
    public static final Config THE_BANE_OF_DEMONS = varpbit(12794, true);
    public static final Config DEMONIC_DEFENCE = varpbit(12795, true);
    public static final Config HALF_WAY_THERE = varpbit(12799, true);
    public static final Config THE_II_JAD_CHALLENGE = varpbit(12819, true);
    public static final Config TZHAAR_KET_RAK_SPEED_TRIALIST = varpbit(12822, true);
    public static final Config FACING_JAD_HEAD_ON_III = varpbit(12825, true);
    public static final Config ANTICOAGULANTS = varpbit(12844, true);
    public static final Config APPROPRIATE_TOOLS = varpbit(12845, true);
    public static final Config THEY_WONT_EXPECT_THIS = varpbit(12845, true);
    public static final Config CHALLY_TIME = varpbit(12847, true);
    public static final Config NYLOCAS_ON_THE_ROCKS = varpbit(12848, true);
    public static final Config JUST_TO_BE_SAFE = varpbit(12849, true);
    public static final Config DONT_LOOK_AT_ME = varpbit(12850, true);
    public static final Config NO_PILLAR = varpbit(12851, true);
    public static final Config ATTACK_STEP_WAIT = varpbit(12852, true);
    public static final Config PASS_IT_ON = varpbit(12853, true);

    /**
     * Combat Achieve Master
     */
    public static final Config COLLATERAL_DAMAGE = varpbit(12469, true);
    public static final Config SWOOP_NO_MORE = varpbit(12471, true);
    public static final Config PRECISE_POSITIONING = varpbit(12508, true);
    public static final Config CERBERUS_MASTER = varpbit(12510, true);
    public static final Config AROOO_NO_MORE = varpbit(12511, true);
    public static final Config CORPOREAL_BEAST_MASTER = varpbit(12524, true);
    public static final Config PERFECT_GROTESQUE_GUARDIANS_II = varpbit(12549, true);
    public static final Config GROTESQUE_GURADIANS_SPEED_CHASER = varpbit(12551, true);
    public static final Config TIL_DAWN = varpbit(12554, true);
    public static final Config CORRUPTED_GAUNTLET_MASTER = varpbit(12557, true);
    public static final Config PERFECT_CORRUPTED_HUNLLEF = varpbit(12560, true);
    public static final Config DEFENCE_DOESNT_MATTER_II = varpbit(12561, true);
    public static final Config CORRUPTED_WARRIOR = varpbit(12563, true);
    public static final Config CORRUPTED_GAUNTLET_SPEED_CHASER = varpbit(12565, true);
    public static final Config GAUNTLET_MASTER = varpbit(12568, true);
    public static final Config PERFECT_CRSTALLINE_HUNLLEF = varpbit(12570, true);
    public static final Config DEFENCE_DOESNT_MATTER = varpbit(12571, true);
    public static final Config GAUNTLET_SPEED_CHASER = varpbit(12575, true);
    public static final Config HESPORI_SPEED_CHASER = varpbit(12586, true);
    public static final Config ALCHEMICAL_GRANDMASTER = varpbit(12594, true);
    public static final Config UNREQUIRED_ANTIPOISONS = varpbit(12595, true);
    public static final Config LIGHTNING_LURE = varpbit(12596, true);
    public static final Config DONT_FLAME_ME = varpbit(12597, true);
    public static final Config MIXING_CORRECTLY = varpbit(12598, true);
    public static final Config THE_FLAME_SKIPPER = varpbit(12599, true);
    public static final Config ALCLEANICAL_HYDRA = varpbit(12600, true);
    public static final Config ALCHEMICAL_SPEED_CHASER = varpbit(12602, true);
    public static final Config WORKING_OVERTIME = varpbit(12604, true);
    public static final Config FIGHT_CAVES_MASTER = varpbit(12606, true);
    public static final Config DENYING_THE_HEALERS = varpbit(12608, true);
    public static final Config YOU_DIDNT_SAY_ANYTHING_ABOUT_A_BAT = varpbit(12610, true);
    public static final Config FIGHT_CAVES_SPEED_CHASER = varpbit(12613, true);
    public static final Config ONE_HUNDRED_TENTACLES = varpbit(12630, true);
    public static final Config NIGHTMARE_VETERAN = varpbit(12643, true);
    public static final Config PERFECT_NIGHTMARE = varpbit(12646, true);
    public static final Config NIGHTMARE_SOLO_SPEED_TRIALIST = varpbit(12649, true);
    public static final Config NIGHTMARE_SOLO_SPEED_CHASER = varpbit(12650, true);
    public static final Config NIGHTMARE_FIVE_SCALE_SPEED_CHASER = varpbit(12653, true);
    public static final Config MOVING_COLLATERAL = varpbit(12673, true);
    public static final Config ZULRAH_MASTER = varpbit(12684, true);
    public static final Config PERFECT_ZULRAH = varpbit(12687, true);
    public static final Config ZULRAH_SPEED_CHASER = varpbit(12689, true);
    public static final Config THEATRE_OF_BLOOD_MASTER = varpbit(12697, true);
    public static final Config POP_IT = varpbit(12699, true);
    public static final Config A_TIMELY_SNACK = varpbit(12700, true);
    public static final Config PERFECT_MAIDEN = varpbit(12702, true);
    public static final Config PERFECT_BLOAT = varpbit(12703, true);
    public static final Config PERFECT_NYLOCAS = varpbit(12704, true);
    public static final Config PERFECT_SOTESTEG = varpbit(12705, true);
    public static final Config PERFECT_XARPUS = varpbit(12706, true);
    public static final Config PERFECT_VERZIK = varpbit(12707, true);
    public static final Config CANT_DRAIN_THIS = varpbit(12708, true);
    public static final Config CAN_YOU_DANCE = varpbit(12709, true);
    public static final Config BACK_IN_MY_DAY = varpbit(12711, true);
    public static final Config THEATRE_TRIO_SPEED_CHASER = varpbit(12713, true);
    public static final Config THEATRE_FOUR_SCALE_SPEED_CHASER = varpbit(12715, true);
    public static final Config THEATRE_FIVE_SCALE_SPEED_CHASER = varpbit(12717, true);
    public static final Config VORKATH_MASTER = varpbit(12727, true);
    public static final Config THE_WALK = varpbit(12728, true);
    public static final Config DODGING_THE_DRAGON = varpbit(12730, true);
    public static final Config VORKATH_SPEED_CHASER = varpbit(12734, true);
    public static final Config EXTENDED_ENCOUNTER = varpbit(12736, true);
    public static final Config CHAMBERS_OF_XERIC_CM_MASTER = varpbit(12746, true);
    public static final Config IMMORTAL_RAID_TEAM = varpbit(12748, true);
    public static final Config IMMORTAL_RAIDER = varpbit(12749, true);
    public static final Config CHAMBERS_OF_XERIC_SOLO_CM_SPEED_CHASER = varpbit(12751, true);
    public static final Config CHAMBERS_OF_XERIC_FIVE_SCALE_CM_SPEED_CHASER = varpbit(12753, true);
    public static final Config CHAMBERS_OF_XERIC_CM_TRIO_SPEED_CHASER = varpbit(12755, true);
    public static final Config CHAMBERS_OF_XERIC_MASTER = varpbit(12758, true);
    public static final Config NO_TIME_FOR_DEATH = varpbit(12762, true);
    public static final Config PUTTING_IT_OLM_ON_THE_LINE = varpbit(12763, true);
    public static final Config A_NOT_SO_SPECIAL_LIZARD = varpbit(12764, true);
    public static final Config STOP_DROP_AND_ROLL = varpbit(12767, true);
    public static final Config ANVIL_NO_MORE = varpbit(12768, true);
    public static final Config UNDYING_RAIDER = varpbit(12771, true);
    public static final Config PLAYING_WITH_LASERS = varpbit(12773, true);
    public static final Config PERFECT_OLM_SOLO = varpbit(12775, true);
    public static final Config PERFECT_OLM_TRIO = varpbit(12776, true);
    public static final Config BLIND_SPOT = varpbit(12777, true);
    public static final Config CHAMBERS_OF_XERIC_SOLO_SPEED_CHASER = varpbit(12780, true);
    public static final Config CHAMBERS_OF_XERIC_FIVE_SCALE_SPEED_CHASER = varpbit(12782, true);
    public static final Config CHAMBERS_OF_XERIC_TRIO_SPEED_CHASER = varpbit(12784, true);
    public static final Config NIBBLERS_BEGONE = varpbit(12804, true);
    public static final Config THE_IV_JAD_CHALLENGE = varpbit(12820, true);
    public static final Config TZHAAR_KET_RAK_SPEED_CHASER = varpbit(12823, true);
    public static final Config FACING_JAD_HEAD_ON_IV = varpbit(12826, true);
    public static final Config SUPPLIES_WHO_NEEDS_EM = varpbit(12827, true);
    public static final Config MULTI_STYLE_SPECIALIST = varpbit(12829, true);
    public static final Config HARD_MORE_COMPLETED_IT = varpbit(12839, true);
    public static final Config THEATRE_OF_BLOOD_SM_SPEED_CHASER = varpbit(12854, true);

    /**
     * Combat Achieve GrandMaster
     */
    public static final Config THE_WORST_RANGED_WEAPON = varpbit(12472, true);
    public static final Config FEATHER_HUNTER = varpbit(12473, true);
    public static final Config DEFENCE_MATTERS = varpbit(12479, true);
    public static final Config KEEP_AWAY = varpbit(12480, true);
    public static final Config OURG_KILLER = varpbit(12481, true);
    public static final Config GROTESQUE_GUARDIANS_SPEED_RUNNER = varpbit(12552, true);
    public static final Config CORRUPTED_GAUNTLET_GRANDMASTER = varpbit(12558, true);
    public static final Config ENGINOL_DIET_II = varpbit(12562, true);
    public static final Config WOLF_PUNCHER_II = varpbit(12564, true);
    public static final Config CORRUPTED_GAUNTLET_SPEED_RUNNER = varpbit(12566, true);
    public static final Config GAUNTLET_SPEED_RUNNER = varpbit(12576, true);
    public static final Config NO_PRESSURE = varpbit(12601, true);
    public static final Config ALCHEMICAL_SPEED_RUNNER = varpbit(12603, true);
    public static final Config DENYING_THE_HEALERS_II = varpbit(12609, true);
    public static final Config NO_TIME_FOR_A_DRINK = varpbit(12612, true);
    public static final Config FIGHT_CAVES_SPEED_RUNNER = varpbit(12614, true);
    public static final Config TERRIBLE_PARENT = varpbit(12644, true);
    public static final Config A_LONG_TRIP = varpbit(12648, true);
    public static final Config NIGHTMARE_SOLO_SPEED_RUNNER = varpbit(12651, true);
    public static final Config NIGHTMARE_FIVE_SCALE_SPEED_RUNNER = varpbit(12654, true);
    public static final Config ANIMAL_WHISPERER = varpbit(12672, true);
    public static final Config PEACH_CONJURER = varpbit(12675, true);
    public static final Config ZULRAH_SPEED_RUNNER = varpbit(12690, true);
    public static final Config THEATRE_OF_BLOOD_GRANDMASTER = varpbit(12698, true);
    public static final Config PERFECT_THEATRE = varpbit(12701, true);
    public static final Config MORYTANIA_ONLY = varpbit(12710, true);
    public static final Config THEATRE_DUO_SPEED_RUNNER = varpbit(12712, true);
    public static final Config THEATRE_TRIO_SPEED_RUNNER = varpbit(12714, true);
    public static final Config THEATRE_FOUR_SCALE_SPEED_RUNNER = varpbit(12716, true);
    public static final Config THEATRE_FIVE_SCALE_SPEED_RUNNER = varpbit(12718, true);
    public static final Config FAITHLESS_ENCOUNTER = varpbit(12732, true);
    public static final Config THE_FREMENNIK_WAY = varpbit(12733, true);
    public static final Config VORKATH_SPEED_RUNNER = varpbit(12735, true);
    public static final Config CHAMBERS_OF_XERIC_CM_GRANDMASTER = varpbit(12747, true);
    public static final Config CHAMBERS_OF_XERIC_CM_SOLO_SPEED_RUNNER = varpbit(12752, true);
    public static final Config CHAMBERS_OF_XERIC_CM_FIVE_SCALE_SPEED_RUNNER = varpbit(12754, true);
    public static final Config CHAMBERS_OF_XERIC_CM_TRIO_SPEED_RUNNER = varpbit(12756, true);
    public static final Config CHAMBERS_OF_XERIC_GRANDMASTER = varpbit(12759, true);
    public static final Config CHAMBERS_OF_XERIC_SOLO_SPEED_RUNNER = varpbit(12781, true);
    public static final Config CHAMBERS_OF_XERIC_FIVE_SCALE_SPEED_RUNNER = varpbit(12783, true);
    public static final Config CHAMBERS_OF_XERIC_TRIO_SPEED_RUNNER = varpbit(12785, true);
    public static final Config DEMON_WHISPERER = varpbit(12796, true);
    public static final Config ASH_COLLECTOR = varpbit(12798, true);
    public static final Config INFERNO_GRANDMASTER = varpbit(12800, true);
    public static final Config THE_FLOOR_IS_LAVA = varpbit(12801, true);
    public static final Config PLAYING_WITH_JADS = varpbit(12802, true);
    public static final Config NO_LUCK_REQUIRED = varpbit(12803, true);
    public static final Config WASNT_EVEN_CLOSE = varpbit(12805, true);
    public static final Config BUDGET_SETUP = varpbit(12806, true);
    public static final Config NIBBLER_CHASER = varpbit(12807, true);
    public static final Config FACING_JAD_HEAD_ON_II = varpbit(12808, true);
    public static final Config JAD_WHAT_ARE_YOU_DOING_HERE = varpbit(12809, true);
    public static final Config INFERNO_SPEED_RUNNER = varpbit(12810, true);
    public static final Config THE_VI_JAD_CHALLENGE = varpbit(12820, true);
    public static final Config TZHAAR_KET_RAK_SPEED_RUNNER = varpbit(12824, true);
    public static final Config IT_WASNT_A_FLUKE = varpbit(12828, true);
    public static final Config STOP_RIGHT_THERE = varpbit(12830, true);
    public static final Config PERSONAL_SPACE = varpbit(12831, true);
    public static final Config ROYAL_AFFAIRS = varpbit(12832, true);
    public static final Config HARDER_MODE_I = varpbit(12833, true);
    public static final Config HARDER_MODE_II = varpbit(12834, true);
    public static final Config NYLO_SNIPER = varpbit(12835, true);
    public static final Config TEAM_WORK_MAKES_THE_DREAM_WORK = varpbit(12836, true);
    public static final Config HARDER_MODE_III = varpbit(12837, true);
    public static final Config PACK_LIKE_A_YAK = varpbit(12838, true);
    public static final Config THEATRE_HM_TRIO_SPEED_RUNNER = varpbit(12840, true);
    public static final Config THEATRE_HM_FOUR_SCALE_SPEED_RUNNER = varpbit(12841, true);
    public static final Config THEATRE_HM_FIVE_SCALE_SPEED_RUNNER = varpbit(12842, true);
    public static final Config THEATRE_OF_BLOOD_HM_GRANDMASTER = varpbit(12843, true);

    /**
     * Combat achievements overview (interface 717)
     */
    public static final Config COMBAT_ACHIVEMENTS_OVERVIEW_EASY = varpbit(12885, true);//max value is 33
    public static final Config COMBAT_ACHIVEMENTS_OVERVIEW_MEDIUM = varpbit(12886, true);//max value is 41
    public static final Config COMBAT_ACHIVEMENTS_OVERVIEW_HARD = varpbit(12887, true);//max value is 58
    public static final Config COMBAT_ACHIVEMENTS_OVERVIEW_ELITE = varpbit(12888, true);//max value is 109
    public static final Config COMBAT_ACHIVEMENTS_OVERVIEW_MASTER = varpbit(12889, true);//max value is 96
    public static final Config COMBAT_ACHIVEMENTS_OVERVIEW_GRANDMASTER = varpbit(12890, true);//max value is 73

    /**
     * Combat Achievements bosses
     */
    public static final Config CHAMBERS_OF_XERIC = varpbit(12891, true);//max value of 29
    public static final Config CHAMBERS_OF_XERIC_CHALLENGE_MODE = varpbit(12892, true);//max value of 11
    public static final Config BARROWS = varpbit(12893, true);//max value of 7
    public static final Config KREE_ARRA = varpbit(12894, true);//max value of 7
    public static final Config GENERAL_GRAARDOR = varpbit(12895, true);//max value of 8
    public static final Config COMMANDER_ZILYANA = varpbit(12896, true);//max value of 7
    public static final Config KRIL_TSUTSAROTH = varpbit(12897, true);//max value of 9
    public static final Config DAGANNOTH_PRIME = varpbit(12898, true);//max value of 4
    public static final Config DAGANNOTH_REX = varpbit(12899, true);//max value of 4
    public static final Config DAGANNOTH_SUPREME = varpbit(12900, true);//max value of 4
    public static final Config CALLISTO = varpbit(12901, true);//max value of 2
    public static final Config VENENATIS = varpbit(12902, true);//max value of 2
    public static final Config VETION = varpbit(12903, true);//max value of 2
    public static final Config CHAOS_ELEMENTAL = varpbit(12904, true);//max value of 4
    public static final Config KING_BLACK_DRAGON = varpbit(12905, true);//max value of 6
    public static final Config GIANT_MOLE = varpbit(12906, true);//max value of 6
    public static final Config KALPHITE_QUEEN = varpbit(12907, true);//max value of 5
    public static final Config CORPOREAL_BEAST = varpbit(12908, true);//max value of 5
    public static final Config ZULRAH = varpbit(12909, true);//max value of 9
    public static final Config CHAOS_FANATIC = varpbit(12910, true);//max value of 4
    public static final Config SCORPIA = varpbit(12911, true);//max value of 4
    public static final Config CRAZY_ARCHAEOLOGIST = varpbit(12912, true);//max value of 4
    public static final Config TZTOK_JAD = varpbit(12913, true);//max value of 10
    public static final Config KRAKEN = varpbit(12914, true);//max value of 5
    public static final Config THERMONUCLEAR_SMOKE_DEVIL = varpbit(12915, true);//max value of 3
    public static final Config CERBERUS = varpbit(12916, true);//max value of 6
    public static final Config ABYSSAL_SIRE = varpbit(12917, true);//max value of 8
    public static final Config SKOTIZO = varpbit(12918, true);//max value of 7
    public static final Config WINTERTODT = varpbit(12919, true);//max value of 8
    public static final Config OBOR = varpbit(12920, true);//max value of 6
    public static final Config TZKAL_ZUK = varpbit(12921, true);//max value of 12
    public static final Config DERANGED_ARCHAEOLOGIST = varpbit(12922, true);//max value of 4
    public static final Config GROTESQUE_GUARDIANS = varpbit(12923, true);//max value of 15
    public static final Config VORKATH = varpbit(12924, true);//max value of 11
    public static final Config BRYOPHYTA = varpbit(12925, true);//max value of 7
    public static final Config ALCHEMICAL_HYDRA = varpbit(12926, true);//max value of 12
    public static final Config HESPORI = varpbit(12927, true);//max value of 6
    public static final Config THE_MIMIC = varpbit(12928, true);//max value of 1
    public static final Config SARACHNIS = varpbit(12929, true);//max value of 5
    public static final Config ZALCANO = varpbit(12930, true);//max value of 4
    public static final Config CRYSTALLINE_HUNLLEF = varpbit(12931, true);//max value of 10
    public static final Config CORRUPTED_HUNLLEF = varpbit(12932, true);//max value of 11
    public static final Config THE_NIGHTMARE = varpbit(12934, true);//max value of 14
    public static final Config THEATRE_OF_BLOOD_ACHIEVEMENT = varpbit(12935, true);//max value of 24
    public static final Config TEMPOROSS = varpbit(12936, true);//max value of 8
    public static final Config TZHAAR_KET_RAKS_CHALLENGES = varpbit(12937, true);//max value of 11
    public static final Config THEATRE_OF_BLOOD_HARD_MODE = varpbit(12938, true);//max value of 14
    public static final Config THEATRE_OF_BLOOD_ENTRY_MODE = varpbit(12939, true);//max value of 12


    public static final Config COLLECTION_LOG_ACHIEVEMENTS = varpbit(6906, true);//max value is 36 THIS IS SENT WHEN YOU CLICK "Collection-Log" on interface 713

    /**
     * Bird houses
     */
    public static final Config BIRD_HOUSE_MEADOW_NORTH = varp(1626, true);
    public static final Config BIRD_HOUSE_MEADOW_SOUTH = varp(1627, true);
    public static final Config BIRD_HOUSE_VALLEY_NORTH = varp(1628, true);
    public static final Config BIRD_HOUSE_VALLEY_SOUTH = varp(1629, true);

    /**
     * Fossil Storage
     */

    public static final Config SMALL_FOSSLISED_LIMBS = varpbit(5832, true);
    public static final Config SMALL_FOSSLISED_SPINE = varpbit(5833, true);
    public static final Config SMALL_FOSSLISED_RIBS = varpbit(5834, true);
    public static final Config SMALL_FOSSLISED_PELVIS = varpbit(5835, true);
    public static final Config SMALL_FOSSLISED_SKULL = varpbit(5836, true);
    public static final Config MEDIUM_FOSSLISED_LIMBS = varpbit(5837, true);
    public static final Config MEDIUM_FOSSLISED_SPINE = varpbit(5838, true);
    public static final Config MEDIUM_FOSSLISED_RIBS = varpbit(5839, true);
    public static final Config MEDIUM_FOSSLISED_PELVIS = varpbit(5840, true);
    public static final Config MEDIUM_FOSSLISED_SKULL = varpbit(5841, true);
    public static final Config LARGE_FOSSLISED_LIMBS = varpbit(5842, true);
    public static final Config LARGE_FOSSLISED_SPINE = varpbit(5843, true);
    public static final Config LARGE_FOSSLISED_RIBS = varpbit(5844, true);
    public static final Config LARGE_FOSSLISED_PELVIS = varpbit(5845, true);
    public static final Config LARGE_FOSSLISED_SKULL = varpbit(5846, true);
    public static final Config RARE_FOSSLISED_LIMBS = varpbit(5852, true);
    public static final Config RARE_FOSSLISED_SPINE = varpbit(5853, true);
    public static final Config RARE_FOSSLISED_RIBS = varpbit(5854, true);
    public static final Config RARE_FOSSLISED_PELVIS = varpbit(5855, true);
    public static final Config RARE_FOSSLISED_SKULL = varpbit(5856, true);
    public static final Config RARE_FOSSLISED_TUSK = varpbit(5952, true);
    public static final Config UNIDENTIFIED_SMALL_FOSSIL = varpbit(5828, true);
    public static final Config UNIDENTIFIED_MEDIUM_FOSSIL = varpbit(5829, true);
    public static final Config UNIDENTIFIED_LARGE_FOSSIL = varpbit(5830, true);
    public static final Config UNIDENTIFIED_RARE_FOSSIL = varpbit(5831, true);
    public static final Config FOSSILISED_ROOTS = varpbit(5847, true);
    public static final Config FOSSILISED_STUMP = varpbit(5848, true);
    public static final Config FOSSILISED_BRANCH = varpbit(5849, true);
    public static final Config FOSSILISED_LEAF = varpbit(5850, true);
    public static final Config FOSSILISED_MUSHROOM = varpbit(5851, true);
    /**
     * Achievement Diaries
     */


    public static final Config KARAMJA_EASY = varpbit(2423, true);
    public static final Config KARAMJA_MEDIUM = varpbit(6288, true);
    public static final Config KARAMJA_HARD = varpbit(6289, true);
    public static final Config KARAMJA_ELITE = varpbit(6290, true);

    public static final Config PVM_EASY_COMPLETED = varpbit(3577, true);
    public static final Config PVM_MEDIUM_COMPLETED = varpbit(3598, true);
    public static final Config PVM_HARD_COMPLETED = varpbit(3610, true);
    public static final Config PVM_ELITE_COMPLETED = varpbit(4567, true);

    public static final Config PVP_EASY_COMPLETED = varpbit(4499, true);
    public static final Config PVP_MEDIUM_COMPLETED = varpbit(4500, true);
    public static final Config PVP_HARD_COMPLETED = varpbit(4501, true);
    public static final Config PVP_ELITE_COMPLETED = varpbit(4502, true);

    public static final Config ARDOUGNE_EASY = varpbit(6291, true);
    public static final Config ARDOUGNE_MEDIUM = varpbit(6292, true);
    public static final Config ARDOUGNE_HARD = varpbit(6293, true);
    public static final Config ARDOUGNE_ELITE = varpbit(6294, true);

    public static final Config MINIGAMES_EASY_COMPLETED = varpbit(4523, true);
    public static final Config MINIGAMES_MEDIUM_COMPLETED = varpbit(4524, true);
    public static final Config MINIGAMES_HARD_COMPLETED = varpbit(4525, true);
    public static final Config MINIGAMES_ELITE_COMPLETED = varpbit(4526, true);

    public static final Config DESERT_EASY = varpbit(6295, true);
    public static final Config DESERT_MEDIUM = varpbit(6296, true);
    public static final Config DESERT_HARD = varpbit(6297, true);
    public static final Config DESERT_ELITE = varpbit(6298, true);

    public static final Config SKILLING_EASY_COMPLETED = varpbit(4503, true);
    public static final Config SKILLING_MEDIUM_COMPLETED = varpbit(4504, true);
    public static final Config SKILLING_HARD_COMPLETED = varpbit(4505, true);
    public static final Config SKILLING_ELITE_COMPLETED = varpbit(4506, true);

    public static final Config FALADOR_EASY = varpbit(6299, true);
    public static final Config FALADOR_MEDIUM = varpbit(6300, true);
    public static final Config FALADOR_HARD = varpbit(6301, true);
    public static final Config FALADOR_ELITE = varpbit(6302, true);

    public static final Config FREMMY_EASY_COMPLETED = varpbit(4531, true);
    public static final Config FREMMY_MEDIUM_COMPLETED = varpbit(4532, true);
    public static final Config FREMMY_HARD_COMPLETED = varpbit(4533, true);
    public static final Config FREMMY_ELITE_COMPLETED = varpbit(4534, true);

    public static final Config FREMMY_EASY = varpbit(6303, true);
    public static final Config FREMMY_MEDIUM = varpbit(6304, true);
    public static final Config FREMMY_HARD = varpbit(6305, true);
    public static final Config FREMMY_ELITE = varpbit(6306, true);

    public static final Config KANDARIN_EASY_COMPLETED = varpbit(4515, true);
    public static final Config KANDARIN_MEDIUM_COMPLETED = varpbit(4516, true);
    public static final Config KANDARIN_HARD_COMPLETED = varpbit(4517, true);
    public static final Config KANDARIN_ELITE_COMPLETED = varpbit(4518, true);

    public static final Config KANDARIN_EASY = varpbit(6307, true);
    public static final Config KANDARIN_MEDIUM = varpbit(6308, true);
    public static final Config KANDARIN_HARD = varpbit(6309, true);
    public static final Config KANDARIN_ELITE = varpbit(6310, true);

    public static final Config LUMBRIDGE_EASY_COMPLETED = varpbit(4535, true);
    public static final Config LUMBRIDGE_MEDIUM_COMPLETED = varpbit(4536, true);
    public static final Config LUMBRIDGE_HARD_COMPLETED = varpbit(4537, true);
    public static final Config LUMBRIDGE_ELITE_COMPLETED = varpbit(4538, true);

    public static final Config LUMBRIDGE_EASY = varpbit(6311, true);
    public static final Config LUMBRIDGE_MEDIUM = varpbit(6312, true);
    public static final Config LUMBRIDGE_HARD = varpbit(6313, true);
    public static final Config LUMBRIDGE_ELITE = varpbit(6314, true);

    public static final Config MORYTANIA_EASY_COMPLETED = varpbit(4527, true);
    public static final Config MORYTANIA_MEDIUM_COMPLETED = varpbit(4528, true);
    public static final Config MORYTANIA_HARD_COMPLETED = varpbit(4529, true);
    public static final Config MORYTANIA_ELITE_COMPLETED = varpbit(4530, true);

    public static final Config MORYTANIA_EASY = varpbit(6315, true);
    public static final Config MORYTANIA_MEDIUM = varpbit(6316, true);
    public static final Config MORYTANIA_HARD = varpbit(6317, true);
    public static final Config MORYTANIA_ELITE = varpbit(6318, true);

    public static final Config VARROCK_EASY_COMPLETED = varpbit(4519, true);
    public static final Config VARROCK_MEDIUM_COMPLETED = varpbit(4520, true);
    public static final Config VARROCK_HARD_COMPLETED = varpbit(4521, true);
    public static final Config VARROCK_ELITE_COMPLETED = varpbit(4522, true);

    public static final Config VARROCK_EASY = varpbit(6319, true);
    public static final Config VARROCK_MEDIUM = varpbit(6320, true);
    public static final Config VARROCK_HARD = varpbit(6321, true);
    public static final Config VARROCK_ELITE = varpbit(6322, true);


    public static final Config WILDERNESS_EASY_COMPLETED = varpbit(4507, true);
    public static final Config WILDERNESS_MEDIUM_COMPLETED = varpbit(4508, true);
    public static final Config WILDERNESS_HARD_COMPLETED = varpbit(4509, true);
    public static final Config WILDERNESS_ELITE_COMPLETED = varpbit(4510, true);

    public static final Config WILDERNESS_EASY = varpbit(6323, true);
    public static final Config WILDERNESS_MEDIUM = varpbit(6324, true);
    public static final Config WILDERNESS_HARD = varpbit(6325, true);
    public static final Config WILDERNESS_ELITE = varpbit(6326, true);

    public static final Config WESTERN_PROV_EASY_COMPLETED = varpbit(4511, true);
    public static final Config WESTERN_PROV_MEDIUM_COMPLETED = varpbit(4512, true);
    public static final Config WESTERN_PROV_HARD_COMPLETED = varpbit(4513, true);
    public static final Config WESTERN_PROV_ELITE_COMPLETED = varpbit(4514, true);

    public static final Config WESTERN_PROV_EASY = varpbit(6327, true);
    public static final Config WESTERN_PROV_MEDIUM = varpbit(6328, true);
    public static final Config WESTERN_PROV_HARD = varpbit(6329, true);
    public static final Config WESTERN_PROV_ELITE = varpbit(6330, true);

    public static final Config DEVIOUS_EASY_COMPLETED = varpbit(7929, true);
    public static final Config DEVIOUS_MEDIUM_COMPLETED = varpbit(7930, true);
    public static final Config DEVIOUS_HARD_COMPLETED = varpbit(7931, true);
    public static final Config DEVIOUS_ELITE_COMPLETED = varpbit(7932, true);

    public static final Config KOUREND_EASY = varpbit(7933, true);
    public static final Config KOUREND_MEDIUM = varpbit(7934, true);
    public static final Config KOUREND_HARD = varpbit(7935, true);
    public static final Config KOUREND_ELITE = varpbit(7936, true);
    /**
     * Quests
     */

/*
    public static final Config BLACK_KNIGHTS_FORTRESS = varp(299, true).defaultValue(1);
    public static final Config COOKS_ASSISTANT = varp(300, true).defaultValue(1);
    public static final Config CORSAIR_CURSE = varpbit(301, true).defaultValue(1);
    public static final Config DEMON_SLAYER = varpbit(302, true).defaultValue(1);
    public static final Config DORIC_QUEST = varp(303, true).defaultValue(1);
    public static final Config DRAGON_SLAYER = varp(304, true).defaultValue(1);
    public static final Config ERNEST_THE_CHICKEN = varp(305, true).defaultValue(1);
    public static final Config GOBLIN_DIPLOMACY = varpbit(306, true).defaultValue(1);
    public static final Config IMP_CATCH = varp(307, true).defaultValue(1);
    public static final Config KNIGHT_SWORD = varp(308, true).defaultValue(1);
    public static final Config MISTHALIN_MYSTERY = varpbit(309, true).defaultValue(1);
    public static final Config PIRATE_TREASURE = varp(310, true).defaultValue(1);
    public static final Config PRINCE_ALI = varp(311, true).defaultValue(1);
    public static final Config RESTLESS_GHOST = varp(312, true).defaultValue(1);
    public static final Config ROME_JULIET = varp(313, true).defaultValue(1);
    public static final Config RUNE_MYSTERIES = varp(314, true).defaultValue(1);
    public static final Config SHEEP_SHEARER = varp(315, true).defaultValue(1);
    public static final Config SHIELD_ARRAV = varp(316, true).defaultValue(1);
    public static final Config VAMPIRE_SLAYER = varp(317, true).defaultValue(1);
    public static final Config WITCH_POTION = varp(318, true).defaultValue(1);
    public static final Config QUEST_VARPS1 = varpbit(319, true).defaultValue(1);
    public static final Config QUEST_VARPS2 = varpbit(320, true).defaultValue(1);
    public static final Config QUEST_VARPS3 = varpbit(321, true).defaultValue(1);
    public static final Config QUEST_VARPS4 = varp(322, true).defaultValue(1);
    public static final Config QUEST_VARPS5 = varpbit(323, true).defaultValue(1);
    public static final Config QUEST_VARPS6 = varpbit(324, true).defaultValue(1);
    public static final Config QUEST_VARPS7 = varpbit(325, true).defaultValue(1);
    public static final Config QUEST_VARPS8 = varpbit(326, true).defaultValue(1);
    public static final Config QUEST_VARPS9 = varp(327, true).defaultValue(1);
    public static final Config QUEST_VARPS10 = varpbit(328, true).defaultValue(1);
    public static final Config QUEST_VARPS11 = varpbit(329, true).defaultValue(1);
    public static final Config QUEST_VARPS12 = varpbit(330, true).defaultValue(1);
    public static final Config QUEST_VARPS13 = varpbit(602, true).defaultValue(1);
    public static final Config QUEST_VARPS14 = varpbit(603, true).defaultValue(1);
    public static final Config QUEST_VARPS15 = varpbit(543, true).defaultValue(1);
    public static final Config QUEST_VARPS16 = varpbit(542, true).defaultValue(1);
    public static final Config QUEST_VARPS17 = varpbit(449, true).defaultValue(1);
    public static final Config QUEST_VARPS18 = varp(448, true).defaultValue(1);
    public static final Config QUEST_VARPS19 = varpbit(331, true).defaultValue(1);
    public static final Config QUEST_VARPS20 = varpbit(332, true).defaultValue(1);
    public static final Config QUEST_VARPS21 = varpbit(333, true).defaultValue(1);
    public static final Config QUEST_VARPS22 = varp(334, true).defaultValue(1);
    public static final Config QUEST_VARPS23 = varp(335, true).defaultValue(1);
    public static final Config QUEST_VARPS24 = varp(336, true).defaultValue(1);
    public static final Config QUEST_VARPS25 = varp(337, true).defaultValue(1);
    public static final Config QUEST_VARPS26 = varpbit(338, true).defaultValue(1);
    public static final Config QUEST_VARPS27 = varpbit(339, true).defaultValue(1);
    public static final Config QUEST_VARPS28 = varp(340, true).defaultValue(1);
    public static final Config QUEST_VARPS29 = varpbit(341, true).defaultValue(1);
    public static final Config QUEST_VARPS31 = varpbit(342, true).defaultValue(1);
    public static final Config QUEST_VARPS32 = varpbit(343, true).defaultValue(1);
    public static final Config QUEST_VARPS33 = varpbit(344, true).defaultValue(1);
    public static final Config QUEST_VARPS34 = varpbit(345, true).defaultValue(1);
    public static final Config QUEST_VARPS35 = varpbit(346, true).defaultValue(1);
    public static final Config QUEST_VARPS36 = varp(347, true).defaultValue(1);
    public static final Config QUEST_VARPS37 = varpbit(348, true).defaultValue(1);
    public static final Config QUEST_VARPS38 = varpbit(349, true).defaultValue(1);
    public static final Config QUEST_VARPS39 = varp(350, true).defaultValue(1);
    public static final Config QUEST_VARPS40 = varpbit(351, true).defaultValue(1);
    public static final Config QUEST_VARPS41 = varp(352, true).defaultValue(1);
    public static final Config QUEST_VARPS42 = varpbit(353, true).defaultValue(1);
    public static final Config QUEST_VARPS43 = varp(354, true).defaultValue(1);
    public static final Config QUEST_VARPS44 = varpbit(355, true).defaultValue(1);
    public static final Config QUEST_VARPS45 = varpbit(356, true).defaultValue(1);
    public static final Config QUEST_VARPS46 = varpbit(357, true).defaultValue(1);
    public static final Config QUEST_VARPS47 = varpbit(358, true).defaultValue(1);
    public static final Config QUEST_VARPS48 = varpbit(359, true).defaultValue(1);
    public static final Config QUEST_VARPS49 = varpbit(360, true).defaultValue(1);
    public static final Config QUEST_VARPS50 = varp(361, true).defaultValue(1);
    public static final Config QUEST_VARPS51 = varpbit(362, true).defaultValue(1);
    public static final Config QUEST_VARPS52 = varpbit(363, true).defaultValue(1);
    public static final Config QUEST_VARPS53 = varp(364, true).defaultValue(1);
    public static final Config QUEST_VARPS54 = varpbit(365, true).defaultValue(1);
    public static final Config QUEST_VARPS55 = varpbit(366, true).defaultValue(1);
    public static final Config QUEST_VARPS56 = varpbit(367, true).defaultValue(1);
    public static final Config QUEST_VARPS57 = varp(368, true).defaultValue(1);
    public static final Config QUEST_VARPS58 = varpbit(369, true).defaultValue(1);
    public static final Config QUEST_VARPS59 = varp(370, true).defaultValue(1);
    public static final Config QUEST_VARPS60 = varpbit(371, true).defaultValue(1);
    public static final Config QUEST_VARPS61 = varpbit(372, true).defaultValue(1);
    public static final Config QUEST_VARPS62 = varpbit(373, true).defaultValue(1);
    public static final Config QUEST_VARPS63 = varp(374, true).defaultValue(1);
    public static final Config QUEST_VARPS64 = varp(375, true).defaultValue(1);
    public static final Config QUEST_VARPS65 = varpbit(376, true).defaultValue(1);
    public static final Config QUEST_VARPS66 = varpbit(377, true).defaultValue(1);
    public static final Config QUEST_VARPS67 = varp(378, true).defaultValue(1);
    public static final Config QUEST_VARPS68 = varp(379, true).defaultValue(1);
    public static final Config QUEST_VARPS69 = varp(380, true).defaultValue(1);
    public static final Config QUEST_VARPS71 = varp(381, true).defaultValue(1);
    public static final Config QUEST_VARPS72 = varpbit(382, true).defaultValue(1);
    public static final Config QUEST_VARPS73 = varpbit(383, true).defaultValue(1);
    public static final Config QUEST_VARPS74 = varpbit(384, true).defaultValue(1);
    public static final Config QUEST_VARPS75 = varp(385, true).defaultValue(1);
    public static final Config QUEST_VARPS76 = varp(386, true).defaultValue(1);
    public static final Config QUEST_VARPS77 = varpbit(387, true).defaultValue(1);
    public static final Config QUEST_VARPS78 = varp(388, true).defaultValue(1);
    public static final Config QUEST_VARPS79 = varp(389, true).defaultValue(1);
    public static final Config QUEST_VARPS80 = varpbit(390, true).defaultValue(1);
    public static final Config QUEST_VARPS81 = varpbit(390, true).defaultValue(1);
    public static final Config QUEST_VARPS82 = varpbit(391, true).defaultValue(1);
    public static final Config QUEST_VARPS83 = varpbit(392, true).defaultValue(1);
    public static final Config QUEST_VARPS84 = varpbit(393, true).defaultValue(1);
    public static final Config QUEST_VARPS85 = varp(394, true).defaultValue(1);
    public static final Config QUEST_VARPS86 = varp(395, true).defaultValue(1);
    public static final Config QUEST_VARPS87 = varpbit(396, true).defaultValue(1);
    public static final Config QUEST_VARPS88 = varp(397, true).defaultValue(1);
    public static final Config QUEST_VARPS89 = varpbit(398, true).defaultValue(1);
    public static final Config QUEST_VARPS90 = varp(399, true).defaultValue(1);
    public static final Config QUEST_VARPS91 = varpbit(400, true).defaultValue(1);
    public static final Config QUEST_VARPS92 = varp(401, true).defaultValue(1);
    public static final Config QUEST_VARPS93 = varpbit(402, true).defaultValue(1);
    public static final Config QUEST_VARPS94 = varp(403, true).defaultValue(1);
    public static final Config QUEST_VARPS95 = varp(404, true).defaultValue(1);
    public static final Config QUEST_VARPS96 = varpbit(405, true).defaultValue(1);
    public static final Config QUEST_VARPS97 = varp(406, true).defaultValue(1);
    public static final Config QUEST_VARPS98 = varp(407, true).defaultValue(1);
    public static final Config QUEST_VARPS99 = varp(408, true).defaultValue(1);
    public static final Config QUEST_VARPS100 = varpbit(409, true).defaultValue(1);
    public static final Config QUEST_VARPS101 = varp(410, true).defaultValue(1);
    public static final Config QUEST_VARPS102 = varp(411, true).defaultValue(1);
    public static final Config QUEST_VARPS103 = varpbit(412, true).defaultValue(1);
    public static final Config QUEST_VARPS104 = varpbit(413, true).defaultValue(1);
    public static final Config QUEST_VARPS105 = varpbit(414, true).defaultValue(1);
    public static final Config QUEST_VARPS106 = varp(415, true).defaultValue(1);
    public static final Config QUEST_VARPS107 = varp(416, true).defaultValue(1);
    public static final Config QUEST_VARPS108 = varpbit(417, true).defaultValue(1);
    public static final Config QUEST_VARPS109 = varp(418, true).defaultValue(1);
    public static final Config QUEST_VARPS110 = varp(419, true).defaultValue(1);
    public static final Config QUEST_VARPS111 = varp(420, true).defaultValue(1);
    public static final Config QUEST_VARPS112 = varp(421, true).defaultValue(1);
    public static final Config QUEST_VARPS113 = varpbit(422, true).defaultValue(1);
    public static final Config QUEST_VARPS114 = varp(423, true).defaultValue(1);
    public static final Config QUEST_VARPS115 = varp(424, true).defaultValue(1);
    public static final Config QUEST_VARPS116 = varpbit(425, true).defaultValue(1);
    public static final Config QUEST_VARPS117 = varpbit(426, true).defaultValue(1);
    public static final Config QUEST_VARPS118 = varpbit(427, true).defaultValue(1);
    public static final Config QUEST_VARPS119 = varpbit(428, true).defaultValue(1);
    public static final Config QUEST_VARPS120 = varp(429, true).defaultValue(1);
    public static final Config QUEST_VARPS121 = varpbit(430, true).defaultValue(1);
    public static final Config QUEST_VARPS122 = varpbit(431, true).defaultValue(1);
    public static final Config QUEST_VARPS123 = varpbit(432, true).defaultValue(1);
    public static final Config QUEST_VARPS124 = varpbit(433, true).defaultValue(1);
    public static final Config QUEST_VARPS125 = varp(434, true).defaultValue(1);
    public static final Config QUEST_VARPS126 = varp(435, true).defaultValue(1);
    public static final Config QUEST_VARPS127 = varp(436, true).defaultValue(1);
    public static final Config QUEST_VARPS128 = varpbit(437, true).defaultValue(1);
    public static final Config QUEST_VARPS129 = varp(438, true).defaultValue(1);
    public static final Config QUEST_VARPS130 = varp(439, true).defaultValue(1);
    public static final Config QUEST_VARPS131 = varp(440, true).defaultValue(1);
    public static final Config QUEST_VARPS132 = varp(441, true).defaultValue(1);
    public static final Config QUEST_VARPS133 = varp(442, true).defaultValue(1);
    public static final Config QUEST_VARPS134 = varpbit(443, true).defaultValue(1);
    public static final Config QUEST_VARPS135 = varpbit(444, true).defaultValue(1);
    public static final Config QUEST_VARPS136 = varp(445, true).defaultValue(1);
    public static final Config QUEST_VARPS137 = varp(446, true).defaultValue(1);
    public static final Config QUEST_VARPS138 = varpbit(447, true).defaultValue(1);
    public static final Config X_MARKS_THE_SPOT = varpbit(550, true).defaultValue(1);
*/


    /**
     * Default options
     */

    public static final Config BRIGHTNESS = varp(166, true).defaultValue(90);

    public static final Config MUSIC_VOLUME = varp(168, true);

    public static final Config SOUND_EFFECT_VOLUME = varp(169, true);

    public static final Config AREA_SOUND_EFFECT_VOLUME = varp(872, true);

    public static final Config CHAT_EFFECTS = varp(171, true);

    public static final Config SPLIT_PRIVATE_CHAT = varp(287, true).defaultValue(1);

    public static final Config HIDE_PRIVATE_CHAT = varpbit(4089, true);

    public static final Config PROFANITY_FILTER = varp(1074, true);

    public static final Config FRIEND_NOTIFICATION_TIMEOUT = varpbit(1627, true);

    public static final Config MOUSE_BUTTONS = varp(170, true);

    public static final Config MOUSE_CAMERA = varpbit(4134, true);

    public static final Config SHIFT_DROP = varpbit(5542, true);

    public static final Config PLAYER_ATTACK_OPTION = varp(1107, true).forceSend();

    public static final Config NPC_ATTACK_OPTION = varp(1306, true).forceSend();

    public static final Config ACCEPT_AID = varpbit(4180, true);

    public static final Config RUNNING = varp(173, true).defaultValue(1);

    public static final Config DISPLAY_NAME = varp(1055, true);
    public static final Config HAS_DISPLAY_NAME = varpbit(8199, true).defaultValue(1).forceSend();

    /**
     * Advanced options
     */

    public static final Config TELEPORTING_MINIMAP_ORB = varpbit(13037, true);

    public static final Config TRANSPARENT_SIDE_PANEL = varpbit(4609, true);

    public static final Config REMAINING_XP_TOOLTIP = varpbit(4181, true);

    public static final Config PRAYER_TOOLTIPS = varpbit(5711, true);

    public static final Config SPECIAL_ATTACK_TOOLTIPS = varpbit(5712, true);

    public static final Config DATA_ORBS = varpbit(4084, true);

    public static final Config TRANSPARENT_CHATBOX = varpbit(4608, true);

    public static final Config CLICK_THROUGH_CHATBOX = varpbit(2570, true);

    public static final Config SIDE_PANELS = varpbit(4607, true);

    public static final Config HOTKEY_CLOSING_PANELS = varpbit(4611, true);

    public static final Config SIDEBAR_INDICATOR = varpbit(3756, false);

    public static final Config CHATBOX_SCROLLBAR = varpbit(6374, true);
    public static final Config ZOOMING_DISABLED = varpbit(6357, true);

    /**
     * House options
     */

    public static final Config BUILDING_MODE = varpbit(2176, true);

    public static final Config RENDER_DOORS_MODE = varpbit(6269, true);

    public static final Config TELEPORT_INSIDE = varpbit(4744, true);

    /**
     * House viewer
     */

    public static final Config HOUSE_VIEWER_SELECTED = varpbit(5329, false);

    public static final Config HOUSE_VIEWER_HIGHLIGHTED = varpbit(5330, false);

    public static final Config HOUSE_VIEWER_ROOM_ROTATION = varpbit(5331, false);

    public static final Config HOUSE_VIEWER_ALLOW_ROTATION = varpbit(5332, false);

    public static final Config HOUSE_VIEWER_ROOM_ID = varpbit(5333, false);

    /**
     * Other construction related things
     */

    public static final Config LECTERN_EAGLE = varp(261, false).defaultValue(0);
    public static final Config LECTERN_DEMON = varp(262, false).defaultValue(0);
    public static final Config PETS_ROAMING_DISABLED = varpbit(2013, true);
    public static final Config HIRED_SERVANT = varpbit(2190, false);

    /**
     * Notification options
     */

    public static final Config LOOT_DROP_NOTIFICATION_ENABLED = varpbit(5399, true);

    public static final Config LOOT_DROP_NOTIFICATION_VALUE = varpbit(5400, true);

    public static final Config UNTRADEABLE_LOOT_NOTIFICATIONS = varpbit(5402, true);

    public static final Config BOSS_KC_UPDATE = varpbit(4930, true);

    public static final Config DROP_ITEM_WARNING_ENABLED = varpbit(5411, true);

    public static final Config DROP_ITEM_WARNING_VALUE = varpbit(5412, true).defaultValue(30000);

    public static final Config FOLLOWER_PRIORITY = varpbit(5599, true);

    /**
     * Wilderness
     */

    public static final Config SINGLES_PLUS = varpbit(5961, true);

    public static final Config FEROX_ENCLAVE = varpbit(10530, true);

    /**
     * Music player
     */

    public static final Config[] MUSIC_UNLOCKS = {
            varp(20, true),
            varp(21, true),
            varp(22, true),
            varp(23, true),
            varp(24, true),
            varp(25, true),
            varp(298, true),
            varp(311, true),
            varp(346, true),
            varp(414, true),
            varp(464, true),
            varp(598, true),
            varp(662, true),
            varp(721, true),
            varp(906, true),
            varp(1009, true),
            varp(1338, true),
    };

    public static final Config MUSIC_PREFERENCE = varp(18, true);

    public static final Config MUSIC_LOOP = varpbit(4137, true);

    /**
     * Display name options
     */

    //varpbit 5605 enables look up

    /**
     * World switcher
     */

    public static final Config WORLD_SWITCHER_SETTINGS = varpbit(4596, true);
    public static final Config WORLD_SWITCHER_FAVOURITE_ONE = varpbit(4597, true);
    public static final Config WORLD_SWITCHER_FAVOURITE_TWO = varpbit(4598, true);

    /**
     * Keybind options
     */

    public static final Config[] KEYBINDS = {
            varpbit(4675, true).defaultValue(1),     //Combat
            varpbit(4680, true).defaultValue(5),     //Prayer
            varpbit(4686, true).defaultValue(10),    //Options

            varpbit(4676, true).defaultValue(2),     //Stats
            varpbit(4682, true).defaultValue(6),     //Magic
            varpbit(4687, true).defaultValue(11),    //Emotes

            varpbit(4677, true).defaultValue(3),     //Quests
            varpbit(4684, true).defaultValue(8),     //Friends
            varpbit(4683, true).defaultValue(7),     //Clan

            varpbit(4678, true).defaultValue(13),    //Inventory
            varpbit(4685, true).defaultValue(9),     //Ignores
            varpbit(4688, true).defaultValue(12),    //Music

            varpbit(4679, true).defaultValue(4),     //Equipment
            varpbit(4689, true).defaultValue(0),     //Logout

    };

    public static final Config ESCAPE_CLOSES = varpbit(4681, true);

    public static final Config SettingSearch = varpbit(9638, false);
    public static final Config SettingSearch1 = varpbit(9639, false);

    /**
     * Combat Options
     */

    public static final Config ATTACK_SET = varp(43, true);

    public static final Config AUTO_RETALIATE = varp(172, true);

    public static final Config WEAPON_TYPE = varpbit(357, false);

    public static final Config SPECIAL_ENERGY = varp(300, true).defaultValue(1000);

    public static final Config SPECIAL_ACTIVE = varp(301, false);

    public static final Config SPECIAL_ORB_STATE = varpbit(8121, false).defaultValue(2);

    public static final Config TARGET_OVERLAY_CUR = varpbit(5653, false);

    public static final Config TARGET_OVERLAY_MAX = varpbit(5654, false);

    /**
     * Skill guide
     */

    public static final Config SKILL_GUIDE_STAT = varpbit(4371, false);

    public static final Config SKILL_GUIDE_CAT = varpbit(4372, false);

    /**
     * Bank
     */

    public static final Config GIM_WITHDRAW_AS = varpbit(3958, false);

    public static final Config BANK_TAB = varpbit(4150, false);

    public static final Config BANK_LAST_X = varpbit(3960, true);

    public static final Config BANK_INSERT_MODE = varpbit(3959, true);//GIM share this

    public static final Config BANK_ALWAYS_PLACEHOLDERS = varpbit(3755, true);

    public static final Config BANK_TAB_DISPLAY = varpbit(4170, true);

    public static final Config BANK_INCINERATOR = varpbit(5102, true);

    public static final Config BANK_DEPOSIT_EQUIPMENT = varpbit(5364, true);

    public static final Config[] BANK_TAB_SIZES = {
            varpbit(4171, true), varpbit(4172, true), varpbit(4173, true),
            varpbit(4174, true), varpbit(4175, true), varpbit(4176, true),
            varpbit(4177, true), varpbit(4178, true), varpbit(4179, true),
    };

    public static final Config BANK_DEFAULT_QUANTITY = varpbit(6590, true);//GIM share this

    /**
     * Prayer
     */

    public static final Config QUICK_PRAYERS_ACTIVE = varpbit(4102, false);

    public static final Config QUICK_PRAYING = varpbit(4103, false);

    public static final Config CHIVALRY_PIETY_UNLOCK = varpbit(3909, false).defaultValue(8);

    public static final Config RIGOUR_UNLOCK = varpbit(5451, true);

    public static final Config AUGURY_UNLOCK = varpbit(5452, true);

    public static final Config PRESERVE_UNLOCK = varpbit(5453, true);

    /**
     * Runecrafting
     */
    public static final Config AIR_TIARA_UNLOCK = varpbit(607, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config MIND_TIARA_UNLOCK = varpbit(608, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config FIRE_TIARA_UNLOCK = varpbit(611, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config WATER_TIARA_UNLOCK = varpbit(609, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config BODY_TIARA_UNLOCK = varpbit(612, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config EARTH_TIARA_UNLOCK = varpbit(610, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config DEATH_TIARA_UNLOCK = varpbit(617, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config COSMIC_TIARA_UNLOCK = varpbit(613, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config WRATH_TIARA_UNLOCK = varpbit(6220, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config NATURE_TIARA_UNLOCK = varpbit(615, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config CHAOS_TIARA_UNLOCK = varpbit(616, true).defaultValue(0); //why default 0? it's already 0?

    /**
     * Rune pouch
     */

    public static final Config RUNE_POUCH_TYPES = varp(1139, false);
    public static final Config RUNE_POUCH_AMOUNTS = varp(1140, false);
    public static final Config RUNE_POUCH_LEFT_TYPE = varpbit(29, true);
    public static final Config RUNE_POUCH_LEFT_AMOUNT = varpbit(1624, true);

    public static final Config RUNE_POUCH_MIDDLE_TYPE = varpbit(1622, true);
    public static final Config RUNE_POUCH_MIDDLE_AMOUNT = varpbit(1625, true);

    public static final Config RUNE_POUCH_RIGHT_TYPE = varpbit(1623, true);
    public static final Config RUNE_POUCH_RIGHT_AMOUNT = varpbit(1626, true);

    /**
     * Collection log
     */
    public static final Config ABYSSAL_SIRE_COMPLETED = varpbit(11963, true);
    public static final Config BARROWS_COMPLETED = varpbit(11964, true);
    public static final Config BRYOPHYTA_COMPLETED = varpbit(11965, true);
    public static final Config CALLISTO_COMPLETED = varpbit(11966, true);
    public static final Config CERBERUS_COMPLETED = varpbit(11967, true);
    public static final Config CHAOS_ELEMENTAL_COMPLETED = varpbit(11968, true);
    public static final Config CHAOS_FANATIC_COMPLETED = varpbit(11969, true);
    public static final Config COMMANDER_ZILYANA_COMPLETED = varpbit(11970, true);
    public static final Config CORPOREAL_BEAST_COMPLETED = varpbit(11971, true);
    public static final Config CRAZY_ARCHAEOLOGIST_COMPLETED = varpbit(11972, true);
    public static final Config DAGANNOTH_KINGS_COMPLETED = varpbit(11973, true);
    public static final Config GENERAL_GRAARDOR_COMPLETED = varpbit(11974, true);
    public static final Config GIANT_MOLE_COMPLETED = varpbit(11975, true);
    public static final Config GROTESQUE_GUARDIANS_COMPLETED = varpbit(11976, true);
    public static final Config KALPHITE_QUEEN_COMPLETED = varpbit(11977, true);
    public static final Config KING_BLACK_DRAGON_COMPLETED = varpbit(11978, true);
    public static final Config KRAKEN_COMPLETED = varpbit(11979, true);
    public static final Config KREE_ARRA__COMPLETED = varpbit(11980, true);
    public static final Config KRIL_TSUTSAROTH_COMPLETED = varpbit(11981, true);
    public static final Config OBOR_COMPLETED = varpbit(11982, true);
    public static final Config SCORPIA_COMPLETED = varpbit(11983, true);
    public static final Config SKOTIZO_COMPLETED = varpbit(11984, true);
    public static final Config THERMONUCLEAR_SMOKE_DEVIL_COMPLETED = varpbit(11985, true);
    public static final Config THE_INFERNO_COMPLETED = varpbit(11986, true);
    public static final Config THE_FIGHT_CAVES_COMPLETED = varpbit(11987, true);
    public static final Config VENENATIS_COMPLETED = varpbit(11988, true);
    public static final Config VETION_COMPLETED = varpbit(11989, true);
    public static final Config VORKATH_COMPLETED = varpbit(11990, true);
    public static final Config WINTERTODT_COMPLETED = varpbit(11991, true);
    public static final Config ZALCANO_COMPLETED = varpbit(11992, true);
    public static final Config ZULRAH_COMPLETED = varpbit(11993, true);
    public static final Config THEATRE_OF_BLOOD_COMPLETED = varpbit(11994, true);
    public static final Config CHAMBERS_OF_XERIC_COMPLETED = varpbit(11995, true);
    public static final Config BEGINNER_TREASURE_TRAILS_COMPLETED = varpbit(11996, true);
    public static final Config EASY_TREASURE_TRAILS_COMPLETED = varpbit(11997, true);
    public static final Config MEDIUM_TREASURE_TRAILS_COMPLETED = varpbit(11998, true);
    public static final Config HARD_TREASURE_TRAILS_COMPLETED = varpbit(11999, true);
    public static final Config ELITE_TREASURE_TRAILS_COMPLETED = varpbit(12000, true);
    public static final Config MASTER_TREASURE_TRAILS_COMPLETED = varpbit(12001, true);
    public static final Config SHARED_TREASURE_TRAILS_COMPLETED = varpbit(12002, true);
    public static final Config CASTLE_WARS_COMPLETED = varpbit(12003, true);
    public static final Config MAGIC_TRAINING_ARENA_COMPLETED = varpbit(12004, true);
    public static final Config BARBARIAN_ASSAULT_COMPLETED = varpbit(12005, true);
    public static final Config SHADES_OF_MORTON_COMPLETED = varpbit(12006, true);
    public static final Config PEST_CONTROL_COMPLETED = varpbit(12007, true);
    public static final Config TEMPLE_TREKKING_COMPLETED = varpbit(12008, true);
    public static final Config FISHING_TRAWLER_COMPLETED = varpbit(12009, true);
    public static final Config GNOME_RESTAURANT_COMPLETED = varpbit(12010, true);
    public static final Config HALLOWED_SEPULCHRE_COMPLETED = varpbit(12011, true);
    public static final Config ROGUES_DEN_COMPLETED = varpbit(12012, true);
    public static final Config TROUBLE_BREWING_COMPLETED = varpbit(12013, true);
    public static final Config TITHE_FARM_COMPLETED = varpbit(12014, true);
    public static final Config MAHOGANY_HOMES_COMPLETED = varpbit(12015, true);
    public static final Config REVENANTS_COMPLETED = varpbit(12016, true);
    public static final Config GLOUGHS_EXPERIMENTS_COMPLETED = varpbit(12017, true);
    public static final Config SLAYER_COMPLETED = varpbit(12018, true);
    public static final Config CHAMPIONS_CHALLENGE_COMPLETED = varpbit(12019, true);
    public static final Config SKILLING_PETS_COMPLETED = varpbit(12020, true);
    public static final Config MOTHERLODE_MINE_COMPLETED = varpbit(12021, true);
    public static final Config SHAYZIEN_ARMOUR_COMPLETED = varpbit(12022, true);
    public static final Config CYCLOPES_COMPLETED = varpbit(12023, true);
    public static final Config CHAOS_DRUIDS_COMPLETED = varpbit(12024, true);
    public static final Config MISCELLANEOUS_COMPLETED = varpbit(12025, true);
    public static final Config ALL_PETS_COMPLETED = varpbit(12026, true);
    public static final Config TZHAAR_COMPLETED = varpbit(12027, true);
    public static final Config CHOMPY_BIRD_HUNTING_COMPLETED = varpbit(12028, true);
    public static final Config RANDOM_EVENTS_COMPLETED = varpbit(12029, true);
    public static final Config ALCHEMICAL_HYDRA_COMPLETED = varpbit(12030, true);
    public static final Config SARACHNIS_COMPLETED = varpbit(12031, true);
    public static final Config AERIAL_FISHING_COMPLETED = varpbit(12032, true);
    public static final Config HESPORI_COMPLETED = varpbit(12033, true);
    public static final Config CREATURE_CREATION_COMPLETED = varpbit(12034, true);
    public static final Config ROOFTOP_AGILITY_COMPLETED = varpbit(12035, true);
    public static final Config FOSSIL_ISLAND_NOTES_COMPLETED = varpbit(12036, true);
    public static final Config MY_NOTES_COMPLETED = varpbit(12037, true);
    public static final Config THE_GAUNTLET_COMPLETED = varpbit(12038, true);
    public static final Config THE_NIGHTMARE_COMPLETED = varpbit(12039, true);
    public static final Config MONKEY_BACKPACKS_COMPLETED = varpbit(12040, true);
    public static final Config LAST_MAN_STANDING_COMPLETED = varpbit(12041, true);
    public static final Config VOLCANIC_MINE_COMPLETED = varpbit(12042, true);
    public static final Config BRIMHAVEN_AGILITY_ARENA_COMPLETED = varpbit(12043, true);
    public static final Config HARD_TREASURE_TRAILS_RARE_COMPLETED = varpbit(12044, true);
    public static final Config ELITE_TREASURE_TRAILS_RARE_COMPLETED = varpbit(12045, true);
    public static final Config MASTER_TREASURE_TRAILS_RARE_COMPLETED = varpbit(12046, true);
    public static final Config SOUL_WARS_COMPLETED = varpbit(12047, true);
    public static final Config SHOOTING_STARS_COMPLETED = varpbit(12048, true);
    public static final Config TEMPOROSS_COMPLETED = varpbit(12049, true);


    public static final Config COLLECTION_LOG_COMPLETED_ENTRIES = varp(2948, true);


    /**
     * Misc
     */

    public static final Config COLLECTION_PROGRESS = varp(2943, false);
    public static final Config COLLECTION_COUNT = varp(2944, false);
    public static final Config COLLECTION_LOG_KC = varp(2048, false);

    public static final Config COLLECTION_LOG_KC_TAB = varp(2048, false);

    public static final Config GAME_FILTER = varpbit(26, true);

    public static final Config MAGIC_BOOK = varpbit(4070, true);

    public static final Config BLIGHTED_SACKS = varpbit(5963, true);

    public static final Config AUTOCAST = varpbit(276, true);

    public static final Config DEFENSIVE_CAST = varpbit(2668, true);

    public static final Config AUTOCAST_SET = varp(664, false);

    public static final Config SMITHING_TYPE = varpbit(3216, false);

    public static final Config ABYSS_MAP = varpbit(625, false);

    public static final Config PLAYER_PRIORITY = varp(1075, false);

    public static final Config STAMINA_POTION = varpbit(25, true);

    public static final Config LOCK_CAMERA = varpbit(4606, false);

    public static final Config MULTI_ZONE = varpbit(4605, false);

    public static final Config MY_TRADE_MODIFIED = varpbit(4374, false);

    public static final Config OTHER_TRADE_MODIFIED = varpbit(4375, false);

    public static final Config HAM_HIDEOUT_ENTRANCE = varpbit(235, false);

    public static final Config VENG_COOLDOWN = varpbit(2451, false);

    public static final Config INFERNO_ENTRANCE = varpbit(5646, true).defaultValue(2); //uhh todo

    public static final Config INFERNO_PILLAR_DAMAGE_WEST = varpbit(5655, false);

    public static final Config INFERNO_PILLAR_DAMAGE_SOUTH = varpbit(5656, false);

    public static final Config INFERNO_PILLAR_DAMAGE_EAST = varpbit(5657, false);

    public static final Config PVP_KILLS = varp(1103, true);

    public static final Config PVP_DEATHS = varp(1102, true);

    public static final Config PVP_KD_OVERLAY = varpbit(4143, true).defaultValue(1);

    public static final Config IN_PVP_AREA = varpbit(8121, true).defaultValue(1);

    public static final Config WILDERNESS_TIMER = varpbit(877, false);

    public static final Config ARDOUGNE_LEVER_UNLOCK = varpbit(4470, false).defaultValue(1);

    public static final Config LARRANS_CHEST = varpbit(6583, false).defaultValue(0);

    /**
     * Deadman Sigils
     */
    public static final Config SIGIL_SLOT_ONE = varpbit(13019, true);
    public static final Config SIGIL_SLOT_TWO = varpbit(13020, true);
    public static final Config SIGIL_SLOT_THREE = varpbit(13021, true);


    /**
     * Blast furnace
     */
    public static final Config BARS_IN_DESPENSER = varpbit(936, false);

    /**
     * Slayer
     */
    public static final Config SLAYER_UNLOCKED_HELM = varpbit(3202, true);
    public static final Config RING_BLING = varpbit(3207, true);
    public static final Config BROADER_FLETCHING = varpbit(3208, true);
    public static final Config KING_BLACK_BONNET = varpbit(5080, true);
    public static final Config KALPHITE_KHAT = varpbit(5081, true);
    public static final Config UNHOLY_HELMET = varpbit(5082, true);
    public static final Config BIGGER_AND_BADDER = varpbit(5358, true);
    public static final Config SEEING_RED = varpbit(2462, true);
    public static final Config UNLOCK_BLOCK_TASK_SIX = varpbit(4538, true).defaultValue(1).forceSend();
    public static final Config UNLOCK_DULY_NOTED = varpbit(4589, true);
    public static final Config GARGOYLE_SMASHER = varpbit(4027, true);
    public static final Config SLUG_SALTER = varpbit(4028, true);
    public static final Config REPTILE_FREEZER = varpbit(4029, true);
    public static final Config SHROOM_SPRAYER = varpbit(4030, true);
    public static final Config NEED_MORE_DARKNESS = varpbit(4031, true);
    public static final Config ANKOU_VERY_MUCH = varpbit(4085, true);
    public static final Config SUQ_ANOTHER_ONE = varpbit(4086, true);
    public static final Config FIRE_AND_DARKNESS = varpbit(4087, true);
    public static final Config PEDAL_TO_THE_METALS = varpbit(4088, true);
    public static final Config AUGMENT_MY_ABBIES = varpbit(4090, true);
    public static final Config ITS_DARK_IN_HERE = varpbit(4091, true);
    public static final Config GREATER_CHALLENGE = varpbit(4092, true);
    public static final Config I_HOPE_YOU_MITH_ME = varpbit(4094, true);
    public static final Config WATCH_THE_BIRDIE = varpbit(4095, true);
    public static final Config HOT_STUFF = varpbit(4691, true);
    public static final Config LIKE_A_BOSS = varpbit(4724, true);
    public static final Config BLEED_ME_DRY = varpbit(4746, true);
    public static final Config SMELL_YA_LATER = varpbit(4747, true);
    public static final Config BIRDS_OF_A_FEATHER = varpbit(4748, true);
    public static final Config I_REALLY_MITH_YOU = varpbit(4749, true);
    public static final Config HORRORIFIC = varpbit(4750, true);
    public static final Config TO_DUST_YOU_SHALL_RETURN = varpbit(4751, true);
    public static final Config WYVER_NOTHER_ONE = varpbit(4752, true);
    public static final Config GET_SMASHED = varpbit(4753, true);
    public static final Config NECHS_PLEASE = varpbit(4754, true);
    public static final Config KRACK_ON = varpbit(4755, true);
    public static final Config SPIRITUAL_FERVOUR = varpbit(4757, true);
    public static final Config REPTILE_GOT_RIPPED = varpbit(4996, true);
    public static final Config GET_SCABARIGHT_ON_IT = varpbit(5359, true);
    public static final Config WYVER_NOTHER_TWO = varpbit(5733, true);
    public static final Config DARK_MANTLE = varpbit(5631, true);
    public static final Config UNDEAD_HEAD = varpbit(6096, true);
    public static final Config USE_MORE_HEAD = varpbit(6570, true);
    public static final Config STOP_THE_WYVERN = varpbit(240, true);
    public static final Config DOUBLE_TROUBLE = varpbit(6485, true);
    public static final Config ADA_MIND_SOME_MORE = varpbit(6094, true);
    public static final Config RUUUUUNE = varpbit(6095, true);
    public static final Config TWISTED_VISION = varpbit(10104, true);
    public static final Config ACTUAL_VAMPYRE_SLAYER = varpbit(10388, true);
    public static final Config TASK_STORAGE = varpbit(12442, true);
    public static final Config MORE_AT_STAKE = varpbit(10389, true);
    public static final Config BASILOCKED = varpbit(9456, true);
    public static final Config BASILONGER = varpbit(9455, true);


    public static final Config SLAYER_TASK_AMOUNT = varp(261, false);
    public static final Config SLAYER_TASK_1 = varp(262, false);
    public static final Config SLAYER_TASK_2 = varp(263, false);
    public static final Config SLAYER_POINTS = varpbit(4068, true);
    public static final Config TRIVIA_POINTS = varpbit(4069, true);

    public static final Config[] BLOCKED_TASKS = {
            varpbit(3209, false),
            varpbit(3210, false),
            varpbit(3211, false),
            varpbit(3212, false),
            varpbit(4441, false),
            varpbit(5023, false),
    };

    /**
     * Quest points
     */
    public static final Config QUEST_POINTS = varp(101, true).defaultValue(252).forceSend();

    /**
     * Farming
     */
    public static final Config FARMING_PATCH_1 = varpbit(4771, true); // guild spirit
    public static final Config FARMING_PATCH_2 = varpbit(4772, true); // guild bush
    public static final Config FARMING_PATCH_3 = varpbit(4773, true); // guild north
    public static final Config FARMING_PATCH_4 = varpbit(4774, true); // guild south
    public static final Config FARMING_COMPOST_BIN = varpbit(4775, true); // herb patch guild
    public static final Config Guild_Flower_Patch = varpbit(7906, true);
    public static final Config Guild_TREE_Patch = varpbit(7905, true);
    public static final Config GUILD_ANIMA_PATCH = varpbit(7911, true);
    public static final Config GUILD_CELASTRUS_PATCH = varpbit(7910, true);
    public static final Config GUILD_FRUIT_PATCH = varpbit(7909, true);
    public static final Config GUILD_REDWOOD_PATCH = varpbit(7907, true);
    public static final Config GUILD_HESPORI_PATCH = varpbit(7908, true);
    public static final Config GUILD_CACTUS_PATCH = varpbit(7904, true);

    public static final Config STORAGE_RAKE = varpbit(1435, true);
    public static final Config STORAGE_SEED_DIBBER = varpbit(1436, true);
    public static final Config STORAGE_SPADE = varpbit(1437, true);
    public static final Config STORAGE_SECATEURS = varpbit(1438, true);
    public static final Config STORAGE_SECATEURS_TYPE = varpbit(1848, true); // 0 for normal, 1 for magic
    public static final Config STORAGE_WATERING_CAN = varpbit(1439, true);
    public static final Config STORAGE_TROWEL = varpbit(1440, true);
    public static final Config STORAGE_EMPTY_BUCKET_1 = varpbit(1441, true); // 5 bits
    public static final Config STORAGE_EMPTY_BUCKET_2 = varpbit(4731, true); // 3 bits
    public static final Config STORAGE_COMPOST_1 = varpbit(1442, true); // 8 bits
    public static final Config STORAGE_COMPOST_2 = varpbit(6266, true); // 2 bits
    public static final Config STORAGE_SUPERCOMPOST_1 = varpbit(1443, true); // 8 bits
    public static final Config STORAGE_SUPERCOMPOST_2 = varpbit(6267, true); // 2 bits
    public static final Config STORAGE_PLANT_CURE = varpbit(6268, true);
    public static final Config STORAGE_ULTRACOMPOST = varpbit(5732, true);
    public static final Config STORAGE_BOTTOMLESS_COMPOST = varpbit(7915, true);


    /**
     * Motherlode mine
     */
    public static final Config PAY_DIRT_IN_SACK = varpbit(5558, true);

    /**
     * Barrows
     */

    public static final Config AHRIM_KILLED = varpbit(457, true);
    public static final Config DHAROK_KILLED = varpbit(458, true);
    public static final Config GUTHAN_KILLED = varpbit(459, true);
    public static final Config KARIL_KILLED = varpbit(460, true);
    public static final Config TORAG_KILLED = varpbit(461, true);
    public static final Config VERAC_KILLED = varpbit(462, true);
    public static final Config BARROWS_CHEST = varpbit(1394, false);

    /**
     * Fairy ring
     */
    public static final Config FAIRY_RING_LEFT = varpbit(3985, true);
    public static final Config FAIRY_RING_MIDDLE = varpbit(3986, true);
    public static final Config FAIRY_RING_RIGHT = varpbit(3987, true);
    public static final Config FAIRY_RING_LAST_DESTINATION = varpbit(5374, true);

    /**
     * Canoe station
     */
    public static final Config LUMBRIDGE_CANOE = varpbit(1839, true);
    public static final Config CHAMPION_GUILD_CANOE = varpbit(1840, true);
    public static final Config BARBARIAN_VILLAGE_CANOE = varpbit(1841, true);
    public static final Config EDGEVILLE_CANOE = varpbit(1842, true);
    public static final Config WILDERNESS_CHINS_CANOE = varpbit(1843, true);

    /**
     * Godwars
     */
    public static final Config GWD_SARADOMIN_KC = varpbit(3972, true);
    public static final Config GWD_ARMADYL_KC = varpbit(3973, true);
    public static final Config GWD_BANDOS_KC = varpbit(3975, true);
    public static final Config GWD_ZAMORAK_KC = varpbit(3976, true);
    //    public static final Config GWD_ZAROS_KC = varpbit(13080, true);
    public static final Config SARADOMINS_LIGHT = varpbit(4733, true);
    public static final Config GODWARS_DUNGEON = varpbit(3966, true);
    public static final Config GODWARS_SARADOMIN_FIRST_ROPE = varpbit(3967, true);
    public static final Config GODWARS_SARADOMIN_SECOND_ROPE = varpbit(3968, true);

    /**
     * Poison
     */
    public static final Config POISONED = varp(102, true);

    /**
     * Disease
     */
    public static final Config DISEASED = varp(456, true);

    /**
     * Exp Counter
     */
    public static final Config XP_COUNTER_SHOWN = varpbit(4702, true);
    public static final Config XP_COUNTER_POSITION = varpbit(4692, true);
    public static final Config XP_COUNTER_SIZE = varpbit(4693, true);
    public static final Config XP_COUNTER_DURATION = varpbit(4694, true);
    public static final Config XP_COUNTER_COLOUR = varpbit(4695, true);
    public static final Config XP_COUNTER_GROUP = varpbit(4696, true);
    public static final Config XP_COUNTER_COUNTER = varpbit(4697, true);
    public static final Config XP_COUNTER_PROGRESS_BAR = varpbit(4698, true);
    public static final Config XP_COUNTER_SPEED = varpbit(4722, true);
    public static final Config XP_FAKE_DROPS = varpbit(8120, true);

    /**
     * Kalphite lair
     */
    public static final Config KALPHITE_LAIR_ROPE_INTERIOR = varpbit(4587, true);
    public static final Config KALPHITE_LAIR_ROPE_EXTERIOR = varpbit(4586, true);

    /**
     * Bounty hunter
     */
    public static final Config BOUNTY_HUNTER_RISK = varpbit(1538, false);
    public static final Config BOUNTY_HUNTER_EMBLEM = varpbit(4162, false);
    public static final Config BOUNTY_HUNTER_RECORD_OVERLAY = varpbit(1621, true);
    public static final Config BOUNTY_HUNTER_ROGUE_KILLS = varp(1134, true);
    public static final Config BOUNTY_HUNTER_ROGUE_RECORD = varp(1133, true);
    public static final Config BOUNTY_HUNTER_TARGET_KILLS = varp(1136, true);
    public static final Config BOUNTY_HUNTER_TARGET_RECORD = varp(1135, true);
    public static final Config BOUNTY_HUNTER_TELEPORT = varpbit(2010, true);

    /**
     * Chatbox interface setting
     */
    public static final Config CHATBOX_INTERFACE_USE_FULL_FRAME = varpbit(5983, false);

    /**
     * Pets
     */
    public static final Config PET_NPC_INDEX = varp(447, false);
    /*
    public static final Config INSURED_PET_DAGANNOTH_SUPREME = varpbit(4338, true);
    public static final Config INSURED_PET_DAGANNOTH_PRIME = varpbit(4339, true);
    public static final Config INSURED_PET_DAGANNOTH_REX = varpbit(4340, true);
    public static final Config INSURED_PET_PENANCE_QUEEN = varpbit(4341, true);
    public static final Config INSURED_PET_KREEARRA = varpbit(4342, true);
    public static final Config INSURED_PET_GRAARDOR = varpbit(4343, true);
    public static final Config INSURED_PET_ZILYANA = varpbit(4344, true);
    public static final Config INSURED_PET_KRIL = varpbit(4345, true);
    public static final Config INSURED_PET_MOLE = varpbit(4346, true);
    public static final Config INSURED_PET_KBD = varpbit(4347, true);
    public static final Config INSURED_PET_KQ = varpbit(4348, true);
    public static final Config INSURED_PET_SMOKE_DEVIL = varpbit(4349, true);
    public static final Config INSURED_PET_KRAKEN = varpbit(4350, true);
    public static final Config INSURED_PET_CHOMPY = varpbit(4445, true);
    public static final Config INSURED_PET_CALLISTO = varpbit(4568, true);
    public static final Config INSURED_PET_VENENATIS = varpbit(4429, true);
    public static final Config INSURED_PET_VETION_PURPLE = varpbit(4569, true);
    public static final Config INSURED_PET_SCORPIA = varpbit(4570, true);
    public static final Config INSURED_PET_JAD = varpbit(4699, true);
    public static final Config INSURED_PET_CERBERUS = varpbit(4726, true);
    public static final Config INSURED_PET_HERON = varpbit(4846, true);
    public static final Config INSURED_PET_GOLEM = varpbit(4847, true);
    public static final Config INSURED_PET_BEAVER = varpbit(4848, true);
    public static final Config INSURED_PET_CHINCHOMPA = varpbit(4849, true);
    public static final Config INSURED_PET_ABYSSAL = varpbit(204, true);
    public static final Config INSURED_PET_CORE = varpbit(997, true);
    public static final Config INSURED_PET_ZULRAH = varpbit(1526, true);
    public static final Config INSURED_PET_CHAOS_ELE = varpbit(3962, true);
    public static final Config INSURED_PET_GIANT_SQUIRREL = varpbit(2169, true);
    public static final Config INSURED_PET_TANGLEROOT = varpbit(2170, true);
    public static final Config INSURED_PET_RIFT_GUARDIAN = varpbit(2171, true);
    public static final Config INSURED_PET_ROCKY = varpbit(2172, true);
    public static final Config INSURED_PET_BLOODHOUND = varpbit(5181, true);
    public static final Config INSURED_PET_PHOENIX = varpbit(5363, true);
    public static final Config INSURED_PET_OLMLET = varpbit(5448, true);
    public static final Config INSURED_PET_SKOTOS = varpbit(5632, true);
    public static final Config INSURED_PET_JAL_NIB_REK = varpbit(5644, true);
    public static final Config INSURED_PET_MIDNIGHT = varpbit(6013, true);
    public static final Config INSURED_PET_HERBI = varpbit(5735, true);
    */

    /**
     * Corp beast damage counter
     */
    public static final Config CORPOREAL_BEAST_DAMAGE = varp(1142, false);

    /**
     * Raid party configs
     */
    public static final Config RAIDS_PREFERRED_PARTY_SIZE = varp(1432, false);
    public static final Config RAIDS_PREFERRED_COMBAT_LEVEL = varpbit(5426, false);
    public static final Config RAIDS_PREFERRED_SKILL_TOTAL = varpbit(5427, false);
    public static final Config RAIDS_PARTY = varp(1427, false).defaultValue(-1);
    public static final Config RAIDS_TAB_ICON = varpbit(5432, false).defaultValue(-1);
    public static final Config RAIDS_STAGE = varp(1430, false).defaultValue(-1);

    public static final Config RAIDS_PARTY_POINTS = varpbit(5431, false);
    public static final Config RAIDS_PERSONAL_POINTS = varpbit(5422, false);
    public static final Config RAIDS_TIMER = varpbit(6386, false);

    public static final Config RAIDS_STORAGE_WARNING_DISMISSED = varpbit(5509, false);
    public static final Config RAIDS_STORAGE_PRIVATE_INVENTORY = varpbit(3459, false);


    /**
     * Gamemode (ironman)
     */
    public static final Config IRONMAN_MODE = varpbit(1777, true);

    /**
     * Used to toggle the friends/ignore list frame icon
     */
    public static final Config FRIENDS_AND_IGNORE_TOGGLE = varpbit(6516, true);

    /**
     * Emotes
     */
    public static final Config UNLOCK_FLAP_EMOTE = varpbit(2309, true);
    public static final Config UNLOCK_SLAP_HEAD_EMOTE = varpbit(2310, true);
    public static final Config UNLOCK_IDEA_EMOTE = varpbit(2311, true);
    public static final Config UNLOCK_STAMP_EMOTE = varpbit(2312, true);
    public static final Config UNLOCK_GOBLIN_BOW_AND_SALUTE_EMOTE = varpbit(532, true);
    public static final Config EMOTES = varp(313, true);

    /**
     * Magic book
     */
    public static final Config DISABLE_SPELL_FILTERING = varpbit(6718, true);
    public static final Config SHOW_COMBAT_SPELLS = varpbit(6605, true);
    public static final Config SHOW_UTILITY_SPELLS = varpbit(6606, true);
    public static final Config SHOW_SPELLS_LACK_LEVEL = varpbit(6607, true);
    public static final Config SHOW_SPELLS_LACK_RUNES = varpbit(6608, true);
    public static final Config SHOW_TELEPORT_SPELLS = varpbit(6609, true);
    public static final Config ANCIENT_MAGIC_SPELLS = varpbit(358, true);//15
    public static final Config LUNAR_MAGIC_SPELLS = varpbit(2448, true);//190
    public static final Config ARCEEUS_MAGIC_SPELLS = varpbit(12296, true);//150
    public static final Config HARMONY_ISLAND_TELEPORT = varpbit(980, true);//150


    /**
     * Clan Wars
     */
    public static final Config CLAN_WARS_GAME_END = varpbit(4270, false);
    public static final Config CLAN_WARS_MELEE_DISABLED = varpbit(4271, false);
    public static final Config CLAN_WARS_RANGING_DISABLED = varpbit(4272, false);
    public static final Config CLAN_WARS_MAGIC_DISABLED = varpbit(4273, false);
    public static final Config CLAN_WARSS_PRAYER_DISABLED = varpbit(4274, false);
    public static final Config CLAN_WARS_FOOD_DISABLED = varpbit(4275, false);
    public static final Config CLAN_WARS_DRINKS_DISABLED = varpbit(4276, false);
    public static final Config CLAN_WARS_SPECIAL_ATTACKS_DISABLED = varpbit(4277, false);
    public static final Config CLAN_WARS_STRAGGLERS = varpbit(4278, false);
    public static final Config CLAN_WARS_IGNORE_FREEZING = varpbit(4279, false);
    public static final Config CLAN_WARS_PJ_TIMER = varpbit(4280, false);
    public static final Config CLAN_WARS_ALLOW_TRIDENT = varpbit(4281, false);
    public static final Config CLAN_WARS_SINGLE_SPELLS = varpbit(4282, false);
    public static final Config CLAN_WARS_ARENA = varpbit(4283, false);
    public static final Config CLAN_WARS_ACCEPT = varpbit(4285, false);
    public static final Config CLAN_WARS_COUNTDOWN_TIMER = varpbit(4286, false);
    public static final Config CLAN_WARS_TEAM_1_COUNT = varpbit(4287, false);
    public static final Config CLAN_WARS_TEAM_2_COUNT = varpbit(4288, false);
    public static final Config CLAN_WARS_ACTIVE_TEAM = varpbit(4289, false);

    /**
     * Catacombs entrances
     */
    public static final Config CATACOMBS_ENTRANCE_NW = varpbit(5090, true);
    public static final Config CATACOMBS_ENTRANCE_SW = varpbit(5088, true);
    public static final Config CATACOMBS_ENTRANCE_SE = varpbit(5087, true);
    public static final Config CATACOMBS_ENTRANCE_NE = varpbit(5089, true);

    /**
     * Death storage
     */
    public static final Config DEATH_STORAGE_TYPE = varp(261, false);
    public static final Config SACRIFICE_ITEM_PRICE = varp(264, true);
    /**
     * Theatre of Blood 1=In Party, 2=Inside/Spectator, 3=Dead Spectating
     */
    public static final Config THEATRE_OF_BLOOD = varpbit(6440, false);
    public static final Config TOBHP = varpbit(6447, false);
    public static final Config TOBHP1 = varpbit(6448, false);
    public static final Config TOBHP2 = varpbit(6449, false);
    /**
     * Theatre of Blood party hud, 0 = List, 1 = Orbs
     */
    public static final Config THEATRE_HUD_STATE = varpbit(6441, false);
    public static final Config BLOAT_DOOR = varpbit(6447, false);
    public static final Config TOB_PARTY_LEADER = varp(1740, false).defaultValue(-1);

    /**
     * Theatre of Blood orb varbits each number stands for the player's health on a scale of 1-27 (I think), 0 hides the orb
     */
    public static final Config THEATRE_OF_BLOOD_ORB_1 = varpbit(6442, false);
    public static final Config THEATRE_OF_BLOOD_ORB_2 = varpbit(6443, false);
    public static final Config THEATRE_OF_BLOOD_ORB_3 = varpbit(6444, false);
    public static final Config THEATRE_OF_BLOOD_ORB_4 = varpbit(6445, false);
    public static final Config THEATRE_OF_BLOOD_ORB_5 = varpbit(6446, false);

    /**
     * Hp bar for zalcano/nightmare
     */

    public static final Config COMBAT_CURRENT_HP = varpbit(6099, true);
    public static final Config HPBARSETNAME = varp(1683, true);
    public static final Config COMBAT_MAX_HP = varpbit(6100, true);
    public static final Config IMPREGNANTED = varpbit(10151, false);

    /**
     * Nightmare Zone
     */
    public static final Config NMZ_COFFER_STATUS = varpbit(3957, false);

    public static final Config NMZ_COFFER_AMT = varpbit(3948, false);

    public static final Config NMZ_ABSORPTION = varpbit(3956, false);

    public static final Config NMZ_POINTS = varpbit(3949, false);

    public static final Config NMZ_REWARD_POINTS_TOTAL = varp(1060, true);

    public static final Config BARRONITE_SHARDS_TOTAL = varp(2953, true);

    public static final Config RAMARNO_TOGGLE_3 = varpbit(12099, true);
    public static final Config RAMARNO_TOGGLE_4 = varpbit(12100, true);
    public static final Config RAMARNO_TOGGLE_6 = varpbit(12101, true);
    public static final Config RAMARNO_TOGGLE_8 = varpbit(12102, true);
    public static final Config RAMARNO_TOGGLE_10 = varpbit(12103, true);
    public static final Config RAMARNO_TOGGLE_12 = varpbit(12104, true);

    public static final Config RAMARNO_SHOP_TIMER_12 = varpbit(12106, true);

    public static final Config TEMPOROSS_REWARDS = varpbit(11936, false);
    public static final Config TEMPOROSS_POINTS = varpbit(11897, false);

    public static final Config GENDER = varpbit(11697, true);
    public static final Config GENDER_VAR = varp(261, true);

    /**
     * Separator
     */

    public int id;

    private Varpbit bit;

    private boolean save;

    private int defaultValue;

    private boolean forceSend;

    public Config defaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Config forceSend() {
        this.forceSend = true;
        return this;
    }

    public void update(Player player) {
        player.updateVarp(bit == null ? id : bit.varpId);
    }

    public int toggle(Player player) {
        if (get(player) == 0) {
            set(player, 1);
            return 1;
        } else {
            set(player, 0);
            return 0;
        }
    }

    public int increment(Player player, int amount) {
        int newValue = get(player) + amount;
        set(player, newValue);
        return newValue;
    }

    public int decrement(Player player, int amount) {
        int newValue = get(player) - amount;
        set(player, newValue);
        return newValue;
    }

    public void reset(Player player) {
        set(player, defaultValue);
    }

    public void set(Player player, int value) {
        if (bit != null) {
            int varpId = bit.varpId;
            int least = bit.leastSigBit;
            int most = bit.mostSigBit;
            int shift = SHIFTS[most - least];
            if (value < 0 || value > shift)
                value = 0;
            int varpValue = player.varps[varpId];
            shift <<= least;
            player.varps[varpId] = ((varpValue & (~shift)) | value << least & shift);
            player.updateVarp(varpId);
        } else {
            player.varps[id] = value;
            player.updateVarp(id);
        }
    }

    public int get(Player player) {
        if (bit != null) {
            int varpId = bit.varpId;
            int least = bit.leastSigBit;
            int most = bit.mostSigBit;
            int shift = SHIFTS[most - least];
            return player.varps[varpId] >> least & shift;
        }
        return player.varps[id];
    }

    /**
     * Creation
     */

    public static Config varp(int id, boolean save) {
        return create(id, null, save, true);
    }

    public static Config varpbit(int id, boolean save) {
        return create(id, Varpbit.get(id), save, true);
    }

    public static Config create(int id, Varpbit bit, boolean save, boolean store) {
        Config config = new Config();
        config.id = id;
        config.bit = bit;
        config.save = save;
        if (store) {
            CONFIGS.add(config);
            CONFIGS.trimToSize();
        }
        return config;
    }

    /**
     * Loading
     */

    public static void save(Player player) {
        for (Config config : CONFIGS) {
            if (!config.save)
                continue;
            int saveId = config.id << 16 | (config.bit != null ? 1 : 0);
            player.savedConfigs.put(saveId, config.get(player));
        }
    }

    public static void load(Player player) {
        for (Config config : CONFIGS) {
            if (!config.save) {
                if (config.defaultValue != 0)
                    config.set(player, config.defaultValue);
                else if (config.forceSend)
                    config.update(player);
                continue;
            }
            int saveId = config.id << 16 | (config.bit != null ? 1 : 0);
            Integer savedValue = player.savedConfigs.get(saveId);
            if (savedValue == null) {
                if (config.defaultValue != 0)
                    config.set(player, config.defaultValue);
                else if (config.forceSend)
                    config.update(player);
                continue;
            }
            if (savedValue == 0) {
                if (config.defaultValue == 0 && config.forceSend)
                    config.update(player);
                continue;
            }
            config.set(player, savedValue);
        }
        player.savedConfigs.clear();
    }

}