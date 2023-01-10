package io.ruin.model.entity.npc.actions;

import io.ruin.Server;
import io.ruin.api.utils.StringUtils;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.utility.Broadcast;

import java.util.*;

public class Lottery {

    private static final boolean enabled = true;

    private static final int NPC_ID = 1815;

    private static final int REWARD_ID = 995;

    private static final Set<Integer> entries = new HashSet<>();

    private static final List<Winner> unclaimedRewards = new ArrayList<>();

    public static int timer = 43200000;

    static {
        NPCAction.register(NPC_ID, "talk-to", Lottery::lotteryInfo);
        NPCAction.register(NPC_ID, "buy-ticket", (player, npc) -> {
            player.integerInput("How many entries would you like to buy per 1M", amount -> {
                buyTicket(player, amount);
            });
        });

        /**
         * Starts event every 12 hours
         * 432 * 100 = 43200 seconds
         * Once it starts, it will...
         * check the entries -> Find a random entry -> Award the player associated -> Reset
         */
        World.startEvent(e -> {
            timer -= Server.tickMs();
        });
        //(int) TimeUnit.MILLISECONDS.toMinutes(p.playTime * Server.tickMs())
        World.startEvent(event -> {
            while (true) {
                event.delay(Server.toTicks(432 * 100));
                handleWinnerEntry();
            }
        });
        LoginListener.register(player -> {
            if (!unclaimedRewards.isEmpty()) {
                unclaimedRewards.stream()
                        .filter(win -> win.getUserId() == player.getUserId())
                        .findAny()
                        .ifPresent(winner -> {
                            int totalEntries = entries.size();
                            player.getBank().add(REWARD_ID, 1000000 * totalEntries);
                            sendMessage(player, "<col=0040ff>You have won the last lottery, your prizes have been deposited in your bank.");
                            unclaimedRewards.remove(winner);
                        });
            }
        });
    }

    private static void buyTicket(Player player, int amount) {
        if (player.getInventory().contains(995, 1000000 * amount)) {
            player.getInventory().remove(995, 1000000 * amount);
            handleEntry(player, amount);
            player.dialogue(
                    new MessageDialogue("You have purchased " + amount + "x lottery ticket! Use it on the Lottery Manager to enter!")
            );
        } else {
            player.dialogue(
                    new MessageDialogue("You do not have enough money to purchase a ticket!")
            );
        }
    }

    private static void lotteryInfo(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hey there, currently the pot is worth <col=1eb8ba>" + entries.size() + "M.</col> It will be drawn in <col=1eb8ba>" +
                        TimeUtils.fromMs(43200000, true) + "</col>. an entry cost <col=1eb8ba>1M GP</col>, do you want to enter."),
                new OptionsDialogue("Purchase a ticket?",
                        new Option("Buy am entry for <col=1eb8ba>1M GP</col>", () -> {
                            player.integerInput("How many entries would you like to buy per 1M", amount -> {
                                buyTicket(player, amount);
                            });
                        }),
                        new Option("Nevermind")
                )
        );
    }

    private static void handleEntry(Player player, int amount) {
        if (!enabled) {
            sendMessage(player, "<col=0040ff>The lottery is currently disabled");
            return;
        }

        if (alreadyEntered(player)) {
            sendMessage(player, "<col=0040ff>You have already entered the lottery.");
            return;
        }
        int successfulEntries;
        for (successfulEntries = 1; successfulEntries <= amount; successfulEntries++) {
            addEntry(player);
        }
        if (successfulEntries > 1)
            sendMessage(player, "<col=0040ff>You have placed " + successfulEntries + " entries into the lottery, good luck!");
        else
            sendMessage(player, "<col=0040ff>You have entered the lottery, good luck!");
    }

    private static void handleWinnerEntry() {
        int totalEntries = entries.size();

        if (entries.isEmpty()) {

            sendBroadcast("No winners this lottery as nobody entered!");

            return;
        }

        Optional<Integer> entryWinnerOptional = entries.stream().skip(new Random().nextInt(totalEntries)).findFirst();

        if (!entryWinnerOptional.isPresent()) {
            System.err.println("This should not be empty...");
            return;
        }

        int winnerId = entryWinnerOptional.get();

        Optional<Player> playerOptional = Optional.ofNullable(World.getPlayer(winnerId));
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            rewardAndReset(player);
        } else {
            unclaimedRewards.add(new Winner(winnerId));
            sendBroadcast("<col=0040ff>The lottery winner is offline, the winnings have been deposited into their bank!");
            clearAllEntries();
        }
    }


    private static void rewardAndReset(Player player) {
        int totalEntries = entries.size();
        player.getInventory().add(REWARD_ID, 5000000 * totalEntries);
        sendMessage(player, "You have won the lottery!");
        sendBroadcast(StringUtils.capitalizeFirst(player.getName()) + " has won the lottery worth " + totalEntries * 5000000 + "m!");
        clearAllEntries();
    }

    private static void addEntry(Player player) {
        entries.add(player.getUserId());
    }

    private static boolean alreadyEntered(Player player) {
        return entries.contains(player.getUserId());
    }

    private static void clearAllEntries() {
        entries.clear();
    }


    private static void sendBroadcast(String message) {
        Broadcast.WORLD.sendLottery("" + message);
    }

    private static void sendMessage(Player player, String message) {
        player.sendFilteredMessage(Color.DARK_RED.wrap("[Lottery]") + " " + message);
    }


    static class Winner {
        int userId;

        Winner(int userId) {
            this.userId = userId;
        }

        public int getUserId() {
            return userId;
        }
    }
}
