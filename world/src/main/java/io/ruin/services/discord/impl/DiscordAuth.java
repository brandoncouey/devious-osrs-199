package io.ruin.services.discord.impl;

import net.dv8tion.//jda.api.hooks.ListenerAdapter;


public class DiscordAuth extends ListenerAdapter {


   /* public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
        String message = e.getMessage().getContentRaw();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://81.105.18.0:3306/discordauth","Nightmare", "Toroth!754394");
        Statement stmt = con.createStatement();
        String[] query = {
                "SELECT * FROM `discordauth` where `auth` = "+ "'" +message+ "'",
        };

        for(String q : query) {
            ResultSet rs = stmt.executeQuery(q);

            while (rs.next()) {
                String name = rs.getString("name");
                String auth = rs.getString("auth");
                if (message.equals(auth)) {
                    Player p2 = World.getPlayer(name);
                    assert p2 != null;
                    p2.DiscordAuth = true;
                    p2.sendMessage(Color.BLUE.wrap("[AUTH]")+" You've authenticated with the discord bot!");
                    e.getChannel().sendMessage("Welcome "+name+" your authentication has been succesful enjoy!").queue();
                    SaveAuthCode.saveAuthUser(e.getAuthor().getName(), message);
                    p2.unlock();
                } else {
                e.getChannel().sendMessage("That Auth code doesn't exist!").queue();;
                e.getChannel().sendMessage("If this is an error contact Devious!").queue();;
                }

            }
        }
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
            e.getChannel().sendMessage("Mate what the fuck did you do! you legit broke this shit how?!").queue();;
        }
    }*/

}
