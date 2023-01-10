package io.ruin;


import io.ruin.model.World;
import io.ruin.model.activities.wellofgoodwill.WellofGoodwill;

public class Configuration {

    public static final String[] OWNERS = {"Ethan", "Brad"};

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
