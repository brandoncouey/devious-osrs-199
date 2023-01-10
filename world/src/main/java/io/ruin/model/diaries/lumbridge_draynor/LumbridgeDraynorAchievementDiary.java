package io.ruin.model.diaries.lumbridge_draynor;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorDiaryEntry.*;


public final class LumbridgeDraynorAchievementDiary extends StatefulAchievementDiary<LumbridgeDraynorDiaryEntry> {

    public static final Set<LumbridgeDraynorDiaryEntry> EASY_TASKS = EnumSet.of(TELEPORT_ESSENCE_LUM, CRAFT_WATER, HANS, PICKPOCKET_MAN_LUM, DRAYNOR_ROOFTOP, SLAY_BUG, MINE_IRON_LUM);

    public static final Set<LumbridgeDraynorDiaryEntry> MEDIUM_TASKS = EnumSet.of(LUMBRIDGE_TELEPORT, AL_KHARID_ROOFTOP, FAIRY_RING, CHOP_WILLOW_DRAY, CRAFT_LAVAS);

    public static final Set<LumbridgeDraynorDiaryEntry> HARD_TASKS = EnumSet.of(EDGEVILLE_TELE, CRAFT_COSMIC, PICKPOCKET_MARTIN);

    public static final Set<LumbridgeDraynorDiaryEntry> ELITE_TASKS = EnumSet.of(CHOP_MAGIC_AL, CRAFT_WATERS, BURN_WILLOW_LOGS);

    public static final String NAME = "Lumbridge & Draynor area";

    public LumbridgeDraynorAchievementDiary(Player player) {
        super(NAME, player);
    }

    int REWARD = 13125;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("LUMBY_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("LUMBY_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("LUMBY_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("LUMBY_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("LUMBY_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("LUMBY_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("LUMBY_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("LUMBY_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("LUMBY_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("LUMBY_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("LUMBY_ELITE", player);
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

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.LUMBRIDGE_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.LUMBRIDGE_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.LUMBRIDGE_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.LUMBRIDGE_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return false;
    }

    @Override
    public Set<LumbridgeDraynorDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<LumbridgeDraynorDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<LumbridgeDraynorDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<LumbridgeDraynorDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (LumbridgeDraynorDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(LumbridgeDraynorDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "TELEPORT_ESSENCE_LUM":
                return achievements.contains(TELEPORT_ESSENCE_LUM);
            case "CRAFT_WATER":
                return achievements.contains(CRAFT_WATER);
            case "HANS":
                return achievements.contains(HANS);
            case "PICKPOCKET_MAN_LUM":
                return achievements.contains(PICKPOCKET_MAN_LUM);
            case "DRAYNOR_ROOFTOP":
                return achievements.contains(DRAYNOR_ROOFTOP);
            case "SLAY_BUG":
                return achievements.contains(SLAY_BUG);
            case "MINE_IRON_LUM":
                return achievements.contains(MINE_IRON_LUM);
            //MEDIUM
            case "LUMBRIDGE_TELEPORT":
                return achievements.contains(LUMBRIDGE_TELEPORT);
            case "AL_KHARID_ROOFTOP":
                return achievements.contains(AL_KHARID_ROOFTOP);
            case "FAIRY_RING":
                return achievements.contains(FAIRY_RING);
            case "CHOP_WILLOW_DRAY":
                return achievements.contains(CHOP_WILLOW_DRAY);
            case "CRAFT_LAVAS":
                return achievements.contains(CRAFT_LAVAS);
            //HARD
            case "EDGEVILLE_TELE":
                return achievements.contains(EDGEVILLE_TELE);
            case "CRAFT_COSMIC":
                return achievements.contains(CRAFT_COSMIC);
            case "PICKPOCKET_MARTIN":
                return achievements.contains(PICKPOCKET_MARTIN);
            //ELITE
            case "CHOP_MAGIC_AL":
                return achievements.contains(CHOP_MAGIC_AL);
            case "CRAFT_WATERS":
                return achievements.contains(CRAFT_WATERS);
            case "BURN_WILLOW_LOGS":
                return achievements.contains(BURN_WILLOW_LOGS);
        }
        return false;
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Lumbridge & Draynor Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("TELEPORT_ESSENCE_LUM") ? "<col=24d124>Have Sedridor teleport you to the Essence Mine.</col>" : "<col=911c25>Have Sedridor teleport you to the Essence Mine.",
                hasCompletedAchieve("CRAFT_WATER") ? "<col=24d124>Craft a Water Rune.</col>" : "<col=911c25>Craft a Water Rune.",
                hasCompletedAchieve("HANS") ? "<col=24d124>Check your age at Hans.</col>" : "<col=911c25>Check your age at Hans.",
                hasCompletedAchieve("PICKPOCKET_MAN_LUM") ? "<col=24d124>Pickpocket a Man or Woman.</col>" : "<col=911c25>Pickpocket a Man or Woman.",
                hasCompletedAchieve("DRAYNOR_ROOFTOP") ? "<col=24d124>Complete the Draynor Village Rooftop Agility Course.</col>" : "<col=911c25>Complete the Draynor Village Rooftop Agility Course.",
                hasCompletedAchieve("SLAY_BUG") ? "<col=24d124>Kill a Cave Bug in the Lumbridge Swamp Caves.</col>" : "<col=911c25>Kill a Cave Bug in the Lumbridge Swamp Caves.",
                hasCompletedAchieve("MINE_IRON_LUM") ? "<col=24d124>Mine Iron Ore at the Al-Kharid Mine. (" + (getAbsoluteAchievementStage(MINE_IRON_LUM)) + "/" + getMaximum(MINE_IRON_LUM) + ")</col>" : "<col=911c25>Mine Iron Ore at the Al-Kharid Mine. (" + (getAbsoluteAchievementStage(MINE_IRON_LUM)) + "/" + getMaximum(MINE_IRON_LUM) + ")",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("LUMBRIDGE_TELEPORT") ? "<col=24d124>Teleport to Lumbridge.</col>" : "<col=911c25>Teleport to Lumbridge.",
                hasCompletedAchieve("AL_KHARID_ROOFTOP") ? "<col=24d124>Complete the Al-Kharid Rooftop Agility Course.</col>" : "<col=911c25>Complete the Al-Kharid Rooftop Agility Course.",
                hasCompletedAchieve("FAIRY_RING") ? "<col=24d124>Travel by Fairy Ring to the Wizards' Tower.</col>" : "<col=911c25>Travel by Fairy Ring to the Wizards' Tower.",
                hasCompletedAchieve("CHOP_WILLOW_DRAY") ? "<col=24d124>Chop Willow Logs in Draynor Village. (" + (getAbsoluteAchievementStage(CHOP_WILLOW_DRAY)) + "/" + getMaximum(CHOP_WILLOW_DRAY) + ")</col>" : "<col=911c25>Chop Willow Logs in Draynor Village. (" + (getAbsoluteAchievementStage(CHOP_WILLOW_DRAY)) + "/" + getMaximum(CHOP_WILLOW_DRAY) + ")",
                hasCompletedAchieve("CRAFT_LAVAS") ? "<col=24d124>Craft a Fire Rune.</col>" : "<col=911c25>Craft a Fire Rune.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("EDGEVILLE_TELE") ? "<col=24d124>Teleport to Edgeville.</col>" : "<col=911c25>Teleport to Edgeville.",
                hasCompletedAchieve("CRAFT_COSMIC") ? "<col=24d124>Craft 56 Cosmic Runes at once.</col>" : "<col=911c25>Craft 56 Cosmic Runes at once.",
                hasCompletedAchieve("PICKPOCKET_MARTIN") ? "<col=24d124>Pickpocket Martin the Master Gardener.</col>" : "<col=911c25>Pickpocket Martin the Master Gardener.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("CHOP_MAGIC_AL") ? "<col=24d124>Chop Magic Logs at the Mage Training Arena. (" + (getAbsoluteAchievementStage(CHOP_MAGIC_AL)) + "/" + getMaximum(CHOP_MAGIC_AL) + ")</col>" : "<col=911c25>Chop Magic Logs at the Mage Training Arena. (" + (getAbsoluteAchievementStage(CHOP_MAGIC_AL)) + "/" + getMaximum(CHOP_MAGIC_AL) + ")",
                hasCompletedAchieve("CRAFT_WATERS") ? "<col=24d124>Craft 140 Water Runes at once.</col>" : "<col=911c25>Craft 140 Water Runes at once.",
                hasCompletedAchieve("BURN_WILLOW_LOGS") ? "<col=24d124>Burn Willow Logs. (" + (getAbsoluteAchievementStage(BURN_WILLOW_LOGS)) + "/" + getMaximum(BURN_WILLOW_LOGS) + ")</col>" : "<col=911c25>Burn Willow Logs. (" + (getAbsoluteAchievementStage(BURN_WILLOW_LOGS)) + "/" + getMaximum(BURN_WILLOW_LOGS) + ")");
    }

    @Override
    public int getMaximum(LumbridgeDraynorDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}