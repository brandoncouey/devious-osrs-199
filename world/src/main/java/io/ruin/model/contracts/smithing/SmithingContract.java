package io.ruin.model.contracts.smithing;

import io.ruin.api.utils.Random;
import io.ruin.model.contracts.SkillingContract;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 * @since 11/24/2021
 */
public class SmithingContract {

    public static void displaySmithingContract(Player player) {
        OptionScroll.open(player, "<col=8B0000>Select a Smithing Contract",
                new Option("-"),
                new Option("Bronze", () -> assignSmithingContract(player, SmithingContractType.BRONZE)),
                new Option("Iron", () -> assignSmithingContract(player, SmithingContractType.IRON)),
                new Option("Steel", () -> assignSmithingContract(player, SmithingContractType.STEEL)),
                new Option("Mithril", () -> assignSmithingContract(player, SmithingContractType.MITHIRIL)),
                new Option("Adamant", () -> assignSmithingContract(player, SmithingContractType.ADAMANT)),
                new Option("Rune", () -> assignSmithingContract(player, SmithingContractType.RUNITE)),
                new Option("-"),
                new Option("Reset Contract", () -> assignSmithingContract(player, SmithingContractType.NONE)),
                new Option("Go back", () -> SkillingContract.open(player))
        );
    }

    public static void assignSmithingContract(Player player, SmithingContractType contractType) {
        switch (contractType) {
            case NONE:
                player.hasAcceptedSmithingContract = false;
                player.smithingContractAmount = 0;
                player.smithingContractType = SmithingContractType.NONE;
                player.sendMessage("You have reset your smithing contract.");
                break;
            case BRONZE:
                player.hasAcceptedSmithingContract = true;
                player.smithingContractAmount = Random.get(50, 100);
                player.smithingContractType = SmithingContractType.BRONZE;
                player.sendMessage("You have been assigned to smelt " + player.smithingContractAmount + " bronze bars.");
                break;
            case IRON:
                player.hasAcceptedSmithingContract = true;
                player.smithingContractAmount = Random.get(50, 100);
                player.smithingContractType = SmithingContractType.IRON;
                player.sendMessage("You have been assigned to smelt " + player.smithingContractAmount + " iron bars.");
                break;
            case STEEL:
                player.hasAcceptedSmithingContract = true;
                player.smithingContractAmount = Random.get(50, 100);
                player.smithingContractType = SmithingContractType.STEEL;
                player.sendMessage("You have been assigned to smelt " + player.smithingContractAmount + " steel bars.");
                break;
            case MITHIRIL:
                player.hasAcceptedSmithingContract = true;
                player.smithingContractAmount = Random.get(50, 100);
                player.smithingContractType = SmithingContractType.MITHIRIL;
                player.sendMessage("You have been assigned to smelt " + player.smithingContractAmount + " mithril bars.");
                break;
            case ADAMANT:
                player.hasAcceptedSmithingContract = true;
                player.smithingContractAmount = Random.get(50, 100);
                player.smithingContractType = SmithingContractType.ADAMANT;
                player.sendMessage("You have been assigned to smelt " + player.smithingContractAmount + " adamant bars.");
                break;
            case RUNITE:
                player.hasAcceptedSmithingContract = true;
                player.smithingContractAmount = Random.get(50, 100);
                player.smithingContractType = SmithingContractType.RUNITE;
                player.sendMessage("You have been assigned to smelt " + player.smithingContractAmount + " rune bars.");
                break;
        }
    }

    public static void advanceSmithingContract(Player player) {
        if (player.hasAcceptedSmithingContract == true) {
            player.smithingContractAmount -= 1;
            return;
        }
        if (player.smithingContractAmount == 0) {
            player.sendMessage("Your contract has been completed go get a new one.");
            return;
        }
    }

    public static void resetSmithingContractStatus(Player player) {
        player.hasAcceptedSmithingContract = false;
        player.smithingContractAmount = 0;
        player.sendMessage("You have reset your smithing contract.");
    }

    public static void rewardPlayerForContractCompletion(Player player) {

    }

}
