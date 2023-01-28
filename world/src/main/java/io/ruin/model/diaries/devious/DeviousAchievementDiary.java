package io.ruin.model.diaries.devious;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.devious.DeviousDiaryEntry.*;

public class DeviousAchievementDiary extends StatefulAchievementDiary<DeviousDiaryEntry> {

    public static final Set<DeviousDiaryEntry> EASY_TASKS = EnumSet.of(SPELL_BOOK, SPEAK_TO_DEATH, FOUNTAIN, BUY_HOUSE, GE, VOTE_STORE, DONATION_STORE);

    public static final Set<DeviousDiaryEntry> MEDIUM_TASKS = EnumSet.of(CATACOMBS, MOLCH_ISLAND);

    public static final Set<DeviousDiaryEntry> HARD_TASKS = EnumSet.of(RECOVER_CANNON);

    public static final Set<DeviousDiaryEntry> ELITE_TASKS = EnumSet.of(COX);

    public static final String NAME = "Kourend area";

    public DeviousAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.DEVIOUS_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.DEVIOUS_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.DEVIOUS_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.DEVIOUS_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 22941;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("KOUREND_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("KOUREND_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("KOUREND_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("KOUREND_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 2, npc);
            claim("KOUREND_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("KOUREND_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("KOUREND_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 4, npc);
            claim("KOUREND_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("KOUREND_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 4) && !hasClaimed("KOUREND_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 4, 1);
            addReward(REWARD + 6, npc);
            claim("KOUREND_ELITE", player);
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
    public Set<DeviousDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<DeviousDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<DeviousDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<DeviousDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (DeviousDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(DeviousDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "SPELL_BOOK":
                return achievements.contains(SPELL_BOOK);
            case "SPEAK_TO_DEATH":
                return achievements.contains(SPEAK_TO_DEATH);
            case "FOUNTAIN":
                return achievements.contains(FOUNTAIN);
            case "BUY_HOUSE":
                return achievements.contains(BUY_HOUSE);
            case "GE":
                return achievements.contains(GE);
            case "VOTE_STORE":
                return achievements.contains(VOTE_STORE);
            case "DONATION_STORE":
                return achievements.contains(DONATION_STORE);
            //MEDIUM
            case "CATACOMBS":
                return achievements.contains(CATACOMBS);
            case "MOLCH_ISLAND":
                return achievements.contains(MOLCH_ISLAND);
            //HARD
            case "RECOVER_CANNON":
                return achievements.contains(RECOVER_CANNON);
            //ELITE
            case "COX":
                return achievements.contains(COX);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Devious Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("SPELL_BOOK") ? "<col=24d124>Change your Spellbook.</col>" : "<col=911c25>Change your Spellbook.",
                hasCompletedAchieve("SPEAK_TO_DEATH") ? "<col=24d124>Speak to Death.</col>" : "<col=911c25>Speak to Death.",
                hasCompletedAchieve("FOUNTAIN") ? "<col=24d124>Restore your stats at the Fountain.</col>" : "<col=911c25>Restore your stats at the Fountain.",
                hasCompletedAchieve("BUY_HOUSE") ? "<col=24d124>Purchase a House.</col>" : "<col=911c25>Purchase a House.",
                hasCompletedAchieve("GE") ? "<col=24d124>Open the Grand Exchange.</col>" : "<col=911c25>Open the Grand Exchange.",
                hasCompletedAchieve("VOTE_STORE") ? "<col=24d124>Open the Vote Store.</col>" : "<col=911c25>Open the Vote Store.",
                hasCompletedAchieve("DONATION_STORE") ? "<col=24d124>Open the Donation Store.</col>" : "<col=911c25>Open the Donation Store.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("CATACOMBS") ? "<col=24d124>Enter the Catacombs of Kourend.</col>" : "<col=911c25>Enter the Catacombs of Kourend.",
                hasCompletedAchieve("MOLCH_ISLAND") ? "<col=24d124>Travel by boat to Molch Island.</col>" : "<col=911c25>Travel by boat to Molch Island.",
                hasCompletedAchieve("LOVAKENGJ") ? "<col=24d124>Smelt a Steel Bar in Lovakengj.</col>" : "<col=911c25>Smelt a Steel Bar in Lovakengj.",
                hasCompletedAchieve("COOK_LOBSTER") ? "<col=24d124>Cook a Lobster in Kingstown.</col>" : "<col=911c25>Cook a Lobster in Kingstown.",
                hasCompletedAchieve("KILL_SANDCRAB") ? "<col=24d124>Kill a Sand Crab.</col>" : "<col=911c25>Kill a Sand Crab.",
                hasCompletedAchieve("CATCH_COPPER") ? "<col=24d124>Catch a Copper Longtail in the Kourend Woodland.</col>" : "<col=911c25>Catch a Copper Longtail in the Kourend Woodland.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("RECOVER_CANNON") ? "<col=24d124>Recover your lost cannon.</col>" : "<col=911c25>Recover your lost cannon.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("COX") ? "<col=24d124>Complete the Chambers of Xeric.</col>" : "<col=911c25>Complete the Chambers of Xeric.");
    }

    @Override
    public int getMaximum(DeviousDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }
}
