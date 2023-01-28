package io.ruin.services.discord.impl;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 3/24/2020
 */

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class AdminCommands extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String message = e.getMessage().getContentRaw().toLowerCase();
        String[] splitMessage = message.split(" ");
        String commandArg = "0";
        if (splitMessage.length != 1) {
            commandArg = splitMessage[1];
        }

/*        if(message.startsWith("::banuser")) {
            for (Player p : World.players) {
                if (p.getUserId() == Integer.parseInt(commandArg)) {
                    Punishment.banuser(p);
                    return;
                }
            }
        }

        if(message.startsWith("::kickuser")) {
            for (Player p : World.players) {
                if (p.getUserId() == Integer.parseInt(commandArg)) {
                    Punishment.kickuser(p);
                    return;
                }
            }
        }

        if(message.startsWith("::muteuser")) {
            for (Player p : World.players) {
                if (p.getUserId() == Integer.parseInt(commandArg)) {
                    Punishment.muteuser(p, TimeUtils.getMinutesToMillis(30), true);
                    return;
                }
            }
        }

        if(message.startsWith("::yeet")) {
            for (Player p : World.players) {
                if (p.getUserId() == Integer.parseInt(commandArg)) {
                    Punishment.uuidBanuser(p);
                    return;
                }
            }
        }*/

        if (message.startsWith("::promoteuser")) {
            int userID = Integer.parseInt(commandArg);
            if (userID == (1 | 4 | 5)) {
                EmbedBuilder fail = new EmbedBuilder();
                fail.setTitle("Nope get the fuck outta here trying that shit.");
                fail.setColor(new Color(0xB00D03));
                fail.addField("You're a cunt", " for even trying that shit, obviously I would have added a check dickhead!", false);
                e.getChannel().sendMessage(fail.build()).queue();
                return;
            }
            for (Player p : World.players) {
                if (p.getUserId() == Integer.parseInt(commandArg)) {
                    PlayerGroup staffGroup = promoteGroup(p);
                    if (staffGroup != null)
                        staffGroup.sync(p, "staff");
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Staff Upgrade!");
                    eb.setColor(new Color(0xB00D03));

                    if (userID < 0) {
                        eb.addField("Nice try!", "You can't edit that account, either it doesn't exist or it is the forum owner.", false);
                    } else {
                        eb.addField("promoting user:", "The user with the following ID: " + userID + ", is being promoted!", false);
                    }
                    eb.addField("Bonus XP!", "The bonus XP is now set to " + userID + "x!", false);
                    e.getChannel().sendMessage(eb.build()).queue();
                    p.forceLogout();
                } else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("User not found");
                    eb.addField("User must be online!", " to perform the task of promoting the user!", false);
                    eb.setColor(new Color(0xB00D03));
                    e.getChannel().sendMessage(eb.build()).queue();
                }
            }
        }

        if (message.startsWith("::demoteuser")) {
            int userID = Integer.parseInt(commandArg);
            if (userID == (1 | 4 | 5)) {
                EmbedBuilder fail = new EmbedBuilder();
                fail.setTitle("Nope get the fuck outta here trying that shit.");
                fail.setColor(new Color(0xB00D03));
                fail.addField("You're a cunt", " for even trying that shit, obviously I would have added a check dickhead!", false);
                e.getChannel().sendMessage(fail.build()).queue();
                return;
            }
            for (Player p : World.players) {
                if (p.getUserId() == Integer.parseInt(commandArg)) {
                    PlayerGroup staffGroup = promoteGroup(p);
                    if (staffGroup != null)
                        staffGroup.sync(p, "staff");
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Staff Upgrade!");
                    eb.setColor(new Color(0xB00D03));

                    if (userID < 0) {
                        eb.addField("Nice try!", "You can't edit that account, either it doesn't exist or it is the forum owner.", false);
                    } else {
                        eb.addField("promoting user:", "The user with the following ID: " + userID + ", is being promoted!", false);
                    }
                    eb.addField("Bonus XP!", "The bonus XP is now set to " + userID + "x!", false);
                    e.getChannel().sendMessage(eb.build()).queue();
                    p.forceLogout();
                } else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("User not found");
                    eb.addField("User must be online!", " to perform the task of promoting the user!", false);
                    eb.setColor(new Color(0xB00D03));
                    e.getChannel().sendMessage(eb.build()).queue();
                }
            }
        }

        if (message.startsWith("::bonusxp")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Bonus XP!");
            eb.setColor(new Color(0xB00D03));
            int amount = Integer.parseInt(commandArg);
            if (amount < 1) {
                eb.addField("Error!", "XP Multiplier cannot be less than 1!", false);
                amount = 1;
            } else if (amount > 4) {
                eb.addField("Error!", "XP Multiplier cannot be greater than 4!", false);
                amount = 4;
            }
            World.boostXp(amount);
            eb.addField("Bonus XP!", "The bonus XP is now set to " + amount + "x!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }

        if (message.equalsIgnoreCase("::doubledrops")) {
            World.toggleDoubleDrops();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Bonus XP!");
            eb.setColor(new Color(0xB00D03));
            eb.addField("Double Drops!", "Double drops are now " + (World.doubleDrops ? "**ENABLED**" : "**DISABLED**") + "!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }

        if (message.equalsIgnoreCase("::doublepc")) {
            World.toggleDoublePest();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("double PC Points!");
            eb.setColor(new Color(0xB00D03));
            eb.addField("Double PC Points!", "Double PC Points are now " + (World.doubleDrops ? "**ENABLED**" : "**DISABLED**") + "!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }

        if (message.equalsIgnoreCase("::doubleslayer")) {
            World.toggleDoubleSlayer();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Double Slayer Points!");
            eb.setColor(new Color(0xB00D03));
            eb.addField("Double Slayer Points!", "Double Slayer Points are now " + (World.doubleDrops ? "**ENABLED**" : "**DISABLED**") + "!", false);
            e.getChannel().sendMessage(eb.build()).queue();
        }
    }

    public static PlayerGroup promoteGroup(Player player) {
        if (player.isGroup(PlayerGroup.REGISTERED) || player.isGroup(PlayerGroup.BETA_TESTER))
            return PlayerGroup.SUPPORT;
        if (player.isGroup(PlayerGroup.SUPPORT))
            return PlayerGroup.MODERATOR;
        if (player.isGroup(PlayerGroup.MODERATOR))
            return PlayerGroup.ADMINISTRATOR;
        return null;
    }

    public static PlayerGroup demoteGroup(Player player) {
        if (player.isGroup(PlayerGroup.SUPPORT))
            return PlayerGroup.REGISTERED;
        if (player.isGroup(PlayerGroup.MODERATOR))
            return PlayerGroup.SUPPORT;
        if (player.isGroup(PlayerGroup.ADMINISTRATOR))
            return PlayerGroup.MODERATOR;
        return null;
    }
}
