package io.ruin.model.inter.pvpinter;

import io.ruin.model.activities.pvp.unrated.BattleGroundsQueue;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

import java.util.ArrayList;

public class PvpInter {

    private static final ArrayList<Player> players = new ArrayList<>();

    static {
        InterfaceHandler.register(820, h -> {//Main inter
            h.actions[72] = (SlotAction) (player, slot) -> claimSeasonRewards(player);//Claim rewards
            h.actions[66] = (SlotAction) (player, slot) -> player.openInterface(InterfaceType.MAIN, 825);//View arenas
            h.actions[78] = (SlotAction) (player, slot) -> player.openInterface(InterfaceType.MAIN, 826);//View bg's
            h.actions[80] = (SlotAction) (player, slot) -> player.openInterface(InterfaceType.MAIN, 824);//View rated
        });
        InterfaceHandler.register(824, h -> {//Rated inter
            h.actions[97] = (SlotAction) (player, slot) -> player.openInterface(InterfaceType.MAIN, 820);//Go back
            h.actions[91] = (SlotAction) (player, slot) -> player.sendMessage("Cannot enter queue right now");//Enter queue
            h.actions[66] = (SlotAction) (player, slot) -> player.sendMessage("2v2 Coming soon");//Choose 2v2
            h.actions[87] = (SlotAction) (player, slot) -> player.sendMessage("3v3 Coming soon");//Choose 3v3
            h.actions[78] = (SlotAction) (player, slot) -> player.sendMessage("BG's Coming soon");//Choose bg's
        });
        InterfaceHandler.register(825, h -> {//Unrated Arenas
            h.actions[97] = (SlotAction) (player, slot) -> player.openInterface(InterfaceType.MAIN, 820);//Go back
            h.actions[91] = (SlotAction) (player, slot) -> player.sendMessage("Cannot enter queue right now");//Enter queue
            h.actions[66] = (SlotAction) (player, slot) -> player.sendMessage("You have selected 2v2 Arenas");//Choose 2v2
            h.actions[87] = (SlotAction) (player, slot) -> player.sendMessage("You have selected 3v3 Arenas");//Choose 3v3
        });
        InterfaceHandler.register(826, h -> {//Unrated BG's
            h.actions[97] = (SlotAction) (player, slot) -> player.openInterface(InterfaceType.MAIN, 820);//Go back
            h.actions[91] = (SlotAction) (player, slot) -> player.sendMessage("Cannot enter queue right now");//Enter queue
            h.actions[66] = (SlotAction) (player, slot) -> BattleGroundsQueue.joinQueue(player);//Choose bg's
        });
        InterfaceHandler.register(822, h -> {//In Queue widget
            h.actions[136] = (SlotAction) (player, slot) -> leaveQueueWidget(player);//Leave queue
        });
    }

    private static void leaveQueueWidget(Player player) {
        player.dialogue(
                new OptionsDialogue("Leave Queue?",
                        new Option("Yes", () -> leave(player)),
                        new Option("No"))
        );
    }

    private static void leave(Player player) {
        final ArrayList<Player> players = new ArrayList<>();
        player.lock();
        players.removeIf(p -> p == player);
        player.closeInterface(InterfaceType.TARGET_OVERLAY);
        player.logoutListener = null;
        player.teleportListener = null;
        player.unlock();
        player.sendMessage("You have left the queue.");
    }

    private static void claimSeasonRewards(Player player) {
        if (player.conquestPoints < 50000) {
            player.dialogue(
                    new MessageDialogue("You must reach level 5 conquest to claim the season rewards!")
            );
        }
        if (player.claimedSeasonReward == true) {
            player.dialogue(
                    new MessageDialogue("You have already claimed this seasons reward!")
            );
        }
        if (player.conquestPoints >= 50000 && player.claimedSeasonReward == false) {
            //Add title & pet reward here
            player.claimedSeasonReward = true;
            player.dialogue(
                    new MessageDialogue("Congratulations, you have claimed this seasons Conquest rewards!")
            );
        }
    }

}