package io.ruin.model.entity.npc.actions;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.StringUtils;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SeasonPass.Achievements;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class Lottery {

    private static Lottery lottery;

    @Expose
    public int CURRENT_07_POT = 0;

    @Expose public int CURRENT_EDGEVILLE_GP_POT = 0;

    @Getter
    @Expose private long end;

    @Getter
    @Expose private String winner = "";

    @Getter
    @Expose private List<String> entries = new ArrayList<>();

    @Getter @Expose private List<UnclaimedLotteryPrize> unclaimedWinners = new ArrayList<>();

    private static final File events_folder = new File("data/events/");

    public void check() {
        if (!isActive()) {
            return;
        }
        if (getEntries().isEmpty()) {
            return;
        }
        if (winner.equals("") && System.currentTimeMillis() >= end) {
            winner = drawWinner();
            if (CURRENT_07_POT > 0 && CURRENT_EDGEVILLE_GP_POT > 0) {
                Broadcast.WORLD.sendMessage("<col=ff0000><shad=000000>" + winner + " has just won the lottery with a pot of " + CURRENT_07_POT + "M 07 GP and "  + CURRENT_EDGEVILLE_GP_POT + "M!");
            } else if (CURRENT_07_POT > 0) {
                Broadcast.WORLD.sendMessage("<col=ff0000><shad=000000>" + winner + " has just won the lottery with a pot of " + CURRENT_07_POT + "M 07 GP!");
            } else if (CURRENT_EDGEVILLE_GP_POT > 0) {
                Broadcast.WORLD.sendMessage("<col=ff0000><shad=000000>" + winner + " has just won the lottery with a pot of " + CURRENT_EDGEVILLE_GP_POT + "M!");
            }
            unclaimedWinners.add(new UnclaimedLotteryPrize(winner, CURRENT_EDGEVILLE_GP_POT, CURRENT_07_POT));
            final Optional<Player> winP = World.getPlayerByName(winner);
            winP.ifPresent(player -> player.sendMessage("<col=0acfb0><shad=1>You won the lottery! Collect your prize from Party pete!"));
            winner = "";
            CURRENT_07_POT = 0;
            CURRENT_EDGEVILLE_GP_POT = 0;
            getEntries().clear();
            save();
        }
    }


    public void start(int osrsgp, int deviousgp, int hours) {
        CURRENT_07_POT = osrsgp;
        CURRENT_EDGEVILLE_GP_POT = deviousgp;
        end = System.currentTimeMillis() + (1000 * 60 * 60 * hours);
        entries.clear();
        if (CURRENT_07_POT > 0 && CURRENT_EDGEVILLE_GP_POT > 0) {
            Broadcast.WORLD.sendMessage("<col=ff0000><shad=2>The lottery has just started with a pot of " + CURRENT_07_POT + "M 07 GP and "  + CURRENT_EDGEVILLE_GP_POT + "M, Talk to Party Pete to get entered to win!");
        } else if (CURRENT_07_POT > 0) {
            Broadcast.WORLD.sendMessage("<col=ff0000><shad=2>The lottery has just started with a pot of " + CURRENT_07_POT + "M 07 GP, Talk to Party Pete to get entered <col=ff0000><shad=2>to win!");
        } else if (CURRENT_EDGEVILLE_GP_POT > 0) {
            Broadcast.WORLD.sendMessage("<col=ff0000><shad=2>The lottery has just started with a pot of " + CURRENT_EDGEVILLE_GP_POT + "M, Talk to Party Pete to get entered <col=ff0000><shad=2>to win!");
        }

    }

    public void addMils(int osrsgp, int deviousgp) {
        CURRENT_07_POT += osrsgp;
        CURRENT_EDGEVILLE_GP_POT = deviousgp;
        if (CURRENT_07_POT > 0 && CURRENT_EDGEVILLE_GP_POT > 0) {
            Broadcast.WORLD.sendMessage("<col=ff0000><shad=2>The lottery has just increased to a pot of  " + CURRENT_07_POT + "M 07 GP and "  + CURRENT_EDGEVILLE_GP_POT + "M, Talk to Party Pete to get entered to win!");
        } else if (CURRENT_07_POT > 0) {
            Broadcast.WORLD.sendMessage("<col=ff0000><shad=2>The lottery has just increased to a pot of  " + CURRENT_07_POT + "M 07 GP, Talk to Party Pete to get entered to win!");
        } else if (CURRENT_EDGEVILLE_GP_POT > 0) {
            Broadcast.WORLD.sendMessage("<col=ff0000><shad=2>The lottery has just increased to a pot of  " + CURRENT_EDGEVILLE_GP_POT + "M, Talk to Party Pete to get entered to win!");
        }
    }

    public String getBroadcastMessage() {
        if (CURRENT_07_POT > 0 && CURRENT_EDGEVILLE_GP_POT > 0) {
            return "Devious Lottery Pot - " + CURRENT_07_POT + "M 07 GP and "  + CURRENT_EDGEVILLE_GP_POT + "M! Get entered to WIN!";
        } else if (CURRENT_07_POT > 0) {
            return "Devious Lottery Pot - " + CURRENT_07_POT + "M 07 GP! Get entered to WIN!";
        } else if (CURRENT_EDGEVILLE_GP_POT > 0) {
            return "Devious Lottery Pot - " + CURRENT_EDGEVILLE_GP_POT + "M! Get entered to WIN!";
        }
        return "";
    }

    public String drawWinner() {
        return getEntries().get(Misc.random(0, getEntries().size() - 1));
    }

    public int getNumOfEntries(String playerName) {
        int entries = 0;
        for (String name : getEntries()) {
            if (name.equalsIgnoreCase(playerName))
                entries++;
        }
        return entries;
    }

    public boolean hasUnclaimedPrize(String playerName) {
        return unclaimedWinners.stream().anyMatch(p -> playerName.equalsIgnoreCase(p.getWinner()));
    }

    public void collect(Player player) {
        int osrsPot = 0;
        int gpPot = 0;
        for (UnclaimedLotteryPrize prize : unclaimedWinners) {
            if (player.getName().equalsIgnoreCase(prize.getWinner())) {
                //TODO add to inventory/set vars
                //player.setOsrsgpEarned(player.getOsrsgpEarned() + prize.osrsPot); TODO
                osrsPot += prize.osrsPot * 1000000;
                gpPot += prize.edgevillePot * 1000000;
                //MoneyPouch.add(player, gpPot); TODO set
            }
        }
        if (osrsPot > 0) {
            player.sendMessage("You redeemed " + (osrsPot / 1000000) + "M worth of OSRS GP.");
        }
        if (gpPot > 0) {
            player.sendMessage("You redeemed " + (gpPot / 1000000) + "M coins.");
        }
        unclaimedWinners.stream().filter(p -> p.getWinner().equalsIgnoreCase(player.getName())).forEach(unclaimedWinners::remove);
        save();
    }

    public static boolean purchase(Player player) {
        if (!getInstance().isActive()) {
            player.sendMessage("The lottery is not currently active! Please come back at another time.");
            return false;
        }
        if (player.getInventory().contains(new Item(995, 5000000))) {
            player.getInventory().remove(new Item(995, 5000000));
        } else {
            player.sendMessage("You do not have enough to purchase a lottery ticket.");
            return false;
        }
        final boolean bonus = Misc.random(0, 3) == 1;
        getInstance().addMils(0, bonus ? 2000000 : 1000000);
        getInstance().getEntries().add(player.getName());
        //Achievements.finishAchievement(player, Achievements.AchievementData.ENTER_THE_LOTTERY);
        //Achievements.doProgress(player, Achievements.AchievementData.ENTER_THE_LOTTERY_THREE_TIMES);
        player.sendMessage("Thank you for joining the lottery, you now have <col=ff0000>" + getInstance().getNumOfEntries(player.getName()) + "</col> lottery entries!");
        return true;
    }


    public boolean isActive() {
        return CURRENT_EDGEVILLE_GP_POT > 0 || CURRENT_07_POT > 0;
    }

    public String getEndFormatted() {
        // Creating date format
        DateFormat simple = new SimpleDateFormat("MMM dd, hh:mm aa");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(Lottery.getInstance().getEnd());

        // Formatting Date according to the
        // given format
        return simple.format(result) + " CST";
    }

    public static void save() {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(lottery);
            if(!events_folder.exists() && !events_folder.mkdirs())
                throw new IOException("events directory could not be created!");
            Files.write(new File(events_folder, "lottery.json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        File file = new File(events_folder, "lottery.json");
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String json = new String(bytes);
                lottery = JsonUtils.GSON_EXPOSE_PRETTY.fromJson(json, Lottery.class);
                Server.println("Successfully loaded lottery...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getEdgevillePot() {
        return CURRENT_EDGEVILLE_GP_POT;
    }

    public int get07Pot() {
        return CURRENT_07_POT;
    }

    public static Lottery getInstance() {
        if (lottery == null)
            lottery = new Lottery();
        return lottery;
    }

    @Data
    @RequiredArgsConstructor
    public static class UnclaimedLotteryPrize {
        @Expose private final String winner;
        @Expose private final int edgevillePot;
        @Expose private final int osrsPot;
    }
}
