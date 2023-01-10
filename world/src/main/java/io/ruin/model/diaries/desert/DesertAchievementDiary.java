package io.ruin.model.diaries.desert;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.desert.DesertDiaryEntry.*;


public final class DesertAchievementDiary extends StatefulAchievementDiary<DesertDiaryEntry> {

    public static final Set<DesertDiaryEntry> EASY_TASKS = EnumSet.of(PASS_GATE, CUT_CACTUS, KILL_SNAKES_DESERT, KILL_LIZARDS_DESERT, MINE_CLAY);

    public static final Set<DesertDiaryEntry> MEDIUM_TASKS = EnumSet.of(KILL_BANDIT, PASS_GATE_ROBES, COMBAT_POTION, CHOP_TEAK, PICKPOCKET_THUG, ACTIVATE_ANCIENT, CAST_BARRAGE, KILL_VULTURE);

    public static final Set<DesertDiaryEntry> HARD_TASKS = EnumSet.of(TRAVEL_POLLNIVNEACH, KILL_DUST_DEVIL);

    public static final Set<DesertDiaryEntry> ELITE_TASKS = EnumSet.of(PRAY_SOPHANEM);

    public static final String NAME = "Desert area";

    public DesertAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.DESERT_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.DESERT_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.DESERT_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.DESERT_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (DesertDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(DesertDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "PASS_GATE":
                return achievements.contains(PASS_GATE);
            case "CUT_CACTUS":
                return achievements.contains(CUT_CACTUS);
            case "KILL_SNAKES_DESERT":
                return achievements.contains(KILL_SNAKES_DESERT);
            case "KILL_LIZARDS_DESERT":
                return achievements.contains(KILL_LIZARDS_DESERT);
            case "MINE_CLAY":
                return achievements.contains(MINE_CLAY);
            //MEDIUM
            case "KILL_BANDIT":
                return achievements.contains(KILL_BANDIT);
            case "PASS_GATE_ROBES":
                return achievements.contains(PASS_GATE_ROBES);
            case "COMBAT_POTION":
                return achievements.contains(COMBAT_POTION);
            case "CHOP_TEAK":
                return achievements.contains(CHOP_TEAK);
            case "PICKPOCKET_THUG":
                return achievements.contains(PICKPOCKET_THUG);
            case "ACTIVATE_ANCIENT":
                return achievements.contains(ACTIVATE_ANCIENT);
            case "CAST_BARRAGE":
                return achievements.contains(CAST_BARRAGE);
            case "KILL_VULTURE":
                return achievements.contains(KILL_VULTURE);
            //HARD
            case "TRAVEL_POLLNIVNEACH":
                return achievements.contains(TRAVEL_POLLNIVNEACH);
            case "KILL_DUST_DEVIL":
                return achievements.contains(KILL_DUST_DEVIL);
            //ELITE
            case "PRAY_SOPHANEM":
                return achievements.contains(PRAY_SOPHANEM);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public boolean hasCompletedSome(String difficulty) {
        switch (difficulty) {
            case "EASY":
                return achievements.containsAll(EASY_TASKS) && !achievements.containsAll(MEDIUM_TASKS)
                        && !achievements.containsAll(HARD_TASKS) && !achievements.containsAll(ELITE_TASKS);
            case "MEDIUM":
                return achievements.containsAll(EASY_TASKS) && achievements.containsAll(MEDIUM_TASKS)
                        && !achievements.containsAll(HARD_TASKS) && !achievements.containsAll(ELITE_TASKS);
            case "HARD":
                return achievements.containsAll(EASY_TASKS) && achievements.containsAll(MEDIUM_TASKS)
                        && achievements.containsAll(HARD_TASKS) && !achievements.containsAll(ELITE_TASKS);
            case "ELITE":
                return achievements.containsAll(EASY_TASKS) && achievements.containsAll(MEDIUM_TASKS)
                        && achievements.containsAll(HARD_TASKS) && achievements.containsAll(ELITE_TASKS);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13133;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("DESERT_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            addReward(REWARD + 1, npc);
            claim("DESERT_MEDIUM", player);
            claim("DESERT_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("DESERT_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("DESERT_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("DESERT_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("DESERT_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("DESERT_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("DESERT_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("DESERT_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("DESERT_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("DESERT_ELITE", player);
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
        return player.getInventory().getAmount(id);
    }

    @Override
    public Set<DesertDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<DesertDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<DesertDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<DesertDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Desert Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("PASS_GATE") ? "<col=24d124>Pass through Shantay Pass.</col>" : "<col=911c25>Pass through Shantay Pass.",
                hasCompletedAchieve("CUT_CACTUS") ? "<col=24d124>Fill a Waterskin by cutting a cactus.</col>" : "<col=911c25>Fill a Waterskin by cutting a cactus.",
                hasCompletedAchieve("KILL_SNAKES_DESERT") ? "<col=24d124>Kill Snakes. (" + (getAbsoluteAchievementStage(KILL_SNAKES_DESERT)) + "/" + getMaximum(KILL_SNAKES_DESERT) + ")</col>" : "<col=911c25>Kill Snakes. (" + (getAbsoluteAchievementStage(KILL_SNAKES_DESERT)) + "/" + getMaximum(KILL_SNAKES_DESERT) + ")",
                hasCompletedAchieve("KILL_LIZARDS_DESERT") ? "<col=24d124>Kill Lizards. (" + (getAbsoluteAchievementStage(KILL_LIZARDS_DESERT)) + "/" + getMaximum(KILL_LIZARDS_DESERT) + ")</col>" : "<col=911c25>Kill Lizards. (" + (getAbsoluteAchievementStage(KILL_LIZARDS_DESERT)) + "/" + getMaximum(KILL_LIZARDS_DESERT) + ")",
                hasCompletedAchieve("MINE_CLAY") ? "<col=24d124>Mine a piece of Clay in the Northeast part of the desert.</col>" : "<col=911c25>Mine a piece of Clay in the Northeast part of the desert.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("KILL_BANDIT") ? "<col=24d124>Kill Bandits. (" + (getAbsoluteAchievementStage(KILL_BANDIT)) + "/" + getMaximum(KILL_BANDIT) + ")</col>" : "<col=911c25>Kill Bandits. (" + (getAbsoluteAchievementStage(KILL_BANDIT)) + "/" + getMaximum(KILL_BANDIT) + ")",
                hasCompletedAchieve("PASS_GATE_ROBES") ? "<col=24d124>Enter the Pyramid Plunder minigame.</col>" : "<col=911c25>Enter the Pyramid Plunder minigame.",
                hasCompletedAchieve("COMBAT_POTION") ? "<col=24d124>Create a Combat Potion in the desert.</col>" : "<col=911c25>Create a Combat Potion in the desert.",
                hasCompletedAchieve("CHOP_TEAK") ? "<col=24d124>Chop Teak Logs near Uzer. (" + (getAbsoluteAchievementStage(CHOP_TEAK)) + "/" + getMaximum(CHOP_TEAK) + ")</col>" : "<col=911c25>Chop Teak Logs near Uzer. (" + (getAbsoluteAchievementStage(CHOP_TEAK)) + "/" + getMaximum(CHOP_TEAK) + ")",
                hasCompletedAchieve("PICKPOCKET_THUG") ? "<col=24d124>Pickpocket Menaphite Thugs. (" + (getAbsoluteAchievementStage(PICKPOCKET_THUG)) + "/" + getMaximum(PICKPOCKET_THUG) + ")</col>" : "<col=911c25>Pickpocket Menaphite Thugs. (" + (getAbsoluteAchievementStage(PICKPOCKET_THUG)) + "/" + getMaximum(PICKPOCKET_THUG) + ")",
                hasCompletedAchieve("CAST_BARRAGE") ? "<col=24d124>Travel on the Magic Carpet to Nardah.</col>" : "<col=911c25>Travel on the Magic Carpet to Nardah.",
                hasCompletedAchieve("KILL_VULTURE") ? "<col=24d124>Kill Vultures. (" + (getAbsoluteAchievementStage(KILL_VULTURE)) + "/" + getMaximum(KILL_VULTURE) + ")</col>" : "<col=911c25>Kill Vultures. (" + (getAbsoluteAchievementStage(KILL_VULTURE)) + "/" + getMaximum(KILL_VULTURE) + ")",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("TRAVEL_POLLNIVNEACH") ? "<col=24d124>Travel on the Magic Carpet to Pollnivneach.</col>" : "<col=911c25>Travel on the Magic Carpet to Pollnivneach.",
                hasCompletedAchieve("KILL_DUST_DEVIL") ? "<col=24d124>Kill Dust Devils. (" + (getAbsoluteAchievementStage(KILL_DUST_DEVIL)) + "/" + getMaximum(KILL_DUST_DEVIL) + ")</col>" : "<col=911c25>Kill Dust Devils. (" + (getAbsoluteAchievementStage(KILL_DUST_DEVIL)) + "/" + getMaximum(KILL_DUST_DEVIL) + ")",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("PRAY_SOPHANEM") ? "<col=24d124>Restore 85 Prayer Points at the Sophanem Great Temple.</col>" : "<col=911c25>Restore 85 Prayer Points at the Sophanem Great Temple.");
    }

    @Override
    public int getMaximum(DesertDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}