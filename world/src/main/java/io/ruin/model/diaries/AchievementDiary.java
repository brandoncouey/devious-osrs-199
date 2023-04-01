package io.ruin.model.diaries;

import io.ruin.model.diaries.pvm.PvMAchievementDiary;
import io.ruin.model.diaries.pvp.PvPAchievementDiary;
import io.ruin.model.diaries.pvp.PvPDiaryEntry;
import io.ruin.model.diaries.minigames.MinigamesAchievementDiary;
import io.ruin.model.diaries.minigames.MinigamesDiaryEntry;
import io.ruin.model.diaries.skilling.SkillingAchievementDiary;
import io.ruin.model.diaries.skilling.SkillingDiaryEntry;
import io.ruin.model.diaries.pvm.PvMDiaryEntry;
import io.ruin.model.diaries.devious.DeviousAchievementDiary;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.diaries.wilderness.WildernessAchievementDiary;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;


/**
 * Represents an Achievement diary implementation
 *
 * @param <T> Enumeration representing the various achievments a {@link Player}
 *            can get
 * @author Kaleem
 * @version 1.1
 */
public abstract class AchievementDiary<T extends Enum<T>> {

    static {
        LoginListener.register(p -> p.DiaryRecorder.forEach((s, integer) -> {
            for (DeviousDiaryEntry value : DeviousDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getDeviousDiary().setAchievementStage(value, integer, false);
                }
            }
            for (PvPDiaryEntry value : PvPDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getPvpDiary().setAchievementStage(value, integer, false);
                }
            }
            for (MinigamesDiaryEntry value : MinigamesDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getMinigamesDiary().setAchievementStage(value, integer, false);
                }
            }
            for (SkillingDiaryEntry value : SkillingDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getSkillingDiary().setAchievementStage(value, integer, false);
                }
            }
            for (PvMDiaryEntry value : PvMDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getPvmDiary().setAchievementStage(value, integer, false);
                }
            }
            for (WildernessDiaryEntry value : WildernessDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getWildernessDiary().setAchievementStage(value, integer, false);
                }
            }
        }));
    }

    /**
     * The name of this {@link AchievementDiary}
     */
    private final String name;

    /**
     * The {@link Player} object the {@link AchievementDiary} is responsible for
     */
    public Player player;

    /**
     * A {@link HashSet} representing the various {@link T} achievements the
     * {@link #player} has
     */
    public Set<T> achievements = new HashSet<>();

    /**
     * Creates a new {@link AchievementDiary} implementation (should be effectively immutable)
     *
     * @param name   The name of this {@link AchievementDiary}
     * @param player
     */
    public AchievementDiary(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public boolean complete(T achievement) {
        boolean success = achievements.add(achievement);
        if (success)
            uponCompletion(achievement);
        return success;
    }

    public final void nonNotifyComplete(T achievement) {
        achievements.add(achievement);
    }

    public final boolean remove(T achievement) {
        return achievements.remove(achievement);
    }

    public void uponCompletion(T achievement) {
        player.openInterface(InterfaceType.UNUSED_OVERLAY2, 660);
        player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Achievement Diary", "Well done! You have completed a task in " + getName() + "!");
        player.sendMessage("<col=8B0000>Well done! You have completed a task in the " + getName() + ". Your Achievement");
        player.sendMessage("<col=8B0000>Diary has been updated.");

        //Devious
        int deviousEasyAmount = player.getDiaryManager().getDeviousDiary().getEasyAmountCompleted();
        int deviousMediumAmount = player.getDiaryManager().getDeviousDiary().getMediumAmountCompleted();
        int deviousHardAmount = player.getDiaryManager().getDeviousDiary().getHardAmountCompleted();
        int deviousEliteAmount = player.getDiaryManager().getDeviousDiary().getEliteAmountCompleted();
        Config.DEVIOUS_EASY.set(player, deviousEasyAmount);
        Config.DEVIOUS_MEDIUM.set(player, deviousMediumAmount);
        Config.DEVIOUS_HARD.set(player, deviousHardAmount);
        Config.DEVIOUS_ELITE.set(player, deviousEliteAmount);
        Config.DEVIOUS_EASY_COMPLETED.set(player, deviousEasyAmount == DeviousAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.DEVIOUS_MEDIUM_COMPLETED.set(player, deviousMediumAmount == DeviousAchievementDiary.MEDIUM_TASKS.size() ? 1 : 0);
        Config.DEVIOUS_HARD_COMPLETED.set(player, deviousHardAmount == DeviousAchievementDiary.HARD_TASKS.size() ? 1 : 0);
        Config.DEVIOUS_ELITE_COMPLETED.set(player, deviousEliteAmount == DeviousAchievementDiary.ELITE_TASKS.size() ? 1 : 0);

        //PvM
        int pvmEasyAmount = player.getDiaryManager().getPvmDiary().getEasyAmountCompleted();
        int pvmMediumAmount = player.getDiaryManager().getPvmDiary().getMediumAmountCompleted();
        int pvmHardAmount = player.getDiaryManager().getPvmDiary().getHardAmountCompleted();
        int pvmEliteAmount = player.getDiaryManager().getPvmDiary().getEliteAmountCompleted();
        Config.SKILLING_EASY.set(player, pvmEasyAmount);
        Config.SKILLING_MEDIUM.set(player, pvmMediumAmount);
        Config.SKILLING_HARD.set(player, pvmHardAmount);
        Config.SKILLING_ELITE.set(player, pvmEliteAmount);
        Config.PVM_EASY_COMPLETED.set(player, pvmEasyAmount == PvMAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.PVM_MEDIUM_COMPLETED.set(player, pvmMediumAmount == PvMAchievementDiary.MEDIUM_TASKS.size() ? 1 : 0);
        Config.PVM_HARD_COMPLETED.set(player, pvmHardAmount == PvMAchievementDiary.HARD_TASKS.size() ? 1 : 0);
        Config.PVM_ELITE_COMPLETED.set(player, pvmEliteAmount == PvMAchievementDiary.ELITE_TASKS.size() ? 1 : 0);

        //Minigames
        int minigamesEasyAmount = player.getDiaryManager().getMinigamesDiary().getEasyAmountCompleted();
        int minigamesMediumAmount = player.getDiaryManager().getMinigamesDiary().getMediumAmountCompleted();
        int minigamesHardAmount = player.getDiaryManager().getMinigamesDiary().getHardAmountCompleted();
        int minigamesEliteAmount = player.getDiaryManager().getMinigamesDiary().getEliteAmountCompleted();
        Config.PVM_EASY.set(player, minigamesEasyAmount);
        Config.PVM_MEDIUM.set(player, minigamesMediumAmount);
        Config.PVM_HARD.set(player, minigamesHardAmount);
        Config.PVM_ELITE.set(player, minigamesEliteAmount);
        Config.MINIGAMES_EASY_COMPLETED.set(player, minigamesEasyAmount == MinigamesAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.MINIGAMES_MEDIUM_COMPLETED.set(player, minigamesMediumAmount == MinigamesAchievementDiary.MEDIUM_TASKS.size() ? 1 : 0);
        Config.MINIGAMES_HARD_COMPLETED.set(player, minigamesHardAmount == MinigamesAchievementDiary.HARD_TASKS.size() ? 1 : 0);
        Config.MINIGAMES_ELITE_COMPLETED.set(player, minigamesEliteAmount == MinigamesAchievementDiary.ELITE_TASKS.size() ? 1 : 0);



        //PvP
        int pvpEasyAmount = player.getDiaryManager().getPvpDiary().getEasyAmountCompleted();
        int pvpMediumAmount = player.getDiaryManager().getPvpDiary().getMediumAmountCompleted();
        int pvpHardAmount = player.getDiaryManager().getPvpDiary().getHardAmountCompleted();
        int pvpEliteAmount = player.getDiaryManager().getPvpDiary().getEliteAmountCompleted();
        Config.PVP_EASYU.set(player, pvpEasyAmount);
        Config.PVP_MEDIUMU.set(player, pvpMediumAmount);
        Config.PVP_HARDU.set(player, pvpHardAmount);
        Config.PVP_ELITEU.set(player, pvpEliteAmount);
        Config.PVP_EASY_COMPLETED.set(player, pvpEasyAmount == PvPAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.PVP_MEDIUM_COMPLETED.set(player, pvpMediumAmount == PvPAchievementDiary.MEDIUM_TASKS.size() ? 1 : 0);
        Config.PVP_HARD_COMPLETED.set(player, pvpHardAmount == PvPAchievementDiary.HARD_TASKS.size() ? 1 : 0);
        Config.PVP_ELITE_COMPLETED.set(player, pvpEliteAmount == PvPAchievementDiary.ELITE_TASKS.size() ? 1 : 0);


        //Skilling
        int skillingEasyAmount = player.getDiaryManager().getSkillingDiary().getEasyAmountCompleted();
        int skillingMediumAmount = player.getDiaryManager().getSkillingDiary().getMediumAmountCompleted();
        int skillingHardAmount = player.getDiaryManager().getSkillingDiary().getHardAmountCompleted();
        int skillingEliteAmount = player.getDiaryManager().getSkillingDiary().getEliteAmountCompleted();
        Config.SKILLING_EASYU.set(player, skillingEasyAmount);
        Config.SKILLING_MEDIUMU.set(player, skillingMediumAmount);
        Config.SKILLING_HARDU.set(player, skillingHardAmount);
        Config.SKILLING_ELITEU.set(player, skillingEliteAmount);
        Config.SKILLING_EASY_COMPLETED.set(player, skillingEasyAmount == SkillingAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.SKILLING_MEDIUM_COMPLETED.set(player, skillingMediumAmount == SkillingAchievementDiary.MEDIUM_TASKS.size() ? 1 : 0);
        Config.SKILLING_HARD_COMPLETED.set(player, skillingHardAmount == SkillingAchievementDiary.HARD_TASKS.size() ? 1 : 0);
        Config.SKILLING_ELITE_COMPLETED.set(player, skillingEliteAmount == SkillingAchievementDiary.ELITE_TASKS.size() ? 1 : 0);


        //Wildy
        int wildernessEasyAmount = player.getDiaryManager().getWildernessDiary().getEasyAmountCompleted();
        int wildernessMediumAmount = player.getDiaryManager().getWildernessDiary().getEasyAmountCompleted();
        int wildernessHardAmount = player.getDiaryManager().getWildernessDiary().getEasyAmountCompleted();
        int wildernessEliteAmount = player.getDiaryManager().getWildernessDiary().getEasyAmountCompleted();
        Config.WILDERNESS_EASY.set(player, wildernessEasyAmount);
        Config.WILDERNESS_MEDIUM.set(player, wildernessMediumAmount);
        Config.WILDERNESS_HARD.set(player, wildernessHardAmount);
        Config.WILDERNESS_ELITE.set(player, wildernessEliteAmount);
        Config.WILDERNESS_EASY_COMPLETED.set(player, wildernessEasyAmount == WildernessAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.WILDERNESS_MEDIUM_COMPLETED.set(player, wildernessMediumAmount == WildernessAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.WILDERNESS_HARD_COMPLETED.set(player, wildernessHardAmount == WildernessAchievementDiary.EASY_TASKS.size() ? 1 : 0);
        Config.WILDERNESS_ELITE_COMPLETED.set(player, wildernessEliteAmount == WildernessAchievementDiary.EASY_TASKS.size() ? 1 : 0);
    }

    public String getName() {
        return name;
    }

    public boolean hasDone(T entry) {
        return get(entry).isPresent();
    }

    public boolean hasDone(Set<T> entries) {
        boolean containsAll = true;
        for (T entry : entries) {
            if (!achievements.contains(entry)) {
                containsAll = false;
            }
        }
        return containsAll;
    }

    public void forEach(Consumer<T> action) {
        achievements.forEach(entry -> action.accept(entry));
    }

    public Optional<T> get(T entry) {
        return achievements.stream().filter(some -> some.equals(entry))
                .findAny();
    }

    public Set<T> getAchievements() {
        return achievements;
    }

    public Player getPlayer() {
        return player;
    }
}
