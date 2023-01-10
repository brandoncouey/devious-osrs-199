package io.ruin.model.events;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Getter
@Setter
@RequiredArgsConstructor
public class Event {

    public enum Type { PVP, PVM, SKILLING, MINIGAME }

    @Getter
    @RequiredArgsConstructor
    public enum Task {
        /* Start of PvP */

        //PVP
        HIGHEST_KILLSTREAK("Highest Killstreak", "","", false, 40),//Impl
        TOTAL_KILLS("Top Pker", "","", false, 35),//Impl
        MOST_MONEY_PKED("Most GP Pked", "","", false, 35),//TODO
        MOST_KILLS_49_WILDY("Wildy Hunter", "", "Kill as many players as you can in level 49 wilderness or higher.", false, 25),//Impl
        //Most kills with weapon
        //Most kills in x gear
        //Most kills with default preset
        //Fastest kill?

        /* End of PvP */


        /** Start of PVM */


        //PVM (Bossing)
        FAST_ABYSSAL_SIRE("Abyssal Sire", "", "Kill Abyssal Sire as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_ABYSSAL_SIRE("Abyssal Sire", "","Kill Abyssal Sire as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_CERBERUS("Cerberus", "","Kill Cerberus as fast as you can, The player will the fastest kill will receive the reward.", true, 0),//TODO
        KC_CERBERUS("Cerberus", "","Kill Cerberus as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_CORPOREAL_BEAST("Corporeal Beast", "", "Kill Corporeal Beast as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_CORPOREAL_BEAST("Corporeal Beast", "","Kill Corporeal Beast as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_GALVEK("Galvek", "","Kill Galvek as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_GALVEK("Galvek", "","Kill Galvek as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_GIANT_MOLE("Giant Mole", "","Kill Giant Mole as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_GIANT_MOLE("Giant Mole", "","Kill Giant Mole as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_CHAOS_ELEMENTAL("Giant Mole", "","Kill Chaos Elemental as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_CHAOS_ELEMENTAL("Giant Mole", "","Kill Chaos Elemental as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_KALPHITE_QUEEN("Kalphite Queen", "", "Kill Kalphite Queen as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_KALPHITE_QUEEN("Kalphite Queen", "","Kill Kalphite Queen as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_KBD("King Black Dragon", "","Kill King Black Dragon as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_KBD("King Black Dragon", "","Kill King Black Dragon as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_KRAKEN("Kraken", "","Kill Kraken as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_KRAKEN("Kraken", "","Kill Kraken as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_NIGHTMARE_OF_ASHIHAMA("Nightmare of Ashihama", "","Kill Nightmare of Ashihama as fast as you can, The player will the fastest kill will receive the reward.", true, 10),//TODO
        KC_NIGHTMARE_OF_ASHIHAMA("Nightmare of Ashihama", "","Kill Nightmare of Ashihama as many times as you can, The player with the most kills during the event period will receive the reward.", false, 10),//TODO

        FAST_GENERAL_GRAARDOR("General Graardor", "","Kill General Graardor as fast as you can, The player will the fastest kill will receive the reward.", true, 15),//TODO
        KC_GENERAL_GRAARDOR("General Graardor", "","Kill General Graardor as many times as you can, The player with the most kills during the event period will receive the reward.", false, 15),//TODO

        FAST_KRIL_TSUTSAROTH("Kril Tsutsaroth", "", "Kill Kril Tsutsaroth as fast as you can, The player will the fastest kill will receive the reward.", true, 15),//TODO
        KC_KRIL_TSUTSAROTH("Kril Tsutsaroth", "","Kill Kril Tsutsaroth as many times as you can, The player with the most kills during the event period will receive the reward.", false, 15),//TODO

        FAST_KREEARRA("", "","Kill Kree Arra as fast as you can, The player will the fastest kill will receive the reward.", true, 15),//TODO
        KC_KREEARRA("", "","Kill Kree Arra as many times as you can, The player with the most kills during the event period will receive the reward.", false, 15),//TODO

        FAST_COMMANDER_ZILYANA("Commander Zilyana", "","Kill Commander Zilyana as fast as you can, The player will the fastest kill will receive the reward.", true, 15),//TODO
        KC_COMMANDER_ZILYANA("Commander Zilyana KC", "","Kill Commander Zilyana as many times as you can, The player with the most kills during the event period will receive the reward.", false, 15),//TODO

        FAST_NEX("Nex Timer", "","Kill Nex as fast as you can, The player will the fastest kill will receive the reward.", true, 30),//TODO
        KC_NEX("Nex KC", "","Kill Nex as many times as you can, The player with the most kills during the event period will receive the reward.", false, 30),//TODO

        FAST_VORKATH("Vorkath", "","Kill Vorkath as fast as you can, The player will the fastest kill will receive the reward.", true, 30),//Impl
        KC_VORKATH("Vorkath", "","Kill Vorkath as many times as you can, The player with the most kills during the event period will receive the reward.", false, 30),//Impl

        FAST_ZULRAH("Zul'rah", "","Kill Zulrah as fast as you can, The player will the fastest kill will receive the reward.", true, 30),//Impl
        KC_ZULRAH("Zul'rah", "","Kill Zulrah as many times as you can, The player with the most kills during the event period will receive the reward.", false, 30),//Impl

        //PvM (Slayer) & Other
        MOST_SLAYER_TASKS_COMPLETED("", "","", false, 15),//Impl
        MOST_REVENANTS_KILLED("", "revenant","", false, 25),//Impl
        MOST_WILDY_DRAGONS_KILLED("", "dragon","", false, 25),//Impl
        MOST_WILDY_ELDER_CHAOS_DRUIDS_KILLED("", "elder chaos druid","", false, 15),//Impl

        /* End of PVM */

        /** Start of Skilling */

        //SKILLING
        MOST_GP_PICKPOCKETED("", "","", false, 10),//Impl
        MOST_GNOME_LAPS_RAN("", "","", false, 10),//Impl
        MOST_GEMS_MINED("", "","", false, 5),//Impl
        MOST_MAGIC_LOGS_BURNED("", "","", false, 10),//Impl
        MOST_MAGIC_TREES_CUT("Magical Lumberjack", "","Chop as many magic logs as physically possible.", false, 10),//Impl
        MOST_DRAGON_BONES_BURIED("", "","", false, 5),//Impl
        MOST_DRAGON_DARTS_CRAFTED("", "", "", false, 10),//Impl
        MOST_BLACK_DHIDE_BODIES_CRAFTED("", "","", false, 10),//Impl
        MOST_DIVINE_SUPER_POTIONS_CREATED("", "","", false, 10),//Impl
        MOST_DRAGONFIRE_SHIELDS_CREATED("", "","", false, 15),//Impl
        MOST_GODSWORD_BLADES_CREATED("", "","", false, 15),//Impl

        /** End of Skilling */


        /** Start of Minigames */

        //MINIGAMES
        MOST_PEST_POINTS_EARNED("", "","", false, 15),//TODO
        MOST_IMPS_CAUGHT("", "","", false, 10),//Impl
        MOST_CYCLOPS_KILLED("", "","Kill as many cyclopses as you can inside the Warriors Guild. The player with the most kills will receive the reward.", false,10),//Impl
        MOST_JADS_KILLED("", "","", false, 10),//Impl
        FASTEST_JAD_KILL("", "", "", true, 10),//Impl

        /** End of Minigames */

        ;

        private final String name;
        private final String identifier;
        private final String description;
        private final boolean isTimedEvent;
        private final int rsgpReward;
        //private final Item[] rewards; TODO

        public Type getType() {
            if (ordinal() >= Task.HIGHEST_KILLSTREAK.ordinal() && ordinal() <= Task.MOST_MONEY_PKED.ordinal())
                return Type.PVP;
            else if (ordinal() >= Task.FAST_ABYSSAL_SIRE.ordinal() && ordinal() <= Task.MOST_WILDY_ELDER_CHAOS_DRUIDS_KILLED.ordinal())
                return Type.PVM;
            else if (ordinal() >= Task.MOST_GP_PICKPOCKETED.ordinal() && ordinal() <= Task.MOST_GODSWORD_BLADES_CREATED.ordinal())
                return Type.SKILLING;
            else if (ordinal() >= Task.MOST_PEST_POINTS_EARNED.ordinal() && ordinal() <= Task.FASTEST_JAD_KILL.ordinal())
                return Type.MINIGAME;
            return Type.PVP;
        }

    }

    @Expose private final Task task;
    @Setter private boolean broadcastedStarted;
    @Expose private final long startMilis;
    @Expose private final long endMilis;
    @Expose private boolean rewarded;
    @Expose private Map<String, Long> results = new ConcurrentHashMap<>(); //Have as a float for things such as timers, etc
    @Expose private Map<String, Long> progress = new ConcurrentHashMap<>();


    public String getStartTimeFormatted() {
        // Creating date format
        DateFormat simple = new SimpleDateFormat("dd MMM, hh:mm aa");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(startMilis);

        // Formatting Date according to the
        // given format
        return simple.format(result) + " CST";
    }

    public String getEndTimeFormatted() {
        // Creating date format
        DateFormat simple = new SimpleDateFormat("dd MMM, hh:mm aa");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(endMilis);

        // Formatting Date according to the
        // given format
        return simple.format(result) + " CST";
    }

}
