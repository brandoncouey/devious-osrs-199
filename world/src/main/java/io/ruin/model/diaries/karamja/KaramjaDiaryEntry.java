package io.ruin.model.diaries.karamja;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum KaramjaDiaryEntry {
    //Easy
    MINE_GOLD("Mine some gold from the rocks on the north-west peninsula of Karamja: %totalstage", 60),
    TELEPORT_TO_KARAMJA("Use an amulet of glory teleport to karamja"),
    SAIL_TO_ARDOUGNE("Travel to ardougne from the port in brimhaven"),
    SAIL_TO_PORT_SARIM("Travel to port sarim via the dock, east of musa point"),
    ATTEMPT_FIGHT_CAVES("Attempt the Tzhaar Fight Caves"),

    //Medium
    DURADEL("Be assigned a slayer task by duradel"),
    CHOP_VINE("Chop the vines to gain deeper access to brimhaven dungeon"),
    TRAVEL_PORT_KHAZARD("Charter the lady of the waves from south of cairn isle to port khazard"),

    //Hard
    COMPLETE_FIGHT_CAVES("Complete the fight caves minigame"),
    KILL_KET_ZEK("Kill a ket-zek in the fight caves"),
    KILL_METAL_DRAGON("Kill a metal dragon in the brimhaven dungeon"),

    //Elite
    CRAFT_NATURES("Craft 56 nature runes at once"),
    ANTI_VENOM("Create an antivenom potion whilst standing in the horse shoe mine"),
    COMPLETE_INFERNO("Complete the inferno minigame");

    private final String description;

    private final int maximumStages;

    public static final Set<KaramjaDiaryEntry> SET = EnumSet.allOf(KaramjaDiaryEntry.class);

    KaramjaDiaryEntry(String description) {
        this(description, -1);
    }

    KaramjaDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<KaramjaDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
