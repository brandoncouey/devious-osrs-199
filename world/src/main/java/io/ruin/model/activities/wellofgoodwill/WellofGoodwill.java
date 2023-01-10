package io.ruin.model.activities.wellofgoodwill;

import io.ruin.api.utils.HomeFiles;
import io.ruin.api.utils.Random;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.ChamberDefinition;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class WellofGoodwill {

    private static int AMOUNT_NEEDED = 0; //50m
    private static final int DONATION_MINIMUM = 50_000; //1m
    private static final int BONUS_DURATION = 60; //2hour

    private static CopyOnWriteArrayList<Player> CONTRIBUTORS = new CopyOnWriteArrayList<Player>();
    public static WellState STATE = WellState.INACTIVE;
    private static long START_TIMER = 0;
    private static int MONEY_IN_WELL = 0;
    public static String perkName = "Null";

    static {
        load();
        World.startEvent(e -> {
            while (true) {
                e.delay(50);
                updateState();
            }
        });
    }

    public static void load() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(HomeFiles.get("well.cfg")));
            String line = in.readLine();
            BufferedReader perk = new BufferedReader(new FileReader(HomeFiles.get("perk.cfg")));
            perkName = perk.readLine();
            if (line != null) {
                if (line.startsWith("GP")) {
                    MONEY_IN_WELL = Integer.parseInt(line.substring(2));
                    if (!perkName.equalsIgnoreCase("null")){
                        AMOUNT_NEEDED = Perks.valueOf(perkName).cost;
                    }
                } else {
                    long timeLeft = Long.parseLong(line);//7200000
                    if (timeLeft > 0) {
                        STATE = WellState.ACTIVE;
                        if (perkName.equalsIgnoreCase("double_xp")) {
                            World.boostXp(2);
                        } else if (perkName.equalsIgnoreCase("DOUBLE_DROPS")) {
                            World.doubleDrops = true;
                        } else if (perkName.equalsIgnoreCase("DOUBLE_SLAYER")) {
                            World.doubleSlayer = true;
                        } else if (perkName.equalsIgnoreCase("DOUBLE_RAIDS")) {
                            World.doubleRaids = true;
                        } else if (perkName.equalsIgnoreCase("double_pest")) {
                            World.doublePest = true;
                        }
                        START_TIMER = System.currentTimeMillis() - (TimeUnit.MINUTES.toMillis(BONUS_DURATION) - timeLeft);
                        MONEY_IN_WELL = AMOUNT_NEEDED;
                    }
                }
            }
            perk.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(HomeFiles.get("well.cfg")));
            String toSave = isActive() ? TimeUnit.MINUTES.toMillis(getMinutesRemaining()) + "" : "GP" + (AMOUNT_NEEDED - getMissingAmount());
            out.write(toSave);
            out.close();

            BufferedWriter perk = new BufferedWriter(new FileWriter(HomeFiles.get("perk.cfg")));
            String perktoSave = perkName;
            perk.write(perktoSave);
            perk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lookDownWell(Player player) {
        if (checkFull(player)) {
            return;
        }
        player.dialogue(new MessageDialogue("It looks like the well could hold another " + Misc.insertCommasToNumber("" + getMissingAmount() + "") + " coins."));
    }

    public static boolean checkFull(Player player) {
        if (STATE == WellState.ACTIVE) {
            player.dialogue(new MessageDialogue("The Fountain of Enchantment is active and has " + getMinutesRemaining() + " minutes left!"));
            return true;
        }
        return false;
    }

    public static void donate(Player player, int amount, String perk) {
        if (perkName.equalsIgnoreCase("null")) {
            perkName = perk;
            AMOUNT_NEEDED = Perks.valueOf(perk).cost;
        }
        if (checkFull(player)) {
            return;
        }
        if (amount < DONATION_MINIMUM) {
            player.dialogue(new MessageDialogue("You must donate at least 1M coins."));
            return;
        }
        if (amount > getMissingAmount()) {
            amount = getMissingAmount();
        }

        if (player.getInventory().getAmount(995) < amount) {
            player.dialogue(new MessageDialogue("You do not have that much money in your inventory."));
            return;
        }
        player.getInventory().remove(995, amount);

        if (!CONTRIBUTORS.contains(player)) {
            CONTRIBUTORS.add(player);
        }
        MONEY_IN_WELL += amount;
        if (amount > 999999) { //1m + for it to send global message
            Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "Fountain of Enchantment: ", "" + player.getName() + " has donated " + Misc.insertCommasToNumber("" + amount + "") + " Coins to the Fountain of Enchantment!");
        }
        player.dialogue(new MessageDialogue("Thank you for your contribution of " + Misc.insertCommasToNumber("" + amount + "") + "."));
        if (getMissingAmount() <= 0) {
            STATE = WellState.ACTIVE;
            START_TIMER = System.currentTimeMillis();
            if (perkName.equalsIgnoreCase("double_xp")) {
                World.boostXp(2);
            } else if (perkName.equalsIgnoreCase("DOUBLE_DROPS")) {
                World.doubleDrops = true;
            } else if (perkName.equalsIgnoreCase("DOUBLE_SLAYER")) {
                World.doubleSlayer = true;
            } else if (perkName.equalsIgnoreCase("DOUBLE_PVP_BM")) {
                World.doublePVP = true;
            } else if (perkName.equalsIgnoreCase("double_pest")) {
                World.doublePest = true;
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("The Fountain of Enchantment Has Been Filled!");
            eb.setDescription("The Found Of Uhld is now granting " + capitalize(perkName));
            eb.setImage("https://www.deviousps.com/discordimg/Fountain_of_Uhld.png");
            eb.setColor(new java.awt.Color(0xB00D03));
            //eb.addField("Notification!", "<@&991399318219128945>", true);
            DiscordConnection.jda.getTextChannelById("994599921980289055").sendMessageEmbeds(eb.build()).queue();
            Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "Fountain of Enchantment: ", "The Fountain of Enchantment has been activated!");
            Broadcast.WORLD.sendNews(Icon.BLUE_INFO_BADGE, "Fountain of Enchantment: ", "It is now granting everyone " + capitalize(perkName) + ".");
            World.sendGraphics(1388, 130, 1, 2327, 3680, 0);
            World.sendGraphics(1388, 130, 1, 2326, 3680, 0);
            World.sendGraphics(1388, 130, 1, 2327, 3679, 0);
            World.sendGraphics(1388, 130, 1, 2326, 3679, 0);
        } else {
            player.sendMessage("The Well stills needs " + getMissingAmount() + "GP, to activate!");
        }
        save();
    }

    public static String capitalize(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("_", " ");
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i+1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i+2));
                }
            }
        }
        return s;
    }

    public static void updateState() {
        if (STATE == WellState.ACTIVE) {
            if (getMinutesRemaining() <= 0) {
                Broadcast.WORLD.sendNews(Icon.ANNOUNCEMENT, "Fountain of Enchantment: ", "The Fountain of Enchantment is no longer granting " + capitalize(perkName) + ".");
                setDefaults();
            }
        }
    }

    public static void setDefaults() {
        CONTRIBUTORS.clear();
        STATE = WellState.INACTIVE;
        World.boostXp(1);
        World.doubleDrops = false;
        World.doubleSlayer = false;
        World.doubleRaids = false;
        World.doublePVP = false;
        perkName = "Null";
        START_TIMER = 0;
        MONEY_IN_WELL = 0;
        save();
    }
    public static void exchangeGPtoBox(Player player) {
        if (player.getInventory().count(995) < 500_000_000) {
            player.sendMessage("You have failed to reach the requirements to receive a luxury box.");
            return;
        }
        player.getInventory().remove(995, 500_000_000);
        player.getInventory().add(290, Random.get(1, 3));
    }
    public static int getMissingAmount() {
        return (AMOUNT_NEEDED - MONEY_IN_WELL);
    }

    public static int getMinutesRemaining() {
        return (BONUS_DURATION - Misc.getMinutesPassed(System.currentTimeMillis() - START_TIMER));
    }

    public static int getMoneyInWell() {
        return (MONEY_IN_WELL);
    }

    public static int getAmountNeeded() {
        return (AMOUNT_NEEDED);
    }

    public static boolean isActive() {
        updateState();
        return STATE == WellState.ACTIVE;
    }

    public enum WellState {
        INACTIVE,
        ACTIVE
    }

    static {
        NPCAction.register(14750,2,(player, npc) -> {
            if (checkFull(player))
                return;

            player.openInterface(InterfaceType.CHATBOX, 1017);
            player.getPacketSender().sendString(1017, 13, "Amount Paid (" + Misc.currency2(WellofGoodwill.getMoneyInWell()) + "/" + Misc.currency(WellofGoodwill.getAmountNeeded()));
            player.getPacketSender().sendString(1017, 14, "Current Perk : " + (perkName.equalsIgnoreCase("null") ? capitalize("None") : capitalize(perkName))); // current chosen perk
        });

        InterfaceHandler.register(1017, h -> {
            h.actions[15] = (SimpleAction) p -> p.integerInput("How much would you like to donate towards " + capitalize(Perks.DOUBLE_XP.name()), amt -> donate(p, amt, Perks.DOUBLE_XP.name()));
            h.actions[16] = (SimpleAction) p -> p.integerInput("How much would you like to donate towards " + capitalize(Perks.DOUBLE_SLAYER.name()), amt -> donate(p, amt, Perks.DOUBLE_SLAYER.name()));
            h.actions[17] = (SimpleAction) p -> p.integerInput("How much would you like to donate towards " + capitalize(Perks.DOUBLE_RAIDS.name()), amt -> donate(p, amt, Perks.DOUBLE_RAIDS.name()));
            h.actions[18] = (SimpleAction) p -> p.integerInput("How much would you like to donate towards " + capitalize(Perks.double_pest.name()), amt -> donate(p, amt, Perks.double_pest.name()));
            h.actions[19] = (SimpleAction) p -> p.integerInput("How much would you like to donate towards " + capitalize(Perks.DOUBLE_DROPS.name()), amt -> donate(p, amt, Perks.DOUBLE_DROPS.name()));
        });
        ItemNPCAction.register(995,14750, (p, item, npc) -> p.dialogue(new MessageDialogue("Would you like to exchange 500m for a Luxury box?" +
                        "Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> exchangeGPtoBox(p)),
                        new Option("No")
                )));

/*        ItemObjectAction.register(31625,(player, item, obj) -> {
            if (item.getId() == 995) {
//                player.openInterface(InterfaceType.CHATBOX, 1017);
                if (checkFull(player)) {
                    return;
                }
                if (WellofGoodwill.perkName.equalsIgnoreCase("null")){
                    OptionScroll.open(player, "Select a perk to donate towards.", true,
                            Arrays.stream(Perks.values()).map(cd -> new Option(capitalize(cd.name()), () -> player.integerInput("How much would you like to donate towards" + capitalize(cd.name()), amt -> donate(player, amt, cd.name())))).collect(Collectors.toList()));
                } else {
                    player.integerInput("How much would you like to donate towards " + capitalize(perkName), amt -> donate(player, amt, perkName));
                }
            }
        });*/
    }
}
