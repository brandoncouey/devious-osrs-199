package io.ruin.model.item.containers.collectionlog;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 */
public class CollectionLogTools {

    public static void openToolsMain(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Category",
                new Option("-"),
                new Option("Bosses", () -> openMenuBosses(c)),
                new Option("Raids", () -> openMenuRaids(c)),
                new Option("Clues", () -> openMenuClues(c)),
                new Option("Minigames", () -> openMenuMinigames(c)),
                new Option("Other", () -> openMenuOther(c)),
                new Option("Add all items to log", () -> CollectionLogInfo.collectAll(c, true, false, false)),
                new Option("-")
        );

        return;
    }

    public static void openMenuBosses(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Boss",
                new Option("-"),
                new Option("Abyssal Sire", () -> handleBossEntry(c, 0)),
                new Option("Alchemical Hydra", () -> handleBossEntry(c, 1)),
                new Option("Barrows", () -> handleBossEntry(c, 2)),
                new Option("Bryophyta", () -> handleBossEntry(c, 3)),
                new Option("Callisto", () -> handleBossEntry(c, 4)),
                new Option("Cerberus", () -> handleBossEntry(c, 5)),
                new Option("Chaos Elemental", () -> handleBossEntry(c, 6)),
                new Option("Chaos Fanatic", () -> handleBossEntry(c, 7)),
                new Option("Commander Zilyana", () -> handleBossEntry(c, 8)),
                new Option("Corporeal Beast", () -> handleBossEntry(c, 9)),
                new Option("Crazy Archaeologist", () -> handleBossEntry(c, 10)),
                new Option("Dagannoth Kings", () -> handleBossEntry(c, 11)),
                new Option("The Fight Caves", () -> handleBossEntry(c, 12)),
                new Option("The Gauntlet", () -> handleBossEntry(c, 13)),
                new Option("General Graardor", () -> handleBossEntry(c, 14)),
                new Option("Giant Mole", () -> handleBossEntry(c, 15)),
                new Option("Grotesque Guardians", () -> handleBossEntry(c, 16)),
                new Option("Hespori", () -> handleBossEntry(c, 17)),
                new Option("The Inferno", () -> handleBossEntry(c, 18)),
                new Option("Kalphite Queen", () -> handleBossEntry(c, 19)),
                new Option("King Black Dragon", () -> handleBossEntry(c, 20)),
                new Option("Kraken", () -> handleBossEntry(c, 21)),
                new Option("Kree'arra", () -> handleBossEntry(c, 22)),
                new Option("K'ril Tsutsaroth", () -> handleBossEntry(c, 23)),
                new Option("The Nightmare", () -> handleBossEntry(c, 24)),
                new Option("Obor", () -> handleBossEntry(c, 25)),
                new Option("Sarachnis", () -> handleBossEntry(c, 26)),
                new Option("Scorpia", () -> handleBossEntry(c, 27)),
                new Option("Skotizo", () -> handleBossEntry(c, 28)),
                new Option("Tempoross", () -> handleBossEntry(c, 29)),
                new Option("Thermonuclear Smoke Devil", () -> handleBossEntry(c, 30)),
                new Option("Venenatis", () -> handleBossEntry(c, 31)),
                new Option("Vet'ion", () -> handleBossEntry(c, 32)),
                new Option("Vorkath", () -> handleBossEntry(c, 33)),
                new Option("Wintertodt", () -> handleBossEntry(c, 34)),
                new Option("Zalcano", () -> handleBossEntry(c, 35)),
                new Option("Zulrah", () -> handleBossEntry(c, 36)),
                new Option("-")
        );

        return;
    }

    public static void handleBossEntry(Player c, int entry) {
        switch (entry) {
            case 0://abyssal sire
                OptionScroll.open(c, "<col=8B0000>Abyssal sire",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemAbyssalSire(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemAbyssalSireAndInventory(c)),
                        new Option("Increase kills +1", () -> c.abyssalSireKills.increment(c)),
                        new Option("Increase kills +5", () -> c.abyssalSireKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.abyssalSireKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 1://alchemical hydra
                OptionScroll.open(c, "<col=8B0000>Alchemical Hydra",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemAlchemicalHydra(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemAlchemicalHydraAndInventory(c)),
                        new Option("Increase kills +1", () -> c.alchemicalHydraKills.increment(c)),
                        new Option("Increase kills +5", () -> c.alchemicalHydraKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.alchemicalHydraKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 2://barrows
                OptionScroll.open(c, "<col=8B0000>Barrows",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemBarrows(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemBarrowsAndInventory(c)),
                        new Option("Increase kills +1", () -> c.barrowsChestsLooted.increment(c)),
                        new Option("Increase kills +5", () -> c.barrowsChestsLooted.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.barrowsChestsLooted.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 3://Bryophyta
                OptionScroll.open(c, "<col=8B0000>Bryophyta",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemBryophyta(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemBryophytaAndInventory(c)),
                        new Option("Increase kills +1", () -> c.bryophytaKills.increment(c)),
                        new Option("Increase kills +5", () -> c.bryophytaKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.bryophytaKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 4://callisto
                OptionScroll.open(c, "<col=8B0000>Callisto",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemCallisto(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemCallistoAndInventory(c)),
                        new Option("Increase kills +1", () -> c.callistoKills.increment(c)),
                        new Option("Increase kills +5", () -> c.callistoKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.callistoKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 5://cerberus
                OptionScroll.open(c, "<col=8B0000>Cerberus",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemCerberus(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemCerberusAndInventory(c)),
                        new Option("Increase kills +1", () -> c.cerberusKills.increment(c)),
                        new Option("Increase kills +5", () -> c.cerberusKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.cerberusKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 6://chaos elemental
                OptionScroll.open(c, "<col=8B0000>Chaos ELemental",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemChaosElemental(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemChaosElementalAndInventory(c)),
                        new Option("Increase kills +1", () -> c.chaosElementalKills.increment(c)),
                        new Option("Increase kills +5", () -> c.chaosElementalKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.chaosElementalKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 7://chaos fanatic
                OptionScroll.open(c, "<col=8B0000>Chaos Fanatic",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemChaosFanatic(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemChaosFanaticAndInventory(c)),
                        new Option("Increase kills +1", () -> c.chaosFanaticKills.increment(c)),
                        new Option("Increase kills +5", () -> c.chaosFanaticKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.chaosFanaticKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 8://commander zilyana
                OptionScroll.open(c, "<col=8B0000>Commander Zilyana",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemCommanderZilyana(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemCommanderZilanaAndInventory(c)),
                        new Option("Increase kills +1", () -> c.commanderZilyanaKills.increment(c)),
                        new Option("Increase kills +5", () -> c.commanderZilyanaKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.commanderZilyanaKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 9://corporeal beast
                OptionScroll.open(c, "<col=8B0000>Coproreal beast",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemCorp(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemCorpAndInventory(c)),
                        new Option("Increase kills +1", () -> c.corporealBeastKills.increment(c)),
                        new Option("Increase kills +5", () -> c.corporealBeastKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.corporealBeastKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 10://crazy archeaologist
                OptionScroll.open(c, "<col=8B0000>Crazy Archaeologist",
                        new Option("-"),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemCrazyArch(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemCrazyArchAndInventory(c)),
                        new Option("Increase kills +1", () -> c.crazyArchaeologistKills.increment(c)),
                        new Option("Increase kills +5", () -> c.crazyArchaeologistKills.addToKillsAndStreak(c, 5)),
                        new Option("Increase kills +10", () -> c.crazyArchaeologistKills.addToKillsAndStreak(c, 10)),
                        new Option("-")
                );
                return;
            case 11:

                return;
            case 12:

                return;
            case 13:

                return;
            case 14:

                return;
            case 15:

                return;
            case 16:

                return;
            case 17:

                return;
            case 18:

                return;
            case 19:

                return;
            case 20:

                return;
            case 21:

                return;
            case 22:

                return;
            case 23:

                return;
            case 24:

                return;
            case 25:

                return;
            case 26:

                return;
            case 27:

                return;
            case 28:

                return;
            case 29:

                return;
            case 30:

                return;
            case 31:

                return;
            case 32:

                return;
            case 33:

                return;
            case 34:

                return;
            case 35:

                return;
            case 36:

                return;

        }
    }


    public static void openMenuRaids(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Raid",
                new Option(""),
                new Option("Chambers of Xeric", () -> handleRaidEntry(c, 0)),
                new Option("Theatre of Blood", () -> handleRaidEntry(c, 1))
        );

        return;
    }

    public static void handleRaidEntry(Player c, int entry) {
        switch (entry) {
            case 0://cox
                OptionScroll.open(c, "<col=8B0000>Chambers of Xeric",
                        new Option(""),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemCoX(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemCoXAndInventory(c)),
                        new Option("Increase kills +1", () -> c.chambersofXericKills.increment(c)),
                        new Option("Increase kills +5", () -> c.chambersofXericKills.addToKillsAndStreakPlus5(c)),
                        new Option("Increase kills +10", () -> c.chambersofXericKills.addToKillsAndStreakPlus10(c))
                );
                return;
            case 1://tob
                OptionScroll.open(c, "<col=8B0000>Theatre of Blood",
                        new Option(""),
                        new Option("Fill Log x 1", () -> CollectionLog.addLogItemToB(c)),
                        new Option("Fill Log and Inventory", () -> CollectionLog.addLogItemToBAndInventory(c)),
                        new Option("Increase kills +1", () -> c.theatreOfBloodKills.increment(c)),
                        new Option("Increase kills +5", () -> c.theatreOfBloodKills.addToKillsAndStreakPlus5(c)),
                        new Option("Increase kills +10", () -> c.theatreOfBloodKills.addToKillsAndStreakPlus10(c))
                );
                return;
        }
    }

    public static void openMenuClues(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Clue",
                new Option(""),
                new Option("Beginner", () -> handleCluesEntry(c, 0)),
                new Option("Easy", () -> handleCluesEntry(c, 1)),
                new Option("Medium", () -> handleCluesEntry(c, 2)),
                new Option("Hard", () -> handleCluesEntry(c, 3)),
                new Option("Elite", () -> handleCluesEntry(c, 4)),
                new Option("Master", () -> handleCluesEntry(c, 5)),
                new Option("Hard (rare)", () -> handleCluesEntry(c, 6)),
                new Option("Elite (rare)", () -> handleCluesEntry(c, 7)),
                new Option("Master (rare)", () -> handleCluesEntry(c, 8)),
                new Option("Shared", () -> handleCluesEntry(c, 9))
        );

        return;
    }

    public static void openMenuMinigames(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Minigame",
                new Option(""),
                new Option("Barbarian Assault", () -> handleMinigamesEntry(c, 0)),
                new Option("Brimhaven Agility Arena", () -> handleMinigamesEntry(c, 1)),
                new Option("Castle Wars", () -> handleMinigamesEntry(c, 2)),
                new Option("Fishing Trawler", () -> handleMinigamesEntry(c, 3)),
                new Option("Gnome Restaurant", () -> handleMinigamesEntry(c, 4)),
                new Option("Hallowed Sepulchre", () -> handleMinigamesEntry(c, 5)),
                new Option("Last Man Standing", () -> handleMinigamesEntry(c, 6)),
                new Option("Magic Training Arena", () -> handleMinigamesEntry(c, 7)),
                new Option("Mahogany Homes", () -> handleMinigamesEntry(c, 8)),
                new Option("Pest Control", () -> handleMinigamesEntry(c, 9)),
                new Option("Rogues Den", () -> handleMinigamesEntry(c, 10)),
                new Option("Shades of Mort'ton", () -> handleMinigamesEntry(c, 11)),
                new Option("Soul Wars", () -> handleMinigamesEntry(c, 12)),
                new Option("Temple Trekking", () -> handleMinigamesEntry(c, 13)),
                new Option("Tithe Farm", () -> handleMinigamesEntry(c, 14)),
                new Option("Trouble Brewing", () -> handleMinigamesEntry(c, 15)),
                new Option("Volcanic Mine", () -> handleMinigamesEntry(c, 16))
        );

        return;
    }

    public static void openMenuOther(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select an option",
                new Option(""),
                new Option("Aerial Fishing", () -> handleOtherEntry(c, 0)),
                new Option("All Pets", () -> handleOtherEntry(c, 1)),
                new Option("Camdozaal", () -> handleOtherEntry(c, 2)),
                new Option("Champion's Challenge", () -> handleOtherEntry(c, 3)),
                new Option("Chaos Druids", () -> handleOtherEntry(c, 4)),
                new Option("Chompy Bird Hunting", () -> handleOtherEntry(c, 5)),
                new Option("Creature Creation", () -> handleOtherEntry(c, 6)),
                new Option("Cyclopes", () -> handleOtherEntry(c, 7)),
                new Option("Fossil Island Notes", () -> handleOtherEntry(c, 8)),
                new Option("Glough's Experiments", () -> handleOtherEntry(c, 9)),
                new Option("Monkey Backpacks", () -> handleOtherEntry(c, 10)),
                new Option("Motherlode Mine", () -> handleOtherEntry(c, 11)),
                new Option("My Notes", () -> handleOtherEntry(c, 12)),
                new Option("Random Events", () -> handleOtherEntry(c, 13)),
                new Option("Revenants", () -> handleOtherEntry(c, 14)),
                new Option("Rooftop Agility", () -> handleOtherEntry(c, 15)),
                new Option("Shayzien Armour", () -> handleOtherEntry(c, 16)),
                new Option("Shooting Stars", () -> handleOtherEntry(c, 17)),
                new Option("Skilling Pets", () -> handleOtherEntry(c, 18)),
                new Option("Slayer", () -> handleOtherEntry(c, 19)),
                new Option("TzHaar", () -> handleOtherEntry(c, 20)),
                new Option("Miscellaneous", () -> handleOtherEntry(c, 21))
        );

        return;
    }

    public static void handleCluesEntry(Player c, int entry) {
        switch (entry) {
            case 0:
                return;
            case 1:
                return;
            case 2:
                return;
            case 3:
                return;
            case 4:
                return;
            case 5:
                return;
            case 6:
                return;
            case 7:
                return;
            case 8:
                return;
            case 9:
                return;

        }
    }

    public static void handleMinigamesEntry(Player c, int entry) {
        switch (entry) {
            case 0:
                return;
            case 1:
                return;
            case 2:
                return;
            case 3:
                return;
            case 4:
                return;
            case 5:
                return;
            case 6:
                return;
            case 7:
                return;
            case 8:
                return;
            case 9:
                return;
            case 10:
                return;
            case 11:
                return;
            case 12:
                return;
            case 13:
                return;
            case 14:
                return;
            case 15:
                return;
            case 16:
                return;
        }
    }

    public static void handleOtherEntry(Player c, int entry) {
        switch (entry) {
            case 0:
                return;
            case 1:
                return;
            case 2:
                return;
            case 3:
                return;
            case 4:
                return;
            case 5:
                return;
            case 6:
                return;
            case 7:
                return;
            case 8:
                return;
            case 9:
                return;
            case 10:
                return;
            case 11:
                return;
            case 12:
                return;
            case 13:
                return;
            case 14:
                return;
            case 15:
                return;
            case 16:
                return;
            case 17:
                return;
            case 18:
                return;
            case 19:
                return;
            case 20:
                return;
            case 21:
                return;

        }
    }

    static {

    }

}
