package io.ruin.model.diaries.kandarin;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum KandarinDiaryEntry {
    //Easy
    PICK_FLAX_SEERS("Collect flax from the seer's village flax field: %totalstage", 75),
    BUY_CANDLE("Buy a candle from the candle maker in catherby"),
    CROSS_BALANCE("Cross the coal trucks log balance"),
    //CATCH_MACKAREL("Catch a mackerel in catherby"),

    //Medium
    BARBARIAN_AGILITY("Complete laps at the barbaian agility course: %totalstage", 45),
    RANGING_GUILD("Enter the ranging guild"),
    CAMELOT_TELEPORT("Teleport to camelot"),
    STRING_MAPLE_SHORT("String a maple shortbow in seers village bank"),
    // FISH_SWORD("Fish some swordfish in catherby: %totalstage", 50),

    //Hard
    SEERS_AGILITY("Complete laps on the seers rooftop course: %totalstage", 50),
    KILL_MITHRIL_DRAGON_KAN("Kill mithril dragons: %totalstage", 115),
    CUT_MAGIC_SEERS("Cut some magic logs behind the ranged guild: %totalstage", 150),
    FLETCH_MAGIC_BOW("String a magic longbow in catherby"),

    //Elite
    COOK_SHARKS("Cook sharks in catherby while wearing cooking gauntlets: %totalstage", 10),
    CATHERY_TELEPORT("Teleport to catherby");

    private final String description;

    private final int maximumStages;

    public static final Set<KandarinDiaryEntry> SET = EnumSet.allOf(KandarinDiaryEntry.class);

    KandarinDiaryEntry(String description) {
        this(description, -1);
    }

    KandarinDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<KandarinDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
