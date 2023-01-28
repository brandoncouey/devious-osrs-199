package io.ruin.model.diaries.devious;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum DeviousDiaryEntry {

    //Easy
    SPELL_BOOK("Change your Spellbook."),
    SPEAK_TO_DEATH("Speak to Death."),
    FOUNTAIN("Restore your stats at the Fountain."),
    BUY_HOUSE("Purchase a House at from the Estate Agent."),
    GE("Open the Grand Exchange."),
    VOTE_STORE("Open the Vote Store."),
    DONATION_STORE("Open the Donation Store."),

    //Medium
    CATACOMBS("Enter the Catacombs of Kourend VIA Teleports."),
    MOLCH_ISLAND("Teleport to Molch Island."),

    //Hard
    RECOVER_CANNON("Recover your lost cannon"),

    //Elite
    COX("Complete the Chambers of Xeric.");

    @Getter
    private final String description;

    @Getter
    private final int maximumStages;

    public static final Set<DeviousDiaryEntry> SET = EnumSet.allOf(DeviousDiaryEntry.class);

    DeviousDiaryEntry(String description) {
        this(description, -1);
    }

    DeviousDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }


    public static Optional<DeviousDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

}
