package io.ruin.model.diaries.fremennik;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum FremennikDiaryEntry {
    //Easy
    KILL_ROCK_CRAB("Kill some rock crabs: %totalstage", 50),
    FFILL_BUCKET("Fill some buckets with water at the rellekka well: %totalstage"),
    KILL_BRINE_RAT("Slay some brine rats: %totalstage", 50),
    KILL_MARKET_GUARD("Slaught some Market Guards"),
    TRAVEL_NEITIZNOT("Travel to neitiznot"),

    //Medium
    MINE_COAL_FREM("Mine some coal in rellekka: %totalstage", 120),
    STEAL_FISH("Steal from the rellekka fish stalls: %totalstage", 90),
    CHOP_MAHOGANY("Chop some mahogany logs in miscellania: %totalstage", 130),
    TRAVEL_MISCELLANIA("Travel to miscellania by fairy ring"),

    //Hard
    TROLLHEIM_TELEPORT("Teleport to trollheim"),
    RELLEKKA_ROOFTOP("Complete laps on the rellekka rooftop course: %totalstage", 40),
    ADDY_ORE("Mine adamant ores on jatizso: %totalstage", 200),
    WATERBIRTH_TELEPORT("Teleport to waterbirth island"),
    DAGANNOTH_KINGS("Kill some dagannoth kings: %totalstage", 30),

    //Elite
    KILL_BANDOS("Kill bandos god wars boss: %totalstage", 10),
    KILL_ARMADYL("Kill armadyl god wars boss: %totalstage", 10),
    KILL_ZAMORAK("Kill zamorak god wars boss: %totalstage", 10),
    KILL_SARADOMIN("Kill saradomin god wars boss: %totalstage", 10);

    private final String description;

    private final int maximumStages;

    public static final Set<FremennikDiaryEntry> SET = EnumSet.allOf(FremennikDiaryEntry.class);

    FremennikDiaryEntry(String description) {
        this(description, -1);
    }

    FremennikDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<FremennikDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
