package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Hiscores {

    public static void save(Player player) {
//        if (player.isAdmin())
//            return;
        saveHiscores(player);
    }

    /**
     * ECO
     */

    private static void saveHiscores(Player player) {

        Server.hsDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    statement = connection.prepareStatement("SELECT * FROM highscores WHERE username = ? LIMIT 1", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    System.err.println(player.getName());
                    System.err.println(resultSet);
                    if (resultSet.next()) {
                        updateECO(player, resultSet);
                        resultSet.updateRow();
                    } else {
                        resultSet.moveToInsertRow();
                        updateECO(player, resultSet);
                        resultSet.insertRow();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw, true);
                t.printStackTrace(pw);
                System.out.println("FAILED TO UPDATE HISCORES FOR: " + player.getName());
                System.out.println(sw.getBuffer().toString());
                /* do nothing */
            }
        });
    }

    private static void updateECO(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateString("username", player.getName());
        resultSet.updateString("rights", player.getPrimaryGroup().name());
        resultSet.updateString("mode", player.getGameMode().name());
        resultSet.updateString("xpmode", player.xpMode.getName());
        resultSet.updateInt("total_level", player.getStats().totalLevel);
        resultSet.updateInt("attack", player.getStats().get(StatType.Attack).currentLevel);
        resultSet.updateInt("defence", player.getStats().get(StatType.Defence).currentLevel);
        resultSet.updateInt("strength", player.getStats().get(StatType.Strength).currentLevel);
        resultSet.updateInt("hitpoints", player.getStats().get(StatType.Hitpoints).currentLevel);
        resultSet.updateInt("ranged", player.getStats().get(StatType.Ranged).currentLevel);
        resultSet.updateInt("prayer", player.getStats().get(StatType.Prayer).currentLevel);
        resultSet.updateInt("magic", player.getStats().get(StatType.Magic).currentLevel);
        resultSet.updateInt("cooking", player.getStats().get(StatType.Cooking).currentLevel);
        resultSet.updateInt("woodcutting", player.getStats().get(StatType.Woodcutting).currentLevel);
        resultSet.updateInt("fletching", player.getStats().get(StatType.Fletching).currentLevel);
        resultSet.updateInt("fishing", player.getStats().get(StatType.Fishing).currentLevel);
        resultSet.updateInt("firemaking", player.getStats().get(StatType.Firemaking).currentLevel);
        resultSet.updateInt("crafting", player.getStats().get(StatType.Crafting).currentLevel);
        resultSet.updateInt("smithing", player.getStats().get(StatType.Smithing).currentLevel);
        resultSet.updateInt("mining", player.getStats().get(StatType.Mining).currentLevel);
        resultSet.updateInt("herblore", player.getStats().get(StatType.Herblore).currentLevel);
        resultSet.updateInt("agility", player.getStats().get(StatType.Agility).currentLevel);
        resultSet.updateInt("thieving", player.getStats().get(StatType.Thieving).currentLevel);
        resultSet.updateInt("slayer", player.getStats().get(StatType.Slayer).currentLevel);
        resultSet.updateInt("farming", player.getStats().get(StatType.Farming).currentLevel);
        resultSet.updateInt("runecrafting", player.getStats().get(StatType.Runecrafting).currentLevel);
        resultSet.updateInt("hunter", player.getStats().get(StatType.Hunter).currentLevel);
        resultSet.updateInt("construction", player.getStats().get(StatType.Construction).currentLevel);
        resultSet.updateInt("kills", Config.PVP_KILLS.get(player));
        resultSet.updateInt("deaths", Config.PVP_DEATHS.get(player));
        resultSet.updateInt("pk_rating", player.pkRating);
        resultSet.updateInt("highest_shutdown", player.highestShutdown);
        resultSet.updateInt("highest_killspress", player.highestKillSpree);
        resultSet.updateInt("last_man_standing", player.lmsWins);
        resultSet.updateInt("abyssal_sire", player.abyssalSireKills.getKills());
        resultSet.updateInt("alchemical_hydra", player.alchemicalHydraKills.getKills());
        resultSet.updateInt("cerberus", player.cerberusKills.getKills());
        resultSet.updateInt("giant_mole", player.giantMoleKills.getKills());
        resultSet.updateInt("kalphite_queen", player.kalphiteQueenKills.getKills());
        resultSet.updateInt("kraken", player.krakenKills.getKills());
        resultSet.updateInt("skotizo", player.skotizoKills.getKills());
        resultSet.updateInt("thermonuclear_smoke_devil", player.thermonuclearSmokeDevilKills.getKills());
        //  resultSet.updateInt("barrelchest",0);//TODO add BarrelChest KC
        resultSet.updateInt("elvarg", player.elvargKills.getKills());
        resultSet.updateInt("demonic", player.demonicGorillaKills.getKills());
        // resultSet.updateInt("giant_roc",0);//TODO add Giant Roc KC
        resultSet.updateInt("vorkath", player.vorkathKills.getKills());
        resultSet.updateInt("zulrah", player.zulrahKills.getKills());
        resultSet.updateInt("king_black_dragon", player.kingBlackDragonKills.getKills());
        resultSet.updateInt("dagannoth_prime", player.dagannothPrimeKills.getKills());
        resultSet.updateInt("dagannoth_rex", player.dagannothRexKills.getKills());
        resultSet.updateInt("dagannoth_supreme", player.dagannothSupremeKills.getKills());
        resultSet.updateInt("callisto", player.callistoKills.getKills());
        resultSet.updateInt("chaos_elemental", player.chaosElementalKills.getKills());
        resultSet.updateInt("chaos_fanatic", player.chaosFanaticKills.getKills());
        resultSet.updateInt("corporeal_beast", player.corporealBeastKills.getKills());
        resultSet.updateInt("crazy_archaeologist", player.crazyArchaeologistKills.getKills());
        resultSet.updateInt("scorpia", player.scorpiaKills.getKills());
        resultSet.updateInt("venenatis", player.venenatisKills.getKills());
        resultSet.updateInt("vetion", player.vetionKills.getKills());
        resultSet.updateInt("barrows_chests", player.barrowsChestsLooted.getKills());
        resultSet.updateInt("wintertodt", player.wintertodtKills.getKills());
        resultSet.updateInt("tztok_jad", player.jadCounter.getKills());
        resultSet.updateInt("tzkal_zuk", player.zukKills.getKills());
        resultSet.updateInt("commander_zilyana", player.commanderZilyanaKills.getKills());
        resultSet.updateInt("general_graardor", player.generalGraardorKills.getKills());
        resultSet.updateInt("kreearra", player.kreeArraKills.getKills());
        resultSet.updateInt("kril_tsutsaroth", player.krilTsutsarothKills.getKills());
        resultSet.updateInt("chambers_of_xeric", player.chambersofXericKills.getKills());
        resultSet.updateInt("theatre_of_blood", player.theatreOfBloodKills.getKills());
        resultSet.updateInt("the_nightmare", player.nightmareofAshihamaKills.getKills());
        resultSet.updateInt("clue_scroll_beginner", player.beginnerClueCount);
        resultSet.updateInt("clue_scroll_easy", player.easyClueCount);
        resultSet.updateInt("clue_scroll_medium", player.medClueCount);
        resultSet.updateInt("clue_scroll_hard", player.hardClueCount);
        resultSet.updateInt("clue_scroll_elite", player.eliteClueCount);
        resultSet.updateInt("clue_scroll_master", player.masterClueCount);
    }

}