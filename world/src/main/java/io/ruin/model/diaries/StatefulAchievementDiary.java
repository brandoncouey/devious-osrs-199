package io.ruin.model.diaries;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.Item;

import java.util.*;


public abstract class StatefulAchievementDiary<T extends Enum<T>> extends DifficultyAchievementDiary<T> {

    private final Map<T, Integer> partialAchievements = new HashMap<>();

    protected final List<Item> easyRewards = new LinkedList<>();
    protected final List<Item> mediumRewards = new LinkedList<>();
    protected final List<Item> hardRewards = new LinkedList<>();
    protected final List<Item> eliteRewards = new LinkedList<>();

    protected int spriteId = 3399;

    public StatefulAchievementDiary(String name, Player player) {
        super(name, player);
    }

    public void progress(T achievement) {
        progress(achievement, true);
    }

    public void progress(T achievement, boolean notify) {
        if (hasDone(achievement)) {
            return;
        }

        OptionalInt current = getAchievementStage(achievement);
        if (current.isEmpty()) {
            setAchievementStage(achievement, 1, notify);
            player.DiaryRecorder.put(achievement.name(), 1);
        } else {
            int currentStage = current.getAsInt();
            setAchievementStage(achievement, currentStage + 1, notify);
            player.DiaryRecorder.replace(achievement.name(), currentStage + 1);
        }

    }


    public abstract int getStage(T achievement);

    public final boolean complete(T achievement) {
        boolean success = achievements.add(achievement);
        if (success) {
            uponCompletion(achievement);
            partialAchievements.remove(achievement);
        }
        return success;
    }

    public OptionalInt getAchievementStage(T achievement) {
        Integer result = partialAchievements.get(achievement);
        if (result == null) {
            if (hasDone(achievement)) {
                return OptionalInt.of(getStage(achievement));
            }
            return OptionalInt.empty();
        }
        return OptionalInt.of(result);
    }

    public int getAbsoluteAchievementStage(T achievement) {
        OptionalInt result = getAchievementStage(achievement);
        if (!result.isPresent()) {
            return 0;
        }
        return result.getAsInt();
    }

    public void setAchievementStage(T achievement, int stage, boolean notify) {
        int maximum = getStage(achievement);

        if (maximum == -1 || maximum == 0) {
            if (notify) {
                complete(achievement);
            } else {
                nonNotifyComplete(achievement);
            }
            return;
        }

        int wantedStage = stage;
        if (wantedStage >= maximum) {
            if (notify) {
                complete(achievement);
            } else {
                nonNotifyComplete(achievement);
            }
            return;
        }
        partialAchievements.put(achievement, wantedStage);
    }


    static {
        InterfaceHandler.register(1041, h -> {
            h.actions[17] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                //Claim Rewards
                switch (player.currentAchievementViewing) {
                    case 1:
                        player.getDiaryManager().getDeviousDiary().claimRewards();
                        break;
                    case 4:
                        player.getDiaryManager().getMinigamesDiary().claimRewards();
                        break;
                    case 2:
                        player.getDiaryManager().getPvmDiary().claimRewards();
                        break;
                    case 3:
                        player.getDiaryManager().getPvpDiary().claimRewards();
                        break;
                    case 5:
                        player.getDiaryManager().getSkillingDiary().claimRewards();
                        break;
                    case 0:
                        player.getDiaryManager().getWildernessDiary().claimRewards();
                        break;
                }
            };
            h.actions[20] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                switch (player.currentAchievementViewing) {
                    case 1:
                        player.getDiaryManager().getDeviousDiary().display("EASY");
                        break;
                    case 4:
                        player.getDiaryManager().getMinigamesDiary().display("EASY");
                        break;
                    case 2:
                        player.getDiaryManager().getPvmDiary().display("EASY");
                        break;
                    case 3:
                        player.getDiaryManager().getPvpDiary().display("EASY");
                        break;
                    case 5:
                        player.getDiaryManager().getSkillingDiary().display("EASY");
                        break;
                    case 0:
                        player.getDiaryManager().getWildernessDiary().display("EASY");
                        break;
                }
            };
            h.actions[33] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                switch (player.currentAchievementViewing) {
                    case 1:
                        player.getDiaryManager().getDeviousDiary().display("MEDIUM");
                        break;
                    case 4:
                        player.getDiaryManager().getMinigamesDiary().display("MEDIUM");
                        break;
                    case 2:
                        player.getDiaryManager().getPvmDiary().display("MEDIUM");
                        break;
                    case 3:
                        player.getDiaryManager().getPvpDiary().display("MEDIUM");
                        break;
                    case 5:
                        player.getDiaryManager().getSkillingDiary().display("MEDIUM");
                        break;
                    case 0:
                        player.getDiaryManager().getWildernessDiary().display("MEDIUM");
                        break;
                }
            };
            h.actions[46] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                switch (player.currentAchievementViewing) {
                    case 1:
                        player.getDiaryManager().getDeviousDiary().display("HARD");
                        break;
                    case 4:
                        player.getDiaryManager().getMinigamesDiary().display("HARD");
                        break;
                    case 2:
                        player.getDiaryManager().getPvmDiary().display("HARD");
                        break;
                    case 3:
                        player.getDiaryManager().getPvpDiary().display("HARD");
                        break;
                    case 5:
                        player.getDiaryManager().getSkillingDiary().display("HARD");
                        break;
                    case 0:
                        player.getDiaryManager().getWildernessDiary().display("HARD");
                        break;
                }
            };
            h.actions[59] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                switch (player.currentAchievementViewing) {
                    case 1:
                        player.getDiaryManager().getDeviousDiary().display("ELITE");
                        break;
                    case 4:
                        player.getDiaryManager().getMinigamesDiary().display("ELITE");
                        break;
                    case 2:
                        player.getDiaryManager().getPvmDiary().display("ELITE");
                        break;
                    case 3:
                        player.getDiaryManager().getPvpDiary().display("ELITE");
                        break;
                    case 5:
                        player.getDiaryManager().getSkillingDiary().display("ELITE");
                        break;
                    case 0:
                        player.getDiaryManager().getWildernessDiary().display("ELITE");
                        break;
                }
            };
            h.actions[72] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                switch (player.currentAchievementViewing) {
                    case 1:
                        player.getDiaryManager().getDeviousDiary().display("GRANDMASTER");
                        break;
                    case 4:
                        player.getDiaryManager().getMinigamesDiary().display("GRANDMASTER");
                        break;
                    case 2:
                        player.getDiaryManager().getPvmDiary().display("GRANDMASTER");
                        break;
                    case 3:
                        player.getDiaryManager().getPvpDiary().display("GRANDMASTER");
                        break;
                    case 5:
                        player.getDiaryManager().getSkillingDiary().display("GRANDMASTER");
                        break;
                    case 0:
                        player.getDiaryManager().getWildernessDiary().display("GRANDMASTER");
                        break;
                }
            };
        });
    }
}
