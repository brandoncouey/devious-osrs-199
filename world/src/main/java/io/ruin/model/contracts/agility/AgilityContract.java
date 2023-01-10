package io.ruin.model.contracts.agility;

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
public class AgilityContract {

    public static void displayAgilityContract(Player player) {
        OptionScroll.open(player, "<col=8B0000>Select an Agility Contract",
                new Option("-"),
                new Option("Gnome course", () -> assignAgilityContract(player, AgilityContractType.GNOME)),
                new Option("Barb course", () -> assignAgilityContract(player, AgilityContractType.BARB)),
                new Option("Draynor Rooftop", () -> assignAgilityContract(player, AgilityContractType.DRAYNOR_ROOFTOP)),
                new Option("Al Kharid Rooftop", () -> assignAgilityContract(player, AgilityContractType.AL_KHARID_ROOFTOP)),
                new Option("Varrock Rooftop", () -> assignAgilityContract(player, AgilityContractType.VARROCK_ROOFTOP)),
                new Option("Canifis Rooftop", () -> assignAgilityContract(player, AgilityContractType.CANIFIS_ROOFTOP)),
                new Option("Falador Rooftop", () -> assignAgilityContract(player, AgilityContractType.FALADOR_ROOFTOP)),
                new Option("Seers Village Rooftop", () -> assignAgilityContract(player, AgilityContractType.SEERS_ROOFTOP)),
                //new Option("Pollnivneach Rooftop", () -> assignAgilityContract(player, AgilityContractType.POLLNIVNEACH_ROOFTOP)),
                new Option("Rellekka Rooftop", () -> assignAgilityContract(player, AgilityContractType.RELLEKKA_ROOFTOP)),
                new Option("Ardougne Rooftop", () -> assignAgilityContract(player, AgilityContractType.ARDOUGNE_ROOFTOP)),
                new Option("-"),
                new Option("Reset Contract", () -> assignAgilityContract(player, AgilityContractType.NONE)),
                new Option("Go back", () -> SkillingContract.open(player))
        );
    }

    public static void assignAgilityContract(Player player, AgilityContractType contractType) {
        switch (contractType) {
            case NONE:
                player.hasAcceptedAgilityContract = false;
                player.agilityContractAmount = 0;
                player.agilityContractType = AgilityContractType.NONE;
                player.sendMessage("You have reset your agility contract.");
                break;
            case GNOME:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 15);
                player.agilityContractType = AgilityContractType.GNOME;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the gnome agility course.");
                break;
            case BARB:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 15);
                player.agilityContractType = AgilityContractType.BARB;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the barbarian agility course.");
                break;
            case DRAYNOR_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.DRAYNOR_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the draynor rooftop agility course.");
                break;
            case AL_KHARID_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.AL_KHARID_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the al kharid rooftop agility course.");
                break;
            case VARROCK_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.VARROCK_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the varrock rooftop agility course.");
                break;
            case CANIFIS_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.CANIFIS_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the canifis rooftop agility course.");
                break;
            case FALADOR_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.FALADOR_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the falador rooftop agility course.");
                break;
            case SEERS_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.SEERS_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the seers rooftop agility course.");
                break;
            case POLLNIVNEACH_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.POLLNIVNEACH_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the pollnivneach rooftop agility course.");
                break;
            case RELLEKKA_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.RELLEKKA_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the rellekka rooftop agility course.");
                break;
            case ARDOUGNE_ROOFTOP:
                player.hasAcceptedAgilityContract = true;
                player.agilityContractAmount = Random.get(5, 20);
                player.agilityContractType = AgilityContractType.ARDOUGNE_ROOFTOP;
                player.sendMessage("You have been assigned " + player.agilityContractAmount + " laps on the ardougne rooftop agility course.");
                break;

        }
    }

    public static void advanceAgilityContract(Player player) {
        if (player.hasAcceptedAgilityContract == true) {
            player.agilityContractAmount -= 1;
            player.sendMessage("You now have " + player.agilityContractAmount + " " + player.agilityContractType + " laps remaining.");
            if (player.agilityContractAmount == 0) {
                completeAgilityContract(player);
            }
        }
    }
    public static void completeAgilityContract(Player c) {
        c.hasCompletedAgilityContract = true;
        c.hasAcceptedAgilityContract = false;
        c.agilityContractAmount = 0;
        c.sendMessage("Your contract has been completed go get a new one.");
        sendTaskCompleteOverlay(c);
    }

    public static void resetAgilityContractStatus(Player player) {
        player.hasAcceptedAgilityContract = false;
        player.agilityContractAmount = 0;
        player.agilityContractType = AgilityContractType.NONE;
        player.sendMessage("You have reset your agility contract.");
    }

    public static void rewardPlayerForContractCompletion(Player player) {

    }
    public static void sendTaskCompleteOverlay(Player player) {
        player.startEvent(event -> {
            player.openInterface(InterfaceType.SECONDARY_OVERLAY, 660);
            player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Contract Completed", "Your reward has been sent to your bank!");
        });
    }

}
