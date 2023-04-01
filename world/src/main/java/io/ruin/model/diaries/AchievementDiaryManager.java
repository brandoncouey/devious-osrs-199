package io.ruin.model.diaries;

import io.ruin.model.diaries.pvp.PvPAchievementDiary;
import io.ruin.model.diaries.minigames.MinigamesAchievementDiary;
import io.ruin.model.diaries.skilling.SkillingAchievementDiary;
import io.ruin.model.diaries.pvm.PvMAchievementDiary;
import io.ruin.model.diaries.devious.DeviousAchievementDiary;
import io.ruin.model.diaries.wilderness.WildernessAchievementDiary;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import lombok.Getter;

public final class AchievementDiaryManager {

    @Getter private final Player player;
    @Getter private final PvPAchievementDiary pvpDiary;
    @Getter private final SkillingAchievementDiary skillingDiary;
    @Getter private final PvMAchievementDiary pvmDiary;
    @Getter private final WildernessAchievementDiary wildernessDiary;
    @Getter private final MinigamesAchievementDiary minigamesDiary;
    @Getter
    private final DeviousAchievementDiary deviousDiary;

    public AchievementDiaryManager(Player player) {
        this.player = player;
        deviousDiary = new DeviousAchievementDiary(player);
        minigamesDiary = new MinigamesAchievementDiary(player);
        pvmDiary = new PvMAchievementDiary(player);
        pvpDiary = new PvPAchievementDiary(player);
        skillingDiary = new SkillingAchievementDiary(player);
        wildernessDiary = new WildernessAchievementDiary(player);
    }


    public void login() {
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


}
