package io.ruin.model.diaries.lumbridge_draynor;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum LumbridgeDraynorDiaryEntry {
    //Easy
    TELEPORT_ESSENCE_LUM("Have Sedridor teleport you to the rune essence mine"),
    CRAFT_WATER("Craft some water runes"),
    HANS("Learn your age from hans in lumbridge"),
    PICKPOCKET_MAN_LUM("Pickpocket a man or woman in lumbridge"),
    DRAYNOR_ROOFTOP("Complete a lap of the draynor village rooftop course"),
    SLAY_BUG("Slay a cave bug in the lumbridge swamp caves"),
    MINE_IRON_LUM("Mine some iron ore at the al-kharid mine: %totalstage", 50),

    //Medium,
    LUMBRIDGE_TELEPORT("Teleport to lumbridge"),
    AL_KHARID_ROOFTOP("Complete a lap of the al-kharid rooftop course"),
    FAIRY_RING("Travel to the wizards' tower by fairy ring"),
    CHOP_WILLOW_DRAY("Chop some willows in draynor village: %totalstage", 150),
    CRAFT_LAVAS("Craft some fire runes."),

    //Hard,
    EDGEVILLE_TELE("Teleport to edgeville"),
    CRAFT_COSMIC("Craft 56 cosmic runes simultaneously"),
    PICKPOCKET_MARTIN("Pickpocket martin the master gardener"),

    //Elite
    CHOP_MAGIC_AL("Chop some magic logs at the mage training arena: %totalstage", 300),
    CRAFT_WATERS("Craft 140 or more water runes at once"),
    BURN_WILLOW_LOGS("Burn willow logs: %totalstage", 100);

    private final String description;

    private final int maximumStages;

    public static final Set<LumbridgeDraynorDiaryEntry> SET = EnumSet.allOf(LumbridgeDraynorDiaryEntry.class);

    LumbridgeDraynorDiaryEntry(String description) {
        this(description, -1);
    }

    LumbridgeDraynorDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<LumbridgeDraynorDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
