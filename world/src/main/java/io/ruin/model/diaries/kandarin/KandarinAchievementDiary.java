package io.ruin.model.diaries.kandarin;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.kandarin.KandarinDiaryEntry.*;


public final class KandarinAchievementDiary extends StatefulAchievementDiary<KandarinDiaryEntry> {

    public static final Set<KandarinDiaryEntry> EASY_TASKS = EnumSet.of(PICK_FLAX_SEERS, BUY_CANDLE, CROSS_BALANCE);

    public static final Set<KandarinDiaryEntry> MEDIUM_TASKS = EnumSet.of(BARBARIAN_AGILITY, RANGING_GUILD, CAMELOT_TELEPORT, STRING_MAPLE_SHORT);

    public static final Set<KandarinDiaryEntry> HARD_TASKS = EnumSet.of(SEERS_AGILITY, KILL_MITHRIL_DRAGON_KAN, CUT_MAGIC_SEERS, FLETCH_MAGIC_BOW);

    public static final Set<KandarinDiaryEntry> ELITE_TASKS = EnumSet.of(COOK_SHARKS, CATHERY_TELEPORT);

    public static final String NAME = "Kandarin area";

    public KandarinAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.KANDARIN_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.KANDARIN_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.KANDARIN_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.KANDARIN_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13137;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("KANDARIN_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("KANDARIN_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("KANDARIN_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("KANDARIN_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("KANDARIN_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("KANDARIN_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("KANDARIN_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("KANDARIN_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("KANDARIN_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("KANDARIN_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("KANDARIN_ELITE", player);
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
    public Set<KandarinDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<KandarinDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<KandarinDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<KandarinDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (KandarinDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(KandarinDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "PICK_FLAX_SEERS":
                return achievements.contains(PICK_FLAX_SEERS);
            case "BUY_CANDLE":
                return achievements.contains(BUY_CANDLE);
            case "CROSS_BALANCE":
                return achievements.contains(CROSS_BALANCE);
            //  case "CATCH_MACKAREL":
            // return achievements.contains(CATCH_MACKAREL);
            //MEDIUM
            case "BARBARIAN_AGILITY":
                return achievements.contains(BARBARIAN_AGILITY);
            case "RANGING_GUILD":
                return achievements.contains(RANGING_GUILD);
            case "CAMELOT_TELEPORT":
                return achievements.contains(CAMELOT_TELEPORT);
            case "STRING_MAPLE_SHORT":
                return achievements.contains(STRING_MAPLE_SHORT);
            //  case "FISH_SWORD":
            // return achievements.contains(FISH_SWORD);
            //HARD
            case "SEERS_AGILITY":
                return achievements.contains(SEERS_AGILITY);
            case "KILL_MITHRIL_DRAGON_KAN":
                return achievements.contains(KILL_MITHRIL_DRAGON_KAN);
            case "CUT_MAGIC_SEERS":
                return achievements.contains(CUT_MAGIC_SEERS);
            case "FLETCH_MAGIC_BOW":
                return achievements.contains(FLETCH_MAGIC_BOW);
            //ELITE
            case "COOK_SHARKS":
                return achievements.contains(COOK_SHARKS);
            case "CATHERY_TELEPORT":
                return achievements.contains(CATHERY_TELEPORT);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Kandarin Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("PICK_FLAX_SEERS") ? "<col=24d124>Pick Flax in Seers' Village. (" + (getAbsoluteAchievementStage(PICK_FLAX_SEERS)) + "/" + getMaximum(PICK_FLAX_SEERS) + ")</col>" : "<col=911c25>Pick Flax in Seers' Village. (" + (getAbsoluteAchievementStage(PICK_FLAX_SEERS)) + "/" + getMaximum(PICK_FLAX_SEERS) + ")",
                hasCompletedAchieve("BUY_CANDLE") ? "<col=24d124>Purchase a Candle from the Candle Maker</col>" : "<col=911c25>Purchase a Candle from the Candle Maker",
                hasCompletedAchieve("CROSS_BALANCE") ? "<col=24d124>Cross the river using the Log Balance shortcut by the Coal Trucks.</col>" : "<col=911c25>Cross the river using the Log Balance shortcut by the Coal Trucks.",
                // hasCompletedAchieve("CATCH_MACKAREL") ? "<col=24d124>Catch a Mackerel in Catherby.</col>" : "<col=911c25>Catch a Mackerel in Catherby.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("BARBARIAN_AGILITY") ? "<col=24d124>Complete the Barbarian Agility Course. (" + (getAbsoluteAchievementStage(BARBARIAN_AGILITY)) + "/" + getMaximum(BARBARIAN_AGILITY) + ")</col>" : "<col=911c25>Complete the Barbarian Agility Course. (" + (getAbsoluteAchievementStage(BARBARIAN_AGILITY)) + "/" + getMaximum(BARBARIAN_AGILITY) + ")",
                hasCompletedAchieve("RANGING_GUILD") ? "<col=24d124>Enter the Ranging Guild.</col>" : "<col=911c25>Enter the Ranging Guild.",
                hasCompletedAchieve("CAMELOT_TELEPORT") ? "<col=24d124>Teleport to Camelot.</col>" : "<col=911c25>Teleport to Camelot.",
                hasCompletedAchieve("STRING_MAPLE_SHORT") ? "<col=24d124>String a Maple Shortbow in the Seers' Village bank.</col>" : "<col=911c25>String a Maple Shortbow in the Seers' Village bank.",
                // hasCompletedAchieve("FISH_SWORD") ? "<col=24d124>Fish Swordfish in Catherby. (" + (getAbsoluteAchievementStage(FISH_SWORD)) + "/" + getMaximum(FISH_SWORD) + ")</col>" : "<col=911c25>Fish Swordfish in Catherby. (" + (getAbsoluteAchievementStage(FISH_SWORD)) + "/" + getMaximum(FISH_SWORD) + ")",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("SEERS_AGILITY") ? "<col=24d124>Complete the Seers' Village Rooftop Agility Course. (" + (getAbsoluteAchievementStage(SEERS_AGILITY)) + "/" + getMaximum(SEERS_AGILITY) + ")</col>" : "<col=911c25>Complete the Seers' Village Rooftop Agility Course. (" + (getAbsoluteAchievementStage(SEERS_AGILITY)) + "/" + getMaximum(SEERS_AGILITY) + ")",
                hasCompletedAchieve("KILL_MITHRIL_DRAGON_KAN") ? "<col=24d124>Kill Mithril Dragons. (" + (getAbsoluteAchievementStage(KILL_MITHRIL_DRAGON_KAN)) + "/" + getMaximum(KILL_MITHRIL_DRAGON_KAN) + ")</col>" : "<col=911c25>Kill Mithril Dragons. (" + (getAbsoluteAchievementStage(KILL_MITHRIL_DRAGON_KAN)) + "/" + getMaximum(KILL_MITHRIL_DRAGON_KAN) + ")",
                hasCompletedAchieve("CUT_MAGIC_SEERS") ? "<col=24d124>Cut Magic Logs behind the Ranged Guild. (" + (getAbsoluteAchievementStage(CUT_MAGIC_SEERS)) + "/" + getMaximum(CUT_MAGIC_SEERS) + ")</col>" : "<col=911c25>Cut Magic Logs behind the Ranged Guild. (" + (getAbsoluteAchievementStage(CUT_MAGIC_SEERS)) + "/" + getMaximum(CUT_MAGIC_SEERS) + ")",
                hasCompletedAchieve("FLETCH_MAGIC_BOW") ? "<col=24d124>Fletch a Magic Longbow in Catherby.</col>" : "<col=911c25>Fletch a Magic Longbow in Catherby.",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("COOK_SHARKS") ? "<col=24d124>Cook Sharks in Catherby while wearing Cooking Gauntlets. (" + (getAbsoluteAchievementStage(COOK_SHARKS)) + "/" + getMaximum(COOK_SHARKS) + ")</col>" : "<col=911c25>Cook Sharks in Catherby while wearing Cooking Gauntlets. (" + (getAbsoluteAchievementStage(COOK_SHARKS)) + "/" + getMaximum(COOK_SHARKS) + ")",
                hasCompletedAchieve("CATHERY_TELEPORT") ? "<col=24d124>Teleport to Catherby.</col>" : "<col=911c25>Teleport to Catherby.");
    }

    @Override
    public int getMaximum(KandarinDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}