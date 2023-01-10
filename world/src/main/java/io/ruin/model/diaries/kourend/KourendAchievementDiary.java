package io.ruin.model.diaries.kourend;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.kourend.KourendDiaryEntry.*;

public class KourendAchievementDiary extends StatefulAchievementDiary<KourendDiaryEntry> {

    public static final Set<KourendDiaryEntry> EASY_TASKS = EnumSet.of(SPELL_BOOK, SPEAK_TO_DEATH, FOUNTAIN, BUY_HOUSE, GE, VOTE_STORE, DONATION_STORE);

    public static final Set<KourendDiaryEntry> MEDIUM_TASKS = EnumSet.of(CATACOMBS, MOLCH_ISLAND, LOVAKENGJ, KILL_JELLIES, COOK_LOBSTER, KILL_SANDCRAB, CATCH_COPPER);

    public static final Set<KourendDiaryEntry> HARD_TASKS = EnumSet.of(WOODCUTTING_GUILD, KILL_LIZARDSHAMAN, STEAL_SILVER, DBONE_ALTAR, WINTERTODT, KOUREND, BLOOD_RUNE, KILL_NECHRYAEL);

    public static final Set<KourendDiaryEntry> ELITE_TASKS = EnumSet.of(CHOP_YEW, SOUL_RUNE, KILL_ABYSSAL, KILL_SKOTIZO, ARCLIGHT, COX);

    public static final String NAME = "Kourend area";

    public KourendAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.KOUREND_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.KOUREND_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.KOUREND_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.KOUREND_ELITE_COMPLETED.set(player, 1);
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
    public Set<KourendDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<KourendDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<KourendDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<KourendDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (KourendDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(KourendDiaryEntry.valueOf(s));
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
            case "LOVAKENGJ":
                return achievements.contains(LOVAKENGJ);
            case "KILL_JELLIES":
                return achievements.contains(KILL_JELLIES);
            case "COOK_LOBSTER":
                return achievements.contains(COOK_LOBSTER);
            case "KILL_SANDCRAB":
                return achievements.contains(KILL_SANDCRAB);
            case "CATCH_COPPER":
                return achievements.contains(CATCH_COPPER);
            //HARD
            case "WOODCUTTING_GUILD":
                return achievements.contains(WOODCUTTING_GUILD);
            case "KILL_LIZARDSHAMAN":
                return achievements.contains(KILL_LIZARDSHAMAN);
            case "STEAL_SILVER":
                return achievements.contains(STEAL_SILVER);
            case "DBONE_ALTAR":
                return achievements.contains(DBONE_ALTAR);
            case "WINTERTODT":
                return achievements.contains(WINTERTODT);
            case "KOUREND":
                return achievements.contains(KOUREND);
            case "BLOOD_RUNE":
                return achievements.contains(BLOOD_RUNE);
            case "KILL_NECHRYAEL":
                return achievements.contains(KILL_NECHRYAEL);
            //ELITE
            case "CHOP_YEW":
                return achievements.contains(CHOP_YEW);

            case "SOUL_RUNE":
                return achievements.contains(SOUL_RUNE);
            case "KILL_ABYSSAL":
                return achievements.contains(KILL_ABYSSAL);
            case "KILL_SKOTIZO":
                return achievements.contains(KILL_SKOTIZO);
            case "ARCLIGHT":
                return achievements.contains(ARCLIGHT);
            case "COX":
                return achievements.contains(COX);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Kourend & Kebos Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("SPELL_BOOK") ? "<col=24d124>Change your Spellbook.</col>" : "<col=911c25>Change your Spellbook.",
                hasCompletedAchieve("SPEAK_TO_DEATH") ? "<col=24d124>Speak to Death.</col>" : "<col=911c25>Speak to Death.",
                hasCompletedAchieve("FOUNTAIN") ? "<col=24d124>Restore your stats at the Fountain.</col>" : "<col=911c25>Restore your stats at the Fountain.",
                hasCompletedAchieve("BUY_HOUSE") ? "<col=24d124>Purchase a House.</col>" : "<col=911c25>Purchase a House.",
                hasCompletedAchieve("GE") ? "<col=24d124>Open the Grand Exchange.</col>" : "<col=911c25>Open the Grand Exchange.",
                hasCompletedAchieve("VOTE_STORE") ? "<col=24d124>Open the Vote Store.</col>" : "<col=911c25>Open the Vote Store.",
                hasCompletedAchieve("DONATION_STORE") ? "<col=24d124>Open the Donation Store.</col>" : "<col=911c25>Open the Donation Store.",
                //hasCompletedAchieve("MITHRIL_SEED") ? "<col=24d124>Plant a Mithril Seed in the Gamble Area.</col>" : "<col=911c25>Plant a Mithril Seed in the Gamble Area.",
                "",
                "<col=501061><strong><u>Medium",
                // hasCompletedAchieve("KOUREND_SARIM") ? "<col=24d124>Travel by boat from Kourend to Port Sarim.</col>" : "<col=911c25>Travel by boat from Kourend to Port Sarim.",
                hasCompletedAchieve("CATACOMBS") ? "<col=24d124>Enter the Catacombs of Kourend.</col>" : "<col=911c25>Enter the Catacombs of Kourend.",
                hasCompletedAchieve("MOLCH_ISLAND") ? "<col=24d124>Travel by boat to Molch Island.</col>" : "<col=911c25>Travel by boat to Molch Island.",
                hasCompletedAchieve("LOVAKENGJ") ? "<col=24d124>Smelt a Steel Bar in Lovakengj.</col>" : "<col=911c25>Smelt a Steel Bar in Lovakengj.",
                hasCompletedAchieve("KILL_JELLIES") ? "<col=24d124>Kill Warped Jellies in the Catacombs. (" + (getAbsoluteAchievementStage(KILL_JELLIES)) + "/" + getMaximum(KILL_JELLIES) + ")</col>" : "<col=911c25>Kill Warped Jellies in the Catacombs. (" + (getAbsoluteAchievementStage(KILL_JELLIES)) + "/" + getMaximum(KILL_JELLIES) + ")",
                hasCompletedAchieve("COOK_LOBSTER") ? "<col=24d124>Cook a Lobster in Kingstown.</col>" : "<col=911c25>Cook a Lobster in Kingstown.",
                hasCompletedAchieve("KILL_SANDCRAB") ? "<col=24d124>Kill a Sand Crab.</col>" : "<col=911c25>Kill a Sand Crab.",
                hasCompletedAchieve("CATCH_COPPER") ? "<col=24d124>Catch a Copper Longtail in the Kourend Woodland.</col>" : "<col=911c25>Catch a Copper Longtail in the Kourend Woodland.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("WOODCUTTING_GUILD") ? "<col=24d124>Enter the Woodcutting Guild.</col>" : "<col=911c25>Enter the Woodcutting Guild.",
                hasCompletedAchieve("KILL_LIZARDSHAMAN") ? "<col=24d124>Kill a Lizardman Shaman.</col>" : "<col=911c25>Kill a Lizardman Shaman.",
                hasCompletedAchieve("STEAL_SILVER") ? "<col=24d124>Steal from the Silver Stall. in Hosidious (" + (getAbsoluteAchievementStage(STEAL_SILVER)) + "/" + getMaximum(STEAL_SILVER) + ")</col>" : "<col=911c25>Steal from the Silver Stall. (" + (getAbsoluteAchievementStage(STEAL_SILVER)) + "/" + getMaximum(STEAL_SILVER) + ")",
                hasCompletedAchieve("DBONE_ALTAR") ? "<col=24d124>Use a Dragon Bone on the Altar.</col>" : "<col=911c25>Use a Dragon Bone on the Altar.",
                hasCompletedAchieve("WINTERTODT") ? "<col=24d124>Complete a game of Wintertodt.</col>" : "<col=911c25>Complete a game of Wintertodt.",
                hasCompletedAchieve("KOUREND") ? "<col=24d124>Teleport to Great Kourend.</col>" : "<col=911c25>Teleport to Great Kourend.",
                hasCompletedAchieve("BLOOD_RUNE") ? "<col=24d124>Craft a Blood Rune.</col>" : "<col=911c25>Craft a Blood Rune.",
                hasCompletedAchieve("KILL_NECHRYAEL") ? "<col=24d124>Kill Greater Nechryael in the Catacombs. (" + (getAbsoluteAchievementStage(KILL_NECHRYAEL)) + "/" + getMaximum(KILL_NECHRYAEL) + ")</col>" : "<col=911c25>Kill Greater Nechryael in the Catacombs. (" + (getAbsoluteAchievementStage(KILL_NECHRYAEL)) + "/" + getMaximum(KILL_NECHRYAEL) + ")",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("CHOP_YEW") ? "<col=24d124>Chop Yew Logs in the Woodcutting Guild. (" + (getAbsoluteAchievementStage(CHOP_YEW)) + "/" + getMaximum(CHOP_YEW) + ")</col>" : "<col=911c25>Chop Yew Logs in the Woodcutting Guild. (" + (getAbsoluteAchievementStage(CHOP_YEW)) + "/" + getMaximum(CHOP_YEW) + ")",
                //  hasCompletedAchieve("CATCH_ANGLER") ? "<col=24d124>Catch a Anglerfish in Port Piscarilius.</col>" : "<col=911c25>Catch a Anglerfish in Port Piscarilius.",
                hasCompletedAchieve("SOUL_RUNE") ? "<col=24d124>Craft a Soul Rune.</col>" : "<col=911c25>Craft a Soul Rune.",
                hasCompletedAchieve("KILL_ABYSSAL") ? "<col=24d124>Kill Abyssal Demons in the Catacombs. (" + (getAbsoluteAchievementStage(KILL_ABYSSAL)) + "/" + getMaximum(KILL_ABYSSAL) + ")</col>" : "<col=911c25>Kill Abyssal Demons in the Catacombs. (" + (getAbsoluteAchievementStage(KILL_ABYSSAL)) + "/" + getMaximum(KILL_ABYSSAL) + ")",
                hasCompletedAchieve("KILL_SKOTIZO") ? "<col=24d124>Kill Skotizo. (" + (getAbsoluteAchievementStage(KILL_SKOTIZO)) + "/" + getMaximum(KILL_SKOTIZO) + ")</col>" : "<col=911c25>Kill Skotizo. (" + (getAbsoluteAchievementStage(KILL_SKOTIZO)) + "/" + getMaximum(KILL_SKOTIZO) + ")",
                hasCompletedAchieve("ARCLIGHT") ? "<col=24d124>Create a Arclight.</col>" : "<col=911c25>Create a Arclight.",
                hasCompletedAchieve("COX") ? "<col=24d124>Complete the Chambers of Xeric.</col>" : "<col=911c25>Complete the Chambers of Xeric.");
    }

    @Override
    public int getMaximum(KourendDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }
}
