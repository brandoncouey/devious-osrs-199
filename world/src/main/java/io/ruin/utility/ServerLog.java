package io.ruin.utility;


import io.ruin.Server;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerLog {

    private static final SimpleDateFormat loggerDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");

    private static final String LOGS_BASE_DIRECTORY = "data/logs/server/";

    @RequiredArgsConstructor
    public enum Type {

        BANS("players", "bans"),
        UNBAN("players", "unbans"),

        IPBAN("players", "ipbans"),
        MAC_BANNED("players", "macbans"),
        UUID_BANNED("players", "uuid_banned"),
        UNIPBAN("players", "un_ipban"),

        SHADOWMUTED("players", "shadowmutes"),
        MUTES("players", "mutes"),
        IP_MUTED("players", "ip_mutes"),
        UNMUTE("players", "unmutes"),

        KICKED("players", "kicks"),
        JAILED("players", "jails"),
        UNJAILED("players", "unjails"),


        COX_RAIDS_COMPLETION("raids", "cox"),
        TOB_RAIDS_COMPLETION("raids", "tob"),




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

    public static void buildLogFiles() {
        var count = 0;
        try {
            for (var log : Type.values()) {
                var folder = new File(LOGS_BASE_DIRECTORY + log.directoryName.toLowerCase() + "/");
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
            Server.println("Successfully built " + count + " server log files!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(final Type type, final String logMessage) {
        try {
            var file = new File(LOGS_BASE_DIRECTORY  + type.directoryName.toLowerCase() + "/" + type.fileName.toLowerCase() + ".txt");
            Writer writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(loggerDateFormat.format(new Date()) + " - " + logMessage + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
