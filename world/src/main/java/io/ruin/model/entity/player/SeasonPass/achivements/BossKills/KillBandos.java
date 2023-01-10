package io.ruin.model.entity.player.SeasonPass.achivements.BossKills;

import com.google.gson.annotations.Expose;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementListener;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.battlepass.BattlePass;
import io.ruin.utility.Utils;

public class KillBandos implements AchievementListener {

    @Expose public int counterStartValue = 0;


    private int xpReward = 10;

    @Expose private static AchievementStage oldStage;

    @Expose private boolean Completed;
    @Expose private boolean started;

    @Expose private int amount = 0;

    private final String bossName;

    public KillBandos(int i) {
        amount = i;
        xpReward = amount * 25;
        bossName = "General Graador";
    }


    public void init(Player player, int setAmount) {
        amount = setAmount;
        xpReward = amount * 25;
        counterStartValue = player.generalGraardorKills.getKills();
        oldStage = stage(player);
        Completed = false;
        started = false;

    }

    public int get(Player player) {
        counterStartValue = player.getBattlePass().tasks.Kill10Bandos.counterStartValue;
        int i = player.generalGraardorKills.getKills() - counterStartValue;
        if(i < 0){
            init(player,amount);
        }
        return i;
    }

    @Override
    public String name() {
        return "Kill " + amount + " " + bossName;
    }

    public void update(Player player){
        AchievementStage newStage = stage(player);
        if(newStage != oldStage) {

            oldStage = newStage;

            if(newStage == AchievementStage.STARTED && !started) {
                player.sendMessage("<col=000080>You have started the Season task: <col=800000>" + name());
                started(player);
                started = true;
            } else if(newStage == AchievementStage.FINISHED && !Completed) {
                player.sendMessage("<col=000080>You have completed the Season task: <col=800000>" + name());
                finished(player);
                Completed = true;
            }
        }
    }

    public AchievementStage stage(Player player) {
        int amt = get(player);
        if (amt >= amount)
            return AchievementStage.FINISHED;
        else if (amt == 0)
            return AchievementStage.NOT_STARTED;
        else
            return AchievementStage.STARTED;
    }

    @Override
    public String[] lines(Player player, boolean finished) {
        return new String[]{
                Achievement.slashIf("", finished),
                "",
                Achievement.slashIf("<col=000080>Assignment</col>: Kill " + amount + " "+ bossName, finished),
                Achievement.slashIf("<col=000080>Reward</col>: "+ xpReward +" Season xp.", finished),
                "",
                "<col=000080>Completed : <col=800000>" + Utils.formatMoneyString(get(player)),
        };
    }

    @Override
    public void started(Player player) {

    }

    @Override
    public void finished(Player player) {
        BattlePass.addExp(xpReward,player);
    }
}
