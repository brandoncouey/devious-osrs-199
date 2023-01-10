package io.ruin.model.diaries;

import io.ruin.model.diaries.ardougne.ArdougneAchievementDiary;
import io.ruin.model.diaries.desert.DesertAchievementDiary;
import io.ruin.model.diaries.falador.FaladorAchievementDiary;
import io.ruin.model.diaries.fremennik.FremennikAchievementDiary;
import io.ruin.model.diaries.kandarin.KandarinAchievementDiary;
import io.ruin.model.diaries.karamja.KaramjaAchievementDiary;
import io.ruin.model.diaries.kourend.KourendAchievementDiary;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorAchievementDiary;
import io.ruin.model.diaries.morytania.MorytaniaAchievementDiary;
import io.ruin.model.diaries.varrock.VarrockAchievementDiary;
import io.ruin.model.diaries.western.WesternAchievementDiary;
import io.ruin.model.diaries.wilderness.WildernessAchievementDiary;
import io.ruin.model.entity.player.Player;

public final class AchievementDiaryManager {

    private final Player player;
    private final VarrockAchievementDiary varrockDiary;
    private final ArdougneAchievementDiary ardougneDiary;
    private final FaladorAchievementDiary faladorDiary;
    private final LumbridgeDraynorAchievementDiary lumbridgeDraynorDiary;
    private final KaramjaAchievementDiary karamjaDiary;
    private final WildernessAchievementDiary wildernessDiary;
    private final MorytaniaAchievementDiary morytaniaDiary;
    private final KandarinAchievementDiary kandarinDiary;
    private final FremennikAchievementDiary fremennikDiary;
    private final WesternAchievementDiary westernDiary;
    private final DesertAchievementDiary desertDiary;
    private final KourendAchievementDiary kourendDiary;

    public AchievementDiaryManager(Player player) {
        this.player = player;
        varrockDiary = new VarrockAchievementDiary(player);
        ardougneDiary = new ArdougneAchievementDiary(player);
        faladorDiary = new FaladorAchievementDiary(player);
        lumbridgeDraynorDiary = new LumbridgeDraynorAchievementDiary(player);
        karamjaDiary = new KaramjaAchievementDiary(player);
        wildernessDiary = new WildernessAchievementDiary(player);
        morytaniaDiary = new MorytaniaAchievementDiary(player);
        kandarinDiary = new KandarinAchievementDiary(player);
        fremennikDiary = new FremennikAchievementDiary(player);
        westernDiary = new WesternAchievementDiary(player);
        desertDiary = new DesertAchievementDiary(player);
        kourendDiary = new KourendAchievementDiary(player);
    }

    public VarrockAchievementDiary getVarrockDiary() {
        return varrockDiary;
    }

    public ArdougneAchievementDiary getArdougneDiary() {
        return ardougneDiary;
    }

    public FaladorAchievementDiary getFaladorDiary() {
        return faladorDiary;
    }

    public LumbridgeDraynorAchievementDiary getLumbridgeDraynorDiary() {
        return lumbridgeDraynorDiary;
    }

    public KaramjaAchievementDiary getKaramjaDiary() {
        return karamjaDiary;
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

    public KourendAchievementDiary getKourendDiary() {
        return kourendDiary;
    }

    public DesertAchievementDiary getDesertDiary() {
        return desertDiary;
    }

    public Player getPlayer() {
        return player;
    }
}
