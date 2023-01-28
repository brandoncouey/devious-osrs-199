package io.ruin.model.diaries.western;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.western.WesternDiaryEntry.*;


public final class WesternAchievementDiary extends StatefulAchievementDiary<WesternDiaryEntry> {

    public static final Set<WesternDiaryEntry> EASY_TASKS = EnumSet.of(MINE_IRON, GNOME_AGILITY, PEST_CONTROL_TELEPORT, FLETCH_OAK_SHORT_WEST, TELEPORT_ESSENCE_MINE, PEST_CONTROL);

    public static final Set<WesternDiaryEntry> MEDIUM_TASKS = EnumSet.of(SHORTCUT, SPIRIT_TREE, MINING_GOLD, BURN_LOGS);

    public static final Set<WesternDiaryEntry> HARD_TASKS = EnumSet.of(COOK_MONK, TELEPORT_APE_ATOLL, PICKPOCKET_ELF);

    public static final Set<WesternDiaryEntry> ELITE_TASKS = EnumSet.of(KILL_ZULRAH, KILL_THERMO);

    public static final String NAME = "Western area";

    public WesternAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.WESTERN_PROV_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.WESTERN_PROV_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.WESTERN_PROV_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.WESTERN_PROV_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13141;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("WESTERN_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("WESTERN_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("WESTERN_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("WESTERN_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("WESTERN_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("WESTERN_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("WESTERN_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("WESTERN_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("WESTERN_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("WESTERN_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("WESTERN_ELITE", player);
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
    public Set<WesternDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<WesternDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<WesternDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<WesternDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (WesternDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(WesternDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "MINE_IRON":
                return achievements.contains(MINE_IRON);
            case "GNOME_AGILITY":
                return achievements.contains(GNOME_AGILITY);
            case "PEST_CONTROL_TELEPORT":
                return achievements.contains(PEST_CONTROL_TELEPORT);
            case "FLETCH_OAK_SHORT_WEST":
                return achievements.contains(FLETCH_OAK_SHORT_WEST);
            case "PEST_CONTROL":
                return achievements.contains(PEST_CONTROL);
            case "TELEPORT_ESSENCE_MINE":
                return achievements.contains(TELEPORT_ESSENCE_MINE);
            //MEDIUM
            case "SHORTCUT":
                return achievements.contains(SHORTCUT);
            case "SPIRIT_TREE":
                return achievements.contains(SPIRIT_TREE);
            case "MINING_GOLD":
                return achievements.contains(MINING_GOLD);
            case "BURN_LOGS":
                return achievements.contains(BURN_LOGS);
            //HARD
            case "COOK_MONK":
                return achievements.contains(COOK_MONK);
            case "TELEPORT_APE_ATOLL":
                return achievements.contains(TELEPORT_APE_ATOLL);
            case "PICKPOCKET_ELF":
                return achievements.contains(PICKPOCKET_ELF);
            //ELITE
            case "KILL_ZULRAH":
                return achievements.contains(KILL_ZULRAH);
            case "KILL_THERMO":
                return achievements.contains(KILL_THERMO);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public void display() {
        player.sendScroll("<col=8B0000>Western Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("MINE_IRON") ? "<col=24d124>Mine an Iron Ore near Piscatoris. (" + (getAbsoluteAchievementStage(MINE_IRON)) + "/" + getMaximum(MINE_IRON) + ")</col>" : "<col=911c25>Mine some iron ore near piscatoris. (" + (getAbsoluteAchievementStage(MINE_IRON)) + "/" + getMaximum(MINE_IRON) + ")",
                hasCompletedAchieve("GNOME_AGILITY") ? "<col=24d124>Complete the Gnome Agility Course.</col>" : "<col=911c25>Complete the Gnome Agility Course.",
                hasCompletedAchieve("PEST_CONTROL_TELEPORT") ? "<col=24d124>Teleport to Pest Control.</col>" : "<col=911c25>Teleport to Pest Control.",
                hasCompletedAchieve("FLETCH_OAK_SHORT_WEST") ? "<col=24d124>Fletch an Oak Shortbow in the Tree Gnome Stronghold.</col>" : "<col=911c25>Fletch an Oak Shortbow in the Tree Gnome Stronghold.",
                hasCompletedAchieve("TELEPORT_ESSENCE_MINE") ? "<col=24d124>Have Brimstail teleport you to the Essence Mine.</col>" : "<col=911c25>Have Brimstail teleport you to the Essence Mine.",
                hasCompletedAchieve("PEST_CONTROL") ? "<col=24d124>Complete a game of Pest Control.</col>" : "<col=911c25>Complete a game of Pest Control.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("SHORTCUT") ? "<col=24d124>Climb down the hill from the Grand Tree to Otto's Grotto.</col>" : "<col=911c25>Climb down the hill from the Grand Tree to Otto's Grotto.",
                hasCompletedAchieve("SPIRIT_TREE") ? "<col=24d124>Travel by Spirit Tree to the Tree Gnome Stronghold.</col>" : "<col=911c25>Travel by Spirit Tree to the Tree Gnome Stronghold.",
                hasCompletedAchieve("MINING_GOLD") ? "<col=24d124>Mine Gold Ore in the Grand Tree Mine. (" + (getAbsoluteAchievementStage(MINING_GOLD)) + "/" + getMaximum(MINING_GOLD) + ")</col>" : "<col=911c25>Mine Gold Ore in the Grand Tree Mine. (" + (getAbsoluteAchievementStage(MINING_GOLD)) + "/" + getMaximum(MINING_GOLD) + ")",
                hasCompletedAchieve("BURN_LOGS") ? "<col=24d124>Burn Teak Logs on Ape Atoll. (" + (getAbsoluteAchievementStage(BURN_LOGS)) + "/" + getMaximum(BURN_LOGS) + ")</col>" : "<col=911c25>Burn Teak Logs on Ape Atoll. (" + (getAbsoluteAchievementStage(BURN_LOGS)) + "/" + getMaximum(BURN_LOGS) + ")",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("COOK_MONK") ? "<col=24d124>Cook Monkfish in Piscatoris. (" + (getAbsoluteAchievementStage(COOK_MONK)) + "/" + getMaximum(COOK_MONK) + ")</col>" : "<col=911c25>Cook Monkfish in Piscatoris. (" + (getAbsoluteAchievementStage(COOK_MONK)) + "/" + getMaximum(COOK_MONK) + ")",
                hasCompletedAchieve("TELEPORT_APE_ATOLL") ? "<col=24d124>Teleport to Ape Atoll.</col>" : "<col=911c25>Teleport to Ape Atoll.",
                hasCompletedAchieve("PICKPOCKET_ELF") ? "<col=24d124>Pickpocket an Elf.</col>" : "<col=911c25>Pickpocket an Elf.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("KILL_ZULRAH") ? "<col=24d124>Kill Zulrah.</col>" : "<col=911c25>Kill Zulrah.",
                hasCompletedAchieve("KILL_THERMO") ? "<col=24d124>Kill the Thermonuclear Smoke Devil.</col>" : "<col=911c25>Kill the Thermonuclear Smoke Devil."
        );
    }

    @Override
    public int getMaximum(WesternDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}