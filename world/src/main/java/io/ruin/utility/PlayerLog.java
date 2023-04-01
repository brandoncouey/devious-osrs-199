package io.ruin.utility;


import io.ruin.Server;
import io.ruin.model.entity.player.Player;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerLog {

    private static final SimpleDateFormat loggerDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");

    private static final String LOGS_BASE_DIRECTORY = "data/logs/players/";

    public static final int HIGH_DROP_VALUE_AMOUNT = 1000000;//1mil

    @RequiredArgsConstructor
    public enum Type {

        CMD_ACTION("actions", "commands"),
        LOGGED_OFF("actions", "logoffs"),
        LOGGED_ON("actions", "logons"),
        KILLED("economy", "deaths"),

        POINT_SHOP("economy/shops", "point_shop_log"),
        DONATOR_SHOP("economy/shops", "donator_shop"),
        NORMAL_SHOP_BUY("economy/shops", "shops_buy"),
        NORMAL_SHOP_SELL("economy/shops", "shops_sell"),
        PUBLIC_CHAT("social", "public_chat"),
        PRIVATE_MESSAGE("social", "private_message"),
        YELL("social", "yells"),
        FRIENDS_CHAT("social", "friends_chat"),
        CLAN_CHAT("social", "clan"),
        TRADING("economy", "trades"),
        ALL_ITEMS("economy", "items"),
        LOYALTY_CHEST("economy", "loyalty_chest"),
        DROPS("pvm", "drops"),
        DROPPED_ITEM("economy", "drops"),
        PVM_INSTANCE("pvm", "instances"),
        DROPS_HIGH_VALUE("economy", "drops_high_value"),

        PICKUP_ITEM("economy", "pickups"),
        PICKUP_ITEM_FROM_PLAYER("economy", "player_item_pickups"),

        KILLED_PLAYER("pvp", "player_kills"),

        STAKED_DUEL("pvp", "staked"),

        COX_REWARDS("economy/raids", "cox_rewards"),
        TOB_REWARDS("economy/raids", "tob_rewards"),

        ;

        /**
         * Represents the directory the log file goes into
         */
        private final String directoryName;

        /**
         * Represents the file name of the log type it goes into
         */
        private final String fileName;
    }

    /**
     * Called upon when the player logs in, it will loop through all of the directories and make them with the file names.
     * @param player
     */
    public static void buildLogFiles(final String player) {
        var count = 0;
        try {
            for (var log : Type.values()) {
                var folder = new File(LOGS_BASE_DIRECTORY + player.toLowerCase() + "/" + log.directoryName.toLowerCase() + "/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                var file = new File(folder.getPath() + "/" + log.fileName.toLowerCase() + ".txt");
                if (!file.exists()) {
                    new BufferedWriter(new FileWriter(file, true));
                    count++;
                }
            }
            if (count > 0)
                Server.println("Successfully built " + count + " player log files for " + player + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(final Type type, final Player player, final String logMessage) {
       log(type, player.getName(), logMessage);
    }

    public static void log(final Type type, final String username, final String logMessage) {
        try {
            var file = new File(LOGS_BASE_DIRECTORY + username.toLowerCase() + "/" + type.directoryName.toLowerCase() + "/" + type.fileName.toLowerCase() + ".txt");
            Writer writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(loggerDateFormat.format(new Date()) + " - " + logMessage + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
