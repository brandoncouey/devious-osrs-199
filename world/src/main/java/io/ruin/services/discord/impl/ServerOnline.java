package io.ruin.services.discord.impl;

import io.ruin.Server;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Hotspot;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerOnline {
    public static void sendDiscordMessage() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Embed embedMessage = new Embed();
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Devious is now ONLINE!", "https://deviousps.com")
                .setColor(new Color(0xB00D03))
                .addField("Players Online:", String.valueOf(World.players.count()), true)
                .addField("Uptime:", TimeUtils.fromMs(Server.currentTick() * Server.tickMs(), false), true)
                .addField("Players in Wild:", String.valueOf(Wilderness.players.size()), true)
                .addField("**Active Bonuses:**", "", false)
                .addField("XP Boost:", World.xpMultiplier + "x", true)
                .addField("Weekend XP:", World.weekendExpBoost ? "Enabled" : "Disabled", true)
                .addField("Double Wintertodt:", World.doubleWintertodt ? "Enabled" : "Disabled", true)
                .addField("Double Slayer:", World.doubleSlayer ? "Enabled" : "Disabled", true)
                .addField("Double PestControl:", World.doublePest ? "Enabled" : "Disabled", true)
                .addField("Double BloodMoney:", World.doublePkp ? "Enabled" : "Disabled", true)
                .addField("Hotspot:", Hotspot.ACTIVE.name, true)
                .addField("When: ", formatter.format(date), true);
        /*
         * Thumbnail
         */
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl("https://deviousps.com/data/assets/logo/fav.png");
        embedMessage.setThumbnail(thumbnail);
        embed.setColor(new java.awt.Color(0xB00D03));
        //DiscordConnection.//jda.getTextChannelById("994599921980289055").sendMessage(embed.build()).queue();
    }
}
