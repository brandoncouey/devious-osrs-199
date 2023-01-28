package io.ruin.model.activities.wellofgoodwill;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


public class WellofGoodwill {

    private static WellofGoodwill instance;

    public enum Perk {
        BONUS_XP,
        DOUBLE_PEST,
        DOUBLE_VOTE,
        DROP_RATE,
        DOUBLE_BLOOD_MONEY
    }

    @Expose private Map<Perk, Integer> PERK_CONTRIBUTION = new HashMap<>();
    @Expose private Map<Perk, Integer> PERK_MINUTES_REMAINING = new HashMap<>();
    private static final Map<Perk, Integer> PERK_REQUIRED_AMOUNT = new HashMap<>();

    //Represents how long the event should list in minutes
    public static Map<Perk, Integer> PERK_DURATION_MINUTES = new HashMap<>();

    private static final File events_folder = new File("data/events/");

    static {
        PERK_REQUIRED_AMOUNT.put(Perk.BONUS_XP, 50_000_000);
        PERK_REQUIRED_AMOUNT.put(Perk.DOUBLE_PEST, 50_000_000);
        PERK_REQUIRED_AMOUNT.put(Perk.DOUBLE_VOTE, 50_000_000);
        PERK_REQUIRED_AMOUNT.put(Perk.DROP_RATE, 50_000_000);
        PERK_REQUIRED_AMOUNT.put(Perk.DOUBLE_BLOOD_MONEY, 50_000_000);

        PERK_DURATION_MINUTES.put(Perk.BONUS_XP, 30);
        PERK_DURATION_MINUTES.put(Perk.DOUBLE_PEST, 60);
        PERK_DURATION_MINUTES.put(Perk.DOUBLE_VOTE, 15);
        PERK_DURATION_MINUTES.put(Perk.DROP_RATE, 30);
        PERK_DURATION_MINUTES.put(Perk.DOUBLE_BLOOD_MONEY, 30);

    }

    public void contribute(final Player player, final Perk perk, final int amount) {
        if (!player.getInventory().contains(995, amount)) {
            player.sendMessage("You do not have that many coins to donate in your inventory.");
            return;
        }
        int currentMinutes = getMinutesLeft(perk);
        if ((getContributed(perk) + amount) <= PERK_REQUIRED_AMOUNT.get(perk)) {
            PERK_MINUTES_REMAINING.put(perk, PERK_MINUTES_REMAINING.get(perk) + PERK_DURATION_MINUTES.get(perk));
            PERK_CONTRIBUTION.put(perk, amount);
        } else {
            int remaining = amount;
            while (remaining > 0) {

                if ((getContributed(perk) + remaining) >= PERK_REQUIRED_AMOUNT.get(perk)) {
                    PERK_MINUTES_REMAINING.put(perk, PERK_MINUTES_REMAINING.get(perk) + PERK_DURATION_MINUTES.get(perk));
                    remaining -= PERK_REQUIRED_AMOUNT.get(perk);
                    if (getContributed(perk) == PERK_REQUIRED_AMOUNT.get(perk)) {
                        PERK_CONTRIBUTION.put(perk, 0);
                    }
                } else {
                    PERK_CONTRIBUTION.put(perk, PERK_CONTRIBUTION.get(perk) + remaining);
                    if (getContributed(perk) == PERK_REQUIRED_AMOUNT.get(perk)) {
                        PERK_CONTRIBUTION.put(perk, 0);
                    }
                }
            }
        }
        player.getInventory().remove(995, amount);
        //TODO properly format these fkn strings  for perks
        if (currentMinutes > 0) {
            Broadcast.WORLD.sendNews(player.getName() + " has just donated to the Well of Goodwill and has extended " + perk.name().toLowerCase().replace("_", " ") + " for an additional " + (getMinutesLeft(perk) - currentMinutes) + " minutes!");
        } else {
            Broadcast.WORLD.sendNews(player.getName() + " has just donated " + Utils.formatMoneyString(amount) + " and has activated " + perk.name().toLowerCase().replace("_", "") + " for " + getMinutesLeft(perk) + " minutes!");
        }
        if (amount >= 1000000) {
            Broadcast.WORLD.sendNews(player.getName() + " has just donated " + Utils.formatMoneyString(amount) + " to the Well of Goodwill for " + perk.name().toLowerCase().replace("_", " ") + "! Only " + Utils.formatMoneyString(getAmountNeeded(perk)) + " is needed to activate it!");
        }
    }

    public static boolean isActive(final Perk perk) {
        return getMinutesLeft(perk) > 0;
    }

    public static boolean isActive() {
        for (final Perk perk : Perk.values()) {
            if (isActive(perk))
                return true;
        }
        return false;
    }

    public static int getMinutesLeft(final Perk perk) {
        if (!getInstance().PERK_MINUTES_REMAINING.containsKey(perk))
            return 0;
        return getInstance().PERK_MINUTES_REMAINING.get(perk);
    }

    public static int getContributed(final Perk perk) {
        return getInstance().PERK_CONTRIBUTION.get(perk);
    }

    public static int getAmountNeeded(final Perk perk) {
        return PERK_REQUIRED_AMOUNT.get(perk) - getInstance().PERK_CONTRIBUTION.get(perk);
    }

    public static void save() {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(instance);
            if(!events_folder.exists() && !events_folder.mkdirs())
                throw new IOException("events directory could not be created!");
            Files.write(new File(events_folder, "wellofgoodwill.json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            Server.logError(e.getMessage());
        }

    }

    public static void load() {
        File file = new File(events_folder, "wellofgoodwill.json");
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String json = new String(bytes);
                instance = JsonUtils.GSON_EXPOSE_PRETTY.fromJson(json, WellofGoodwill.class);
                System.out.println("Successfully loaded well of goodwill!");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        }
    }

    public static WellofGoodwill getInstance() {
        if (instance == null)
            instance = new WellofGoodwill();
        return instance;
    }

}
