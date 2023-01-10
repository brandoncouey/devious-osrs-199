package io.ruin.model.diaries.varrock;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum VarrockDiaryEntry {
    //Easy
    TEA_STALL("Steal from a Tea Stall"),
    TELEPORT_ESSENCE_VAR("Have aubury teleport you to the essence mine"),
    MINE_IRON("Mine some iron ore in the south east mining patch near varrock: %totalstage", 30),
    MAKE_PLANK("Make a normal plank at Edgeville"),
    EARTH_RUNES("Craft some earth runes"),
    STRONGHOLD("Enter the second level of the stronghold of security"),
    JUMP_FENCE("Jump over the fence south of varrock"),

    //Medium
    CHAMPIONS_GUILD("Enter the champion's guild"),
    SPIRIT_TREE("Use the spirit tree in the north-eastern corner of grand exchange"),
    DIGSITE("Teleport to the digsite using a digsite pendant"),
    PURCHASE_KITTEN("Buy a kitten from gertrude"),
    TELEPORT_VARROCK("Teleport to varrock"),

    //Hard
    OBSTACLE_PIPE("Squeeze through the obstacle pipe in edgeville dungeon"),
    ROOFTOP("Complete laps on the varrock rooftop agility course", 30),

    //Elite
    SUPER_COMBAT("Create a super combat potion in varrock west bank"),
    SUMMER_PIE("Bake a summer pie in the cooking guild"),
    ALOT_OF_EARTH("Craft 100 or more earth runes simultaneously");

    private final String description;

    private final int maximumStages;

    public static final Set<VarrockDiaryEntry> SET = EnumSet.allOf(VarrockDiaryEntry.class);

    VarrockDiaryEntry(String description) {
        this(description, -1);
    }

    VarrockDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static Optional<VarrockDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
