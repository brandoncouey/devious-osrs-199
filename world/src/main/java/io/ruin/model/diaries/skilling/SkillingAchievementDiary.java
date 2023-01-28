package io.ruin.model.diaries.skilling;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.skilling.SkillingDiaryEntry.*;


public final class SkillingAchievementDiary extends StatefulAchievementDiary<SkillingDiaryEntry> {

    public static final Set<SkillingDiaryEntry> EASY_TASKS = EnumSet.of(KILL_DUCK, WESTERN_WALL, FILL_BUCKET, REPAIR_STRUT, TRAVEL_ENTRANA, MIND_TIARA, FARMING_SHOP);

    public static final Set<SkillingDiaryEntry> MEDIUM_TASKS = EnumSet.of(CRYSTAL_CHEST, PICKPOCKET_GUARD, GOLD_ORE, NARROW_CREVICE, ALTAR_OF_GUTHIX, TELEPORT_FALADOR);

    public static final Set<SkillingDiaryEntry> HARD_TASKS = EnumSet.of(KILL_WYVERN, FALADOR_ROOFTOP, KILL_GIANT_MOLE, WARRIOR_GUILD);

    public static final Set<SkillingDiaryEntry> ELITE_TASKS = EnumSet.of(STRANGE_FLOOR, KILL_BLACK_DRAGON, WEAR_PROSPECTOR);

    public static final String NAME = "Falador area";

    public SkillingAchievementDiary(Player player) { super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.SKILLING_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.SKILLING_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.SKILLING_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.SKILLING_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (SkillingDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(SkillingDiaryEntry.valueOf(s));
                }
            });
        }

        switch (difficulty) {
            //EASY
            case "KILL_DUCK":
                return achievements.contains(KILL_DUCK);
            case "WESTERN_WALL":
                return achievements.contains(WESTERN_WALL);
            case "FILL_BUCKET":
                return achievements.contains(FILL_BUCKET);
            case "REPAIR_STRUT":
                return achievements.contains(REPAIR_STRUT);
            case "TRAVEL_ENTRANA":
                return achievements.contains(TRAVEL_ENTRANA);
            case "MIND_TIARA":
                return achievements.contains(MIND_TIARA);
            case "FARMING_SHOP":
                return achievements.contains(FARMING_SHOP);
            //MEDIUM
            case "CRYSTAL_CHEST":
                return achievements.contains(CRYSTAL_CHEST);
            case "PICKPOCKET_GUARD":
                return achievements.contains(PICKPOCKET_GUARD);
            case "GOLD_ORE":
                return achievements.contains(GOLD_ORE);
            case "NARROW_CREVICE":
                return achievements.contains(NARROW_CREVICE);
            case "ALTAR_OF_GUTHIX":
                return achievements.contains(ALTAR_OF_GUTHIX);
            case "TELEPORT_FALADOR":
                return achievements.contains(TELEPORT_FALADOR);
            //HARD
            case "KILL_WYVERN":
                return achievements.contains(KILL_WYVERN);
            case "FALADOR_ROOFTOP":
                return achievements.contains(FALADOR_ROOFTOP);
            case "KILL_GIANT_MOLE":
                return achievements.contains(KILL_GIANT_MOLE);
            case "WARRIOR_GUILD":
                return achievements.contains(WARRIOR_GUILD);
            //ELITE
            case "STRANGE_FLOOR":
                return achievements.contains(STRANGE_FLOOR);
            case "KILL_BLACK_DRAGON":
                return achievements.contains(KILL_BLACK_DRAGON);
            case "WEAR_PROSPECTOR":
                return achievements.contains(WEAR_PROSPECTOR);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13117;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("FALADOR_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("FALADOR_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("FALADOR_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("FALADOR_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("FALADOR_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("FALADOR_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("FALADOR_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("FALADOR_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("FALADOR_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("FALADOR_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("FALADOR_ELITE", player);
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
        return player.getInventory().count(id); }

    @Override
    public Set<SkillingDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<SkillingDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<SkillingDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<SkillingDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Falador Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("KILL_DUCK") ? "<col=24d124>Kill a Duck in Falador Park.</col>" : "<col=911c25>Kill a Duck in Falador Park.",
                hasCompletedAchieve("WESTERN_WALL") ? "<col=24d124>Climb the Crumbling Wall shortcut.</col>" : "<col=911c25>Climb the Crumbling Wall shortcut.",
                hasCompletedAchieve("FILL_BUCKET") ? "<col=24d124>Fill a Bucket at the Waterpump in Northwest Falador.</col>" : "<col=911c25>Fill a Bucket at the Waterpump in Northwest Falador.",
                hasCompletedAchieve("REPAIR_STRUT") ? "<col=24d124>Repair a Broken Strut in the Motherlode Mine." : "<col=911c25>Repair a Broken Strut in the Motherlode Mine.",
                hasCompletedAchieve("TRAVEL_ENTRANA") ? "<col=24d124>Travel by boat to Entrana.</col>" : "<col=911c25>Travel by boat to Entrana.",
                hasCompletedAchieve("MIND_TIARA") ? "<col=24d124>Mine a Copper Ore in Rimmington.</col>" : "<col=911c25>Mine a Copper Ore in Rimmington.",
                hasCompletedAchieve("FARMING_SHOP") ? "<col=24d124>Kill a White Knight.</col>" : "<col=911c25>Kill a White Knight.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("CRYSTAL_CHEST") ? "<col=24d124>Unlock the Crystal Chest in Taverley.</col>" : "<col=911c25>Unlock the Crystal Chest in Taverley.",
                hasCompletedAchieve("PICKPOCKET_GUARD") ? "<col=24d124>Pickpocket a Falador Guard.</col>" : "<col=911c25>Pickpocket a Falador Guard.",
                hasCompletedAchieve("GOLD_ORE") ? "<col=24d124>Mine a piece of Gold Ore in the Crafting Guild.</col>" : "<col=911c25>Mine a piece of Gold Ore in the Crafting Guild.",
                hasCompletedAchieve("NARROW_CREVICE") ? "<col=24d124>Squeeze through the Crevice shortcut in the Dwarven Mines.</col>" : "<col=911c25>Squeeze through the Crevice shortcut in the Dwarven Mines.",
                hasCompletedAchieve("ALTAR_OF_GUTHIX") ? "<col=24d124>Pray at the Guthix Altar in Taverley.</col>" : "<col=911c25>Pray at the Guthix Altar in Taverley.",
                hasCompletedAchieve("TELEPORT_FALADOR") ? "<col=24d124>Teleport to Falador.</col>" : "<col=911c25>Teleport to Falador.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("KILL_WYVERN") ? "<col=24d124>Kill Skeletal Wyverns. (" + (getAbsoluteAchievementStage(KILL_WYVERN)) + "/" + getMaximum(KILL_WYVERN) + ")</col>" : "<col=911c25>Kill Skeletal Wyverns. (" + (getAbsoluteAchievementStage(KILL_WYVERN)) + "/" + getMaximum(KILL_WYVERN) + ")",
                hasCompletedAchieve("FALADOR_ROOFTOP") ? "<col=24d124>Complete the Falador Rooftop Agility Course. (" + (getAbsoluteAchievementStage(FALADOR_ROOFTOP)) + "/" + getMaximum(FALADOR_ROOFTOP) + ")</col>" : "<col=911c25>Complete the Falador Rooftop Agility Course. (" + (getAbsoluteAchievementStage(FALADOR_ROOFTOP)) + "/" + getMaximum(FALADOR_ROOFTOP) + ")",
                hasCompletedAchieve("KILL_GIANT_MOLE") ? "<col=24d124>Kill Giant Moles. (" + (getAbsoluteAchievementStage(KILL_GIANT_MOLE)) + "/" + getMaximum(KILL_GIANT_MOLE) + ")</col>" : "<col=911c25>Kill Giant Moles. (" + (getAbsoluteAchievementStage(KILL_GIANT_MOLE)) + "/" + getMaximum(KILL_GIANT_MOLE) + ")",
                hasCompletedAchieve("WARRIOR_GUILD") ? "<col=24d124>Enter the Warrior's Guild.</col>" : "<col=911c25>Enter the Warrior's Guild.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("STRANGE_FLOOR") ? "<col=24d124>Cross the Strange Floor shortcut in Taverley Dungeon.</col>" : "<col=911c25>Cross the Strange Floor shortcut in Taverley Dungeon.",
                hasCompletedAchieve("KILL_BLACK_DRAGON") ? "<col=24d124>Kill Black Dragons on the upper level of Taverley Dungeon. (" + (getAbsoluteAchievementStage(KILL_BLACK_DRAGON)) + "/" + getMaximum(KILL_BLACK_DRAGON) + ")</col>" : "<col=911c25>Kill Black Dragons on the upper level of Taverley Dungeon. (" + (getAbsoluteAchievementStage(KILL_BLACK_DRAGON)) + "/" + getMaximum(KILL_BLACK_DRAGON) + ")",
                hasCompletedAchieve("WEAR_PROSPECTOR") ? "<col=24d124>Enter the Mining Guild wearing full Prospector.</col>" : "<col=911c25>Enter the Mining Guild wearing full Prospector.");
    }

    @Override
    public int getMaximum(SkillingDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}