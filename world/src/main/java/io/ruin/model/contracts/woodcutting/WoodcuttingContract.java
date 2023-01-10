package io.ruin.model.contracts.woodcutting;

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
public class WoodcuttingContract {

    public static void displayWoodcuttingContract(Player c) {
        OptionScroll.open(c, "<col=8B0000>Select a Woodcutting Contract",
                new Option("-"),
                new Option("Tree", () -> assignWoodcuttingContract(c, WoodcuttingContractType.TREE)),
                new Option("Oak", () -> assignWoodcuttingContract(c, WoodcuttingContractType.OAK)),
                new Option("Willow", () -> assignWoodcuttingContract(c, WoodcuttingContractType.WILLOW)),
                new Option("Maple", () -> assignWoodcuttingContract(c, WoodcuttingContractType.MAPLE)),
                new Option("Yew", () -> assignWoodcuttingContract(c, WoodcuttingContractType.YEW)),
                new Option("Magic", () -> assignWoodcuttingContract(c, WoodcuttingContractType.MAGIC)),
                new Option("Redwood", () -> assignWoodcuttingContract(c, WoodcuttingContractType.REDWOOD)),
                new Option("Teak", () -> assignWoodcuttingContract(c, WoodcuttingContractType.TEAK)),
                new Option("Mahogany", () -> assignWoodcuttingContract(c, WoodcuttingContractType.MAHOGANY)),
                new Option("Blisterwood", () -> assignWoodcuttingContract(c, WoodcuttingContractType.BLISTERWOOD)),
                new Option("-"),
                new Option("Reset Contract", () -> assignWoodcuttingContract(c, WoodcuttingContractType.NONE)),
                new Option("Go back", () -> SkillingContract.open(c))
        );
        return;
    }

    public static void assignWoodcuttingContract(Player c, WoodcuttingContractType contractType) {
        if (c.hasAcceptedWoodcuttingContract == true) {
            c.sendMessage("You cannot start another Woodcutting contract until your current is finished.");
            return;
        }
        switch (contractType) {
            case NONE:
                c.hasAcceptedWoodcuttingContract = false;
                c.woodcuttingContractAmount = 0;
                c.woodcuttingContractType = WoodcuttingContractType.NONE;
                c.sendMessage("You have reset your woodcutting contract.");
                break;
            case TREE:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 100);
                c.woodcuttingContractType = WoodcuttingContractType.TREE;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Normal Trees.");
                break;
            case OAK:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 150);
                c.woodcuttingContractType = WoodcuttingContractType.OAK;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Oak Trees.");
                break;
            case WILLOW:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 200);
                c.woodcuttingContractType = WoodcuttingContractType.WILLOW;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Willow Trees.");
                break;
            case MAPLE:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 200);
                c.woodcuttingContractType = WoodcuttingContractType.MAPLE;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Maple Trees.");
                break;
            case YEW:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 200);
                c.woodcuttingContractType = WoodcuttingContractType.YEW;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Yew Trees.");
                break;
            case MAGIC:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 100);
                c.woodcuttingContractType = WoodcuttingContractType.MAGIC;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Magic Trees.");
                break;
            case REDWOOD:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 100);
                c.woodcuttingContractType = WoodcuttingContractType.REDWOOD;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Redwood Trees.");
                break;
            case TEAK:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 200);
                c.woodcuttingContractType = WoodcuttingContractType.TEAK;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Teak Trees.");
                break;
            case MAHOGANY:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 200);
                c.woodcuttingContractType = WoodcuttingContractType.MAHOGANY;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Mahogany Trees.");
                break;
            case BLISTERWOOD:
                c.hasAcceptedWoodcuttingContract = true;
                c.woodcuttingContractAmount = Random.get(50, 100);
                c.woodcuttingContractType = WoodcuttingContractType.BLISTERWOOD;
                c.sendMessage("You have been assigned " + c.woodcuttingContractAmount + " Blisterwood Trees.");
                break;
        }
        return;

    }

    public static void advanceWoodcuttingContract(Player c) {
        if (c.hasAcceptedWoodcuttingContract == true) {
            c.woodcuttingContractAmount -= 1;
            c.sendMessage("You now have " + c.woodcuttingContractAmount + " " + c.woodcuttingContractType + " remaining.");
            if (c.woodcuttingContractAmount == 0) {
                completeWoodcuttingTask(c);
            }
        }
    }


    public static void completeWoodcuttingTask(Player c) {
            c.hasCompletedWoodcuttingContract = true;
            c.hasAcceptedWoodcuttingContract = false;
            c.woodcuttingContractAmount = 0;
            c.sendMessage("Your contract has been completed go get a new one.");
            sendTaskCompleteOverlay(c);
    }

    public static void resetWoodcuttingContract(Player c) {
        c.hasAcceptedWoodcuttingContract = false;
        c.woodcuttingContractAmount = 0;
        c.woodcuttingContractType = WoodcuttingContractType.NONE;
        c.sendMessage("You have reset your woodcutting contract.");
    }

    public static void sendTaskCompleteOverlay(Player player) {
        player.startEvent(event -> {
            player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
            player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Contract Completed", "Go turn it in for a reward.");
        });
    }
}
