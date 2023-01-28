package io.ruin.model.diaries.pvp;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.pvp.PvPDiaryEntry.*;

public final class PvPAchievementDiary extends StatefulAchievementDiary<PvPDiaryEntry> {

    public static final Set<PvPDiaryEntry> EASY_TASKS = EnumSet.of(STEAL_CAKE, WILDERNESS_LEVER, TELEPORT_ESSENCE_ARD, CROSS_THE_LOG);

    public static final Set<PvPDiaryEntry> MEDIUM_TASKS = EnumSet.of(TELEPORT_ARDOUGNE, PICKPOCKET_ARD, IBANS_STAFF, DRAGON_SQUARE);

    public static final Set<PvPDiaryEntry> HARD_TASKS = EnumSet.of(STEAL_FUR, PRAY_WITH_CHIVALRY, CRAFT_DEATH, ARDOUGNE_ROOFTOP);

    public static final Set<PvPDiaryEntry> ELITE_TASKS = EnumSet.of(STEAL_GEM_ARD, PICKPOCKET_HERO, SUPER_COMBAT_ARD);

    public static final String NAME = "Ardougne area";

    public PvPAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.PVP_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.PVP_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.PVP_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.PVP_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (PvPDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(PvPDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "STEAL_CAKE":
                return achievements.contains(STEAL_CAKE);
            case "WILDERNESS_LEVER":
                return achievements.contains(WILDERNESS_LEVER);
            case "TELEPORT_ESSENCE":
                return achievements.contains(TELEPORT_ESSENCE_ARD);
            case "CROSS_LOG":
                return achievements.contains(CROSS_THE_LOG);
            //MEDIUM
            case "TELEPORT_ARD":
                return achievements.contains(TELEPORT_ARDOUGNE);
            case "PICKPOCKET_ARD":
                return achievements.contains(PICKPOCKET_ARD);
            case "IBANS":
                return achievements.contains(IBANS_STAFF);
            case "DRAGON_SQUARE":
                return achievements.contains(DRAGON_SQUARE);
            //HARD
            case "STEAL_FUR":
                return achievements.contains(STEAL_FUR);
            case "PRAY_WITH_CHIVALRY":
                return achievements.contains(PRAY_WITH_CHIVALRY);
            case "CRAFT_DEATH":
                return achievements.contains(CRAFT_DEATH);
            case "ARDOUGNE_ROOFTOP":
                return achievements.contains(ARDOUGNE_ROOFTOP);
            //ELITE
            case "STEAL_GEM":
                return achievements.contains(STEAL_GEM_ARD);
            case "PICKPOCKET_HERO":
                return achievements.contains(PICKPOCKET_HERO);
            case "SUPER_COMBAT":
                return achievements.contains(SUPER_COMBAT_ARD);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13121;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("ARDOUGNE_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("ARDOUGNE_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("ARDOUGNE_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("ARDOUGNE_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("ARDOUGNE_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("ARDOUGNE_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("ARDOUGNE_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("ARDOUGNE_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("ARDOUGNE_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("ARDOUGNE_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("ARDOUGNE_ELITE", player);
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
    public Set<PvPDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<PvPDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<PvPDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<PvPDiaryEntry> getElite() {
        return ELITE_TASKS;
    }


    public void display() {
        player.sendScroll("<col=8B0000>Ardougne Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("STEAL_CAKE") ? "<col=24d124>Steal from the Baker's Stall. (" + (getAbsoluteAchievementStage(STEAL_CAKE)) + "/" + getMaximum(STEAL_CAKE) + ")</col>" : "<col=911c25>Steal from the Baker's Stall. (" + (getAbsoluteAchievementStage(STEAL_CAKE)) + "/" + getMaximum(STEAL_CAKE) + ")",
                hasCompletedAchieve("WILDERNESS_LEVER") ? "<col=24d124>Use the lever to teleport to the Wilderness.</col>" : "<col=911c25>Use the lever to teleport to the Wilderness.",
                hasCompletedAchieve("TELEPORT_ESSENCE") ? "<col=24d124>Have Wizard Cromperty teleport you to the Essence Mine.</col>" : "<col=911c25>Have Wizard Cromperty teleport you to the Essence Mine.",
                hasCompletedAchieve("CROSS_LOG") ? "<col=24d124>Cross the river using the Log Balance shortcut.</col>" : "<col=911c25>Cross the river using the Log Balance shortcut.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("TELEPORT_ARD") ? "<col=24d124>Teleport to Ardougne.</col>" : "<col=911c25>Teleport to Ardougne.",
                hasCompletedAchieve("PICKPOCKET_ARD") ? "<col=24d124>Pickpocket the Master Farmer. (" + (getAbsoluteAchievementStage(PICKPOCKET_ARD)) + "/" + getMaximum(PICKPOCKET_ARD) + ")</col>" : "<col=911c25>Pickpocket the Master Farmer. (" + (getAbsoluteAchievementStage(PICKPOCKET_ARD)) + "/" + getMaximum(PICKPOCKET_ARD) + ")",
                hasCompletedAchieve("IBANS") ? "<col=24d124>Equip an Iban's Staff.</col>" : "<col=911c25>Equip an Iban's Staff.",
                hasCompletedAchieve("DRAGON_SQUARE") ? "<col=24d124>Create a Dragon Square Shield in West Ardougne.</col>" : "<col=911c25>Create a Dragon Square Shield in West Ardougne.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("STEAL_FUR") ? "<col=24d124>Steal from the Fur Stall. (" + (getAbsoluteAchievementStage(STEAL_FUR)) + "/" + getMaximum(STEAL_FUR) + ")</col>" : "<col=911c25>Steal from the Fur Stall. (" + (getAbsoluteAchievementStage(STEAL_FUR)) + "/" + getMaximum(STEAL_FUR) + ")",
                hasCompletedAchieve("PRAY_WITH_CHIVALRY") ? "<col=24d124>Pray in the Church with Chivalry active.</col>" : "<col=911c25>Pray in the Church with Chivalry active.",
                hasCompletedAchieve("CRAFT_DEATH") ? "<col=24d124>Craft a Death Rune.</col>" : "<col=911c25>Craft a Death Rune.",
                hasCompletedAchieve("ARDOUGNE_ROOFTOP") ? "<col=24d124>Complete the Ardougne Rooftop Agility Course. (" + (getAbsoluteAchievementStage(ARDOUGNE_ROOFTOP)) + "/" + getMaximum(ARDOUGNE_ROOFTOP) + ")</col>" : "<col=911c25>Complete the Ardougne Rooftop Agility Course. (" + (getAbsoluteAchievementStage(ARDOUGNE_ROOFTOP)) + "/" + getMaximum(ARDOUGNE_ROOFTOP) + ")",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("STEAL_GEM") ? "<col=24d124>Steal from the Gem Stall. (" + (getAbsoluteAchievementStage(STEAL_GEM_ARD)) + "/" + getMaximum(STEAL_GEM_ARD) + ")</col>" : "<col=911c25>Steal from the Gem Stall. (" + (getAbsoluteAchievementStage(STEAL_GEM_ARD)) + "/" + getMaximum(STEAL_GEM_ARD) + ")",
                hasCompletedAchieve("PICKPOCKET_HERO") ? "<col=24d124>Pickpocket Heroes. (" + (getAbsoluteAchievementStage(PICKPOCKET_HERO)) + "/" + getMaximum(PICKPOCKET_HERO) + ")</col>" : "<col=911c25>Pickpocket Heroes. (" + (getAbsoluteAchievementStage(PICKPOCKET_HERO)) + "/" + getMaximum(PICKPOCKET_HERO) + ")",
                hasCompletedAchieve("SUPER_COMBAT") ? "<col=24d124>Create Super Combat Potions on the bridge in the zoo. (" + (getAbsoluteAchievementStage(SUPER_COMBAT_ARD)) + "/" + getMaximum(SUPER_COMBAT_ARD) + ")</col>" : "<col=911c25>Create Super Combat Potions on the bridge in the zoo. (" + (getAbsoluteAchievementStage(SUPER_COMBAT_ARD)) + "/" + getMaximum(SUPER_COMBAT_ARD) + ")");
    }

    @Override
    public int getMaximum(PvPDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}