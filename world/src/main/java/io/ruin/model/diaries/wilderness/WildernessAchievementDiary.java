package io.ruin.model.diaries.wilderness;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.wilderness.WildernessDiaryEntry.*;


public final class WildernessAchievementDiary extends StatefulAchievementDiary<WildernessDiaryEntry> {

    public static final Set<WildernessDiaryEntry> EASY_TASKS = EnumSet.of(KILL_MAMMOTH, WILDERNESS_LEVER, WILDERNESS_ALTAR, KILL_EARTH_WARRIOR, KBD_LAIR, MINE_IRON_WILD, ABYSS_TELEPORT);

    public static final Set<WildernessDiaryEntry> MEDIUM_TASKS = EnumSet.of(MINE_MITHRIL_WILD, WILDERNESS_GODWARS, WILDERNESS_AGILITY, KILL_GREEN_DRAGON, KILL_ANKOU, KILL_BLOODVELD);

    public static final Set<WildernessDiaryEntry> HARD_TASKS = EnumSet.of(BLACK_SALAMANDER, KILL_LAVA_DRAGON, CHAOS_ELEMENTAL, CRAZY_ARCHAEOLOGIST, CHAOS_FANATIC, SCORPIA, SPIRITUAL_WARRIOR);

    public static final Set<WildernessDiaryEntry> ELITE_TASKS = EnumSet.of(CALLISTO, VENENATIS, VETION, ROGUES_CHEST, SPIRITUAL_MAGE, MAGIC_LOG_WILD);

    public static final String NAME = "Wilderness area";

    public WildernessAchievementDiary(Player player) {
        super(NAME, player);
    }

    int REWARD = 13108;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("WILDY_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("WILDY_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("WILDY_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("WILDY_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("WILDY_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("WILDY_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("WILDY_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("WILDY_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("WILDY_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("WILDY_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("WILDY_ELITE", player);
        } else {
            player.dialogue(new NPCDialogue(npc, "Looks like you've done everything now for all my rewards!"));
        }

    }

    public void npcDialogue(String dialogue) {
        //player.getDH().sendNpcChat1(dialogue, player.npcType, "Diary Manager");
        // player.nextChat = -1;
    }

    public void addReward(int reward, NPC npc) {
        player.getInventory().add(reward, 1);
        player.dialogue(new NPCDialogue(npc, "Here you go, upgraded and ready to be used!"));
        //player.getDH().sendNpcChat1("Here you go, upgraded and ready to be used.", player.npcType, "Diary Manager");
    }

    public void upgradeReward(int reward, int upgrade, NPC npc) {
        player.getInventory().remove(reward, 1);
        player.getInventory().add(upgrade, 1);
        player.dialogue(new NPCDialogue(npc, "Here you go, upgraded and ready!"));
        //player.getDH().sendNpcChat1("Here you go, upgraded and ready.", player.npcType, "Diary Manager");
    }

    public int getCount(int id) {
        return player.getInventory().count(id);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.WILDERNESS_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.WILDERNESS_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.WILDERNESS_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.WILDERNESS_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    @Override
    public Set<WildernessDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<WildernessDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<WildernessDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<WildernessDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (WildernessDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(WildernessDiaryEntry.valueOf(s));
                }
            });
        }

        switch (difficulty) {
            //EASY
            case "KILL_MAMMOTH":
                return achievements.contains(KILL_MAMMOTH);
            case "WILDERNESS_LEVER":
                return achievements.contains(WILDERNESS_LEVER);
            case "WILDERNESS_ALTAR":
                return achievements.contains(WILDERNESS_ALTAR);
            case "KILL_EARTH_WARRIOR":
                return achievements.contains(KILL_EARTH_WARRIOR);
            case "KBD_LAIR":
                return achievements.contains(KBD_LAIR);
            case "MINE_IRON_WILD":
                return achievements.contains(MINE_IRON_WILD);
            case "ABYSS_TELEPORT":
                return achievements.contains(ABYSS_TELEPORT);
            //MEDIUM
            case "MINE_MITHRIL_WILD":
                return achievements.contains(MINE_MITHRIL_WILD);
            case "WILDERNESS_GODWARS":
                return achievements.contains(WILDERNESS_GODWARS);
            case "WILDERNESS_AGILITY":
                return achievements.contains(WILDERNESS_AGILITY);
            case "KILL_GREEN_DRAGON":
                return achievements.contains(KILL_GREEN_DRAGON);
            case "KILL_ANKOU":
                return achievements.contains(KILL_ANKOU);
            case "KILL_BLOODVELD":
                return achievements.contains(KILL_BLOODVELD);
            //HARD
            case "BLACK_SALAMANDER":
                return achievements.contains(BLACK_SALAMANDER);
            case "KILL_LAVA_DRAGON":
                return achievements.contains(KILL_LAVA_DRAGON);
            case "CHAOS_ELEMENTAL":
                return achievements.contains(CHAOS_ELEMENTAL);
            case "CRAZY_ARCHAEOLOGIST":
                return achievements.contains(CRAZY_ARCHAEOLOGIST);
            case "CHAOS_FANATIC":
                return achievements.contains(CHAOS_FANATIC);
            case "SCORPIA":
                return achievements.contains(SCORPIA);
            case "SPIRITUAL_WARRIOR":
                return achievements.contains(SPIRITUAL_WARRIOR);
            //ELITE
            case "CALLISTO":
                return achievements.contains(CALLISTO);
            case "VENENATIS":
                return achievements.contains(VENENATIS);
            case "VETION":
                return achievements.contains(VETION);
            case "ROGUES_CHEST":
                return achievements.contains(ROGUES_CHEST);
            case "SPIRITUAL_MAGE":
                return achievements.contains(SPIRITUAL_MAGE);
            case "MAGIC_LOG_WILD":
                return achievements.contains(MAGIC_LOG_WILD);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Wilderness Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("KILL_MAMMOTH") ? "<col=24d124>Kill a Mammoth.</col>" : "<col=911c25>Kill a Mammoth.",
                hasCompletedAchieve("WILDERNESS_LEVER") ? "<col=24d124>Enter the Wilderness using an lever.</col>" : "<col=911c25>Enter the Wilderness using an lever.",
                hasCompletedAchieve("WILDERNESS_ALTAR") ? "<col=24d124>Pray at the Deep Wilderness Chaos Altar.</col>" : "<col=911c25>Pray at the Deep Wilderness Chaos Altar.",
                hasCompletedAchieve("KILL_EARTH_WARRIOR") ? "<col=24d124>Kill an Earth Warrior.</col>" : "<col=911c25>Kill an Earth Warrior.",
                hasCompletedAchieve("KBD_LAIR") ? "<col=24d124>Enter the King Black Dragon Lair.</col>" : "<col=911c25>Enter the King Black Dragon Lair.",
                hasCompletedAchieve("MINE_IRON_WILD") ? "<col=24d124>Mine an Iron Ore near the Mage of Zamorak.</col>" : "<col=911c25>Mine an Iron Ore near the Mage of Zamorak.",
                hasCompletedAchieve("ABYSS_TELEPORT") ? "<col=24d124>Have the Mage of Zamorak teleport you to the Abyss.</col>" : "<col=911c25>Have the Mage of Zamorak teleport you to the Abyss.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("MINE_MITHRIL_WILD") ? "<col=24d124>Mine a Mithril Ore in the Hobgoblin Pit.</col>" : "<col=911c25>Mine a Mithril Ore in the Hobgoblin Pit.",
                hasCompletedAchieve("WILDERNESS_GODWARS") ? "<col=24d124>Enter the Wilderness God Wars Dungeon.</col>" : "<col=911c25>Enter the Wilderness God Wars Dungeon.",
                hasCompletedAchieve("WILDERNESS_AGILITY") ? "<col=24d124>Complete the Wilderness Agility Course.</col>" : "<col=911c25>Complete the Wilderness Agility Course.",
                hasCompletedAchieve("KILL_GREEN_DRAGON") ? "<col=24d124>Kill a Green Dragon.</col>" : "<col=911c25>Kill a Green Dragon.",
                hasCompletedAchieve("KILL_ANKOU") ? "<col=24d124>Kill an Ankou.</col>" : "<col=911c25>Kill an Ankou.",
                hasCompletedAchieve("KILL_BLOODVELD") ? "<col=24d124>Kill a Bloodveld in the Wilderness God Wars Dungeon.</col>" : "<col=911c25>Kill a Bloodveld in the Wilderness God Wars Dungeon.",
                // hasCompletedAchieve("EMBLEM_TRADER") ? "<col=24d124>Talk to the Emblem Trader in Edgeville.</col>" : "<col=911c25>Talk to the Emblem Trader in Edgeville.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("BLACK_SALAMANDER") ? "<col=24d124>Catch a Black Salamander.</col>" : "<col=911c25>Catch a Black Salamander.",
                hasCompletedAchieve("KILL_LAVA_DRAGON") ? "<col=24d124>Kill a Lava Dragon.</col>" : "<col=911c25>Kill a Lava Dragon.",
                hasCompletedAchieve("CHAOS_ELEMENTAL") ? "<col=24d124>Kill the Chaos Elemental.</col>" : "<col=911c25>Kill the Chaos Elemental.",
                hasCompletedAchieve("CRAZY_ARCHAEOLOGIST") ? "<col=24d124>Kill the Crazy Archaeologist.</col>" : "<col=911c25>Kill the Crazy Archaeologist.",
                hasCompletedAchieve("CHAOS_FANATIC") ? "<col=24d124>Kill the Chaos Fanatic.</col>" : "<col=911c25>Kill the Chaos Fanatic.",
                hasCompletedAchieve("SCORPIA") ? "<col=24d124>Kill Scorpia.</col>" : "<col=911c25>Kill Scorpia.",
                hasCompletedAchieve("SPIRITUAL_WARRIOR") ? "<col=24d124>Kill a Spirtual Warrior in the Wilderness God Wars Dungeon.</col>" : "<col=911c25>Kill a Spirtual Warrior in the Wilderness God Wars Dungeon.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("CALLISTO") ? "<col=24d124>Kill Callisto.</col>" : "<col=911c25>Kill Callisto.",
                hasCompletedAchieve("VENENATIS") ? "<col=24d124>Kill Veneatis.</col>" : "<col=911c25>Kill Veneatis.",
                hasCompletedAchieve("VETION") ? "<col=24d124>Kill Vet'ion.</col>" : "<col=911c25>Kill Vet'ion.",
                hasCompletedAchieve("ROGUES_CHEST") ? "<col=24d124>Steal from the Chest in Rogue's Castle.</col>" : "<col=911c25>Steal from the Chest in Rogue's Castle.",
                hasCompletedAchieve("SPIRITUAL_MAGE") ? "<col=24d124>Kill a Spiritual Mage in the Wilderness God Wars Dungeon.</col>" : "<col=911c25>Kill a Spiritual Mage in the Wilderness God Wars Dungeon.",
                hasCompletedAchieve("MAGIC_LOG_WILD") ? "<col=24d124>Cut Magic Logs in the Resource Area. (" + (getAbsoluteAchievementStage(MAGIC_LOG_WILD)) + "/" + getMaximum(MAGIC_LOG_WILD) + ")</col>" : "<col=911c25>Cut Magic Logs in the Resource Area. (" + (getAbsoluteAchievementStage(MAGIC_LOG_WILD)) + "/" + getMaximum(MAGIC_LOG_WILD) + ")"
        );
    }

    @Override
    public int getMaximum(WildernessDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}