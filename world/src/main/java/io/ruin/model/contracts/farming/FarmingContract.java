package io.ruin.model.contracts.farming;

import io.ruin.api.utils.Random;
import io.ruin.model.contracts.SkillingContract;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

/**
 * @author Greco
 * @since 11/24/2021
 */
public class FarmingContract {

    /* displays the farming contract menu */
    public static void displayFarmingContracts(Player player) {
        OptionScroll.open(player, "<col=8B0000>Select a Farming Contract Tier",
                new Option("-"),
                new Option("Easy Contract", () -> displayFarmingContractEasyTier(player)),
                new Option("Medium Contract", () -> displayFarmingContractMediumTier(player)),
                new Option("Hard Contract", () -> displayFarmingContractHardTier(player)),
                new Option("-"),
                new Option("Reset Contract", () -> assignFarmingContractByTier(player, FarmingContractTier.NONE, FarmingContractType.NONE)),
                new Option("Go back", () -> SkillingContract.open(player))
        );
    }

    /* handles the easy farming contracts menu */
    public static void displayFarmingContractEasyTier(Player player) {
        if (!player.getStats().check(StatType.Farming, 45)) {
            player.sendMessage("You need a Farming level of 45 to get an easy farming contract.");
            return;
        }
        OptionScroll.open(player, "<col=8B0000>Select an Easy Farming Contract",
                new Option("-"),
                new Option("Potatoes", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.POTATOES)),
                new Option("Marigold", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.MARIGOLD)),
                new Option("Onions", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.ONIONS)),
                new Option("Cabbages", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.CABBAGES)),
                new Option("Redberries", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.REDBERRIES)),
                new Option("Rosemary", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.ROSEMARY)),
                new Option("Tomatoes", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.TOMATOES)),
                new Option("Sweetcorn", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.SWEETCORN)),
                new Option("Cadavaberries", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.CADAVABERRIES)),
                new Option("Nasturtium", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.NASTIRTIUM)),
                new Option("Woad", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.WOAD)),
                new Option("Limpwurt", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.LIMPWURT)),
                new Option("Strawberries", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.STRAWBERRIES)),
                new Option("Dwellberries", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.DWELLBERRIES)),
                new Option("Jangerberries", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.JANGERBERRIES)),
                new Option("Cactus", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.CACTUS)),
                new Option("Guam", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.GUAM)),
                new Option("Marrentill", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.MARRENTILL)),
                new Option("Oak Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.OAK_TREE)),
                new Option("Tarromin", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.TARROMIN)),
                new Option("Harralander", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.HARRALANDER)),
                new Option("Willow Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.WILLOW_TREE)),
                new Option("Ranarr", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.RANARR)),
                new Option("Toadflax", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.TOADFLAX)),
                new Option("Irit", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.IRIT)),
                new Option("Maple Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.MAPLE_TREE)),
                new Option("Avantoe", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.AVANTOE)),
                new Option("Apple Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.APPLE_TREE)),
                new Option("Banana Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.BANANA_TREE)),
                new Option("Orange Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.ORANGE_TREE)),
                new Option("Curry Tree", () -> assignFarmingContractByTier(player, FarmingContractTier.EASY, FarmingContractType.CURRY_TREE)),
                new Option("-"),
                new Option("Reset Contract", () -> assignFarmingContractByTier(player, FarmingContractTier.NONE, FarmingContractType.NONE)),
                new Option("Go back", () -> displayFarmingContracts(player))
        );
    }

    /* handles the medium farming contracts menu */
    public static void displayFarmingContractMediumTier(Player player) {
        if (!player.getStats().check(StatType.Farming, 65)) {
            player.sendMessage("You need a Farming level of 65 to get a medium farming contract.");
            return;
        }
        OptionScroll.open(player, "<col=8B0000>Select a Medium Farming Contract",
                new Option("-"),
                new Option("Strawberries", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.STRAWBERRIES)),
                new Option("Irit", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.IRIT)),
                new Option("Maple tree", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.MAPLE_TREE)),
                new Option("Watermelons", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.WATERMELONS)),
                new Option("Jangerberries", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.JANGERBERRIES)),
                new Option("Avantoe", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.AVANTOE)),
                new Option("Cactus", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.CACTUS)),
                new Option("Kwuarm", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.KWUARM)),
                new Option("White Lily", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.WHITE_LILY)),
                new Option("Whiteberries", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.WHITEBERRIES)),
                new Option("Yew tree", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.YEW_TREE)),
                new Option("Snape grass", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.SNAPE_GRASS)),
                new Option("Snapdragon", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.SNAPDRAGON)),
                new Option("Potato cactus", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.POTATO_CACTUS)),
                new Option("Cadantine", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.CADANTINE)),
                new Option("Poison ivy", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.POISON_IVY)),
                new Option("Lantadyme", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.LANTADYME)),
                new Option("Magic tree", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.MAGIC_TREE)),
                new Option("Curry tree", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.CURRY_TREE)),
                new Option("Pineapple plant", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.PINEAPPLE_PLANT)),
                new Option("Papaya tree", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.PAPAYA_PLANT)),
                new Option("Palm tree", () -> assignFarmingContractByTier(player, FarmingContractTier.MEDIUM, FarmingContractType.PALM_TREE)),
                new Option("-"),
                new Option("Reset Contract", () -> assignFarmingContractByTier(player, FarmingContractTier.NONE, FarmingContractType.NONE)),
                new Option("Go back", () -> displayFarmingContracts(player))
        );
    }

    /* handles the hard farming contracts menu */
    public static void displayFarmingContractHardTier(Player player) {
        if (!player.getStats().check(StatType.Farming, 85)) {
            player.sendMessage("You need a Farming level of 85 to get a hard farming contract.");
            return;
        }
        OptionScroll.open(player, "<col=8B0000>Select a Hard Farming Contract",
                new Option("-"),
                new Option("Maple tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.MAPLE_TREE)),
                new Option("Watermelons", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.WATERMELONS)),
                new Option("White lily", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.WHITE_LILY)),
                new Option("Whiteberries", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.WHITEBERRIES)),
                new Option("Yew tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.YEW_TREE)),
                new Option("Snape grass", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.SNAPE_GRASS)),
                new Option("Snapdragon", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.SNAPDRAGON)),
                new Option("Potato cactus", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.POTATO_CACTUS)),
                new Option("Cadantine", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.CADANTINE)),
                new Option("Palm tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.PALM_TREE)),
                new Option("Poison ivy", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.POISON_IVY)),
                new Option("Lantadyme", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.LANTADYME)),
                new Option("Magic tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.MAGIC_TREE)),
                new Option("Dwarf weed", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.DWARF_WEED)),
                new Option("Dragonfruit tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.DRAGONFRUIT_TREE)),
                new Option("Celastrus tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.CELASTRUS_TREE)),
                new Option("Torstol", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.TORSTOL)),
                new Option("Redwood tree", () -> assignFarmingContractByTier(player, FarmingContractTier.HARD, FarmingContractType.REDWOOD_TREE)),
                new Option("-"),
                new Option("Reset Contract", () -> assignFarmingContractByTier(player, FarmingContractTier.NONE, FarmingContractType.NONE)),
                new Option("Go back", () -> displayFarmingContracts(player))
        );
    }

    /* assigns farming contract by tier and type */
    public static void assignFarmingContractByTier(Player player, FarmingContractTier contractTier, FarmingContractType contractType) {
        switch (contractTier) {
            case NONE:
                switch (contractType) {
                    case NONE:
                        player.hasAcceptedFarmingContract = false;
                        player.farmingContractAmount = 0;
                        player.farmingContractTier = FarmingContractTier.NONE;
                        player.farmingContractType = FarmingContractType.NONE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have reset your farming contract.");
                        break;
                }
                break;
            case EASY:
                switch (contractType) {
                    case POTATOES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(5, 30);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.POTATOES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " potatoes.");
                        break;
                    case MARIGOLD:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.MARIGOLD;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " marigolds.");
                        break;
                    case ONIONS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(5, 20);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.ONIONS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " onions.");
                        break;
                    case CABBAGES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(5, 20);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.CABBAGES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " cabbages.");
                        break;
                    case REDBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 6);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.REDBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " redberries.");
                        break;
                    case ROSEMARY:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 5);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.ROSEMARY;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " rosemary.");
                        break;
                    case TOMATOES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 20);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.TOMATOES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " tomatoes.");
                        break;
                    case SWEETCORN:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(5, 30);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.SWEETCORN;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " sweetcorn.");
                        break;
                    case CADAVABERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 8);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.CADAVABERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " cadavaberries.");
                        break;
                    case NASTIRTIUM:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.NASTIRTIUM;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " nastirtium.");
                        break;
                    case WOAD:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.WOAD;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " woad.");
                        break;
                    case LIMPWURT:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.LIMPWURT;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " limpwurt roots.");
                        break;
                    case STRAWBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(5, 30);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.STRAWBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " strawberries.");
                        break;
                    case DWELLBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.DWELLBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " dwellberries.");
                        break;
                    case JANGERBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.JANGERBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " jangerberries.");
                        break;
                    case CACTUS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 6);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.CACTUS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " cactus spines.");
                        break;
                    case GUAM:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.GUAM;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " guam.");
                        break;
                    case MARRENTILL:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.MARRENTILL;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " marrentill.");
                        break;
                    case OAK_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 3);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.OAK_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " oak tree.");
                        break;
                    case TARROMIN:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.TARROMIN;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " tarromin.");
                        break;
                    case HARRALANDER:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.HARRALANDER;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " harralander.");
                        break;
                    case WILLOW_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.WILLOW_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " willow trees.");
                        break;
                    case RANARR:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.RANARR;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " ranarr.");
                        break;
                    case TOADFLAX:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.TOADFLAX;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " toadflax.");
                        break;
                    case IRIT:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.IRIT;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " irit.");
                        break;
                    case MAPLE_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.MAPLE_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " maple trees.");
                        break;
                    case AVANTOE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.AVANTOE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " avantoes.");
                        break;
                    case APPLE_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.APPLE_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " apple trees.");
                        break;
                    case BANANA_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.BANANA_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " banana trees.");
                        break;
                    case ORANGE_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.ORANGE_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " orange trees.");
                        break;
                    case CURRY_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 3);
                        player.farmingContractTier = FarmingContractTier.EASY;
                        player.farmingContractType = FarmingContractType.CURRY_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " curry trees.");
                        break;
                }
                break;
            case MEDIUM:
                switch (contractType) {
                    case STRAWBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 12);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.STRAWBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " strawberries.");
                        break;
                    case IRIT:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.IRIT;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " irit.");
                        break;
                    case MAPLE_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.MAPLE_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " maple trees.");
                        break;
                    case WATERMELONS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 20);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.WATERMELONS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " watermelons.");
                        break;
                    case JANGERBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 3);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.JANGERBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " jangerberries.");
                        break;
                    case AVANTOE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 15);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.AVANTOE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " avantoes.");
                        break;
                    case CACTUS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 6);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.CACTUS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " cactus spines.");
                        break;
                    case KWUARM:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 12);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.KWUARM;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " kwuarms.");
                        break;
                    case WHITE_LILY:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 5);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.WHITE_LILY;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " white lily.");
                        break;
                    case WHITEBERRIES:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 6);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.WHITEBERRIES;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " whiteberries.");
                        break;
                    case YEW_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.YEW_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " yew trees.");
                        break;
                    case SNAPE_GRASS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 10);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.SNAPE_GRASS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " snape grass.");
                        break;
                    case SNAPDRAGON:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 12);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.SNAPDRAGON;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " snapdragons.");
                        break;
                    case POTATO_CACTUS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.POTATO_CACTUS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " potato cactus.");
                        break;
                    case CADANTINE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 12);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.CADANTINE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " cadentine.");
                        break;
                    case POISON_IVY:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 6);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.POISON_IVY;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " poison ivy.");
                        break;
                    case LANTADYME:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 12);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.LANTADYME;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " lantadyme.");
                        break;
                    case MAGIC_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.MAGIC_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " magic trees.");
                        break;
                    case CURRY_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.CURRY_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " curry trees.");
                        break;
                    case PINEAPPLE_PLANT:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.PINEAPPLE_PLANT;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " pineapple plants.");
                        break;
                    case PAPAYA_PLANT:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.PAPAYA_PLANT;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " papaya trees.");
                        break;
                    case PALM_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.MEDIUM;
                        player.farmingContractType = FarmingContractType.PALM_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " palm trees.");
                        break;
                }
                break;
            case HARD:
                switch (contractType) {
                    case MAPLE_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.MAPLE_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " maple trees.");
                        break;
                    case WATERMELONS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(5, 20);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.WATERMELONS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + "  watermelons.");
                        break;
                    case WHITE_LILY:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 5);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.WHITE_LILY;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " white lily.");
                        break;
                    case YEW_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.YEW_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " yew trees.");
                        break;
                    case SNAPE_GRASS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.SNAPE_GRASS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " snape grass.");
                        break;
                    case SNAPDRAGON:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.SNAPDRAGON;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " snapdragons.");
                        break;
                    case POTATO_CACTUS:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(3, 10);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.POTATO_CACTUS;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " potato cactus.");
                        break;
                    case CADANTINE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 10);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.CADANTINE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " cadentines.");
                        break;
                    case PALM_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.PALM_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " palm trees.");
                        break;
                    case POISON_IVY:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.POISON_IVY;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " poison ivy.");
                        break;
                    case LANTADYME:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 15);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.LANTADYME;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " lantadymes.");
                        break;
                    case MAGIC_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 4);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.MAGIC_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " magic trees.");
                        break;
                    case DWARF_WEED:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(2, 12);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.DWARF_WEED;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " dwarf weeds.");
                        break;
                    case DRAGONFRUIT_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 3);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.DRAGONFRUIT_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " dragonfruit trees.");
                        break;
                    case CELASTRUS_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 3);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.CELASTRUS_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " celastrus trees.");
                        break;
                    case TORSTOL:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 12);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.TORSTOL;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " torstols.");
                        break;
                    case REDWOOD_TREE:
                        player.hasAcceptedFarmingContract = true;
                        player.farmingContractAmount = Random.get(1, 3);
                        player.farmingContractTier = FarmingContractTier.HARD;
                        player.farmingContractType = FarmingContractType.REDWOOD_TREE;
                        player.hasCompletedFarmingContract = false;
                        player.sendMessage("You have been assigned to harvest " + player.farmingContractAmount + " redwood trees.");
                        break;
                }
                break;
        }
    }

    /* advances the farming contract */
    public static void advanceFarmingContract(Player player) {
        if (player.hasAcceptedFarmingContract == true) {
            player.farmingContractAmount -= 1;
            return;
        }
        if (player.farmingContractAmount == 0) {
            player.hasCompletedFarmingContract = true;
            player.sendMessage("Your contract has been completed go get a new one.");
            sendTaskCompleteOverlay(player);
            return;
        }
    }

    /* resets the state of the farming contract to 0 */
    public static void resetFarmingContractStatus(Player player) {
        player.hasAcceptedFarmingContract = false;
        player.farmingContractAmount = 0;
        player.farmingContractTier = FarmingContractTier.NONE;
        player.farmingContractType = FarmingContractType.NONE;
        player.hasCompletedFarmingContract = false;
        player.sendMessage("You have reset your farming contract.");
    }

    /* rewards the player for completing contract */
    public static void rewardPlayerForContractCompletion(Player player, FarmingContractTier contractTier, int itemId, int amount) {
        switch (contractTier) {
            case EASY:
                if (player.hasCompletedFarmingContract == true) {
                    player.getInventory().add(itemId, amount);
                    player.sendMessage("You have completed an easy farming contract, here is your reward.");
                }
                break;
            case MEDIUM:
                if (player.hasCompletedFarmingContract == true) {
                    player.getInventory().add(itemId, amount);
                    player.sendMessage("You have completed a medium farming contract, here is your reward.");
                }
                break;
            case HARD:
                if (player.hasCompletedFarmingContract == true) {
                    player.getInventory().add(itemId, amount);
                    player.sendMessage("You have completed a hard farming contract, here is your reward.");
                }
                break;
        }
    }

    public static void sendTaskCompleteOverlay(Player player) {
        if (player.getFarmingContractTier() == FarmingContractTier.EASY) {
            player.startEvent(event -> {
                player.openInterface(InterfaceType.MAIN, 660);
                player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Easy Farming Contract Completed", "Go turn it in for a reward.");
                event.delay(2);
                player.closeInterface(InterfaceType.MAIN);
            });
        } else if (player.getFarmingContractTier() == FarmingContractTier.MEDIUM) {
            player.startEvent(event -> {
                player.openInterface(InterfaceType.MAIN, 660);
                player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Medium Farming Contract Completed", "Go turn it in for a reward.");
                event.delay(2);
                player.closeInterface(InterfaceType.MAIN);
            });
        } else if (player.getFarmingContractTier() == FarmingContractTier.HARD) {
            player.startEvent(event -> {
                player.openInterface(InterfaceType.MAIN, 660);
                player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Hard Farming Contract Completed", "Go turn it in for a reward.");
                event.delay(2);
                player.closeInterface(InterfaceType.MAIN);
            });
        } else {
            player.startEvent(event -> {
                player.openInterface(InterfaceType.MAIN, 660);
                player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Farming Contract Completed", "Go turn it in for a reward.");
                event.delay(2);
                player.closeInterface(InterfaceType.MAIN);
            });
        }
    }

}
