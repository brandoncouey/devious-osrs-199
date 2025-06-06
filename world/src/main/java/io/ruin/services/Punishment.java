package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.utils.IPBans;
import io.ruin.api.utils.IPMute;
import io.ruin.api.utils.StringUtils;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.World;
import io.ruin.model.activities.jail.Jail;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerFile;
import io.ruin.process.task.TaskWorker;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.utility.Misc;
import io.ruin.utility.ServerLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Date;

public class Punishment {

    /**
     * Kicking (Admins only to prevent inevitable problems)
     */

    public static void kick(Player p1, Player p2) {
        p1.sendMessage("Kicking " + p2.getName() + "...");
        TaskWorker.startTask(t -> {
            if (!p2.isOnline())
                return;
            p2.lock();
            p2.resetActions(true, true, true);
            p2.forceLogout();
            logPunishment(p1, p2, -2, "Kicked");
        });
    }

    /**
     * Jailing - tbh i want to come back and fix this jail system up.. was kinda rushed..
     * something about coding punishment cmds makes me depressed i swear idk why l0l
     *
     * @return
     */

    public static void jail(Player p1, Player p2, int ores) {
        if (p2.tournament != null) {
            p1.sendMessage("You can't jail a player while they are within a tournament.");
            return;
        }

        if (p2.lmsSession != null) {
            p2.lmsSession.eliminatePlayer(p2);
        }

        p1.sendMessage("Jailing " + p2.getName() + " to " + ores + " ores...");
        p2.jailerName = p1.getName();
        p2.jailOresAssigned = ores;
        p2.jailOresCollected = 0;
        World.startEvent(e -> {
            while (p2.isLocked())
                e.delay(1); //wait
            if (!p2.isOnline())
                return;
            p2.resetActions(true, true, true);
            Jail.startEvent(p2);
        });
        logPunishment(p1, p2, -2, "jailed", new MessageEmbed.Field("Ores", String.valueOf(ores), true));
    }

    public static void jail(Player player, NPC npc, int ores) {
        if (player.lmsSession != null) {
            player.lmsSession.eliminatePlayer(player);
        }
        player.jailerName = npc.getDef().name;
        player.jailOresAssigned = ores;
        player.jailOresCollected = 0;
        World.startEvent(e -> {
            if (!player.isOnline())
                return;
            player.resetActions(true, true, true);
            Jail.startEvent(player);
        });
    }

    public static void unjail(Player p1, Player p2) {
        p2.jailOresAssigned = 0; //this will automatically finish their sentence lol
        p2.jailerName = null;
        p1.sendMessage("You unjailed " + p2.getName() + ".");
        logPunishment(p1, p2, -2, "unjailed");
    }

    /**
     * Muting
     */

    public static void mute(Player p1, Player p2, long time, boolean shadow) {
        p2.muteEnd = time;
        p2.shadowMute = shadow;
        p1.sendMessage(p2.getName() + " is now muted.");
        logPunishment(p1, p2, time, shadow ? "shadowmuted" : "muted");
    }

    public static void unmute(Player p1, Player p2) {
        if (p2.muteEnd == 0) {
            p1.sendMessage(p2.getName() + " isn't muted.");
            return;
        }
        if (p2.shadowMute && !p1.isAdmin()) {
            p1.sendMessage("This player can only be unmuted by an admin.");
            return;
        }
        p2.muteEnd = 0;
        if (p2.shadowMute)
            p2.shadowMute = false;
        else
            p2.sendMessage("Your mute has been lifted.");
        p1.sendMessage(p2.getName() + " is now unmuted.");
        logPunishment(p1, p2, -2, "unmuted");
    }
    public static void unban(Player p1, String search) {
        Player p2 = PlayerFile.load(Misc.formatStringFormal(search).replace(" ", "_"));
        if (p2 != null) {
            p2.setPermBanned(false);
            PlayerFile.save(p2, 0);
            World.sendStaffMessage(p1.getName () + " has just unbanned " + p2.getName() + ".");
            p1.sendMessage(p2.getName() + " is now unbanned.");
            logPunishment(p1, p2, -2, "unbanned");
        } else {
            p1.sendMessage("Unable to load player file " + search);
        }
    }

    public static boolean isMuted(Player player) {
        return player.muteEnd == -1 || (player.muteEnd > 0 && System.currentTimeMillis() < player.muteEnd);
    }

    /**
     * Banning
     */

    public static void ban(Player p1, Player p2) {
        p1.sendMessage("Attemping to ban " + p2.getName() + "...");
        Server.worker.execute(() -> {
            p2.setPermBanned(true);
            p2.forceLogout();
            p1.sendMessage("Successfully banned " + p2.getName() + "!");
            logPunishment(p1, p2, -1, "banned");
        });
    }

    public static void macBan(Player staffMember, Player punishedUser) {
        IPBans.requestBan(punishedUser.getName(), punishedUser.getIp());
        ban(staffMember, punishedUser);
        World.getPlayerStream().filter(player -> player.getMACAddress().equalsIgnoreCase(punishedUser.getMACAddress())).forEach(Player::forceLogout);
        logPunishment(staffMember, punishedUser, -1, "MAC banned");

    }

    public static void uuidBan(Player staffMember, Player punishedUser) {
        IPBans.requestBan(punishedUser.getName(), punishedUser.getIp());
        ban(staffMember, punishedUser);
        World.getPlayerStream().filter(player -> player.getUUID().equalsIgnoreCase(punishedUser.getUUID())).forEach(Player::forceLogout);
        logPunishment(staffMember, punishedUser, -1, "UUID banned");
//        CentralClient.reloadBans();
    }

    public static void ipMute(Player staffMember, Player punishedUser) {
        IPMute.requestMute(punishedUser.getName(), punishedUser.getIp());
        mute(staffMember, punishedUser, Long.MAX_VALUE, true);
        logPunishment(staffMember, punishedUser, Long.MAX_VALUE, "IP Muted");
    }

    public static void ipBan(Player staffMember, Player punishedUser) {
        IPBans.requestBan(punishedUser.getName(), punishedUser.getIp());
        ban(staffMember, punishedUser);
        World.getPlayerStream().filter(player -> player.getIp().equalsIgnoreCase(punishedUser.getIp())).forEach(Player::forceLogout);
        logPunishment(staffMember, punishedUser, -1, "IP banned");
//        CentralClient.reloadBans();
    }

    private static void logPunishment(Player staff, Player victim, long time, String type, MessageEmbed.Field... fields) {
        String until;

        if (time == -1 || time == -2) {
            until = "Never (perm).";
        } else {
            until = TimeUtils.dateFormat.format(new Date(time))
                    + " (in " + TimeUtils.fromMs(time - System.currentTimeMillis(), false) + ")";
        }

        ServerLog.Type log = null;
        switch (type) {
            case "IP banned":
                log = ServerLog.Type.IPBAN;
                break;
            case "Kicked":
                log = ServerLog.Type.KICKED;
                break;
            case "jailed":
                log = ServerLog.Type.JAILED;
                break;
            case "unjailed":
                log = ServerLog.Type.UNJAILED;
                break;
            case "unmuted":
                log = ServerLog.Type.UNMUTE;
                break;
            case "banned":
                log = ServerLog.Type.BANS;
                break;
            case "MAC banned":
                log = ServerLog.Type.MAC_BANNED;
                break;
            case "UUID banned":
                log = ServerLog.Type.UUID_BANNED;
                break;
            case "IP Muted":
                log = ServerLog.Type.IP_MUTED;
                break;
            case "shadowmuted":
                log = ServerLog.Type.SHADOWMUTED;
                break;
            case "muted":
                log = ServerLog.Type.MUTES;
                break;
            case "unbanned":
                log = ServerLog.Type.UNBAN;
                break;
        }
        if (log != null)
             ServerLog.log(log, "Staff Name=" + staff.getName() + ", Staff IP=" + staff.getIp() + ", Target=" + victim.getName() + ", Duration=" + until + ", Time=" + Misc.formatTime(System.currentTimeMillis()));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(String.format("%s has been %s by %s", victim.getName(), type, staff.getName()));
        builder.addField("Name", victim.getName(), true);
        builder.addField("Staff member", staff.getName(), true);
        builder.addField("Punishment", StringUtils.capitalizeFirst(type), true);

        if (time != -2)
            builder.addField("Expires", until, true);

        if (fields != null && fields.length > 0) {
            for (MessageEmbed.Field field : fields) {
                builder.addField(field);
            }
        }
        if (!World.isDev()) {
            //DiscordConnection.//jda.getTextChannelById(DiscordConnection.CHANNEL_PUNISHMENTS).sendMessageEmbeds(builder.build()).queue();
        }
    }

}