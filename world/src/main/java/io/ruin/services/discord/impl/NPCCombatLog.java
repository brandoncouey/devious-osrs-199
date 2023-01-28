package io.ruin.services.discord.impl;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.services.discord.DiscordConnection;
import net.dv8tion.jda.api.EmbedBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NPCCombatLog {
    public static void sendDiscordMessage(Player player, NPC npc) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("A NPC doesn't have a combat file, someone should fix this...");
        eb.addField("Username: ", player.getName(), true);
        eb.addField("When: ", formatter.format(date), true);
        eb.addField("NPC ID: ", String.valueOf(npc.getId()), true);
        eb.addField("NPC Name: ", npc.getDef().name, true);
        eb.setColor(new java.awt.Color(0xB00D03));
        //DiscordConnection.//jda.getTextChannelById("991831248337125376").sendMessage(eb.build()).queue();
    }
}
