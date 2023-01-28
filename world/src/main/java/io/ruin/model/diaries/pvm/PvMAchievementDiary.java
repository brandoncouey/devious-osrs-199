package io.ruin.model.diaries.pvm;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.pvm.PvMDiaryEntry.*;


public final class PvMAchievementDiary extends StatefulAchievementDiary<PvMDiaryEntry> {

    public static final Set<PvMDiaryEntry> EASY_TASKS = EnumSet.of(MINE_GOLD, TELEPORT_TO_KARAMJA, SAIL_TO_ARDOUGNE, SAIL_TO_PORT_SARIM, ATTEMPT_FIGHT_CAVES);

    public static final Set<PvMDiaryEntry> MEDIUM_TASKS = EnumSet.of(DURADEL, CHOP_VINE, TRAVEL_PORT_KHAZARD);

    public static final Set<PvMDiaryEntry> HARD_TASKS = EnumSet.of(COMPLETE_FIGHT_CAVES, KILL_KET_ZEK, KILL_METAL_DRAGON);

    public static final Set<PvMDiaryEntry> ELITE_TASKS = EnumSet.of(CRAFT_NATURES, ANTI_VENOM, COMPLETE_INFERNO);

    public static final String NAME = "Karamja area";

    public PvMAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.PVM_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.PVM_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.PVM_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.PVM_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 11136;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("KARAMJA_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("KARAMJA_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("KARAMJA_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("KARAMJA_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 2, npc);
            claim("KARAMJA_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("KARAMJA_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("KARAMJA_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 4, npc);
            claim("KARAMJA_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("KARAMJA_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 4) && !hasClaimed("KARAMJA_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 4, 1);
            addReward(13103, npc);
            claim("KARAMJA_ELITE", player);
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
        if (player.getBank().contains(id))
            return 1;
        if (player.getEquipment().contains(id))
            return 1;

        return player.getInventory().getAmount(id);
    }

    @Override
    public Set<PvMDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<PvMDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<PvMDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<PvMDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (PvMDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(PvMDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "MINE_GOLD":
                return achievements.contains(MINE_GOLD);
            case "TELEPORT_TO_KARAMJA":
                return achievements.contains(TELEPORT_TO_KARAMJA);
            case "SAIL_TO_ARDOUGNE":
                return achievements.contains(SAIL_TO_ARDOUGNE);
            case "SAIL_TO_PORT_SARIM":
                return achievements.contains(SAIL_TO_PORT_SARIM);
            case "ATTEMPT_FIGHT_CAVES":
                return achievements.contains(ATTEMPT_FIGHT_CAVES);
            //MEDIUM
            case "DURADEL":
                return achievements.contains(DURADEL);
            case "CHOP_VINE":
                return achievements.contains(CHOP_VINE);
            case "TRAVEL_PORT_KHAZARD":
                return achievements.contains(TRAVEL_PORT_KHAZARD);
            //HARD
            case "COMPLETE_FIGHT_CAVES":
                return achievements.contains(COMPLETE_FIGHT_CAVES);
            case "KILL_KET_ZEK":
                return achievements.contains(KILL_KET_ZEK);
            case "KILL_METAL_DRAGON":
                return achievements.contains(KILL_METAL_DRAGON);
            //ELITE
            case "CRAFT_NATURES":
                return achievements.contains(CRAFT_NATURES);
            case "ANTI_VENOM":
                return achievements.contains(ANTI_VENOM);
            case "COMPLETE_INFERNO":
                return achievements.contains(COMPLETE_INFERNO);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Karamja Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("MINE_GOLD") ? "<col=24d124>Mine Gold Ore at the Horseshoe Mine. (" + (getAbsoluteAchievementStage(MINE_GOLD)) + "/" + getMaximum(MINE_GOLD) + ")</col>" : "<col=911c25>Mine Gold Ore at the Horseshoe Mine. (" + (getAbsoluteAchievementStage(MINE_GOLD)) + "/" + getMaximum(MINE_GOLD) + ")",
                hasCompletedAchieve("TELEPORT_TO_KARAMJA") ? "<col=24d124>Teleport to Karamja using an Amulet of Glory.</col>" : "<col=911c25>Teleport to Karamja using an Amulet of Glory.",
                hasCompletedAchieve("SAIL_TO_ARDOUGNE") ? "<col=24d124>Travel by boat from Brimhaven to Ardougne.</col>" : "<col=911c25>Travel by boat from Brimhaven to Ardougne.",
                hasCompletedAchieve("SAIL_TO_PORT_SARIM") ? "<col=24d124>Travel by boat from Karamja to Port Sarim.</col>" : "<col=911c25>Travel by boat from Karamja to Port Sarim.",
                hasCompletedAchieve("ATTEMPT_FIGHT_CAVES") ? "<col=24d124>Attempt the Tzhaar Fight Caves.</col>" : "<col=911c25>Attempt the Tzhaar Fight Caves.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("DURADEL") ? "<col=24d124>Be assigned a Slayer Task by Duradel.</col>" : "<col=911c25>Be assigned a Slayer Task by Duradel.",
                hasCompletedAchieve("CHOP_VINE") ? "<col=24d124>Chop the Vines in Brimhaven Dungeon.</col>" : "<col=911c25>Chop the Vines in Brimhaven Dungeon.",
                hasCompletedAchieve("TRAVEL_PORT_KHAZARD") ? "<col=24d124>Travel by boat from Cairn Isle to Port Khazard.</col>" : "<col=911c25>Travel by boat from Cairn Isle to Port Khazard.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("COMPLETE_FIGHT_CAVES") ? "<col=24d124>Complete the Tzhaar Fight Caves.</col>" : "<col=911c25>Complete the Tzhaar Fight Caves.",
                hasCompletedAchieve("KILL_KET_ZEK") ? "<col=24d124>Teleport to Tai Bwo Wannai.</col>" : "<col=911c25>Teleport to Tai Bwo Wannai.",
                hasCompletedAchieve("KILL_METAL_DRAGON") ? "<col=24d124>Kill a Metal Dragon in the Brimhaven Dungeon.</col>" : "<col=911c25>Kill a Metal Dragon in the Brimhaven Dungeon.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("CRAFT_NATURES") ? "<col=24d124>Craft 56 Nature Runes at once.</col>" : "<col=911c25>Craft 56 Nature Runes at once.",
                hasCompletedAchieve("ANTI_VENOM") ? "<col=24d124>Create an Antivenom in the Horseshoe mine.</col>" : "<col=911c25>Create an Antivenom in the Horseshoe mine.",
                hasCompletedAchieve("COMPLETE_INFERNO") ? "<col=24d124>Complete the Inferno.</col>" : "<col=911c25>Complete the Inferno.");
    }

    @Override
    public int getMaximum(PvMDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}