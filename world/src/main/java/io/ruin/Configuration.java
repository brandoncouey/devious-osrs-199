package io.ruin;


import io.ruin.model.World;
import io.ruin.model.activities.wellofgoodwill.WellofGoodwill;

import java.util.Arrays;

public class Configuration {

    public static final String[] OWNERS = {"float32", "brad"};

    public static final String[] ADMINS = {};

    public static boolean isAdmin(String username) {
        for (String n : OWNERS) {
            if (n.equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    public static int GLOBAL_XP_MULTIPLIER() {
        int percentage = 100;
        if (WellofGoodwill.isActive())
            percentage += 30;
        if (World.weekendExpBoost)
            percentage += 25;
        return percentage;
    }


    public static String STAFF_EVENT = "None";

    public static boolean CONSOLE_MESSAGES = false;

    public static int WORLD = 1;

    public static String CENTRAL_IP = /*localcentral ? "localhost" :*/ "127.0.0.1";

}
