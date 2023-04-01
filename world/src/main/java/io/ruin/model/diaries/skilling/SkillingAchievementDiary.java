package io.ruin.model.diaries.skilling;

import io.ruin.model.diaries.StatefulAchievementDiary;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.utility.Misc;
import io.ruin.utility.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.ruin.model.diaries.skilling.SkillingDiaryEntry.*;


public final class SkillingAchievementDiary extends StatefulAchievementDiary<SkillingDiaryEntry> {

    public static final Set<SkillingDiaryEntry> EASY_TASKS = EnumSet.of(KILL_DUCK, WESTERN_WALL, FILL_BUCKET, REPAIR_STRUT, TRAVEL_ENTRANA, MIND_TIARA, FARMING_SHOP);

    public static final Set<SkillingDiaryEntry> MEDIUM_TASKS = EnumSet.of(CRYSTAL_CHEST, PICKPOCKET_GUARD, GOLD_ORE, NARROW_CREVICE, ALTAR_OF_GUTHIX, TELEPORT_FALADOR);

    public static final Set<SkillingDiaryEntry> HARD_TASKS = EnumSet.of(KILL_WYVERN, FALADOR_ROOFTOP, KILL_GIANT_MOLE, WARRIOR_GUILD);

    public static final Set<SkillingDiaryEntry> ELITE_TASKS = EnumSet.of(STRANGE_FLOOR, KILL_BLACK_DRAGON, WEAR_PROSPECTOR);

    public static final String NAME = "Skilling";

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

    public boolean hasCompletedAchieve(String achievementName) {
        for (SkillingDiaryEntry value : values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(SkillingDiaryEntry.valueOf(s));
                }
            });
        }
        Optional<SkillingDiaryEntry> achievement = fromName(achievementName);
        if (achievement.isEmpty()) return false;
        return achievements.contains(achievement.get());
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

    public void addReward(int reward, NPC npc) {
        player.getInventory().add(reward, 1);
        player.dialogue(new NPCDialogue(npc, "Here you go, upgraded and ready to be used!"));
    }

    public void upgradeReward(int reward, int upgrade, NPC npc) {
        player.getInventory().remove(reward, 1);
        player.getInventory().add(upgrade, 1);
        player.dialogue(new NPCDialogue(npc, "Here you go, upgraded and ready!"));
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

    public int getEasyAmountCompleted() {
        AtomicInteger amount = new AtomicInteger();
        EASY_TASKS.forEach(e -> {
            if (hasCompletedAchieve(e.name()))
                amount.getAndIncrement();
        });
        return amount.get();
    }
    public int getMediumAmountCompleted() {
        AtomicInteger amount = new AtomicInteger();
        MEDIUM_TASKS.forEach(e -> {
            if (hasCompletedAchieve(e.name()))
                amount.getAndIncrement();
        });
        return amount.get();
    }
    public int getHardAmountCompleted() {
        AtomicInteger amount = new AtomicInteger();
        HARD_TASKS.forEach(e -> {
            if (hasCompletedAchieve(e.name()))
                amount.getAndIncrement();
        });
        return amount.get();
    }
    public int getEliteAmountCompleted() {
        AtomicInteger amount = new AtomicInteger();
        ELITE_TASKS.forEach(e -> {
            if (hasCompletedAchieve(e.name()))
                amount.getAndIncrement();
        });
        return amount.get();
    }

    public final void display(String difficulty) {
        player.currentAchievementDifficultyViewing = difficulty;
        player.getPacketSender().sendString(1041, 14, "Skilling Achievement Diary");
        for (int i = 0; i < 32; i++) {
            player.getPacketSender().setHidden(1041, 74 + (i * 5), true);
        }
        AtomicInteger index = new AtomicInteger();

        Set<SkillingDiaryEntry> TASKS = EASY_TASKS;
        List<Item> rewards = easyRewards;

        switch (difficulty) {
            case "MEDIUM":
                TASKS = MEDIUM_TASKS;
                rewards = mediumRewards;
                spriteId = 3400;
                break;
            case "HARD":
                TASKS = HARD_TASKS;
                rewards = hardRewards;
                spriteId = 3401;
                break;
            case "ELITE":
                TASKS = ELITE_TASKS;
                rewards = eliteRewards;
                spriteId = 3402;
                break;
        }

        int finalSpriteId = spriteId;
        TASKS.forEach(e -> {
            if (!hasCompletedAchieve(e.name())) {
                player.getPacketSender().setSprite(1041, 76 + (index.get() * 5), finalSpriteId);
                player.getPacketSender().sendString(1041, 77 + (index.get() * 5), Misc.formatStringFormal(e.getName()));
                player.getPacketSender().sendString(1041, 78 + (index.get() * 5), e.getDescription().replace("%progress%", "<col=deb31f>" + Utils.formatMoneyString(player.getDiaryManager().getSkillingDiary().getAchievementStage(e).isPresent() ? player.getDiaryManager().getSkillingDiary().getAchievementStage(e).getAsInt() : 0)).replace("%amount%", Utils.formatMoneyString(e.getMaximumStages()) + "</col>"));
                player.getPacketSender().setHidden(1041, 74 + (index.get() * 5), false);
                index.getAndIncrement();
            }
        });
        TASKS.forEach(e -> {
            if (hasCompletedAchieve(e.name())) {
                player.getPacketSender().setSprite(1041, 76 + (index.get() * 5), finalSpriteId);
                player.getPacketSender().sendString(1041, 77 + (index.get() * 5), "<col=404040><str>" + Misc.formatStringFormal(e.name()));
                player.getPacketSender().sendString(1041, 78 + (index.get() * 5), e.getDescription().replace("%progress%", "<col=deb31f>" + Utils.formatMoneyString(player.getDiaryManager().getSkillingDiary().getAchievementStage(e).isPresent() ? player.getDiaryManager().getSkillingDiary().getAchievementStage(e).getAsInt() : 0)).replace("%amount%", Utils.formatMoneyString(e.getMaximumStages()) + "</col>"));
                player.getPacketSender().setHidden(1041, 74 + (index.get() * 5), false);
                index.getAndIncrement();
            }
        });
        player.getPacketSender().sendString(1041, 24, getEasyAmountCompleted() + "/" + EASY_TASKS.size());
        player.getPacketSender().sendString(1041, 37, getMediumAmountCompleted() + "/" + MEDIUM_TASKS.size());
        player.getPacketSender().sendString(1041, 50, getHardAmountCompleted() + "/" + HARD_TASKS.size());
        player.getPacketSender().sendString(1041, 63, getEliteAmountCompleted() + "/" + ELITE_TASKS.size());
        int count = 0;
        for (Item reward : rewards) {
            player.getPacketSender().sendItems(1041, (248 + count), 0, reward);
            count++;
        }
        player.openInterface(InterfaceType.MAIN, 1041);
    }

    @Override
    public int getStage(SkillingDiaryEntry achievement) {
        return achievement.getMaximumStages();
    }

    public void claimRewards() {
        if (!hasCompleted(player.currentAchievementDifficultyViewing)) {
            player.sendMessage("You must complete all of the " + Misc.formatStringFormal(player.currentAchievementDifficultyViewing)  + " " + NAME + " achievement diaries to claim your rewards.");
            return;
        }
        switch (player.currentAchievementDifficultyViewing) {
            case "EASY":
                for (Item item : easyRewards) {
                    if (new HashSet<>(player.claimedAchievementRewards).containsAll(easyRewards)) {
                        player.sendMessage("You've already claimed all of your rewards.");
                        return;
                    }
                    if (!player.claimedAchievementRewards.contains(item)) {
                        if (player.getInventory().getFreeSlots() > 0) {
                            player.getInventory().add(item);
                            player.claimedAchievementRewards.add(item);
                        } else {
                            player.sendMessage("You do not have enough inventory space.");
                            return;
                        }
                    }
                }
                break;
            case "MEDIUM":
                if (new HashSet<>(player.claimedAchievementRewards).containsAll(mediumRewards)) {
                    player.sendMessage("You've already claimed all of your rewards.");
                    return;
                }
                for (Item item : mediumRewards) {
                    if (!player.claimedAchievementRewards.contains(item)) {
                        if (player.getInventory().getFreeSlots() > 0) {
                            player.getInventory().add(item);
                            player.claimedAchievementRewards.add(item);
                        } else {
                            player.sendMessage("You do not have enough inventory space.");
                            return;
                        }
                    }
                }
                break;
            case "HARD":
                if (new HashSet<>(player.claimedAchievementRewards).containsAll(hardRewards)) {
                    player.sendMessage("You've already claimed all of your rewards.");
                    return;
                }
                for (Item item : hardRewards) {
                    if (!player.claimedAchievementRewards.contains(item)) {
                        if (player.getInventory().getFreeSlots() > 0) {
                            player.getInventory().add(item);
                            player.claimedAchievementRewards.add(item);
                        } else {
                            player.sendMessage("You do not have enough inventory space.");
                            return;
                        }
                    }
                }
                break;
            case "ELITE":
                if (new HashSet<>(player.claimedAchievementRewards).containsAll(eliteRewards)) {
                    player.sendMessage("You've already claimed all of your rewards.");
                    return;
                }
                for (Item item : eliteRewards) {
                    if (!player.claimedAchievementRewards.contains(item)) {
                        if (player.getInventory().getFreeSlots() > 0) {
                            player.getInventory().add(item);
                            player.claimedAchievementRewards.add(item);
                        } else {
                            player.sendMessage("You do not have enough inventory space.");
                            return;
                        }
                    }
                }
                break;
        }
    }
}