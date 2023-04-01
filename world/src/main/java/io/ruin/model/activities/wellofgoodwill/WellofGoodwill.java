package io.ruin.model.activities.wellofgoodwill;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import kotlin.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


public class WellofGoodwill {

    private static WellofGoodwill instance;

    @RequiredArgsConstructor
    public enum Perk {
        BONUS_XP("Bonus Experience", "Receive double experience while training your skills.", 30, 50_000_000, 10),
        DOUBLE_PEST("Double Mini-game Points", "Receive double pest control points upon winning a pest control game.", 60,50_000_000, 10),
        DOUBLE_VOTE("Double Vote Points", "Receive double voting points upon voting.", 30,50_000_000, 10),
        DROP_RATE("Increased Drop Rate", "Increase the drop rate percentages for each kill.", 30,50_000_000, 10),
        DOUBLE_BLOOD_MONEY("Double Blood Money", "Receive double blood money when killing players and completing events within the wilderness.", 60, 50_000_000, 10),

        ;

        @Getter private final String name;

        @Getter
        private final String description;

        @Getter
        private final int durationMinutes;

        @Getter
        private final int gpAmountRequired;

        @Getter
        private final int donatorPointsRequired;
    }

    @Expose private static Map<Perk, Pair<Integer, Integer>> PERK_CONTRIBUTION = new HashMap<>();
    @Expose private static Map<Perk, Integer> PERK_MINUTES_REMAINING = new HashMap<>();
    private static final Map<Perk, Pair<Integer, Integer>> PERK_REQUIRED_AMOUNT = new HashMap<>();

    //Represents how long the event should list in minutes
    public static Map<Perk, Integer> PERK_DURATION_MINUTES = new HashMap<>();

    @Expose public static Map<String, Integer> gpContributors = new HashMap<>();

    @Expose public static Map<String, Integer> dpContributors = new HashMap<>();

    private static final File events_folder = new File("data/events/");

    static {
        for (Perk perk : Perk.values()) {
            PERK_REQUIRED_AMOUNT.put(perk, new Pair<>(perk.getGpAmountRequired(), perk.getDonatorPointsRequired()));
            PERK_DURATION_MINUTES.put(perk, perk.getDurationMinutes());
            PERK_MINUTES_REMAINING.put(perk, 0);
            PERK_CONTRIBUTION.put(perk, new Pair<>(0, 0));
        }
    }

    static {
        InterfaceHandler.register(1042, h -> {

            for (int i = 0; i < 7; i++) {
                h.actions[19 + (i * 4)] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                    WellofGoodwill.getInstance().select(player, ((childId - 19) / 4));
                };
            }
            //Contribute GP
            h.actions[86] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.integerInput("How much gp would you like to contribute?", amount -> {
                    WellofGoodwill.getInstance().contributeGP(player, player.currentViewingPerk, amount);
                });
            };
            //Contribute DP
            h.actions[89] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.integerInput("How much donator points would you like to contribute?", amount -> {
                    WellofGoodwill.getInstance().contributeDP(player, player.currentViewingPerk, amount);
                });
            };
        });
    }

    public int getGPDonatedForPerk(final Perk perk) {
        if (!PERK_CONTRIBUTION.containsKey(perk)) {
            System.out.println("nop");
            return 0;
        }
        return PERK_CONTRIBUTION.get(perk).getFirst();
    }
    public int getDPDonatedForPerk(final Perk perk) {
        if (!PERK_CONTRIBUTION.containsKey(perk)) {
            return 0;
        }
        return PERK_CONTRIBUTION.get(perk).getSecond();
    }

    public String getGpDonatedFormatted(int amount) {
        if (amount < 1000) return amount + " GP";
        if (amount > 1_000_000) {
            return (amount / 1_000_000) + "M";
        }
        return (amount / 1000) + "K";
    }

    public void select(Player player, int index) {
        final Perk perk = Perk.values()[index];
        player.getPacketSender().sendString(1042, 78, Misc.formatStringFormal(perk.getName()));
        player.getPacketSender().sendString(1042, 79, Misc.formatStringFormal(perk.getDescription()));
        player.getPacketSender().sendString(1042, 85,
                getGpDonatedFormatted(getGPDonatedForPerk(perk)) + " has been contributed..." +
                "<br>" +
                getDPDonatedForPerk(perk) + " Donator Points have been contributed...");
        player.getPacketSender().sendString(1042, 3, "");
    }

    public static String getDescription(Perk perk) {
        StringBuilder sb = new StringBuilder();
        sb.append(Misc.formatStringFormal(perk.getDescription())).append("<br><br>");

        sb.append("Currently Active: ");
        sb.append(isActive(perk) ? "<col=00ff00>" + getMinutesLeft(perk) + "m left</col>" : "<col=ff0000>Not Active</col>").append("<br>");

        sb.append("GP Amount Needed: ");
        sb.append(getInstance().getGpDonatedFormatted(getAmountNeeded(perk, true))).append("<br>");

        sb.append("Donator Points Amount Needed: ");
        sb.append(getAmountNeeded(perk, false)).append("<br>");

        return sb.toString();
    }

    public String getTopContributors() {
        StringBuilder sb = new StringBuilder();
        sb.append("<br>");
        sb.append("        Top Contributors        ");

        return sb.toString();
    }

    public void open(Player player) {
        for (int i = 0; i < 13; i++) {
            player.getPacketSender().setHidden(1042, 18 + (i * 4), true);
        }
        for (int i = 0; i < Perk.values().length; i++) {
            player.getPacketSender().sendString(1042, 20 + (i * 4), Misc.formatStringFormal(Perk.values()[i].name()));
            player.getPacketSender().setHidden(1042, 18 + (i * 4), false);
        }
        select(player, 0);
        player.openInterface(InterfaceType.MAIN, 1042);
    }

    public void open(Player player, Perk perk) {
        for (int i = 0; i < 13; i++) {
            player.getPacketSender().setHidden(1042, 18 + (i * 4), true);
        }
        for (int i = 0; i < Perk.values().length; i++) {
            player.getPacketSender().sendString(1042, 20 + (i * 4), Misc.formatStringFormal(Perk.values()[i].name()));
            player.getPacketSender().setHidden(1042, 18 + (i * 4), false);
        }
        select(player, perk.ordinal());
        player.openInterface(InterfaceType.MAIN, 1042);
    }

    public void contributeDP(final Player player, final Perk perk, final int amount) {
        if (player.donatorPoints < amount) {
            player.sendMessage("You do not have that many donator points to donate.");
            return;
        }
        if (dpContributors.containsKey(player.getName())) {
            dpContributors.put(player.getName(), gpContributors.get(player.getName()) + amount);
        } else {
            dpContributors.put(player.getName(), amount);
        }
        int previousCurrentMinutes = getMinutesLeft(perk);
        if ((getContributed(perk, false) + amount) <= PERK_REQUIRED_AMOUNT.get(perk).getSecond()) {
            PERK_CONTRIBUTION.put(perk, new Pair<>(PERK_CONTRIBUTION.get(perk).getFirst(), amount));
        } else {
            int remaining = amount;
            while (remaining > 0) {
                if ((getContributed(perk, false) + remaining) >= PERK_REQUIRED_AMOUNT.get(perk).getSecond()) {
                    PERK_MINUTES_REMAINING.put(perk, PERK_MINUTES_REMAINING.get(perk) + PERK_DURATION_MINUTES.get(perk));
                    if (getContributed(perk, false) == PERK_REQUIRED_AMOUNT.get(perk).getSecond()) {
                        PERK_CONTRIBUTION.put(perk, new Pair<>(PERK_CONTRIBUTION.get(perk).getFirst(), 0));
                    }
                } else {
                    PERK_CONTRIBUTION.put(perk, new Pair<>(PERK_CONTRIBUTION.get(perk).getFirst() , PERK_CONTRIBUTION.get(perk).getSecond() + remaining));
                    if (getContributed(perk, false) == PERK_REQUIRED_AMOUNT.get(perk).getSecond()) {
                        PERK_CONTRIBUTION.put(perk, new Pair<>(PERK_CONTRIBUTION.get(perk).getFirst(), 0));
                    }
                }
                remaining -= PERK_REQUIRED_AMOUNT.get(perk).getSecond();
            }
        }
        player.donatorPoints -= amount;
        int currentMinutes = getMinutesLeft(perk);
        if (previousCurrentMinutes > 0) {
            Broadcast.WORLD.sendNews(player.getName() + " has just donated " + amount + " Donator Points to the Well of Goodwill and has extended " + perk.name().toLowerCase().replace("_", " ") + " for an additional " + (getMinutesLeft(perk) - previousCurrentMinutes) + " minutes!");
        } else if (currentMinutes > 0){
            Broadcast.WORLD.sendNews(player.getName() + " has just donated " + amount + " Donator Points and has activated " + perk.name().toLowerCase().replace("_", "") + " for " + (getMinutesLeft(perk) - previousCurrentMinutes) + " minutes!");
        }
        Broadcast.WORLD.sendNews(player.getName() + " has just donated " + amount + " Donator Points to the Well of Goodwill for " + perk.name().toLowerCase().replace("_", " ") + "! Only " + getAmountNeeded(perk, false) + " Donator Points is needed to activate it!");
        WellofGoodwill.getInstance().open(player, perk);
        player.sendMessage("You donated " + amount + " Donator Points to the well of goodwill for the " + perk.getName() + " perk.");
    }

    public void contributeGP(final Player player, final Perk perk, final int amount) {
        if (!player.getInventory().contains(995, amount)) {
            player.sendMessage("You do not have that many coins to donate in your inventory.");
            return;
        }
        if (gpContributors.containsKey(player.getName())) {
            gpContributors.put(player.getName(), gpContributors.get(player.getName()) + amount);
        } else {
            gpContributors.put(player.getName(), amount);
        }
        int previousCurrentMinutes = getMinutesLeft(perk);
        if ((getContributed(perk, true) + amount) <= PERK_REQUIRED_AMOUNT.get(perk).getFirst()) {
            PERK_CONTRIBUTION.put(perk, new Pair<>(amount, PERK_CONTRIBUTION.get(perk).getSecond()));
        } else {
            int remaining = amount;
            while (remaining > 0) {
                System.out.println("Currently contributed: " + getContributed(perk, true));
                if ((getContributed(perk, true) + remaining) >= PERK_REQUIRED_AMOUNT.get(perk).getFirst()) {
                    PERK_MINUTES_REMAINING.put(perk, PERK_MINUTES_REMAINING.get(perk) + PERK_DURATION_MINUTES.get(perk));
                    System.out.println("Adding Perk Minutes Remaining: " + PERK_DURATION_MINUTES.get(perk));
                    if (getContributed(perk, true) == PERK_REQUIRED_AMOUNT.get(perk).getFirst()) {
                        PERK_CONTRIBUTION.put(perk, new Pair<>(0, PERK_CONTRIBUTION.get(perk).getSecond()));
                    }
                } else {
                    PERK_CONTRIBUTION.put(perk, new Pair<>(PERK_CONTRIBUTION.get(perk).getFirst() + remaining, PERK_CONTRIBUTION.get(perk).getSecond()));
                    if (getContributed(perk, true) == PERK_REQUIRED_AMOUNT.get(perk).getFirst()) {
                        PERK_CONTRIBUTION.put(perk, new Pair<>(0, PERK_CONTRIBUTION.get(perk).getSecond()));
                    }
                }
                remaining -= PERK_REQUIRED_AMOUNT.get(perk).getFirst();
            }
        }

        player.getInventory().remove(995, amount);
        int currentMinutes = getMinutesLeft(perk);
        if (previousCurrentMinutes > 0) {
            Broadcast.WORLD.sendNews(player.getName() + " has just contributed to the Well of Goodwill and has extended " + perk.name().toLowerCase().replace("_", " ") + " for an additional " + (getMinutesLeft(perk) - previousCurrentMinutes) + " minutes!");
        } else if (currentMinutes > 0) {
            Broadcast.WORLD.sendNews(player.getName() + " has just contributed " + getGpDonatedFormatted(amount) + " and has activated " + perk.name().toLowerCase().replace("_", "") + " for " + getMinutesLeft(perk) + " minutes!");
        } else if (amount >= 1000000) {
            Broadcast.WORLD.sendNews(player.getName() + " has just contributed " + getGpDonatedFormatted(amount) + " to the Well of Goodwill for " + perk.name().toLowerCase().replace("_", " ") + "! Only " + getGpDonatedFormatted(getAmountNeeded(perk, true)) + " is needed to activate it!");
        }
        WellofGoodwill.getInstance().open(player, perk);
        player.sendMessage("You contribute " + getGpDonatedFormatted(amount) + " to the well of goodwill for the " + perk.getName() + " perk.");
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
        if (!PERK_MINUTES_REMAINING.containsKey(perk))
            return 0;
        return PERK_MINUTES_REMAINING.get(perk);
    }

    public static int getContributed(final Perk perk, boolean gp) {
        if (gp)
            return PERK_CONTRIBUTION.get(perk).getFirst();
        return PERK_CONTRIBUTION.get(perk).getSecond();
    }

    public static int getAmountNeeded(final Perk perk, boolean gp) {
        if (gp)
            return PERK_REQUIRED_AMOUNT.get(perk).getFirst() - PERK_CONTRIBUTION.get(perk).getFirst();
        return PERK_REQUIRED_AMOUNT.get(perk).getSecond() - PERK_CONTRIBUTION.get(perk).getSecond();
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
                Server.println("Successfully loaded well of goodwill...");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        }
    }

    public static void sendTab(Player player) {
        for (int i = 0; i < 11; i++) {
            player.getPacketSender().setHidden(1052, 25 + (i * 7), true);
        }
        int count = 0;
        for (Perk perk : Perk.values()) {
            if (isActive(perk)) {
                player.getPacketSender().setHidden(1052, 25 + (count * 7), false);
                player.getPacketSender().setSprite(1052, 29 + (count * 7), 2703);
                player.getPacketSender().sendString(1052, 30 + (count * 7), perk.getName());
                if (getMinutesLeft(perk) >= 1) {
                    player.getPacketSender().sendString(1052, 31 + (count * 7), getMinutesLeft(perk) + "m left");
                } else {
                    player.getPacketSender().sendString(1052, 31 + (count * 7), "ended");
                }
                count++;
            }
        }
    }

    static {
        ObjectAction.register(6097, "donate", (player, obj) ->  {
            WellofGoodwill.getInstance().open(player);
        });
        ObjectAction.register(6097, "view-contributors", (player, obj) ->  {

        });
    }

    public static WellofGoodwill getInstance() {
        if (instance == null)
            instance = new WellofGoodwill();
        return instance;
    }

}
