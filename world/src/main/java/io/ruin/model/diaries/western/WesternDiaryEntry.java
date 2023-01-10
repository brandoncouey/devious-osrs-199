package io.ruin.model.diaries.western;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum WesternDiaryEntry {
    //Easy
    MINE_IRON("Mine some iron ore near piscatoris: %totalstage", 30),
    GNOME_AGILITY("Complete a lap of the gnome agility course"),
    PEST_CONTROL_TELEPORT("Teleport to pest control"),
    FLETCH_OAK_SHORT_WEST("Fletch an oak shortbow in the gnome stronghold"),
    TELEPORT_ESSENCE_MINE("Have brimstail teleport you to the essence mine"),
    PEST_CONTROL("Complete a game of pest control"),

    //Medium
    SHORTCUT("Take the agility shortcut from the grand tree to otto's grotto"),
    SPIRIT_TREE("Travel to the gnome tree stronghold by spirit tree"),
    MINING_GOLD("Mine some gold ore underneath the grand tree: %totalstage", 100),
    BURN_LOGS("Burn some teak logs on ape atoll: %totalstage", 75),

    //Hard
    COOK_MONK("Cook some monkfish in piscatoris: %totalstage", 200),
    TELEPORT_APE_ATOLL("Teleport to ape atoll"),
    PICKPOCKET_ELF("Pickpocket an elf"),

    //Elite
    KILL_ZULRAH("Kill zulrah"),
    KILL_THERMO("Kill the thermonuclear smoke devil");

    private final String description;

    private final int maximumStages;

    public static final Set<WesternDiaryEntry> SET = EnumSet.allOf(WesternDiaryEntry.class);

    WesternDiaryEntry(String description) {
        this(description, -1);
    }

    WesternDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<WesternDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
