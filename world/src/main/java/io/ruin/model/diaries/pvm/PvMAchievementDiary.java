package io.ruin.model.diaries.pvm;

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

import static io.ruin.model.diaries.pvm.PvMDiaryEntry.*;


public final class PvMAchievementDiary extends StatefulAchievementDiary<PvMDiaryEntry> {

    public static final Set<PvMDiaryEntry> EASY_TASKS = EnumSet.of(MINE_GOLD, TELEPORT_TO_KARAMJA, SAIL_TO_ARDOUGNE, SAIL_TO_PORT_SARIM);

    public static final Set<PvMDiaryEntry> MEDIUM_TASKS = EnumSet.of(DURADEL, CHOP_VINE, TRAVEL_PORT_KHAZARD);

    public static final Set<PvMDiaryEntry> HARD_TASKS = EnumSet.of(KILL_METAL_DRAGON);

    public static final Set<PvMDiaryEntry> ELITE_TASKS = EnumSet.of(CRAFT_NATURES, ANTI_VENOM);

    public static final String NAME = "Player vs Monster";

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

    public boolean hasCompletedAchieve(String achievementName) {
        for (PvMDiaryEntry value : PvMDiaryEntry.values()) {
            player.DiaryRecorder.forEach((s, integer) -> {
                if (s.equalsIgnoreCase(value.toString()) && integer == 1000) {
                    achievements.add(PvMDiaryEntry.valueOf(s));
                }
            });
        }
        Optional<PvMDiaryEntry> achievement = PvMDiaryEntry.fromName(achievementName);
        if (achievement.isEmpty()) return false;
        return achievements.contains(achievement.get());
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
        player.getPacketSender().sendString(1041, 14, "PvM Achievement Diary");
        for (int i = 0; i < 32; i++) {
            player.getPacketSender().setHidden(1041, 74 + (i * 5), true);
        }
        AtomicInteger index = new AtomicInteger();

        Set<PvMDiaryEntry> TASKS = EASY_TASKS;
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
                player.getPacketSender().sendString(1041, 78 + (index.get() * 5), e.getDescription().replace("%progress%", "<col=deb31f>" + Utils.formatMoneyString(player.getDiaryManager().getPvmDiary().getAchievementStage(e).isPresent() ? player.getDiaryManager().getPvmDiary().getAchievementStage(e).getAsInt() : 0)).replace("%amount%", Utils.formatMoneyString(e.getMaximumStages()) + "</col>"));
                player.getPacketSender().setHidden(1041, 74 + (index.get() * 5), false);
                index.getAndIncrement();
            }
        });
        TASKS.forEach(e -> {
            if (hasCompletedAchieve(e.name())) {
                player.getPacketSender().setSprite(1041, 76 + (index.get() * 5), finalSpriteId);
                player.getPacketSender().sendString(1041, 77 + (index.get() * 5), "<col=404040><str>" + Misc.formatStringFormal(e.name()));
                player.getPacketSender().sendString(1041, 78 + (index.get() * 5), e.getDescription().replace("%progress%", "<col=deb31f>" + Utils.formatMoneyString(player.getDiaryManager().getPvmDiary().getAchievementStage(e).isPresent() ? player.getDiaryManager().getPvmDiary().getAchievementStage(e).getAsInt() : 0)).replace("%amount%", Utils.formatMoneyString(e.getMaximumStages()) + "</col>"));
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
    public int getStage(PvMDiaryEntry achievement) {
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