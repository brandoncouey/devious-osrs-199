package io.ruin.model.diaries.morytania;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum MorytaniaDiaryEntry {
    //Easy
    // SBOTT("Have sbott tan something for you"),
    KILL_BANSHEE("Kill a banshee in the slayer tower"),
    KILL_GHOUL("Kill a ghoul"),

    //Medium
    CATCH_LIZARD("Catch a swamp lizard"),
    CANAFIS_ROOFTOP("Complete a lap of the canafis rooftop agility course"),
    TRAVEL_DRAGONTOOTH("Travel to dragontooth island"),
    ECTOPHIAL("Use an ectophial to return to port phasmatys"),

    //Hard
    CLIMB_CHAIN("Climb the advanced spike chain within the slayer tower"),
    KILL_CAVE_HORROR("Kill a cave horror"),
    BURN_MAHOGANY("Burn some mahogany logs on mos le'harmless: %totalstage", 150),
    LOOT_CHEST("Loot the barrows chest"),

    //Elite
    // BARROWS_CHEST("Loot the barrows chest while wearing any complete barrows set"),
    CRAFT_DHIDE("Craft a black dragonhide body in the canafis bank"),
    KILL_ABYSSAL_DEMON("Kill abyssal demons in the slayer tower: %totalstage", 200);

    private final String description;

    private final int maximumStages;

    public static final Set<MorytaniaDiaryEntry> SET = EnumSet.allOf(MorytaniaDiaryEntry.class);

    MorytaniaDiaryEntry(String description) {
        this(description, -1);
    }

    MorytaniaDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<MorytaniaDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
