package io.ruin.model.diaries.wilderness;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum WildernessDiaryEntry {
    //Easy
    KILL_MAMMOTH("", "Kill a mammoth"),
    WILDERNESS_LEVER("", "Enter the wilderness from the ardougne or edgeville lever"),
    WILDERNESS_ALTAR("", "Pray at the chaos altar in level 38, western wilderness"),
    KILL_EARTH_WARRIOR("", "Kill an earth warrior in the wilderness beneath edgeville"),
    KBD_LAIR("", "Enter the king black dragon lair"),
    MINE_IRON_WILD("", "Mine some iron ore in the wilderness"),
    ABYSS_TELEPORT("", "Have the mage of zamorak teleport you to the abyss"),

    //Medium
    MINE_MITHRIL_WILD("", "Mine some mithril ore in the wilderness"),
    WILDERNESS_GODWARS("", "Enter the wilderness god wars dungeon"),
    WILDERNESS_AGILITY("", "Complete a lap of the wilderness agility course"),
    KILL_GREEN_DRAGON("", "Kill a green dragon"),
    KILL_ANKOU("", "Kill an ankou in the wilderness"),
    KILL_BLOODVELD("", "Kill a bloodveld in the wilderness god wars dungeon"),

    //Hard
    BLACK_SALAMANDER("", "Catch a black salamander"),
    KILL_LAVA_DRAGON("", "Kill a lava dragon"),
    CHAOS_ELEMENTAL("", "Kill the chaos elemental"),
    CRAZY_ARCHAEOLOGIST("", "Kill the crazy archaeologist"),
    CHAOS_FANATIC("", "Kill the chaos fanatic"),
    SCORPIA("", "Kill scorpia"),
    SPIRITUAL_WARRIOR("", "Kill a spiritual warrior in the wilderness god wars dungeon"),

    //Elite
    CALLISTO("", "Kill callisto"),
    VENENATIS("", "Kill venenatis"),
    VETION("", "Kill vet'ion"),
    ROGUES_CHEST("", "Steal from the chest in rogue's castle"),
    SPIRITUAL_MAGE("", "Slay a spiritual mage inside the wilderness god wars dungeon"),
    MAGIC_LOG_WILD("", "Cut some magic logs in the resource area: %totalstage", 200);

    @Getter
    private final String name;

    private final String description;

    private final int maximumStages;

    public static final Set<WildernessDiaryEntry> SET = EnumSet.allOf(WildernessDiaryEntry.class);

    WildernessDiaryEntry(String name, String description) {
        this(name, description, -1);
    }

    WildernessDiaryEntry(String name, String description, int maximumStages) {
        this.name = name;
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<WildernessDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
