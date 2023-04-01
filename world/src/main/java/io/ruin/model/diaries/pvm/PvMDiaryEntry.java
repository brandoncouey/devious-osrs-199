package io.ruin.model.diaries.pvm;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum PvMDiaryEntry {
    //Easy
    MINE_GOLD("", "Mine some gold from the rocks on the north-west peninsula of Karamja: %totalstage", 60),
    TELEPORT_TO_KARAMJA("", "Use an amulet of glory teleport to karamja"),
    SAIL_TO_ARDOUGNE("", "Travel to ardougne from the port in brimhaven"),
    SAIL_TO_PORT_SARIM("", "Travel to port sarim via the dock, east of musa point"),

    KILL_DUST_DEVIL("", "Kill some dust devils, wearing a slayer helmet: %totalstage", 80),

    KILL_ROCK_CRAB("", "Kill some rock crabs: %totalstage", 50),

    KILL_BRINE_RAT("", "Slay some brine rats: %totalstage", 50),
    KILL_MARKET_GUARD("", "Slaught some Market Guards"),
    SLAY_BUG("", "Slay a cave bug in the lumbridge swamp caves"),
    KILL_BANSHEE("", "Kill a banshee in the slayer tower"),
    KILL_GHOUL("", "Kill a ghoul"),

    KILL_CAVE_HORROR("", "Kill a cave horror"),

    LOOT_CHEST("", "Loot the barrows chest"),

    //Medium
    DURADEL("", "Be assigned a slayer task by duradel"),
    CHOP_VINE("", "Chop the vines to gain deeper access to brimhaven dungeon"),
    TRAVEL_PORT_KHAZARD("", "Charter the lady of the waves from south of cairn isle to port khazard"),

    //Hard

    KILL_METAL_DRAGON("", "Kill a metal dragon in the brimhaven dungeon"),

    KILL_ZULRAH("", "Kill zulrah"),
    KILL_THERMO("", "Kill the thermonuclear smoke devil"),

    //Elite
    CRAFT_NATURES("", "Craft 56 nature runes at once"),
    ANTI_VENOM("", "Create an antivenom potion whilst standing in the horse shoe mine"),


    DAGANNOTH_KINGS("", "Kill some dagannoth kings: %totalstage", 30),

    KILL_MITHRIL_DRAGON_KAN("", "Kill mithril dragons: %totalstage", 115),

    KILL_ABYSSAL_DEMON("", "Kill abyssal demons in the slayer tower: %totalstage", 200),

    //Elite
    KILL_BANDOS("", "Kill bandos god wars boss: %totalstage", 10),
    KILL_ARMADYL("", "Kill armadyl god wars boss: %totalstage", 10),
    KILL_ZAMORAK("", "Kill zamorak god wars boss: %totalstage", 10),
    KILL_SARADOMIN("", "Kill saradomin god wars boss: %totalstage", 10);

    @Getter
    private final String name;

    private final String description;

    private final int maximumStages;

    public static final Set<PvMDiaryEntry> SET = EnumSet.allOf(PvMDiaryEntry.class);

    PvMDiaryEntry(String name, String description) {
        this(name, description, -1);
    }

    PvMDiaryEntry(String name, String description, int maximumStages) {
        this.name = name;
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<PvMDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
