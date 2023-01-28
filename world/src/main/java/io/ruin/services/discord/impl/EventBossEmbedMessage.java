package io.ruin.services.discord.impl;

import io.ruin.content.activities.event.impl.eventboss.EventBossType;
import io.ruin.services.discord.DiscordConnection;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class EventBossEmbedMessage {
    public static void sendDiscordMessage(EventBossType boss, String location) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("An Event Boss Has Spawned!");
        eb.setDescription(location);
        eb.setImage(boss.getEmbedUrl());
        eb.setColor(new Color(0xB00D03));
        //DiscordConnection.//jda.getTextChannelById("1059929156718182471").sendMessageEmbeds(eb.build()).queue();
    }
}
