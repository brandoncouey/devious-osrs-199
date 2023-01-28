package io.ruin.services.discord.impl;

import io.ruin.services.discord.DiscordConnection;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerOffline {
    public static void sendDiscordMessage() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Embed embedMessage = new Embed();
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Devious is now OFFLINE!", "https://deviousps.com")
                .setColor(new Color(0xB00D03))
                .addField("When: ", formatter.format(date), true);
        /*
         * Thumbnail
         */
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl("https://deviousps.com/data/assets/logo/fav.png");
        embedMessage.setThumbnail(thumbnail);
        embed.setColor(new java.awt.Color(0xB00D03));
        //DiscordConnection.jda.getTextChannelById("994599921980289055").sendMessage(embed.build()).queue();
    }
}
