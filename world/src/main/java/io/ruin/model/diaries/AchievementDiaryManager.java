package io.ruin.model.diaries;

import io.ruin.model.diaries.pvp.PvPAchievementDiary;
import io.ruin.model.diaries.minigames.MinigamesAchievementDiary;
import io.ruin.model.diaries.skilling.SkillingAchievementDiary;
import io.ruin.model.diaries.fremennik.FremennikAchievementDiary;
import io.ruin.model.diaries.kandarin.KandarinAchievementDiary;
import io.ruin.model.diaries.pvm.PvMAchievementDiary;
import io.ruin.model.diaries.devious.DeviousAchievementDiary;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorAchievementDiary;
import io.ruin.model.diaries.morytania.MorytaniaAchievementDiary;
import io.ruin.model.diaries.varrock.VarrockAchievementDiary;
import io.ruin.model.diaries.western.WesternAchievementDiary;
import io.ruin.model.diaries.wilderness.WildernessAchievementDiary;
import io.ruin.model.entity.player.Player;

public final class AchievementDiaryManager {

    private final Player player;
    private final VarrockAchievementDiary varrockDiary;
    private final PvPAchievementDiary pvpDiary;
    private final SkillingAchievementDiary skillingDiary;
    private final LumbridgeDraynorAchievementDiary lumbridgeDraynorDiary;
    private final PvMAchievementDiary pvmDiary;
    private final WildernessAchievementDiary wildernessDiary;
    private final MorytaniaAchievementDiary morytaniaDiary;
    private final KandarinAchievementDiary kandarinDiary;
    private final FremennikAchievementDiary fremennikDiary;
    private final WesternAchievementDiary westernDiary;
    private final MinigamesAchievementDiary minigamesDiary;
    private final DeviousAchievementDiary deviousDiary;

    public AchievementDiaryManager(Player player) {
        this.player = player;
        varrockDiary = new VarrockAchievementDiary(player);
        lumbridgeDraynorDiary = new LumbridgeDraynorAchievementDiary(player);
        morytaniaDiary = new MorytaniaAchievementDiary(player);
        kandarinDiary = new KandarinAchievementDiary(player);
        fremennikDiary = new FremennikAchievementDiary(player);
        westernDiary = new WesternAchievementDiary(player);

        deviousDiary = new DeviousAchievementDiary(player);
        minigamesDiary = new MinigamesAchievementDiary(player);
        pvmDiary = new PvMAchievementDiary(player);
        pvpDiary = new PvPAchievementDiary(player);
        skillingDiary = new SkillingAchievementDiary(player);
        wildernessDiary = new WildernessAchievementDiary(player);
    }

    public VarrockAchievementDiary getVarrockDiary() {
        return varrockDiary;
    }

    public PvPAchievementDiary getPvpDiary() {
        return pvpDiary;
    }

    public SkillingAchievementDiary getSkillingDiary() {
        return skillingDiary;
    }

    public LumbridgeDraynorAchievementDiary getLumbridgeDraynorDiary() {
        return lumbridgeDraynorDiary;
    }

    public PvMAchievementDiary getPvmDiary() {
        return pvmDiary;
    }

    public WildernessAchievementDiary getWildernessDiary() {
        return wildernessDiary;
    }

    public MorytaniaAchievementDiary getMorytaniaDiary() {
        return morytaniaDiary;
    }

    public KandarinAchievementDiary getKandarinDiary() {
        return kandarinDiary;
    }

    public FremennikAchievementDiary getFremennikDiary() {
        return fremennikDiary;
    }

    public WesternAchievementDiary getWesternDiary() {
        return westernDiary;
    }

    public DeviousAchievementDiary getDeviousDiary() {
        return deviousDiary;
    }

    public MinigamesAchievementDiary getMinigamesDiary() {
        return minigamesDiary;
    }

    public Player getPlayer() {
        return player;
    }
}
