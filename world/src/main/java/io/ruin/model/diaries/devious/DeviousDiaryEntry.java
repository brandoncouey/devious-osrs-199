package io.ruin.model.diaries.devious;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum DeviousDiaryEntry {


    /** ========================== EASY ========================== */

    VOTE_STORE("Devious Supporter", "Open the Vote Store."),
    DONATION_STORE("Back'n Devious", "Open the Donation Store."),
    SPEAK_TO_DEATH("Oh Dear...", "Speak to death."),
    GE("Monies", "Open the Grand Exchange."),
    HANS("Long Time No Talk", "Learn your age from hans in lumbridge"),
    DRAYNOR_ROOFTOP("Draynor Rooftop", "Complete a lap of the draynor village rooftop course."),
    RELLEKKA_ROOFTOP("Rellekka Rooftop", "Complete %progress%/%amount% laps on the rellekka rooftop course.", 40),
    CHOP_WILLOW_DRAY("Draynor Willows", "Chop %progress%/%amount% willows in draynor village.", 500),
    RANGING_GUILD("Guild of Rangers", "Enter the ranging guild"),
    GNOME_AGILITY("It's Gnomey", "Complete a lap of the gnome agility course"),
    PICK_FLAX_SEERS("Pick'n Flax", "Collect %progress%/%amount% flax from the seer's village flax field.", 75),
    PICKPOCKET_MAN_LUM("Amongst Thieves", "Pickpocket a man or woman in lumbridge"),
    TELEPORT_ESSENCE_LUM("Essence", "Have Sedridor teleport you to the rune essence mine"),
    CRAFT_FIRES("Fire Runes", "Craft some fire runes."),
    EDGEVILLE_TELE("A Home", "Teleport to edgeville."),
    BUY_HOUSE("Homeowner", "Purchase a house from the Estate Agent for 50,000gp."),
    MINE_IRON_LUM("Iron Man", "Mine %progress%/%amount% iron ore at the al-kharid mine.", 50),
    BARBARIAN_AGILITY("Barbarian Agility", "Complete %progress%/%amount% laps at the barbaian agility course.", 45),
    PURCHASE_KITTEN("Here Kitty", "Buy a kitten from gertrude."),
    CRAFT_WATER("Water Runes", "Craft %progress%/%amount% water runes.", 500),
    BUY_CANDLE("Candles", "Buy a candle from the candle maker in catherby."),
    BURN_WILLOW_LOGS("Willows r Burnin", "Burn %progress%/%amount% willow logs.", 100),



    /** ========================== MEDIUM ========================== */
    CRAFT_COSMIC("Cosmic Stars", "Craft 56 cosmic runes simultaneously."),

    PICKPOCKET_MARTIN("Martin's Pockets", "Pickpocket martin the master gardener."),

    MINE_IRON_PIC("Pictorial Iron Miner", "Mine %progress%/%amount% iron ore near piscatoris.", 30),

    FAIRY_RING("Ring of Fairies", "Travel to the wizards' tower by fairy ring."),

    MINE_COAL_FREM("Fremmy Coal", "Mine %progress%/%amount% coal in rellekka.", 120),

    MINING_GOLD("Gold Digger", "Mine %progress%/%amount% gold ore underneath the grand tree.", 100),
    TRAVEL_MISCELLANIA("Miscellania", "Travel to miscellania by fairy ring."),
    CUT_MAGIC_SEERS("Not So Magic Trees", "Cut %progress%/%amount% magic logs behind the ranged guild.", 150),

    AL_KHARID_ROOFTOP("Al-kharid Rooftop", "Complete a lap of the al-kharid rooftop course."),

    BURN_LOGS("Burnin' em Logs", "Burn %progress%/%amount% teak logs on ape atoll.", 75),

    /** ========================== HARD ========================== */

    SUMMER_PIE("Summer Pie", "Bake a summer pie in the cooking guild."),

    ALOT_OF_EARTH("Lotta Earth", "Craft 100 or more earth runes simultaneously."),

    SEERS_AGILITY("Seers Rooftop", "Complete %progress%/%amount% laps on the seers rooftop course.", 50),
    ADDY_ORE("Addy Ore", "Mine %progress%/%amount% adamant ores on jatizso.", 200),
    COOK_MONK("Cook'n Monks", "Cook %progress%/%amount% monkfish in piscatoris.", 200),

    CHOP_MAHOGANY("Mahogany", "Chop %progress%/%amount% mahogany logs in miscellania.", 1500),

    /** ========================== ELITE ========================== */


    RECOVER_CANNON("Losta Cannon", "Recover your lost cannon."),

    FLETCH_MAGIC_BOW("Long Magic Bows", "String %progress%/%amount% magic longbows in catherby", 1000),
    CHOP_MAGIC_AL("Mage Magic Trees", "Chop %progress%/%amount% magic logs at the mage training arena.", 2500),
    PICKPOCKET_ELF("Elf Pockets", "Pickpocket %progress%/%amount% elf's.", 1750),
    SUPER_COMBAT("Super Combat", "Create %progress%/%amount% super combat potions in varrock west bank.", 1250),
    COOK_SHARKS("Specialty Sharks", "Cook %progress%/%amount% sharks in catherby while wearing cooking gauntlets.", 2500),


    /** ========================== END =============================== */






    LUMBRIDGE_TELEPORT("", "Teleport to lumbridge"),

    CRAFT_WATERS("", "Craft 140 or more water runes at once"),
    FFILL_BUCKET("", "Fill %progress%/%amount% buckets with water at the rellekka well."),
    TRAVEL_NEITIZNOT("", "Travel to neitiznot"),
    STEAL_FISH("", "Steal from the rellekka fish stalls: %progress%/%amount% ", 90),

    TROLLHEIM_TELEPORT("", "Teleport to trollheim"),

    WATERBIRTH_TELEPORT("", "Teleport to waterbirth island"),

    CROSS_BALANCE("", "Cross the coal trucks log balance"),
    PEST_CONTROL_TELEPORT("", "Teleport to pest control"),
    FLETCH_OAK_SHORT_WEST("", "Fletch an oak shortbow in the gnome stronghold"),
    SHORTCUT("", "Take the agility shortcut from the grand tree to otto's grotto"),

    TELEPORT_APE_ATOLL("", "Teleport to ape atoll"),
    CAMELOT_TELEPORT("", "Teleport to camelot"),
    STRING_MAPLE_SHORT("", "String a maple shortbow in seers village bank"),
    CATHERY_TELEPORT("", "Teleport to catherby"),
    SPELL_BOOK("Spell Books", "Change your magic spell book at any altar."),

    FOUNTAIN("Fountain of Rejuvenation", "Drink from the Fountain of Rejuvenation."),
    CATACOMBS("", "Enter the Catacombs of Kourend VIA Teleports."),
    MOLCH_ISLAND("", "Teleport to Molch Island."),

    COX("", "Complete the Chambers of Xeric."),

    TEA_STALL("", "Steal from a Tea Stall"),
    TELEPORT_ESSENCE_VAR("", "Have aubury teleport you to the essence mine"),
    MINE_IRON("", "Mine some iron ore in the south east mining patch near varrock: %progress%/%amount% ", 30),
    MAKE_PLANK("", "Make a normal plank at Edgeville"),
    EARTH_RUNES("", "Craft some earth runes"),
    STRONGHOLD("", "Enter the second level of the stronghold of security"),
    JUMP_FENCE("", "Jump over the fence south of varrock"),
    CHAMPIONS_GUILD("", "Enter the champion's guild"),
    DIGSITE("", "Teleport to the digsite using a digsite pendant"),
    TELEPORT_VARROCK("", "Teleport to varrock"),
    OBSTACLE_PIPE("", "Squeeze through the obstacle pipe in edgeville dungeon"),
    ROOFTOP("", "Complete laps on the varrock rooftop agility course", 30),



    ECTOPHIAL("Ectophial", "Teleport using the Ectophial."),

    TELEPORT_ESSENCE_MINE("", "Have brimstail teleport you to the essence mine"),
    TRAVEL_DRAGONTOOTH("Dragontooth", "Travel to dragontooth island.");



    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final int stages;

    public static final Set<DeviousDiaryEntry> SET = EnumSet.allOf(DeviousDiaryEntry.class);

    DeviousDiaryEntry(String name, String description) {
        this(name, description, -1);
    }

    DeviousDiaryEntry(String name, String description, int stages) {
        this.name = name;
        this.description = description;
        this.stages = stages;
    }


    public static Optional<DeviousDiaryEntry> fromName(String name) {
        return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
    }

}
