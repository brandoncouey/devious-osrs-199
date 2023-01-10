package io.ruin.model.diaries.morytania;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.morytania.MorytaniaDiaryEntry.*;

public final class MorytaniaAchievementDiary extends StatefulAchievementDiary<MorytaniaDiaryEntry> {

    public static final Set<MorytaniaDiaryEntry> EASY_TASKS = EnumSet.of(KILL_BANSHEE, KILL_GHOUL);

    public static final Set<MorytaniaDiaryEntry> MEDIUM_TASKS = EnumSet.of(CATCH_LIZARD, CANAFIS_ROOFTOP, TRAVEL_DRAGONTOOTH, ECTOPHIAL);

    public static final Set<MorytaniaDiaryEntry> HARD_TASKS = EnumSet.of(CLIMB_CHAIN, KILL_CAVE_HORROR, BURN_MAHOGANY, LOOT_CHEST);

    public static final Set<MorytaniaDiaryEntry> ELITE_TASKS = EnumSet.of(CRAFT_DHIDE, KILL_ABYSSAL_DEMON);

    public static final String NAME = "Morytania area";

    public MorytaniaAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.MORYTANIA_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.MORYTANIA_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.MORYTANIA_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.MORYTANIA_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13112;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("MORYTANIA_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("MORYTANIA_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("MORYTANIA_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("MORYTANIA_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("MORYTANIA_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("MORYTANIA_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("MORYTANIA_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("MORYTANIA_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("MORYTANIA_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("MORYTANIA_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("MORYTANIA_ELITE", player);
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

    @Override
    public Set<MorytaniaDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<MorytaniaDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<MorytaniaDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<MorytaniaDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (MorytaniaDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(MorytaniaDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            // case "SBOTT":
            //   return achievements.contains(SBOTT);
            case "KILL_BANSHEE":
                return achievements.contains(KILL_BANSHEE);
            case "KILL_GHOUL":
                return achievements.contains(KILL_GHOUL);
            //MEDIUM
            case "CATCH_LIZARD":
                return achievements.contains(CATCH_LIZARD);
            case "CANAFIS_ROOFTOP":
                return achievements.contains(CANAFIS_ROOFTOP);
            case "TRAVEL_DRAGONTOOTH":
                return achievements.contains(TRAVEL_DRAGONTOOTH);
            case "ECTOPHIAL":
                return achievements.contains(ECTOPHIAL);
            //HARD
            case "CLIMB_CHAIN":
                return achievements.contains(CLIMB_CHAIN);
            case "KILL_CAVE_HORROR":
                return achievements.contains(KILL_CAVE_HORROR);
            case "BURN_MAHOGANY":
                return achievements.contains(BURN_MAHOGANY);
            case "LOOT_CHEST":
                return achievements.contains(LOOT_CHEST);
            //ELITE
          /*  case "BARROWS_CHEST":
                return achievements.contains(BARROWS_CHEST);*/
            case "CRAFT_DHIDE":
                return achievements.contains(CRAFT_DHIDE);
            case "KILL_ABYSSAL_DEMON":
                return achievements.contains(KILL_ABYSSAL_DEMON);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Morytania Diary",
                "<col=501061><strong><u>Easy",
                //hasCompletedAchieve("SBOTT") ? "<col=24d124>Tan a Hide at Sbott.</col>" : "<col=911c25>Tan a Hide at Sbott.",
                hasCompletedAchieve("KILL_BANSHEE") ? "<col=24d124>Kill a Banshee in the Slayer Tower.</col>" : "<col=911c25>Kill a Banshee in the Slayer Tower.",
                hasCompletedAchieve("KILL_GHOUL") ? "<col=24d124>Kill a Ghoul.</col>" : "<col=911c25>Kill a Ghoul.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("CATCH_LIZARD") ? "<col=24d124>Catch a Swamp Lizard.</col>" : "<col=911c25>Catch a Swamp Lizard.",
                hasCompletedAchieve("CANAFIS_ROOFTOP") ? "<col=24d124>Complete the Canafis Rooftop Agility Course.</col>" : "<col=911c25>Complete the Canafis Rooftop Agility Course.",
                hasCompletedAchieve("TRAVEL_DRAGONTOOTH") ? "<col=24d124>Travel by boat to Dragontooth Island.</col>" : "<col=911c25>Travel by boat to Dragontooth Island.",
                hasCompletedAchieve("ECTOPHIAL") ? "<col=24d124>Teleport to Port Phasmatys using an Ectophial.</col>" : "<col=911c25>Teleport to Port Phasmatys using an Ectophial.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("CLIMB_CHAIN") ? "<col=24d124>Climb the Advanced Spike Chain in the Slayer Tower.</col>" : "<col=911c25>Climb the Advanced Spike Chain in the Slayer Tower.",
                hasCompletedAchieve("KILL_CAVE_HORROR") ? "<col=24d124>Kill a Cave Horror.</col>" : "<col=911c25>Kill a Cave Horror.",
                hasCompletedAchieve("BURN_MAHOGANY") ? "<col=24d124>Burn Mahogany Logs on Mos Le'Harmless. (" + (getAbsoluteAchievementStage(BURN_MAHOGANY)) + "/" + getMaximum(BURN_MAHOGANY) + ")</col>" : "<col=911c25>Burn Mahogany Logs on Mos Le'Harmless. (" + (getAbsoluteAchievementStage(BURN_MAHOGANY)) + "/" + getMaximum(BURN_MAHOGANY) + ")",
                hasCompletedAchieve("LOOT_CHEST") ? "<col=24d124>Loot the Barrows Chest.</col>" : "<col=911c25>Loot the Barrows Chest.",
                "",
                "<col=501061><strong><u>Elite",
                //    hasCompletedAchieve("BARROWS_CHEST") ? "<col=24d124>Loot the Barrows Chest while wearing a full set of barrows.</col>" : "<col=911c25>Loot the Barrows Chest while wearing a full set of barrows.",
                hasCompletedAchieve("CRAFT_DHIDE") ? "<col=24d124>Craft a Black D'hide Body in the Canafis bank.</col>" : "<col=911c25>Craft a Black D'hide Body in the Canafis bank.",
                hasCompletedAchieve("KILL_ABYSSAL_DEMON") ? "<col=24d124>Kill Abyssal Demons in the Slayer Tower. (" + (getAbsoluteAchievementStage(KILL_ABYSSAL_DEMON)) + "/" + getMaximum(KILL_ABYSSAL_DEMON) + ")</col>" : "<col=911c25>Kill Abyssal Demons in the Slayer Tower. (" + (getAbsoluteAchievementStage(KILL_ABYSSAL_DEMON)) + "/" + getMaximum(KILL_ABYSSAL_DEMON) + ")"
        );
    }

    @Override
    public int getMaximum(MorytaniaDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}