package io.ruin.model.diaries.minigames;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum MinigamesDiaryEntry {




    /*======================= EASY ======================= */

    ATTEMPT_FIGHT_CAVES("", "Attempt the Tzhaar Fight Caves"),



    /*======================= MEDIUM ======================= */

    PEST_CONTROL("",  "Complete a game of pest control"),


    /*======================= HARD ======================= */

    COMPLETE_FIGHT_CAVES("", "Complete the fight caves minigame"),
    KILL_KET_ZEK("", "Kill a ket-zek in the fight caves"),


    /*======================= ELITE ======================= */

    COMPLETE_INFERNO("", "Complete the inferno minigame"),




    //Easy
    PASS_GATE("", "Pass through shantay pass gate"),
    CUT_CACTUS("", "Cut a cactus for some water"),
    KILL_SNAKES_DESERT("", "Kill some snakes: %totalstage", 20),
    KILL_LIZARDS_DESERT("", "Kill some lizards: %totalstage", 20),
    MINE_CLAY("", "Mine some clay in the north-eastern part of the desert"),



    //Medium
    KILL_BANDIT("", "Kill some bandits: %totalstage", 80),
    PASS_GATE_ROBES("", "Pass through shantay pass wearing desert robes"),
    COMBAT_POTION("", "Create a combat potion in the desert"),
    CHOP_TEAK("", "Chop some teak logs near uzer: %totalstage", 30),
    PICKPOCKET_THUG("", "Pickpocket a menaphite thug: %totalstage", 150),
    ACTIVATE_ANCIENT("", "Activate ancient magics at the pyramid"),
    CAST_BARRAGE("", "Travel to Nardah via magic carpet"),
    KILL_VULTURE("", "Kill some vultures: %totalstage", 70),

    //Hard
    TRAVEL_POLLNIVNEACH("", "Travel to pollnivneach via magic carpet"),


    //Elite
    PRAY_SOPHANEM("", "Restore at least 85 Prayer points when praying at the Altar in Sophanem");

    @Getter
    private final String name;

    private final String description;

    private final int maximumStages;

    public static final Set<MinigamesDiaryEntry> SET = EnumSet.allOf(MinigamesDiaryEntry.class);

    MinigamesDiaryEntry(String name, String description) {
        this(name, description, -1);
    }

    MinigamesDiaryEntry(String name, String description, int maximumStages) {
        this.name = name;
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<MinigamesDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
