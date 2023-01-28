package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.content.activities.lms.LastManStandingQueue;
import io.ruin.content.activities.lms.LastManStandingSession;
import io.ruin.content.activities.tournament.Tournament;
import io.ruin.content.activities.tournament.TournamentFightPair;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.activities.barrows.BarrowsBrother;
import io.ruin.model.activities.cluescrolls.ClueSave;
import io.ruin.model.activities.fightcaves.FightCaves;
import io.ruin.model.activities.gambling.FlowerPoker;
import io.ruin.model.activities.inferno.Inferno;
import io.ruin.model.activities.minigames.JadChallenge;
import io.ruin.model.activities.pestcontrol.PestControlGame;
import io.ruin.model.activities.poll.PlayerBallot;
import io.ruin.model.activities.poll.PollBooth;
import io.ruin.model.activities.pvminstances.PVMInstance;
import io.ruin.model.activities.pvp.unrated.BattleGrounds;
import io.ruin.model.activities.raids.party.Party;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.activities.tempoross.Tempoross;
import io.ruin.model.activities.wilderness.WildernessObelisk;
import io.ruin.model.combat.WildernessRating;
import io.ruin.model.content.DrakoUpgrades.UpgradeManager;
import io.ruin.model.contracts.agility.AgilityContractType;
import io.ruin.model.contracts.farming.FarmingContractTier;
import io.ruin.model.contracts.farming.FarmingContractType;
import io.ruin.model.contracts.fishing.FishingContractType;
import io.ruin.model.contracts.mining.MiningContractType;
import io.ruin.model.contracts.smithing.SmithingContractType;
import io.ruin.model.contracts.woodcutting.WoodcuttingContractType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.inter.battlepass.Level;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.journal.bestiary.DropTableEntry;
import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.journal.toggles.EdgevilleBlacklist;
import io.ruin.model.inter.teleports.TeleportCategory;
import io.ruin.model.inter.teleports.TeleportDestination;
import io.ruin.model.inter.teleports.TeleportList;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.pets.Pets;
import io.ruin.model.item.actions.impl.pets.PetData;
import io.ruin.model.item.actions.impl.storage.EssencePouch;
import io.ruin.model.item.containers.GambleInterface;
import io.ruin.model.item.containers.collectionlog.CollectionLog;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.impl.CanoeStation;
import io.ruin.model.map.object.actions.impl.FairyRing;
import io.ruin.model.skills.birdhouses.BirdHouseHandler;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.construction.seat.Seat;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.skills.magic.spells.modern.Teleother;
import io.ruin.model.skills.slayer.SlayerTask;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;
import io.ruin.utility.TimedList;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;

public abstract class PlayerAttributes extends Entity {



    @Expose @Getter public UpgradeManager upgradeManager;

    @Expose
    public boolean achievedComp = false;

    @Expose public HashMap<Integer, Integer> SuccessfulUpgrades = new HashMap<>();
    @Expose public HashMap<Integer, Integer> FailedUpgrades = new HashMap<>();

    @Expose
    public int prestigePoints = 0;

    @Expose
    public int raidPoints = 0;

    @Expose
    public boolean referral = false;
    @Expose
    public int TheatreOfBloodCompletions = 0;
    @Expose
    public int TOBChests = 0;
    @Expose
    public boolean tobcannon = false;
    /**
     * League points
     */

    @Expose
    public int totalLeaguePoints;
    @Expose
    public int twistedLeaguePoints;
    @Expose
    public int trailblazerLeaguePoints;
    @Expose
    public int shatteredrelicsLeaguePoints;

    @Expose
    public static boolean lootit = false;

    @Expose
    public static boolean lyla = false;

    public static void toggle1Loot() {
        lootit = !lootit;
    }

    public static void toggleLyla() {
        lyla = !lyla;
    }

    /**
     * Twisted league tier 1 relics
     */

    @Expose
    public boolean abyssalAccumulatorUnlocked = false;
    @Expose
    public boolean endlessEnduranceUnlocked = false;
    @Expose
    public boolean darkAltarDevotionUnlocked = false;

    /**
     * Twisted league tier 2 relics
     */

    @Expose
    public boolean hardcoreHarvesterUnlocked = false;
    @Expose
    public boolean arcaneCourierUnlocked = false;
    @Expose
    public boolean UnnaturalSelectionUnlocked = false;

    /**
     * Twisted league tier 3 relics
     */

    @Expose
    public boolean eyeOfTheArtisanUnlocked = false;
    @Expose
    public boolean giftOfTheGathererUnlocked = false;
    @Expose
    public boolean wayOfTheWarriorUnlocked = false;

    /**
     * Twisted league tier 4 relics
     */

    @Expose
    public boolean spiritOfDinhUnlocked = false;
    @Expose
    public boolean konarsBlessingUnlocked = false;
    @Expose
    public boolean treasureSeekerUnlocked = false;

    /**
     * Twisted league tier 5 relics
     */

    @Expose
    public boolean xericsFocusUnlocked = false;
    @Expose
    public boolean xericsResilienceUnlocked = false;
    @Expose
    public boolean xericsWisdomUnlocked = false;

    /**
     * Trailblazer league
     */

    /**
     * Trailblazer league tier 1 relics
     */

    @Expose
    public boolean endlessHarvestUnlocked = false;
    @Expose
    public boolean productionMasterUnlocked = false;
    @Expose
    public boolean skillingProdigyUnlocked = false;

    /**
     * Trailblazer league tier 2 relics
     */

    @Expose
    public boolean fairysFlightUnlocked = false;
    @Expose
    public boolean eternalJewellerUnlocked = false;
    @Expose
    public boolean lastRecallUnlocked = false;

    /**
     * Trailblazer league tier 3 relics
     */

    @Expose
    public boolean quickShotUnlocked = false;
    @Expose
    public boolean fluidStrikesUnlocked = false;
    @Expose
    public boolean doubleCastUnlocked = false;

    /**
     * Trailblazer league tier 4 relics
     */

    //treasure seeker & unnatural selection

    /**
     * Trailblazer league tier 5 relics
     */

    @Expose
    public boolean theBotanistUnlocked = false;
    @Expose
    public boolean infernalGatheringUnlocked = false;
    @Expose
    public boolean EquilibriumUnlocked = false;

    /**
     * Shattered relics
     */

    @Expose
    public boolean hasUnlockedLumbridgeWaystone = false;
    @Expose
    public boolean hasUnlockedFaladorWaystone = false;
    @Expose
    public boolean hasUnlockedVarrockWaystone = false;
    @Expose
    public boolean hasUnlockedAlKharidWaystone = false;
    @Expose
    public boolean hasUnlockedCatherbyWaystone = false;
    @Expose
    public boolean hasUnlockedArdougneWaystone = false;
    @Expose
    public boolean hasUnlockedBrimhavenWaystone = false;
    @Expose
    public boolean hasUnlockedRellekkaWaystone = false;
    @Expose
    public boolean hasUnlockedFeroxEnclaveWaystone = false;
    @Expose
    public boolean hasUnlockedCanifisWaystone = false;
    @Expose
    public boolean hasUnlockedPrifddinasWaystone = false;
    @Expose
    public boolean hasUnlockedKourendWaystone = false;

    /**
     * Inferno sacrfice fire cape
     */

    @Expose
    public boolean hasSacrificedFireCape = false;

    /**
     * BitCoin
     */
    @Expose
    public double bitcoins;


    /**
     * Drop table search results.
     */
    public List<DropTableEntry> dropTableSearchResults;

    @Expose
    public boolean debug;
    @Expose
    @Getter
    @Setter
    public int tutorialStage;
    @Setter
    protected Runnable onDialogueContinued;

    @Expose
    public Journal journal = Journal.BESTIARY;

    @Expose
    public int targetOverlaySetting = 0;
    public Entity targetOverlayTarget;
    public int targetOverlayResetTicks;

    @Expose
    public boolean breakVials;
    @Expose
    public boolean discardBuckets;

    @Expose
    public BirdHouseHandler birdHouseHandler;

    /**
     * Quests
     */
    @Expose
    public int questPoints = 0;
    @Expose
    public int completedQuests;
    @Expose
    public int doricsQuest;
    @Expose
    public int cooksAssistant;
    @Expose
    public int dragonslayerTwo;
    @Expose
    public int monkeyMadness;
    @Expose
    public int santaQuest;
    @Expose
    public int sheepShearer;

    /**
     * arena pvp variables
     */
    @Expose
    public int conquestPoints;
    @Expose
    public int arenatwosWins;
    @Expose
    public int arenathreesWins;
    @Expose
    public int battlegroundWins;
    @Expose
    public boolean claimedSeasonReward;
    public BattleGrounds battleGroundsGame;


    /**
     * achivement points
     */
    @Expose
    public int achievementPoints;

    /**
     * Reputation
     */
    @Expose
    public int greendragonRep;
    @Expose
    public int giantmoleRep;
    @Expose
    public int goblinRep;
    @Expose
    public int gnomeTroops;
    @Expose
    public int shamanRep;
    @Expose
    public int wyrmRep;

    @Expose
    public int kingArthurRep;
    @Expose
    public int sirKayRep;
    @Expose
    public int pentynRep;
    @Expose
    public int councillorAndrews;
    @Expose
    public int kingRoald;
    @Expose
    public int auburyRep;
    @Expose
    public int thessaliaRep;
    @Expose
    public int ellamariaRep;

    @Expose
    public int flynnRep;
    @Expose
    public int doormanRep;
    @Expose
    public int herquinRep;
    @Expose
    public int wayneRep;

    @Expose
    public static final int maxDesertRep = 7500;
    @Expose
    public static final int maxEdgevilleRep = 21000;
    @Expose
    public static final int maxFaladorRep = 10000;
    @Expose
    public static final int maxFremennikRep = 6000;
    @Expose
    public static final int maxKandarinRep = 5000;
    @Expose
    public static final int maxKaramjaRep = 5500;
    @Expose
    public static final int maxKebosRep = 10000;
    @Expose
    public static final int maxKourendRep = 8500;
    @Expose
    public static final int maxLumbridgeRep = 7000;
    @Expose
    public static final int maxMorytaniaRep = 12000;
    @Expose
    public static final int maxPrifddinasRep = 10000;
    @Expose
    public static final int maxVarrockRep = 8000;
    @Expose
    public int desertRep, edgevilleRep, faladorRep, fremennikRep, kandarinRep, karamjaRep, kebosRep, kourendRep, lumbridgeRep, morytaniaRep, prifddinasRep, varrockRep;

    /**
     * Diaries
     */
    @Expose
    public int easyArdy = 0;
    @Expose
    public int medArdy = 0;
    @Expose
    public int hardArdy = 0;
    @Expose
    public int eliteArdy = 0;

    @Expose
    public int desertEasy = 0;
    @Expose
    public int desertMedium = 0;
    @Expose
    public int desertHard = 0;
    @Expose
    public int desertElite = 0;

    @Expose
    public int faladorEasy = 0;
    @Expose
    public int faladorMedium = 0;
    @Expose
    public int faladorHard = 0;
    @Expose
    public int faladorElite = 0;

    @Expose
    public int fremennikEasy = 0;
    @Expose
    public int fremennikMedium = 0;
    @Expose
    public int fremennikHard = 0;
    @Expose
    public int fremennikElite = 0;

    @Expose
    public int kandarinEasy = 0;
    @Expose
    public int kandarinMedium = 0;
    @Expose
    public int kandarinHard = 0;
    @Expose
    public int kandarinElite = 0;

    @Expose
    public int karamjaEasy = 0;
    @Expose
    public int karamjaMedium = 0;
    @Expose
    public int karamjaHard = 0;
    @Expose
    public int karamjaElite = 0;

    @Expose
    public int lumbEasy = 0;
    @Expose
    public int lumbMedium = 0;
    @Expose
    public int lumbHard = 0;
    @Expose
    public int lumbElite = 0;

    @Expose
    public int morytaniaEasy = 0;
    @Expose
    public int morytaniaMedium = 0;
    @Expose
    public int morytaniaHard = 0;
    @Expose
    public int morytaniaElite = 0;

    @Expose
    public int varrockEasy = 0;
    @Expose
    public int varrockMedium = 0;
    @Expose
    public int varrockHard = 0;
    @Expose
    public int varrockElite = 0;

    @Expose
    public int westernEasy = 0;
    @Expose
    public int westernMedium = 0;
    @Expose
    public int westernHard = 0;
    @Expose
    public int westernElite = 0;

    @Expose
    public int wildyEasy = 0;
    @Expose
    public int wildyMedium = 0;
    @Expose
    public int wildyHard = 0;
    @Expose
    public int wildyElite = 0;

    @Expose
    public int kourendEasy = 0;
    @Expose
    public int kourendMedium = 0;
    @Expose
    public int kourendHard = 0;
    @Expose
    public int kourendElite = 0;

    @Expose
    public boolean tonel = false;
    @Expose
    public boolean ttwol = false;
    @Expose
    public boolean tthreel = false;
    @Expose
    public boolean tfourl = false;
    @Expose
    public boolean tfivel = false;
    @Expose
    public boolean tsixl = false;
    @Expose
    public boolean tsevenl = false;
    @Expose
    public boolean teightl = false;
    @Expose
    public boolean tninel = false;

    @Expose
    public int tone;
    @Expose
    public int ttwo;
    @Expose
    public int tthree;
    @Expose
    public int tfour;
    @Expose
    public int tfive;
    @Expose
    public int tsix;
    @Expose
    public int tseven;
    @Expose
    public int teight;
    @Expose
    public int tnine;

    @Expose
    public int card = 0;

    /**
     * Poll booth
     */
    @Expose
    @Getter
    @Setter
    public PlayerBallot ballot;

    @Expose
    @Getter
    @Setter
    PollBooth pollBooth;

    /**
     * has accepted skilling contracts boolean
     */
    @Expose
    public boolean hasAcceptedFarmingContract;
    @Expose
    public boolean hasAcceptedHerbloreContract;
    @Expose
    public boolean hasAcceptedWoodcuttingContract;
    @Expose
    public boolean hasAcceptedFiremakingContract;
    @Expose
    public boolean hasAcceptedFletchingContract;
    @Expose
    public boolean hasAcceptedMiningContract;
    @Expose
    public boolean hasAcceptedSmithingContract;
    @Expose
    public boolean hasAcceptedFishingContract;
    @Expose
    public boolean hasAcceptedCookingContract;
    @Expose
    public boolean hasAcceptedSlayerContract;
    @Expose
    public boolean hasAcceptedAgilityContract;
    @Expose
    public boolean hasAcceptedHunterContract;
    @Expose
    public boolean hasAcceptedThievingContract;
    @Expose
    public boolean hasAcceptedPrayerContract;
    @Expose
    public boolean hasAcceptedRunecraftingContract;
    @Expose
    public boolean hasAcceptedConstructionContract;

    /**
     * has completed skilling contracts boolean
     */
    @Expose
    public boolean hasCompletedFarmingContract;
    @Expose
    public boolean hasCompletedHerbloreContract;
    @Expose
    public boolean hasCompletedWoodcuttingContract;
    @Expose
    public boolean hasCompletedFiremakingContract;
    @Expose
    public boolean hasCompletedFletchingContract;
    @Expose
    public boolean hasCompletedMiningContract;
    @Expose
    public boolean hasCompletedSmithingContract;
    @Expose
    public boolean hasCompletedFishingContract;
    @Expose
    public boolean hasCompletedCookingContract;
    @Expose
    public boolean hasCompletedSlayerContract;
    @Expose
    public boolean hasCompletedAgilityContract;
    @Expose
    public boolean hasCompletedHunterContract;
    @Expose
    public boolean hasCompletedThievingContract;
    @Expose
    public boolean hasCompletedPrayerContract;
    @Expose
    public boolean hasCompletedRunecraftingContract;
    @Expose
    public boolean hasCompletedConstructionContract;

    /**
     * skilling contract int amount
     **/
    @Expose
    public int woodcuttingContractAmount;
    @Expose
    public int fishingContractAmount;
    @Expose
    public int miningContractAmount;
    @Expose
    public int smithingContractAmount;
    @Expose
    public int fletchingContractAmount;
    @Expose
    public int firemakingContractAmount;
    @Expose
    public int herbloreContractAmount;
    @Expose
    public int farmingContractAmount;
    @Expose
    public int cookingContractAmount;
    @Expose
    public int slayerContractAmount;
    @Expose
    public int agilityContractAmount;
    @Expose
    public int hunterContractAmount;
    @Expose
    public int thievingContractAmount;
    @Expose
    public int prayerContractAmount;
    @Expose
    public int runecraftingContractAmount;
    @Expose
    public int constructionContractAmount;

    /**
     * farming contract tier
     **/
    @Expose
    @Getter
    public FarmingContractTier farmingContractTier;


    /**
     * skilling contract types
     **/
    @Expose
    @Getter
    public AgilityContractType agilityContractType;
    @Expose
    @Getter
    public FarmingContractType farmingContractType;
    @Expose
    @Getter
    public FishingContractType fishingContractType;
    @Expose
    @Getter
    public MiningContractType miningContractType;
    @Expose
    @Getter
    public WoodcuttingContractType woodcuttingContractType;
    @Expose
    @Getter
    public SmithingContractType smithingContractType;

    /**
     * Nightmare Zone
     */
    @Expose
    public int nightmareZonePoints = 0;

    @Expose
    public int nmzRewardPoints;

    @Expose
    public boolean nmz = false;

    @Expose
    public int nmzCofferCoins;

    @Expose
    public int deathsCoffer;

    @Expose
    public int absorptionPoints = 0;

    @Expose
    public int combatXpRate = 100;

    @Expose
    public XpMode xpMode = XpMode.EASY;

    @Expose
    public boolean restored;
    @Expose
    public boolean beforeFuckup;
    @Expose
    public boolean edgeHome;
    @Expose
    public boolean dzHome;

    @Expose
    public int theatreOfBloodCompleted;
    @Expose
    public int theatreOfBloodStage = 0;
    @Expose
    public String theatreroom = "";
    @Expose
    public boolean tobreward = false;
    @Expose
    public boolean scrying;
    public int viewingTheatreSlot = -1;
    @Expose
    @Setter
    @Getter
    public boolean acceptedTheatreRisk;
    @Expose
    @Setter
    @Getter
    public boolean acceptedTheatreCrystals;

    /**
     * 2FA
     */

    public boolean tfa;

    /**
     * Agility
     */
    @Expose
    public int lastAgilityObjId = -1;
    @Expose
    public int draynorRooftopLaps;
    @Expose
    public int alKharidRooftopLaps;
    @Expose
    public int varrockRooftopLaps;
    @Expose
    public int canifisRooftopLaps;
    @Expose
    public int faladorRooftopLaps;
    @Expose
    public int seersRooftopLaps;
    @Expose
    public int rellekkaRooftopLaps;
    @Expose
    public int ardougneRooftopLaps;
    @Expose
    public int gnomeStrongholdLaps;
    @Expose
    public int barbarianCrouseLaps;
    @Expose
    public int wildernessCourseLaps;
    @Expose
    public int prifddinasLaps;

    /**
     * Smelting
     */
    @Expose
    public int smeltedBronzeBars;
    @Expose
    public int smeltedBluriteBars;
    @Expose
    public int smeltedIronBars;
    @Expose
    public int smeltedSilverBars;
    @Expose
    public int smeltedSteelBars;
    @Expose
    public int smeltedGoldBars;
    @Expose
    public int smeltedMithrilBars;
    @Expose
    public int smeltedAdamantBars;
    @Expose
    public int smeltedRuniteBars;
    @Expose
    public int smeltedCorruptBars;

    @Expose
    public int smithedBars;


    /**
     * Mining
     */
    @Expose
    public int minedClay;
    @Expose
    public int minedCopper;
    @Expose
    public int minedTin;
    @Expose
    public int minedIron;
    @Expose
    public int minedSilver;
    @Expose
    public int minedCoal;
    @Expose
    public int minedGold;
    @Expose
    public int minedMithril;
    @Expose
    public int minedLovakite;
    @Expose
    public int minedAdamant;
    @Expose
    public int minedRunite;
    @Expose
    public int minedAmethyst;
    @Expose
    public int minedRuneEssence;
    @Expose
    public int minedPureEssence;
    @Expose
    public int minedDarkEssence;
    @Expose
    public int minedGeode;
    @Expose
    public int minedSandstone;
    @Expose
    public int minedGranite;
    @Expose
    public int minedGemRock;
    @Expose
    public int minedCorrupt;
    @Expose
    public int minedTephra;

    /**
     * Woodcutting
     */
    @Expose
    public int choppedRegular;
    @Expose
    public int choppedSapling;
    @Expose
    public int choppedAchey;
    @Expose
    public int choppedOak;
    @Expose
    public int choppedWillow;
    @Expose
    public int choppedTeak;
    @Expose
    public int choppedJuniper;
    @Expose
    public int choppedMaple;
    @Expose
    public int choppedYew;
    @Expose
    public int choppedMagic;
    @Expose
    public int choppedRedwood;
    @Expose
    public int choppedCelastrus;
    @Expose
    public int choppedMahogany;
    @Expose
    public int choppedCorrupt;
    @Expose
    public int openedBirdsNests;
    @Expose
    public int acquiredBirdsNests;

    /**
     * Cooking
     */
    @Expose
    public int cookedFood;
    @Expose
    public int burntFood;
    @Expose
    public int jugsOfWineMade;
    @Expose
    public int cookedOnFire;

    /**
     * Thieving
     */
    @Expose
    public int vegetableStallThieves;
    @Expose
    public int bakerStallThieves;
    @Expose
    public int craftingStallThieves;
    @Expose
    public int monkeyFoodStallThieves;
    @Expose
    public int monkeyGeneralStallThieves;
    @Expose
    public int teaStallThieves;
    @Expose
    public int silkStallThieves;
    @Expose
    public int wineStallThieves;
    @Expose
    public int seedStallThieves;
    @Expose
    public int furStallThieves;
    @Expose
    public int fishStallThieves;
    @Expose
    public int crossbowStallThieves;
    @Expose
    public int silverStallThieves;
    @Expose
    public int spiceStallThieves;
    @Expose
    public int magicStallThieves;
    @Expose
    public int scimitarStallThieves;
    @Expose
    public int gemStallThieves;
    @Expose
    public int gemMorStallThieves;
    @Expose
    public int oreStallThieves;
    @Expose
    public int pickpocketMan;
    @Expose
    public int pickpocketFarmer;
    @Expose
    public int pickpocketWarrior;
    @Expose
    public int pickpocketHamMember;
    @Expose
    public int pickpocketRogue;
    @Expose
    public int pickpocketMasterFarmer;
    @Expose
    public int pickpocketGuard;
    @Expose
    public int pickpocketBandit;
    @Expose
    public int pickpocketKnight;
    @Expose
    public int pickpocketPaladin;
    @Expose
    public int pickpocketGnome;
    @Expose
    public int pickpocketHero;
    @Expose
    public int pickpocketElf;
    @Expose
    public int pickpocketTzhaarHur;
    @Expose
    public int wallSafesCracked;
    @Expose
    public int rougesCastleChests;
    @Expose
    public int pickedLocks;

    /**
     * Firemaking
     */
    @Expose
    public int normalLogsBurnt;
    @Expose
    public int acheyLogsBurnt;
    @Expose
    public int oakLogsBurnt;
    @Expose
    public int willowLogsBurnt;
    @Expose
    public int teakLogsBurnt;
    @Expose
    public int arcticPineLogsBurnt;
    @Expose
    public int mapleLogsBurnt;
    @Expose
    public int mahoganyLogsBurnt;
    @Expose
    public int yewLogsBurnt;
    @Expose
    public int magicLogsBurnt;
    @Expose
    public int redwoodLogsBurnt;
    @Expose
    public int redLogsBurnt;
    @Expose
    public int greenLogsBurnt;
    @Expose
    public int blueLogsBurnt;
    @Expose
    public int whiteLogsBurnt;
    @Expose
    public int purpleLogsBurnt;
    @Expose
    public int kindlingBurnt;

    /**
     * Prayer
     */
    @Expose
    public int fiendishAshesSpread;
    @Expose
    public int vileAshesSpread;
    @Expose
    public int maliciousAshesSpread;
    @Expose
    public int infernalAshesSpread;
    @Expose
    public int abyssalAshesSpread;

    @Expose
    public int regularBonesBuried;
    @Expose
    public int burntBonesBuried;
    @Expose
    public int batBonesBuried;
    @Expose
    public int wolfBonesBuried;
    @Expose
    public int bigBonesBuried;
    @Expose
    public int longBonesBuried;
    @Expose
    public int curvedBonesBuried;
    @Expose
    public int jogreBonesBuried;
    @Expose
    public int babydragonBonesBuried;
    @Expose
    public int dragonBonesBuried;
    @Expose
    public int zogreBonesBuried;
    @Expose
    public int ourgBonesBuried;
    @Expose
    public int monkeyBonesBuried;
    @Expose
    public int wyvernBonesBuried;
    @Expose
    public int dagannothBonesBuried;
    @Expose
    public int lavaDragonBonesBuried;
    @Expose
    public int superiorDragonBonesBuried;
    @Expose
    public int wyrmBonesBuried;
    @Expose
    public int drakeBonesBuried;
    @Expose
    public int hydraBonesBuried;
    @Expose
    public int regularBonesAltar;
    @Expose
    public int burntBonesAltar;
    @Expose
    public int batBonesAltar;
    @Expose
    public int wolfBonesAltar;
    @Expose
    public int bigBonesAltar;
    @Expose
    public int longBonesAltar;
    @Expose
    public int curvedBonesAltar;
    @Expose
    public int jogreBonesAltar;
    @Expose
    public int babydragonBonesAltar;
    @Expose
    public int dragonBonesAltar;
    @Expose
    public int zogreBonesAltar;
    @Expose
    public int ourgBonesAltar;
    @Expose
    public int monkeyBonesAltar;
    @Expose
    public int wyvernBonesAltar;
    @Expose
    public int dagannothBonesAltar;
    @Expose
    public int lavaDragonBonesAltar;
    @Expose
    public int superiorDragonBonesAltar;
    @Expose
    public int wyrmBonesAltar;
    @Expose
    public int drakeBonesAltar;
    @Expose
    public int hydraBonesAltar;
    public TickDelay boneBuryDelay = new TickDelay();
    public TickDelay ashSpreadDelay = new TickDelay();

    /**
     * Battle-Pass
     */
    public boolean claimedPass = true;
    @Expose
    public int seasonpassv = 0;
    @Expose
    public int currentLevel;
    @Expose
    public int currentxp;
    @Expose
    public List<Boolean> takenRewards = new ArrayList<>();
    @Expose
    public List<Level> levels = new ArrayList<>();

    /**
     * Fishing
     */
    @Expose
    public int totalFishCaught;


    /**
     * Construction
     */
    public int houseBuildPointX, houseBuildPointY, houseBuildPointZ;
    public Room[] houseViewerRooms;
    public Room houseViewerRoom;

    /**
     * Runecrafting
     */

    @Expose
    public int craftedAir;
    @Expose
    public int craftedMind;
    @Expose
    public int craftedWater;
    @Expose
    public int craftedEarth;
    @Expose
    public int craftedFire;
    @Expose
    public int craftedBody;
    @Expose
    public int craftedCosmic;
    @Expose
    public int craftedLaw;
    @Expose
    public int craftedNature;
    @Expose
    public int craftedChaos;
    @Expose
    public int craftedDeath;
    @Expose
    public int craftedAstral;
    @Expose
    public int craftedBlood;
    @Expose
    public int craftedSoul;
    @Expose
    public int craftedWrath;


    /**
     * Farming
     */

    @Expose
    public int harvestedPotato;
    @Expose
    public int harvestedOnion;
    @Expose
    public int harvestedCabbage;
    @Expose
    public int harvestedTomato;
    @Expose
    public int harvestedSweetcorn;
    @Expose
    public int harvestedStrawberry;
    @Expose
    public int harvestedWatermelon;

    @Expose
    public int harvestedMarigolds;
    @Expose
    public int harvestedRosemary;
    @Expose
    public int harvestedNasturtium;
    @Expose
    public int harvestedWoad;
    @Expose
    public int harvestedLimpwurt;

    @Expose
    public int harvestedGuam;
    @Expose
    public int harvestedMarrentill;
    @Expose
    public int harvestedTarromin;
    @Expose
    public int harvestedHarralander;
    @Expose
    public int harvestedRanarr;
    @Expose
    public int harvestedToadflax;
    @Expose
    public int harvestedIrit;
    @Expose
    public int harvestedAvantoe;
    @Expose
    public int harvestedKwuarm;
    @Expose
    public int harvestedSnapdragon;
    @Expose
    public int harvestedCadantine;
    @Expose
    public int harvestedLantadyme;
    @Expose
    public int harvestedDwarfWeed;
    @Expose
    public int harvestedTorstol;

    @Expose
    public int harvestedBittercap;

    @Expose
    public int grownApple;
    @Expose
    public int grownBanana;
    @Expose
    public int grownOrange;
    @Expose
    public int grownCurry;
    @Expose
    public int grownPineapple;
    @Expose
    public int grownPapaya;
    @Expose
    public int grownPalm;

    @Expose
    public int grownOak;
    @Expose
    public int grownWillow;
    @Expose
    public int grownMaple;
    @Expose
    public int grownYew;
    @Expose
    public int grownMagic;
    @Expose
    public int grownRedWood;
    @Expose
    public int grownCelastrus;

    @Expose
    public int grownRedberry;
    @Expose
    public int grownCadavaberry;
    @Expose
    public int grownDwellberry;
    @Expose
    public int grownJangerberry;
    @Expose
    public int grownWhiteberry;
    @Expose
    public int grownPoisonIvy;

    @Expose
    public int harvestedBarley;
    @Expose
    public int harvestedHammerstone;
    @Expose
    public int harvestedAsgarnian;
    @Expose
    public int harvestedJute;
    @Expose
    public int harvestedYanillian;
    @Expose
    public int harvestedKrandorian;
    @Expose
    public int harvestedWildblood;

    @Expose
    public int grownCalquat;
    @Expose
    public int grownCactus;

    @Expose
    public int grownSpiritTree;

    @Expose
    public int grownAttas;
    @Expose
    public int grownIasor;
    @Expose
    public int grownKronos;
    @Expose
    public int harvestedHespori;

    /**
     * Clue scrolls
     */

    @Expose
    public ClueSave beginnerClue;
    @Expose
    public ClueSave easyClue;
    @Expose
    public ClueSave medClue;
    @Expose
    public ClueSave hardClue;
    @Expose
    public ClueSave eliteClue;
    @Expose
    public ClueSave masterClue;

    @Expose
    public int beginnerClueCount;
    @Expose
    public int easyClueCount;
    @Expose
    public int medClueCount;
    @Expose
    public int hardClueCount;
    @Expose
    public int eliteClueCount;
    @Expose
    public int masterClueCount;


    /**
     * Motherlode mine
     */

    @Expose
    public int minedPaydirt;
    @Expose
    public int cleanedPaydirt;

    @Expose
    public int paydirtInWater;

    @Expose
    public boolean motherlodeRestrictedMineUnlocked;
    @Expose
    public boolean motherlodeBiggerSackUnlocked;

    /**
     * Pyramid Plunder
     */
    @Expose
    public long lastTimeEnteredPlunder = 0;
    @Expose
    public boolean disarmedPlunderRoomTrap = false;
    @Expose
    public boolean inPyramidPlunder = false;
    @Expose
    public int nextPlunderRoomId;
    @Expose
    public int totalPyramidPlunderGames = 0;
    @Expose
    public boolean LeftPyramid = false;
    @Expose
    public long PyramidtimeRemaining;
    @Expose
    public boolean loot26580;
    @Expose
    public boolean loot26600;
    @Expose
    public boolean loot26601;
    @Expose
    public boolean loot26602;
    @Expose
    public boolean loot26603;
    @Expose
    public boolean loot26604;
    @Expose
    public boolean loot26605;
    @Expose
    public boolean loot26606;
    @Expose
    public boolean loot26607;
    @Expose
    public boolean loot26608;
    @Expose
    public boolean loot26609;
    @Expose
    public boolean loot26610;
    @Expose
    public boolean loot26611;
    @Expose
    public boolean loot26612;
    @Expose
    public boolean loot26613;
    @Expose
    public boolean loot26616;
    @Expose
    public boolean loot26626;

    /**
     * Misc
     */

    @Expose
    public long playTime;

    @Expose
    public int staminaTicks;

    public int specialRestoreTicks;

    public Config selectedKeybindConfig;

    public int selectedSettingMenu = -1;
    public int selectedSettingChild = -1;

    public TickDelay eatDelay = new TickDelay();

    public TickDelay karamDelay = new TickDelay();

    public TickDelay potDelay = new TickDelay();

    public SmithBar smithBar;

    @Expose
    public int darkEssFragments;

    @Expose
    public int baggedCoal;

    @Expose
    public boolean miningGuildMinerals;

    @Expose
    public int gluttony;

    @Expose
    public int yesEmote;

    @Expose
    public int noEmote;

    public TickDelay yesDelay = new TickDelay();

    public TickDelay noDelay = new TickDelay();

    public TickDelay emoteDelay = new TickDelay();

    @Expose
    public int teleportTabsBroken;

    @Expose
    public boolean beginnerParkourEnergyBoost;

    @Expose
    public boolean goldenArmadylGodsword = false;
    @Expose
    public boolean goldenBandosGodsword = false;
    @Expose
    public boolean goldenSaradominGodsword = false;
    @Expose
    public boolean goldenZamorakGodsword = false;

    public Teleother teleotherActive;


    @Expose
    public boolean kylieMinnowDialogueStarted;
    @Expose
    public boolean taxidermistDialogueStarted;
    @Expose
    public boolean fairyAerykaDialogueStarted;

    @Expose
    public boolean elnockInquisitorDialogueStarted;

    @Expose
    public boolean elnockInquisitorGivenEquipment;

    public NPC examineMonster;

    @Expose
    public int runForestRun;

    @Expose
    public boolean runForestRunUnlockable;

    @Expose
    public BarrowsBrother barrowsChestBrother;
    @Expose
    public boolean BarrowsMessage = false;

    @Expose
    public int barrowsChestsOpened;

    public TickDelay magicImbueEffect = new TickDelay();

    public NPC teleportsWizard;

    @Expose
    public int teleportCategoryIndex, teleportSubcategoryIndex;

    @Expose
    public boolean canEnterMorUlRek;

    @Expose
    public FairyRing[] unlockedFairyRingTeleports = new FairyRing[FairyRing.values().length];

    public GameObject fairyRing;

    public CanoeStation canoeStation;

    public boolean npcTarget; //just so we can check if a player has a target or not.

    @Expose
    public boolean edgevilleLeverWarning = true;

    @Expose
    public int mageArenaPoints;

    public boolean teleportsInterface;

    @Expose
    public int previousTeleportX = -1, previousTeleportY, previousTeleportZ;

    @Expose
    public int previousTeleportPrice;

    public TickDelay overloadHeartCooldown = new TickDelay();
    public TickDelay imbueHeartCooldown = new TickDelay();
    public TickDelay teleCooldown = new TickDelay();
    @Expose
    public boolean newPlayer = true;

    @Expose
    public boolean finishedIntro = false;

    public boolean inTutorial = false;

    @Expose
    public boolean choseXpMode = false;

    @Expose
    public boolean krakenWarning = true;

    @Expose
    public boolean smokeBossWarning = true;

    @Expose
    public boolean wyvernWarning = true;

    @Expose
    public int dragSetting = 5;

    @Expose
    public int switchGrading;

    @Expose
    public boolean showTimers = true;

    @Expose
    public boolean colorValuedGroundItems = false;

    @Expose
    public boolean swapRangePrayerPosition;

    @Expose
    public boolean swapMagePrayerPosition;

    @Expose
    public long yellDelay;

    @Expose
    public boolean yellFilter;

    @Expose
    public boolean hideTitles = false;

    /**
     * PVP
     */

    public boolean pvpAttackZone;

    public boolean singlesPlus;

    public int pvpCombatLevel;

    @Expose
    public int recoilDamageRemaining = 40;

    @Expose
    public int sufferingCharges;

    /**
     * Dueling stuff
     */

    @Expose
    public int presetDuelVarp, lastDuelVarp;

    @Expose
    public int duelWins, duelLosses;

    public TickDelay acceptDelay = new TickDelay();

    public int totalStaked = 0;

    public int bloodMoneyStaked = 0;

    public int bloodyTokensStaked = 0;

    @Expose
    public boolean experienceLock;


    /**
     * Wilderness stuff
     */

    public int wildernessLevel = -1;

    @Expose
    public boolean mageArena, resourceArea;

    @Expose
    public boolean obeliskRedirectionScroll;

    @Expose
    public WildernessObelisk obeliskDestination;

    @Expose
    public int SilverCraftingAmount = 1;

    /**
     * Teleport interface
     */
    public int teleportSelectedCategory = -1;
    @Expose
    public List<Integer> favoriteTeleports = new LinkedList<>();
    public List<TeleportList.Teleport> searchTeleports;
    public boolean tpWildWarn;

    /**
     * Achievements
     */

    public AchievementStage[] achievementStages = new AchievementStage[Achievement.values().length];

    /**
     * Gem bag
     */
    @Expose
    public int gemBagTopaz;
    @Expose
    public int gemBagOpal;
    @Expose
    public int gemBagJade;
    @Expose
    public int gemBagDiamond;
    @Expose
    public int gemBagRuby;
    @Expose
    public int gemBagEmerald;
    @Expose
    public int gemBagSapphire;
    @Expose
    public int gemBagDragonstone;
    @Expose
    public int gemBagOnyx;
    @Expose
    public int gemBagZenyte;

    /**
     * Herb sack
     */
    @Expose
    public int herbSackGuamLeaf;
    @Expose
    public int herbSackMarrentill;
    @Expose
    public int herbSackTarromin;
    @Expose
    public int herbSackHarralander;
    @Expose
    public int herbSackRanarrWeed;
    @Expose
    public int herbSackToadflax;
    @Expose
    public int herbSackIritLeaf;
    @Expose
    public int herbSackAvantoe;
    @Expose
    public int herbSackKwuarm;
    @Expose
    public int herbSackSnapdragon;
    @Expose
    public int herbSackCadantine;
    @Expose
    public int herbSackLantadyme;
    @Expose
    public int herbSackDwarfWeed;
    @Expose
    public int herbSackTorstol;

    /**
     * Fight caves
     */
    @Expose
    public FightCaves fightCaves;
    @Expose
    public long fightCavesBestTime;

    /**
     * Six Jad Challenge
     */
    @Expose
    public boolean Challenge2 = false;
    @Expose
    public boolean Challenge3 = false;
    @Expose
    public boolean Challenge4 = false;
    @Expose
    public boolean Challenge5 = false;
    @Expose
    public boolean Challenge6 = false;
    @Expose
    public JadChallenge sixjad;
    @Expose
    public long sixjadBestTime;
    /**
     * Inferno
     */
    @Expose
    public boolean talkedToTzHaarKetKeh;
    @Expose
    public Inferno inferno;
    @Expose
    public long infernoBestTime;

    /**
     * Warriors guild
     */
    public TickDelay tokenEvent = new TickDelay();
    public int nextDefenderId = -1;

    /**
     * Wintertodt
     */
    public int wintertodtPoints = 0;
    @Expose
    public int lifetimeWintertodtPoints;
    @Expose
    public int wintertodtSubdued;
    @Expose
    public int wintertodtHighscore;
    @Expose
    public boolean talkedToIgnisia;

    /**
     * Hunter
     */
    public ArrayList<Trap> traps = new ArrayList<>(5);
    @Expose
    public int caughtSwift, caughtWarbler, caughtLongtail, caughtTwitch, caughtWagtail;
    @Expose
    public int caughtGreyChinchompa, caughtRedChinchompa, caughtBlackChinchompa;
    @Expose
    public int caughtSwampLizard, caughtOrangeSalamander, caughtRedSalamander, caughtBlackSalamander;

    /**
     * Godwars
     */
    public TickDelay godwarsAltarCooldown = new TickDelay();

    /**
     * Duo Slayer
     */
    @Expose
    @Getter
    @Setter
    public boolean duoSlayerPartner = false;
    @Expose
    @Getter
    @Setter
    public String duoSlayerPartnerName = null;
    @Expose
    @Getter
    @Setter
    public int duoSlayerPartnerUid = -1;
    @Expose
    public String duoSlayerTaskName = null;
    @Expose
    public int duoSlayerTaskRemaining = 0;
    @Expose
    public int duoSlayerTasksCompleted = 0;
    @Expose
    public int duoSlayerSpree = 0;
    @Expose
    public int duoSlayerLocation = 0;
    @Expose
    public boolean duoSlayerCombatCheck = true;
    @Expose
    @Getter
    @Setter
    public int duoSlayerTaskId;
    @Expose
    @Getter
    @Setter
    public boolean duoSlayerHost = false;

    /**
     * Slayer
     */
    public SlayerTask slayerTask;
    @Expose
    public int slayerTaskRemaining;
    @Expose
    public int slayerExtendedCounter;
    @Expose
    public int slayerTaskAmountAssigned;
    @Expose
    public List<String> slayerBlockedTasks;
    @Expose
    public int slayerTasksCompleted;
    @Expose
    public boolean slayerTaskDangerous = false;
    @Expose
    public int wildernessTasksCompleted;
    @Expose
    public int wildernessSlayerPoints;
    @Expose
    public int taskLocation = 0;

    public TickDelay blackChinchompaBoost = new TickDelay();
    @Expose
    public int blackChinchompaBoostTimeLeft;
    public TickDelay darkCrabBoost = new TickDelay();
    @Expose
    public int darkCrabBoostTimeLeft;

    /**
     * Antifire
     */
    @Expose
    public int antifireTicks;
    @Expose
    public int superAntifireTicks;

    /**
     * Alch
     */
    public TickDelay alchDelay = new TickDelay();

    /**
     * Jail
     */

    @Expose
    public String offense;

    /**
     * Jail
     */

    @Expose
    public String jailerName;

    @Expose
    public int jailOresAssigned, jailOresCollected;
    @Expose
    public long jailTime;

    @Expose
    public boolean jailed = false;

    /**
     * Vengeance
     */

    @Expose
    public boolean vengeanceActive;

    /**
     * Superheat delay
     */
    public TickDelay superheatDelay = new TickDelay();

    /**
     * Runecrafting pouch
     */
    @Expose
    public Map<EssencePouch, Integer> runeEssencePouches = new HashMap<>();

    /**
     * Mute
     */

    @Expose
    public long muteEnd;

    @Expose
    public boolean shadowMute;

    /**
     * Kill spree/shutdown
     */
    @Expose
    public int currentKillSpree;
    @Expose
    public int highestKillSpree;
    @Expose
    public int highestShutdown;
    @Expose
    public long nextWildernessBonus;

    /**
     * Farm prevention
     */
    @Expose
    public TimedList recentMembers = new TimedList();
    @Expose
    public TimedList bmIpLogs = new TimedList();
    @Expose
    public TimedList bmUserLogs = new TimedList();

    /**
     * Runecrafting achievement thingies
     */
    @Expose
    public boolean enteredAbyss;
    @Expose
    public int abyssCreaturesKilled;

    /**
     * Spec effect
     */
    @Expose
    public int dragonAxeSpecial;
    @Expose
    public int infernalAxeSpecial;
    @Expose
    public int dragonPickaxeSpecial;
    @Expose
    public int infernalPickaxeSpecial;

    @Expose
    public boolean morytaniaFarmAchievement;

    public OptionScroll optionScroll;

    /**
     * Kill counter interface handler stuff (I dont like this)
     */
    public List<Function<Player, KillCounter>> activeKillLogList = null;
    public int activeKillLogSlot = -1;
    /**
     * Boss kill counters
     */

    @Expose
    public KillCounter barrowsKills = new KillCounter();
    @Expose
    public KillCounter kreeArraKills = new KillCounter();
    @Expose
    public KillCounter commanderZilyanaKills = new KillCounter();
    @Expose
    public KillCounter generalGraardorKills = new KillCounter();
    @Expose
    public KillCounter krilTsutsarothKills = new KillCounter();
    @Expose
    public KillCounter dagannothRexKills = new KillCounter();
    @Expose
    public KillCounter dagannothPrimeKills = new KillCounter();
    @Expose
    public KillCounter dagannothSupremeKills = new KillCounter();
    @Expose
    public KillCounter giantMoleKills = new KillCounter();
    @Expose
    public KillCounter kalphiteQueenKills = new KillCounter();
    @Expose
    public KillCounter kingBlackDragonKills = new KillCounter();
    @Expose
    public KillCounter callistoKills = new KillCounter();
    @Expose
    public KillCounter venenatisKills = new KillCounter();
    @Expose
    public KillCounter vetionKills = new KillCounter();
    @Expose
    public KillCounter chaosElementalKills = new KillCounter();
    @Expose
    public KillCounter chaosFanaticKills = new KillCounter();
    @Expose
    public KillCounter crazyArchaeologistKills = new KillCounter();
    @Expose
    public KillCounter scorpiaKills = new KillCounter();
    @Expose
    public KillCounter barrowsChestsLooted = new KillCounter();
    @Expose
    public KillCounter corporealBeastKills = new KillCounter();
    @Expose
    public KillCounter zulrahKills = new KillCounter();
    @Expose
    public KillCounter jadCounter = new KillCounter();
    @Expose
    public KillCounter zukKills = new KillCounter();
    @Expose
    public KillCounter krakenKills = new KillCounter();
    @Expose
    public KillCounter thermonuclearSmokeDevilKills = new KillCounter();
    @Expose
    public KillCounter cerberusKills = new KillCounter();
    @Expose
    public KillCounter abyssalSireKills = new KillCounter();
    @Expose
    public KillCounter skotizoKills = new KillCounter();
    @Expose
    public KillCounter wintertodtKills = new KillCounter();
    @Expose
    public KillCounter nightmareofAshihamaKills = new KillCounter();
    @Expose
    public KillCounter oborKills = new KillCounter();
    @Expose
    public KillCounter chambersofXericKills = new KillCounter();
    @Expose
    public KillCounter theatreOfBloodKills = new KillCounter();
    @Expose
    public KillCounter derangedArchaeologistKills = new KillCounter();
    @Expose
    public KillCounter elvargKills = new KillCounter();
    @Expose
    public KillCounter vorkathKills = new KillCounter();
    @Expose
    public KillCounter hydraKills = new KillCounter();
    @Expose
    public KillCounter nexKills = new KillCounter();
    @Expose
    public KillCounter sarachnisKills = new KillCounter();
    @Expose
    public KillCounter alchemicalHydraKills = new KillCounter();
    @Expose
    public KillCounter bryophytaKills = new KillCounter();
    @Expose
    public KillCounter grotesqueGuardianKills = new KillCounter();
    @Expose
    public KillCounter hesporiKills = new KillCounter();

    /**
     * Slayer kill counters
     */
    @Expose
    public KillCounter crawlingHandKills = new KillCounter();
    @Expose
    public KillCounter caveBugKills = new KillCounter();
    @Expose
    public KillCounter caveCrawlerKills = new KillCounter();
    @Expose
    public KillCounter bansheeKills = new KillCounter();
    @Expose
    public KillCounter caveSlimeKills = new KillCounter();
    @Expose
    public KillCounter rockslugKills = new KillCounter();
    @Expose
    public KillCounter desertLizardKills = new KillCounter();
    @Expose
    public KillCounter cockatriceKills = new KillCounter();
    @Expose
    public KillCounter pyrefiendKills = new KillCounter();
    @Expose
    public KillCounter mogreKills = new KillCounter();
    @Expose
    public KillCounter harpieBugSwarmKills = new KillCounter();
    @Expose
    public KillCounter wallBeastKills = new KillCounter();
    @Expose
    public KillCounter killerwattKills = new KillCounter();
    @Expose
    public KillCounter molaniskKills = new KillCounter();
    @Expose
    public KillCounter basiliskKills = new KillCounter();
    @Expose
    public KillCounter seaSnakeKills = new KillCounter();
    @Expose
    public KillCounter terrorDogKills = new KillCounter();
    @Expose
    public KillCounter feverSpiderKills = new KillCounter();
    @Expose
    public KillCounter infernalMageKills = new KillCounter();
    @Expose
    public KillCounter brineRatKills = new KillCounter();
    @Expose
    public KillCounter bloodveldKills = new KillCounter();
    @Expose
    public KillCounter fireGiantKills = new KillCounter();
    @Expose
    public KillCounter jellyKills = new KillCounter();
    @Expose
    public KillCounter turothKills = new KillCounter();

    @Expose
    public KillCounter mithrildragKills = new KillCounter();


    @Expose
    public KillCounter blackdragonKills = new KillCounter();


    @Expose
    public KillCounter mutatedZygomiteKills = new KillCounter();
    @Expose
    public KillCounter caveHorrorKills = new KillCounter();
    @Expose
    public KillCounter aberrantSpectreKills = new KillCounter();
    @Expose
    public KillCounter spiritualRangerKills = new KillCounter();
    @Expose
    public KillCounter dustDevilKills = new KillCounter();
    @Expose
    public KillCounter spiritualWarriorKills = new KillCounter();
    @Expose
    public KillCounter kuraskKills = new KillCounter();
    @Expose
    public KillCounter skeletalWyvernKills = new KillCounter();
    @Expose
    public KillCounter gargoyleKills = new KillCounter();
    @Expose
    public KillCounter nechryaelKills = new KillCounter();
    @Expose
    public KillCounter spiritualMageKills = new KillCounter();
    @Expose
    public KillCounter abyssalDemonKills = new KillCounter();
    @Expose
    public KillCounter caveKrakenKills = new KillCounter();
    @Expose
    public KillCounter darkBeastKills = new KillCounter();
    @Expose
    public KillCounter smokeDevilKills = new KillCounter();
    @Expose
    public KillCounter superiorCreatureKills = new KillCounter();
    @Expose
    public KillCounter brutalBlackDragonKills = new KillCounter();
    @Expose
    public KillCounter fossilIslandWyvernsKills = new KillCounter();


    @Expose
    public KillCounter blackDragon = new KillCounter();

    /**
     * Misc kill counters
     */
    @Expose
    public KillCounter demonicGorillaKills = new KillCounter();
    @Expose
    public KillCounter adamantDragonKills = new KillCounter();
    @Expose
    public KillCounter runeDragonKills = new KillCounter();
    @Expose
    public KillCounter jungleDemonKills = new KillCounter();
    @Expose
    public KillCounter battleMageKills = new KillCounter();

    @Expose
    public int crystalChestsOpened;
    @Expose
    public int elvenCrystalChestsOpened;


    public ActivityTimer zulrahTimer;
    @Expose
    public long zulrahBestTime;

    @Expose
    public boolean isInstanced = false;

    /**
     * Staff of the dead
     */
    public TickDelay sotdDelay = new TickDelay();

    /**
     * Rock cake
     */
    public TickDelay rockCakeDelay = new TickDelay();

    /**
     * new Make-X interface last chosen amount
     */
    @Expose
    public int lastMakeXAmount;

    /**
     * Hide donator icons
     */
    @Expose
    public boolean hidePlayerIcon = false;

    /**
     * Pet
     */

    @Expose
    public Pets pet;

    @Expose
    @Getter
    private final Map<Pets, PetData> petStatistics = new HashMap<>();

    public boolean callPet;

    public boolean hidePet;

    public boolean showPet;


    /**
     * Presets
     */

    @Expose
    public PresetCustom[] customPresets = new PresetCustom[PresetCustom.ENTRIES.length];

    @Expose
    public boolean Shift_Drop = false;
    @Expose
    public boolean ESC_Close = false;

    /**
     * Elo rating
     */
    @Expose
    public int pkRating = WildernessRating.DEFAULT_RATING;

    /**
     * PVP Instance position
     */
    @Expose
    public Position pvpInstancePosition;

    /**
     * Loyalty Chest
     */
    @Expose
    public long loyaltyTimer = System.currentTimeMillis();
    @Expose
    public long loyaltySpreeTimer = System.currentTimeMillis();
    @Expose
    public int loyaltyChestReward = 1;
    @Expose
    public int loyaltyChestCount;
    @Expose
    public int loyaltyChestSpree;
    @Expose
    public int highestLoyaltyChestSpree;

    /*
     * first3 stuff
     */
    public TickDelay first3 = new TickDelay();
    @Expose
    public int first3TimeLeft;

    /*
     * Custom Points
     */
    @Expose
    public int PvmPoints;

    /**
     * Store
     */

    @Expose
    public int storeAmountSpent;

    @Expose
    public List<Item> claimedStoreItems;

    /**
     * Resting
     */
    @Expose
    public boolean resting;

    /**
     * Elder chaos druid teleport
     */
    public TickDelay elderChaosDruidTeleport = new TickDelay();

    /**
     * Tournament system attributes
     */
    public boolean tempUseRaidPrayers = false;
    @Expose
    public boolean joinedTournament = false; // Save this in case the server disconnects/restarts during the waiting lobby
    public Position viewingOrbLocation;
    public boolean usingTournamentOrb = false;
    public boolean usingTournamentOrbFromHome = false;
    public int cachedRunePouchTypes;
    public int cachedRunePouchAmounts;
    public boolean tournamentPouch = false;
    public boolean tournamentAugury = false;
    public boolean tournamentRigour = false;
    public boolean tournamentPreserve = false;
    @Expose
    public int tournamentWins = 0;
    @Expose
    public int[] preTournyAttack, preTournyStrength, preTournyDefence, preTournyRanged, preTournyPrayer, preTournyMagic, preTournyHitpoints;

    /**
     * Blood fury effect
     */
    @Expose
    public boolean amuletOfBloodFuryEffect = true;

    /**
     * Login date
     */
    public long sessionStart = System.currentTimeMillis();

    /**
     * Bestiary
     */
    public List<JournalEntry> bestiarySearchResults;

    /**
     * Active valcano
     */
    public int bloodyFragments;

    /**
     * Raids party settings
     */
    @Expose
    public int partyAdvertisementsRemaining = 15;
    public boolean advertisingParty = false;
    public long advertisementStartTick = 0L;
    public Party raidsParty;
    public Party viewingParty;
    @Expose
    public boolean raidsEntranceWarning = false;

    /**
     * Ring of suffering
     */
    @Expose
    public boolean ringOfSufferingEffect = true;

    /**
     * Buy Credits
     */
    public int selectedCreditPackage, selectedPaymentMethod;

    /**
     *
     */
    @Expose
    public long rejuvenationPool = 0;

    /**
     * Title
     */
    @Expose
    public int titleId = -1;
    public Title title;
    @Expose
    public String customTitle;
    @Expose
    public boolean hasCustomTitle;

    /*
     * Summer Event
     */
    @Expose
    public long lastSacrifice;

    /**
     * Intro achievements fields
     */
    @Expose
    public boolean bestiaryIntro = false;
    @Expose
    public int teleportPortalUses = 0;
    @Expose
    public int presetsLoaded = 0;

    /**
     * New con code
     */
    @Expose
    public House house = null;

    public Seat seat;

    /**
     * Boost scrolls
     */
    public TickDelay expBonus = new TickDelay();
    @Expose
    public int expBonusTimeLeft;
    public TickDelay petDropBonus = new TickDelay();
    @Expose
    public int petDropBonusTimeLeft;
    public TickDelay rareDropBonus = new TickDelay();
    @Expose
    public int rareDropBonusTimeLeft;

    /**
     * Wilderness points
     */
    @Expose
    public int wildernessPoints;
    /**
     * Wintertodt Points
     */
    @Expose
    public int wintertodtstorePoints;
    /**
     * Toggle for exp counter to show hit
     */
    @Expose
    public boolean showHitAsExperience = false;

    /**
     * Mystery box incentives
     */
    @Expose
    public boolean firstMysteryBoxReward = true;
    @Expose
    public int guaranteedMysteryBoxLoot = 1;

    /**
     * Equip timer to prevent animations
     */
    public TickDelay recentlyEquipped = new TickDelay();

    /**
     * Nurse special attack refill cooldown
     */
    public TickDelay nurseSpecialRefillCooldown = new TickDelay();

    /**
     * Used when a player has a bounty target to ensure he doesn't switch presets
     */
    public Preset lastPresetUsed;

    /**
     * Edgeville blacklisted users
     */
    @Expose
    public EdgevilleBlacklist[] edgevilleBlacklistedUsers = new EdgevilleBlacklist[EdgevilleBlacklist.ENTRIES.length];

    /**
     * Used to determine if a player is an official dice host or not (set when the player claims the dice bag)
     */
    @Expose
    public boolean diceHost = false;
    @Expose public String lastLoginDate; // would have used a LocalDate object for this but it doesn't serialize properly with the @Expose annotation
    @Expose public int dailyTasksCompleted = 0;
    @Expose
    public int afkPoints;
    @Expose
    public int exchangePoints;
    @Expose public int dailyEasyTask = -1;
    @Expose public int dailyMediumTask = -1;
    @Expose public int dailyHardTask = -1;
    @Expose public int dailyTaskPoints;
    @Expose public boolean hasTask;
    @Expose
    @Getter
    @Setter
    public DailyTask.PossibleTasksEasy currentTaskEasy;
    @Expose
    @Getter
    @Setter
    public DailyTask.PossibleTasksMedium currentTaskMedium;
    @Expose
    @Getter
    @Setter
    public DailyTask.PossibleTasksHard currentTaskHard;
    public boolean dailyEnabled = true;
    public boolean completedDailyTask = false;
    public boolean completedDailyTaskMedium = false;
    public boolean completedDailyTaskHard = false;
    @Expose
    public int dailyTaskDate = 0;
    @Expose
    public int dailyCount;
    @Expose
    public int totalDailyDone;
    @Expose
    public int totalDailyMediumDone;
    @Expose
    public int totalDailyHardDone;
    @Expose
    public int dailyTaskCompletePoints = 0;
    @Expose
    public boolean claimedDailyCache = false;

    /**
     * PvP weapon specials
     */
    public TickDelay vestasSpearSpecial = new TickDelay();
    public TickDelay morrigansAxeSpecial = new TickDelay();

    @Expose
    public boolean PVPAmorNotification = true;

    /**
     * Supply chest wilderness event
     */
    public boolean supplyChestRestricted = false;
    @Expose
    public boolean supplyChestWarning = true;

    /**
     * Blood money key wilderness event
     */
    public boolean bloodyKeyRestricted = false;
    @Expose
    public boolean bloodyKeyWarning = true;

    /**
     * Attribute used to hide free items on the PVP world
     */
    @Expose
    public boolean hideFreeItems = false;

    /**
     * Used for the dragonfire shield special attack
     */
    public boolean dragonfireShieldSpecial = false;
    public TickDelay dragonfireShieldCooldown = new TickDelay();

    @Expose
    public ArrayList<Item> unlockedIronmanItems = new ArrayList<>();

    /**
     * Bone Crusher
     */
    @Expose
    public boolean boneCrusher = true;
    @Expose
    public int boneCrusherChargers = 0;


    /**
     * Auto Casting Storing
     */
    @Expose
    public TargetSpell targetSpellStore = null;
    @Expose
    public int AutoCastStore = 0;
    @Expose
    public boolean AutoCastStorer = true;

    /**
     * Sigils
     */
    @Expose
    public boolean SOTRR;

    @Expose
    public boolean SOTMM;

    @Expose
    public boolean SOFR;

    @Expose
    public boolean SOFC;

    @Expose
    public boolean SOFA;
    /**
     * Ring of wealth attributes for features
     */
    @Expose
    public boolean ROWAutoCollectBloodMoney = false;

    @Expose
    public boolean ROWAutoCollectEther = false;

    @Expose
    public boolean ROWAutoCollectGold = false;

    public boolean insideWildernessAgilityCourse = false;

    public TickDelay presetDelay = new TickDelay();

    public StatType selectedSkillLampSkill;

    /**
     * Risk protection toggle
     */
    @Expose
    public int riskProtectionTier = 10;
    @Expose
    public long riskedBloodMoney = 0;

    public TickDelay riskProtectionExpirationDelay = new TickDelay();

    /**
     * Gambling
     */
    @Getter
    @Setter
    public FlowerPoker flowerPoker;
    @Getter
    @Setter
    protected boolean isInGambleParty = false;
    @Getter
    @Setter
    protected boolean gambleMonitorOpen = false;
    @Expose
    @Getter
    @Setter
    public int gambleRequestCooldown;
    public GambleInterface gambleInterface;

    /**
     * Magic skillcape attributes
     */
    @Expose
    public long mageSkillcapeSpecial = System.currentTimeMillis();
    @Expose
    public int magicSkillcapeUses = 0;

    /**
     * PvM Instances
     */
    public PVMInstance currentInstance;

    public boolean DonatorSlayerDungeons = false;

    public boolean claimedBox = true;
    public boolean easterEgg = false;

    /**
     * Idle timer
     */
    public int idleTicks;
    public boolean isIdle = false;

    public ActivityTimer galvekTimer;
    @Expose
    public long galvekBestTime;

    /**
     * Christmas 2018
     */
    @Expose
    public boolean gotChristmasPresent = false;
    @Expose
    public boolean snowballPeltOption = false;
    @Expose
    public int snowballPoints = 0;
    public TickDelay snowballCooldown = new TickDelay();

    /**
     * Zalcano Throwing
     */
    @Expose
    public boolean TephraThrow = false;
    public TickDelay tephraCooldown = new TickDelay();
    @Expose
    public boolean TephraBoost = false;

    /**
     * Giveaway
     */
    @Expose
    public int giveawayId = -1;
    @Expose
    public int giveawayEntries = 0;

    public TickDelay lastAttackerPVP = new TickDelay();

    public TickDelay specTeleportDelay = new TickDelay();

    @Expose
    public boolean bloodyMerchantTradeWarning = true;

    @Expose
    public boolean broadcastBossEvent = true;
    @Expose
    public boolean broadcastActiveVolcano = true;
    @Expose
    public boolean broadcastHotspot = true;
    @Expose
    public boolean broadcastSupplyChest = true;
    @Expose
    public boolean broadcastBloodyMechant = true;
    @Expose
    public boolean broadcastAnnouncements = true;
    @Expose
    public boolean broadcastTournaments = true;


    @Expose
    public boolean bootsOfLightnessTaken = false;
    @Expose
    public int demonKills = 0;

    /**
     * Nest boxes
     */
    @Expose
    public int nestBoxSeeds;
    @Expose
    public int nestBoxRings;

    public TickDelay edgevilleStallCooldown = new TickDelay();

    @Expose
    public double chaosAltarBoneChance = 50;

    /**
     * Appreciation points
     */
    @Expose
    public int appreciationTicks = 0;
    @Expose
    public int appreciationPoints = 0;

    @Expose
    public double energyUnits = 10000;


    @Expose
    public int refundedCredits = 0;


    public NPC botPreventionNPC;
    public boolean dismissBotPreventionNPC;
    public TickDelay botPreventionJailDelay = new TickDelay();
    public TickDelay botPreventionNpcShoutDelay = new TickDelay();

    @Expose
    public int claimedVotes;

    @Expose
    public int voteMysteryBoxReward;

    @Expose
    public int implingCaught = 0;

    @Expose
    public boolean startedEggHunt = false;

    @Expose
    public boolean preventSkippingCourse = false;

    // Skill cape dailies
    @Expose
    public long lastAgilityCapeBoost;

    @Expose
    public int timesKilledDonatorBoss;
    @Expose
    public long lastTimeKilledDonatorBoss;

    /**
     * Divine potions
     */
    public boolean divineAttackBoostActive = false;
    public boolean divineStrBoostActive = false;
    public boolean divineDefBoostActive = false;
    public boolean divineSuperCmbBoostActive = false;
    public boolean divineMagicBoostActive = false;
    public boolean divineRangingBoostActive = false;

    public boolean overloadBoostActive = false;
    public boolean prayerEnhanceBoostActive = false;

    @Expose
    public boolean obtained50KCVorkathHead = false;

    public PestControlGame pestGame;
    @Expose
    public int pestPoints;
    @Expose
    public int pestNoviceWins;
    @Expose
    public int pestIntermediateWins;
    @Expose
    public int pestVeteranWins;
    public int pestActivityScore;
    public int selectedWidgetId;

    public Tempoross temporossGame;
    @Expose
    public int temporossPoints;
    @Expose
    public int temporossWins;
    public int temporossActivityScore;
    @Expose
    public boolean temporossTether;
    @Expose
    public boolean joinedTempoross;
    @Expose
    public int temporossrewards;
    @Expose
    public long temporosstimer;

    @Expose
    public boolean autoCollectEther = false;
    public int nexDamage;
    public int tobDamage;
    @Expose
    public boolean unlockedGreenSkin;
    @Expose
    public boolean unlockedBlueSkin;
    @Expose
    public boolean unlockedPurpleSkin;
    @Expose
    public boolean unlockedWhiteSkin;
    @Expose
    public boolean unlockedBlackSkin;

    public LastManStandingSession lmsSession;
    public LastManStandingQueue lmsQueue;
    public int lmsSessionKills;
    @Expose
    public int lmsKills;
    @Expose
    public int lmsWins;
    @Expose
    public int lmsGamesPlayed;
    @Expose
    public int lmsRank;
    @Expose
    public boolean canTarget;

    public Tournament tournament;
    public TournamentFightPair tournamentFight;

    @Expose
    public int mysteriousStrangerVarp;
    public boolean rigging = false;

    //    @Expose public Cannon cannon;
    @Expose
    public Position cannonPosition;
    @Expose
    public int cannonBallsLoaded;
    @Expose
    public boolean cannonLost = false;

    public int pkModeTutorialOp;

    @Expose
    public int bossPoints;

    @Expose
    public boolean bountyHunterOverlay = true;

    @Expose
    @Getter
    public TeleportInterface teleports;



    @Expose
    public boolean cerberusMetamorphisis;
    @Expose
    public boolean infernalJadMetamorphisis;
    @Expose
    public boolean abyssalSireMetamorphisis;

    public TeleportCategory teleportCategory;

    @Expose
    public TeleportDestination[] previousTeleportDestinations = new TeleportDestination[2];

    @Expose
    public TeleportDestination homeTeleportDestination;

    public boolean selectingHomeTeleportDestination;

    /**
     * blast furnace variables
     */
    @Getter
    @Setter
    @Expose
    public int blastFurnaceCofferAmount = 0;
    @Getter
    @Setter
    @Expose
    public int blastFurnaceOres = 0;
    @Getter
    @Setter
    @Expose
    public int blastFurnaceCoalAmount;
    @Getter
    @Setter
    @Expose
    public int blastFurnaceBars = 0;

    @Expose
    @Getter
    @Setter
    public int collection_log_current_entry = 0;
    @Expose
    @Getter
    @Setter
    public int collection_log_current_struct = 471;//471-475
    @Expose
    @Getter
    @Setter
    public int collection_log_current_tab = 0;
    @Expose
    @Getter
    @Setter
    public int[] collection_log_current_params = CollectionLog.bossParams;

    @Expose
    public int collectionLogTotalItemsCollected;
    @Expose
    public int SoulWarsSpoilsofWarOpened;
    @Expose
    public int barbAssaultHighLevelGambles;

    @Expose
    public boolean DiscordAuth = false;

    @Expose
    public String DiscordCode = "";

    /**
     * Display Names
     */

    @Expose
    @Getter
    @Setter
    public int availableNameChanges;
    @Expose
    @Getter
    @Setter
    public int totalNameChanges;

    @Expose
    public int braceletSaves = 30;

    @Getter
    @Setter
    public boolean isGaunletLootAvailable = false;

}