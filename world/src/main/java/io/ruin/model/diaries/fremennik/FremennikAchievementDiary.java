package io.ruin.model.diaries.fremennik;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;

import java.util.EnumSet;
import java.util.Set;

import static io.ruin.model.diaries.fremennik.FremennikDiaryEntry.*;


public final class FremennikAchievementDiary extends StatefulAchievementDiary<FremennikDiaryEntry> {

    public static final Set<FremennikDiaryEntry> EASY_TASKS = EnumSet.of(KILL_ROCK_CRAB, FFILL_BUCKET, KILL_BRINE_RAT, KILL_MARKET_GUARD, TRAVEL_NEITIZNOT);

    public static final Set<FremennikDiaryEntry> MEDIUM_TASKS = EnumSet.of(MINE_COAL_FREM, STEAL_FISH, CHOP_MAHOGANY);

    public static final Set<FremennikDiaryEntry> HARD_TASKS = EnumSet.of(TROLLHEIM_TELEPORT, RELLEKKA_ROOFTOP, ADDY_ORE, WATERBIRTH_TELEPORT, DAGANNOTH_KINGS);

    public static final Set<FremennikDiaryEntry> ELITE_TASKS = EnumSet.of(KILL_BANDOS, KILL_ARMADYL, KILL_ZAMORAK, KILL_SARADOMIN);

    public static final String NAME = "Fremennik area";

    public FremennikAchievementDiary(Player player) {
        super(NAME, player);
    }

    public boolean hasCompleted(String difficulty) {
        switch (difficulty) {
            case "EASY":
                if (achievements.containsAll(EASY_TASKS)) {
                    Config.FREMMY_EASY_COMPLETED.set(player, 1);
                    return false;
                }
            case "MEDIUM":
                if (achievements.containsAll(MEDIUM_TASKS)) {
                    Config.FREMMY_MEDIUM_COMPLETED.set(player, 1);
                    return false;
                }
            case "HARD":
                if (achievements.containsAll(HARD_TASKS)) {
                    Config.FREMMY_HARD_COMPLETED.set(player, 1);
                    return false;
                }
            case "ELITE":
                if (achievements.containsAll(ELITE_TASKS)) {
                    Config.FREMMY_ELITE_COMPLETED.set(player, 1);
                    return true;
                }
        }
        return achievements.containsAll(EASY_TASKS);
    }

    int REWARD = 13129;

    public void claimReward(NPC npc) {
        //EASY
        if (!hasDone(EntryDifficulty.EASY)) {
            player.dialogue(new NPCDialogue(npc, "Come back when you've completed the easy tasks of this area."));
            return;
        }

        if (!player.DiaryRewards.contains("FREMMY_EASY")) {
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 1 reward."));
            addReward(REWARD, npc);
            claim("FREMMY_EASY", player);
            return;
        }

        if (!player.DiaryRewards.contains("FREMMY_MEDIUM") && hasDone(EntryDifficulty.MEDIUM)) {
            if (!player.getInventory().contains(REWARD) && !hasClaimed("FREMMY_HARD", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 1 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 2 reward."));
            player.getInventory().remove(REWARD, 1);
            addReward(REWARD + 1, npc);
            claim("FREMMY_MEDIUM", player);
            return;
        }

        if (!player.DiaryRewards.contains("FREMMY_HARD") && hasDone(EntryDifficulty.HARD)) {
            if (!player.getInventory().contains(REWARD + 1) && !hasClaimed("FREMMY_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 2 reward and I will upgrade it for you!"));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 3 reward."));
            player.getInventory().remove(REWARD + 1, 1);
            addReward(REWARD + 2, npc);
            claim("FREMMY_HARD", player);
            return;
        }

        if (!player.DiaryRewards.contains("FREMMY_ELITE") && hasDone(EntryDifficulty.ELITE)) {
            if (!player.getInventory().contains(REWARD + 2) && !hasClaimed("FREMMY_ELITE", player)) {
                player.dialogue(new NPCDialogue(npc, "Bring me the tier 3 reward and I will upgrade it for you!"));
                return;
            }

            player.dialogue(new NPCDialogue(npc, "Nice job, here have the tier 4 reward."));
            player.getInventory().remove(REWARD + 2, 1);
            addReward(REWARD + 3, npc);
            claim("FREMMY_ELITE", player);
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
    public Set<FremennikDiaryEntry> getEasy() {
        return EASY_TASKS;
    }

    @Override
    public Set<FremennikDiaryEntry> getMedium() {
        return MEDIUM_TASKS;
    }

    @Override
    public Set<FremennikDiaryEntry> getHard() {
        return HARD_TASKS;
    }

    @Override
    public Set<FremennikDiaryEntry> getElite() {
        return ELITE_TASKS;
    }

    public boolean hasCompletedAchieve(String difficulty) {
        for (FremennikDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(FremennikDiaryEntry.valueOf(s));
                }
            });
        }
        switch (difficulty) {
            //EASY
            case "KILL_ROCK_CRAB":
                return achievements.contains(KILL_ROCK_CRAB);
            case "FFILL_BUCKET":
                return achievements.contains(FFILL_BUCKET);
            case "KILL_BRINE_RAT":
                return achievements.contains(KILL_BRINE_RAT);
            case "KILL_MARKET_GUARD":
                return achievements.contains(KILL_MARKET_GUARD);
            case "TRAVEL_NEITIZNOT":
                return achievements.contains(TRAVEL_NEITIZNOT);
            //MEDIUM
            case "MINE_COAL_FREM":
                return achievements.contains(MINE_COAL_FREM);
            case "STEAL_FISH":
                return achievements.contains(STEAL_FISH);
            case "CHOP_MAHOGANY":
                return achievements.contains(CHOP_MAHOGANY);
            case "TRAVEL_MISCELLANIA":
                return achievements.contains(TRAVEL_MISCELLANIA);
            //HARD
            case "TROLLHEIM_TELEPORT":
                return achievements.contains(TROLLHEIM_TELEPORT);
            case "RELLEKKA_ROOFTOP":
                return achievements.contains(RELLEKKA_ROOFTOP);
            case "ADDY_ORE":
                return achievements.contains(ADDY_ORE);
            case "WATERBIRTH_TELEPORT":
                return achievements.contains(WATERBIRTH_TELEPORT);
            case "DAGANNOTH_KINGS":
                return achievements.contains(DAGANNOTH_KINGS);
            //ELITE
            case "KILL_BANDOS":
                return achievements.contains(KILL_BANDOS);
            case "KILL_ARMADYL":
                return achievements.contains(KILL_ARMADYL);
            case "KILL_ZAMORAK":
                return achievements.contains(KILL_ZAMORAK);
            case "KILL_SARADOMIN":
                return achievements.contains(KILL_SARADOMIN);
        }
        return achievements.containsAll(EASY_TASKS);
    }

    public final void display() {
        player.sendScroll("<col=8B0000>Fremennik Diary",
                "<col=501061><strong><u>Easy",
                hasCompletedAchieve("KILL_ROCK_CRAB") ? "<col=24d124>Kill Rock Crabs. (" + (getAbsoluteAchievementStage(KILL_ROCK_CRAB)) + "/" + getMaximum(KILL_ROCK_CRAB) + ")</col>" : "<col=911c25>Kill Rock Crabs. (" + (getAbsoluteAchievementStage(KILL_ROCK_CRAB)) + "/" + getMaximum(KILL_ROCK_CRAB) + ")",
                hasCompletedAchieve("FFILL_BUCKET") ? "<col=24d124>Fill 1 Bucket at the Rellekka well. </col>" : "<col=911c25>Fill Bucket at the Rellekka well.",
                hasCompletedAchieve("KILL_BRINE_RAT") ? "<col=24d124>Kill Brine Rats. (" + (getAbsoluteAchievementStage(KILL_BRINE_RAT)) + "/" + getMaximum(KILL_BRINE_RAT) + ")</col>" : "<col=911c25>Kill Brine Rats. (" + (getAbsoluteAchievementStage(KILL_BRINE_RAT)) + "/" + getMaximum(KILL_BRINE_RAT) + ")",
                hasCompletedAchieve("KILL_MARKET_GUARD") ? "<col=24d124>Enter the Fremennik Slayer Dungeon.</col>" : "<col=911c25>Enter the Fremennik Slayer Dungeon.",
                hasCompletedAchieve("TRAVEL_NEITIZNOT") ? "<col=24d124>Travel by boat to Neitiznot.</col>" : "<col=911c25>Travel by boat to Neitiznot.",
                "",
                "<col=501061><strong><u>Medium",
                hasCompletedAchieve("MINE_COAL_FREM") ? "<col=24d124>Mine Coal in Rellekka. (" + (getAbsoluteAchievementStage(MINE_COAL_FREM)) + "/" + getMaximum(MINE_COAL_FREM) + ")</col>" : "<col=911c25>Mine Coal in Rellekka. (" + (getAbsoluteAchievementStage(MINE_COAL_FREM)) + "/" + getMaximum(MINE_COAL_FREM) + ")",
                hasCompletedAchieve("STEAL_FISH") ? "<col=24d124>Steal from the Fish Stall. (" + (getAbsoluteAchievementStage(STEAL_FISH)) + "/" + getMaximum(STEAL_FISH) + ")</col>" : "<col=911c25>Steal from the Fish Stall. (" + (getAbsoluteAchievementStage(STEAL_FISH)) + "/" + getMaximum(STEAL_FISH) + ")",
                hasCompletedAchieve("CHOP_MAHOGANY") ? "<col=24d124>Chop Mahogany Logs in Miscellania. (" + (getAbsoluteAchievementStage(CHOP_MAHOGANY)) + "/" + getMaximum(CHOP_MAHOGANY) + ")</col>" : "<col=911c25>Chop Mahogany Logs in Miscellania. (" + (getAbsoluteAchievementStage(CHOP_MAHOGANY)) + "/" + getMaximum(CHOP_MAHOGANY) + ")",
                hasCompletedAchieve("TRAVEL_MISCELLANIA") ? "<col=24d124>Travel by Fairy Ring to Miscellania.</col>" : "<col=911c25>Travel by Fairy Ring to Miscellania.",
                "",
                "<col=501061><strong><u>Hard",
                hasCompletedAchieve("TROLLHEIM_TELEPORT") ? "<col=24d124>Teleport to Trollheim.</col>" : "<col=911c25>Teleport to Trollheim.",
                hasCompletedAchieve("RELLEKKA_ROOFTOP") ? "<col=24d124>Complete the Rellekka Rooftop Agility Course. (" + (getAbsoluteAchievementStage(RELLEKKA_ROOFTOP)) + "/" + getMaximum(RELLEKKA_ROOFTOP) + ")</col>" : "<col=911c25>Complete the Rellekka Rooftop Agility Course. (" + (getAbsoluteAchievementStage(RELLEKKA_ROOFTOP)) + "/" + getMaximum(RELLEKKA_ROOFTOP) + ")",
                hasCompletedAchieve("ADDY_ORE") ? "<col=24d124>Mine Adamant Ores in the Jatizso Mine. (" + (getAbsoluteAchievementStage(ADDY_ORE)) + "/" + getMaximum(ADDY_ORE) + ")</col>" : "<col=911c25>Mine Adamant Ores in the Jatizo Mine. (" + (getAbsoluteAchievementStage(ADDY_ORE)) + "/" + getMaximum(ADDY_ORE) + ")",
                hasCompletedAchieve("WATERBIRTH_TELEPORT") ? "<col=24d124>Teleport to Waterbirth Island.</col>" : "<col=911c25>Teleport to Waterbirth Island.",
                hasCompletedAchieve("DAGANNOTH_KINGS") ? "<col=24d124>Kill Dagannoth Kings. (" + (getAbsoluteAchievementStage(DAGANNOTH_KINGS)) + "/" + getMaximum(DAGANNOTH_KINGS) + ")</col>" : "<col=911c25>Kill Dagannoth Kings. (" + (getAbsoluteAchievementStage(DAGANNOTH_KINGS)) + "/" + getMaximum(DAGANNOTH_KINGS) + ")",
                "",
                "<col=501061><strong><u>Elite",
                hasCompletedAchieve("KILL_BANDOS") ? "<col=24d124>Kill General Graardor. (" + (getAbsoluteAchievementStage(KILL_BANDOS)) + "/" + getMaximum(KILL_BANDOS) + ")</col>" : "<col=911c25>Kill General Graardor. (" + (getAbsoluteAchievementStage(KILL_BANDOS)) + "/" + getMaximum(KILL_BANDOS) + ")",
                hasCompletedAchieve("KILL_ARMADYL") ? "<col=24d124>Kill Kree'arra. (" + (getAbsoluteAchievementStage(KILL_ARMADYL)) + "/" + getMaximum(KILL_ARMADYL) + ")</col>" : "<col=911c25>Kill Kree'arra. (" + (getAbsoluteAchievementStage(KILL_ARMADYL)) + "/" + getMaximum(KILL_ARMADYL) + ")",
                hasCompletedAchieve("KILL_ZAMORAK") ? "<col=24d124>Kill Kr'ill Tsutaroth. (" + (getAbsoluteAchievementStage(KILL_ZAMORAK)) + "/" + getMaximum(KILL_ZAMORAK) + ")</col>" : "<col=911c25>Kill K'rill Tsutaroth. (" + (getAbsoluteAchievementStage(KILL_ZAMORAK)) + "/" + getMaximum(KILL_ZAMORAK) + ")",
                hasCompletedAchieve("KILL_SARADOMIN") ? "<col=24d124>Kill Commander Zilyana. (" + (getAbsoluteAchievementStage(KILL_SARADOMIN)) + "/" + getMaximum(KILL_SARADOMIN) + ")</col>" : "<col=911c25>Kill Commander Zilyana. (" + (getAbsoluteAchievementStage(KILL_SARADOMIN)) + "/" + getMaximum(KILL_SARADOMIN) + ")");
    }

    @Override
    public int getMaximum(FremennikDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

}