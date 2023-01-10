package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.Server;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.shop.ShopManager;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoteGambler {

    private static final int VOTE_TICKET_ID = 995;

    private static final int NPC_ID = 1815;

    private static final int REWARD_ID = 995;

    private static final int WIN_AMOUNT = 5_000_000;

    private static final int COST = 5_000_000;

    private static final ArrayList<String> participants = new ArrayList<>();

    private static final List<Winner> unclaimedRewards = new ArrayList<>();

    static {
        ItemNPCAction.register(VOTE_TICKET_ID, NPC_ID, (player, item, npc) -> handleEntry(player, npc));

        NPCAction.register(NPC_ID, "Join-Lottery", VoteGambler::handleEntry);

        NPCAction.register(NPC_ID, "Lottery-info", VoteGambler::lotteryInfo);

        NPCAction.register(NPC_ID, "Trade", (player, npc) -> ShopManager.openIfExists(player, "aecef817-39c1-4a22-9ee9-2815da9dd3df"));

        /**
         * Starts event every 12 hours
         * 432 * 100 = 43200 seconds
         * Once it starts, it will...
         * check the entries -> Find a random entry -> Award the player associated -> Reset
         */
        World.startEvent(event -> {
            while (true) {
                event.delay(Server.toTicks(432 * 100));
                handleWinnerEntry();
            }
        });
        LoginListener.register(player -> {
            if (!unclaimedRewards.isEmpty()) {
                unclaimedRewards.stream()
                        .filter(win -> win.getUserId().equalsIgnoreCase(player.getName()))
                        .findAny()
                        .ifPresent(winner -> {
                            player.getBank().add(REWARD_ID, 2);
                            sendMessage(player, "You have won the last vote lottery, your prizes have been deposited in your bank.");
                            unclaimedRewards.remove(winner);
                        });
            }
        });
    }


    private static void lotteryInfo(Player player, NPC npc) {
        int totalEntries = participants.size();
        String type = totalEntries == 1 ? "entry" : "entries";
        String message = totalEntries == 0 ? "There are no entries" : "There is currently " + Color.RED.wrap(totalEntries + " ") + type;
        player.dialogue(new NPCDialogue(npc, message));
    }

    private static void handleEntry(Player player, NPC npc) {
        if (alreadyEntered(player)) {
            player.sendMessage("You have already joined the lottery this time!");
            return;
        }

        int amount = player.getInventory().count(995);
        if (amount < 5_000_000) {
            player.sendMessage("unfortunately you do not have enough coins to enter the lottery!");
            return;
        }

        player.getInventory().remove(995, COST);
        addEntry(player);
        sendMessage(player, "Goodluck, you have entered the Vote lottery");
    }

    private static Player player;

    private static void handleWinnerEntry() {
        int line = io.ruin.api.utils.Random.get(participants.size());
        String winner = participants.get(line);

        if (participants.isEmpty()) {
            sendBroadcast("No winners this raffle since there were no entries");
            return;
        }

        Optional<Player> playerOptional = Optional.ofNullable(World.getPlayer(winner));
        if (playerOptional.isPresent()) {
            player = playerOptional.get();
            rewardAndReset(player);
        } else {
            unclaimedRewards.add(new Winner(player.getName()));
            sendBroadcast(StringUtils.capitalizeFirst(player.getName()) + " has won the Vote Lottery");
            clearAllEntries();
        }
    }


    private static void rewardAndReset(Player player) {
        player.getInventory().add(REWARD_ID, (int) (WIN_AMOUNT * (participants.size() / 5 * 2.75))); // if 100 players max cap is 275M win back ( 550M Eco sink )
        sendMessage(player, "You have won the vote raffle draw");
        sendBroadcast(StringUtils.capitalizeFirst(player.getName()) + " has won the Vote Lottery");
        sendBroadcast("Vote now for a chance to win the house pot!!");
        clearAllEntries();
    }

    private static void addEntry(Player player) {
        participants.add(player.getName());
    }

    private static boolean alreadyEntered(Player player) {
        return participants.contains(player.getName());
    }

    private static void clearAllEntries() {
        participants.clear();
    }


    private static void sendBroadcast(String message) {
        Broadcast.WORLD.sendNews("[Vote Lottery] " + message);
    }

    private static void sendMessage(Player player, String message) {
        player.sendFilteredMessage(Color.DARK_RED.wrap("[Vote Lottery]") + " " + message);
    }


    static class Winner {
        String userId;

        Winner(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }
    }
}
