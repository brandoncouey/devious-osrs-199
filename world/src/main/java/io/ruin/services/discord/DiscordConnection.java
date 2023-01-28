package io.ruin.services.discord;

import io.ruin.model.World;
import io.ruin.process.task.TaskWorker;
import io.ruin.services.discord.impl.AdminCommands;
import io.ruin.services.discord.impl.DiscordAuth;
import io.ruin.services.discord.impl.UserCommands;
import net.dv8tion.//jda.api.JDA;
import net.dv8tion.//jda.api.JDABuilder;
import net.dv8tion.//jda.api.OnlineStatus;
import net.dv8tion.//jda.api.entities.Activity;
import net.dv8tion.//jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordConnection extends ListenerAdapter {

    public static JDA jda;

    String token = "OTkxNDQ5NjkwODAzNTU2MzYz.G8bebi.-j933liFZ5YdgsrLQZ6E1UppdnXElx37iFAyXg";

    public static final String CHANNEL_PUNISHMENTS = "991831244721639464";

    public static final String CHANNEL_OSRS_DONATIONS = "991399358274748526";

    public void initialize() {
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(this);
        builder.addEventListeners(new UserCommands());
        builder.addEventListeners(new DiscordAuth());
        builder.addEventListeners(new AdminCommands());
        try {
            jda = builder.build();
            TaskWorker.startTask(t -> {
                while (true) {
                    t.sleep(60000L);
                    if (!World.isPVPWorld()) {
                        //jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.watching(World.players.count() + " players!"));
                    }
                }
            });
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

}