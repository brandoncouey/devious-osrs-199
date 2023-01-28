package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.combat.Hit;
import io.ruin.model.diaries.pvp.PvPDiaryEntry;
import io.ruin.model.diaries.minigames.MinigamesDiaryEntry;
import io.ruin.model.diaries.skilling.SkillingDiaryEntry;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import io.ruin.model.diaries.western.WesternDiaryEntry;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.actions.impl.skillcapes.ThievingSkillCape;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.BotPrevention;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public enum PickPocket {

    MAN(1, 8.0, 422, 5, 1, "man's", PlayerCounter.PICKPOCKETED_MAN,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 500, 700, 1)  //Coins
            )),
    FARMER(10, 14.5, 433, 5, 1, "farmer's", PlayerCounter.PICKPOCKETED_FARMER,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 600, 1000, 6), //Coins
                    new LootItem(5318, 1, 1) //Potato seed
            )),
    HAM(15, 18.5, 433, 4, 1, "H.A.M member's", PlayerCounter.PICKPOCKETED_HAM_MEMBER,
            new LootTable().addTable(1,
                    new LootItem(882, 16, 60), //Coins
                    new LootItem(1351, 1, 1), //Coins
                    new LootItem(1265, 1, 1), //Coins
                    new LootItem(1349, 1, 1), //Coins
                    new LootItem(1267, 1, 1), //Coins
                    new LootItem(886, 20, 1), //Coins
                    new LootItem(1353, 1, 1), //Coins
                    new LootItem(1207, 1, 1), //Coins
                    new LootItem(1129, 1, 1), //Coins
                    new LootItem(4302, 1, 1), //Coins
                    new LootItem(4298, 1, 1), //Coins
                    new LootItem(4300, 1, 1), //Coins
                    new LootItem(4304, 1, 1), //Coins
                    new LootItem(4306, 1, 1), //Coins
                    new LootItem(4308, 1, 1), //Coins
                    new LootItem(4310, 1, 1), //Coins
                    new LootItem(COINS_995, 1200, 1), //Coins
                    new LootItem(319, 1, 1), //Coins
                    new LootItem(2138, 1, 1), //Coins
                    new LootItem(453, 1, 1), //Coins
                    new LootItem(440, 1, 1), //Coins
                    new LootItem(1739, 1, 1), //Coins
                    new LootItem(314, 5, 1), //Coins
                    new LootItem(1734, 6, 1), //Coins
                    new LootItem(1733, 1, 1), //Coins
                    new LootItem(1511, 1, 1), //Coins
                    new LootItem(686, 1, 1), //Coins
                    new LootItem(697, 1, 1), //Coins
                    new LootItem(1625, 1, 1), //Coins
                    new LootItem(1627, 1, 1), //Coins
                    new LootItem(199, 5, 1), //Coins
                    new LootItem(201, 6, 1), //Coins
                    new LootItem(203, 1, 1) //Coins
            )),
    WARRIOR(25, 26.0, 386, 5, 2, "warrior's", PlayerCounter.PICKPOCKETED_WARRIOR,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 900, 1200, 1) //Coins
            )),
    ROGUE(32, 35.5, 422, 5, 2, "rogue's", PlayerCounter.PICKPOCKETED_ROGUE,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 2500, 5000, 10), //Coins
                    new LootItem(556, 8, 5),  //Air runes
                    new LootItem(1933, 1, 4), //Jug of wine
                    new LootItem(1219, 1, 3), //Iron dagger(p)
                    new LootItem(1523, 1, 1)  //Lockpick
            )),
    MASTER_FARMER(38, 43.0, 386, 5, 3, "master farmer's", PlayerCounter.PICKPOCKETED_MASTER_FARMER,
            new LootTable().addTable(1,
                    new LootItem(5318, 1, 4, 8), //Potato seed
                    new LootItem(5319, 1, 3, 5), //Onion seed
                    new LootItem(5324, 1, 3, 5), //Cabbage seed
                    new LootItem(5322, 1, 2, 5), //Tomato seed
                    new LootItem(5320, 1, 2, 5), //Sweetcorn seed
                    new LootItem(5096, 1, 5), //Marigold seed
                    new LootItem(5097, 1, 5), //Rosemary seed
                    new LootItem(5098, 1, 5), //Nasturtium seed
                    new LootItem(5291, 1, 5), //Guam seed
                    new LootItem(5292, 1, 5), //Marrentill seed
                    new LootItem(5293, 1, 5), //Tarromin seed
                    new LootItem(5294, 1, 5), //Harralander seed
                    new LootItem(5323, 1, 3), //Strawberry seed
                    new LootItem(5321, 1, 3), //Watermelon seed
                    new LootItem(5100, 1, 3), //Limpwurt seed
                    new LootItem(5295, 1, 2), //Ranarr seed
                    new LootItem(5296, 1, 2), //Toadflax seed
                    new LootItem(5297, 1, 2), //Irit seed
                    new LootItem(5298, 1, 1), //Avantoe seed
                    new LootItem(5299, 1, 1), //Kwuarm seed
                    new LootItem(5300, 1, 1), //Snapdragon seed
                    new LootItem(5301, 1, 1), //Cadantine seed
                    new LootItem(5302, 1, 1), //Lantadyme seed
                    new LootItem(5303, 1, 1), //Dwarf weed seed
                    new LootItem(5304, 1, 1)  //Torstol seed
            )),
    MASTER_GARDENER(38, 43.0, 386, 5, 3, "master gardener", PlayerCounter.PICKPOCKETED_MASTER_FARMER,
            new LootTable().addTable(1,
                    new LootItem(5318, 1, 4, 8), //Potato seed
                    new LootItem(5319, 1, 3, 5), //Onion seed
                    new LootItem(5324, 1, 3, 5), //Cabbage seed
                    new LootItem(5322, 1, 2, 5), //Tomato seed
                    new LootItem(5320, 1, 2, 5), //Sweetcorn seed
                    new LootItem(5096, 1, 5), //Marigold seed
                    new LootItem(5097, 1, 5), //Rosemary seed
                    new LootItem(5098, 1, 5), //Nasturtium seed
                    new LootItem(5291, 1, 5), //Guam seed
                    new LootItem(5292, 1, 5), //Marrentill seed
                    new LootItem(5293, 1, 5), //Tarromin seed
                    new LootItem(5294, 1, 5), //Harralander seed
                    new LootItem(5323, 1, 3), //Strawberry seed
                    new LootItem(5321, 1, 3), //Watermelon seed
                    new LootItem(5100, 1, 3), //Limpwurt seed
                    new LootItem(5295, 1, 2), //Ranarr seed
                    new LootItem(5296, 1, 2), //Toadflax seed
                    new LootItem(5297, 1, 2), //Irit seed
                    new LootItem(5298, 1, 1), //Avantoe seed
                    new LootItem(5299, 1, 1), //Kwuarm seed
                    new LootItem(5300, 1, 1), //Snapdragon seed
                    new LootItem(5301, 1, 1), //Cadantine seed
                    new LootItem(5302, 1, 1), //Lantadyme seed
                    new LootItem(5303, 1, 1), //Dwarf weed seed
                    new LootItem(5304, 1, 1)  //Torstol seed
            )),
    GUARD(40, 46.8, 386, 5, 2, "guard's", PlayerCounter.PICKPOCKETED_GUARD,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 1500, 2000, 1) //Coins
            )),
    BANDIT(53, 79.5, 422, 5, 3, "bandit's", PlayerCounter.PICKPOCKETED_BANDIT,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 2000, 2800, 8), //Coins
                    new LootItem(175, 1, 3),  //Antipoison
                    new LootItem(1523, 1, 1)  //Lockpick
            )),
    KNIGHT(55, 84.3, 386, 5, 3, "knight's", PlayerCounter.PICKPOCKETED_KNIGHT,
            new LootTable().addTable(1,
                    new LootItem(22524, 1, 1) //Coins
            )),
    MENAPHITE_THUG(65, 105.15, 386, 5, 2, "guard's", PlayerCounter.PICKPOCKETED_GUARD,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 1500, 2000, 1) //Coins
            )),
    PALADIN(70, 151.75, 386, 5, 3, "paladin's", PlayerCounter.PICKPOCKETED_PALADIN,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 4000, 4500, 6), //Coins
                    new LootItem(562, 2, 3)   //Chaos runes
            )),
    GNOME(75, 198.5, 201, 5, 1, "gnome's", PlayerCounter.PICKPOCKETED_GNOME,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 4000, 4500, 16), //Coins
                    new LootItem(5321, 3, 8),   //Watermelon seed
                    new LootItem(5100, 1, 8),   //Limpwurt seed
                    new LootItem(5295, 1, 7),   //Ranarr seed
                    new LootItem(5296, 1, 7),   //Toadflax seed
                    new LootItem(5297, 1, 7),   //Irit seed
                    new LootItem(5298, 1, 7),   //Avantoe seed
                    new LootItem(5299, 1, 7),   //Kwuarm seed
                    new LootItem(5300, 1, 7),   //Snapdragon seed
                    new LootItem(5301, 1, 7),   //Cadantine seed
                    new LootItem(5302, 1, 6),   //Lantadyme seed
                    new LootItem(5303, 1, 5),   //Dwarf weed seed
                    new LootItem(5304, 1, 4),   //Torstol seed
                    new LootItem(5312, 1, 4),   //Acorn
                    new LootItem(5313, 1, 3),   //Willow seed
                    new LootItem(5314, 1, 4),   //Maple seed
                    new LootItem(5315, 1, 1),   //Yew seed
                    new LootItem(5283, 1, 9),   //Apple tree seed
                    new LootItem(5284, 1, 8),   //Banana tree seed
                    new LootItem(5285, 1, 7),   //Orange tree seed
                    new LootItem(5286, 1, 6),   //Curry tree seed
                    new LootItem(5287, 1, 3),   //Pineapple seed
                    new LootItem(5288, 1, 2)    //Papaya tree seed
            )),
    HERO(80, 275.0, 386, 6, 4, "hero's", PlayerCounter.PICKPOCKETED_HERO,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 5000, 6000, 16),  //Coins
                    new LootItem(565, 1, 5),  //Blood rune
                    new LootItem(560, 2, 5),  //Death runes
                    new LootItem(1933, 1, 2), //Jug of wine
                    new LootItem(569, 1, 2),  //Fire orb
                    new LootItem(444, 1, 2),  //Gold ore
                    new LootItem(1617, 1, 1)  //Uncut diamond
            )),
    ELF(85, 353.0, 422, 6, 5, "elf's", PlayerCounter.PICKPOCKETED_ELF,
            new LootTable().addTable(1,
                    new LootItem(COINS_995, 5500, 6500, 16), //Coins
                    new LootItem(561, 3, 5),  //Nature runes
                    new LootItem(560, 2, 5),  //Death runes
                    new LootItem(1933, 1, 2), //Jug of wine
                    new LootItem(569, 1, 2),  //Fire orb
                    new LootItem(444, 1, 2),  //Gold ore
                    new LootItem(1617, 1, 1)  //Uncut diamond
            )),
    TZHAAR_HUR(90, 103.0, 2609, 6, 5, "tzhaar-hur's", PlayerCounter.PICKPOCKETED_TZHAAR_HUR,
            new LootTable().addTable(1,
                    new LootItem(1755, 1, 6),                    //Chisel
                    new LootItem(2347, 1, 5),                    //Hammer
                    new LootItem(1935, 1, 5),                    //Jug
                    new LootItem(946, 1, 2),                     //Knife
                    new LootItem(1931, 1, 2),                    //Pot
                    new LootItem(6529, 1, 16, 2),  //Tokkul
                    new LootItem(1623, 1, 1),                    //Uncut Sapphire
                    new LootItem(1619, 1, 1)                     //Uncut Ruby
            ));

    public final int levelReq, stunAnimation, stunSeconds, stunDamage;
    private final String name, identifier;
    public final double exp;
    public final LootTable lootTable;
    public final PlayerCounter counter;

    PickPocket(int levelReq, double exp, int stunAnimation, int stunSeconds, int stunDamage, String identifier, PlayerCounter counter, LootTable lootTable) {
        this.levelReq = levelReq;
        this.exp = exp;
        this.stunAnimation = stunAnimation;
        this.stunSeconds = stunSeconds;
        this.stunDamage = stunDamage;
        this.name = identifier.replace("'s", "");
        this.identifier = identifier;
        this.counter = counter;
        this.lootTable = lootTable;
    }

    private static void pickpocket(Player player, NPC npc, PickPocket pickpocket) {
        if (!player.getStats().check(StatType.Thieving, pickpocket.levelReq, "pickpocket the " + pickpocket.name + "."))
            return;
        if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more loot.");
            return;
        }

        if (player.getInventory().getAmount(22521) >= 28 || player.getInventory().getAmount(22522) >= 28 || player.getInventory().getAmount(22523) >= 28 || player.getInventory().getAmount(22524) >= 28) {
            player.sendMessage("You cannot hold more than 28 coin pouches at a time.");
            return;
        }

        if (player.isStunned()) {
            player.sendMessage("You're stunned!");
            return;
        }

        if (BotPrevention.isBlocked(player)) {
            player.sendMessage("You can't pickpocket an NPC while a guard is watching you.");
            return;
        }

        player.startEvent(event -> {
            player.lock(LockType.FULL_REGULAR_DAMAGE);
            player.sendFilteredMessage("You attempt to pick the " + pickpocket.identifier + " pocket.");
            if (successful(player, pickpocket)) {
                player.animate(881);
                player.privateSound(2581);
                player.sendFilteredMessage("You pick the " + pickpocket.identifier + " pocket.");
                player.getInventory().add(pickpocket.lootTable.rollItem());
                player.getStats().addXp(StatType.Thieving, pickpocket.exp, true);
                if (Random.rollDie(50, 1)) {
                    player.getInventory().addOrDrop(6828, 1);
                    player.sendMessage("You've discovered a Skilling box. It's been added to your inventory.");
                }
                if(player.currentTaskHard == DailyTask.PossibleTasksHard.MASTER_FARMER){
                    DailyTask.increaseHard(player, DailyTask.PossibleTasksHard.MASTER_FARMER);
                }
                if (player.getEquipment().contains(5553) && player.getEquipment().contains(5554) && player.getEquipment().contains(5555) &&
                        player.getEquipment().contains(5556) && player.getEquipment().contains(5557)) {
                    player.getInventory().add(pickpocket.lootTable.rollItem());
                }
                if (npc.getId() == 5730) {
                    player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.PICKPOCKET_ARD);
                }
                if (npc.getId() == 3295) {
                    player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.PICKPOCKET_HERO);
                }
                if (npc.getId() == 3550) {
                    player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.PICKPOCKET_THUG);
                }
                if (npc.getId() == 3269) {
                    player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.PICKPOCKET_GUARD);
                }
                if (pickpocket.identifier.equalsIgnoreCase("elf's")) {
                    player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.PICKPOCKET_ELF);
                }
                if (npc.getId() == 3271) {
                    player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.PICKPOCKET_GUARD);
                }
                if (npc.getId() == 5832) {
                    player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_MARTIN);
                }
                if (npc.getDef().name.equalsIgnoreCase("man") && player.getPosition().regionId() == 12850) {
                    player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_MAN_LUM);
                }
            } else {
                player.sendFilteredMessage("You fail to pick the " + pickpocket.identifier + " pocket.");
                npc.forceText("What do you think you're doing?");
                npc.faceTemp(player);
                npc.animate(pickpocket.stunAnimation);
                player.hit(new Hit().randDamage(pickpocket.stunDamage));
                player.stun(pickpocket.stunSeconds, true);
            }
            BotPrevention.attemptBlock(player);
            player.unlock();
        });
    }

    private static boolean successful(Player player, PickPocket pickpocket) {
        return Random.get(100) <= chance(player, pickpocket.levelReq);
    }

    private static final int GLOVES_OF_SILENCE = 10075;
    private static final int[] MAX_CAPES = {13329, 13331, 13333, 13335, 13337, 13342, 20760};

    private static int chance(Player player, int levelReq) {
        int slope = 2;
        int chance = 60; //Starts at a 60% chance
        int thievingLevel = player.getStats().get(StatType.Thieving).currentLevel;
        int requiredLevel = levelReq;

        if (player.getEquipment().hasId(GLOVES_OF_SILENCE))
            chance += 5;
        if (player.getEquipment().hasMultiple(MAX_CAPES) || ThievingSkillCape.wearsThievingCape(player))
            chance *= 1.1;
        if (thievingLevel > levelReq)
            chance += (thievingLevel - requiredLevel) * slope;
        return Math.min(chance, 95); //Caps at 95%
    }

    static {
        NPCDef.forEach(npcDef -> {
            for (PickPocket pickpocket : values()) {
                if (npcDef.name.equalsIgnoreCase(pickpocket.name().replace("_", " ")) ||
                        npcDef.name.toLowerCase().contains(pickpocket.name().toLowerCase())) {
                    int pickpocketOption = npcDef.getOption("pickpocket", "Pickpocket");
                    if (pickpocketOption == -1)
                        return;
                    NPCAction.register(npcDef.name, pickpocketOption, (player, npc) -> pickpocket(player, npc, pickpocket));
                    NPCAction.register("tzhaar-hur", pickpocketOption, (player, npc) -> pickpocket(player, npc, TZHAAR_HUR));
                    final int[] HAM_MEMBERS = {2540, 2541};
                    for (int hamMember : HAM_MEMBERS)
                        NPCAction.register(hamMember, pickpocketOption, (player, npc) -> pickpocket(player, npc, HAM));
                    final int[] PRIF_GUYS = {9189, 9119, 9058, 9056, 9192, 9053, 9139, 9054, 9055, 9087, 9125, 9183, 9152, 9085, 9153, 9130, 9129, 9475, 9084, 9188, 9184, 9155, 9169, 9057, 9109, 9183, 9117, 9113, 9184, 9475, 9151, 9137, 9063, 9121, 9165, 9164, 9145, 9064, 9170, 9060, 9065, 9147, 9240, 9088, 9099, 9062, 9091, 9092, 9100, 9143, 9093, 9098, 9144, 9094, 9059, 9126, 9097, 9096, 9095, 9061, 9157, 9066, 9156, 9186, 9242, 9475, 9067, 9106, 9186, 9114, 9107, 9187, 9475, 9068, 9188, 9185, 9086, 9069, 9070, 3422, 9122, 9076, 3422, 3421, 9071, 9159, 9072, 3421, 9239, 9131, 9127, 9131, 3422, 8733, 9146, 9089, 9201, 9158, 8733, 3422, 8733, 3422, 9120, 9166, 9074, 9073, 3420, 3421, 3421, 9075, 9184, 9475, 9185, 9115, 9110, 9188, 9111, 9185, 9080, 9081, 9475, 9154, 9184, 9148, 9161, 9163, 9188, 9185, 9124, 9185, 9162, 9160, 9083, 9168, 9082, 3421, 9138, 9241, 9167, 9077, 9090, 3421, 9078, 1174, 1173, 1417, 3421, 3421, 9123, 1174, 3421, 3421, 1173, 3421, 1417, 3421, 9079, 9183, 9242, 2093, 9475, 9189, 9108, 9116, 9182, 9112};
                    for (int prif : PRIF_GUYS)
                        NPCAction.register(prif, pickpocketOption, (player, npc) -> pickpocket(player, npc, ELF));
                    NPCAction.register(5832, pickpocketOption, (player, npc) -> pickpocket(player, npc, MASTER_GARDENER));
                }
            }
        });
    }
}
