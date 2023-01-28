package io.ruin.model.diaries.skilling;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum SkillingDiaryEntry {
    //Easy
    KILL_DUCK("Kill a duck in falador park"),
    WESTERN_WALL("Climb over the western falador wall"),
    FILL_BUCKET("Fill a bucket from the pump north of falador west bank"),
    REPAIR_STRUT("Repair a broken strut in the motherlode mine"),
    TRAVEL_ENTRANA("Take the boat to Entrana"),
    MIND_TIARA("Make a mind tiara"),
    FARMING_SHOP(" Browse sarah's farming shop"),

    //Medium
    CRYSTAL_CHEST("Unlock the crystal chest in taverley"),
    PICKPOCKET_GUARD("Pickpocket a falador guard"),
    GOLD_ORE("Mine some gold ore at the crafting guild"),
    NARROW_CREVICE("Squeeze through the crevice in the dwarven mines"),
    ALTAR_OF_GUTHIX("Pray at the guthix altar in taverly"),
    TELEPORT_FALADOR("Teleport to falador"),

    //Hard
    KILL_WYVERN("Kill skeletal wyverns in the asgarnia ice dungeon", 150),
    FALADOR_ROOFTOP("Complete laps on the falador rooftop course", 50),
    KILL_GIANT_MOLE("Kill the giant mole beneath falador park: %totalstage", 50),
    WARRIOR_GUILD("Enter the warrior's guild"),

    //Elite
    STRANGE_FLOOR("Jump over the strange floor in taverley dungeon"),
    KILL_BLACK_DRAGON("Kill black dragons on the upper level of taverley dungeon", 200),
    WEAR_PROSPECTOR("Enter the mining guild wearing full prospector");

    private final String description;

    private final int maximumStages;

    public static final Set<SkillingDiaryEntry> SET = EnumSet.allOf(SkillingDiaryEntry.class);

    SkillingDiaryEntry(String description) {
        this(description, -1);
    }

    SkillingDiaryEntry(String description, int maximumStages) {
        this.description = description;
        this.maximumStages = maximumStages;
    }

    public final String getDescription() {
        return description;
    }

    public static final Optional<SkillingDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

    public int getMaximumStages() {
        return maximumStages;
    }
}
