package io.ruin.model.contracts.fishing;

import io.ruin.api.utils.Random;
import io.ruin.model.contracts.SkillingContract;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 * @since 03/30/2021
 */
public class FishingContract {

    /* display fishing contract menu */
    public static void displayFishingContract(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Woodcutting Contract",
                new Option("-"),
                new Option("Lobster", () -> assignFishingContract(c, FishingContractType.LOBSTER)),
                new Option("Swordfish", () -> assignFishingContract(c, FishingContractType.SWORDFISH)),
                new Option("Shark", () -> assignFishingContract(c, FishingContractType.SHARK)),
                new Option("Anglerfish", () -> assignFishingContract(c, FishingContractType.ANGLERFISH)),
                new Option("-"),
                new Option("Reset Contract", () -> assignFishingContract(c, FishingContractType.NONE)),
                new Option("Go back", () -> SkillingContract.open(c))
        );
        return;

    }

    /* assigns the fishing contract */
    public static void assignFishingContract(Player c, FishingContractType contractType) {
        switch (contractType) {
            case NONE:
                c.hasAcceptedFishingContract = false;
                c.fishingContractAmount = 0;
                c.fishingContractType = FishingContractType.NONE;
                c.hasCompletedFishingContract = false;
                c.sendMessage("You have reset your Fishing Contract.");
                break;
            case LOBSTER:
                c.hasAcceptedFishingContract = true;
                c.fishingContractAmount = Random.get(50, 200);
                c.fishingContractType = FishingContractType.LOBSTER;
                c.sendMessage("You have been assigned to fish " + c.fishingContractAmount + " Lobsters.");
                break;
            case SWORDFISH:
                c.hasAcceptedFishingContract = true;
                c.fishingContractAmount = Random.get(50, 200);
                c.fishingContractType = FishingContractType.SWORDFISH;
                c.sendMessage("You have been assigned to fish " + c.fishingContractAmount + " Swordfish.");
                break;
            case SHARK:
                c.hasAcceptedFishingContract = true;
                c.fishingContractAmount = Random.get(50, 200);
                c.fishingContractType = FishingContractType.SHARK;
                c.sendMessage("You have been assigned to fish " + c.fishingContractAmount + " Sharks.");
                break;
            case ANGLERFISH:
                c.hasAcceptedFishingContract = true;
                c.fishingContractAmount = Random.get(50, 200);
                c.fishingContractType = FishingContractType.ANGLERFISH;
                c.sendMessage("You have been assigned to fish " + c.fishingContractAmount + " Anglerfish.");
                break;
        }
    }

    /* advances the fishing contract amount */
    public static void advanceFishingContract(Player player) {
        if (player.hasAcceptedFishingContract == true) {
            player.fishingContractAmount -= 1;
            return;
        }
        if (player.fishingContractAmount == 0) {
            player.hasCompletedFishingContract = true;
            player.sendMessage("Your fishing contract has been completed go get a new one.");
            sendTaskCompleteOverlay(player);
            return;
        }
    }

    /* resets the fishing contract to 0 */
    public static void resetFishingContractStatus(Player player) {
        player.hasAcceptedFishingContract = false;
        player.fishingContractAmount = 0;
        player.fishingContractType = FishingContractType.NONE;
        player.hasCompletedFishingContract = false;
        player.sendMessage("You have reset your fishing contract.");
    }

    public static void rewardPlayerForContractCompletion(Player player, int itemId, int amount) {
        if (player.hasCompletedFishingContract == true) {
            player.getInventory().add(itemId, amount);
            player.sendMessage("You have completed a fishing contract, here is your reward.");
        }
    }

    public static void sendTaskCompleteOverlay(Player player) {
        player.startEvent(event -> {
            player.openInterface(InterfaceType.MAIN, 660);
            player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Fishing Contract Completed", "Go turn it in for a reward.");
            event.delay(2);
            player.closeInterface(InterfaceType.MAIN);
        });
    }


}
