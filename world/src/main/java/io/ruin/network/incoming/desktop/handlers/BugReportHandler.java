package io.ruin.network.incoming.desktop.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.utility.IdHolder;
import net.dv8tion.jda.api.EmbedBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

@IdHolder(ids = {71})
public class BugReportHandler implements Incoming {

    String report1;
    String report2;
    short combinedValue;
    int intStack;

    public void BugReportHandler(String report1, String report2, short combinedValue, int intStack) {
        this.report1 = report1;
        this.report2 = report2;
        this.combinedValue = combinedValue;
        this.intStack = intStack;
    }

    @Override
    public void handle(Player player, InBuffer in, int opcode) {

        combinedValue = (short) in.readShort();
        report1 = in.readString();
        report2 = in.readString();
        intStack = in.readByte();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("A Bug Report Has Been Submitted!");
        eb.addField("Username: ", player.getName(), true);
        eb.addField("Bug: ", report1, true);
        eb.addField("Description: ", report2, true);
        eb.addField("Date: ", formatter.format(date), true);
        eb.setColor(new java.awt.Color(0xB00D03));
        DiscordConnection.jda.getTextChannelById("987045470142611486").sendMessageEmbeds(eb.build()).queue();

//            // TODO discord send message whatever bullshit
//            System.out.println("report1 = " + report1);
//            System.out.println("report2 = " + report2);
    }
}
