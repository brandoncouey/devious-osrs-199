package io.ruin.model.diaries.varrock;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.varrock.VarrockDiaryEntry.*;


public final class VarrockAchievementDiary extends StatefulAchievementDiary<VarrockDiaryEntry> {

    public static final Set<VarrockDiaryEntry> EASY_TASKS = EnumSet.of(TEA_STALL, TELEPORT_ESSENCE_VAR, MINE_IRON, EARTH_RUNES, MAKE_PLANK, STRONGHOLD, JUMP_FENCE);

    public static final Set<VarrockDiaryEntry> MEDIUM_TASKS = EnumSet.of(CHAMPIONS_GUILD, SPIRIT_TREE, DIGSITE, PURCHASE_KITTEN, TELEPORT_VARROCK);

    public static final Set<VarrockDiaryEntry> HARD_TASKS = EnumSet.of(OBSTACLE_PIPE, ROOFTOP);

    public static final Set<VarrockDiaryEntry> ELITE_TASKS = EnumSet.of(SUPER_COMBAT, SUMMER_PIE, ALOT_OF_EARTH);

    public static final String NAME = "Varrock area";

    public VarrockAchievementDiary(Player player) { super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.VARROCK_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.VARROCK_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.VARROCK_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.VARROCK_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (VarrockDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(VarrockDiaryEntry.valueOf(s));
                }
            });
        }

        switch (difficulty) {
            //EASY
            case "TEA_STALL":
                return achievements.contains(TEA_STALL);
            case "TELEPORT_ESSENCE_VAR":
                return achievements.contains(TELEPORT_ESSENCE_VAR);
            case "MINE_IRON":
                return achievements.contains(MINE_IRON);
            case "MAKE_PLANK":
                return achievements.contains(MAKE_PLANK);
            case "EARTH_RUNES":
                return achievements.contains(EARTH_RUNES);
            case "STRONGHOLD":
                return achievements.contains(STRONGHOLD);
            case "JUMP_FENCE":
                return achievements.contains(JUMP_FENCE);
            //MEDIUM
            case "CHAMPIONS_GUILD":
                return achievements.contains(CHAMPIONS_GUILD);
            case "SPIRIT_TREE":
                return achievements.contains(SPIRIT_TREE);
            case "DIGSITE":
                return achievements.contains(DIGSITE);
            case "PURCHASE_KITTEN":
                return achievements.contains(PURCHASE_KITTEN);
            case "TELEPORT_VARROCK":
                return achievements.contains(TELEPORT_VARROCK);
            //HARD
            case "OBSTACLE_PIPE":
                return achievements.contains(OBSTACLE_PIPE);
            case "ROOFTOP":
                return achievements.contains(ROOFTOP);
            //ELITE
            case "SUPER_COMBAT":
                return achievements.contains(SUPER_COMBAT);
            case "SUMMER_PIE":
                return achievements.contains(SUMMER_PIE);
            case "ALOT_OF_EARTH":
                return achievements.contains(ALOT_OF_EARTH);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13104;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }


        if (!player.DiaryRewards.contains("VARROCK_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("VARROCK_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("VARROCK_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("VARROCK_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("VARROCK_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("VARROCK_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("VARROCK_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("VARROCK_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("VARROCK_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("VARROCK_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(13107, npc);
            claim("VARROCK_ELITE", player);
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
    public Set<VarrockDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<VarrockDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<VarrockDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<VarrockDiaryEntry> getElite() {
        return ELITE_TASKS;
    }


    public final void display() {
        player.sendScroll("<col=8B0000>Varrock Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("STEAL_TEA") ? "<col=24d124>Steal from the Tea Stall.</col>" : "<col=911c25>Steal from the Tea Stall.",
                hasCompletedAchieve("TELEPORT_ESSENCE_VAR") ? "<col=24d124>Have Aubury teleport you to the Essence Mine.</col>" : "<col=911c25>Have Aubury teleport you to the Essence Mine.",
                hasCompletedAchieve("MINE_IRON") ? "<col=24d124>Mine Iron Ore in the East Varrock mine. " + (getAbsoluteAchievementStage(MINE_IRON)) + "/" + getMaximum(MINE_IRON) + "</col>" : "<col=911c25>Mine Iron Ore in the East Varrock mine. " + (getAbsoluteAchievementStage(MINE_IRON)) + "/" + getMaximum(MINE_IRON) + ")",
                hasCompletedAchieve("MAKE_PLANK") ? "<col=24d124>Make a Plank in Edgeville, south of bank..</col>" : "<col=911c25>Make a Plank at Edgeville.",
                hasCompletedAchieve("EARTH_RUNES") ? "<col=24d124>Craft a Earth Rune.</col>" : "<col=911c25>Craft a Earth Rune.",
                hasCompletedAchieve("STRONGHOLD") ? "<col=24d124>Enter the second level of the Stronghold of Security.</col>" : "<col=911c25>Enter the second level of the Stronghold of Security.",
                hasCompletedAchieve("JUMP_FENCE") ? "<col=24d124>Jump the Fence South of Varrock.</col>" : "<col=911c25>Jump the Fence South of Varrock.",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("CHAMPIONS_GUILD") ? "<col=24d124>Enter the Champions' Guild.</col>" : "<col=911c25>Enter the Champions' Guild.",
                hasCompletedAchieve("SPIRIT_TREE") ? "<col=24d124>Travel by Spirit Tree from the Grand Exchange.</col>" : "<col=911c25>Travel by Spirit Tree from the Grand Exchange.",
                hasCompletedAchieve("DIGSITE") ? "<col=24d124>Teleport to the Digsite using a Digsite Pendant.</col>" : "<col=911c25>Teleport to the Digsite using a Digsite Pendant.",
                hasCompletedAchieve("PURCHASE_KITTEN") ? "<col=24d124>Buy a Kitten from Gertrude.</col>" : "<col=911c25>Buy a Kitten from Gertrude.",
                hasCompletedAchieve("TELEPORT_VARROCK") ? "<col=24d124>Teleport to Varrock.</col>" : "<col=911c25>Teleport to Varrock.",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("OBSTACLE_PIPE") ? "<col=24d124>Use the Obstacle Pipe shortcut in the Edgeville Dungeon.</col>" : "<col=911c25>Use the Obstacle Pipe shortcut in the Edgeville Dungeon.",
                hasCompletedAchieve("ROOFTOP") ? "<col=24d124>Complete the Varrock Rooftop Agility Course. (" + (getAbsoluteAchievementStage(ROOFTOP)) + "/" + getMaximum(ROOFTOP) + "</col>" : "<col=911c25>Complete the Varrock Rooftop Agility Course. (" + (getAbsoluteAchievementStage(ROOFTOP)) + "/" + getMaximum(ROOFTOP) + ")",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("SUPER_COMBAT") ? "<col=24d124>Create a Super Combat Potion in the West Varrock bank.</col>" : "<col=911c25>Create a Super Combat Potion in the West Varrock bank.",
                hasCompletedAchieve("SUMMER_PIE") ? "<col=24d124>Bake a Summer Pie in the Cooking Guild.</col>" : "<col=911c25>Bake a Summer Pie in the Cooking Guild.",
                hasCompletedAchieve("ALOT_OF_EARTH") ? "<col=24d124>Craft 100 Earth Runes at once.</col>" : "<col=911c25>Craft 100 Earth Runes at once."
        );
    }

    @Override
    public int getMaximum(VarrockDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}