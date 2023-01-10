package io.ruin.model.entity.player.SeasonPass;

import io.ruin.model.achievements.AchievementCategory;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SeasonPass.achivements.BossKills.KillBandos;
import io.ruin.model.entity.player.SeasonPass.achivements.BossKills.KillBarrows;
import io.ruin.model.entity.player.SeasonPass.achivements.BossKills.KillZulrah;
import io.ruin.model.entity.player.SeasonPass.achivements.mining.MineMithrilOre;
import io.ruin.model.entity.player.SeasonPass.achivements.mining.MineRuneOre;
import io.ruin.model.entity.player.SeasonPass.achivements.woodcutting.CutMagicLogs;
import io.ruin.model.entity.player.SeasonPass.achivements.woodcutting.CutMapleLogs;
import io.ruin.model.entity.player.SeasonPass.achivements.woodcutting.CutRedwoodLogs;
import io.ruin.model.entity.player.SeasonPass.achivements.woodcutting.CutYewLogs;
import io.ruin.model.inter.InterfaceType;

public enum Achievements {

    /**
     * Intro
     */
    Cut10RedwoodLogs(new CutRedwoodLogs(10), AchievementCategory.Introductory),
    Cut10MagicLogs(new CutMagicLogs(10), AchievementCategory.Introductory),
    Cut10MapleLogs(new CutMapleLogs(10), AchievementCategory.Introductory),
    Cut10YewLogs(new CutYewLogs(10), AchievementCategory.Introductory),

    Cut100RedwoodLogs(new CutRedwoodLogs(100), AchievementCategory.Novice),
    Cut100MagicLogs(new CutMagicLogs(100), AchievementCategory.Novice),
    Cut100MapleLogs(new CutMapleLogs(100), AchievementCategory.Novice),
    Cut100YewLogs(new CutYewLogs(100), AchievementCategory.Novice),

    Cut1000RedwoodLogs(new CutRedwoodLogs(1000), AchievementCategory.Experienced),
    Cut1000MagicLogs(new CutMagicLogs(1000), AchievementCategory.Experienced),
    Cut1000MapleLogs(new CutMapleLogs(1000), AchievementCategory.Experienced),
    Cut1000YewLogs(new CutYewLogs(1000), AchievementCategory.Experienced),

    Mine10Rune(new MineRuneOre(10), AchievementCategory.Introductory),
    Mine100Rune(new MineRuneOre(100), AchievementCategory.Novice),
    Mine1000Rune(new MineRuneOre(1000), AchievementCategory.Experienced),

    Mine10Mithril(new MineMithrilOre(10), AchievementCategory.Introductory),
    Mine100Mithril(new MineMithrilOre(100), AchievementCategory.Novice),
    Mine1000Mithril(new MineMithrilOre(1000), AchievementCategory.Experienced),

    Kill10Bandos(new KillBandos(10), AchievementCategory.Experienced),
    Kill100Bandos(new KillBandos(100), AchievementCategory.Master),

    Kill10Barrows(new KillBarrows(10), AchievementCategory.Experienced),
    Kill100Barrows(new KillBarrows(100), AchievementCategory.Master),
    Kill1000Barrows(new KillBarrows(1000), AchievementCategory.Master),

    Kill10Zulrah(new KillZulrah(10), AchievementCategory.Experienced),
    Kill100Zulrah(new KillZulrah(100), AchievementCategory.Master),
    Kill1000Zulrah(new KillZulrah(1000), AchievementCategory.Master);

    private final AchievementListener listener;
    private final AchievementCategory category;

    Achievements(AchievementListener listener, AchievementCategory category) {
        this.listener = listener;
        this.category = category;
    }

    public AchievementListener getListener() {
        return listener;
    }

    public AchievementCategory getCategory() {
        return category;
    }

    public void update(Player player) {
        AchievementStage oldStage = player.achievementStages[ordinal()];
        AchievementStage newStage = player.achievementStages[ordinal()];
        if (newStage != oldStage) {
            if (newStage == AchievementStage.STARTED) {
                player.sendMessage("<col=000080>You have started the task: <col=800000>" + getListener().name());
                getListener().started(player);
            } else if (newStage == AchievementStage.FINISHED) {
                player.openInterface(InterfaceType.PRIMARY_OVERLAY, 660);
                player.getPacketSender().sendClientScript(3343, "iss", 0xff981f, "Task completed", getListener().name());
                player.sendMessage("You have completed the task" + "<col=FF0000>!" + getListener().name());
                getListener().finished(player);
            }
        }
    }

    public static AchievementStage counterStage(int current, int start, int finish) {
        if (current >= finish)
            return AchievementStage.FINISHED;
        else if (current <= start)
            return AchievementStage.NOT_STARTED;
        return AchievementStage.STARTED;
    }

    public boolean isFinished(Player player) {
        return getListener().stage(player) == AchievementStage.FINISHED;
    }

    public static void staticInit() {
        for (Achievements achievement : values()) {
            if (achievement.listener != null) achievement.listener.init();
        }
    }

    static {
       /* LoginListener.register(player -> {
            for (Achievements achievement : values()) {
                player.achievementStages[achievement.ordinal()] = achievement.getListener().stage(player); // forces achievements to have the correct state on login
            }
        });*/
    }
}

