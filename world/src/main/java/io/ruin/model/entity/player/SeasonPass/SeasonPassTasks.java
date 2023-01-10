package io.ruin.model.entity.player.SeasonPass;

import com.google.gson.annotations.Expose;
import io.ruin.model.achievements.AchievementListener;
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

public class SeasonPassTasks {
    @Expose public CutRedwoodLogs cut10Redwood;
    @Expose public CutMagicLogs cut10Magic;
    @Expose public CutYewLogs cut10Yew;
    @Expose public CutMapleLogs cut10Maple;

    @Expose public CutRedwoodLogs cut100Redwood;
    @Expose public CutMagicLogs cut100Magic;
    @Expose public CutYewLogs cut100Yew;
    @Expose public CutMapleLogs cut100Maple;

    @Expose public CutRedwoodLogs cut1000Redwood;
    @Expose public CutMagicLogs cut1000Magic;
    @Expose public CutYewLogs cut1000Yew;
    @Expose public CutMapleLogs cut1000Maple;


    @Expose public MineMithrilOre Mine10Mithril;
    @Expose public MineMithrilOre Mine100Mithril;
    @Expose public MineMithrilOre Mine1000Mithril;

    @Expose public MineRuneOre Mine10Rune;
    @Expose public MineRuneOre Mine100Rune;
    @Expose public MineRuneOre Mine1000Rune;

    @Expose public KillBandos Kill10Bandos;
    @Expose public KillBandos Kill100Bandos;
    @Expose public KillBandos Kill1000Bandos;

    @Expose public KillZulrah Kill10Zulrah;
    @Expose public KillZulrah Kill100Zulrah;
    @Expose public KillZulrah Kill1000Zulrah;

    @Expose public KillBarrows loot10BarrowsChest;
    @Expose public KillBarrows loot100BarrowsChest;
    @Expose public KillBarrows loot1000BarrowsChest;

    public SeasonPassTasks(Player player) {
        AchievementListener temp = new KillBandos(10);
    }
    public void update(Player player){
        if(cut10Magic != null){
            cut10Magic.update(player);
        }else{
            cut10Magic = new CutMagicLogs(10);
            cut10Magic.init(player,10);
            cut10Magic.update(player);
        }
        if(cut10Yew != null){
            cut10Yew.update(player);
        }else{
            cut10Yew = new CutYewLogs(10);
            cut10Yew.init(player,10);
            cut10Yew.update(player);
        }
        if(cut10Maple != null){
            cut10Maple.update(player);
        }else{
            cut10Maple = new CutMapleLogs(10);
            cut10Maple.init(player,10);
            cut10Maple.update(player);
        }

        if(cut100Magic != null){
            cut100Magic.update(player);
        }else{
            cut100Magic = new CutMagicLogs(100);
            cut100Magic.init(player,100);
            cut100Magic.update(player);
        }

        if(cut100Yew != null){
            cut100Yew.update(player);
        }else{
            cut100Yew = new CutYewLogs(100);
            cut100Yew.init(player,100);
            cut100Yew.update(player);
        }

        if(cut100Maple != null){
            cut100Maple.update(player);
        }else{
            cut100Maple = new CutMapleLogs(100);
            cut100Maple.init(player,100);
            cut100Maple.update(player);
        }


        if(cut1000Magic != null){
            cut1000Magic.update(player);
        }else{
            cut1000Magic = new CutMagicLogs(1000);
            cut1000Magic.init(player,1000);
            cut1000Magic.update(player);
        }

        if(cut1000Yew != null){
            cut1000Yew.update(player);
        }else{
            cut1000Yew = new CutYewLogs(1000);
            cut1000Yew.init(player,1000);
            cut1000Yew.update(player);
        }

        if(cut1000Maple != null){
            cut1000Maple.update(player);
        }else{
            cut1000Maple = new CutMapleLogs(1000);
            cut1000Maple.init(player,1000);
            cut1000Maple.update(player);
        }

        if(Mine10Mithril != null){
            Mine10Mithril.update(player);
        }else{
            Mine10Mithril = new MineMithrilOre(10);
            Mine10Mithril.init(player,10);
            Mine10Mithril.update(player);
        }
        if(Mine100Mithril != null){
            Mine100Mithril.update(player);
        }else{
            Mine100Mithril = new MineMithrilOre(100);
            Mine100Mithril.init(player,100);
            Mine100Mithril.update(player);
        }
        if(Mine1000Mithril != null){
            Mine1000Mithril.update(player);
        }else{
            Mine1000Mithril = new MineMithrilOre(1000);
            Mine1000Mithril.init(player,1000);
            Mine1000Mithril.update(player);
        }

        if(Mine10Rune != null){
            Mine10Rune.update(player);
        }else{
            Mine10Rune = new MineRuneOre(10);
            Mine10Rune.init(player,10);
            Mine10Rune.update(player);
        }
        if(Mine100Rune != null){
            Mine100Rune.update(player);
        }else{
            Mine100Rune = new MineRuneOre(100);
            Mine100Rune.init(player,100);
            Mine100Rune.update(player);
        }
        if(Mine1000Rune != null){
            Mine1000Rune.update(player);
        }else{
            Mine1000Rune = new MineRuneOre(1000);
            Mine1000Rune.init(player,1000);
            Mine1000Rune.update(player);
        }

        if(Kill10Bandos != null){
            Kill10Bandos.update(player);
        }else{
            Kill10Bandos = new KillBandos(10);
            Kill10Bandos.init(player,10);
            Kill10Bandos.update(player);
        }
        if(Kill100Bandos != null){
            Kill100Bandos.update(player);
        }else{
            Kill100Bandos = new KillBandos(100);
            Kill100Bandos.init(player,100);
            Kill100Bandos.update(player);
        }


        if(Kill10Zulrah != null){
            Kill10Zulrah.update(player);
        }else{
            Kill10Zulrah = new KillZulrah(10);
            Kill10Zulrah.init(player,10);
            Kill10Zulrah.update(player);
        }
        if(Kill100Zulrah != null){
            Kill100Zulrah.update(player);
        }else{
            Kill100Zulrah = new KillZulrah(100);
            Kill100Zulrah.init(player,100);
            Kill100Zulrah.update(player);
        }

        if(Kill1000Zulrah != null){
            Kill1000Zulrah.update(player);
        }else{
            Kill1000Zulrah = new KillZulrah(1000);
            Kill1000Zulrah.init(player,1000);
            Kill1000Zulrah.update(player);
        }

        if(loot10BarrowsChest != null){
            loot10BarrowsChest.update(player);
        }else{
            loot10BarrowsChest = new KillBarrows(10);
            loot10BarrowsChest.init(player,10);
            loot10BarrowsChest.update(player);
        }
        if(loot100BarrowsChest != null){
            loot100BarrowsChest.update(player);
        }else{
            loot100BarrowsChest = new KillBarrows(100);
            loot100BarrowsChest.init(player,100);
            loot100BarrowsChest.update(player);
        }

        if(loot1000BarrowsChest != null){
            loot1000BarrowsChest.update(player);
        }else{
            loot1000BarrowsChest = new KillBarrows(1000);
            loot1000BarrowsChest.init(player,1000);
            loot1000BarrowsChest.update(player);
        }

        if(cut10Redwood != null){
            cut10Redwood.update(player);
        }else{
            cut10Redwood = new CutRedwoodLogs(10);
            cut10Redwood.init(player,10);
            cut10Redwood.update(player);
        }
        if(cut100Redwood != null){
            cut100Redwood.update(player);
        }else{
            cut100Redwood = new CutRedwoodLogs(100);
            cut100Redwood.init(player,100);
            cut100Redwood.update(player);
        }

        if(cut1000Redwood != null){
            cut1000Redwood.update(player);
        }else{
            cut1000Redwood = new CutRedwoodLogs(1000);
            cut1000Redwood.init(player,1000);
            cut1000Redwood.update(player);
        }


    }

}
