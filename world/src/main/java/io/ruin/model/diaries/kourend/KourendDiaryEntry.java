package io.ruin.model.diaries.kourend;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum KourendDiaryEntry {

    //Easy
    SPELL_BOOK("Change your Spellbook."),
    SPEAK_TO_DEATH("Speak to Death."),
    FOUNTAIN("Restore your stats at the Fountain."),
    BUY_HOUSE("Purchase a House at Edge."),
    GE("Open the Grand Exchange at Edge."),
    VOTE_STORE("Open the Vote Store at Edge."),
    DONATION_STORE("Open the Donation Store at Edge."),
    //MITHRIL_SEED("Plant a Mithril Seed in the Gamble Area."),

    //Medium
    //   KOUREND_SARIM("Travel by boat from Kourend to Port Sarim."),
    CATACOMBS("Enter the Catacombs of Kourend VIA Teleports."),
    MOLCH_ISLAND("Teleport to Molch Island."),
    LOVAKENGJ("Smelt a Steel Bar in Lovakengj."),
    KILL_JELLIES("Kill Jellies in the Catacombs.", 25),
    COOK_LOBSTER("Cook a Lobster in the Hosidius Kitchen."),
    KILL_SANDCRAB("Kill a Sand Crab."),
    CATCH_COPPER("Catch a Copper Longtail in the Kourend Woodland."),

    //Hard
    WOODCUTTING_GUILD("Enter the Woodcutting Guild."),
    KILL_LIZARDSHAMAN("Kill a Lizardman Shaman."),
    STEAL_SILVER("Steal from the Silver Stall in Hosidius.", 50),
    DBONE_ALTAR("Use a Dragon Bone on the Altar @ Edge."),
    WINTERTODT("Complete a game of Wintertodt."),
    KOUREND("Teleport to Kourend."),
    BLOOD_RUNE("Craft a Blood Rune."),
    KILL_NECHRYAEL("Kill Nechryael in the Catacombs.", 25),

    //Elite
    CHOP_YEW("Chop Yew Logs in the Woodcutting Guild.", 50),
    SOUL_RUNE("Craft a Soul Rune."),
    KILL_ABYSSAL("Kill Abyssal Demons in the Catacombs.", 50),
    KILL_SKOTIZO("Kill Skotizo.", 1),
    ARCLIGHT("Create a Arclight."),
    COX("Complete the Chambers of Xeric.");

    private final String description;

    private final int maximumStages;

    public static final Set<KourendDiaryEntry> SET = EnumSet.allOf(KourendDiaryEntry.class);

    KourendDiaryEntry(String description) {
        this(description, -1);
    }

    KourendDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<KourendDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
