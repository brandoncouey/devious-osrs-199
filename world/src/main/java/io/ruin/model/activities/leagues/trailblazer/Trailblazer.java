package io.ruin.model.activities.leagues.trailblazer;

import io.ruin.model.World;
import io.ruin.model.activities.leagues.LeagueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;

public class Trailblazer {

    public static int TRAILBLAZER_INVENTORY = 656;

    public static int TRAILBLAZER_TASKS = 657;

    public static int TRAILBLAZER_RELICS = 655;

    public static int TRAILBLAZER_INFO = 654;

    public static void openTrailblazerTasks(Player player) {

        player.openInterface(InterfaceType.MAIN, TRAILBLAZER_TASKS);

        player.getPacketSender().sendAccessMask(TRAILBLAZER_TASKS, 27, 0, 16, 2);//show menu

        player.getPacketSender().sendAccessMask(TRAILBLAZER_TASKS, 32, 1, 6, 2);//tier

        player.getPacketSender().sendAccessMask(TRAILBLAZER_TASKS, 33, 1, 7, 2);//type

        player.getPacketSender().sendAccessMask(TRAILBLAZER_TASKS, 34, 1, 12, 2);//area

        player.getPacketSender().sendAccessMask(TRAILBLAZER_TASKS, 35, 1, 3, 2);//completed

    }

    static {

        InterfaceHandler.register(TRAILBLAZER_INVENTORY, h -> {

        });

        InterfaceHandler.register(TRAILBLAZER_TASKS, h -> {

        });

        InterfaceHandler.register(TRAILBLAZER_RELICS, h -> {

        });

        InterfaceHandler.register(TRAILBLAZER_INFO, h -> {

        });

        LoginListener.register(player -> {
            player.startEvent(e -> {
                if (World.leagueType == LeagueType.TRAILBLAZER_LEAGUE) {
                    player.sendMessage("<col=8B0000>Welcome to Devious Trailblazer League!");
                }
            });
        });

    }
}
