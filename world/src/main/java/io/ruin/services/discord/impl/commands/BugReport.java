package io.ruin.services.discord.impl.commands;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;

public class BugReport {


    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 156);
        player.getPacketSender().sendClientScript(1095, "s", "Please check the <col=ffffff>official Devious discord</col> (with the command '<col=ffffff>::discord</col>') before reporting issues, as the developers cannot help with bugs that they're unaware of.<br><br>Please provide as much detail about what you were doing before an issue occurred.");
        player.getPacketSender().sendAccessMask(156, 13, 0, 1, 2);
        player.getPacketSender().sendAccessMask(156, 19, 0, 1, 2);

    }

    public static void handleBugReportButton(Player player) {

        String a = "aceabc";
        String b = "12345";
        byte d = 0;

        short c = (short) (a.length() + b.length() + 1);
        player.getPacketSender().submitBugReport(c, a, b, d);

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        EmbedBuilder eb = new EmbedBuilder();
//        eb.setTitle("A Bug Report Has Been Submitted!");
//        eb.addField("Username: ", player.getName(),true);
//        eb.addField("Bug: ", a,true);
//        eb.addField("Description: ", b, true);
//        eb.addField("Date: ", formatter.format(date), true);
//        eb.setColor(new java.awt.Color(0xB00D03));
//        DiscordConnection.jda.getTextChannelById("991831233468305449").sendMessage(eb.build()).queue();

        // 156.13
        // 156.19
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
    }

    static {
        InterfaceHandler.register(156, h -> {
            h.actions[23] = (DefaultAction) (player, bug, report, bite) -> handleBugReportButton(player);
        });
    }
}
