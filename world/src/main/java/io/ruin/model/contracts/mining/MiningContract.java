package io.ruin.model.contracts.mining;

import io.ruin.api.utils.Random;
import io.ruin.model.contracts.SkillingContract;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 * @since 11/24/2021
 */
public class MiningContract {

    public static void displayMiningContract(Player player) {
        OptionScroll.open(player, "<col=8B0000>Select a Mining Contract",
                new Option("-"),
                new Option("Iron", () -> assignMiningContract(player, MiningContractType.IRON)),
                new Option("Coal", () -> assignMiningContract(player, MiningContractType.COAL)),
                new Option("Mithril", () -> assignMiningContract(player, MiningContractType.MITHRIL)),
                new Option("Adamant", () -> assignMiningContract(player, MiningContractType.ADAMANT)),
                new Option("Rune", () -> assignMiningContract(player, MiningContractType.RUNE)),
                new Option("-"),
                new Option("Reset Contract", () -> assignMiningContract(player, MiningContractType.NONE)),
                new Option("Go back", () -> SkillingContract.open(player))
        );
    }

    public static void assignMiningContract(Player player, MiningContractType contractType) {
        switch (contractType) {
            case NONE:
                player.hasAcceptedMiningContract = false;
                player.miningContractAmount = 0;
                player.miningContractType = MiningContractType.NONE;
                player.sendMessage("You have reset your mining contract.");
                break;
            case IRON:
                player.hasAcceptedMiningContract = true;
                player.miningContractAmount = Random.get(50, 200);
                player.miningContractType = MiningContractType.IRON;
                player.sendMessage("You have been assigned to mine " + player.miningContractAmount + " Iron ores.");
                break;
            case COAL:
                player.hasAcceptedMiningContract = true;
                player.miningContractAmount = Random.get(50, 200);
                player.miningContractType = MiningContractType.COAL;
                player.sendMessage("You have been assigned to mine " + player.miningContractAmount + " Coal.");
                break;
            case MITHRIL:
                player.hasAcceptedMiningContract = true;
                player.miningContractAmount = Random.get(50, 200);
                player.miningContractType = MiningContractType.MITHRIL;
                player.sendMessage("You have been assigned to mine " + player.miningContractAmount + " Mithril ores.");
                break;
            case ADAMANT:
                player.hasAcceptedMiningContract = true;
                player.miningContractAmount = Random.get(50, 200);
                player.miningContractType = MiningContractType.ADAMANT;
                player.sendMessage("You have been assigned to mine " + player.miningContractAmount + " Adamant ores.");
                break;
            case RUNE:
                player.hasAcceptedMiningContract = true;
                player.miningContractAmount = Random.get(50, 100);
                player.miningContractType = MiningContractType.RUNE;
                player.sendMessage("You have been assigned to mine " + player.miningContractAmount + " Runite ores.");
                break;
        }
    }

    public static void advanceMiningContract(Player player) {
        if (player.hasAcceptedMiningContract == true) {
            player.miningContractAmount -= 1;
            return;
        }
        if (player.miningContractAmount == 0) {
            player.hasCompletedMiningContract = true;
            player.sendMessage("Your contract has been completed go get a new one.");
            sendTaskCompleteOverlay(player);
            return;
        }
    }

    public static void resetMiningContractStatus(Player player) {
        player.hasAcceptedMiningContract = false;
        player.miningContractAmount = 0;
        player.miningContractType = MiningContractType.NONE;
        player.sendMessage("You have reset your mining contract.");
    }

    public static void sendTaskCompleteOverlay(Player player) {
        player.startEvent(event -> {
            player.openInterface(InterfaceType.MAIN, 660);
            player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Mining Contract Completed", "Go turn it in for a reward.");
            event.delay(2);
            player.closeInterface(InterfaceType.MAIN);
        });
    }

}
