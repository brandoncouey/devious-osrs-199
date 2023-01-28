package io.ruin.services;

import io.ruin.model.combat.CombatUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.Utils;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Highscores implements Runnable {

    public static final long MAX_XP = ((long) 5000000000.0);

    public static final String HOST = "185.251.116.66";
    public static final String USER = "psdeviou_root";
    public static final String PASS = "O%%BSM@R(s)S";
    public static final String DATABASE = "psdeviou_scores";

    private Player player;

    public Highscores(Player player) {
        this.player = player;
    }

    /**
     * Runs on a new thread. to run this, use this code anywhere:
     * new Thread(new Highscores(player)).start();
     */
    @Override
    public void run() {
        try {
            Database db = new Database(HOST, USER, PASS, DATABASE);

            if (!db.init()) {
                return;
            }

            String username = Utils.formatString(player.getName());

            long overall_xp = player.getStats().totalXp;
            long stored_xp  = 0;

            ResultSet rs = db.executeQuery("SELECT * FROM hs_stats WHERE username='"+username+"' LIMIT 1");

            if (rs.next()) {
                // get stored overall exp to compare to player's current overall xp.
                stored_xp = rs.getLong("overall_xp");
            }

            //TODO we update hs_users

            Date date = new Date();
            date.setHours(date.getHours() + 6);//We have to offset the hours due to timezones
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (stored_xp != overall_xp) {

                HashMap<String, String> map = new HashMap<>();
                map.put("username", Utils.formatString(player.getName()));
                map.put("rights", "" + player.getPrimaryGroup().ordinal());
                map.put("mode", "regular");
                map.put("exp_rate", "" + player.xpMode.name());
                map.put("total_level", "" + player.getStats().totalLevel);
                map.put("cmb_level", "" + CombatUtils.getCombatLevel(player));
                map.put("last_update", sdf.format(date));
                db.executeUpdate(this.buildQuery("hs_users", map));

                map.clear();

                map.put("username", Utils.formatString(player.getName()));
                map.put("overall_xp", "" + overall_xp);
                for (int i = 0; i < StatType.values().length; i++) {
                    map.put(StatType.values()[i].name().toLowerCase() + "_xp", "" + (int) player.getStats().get(i).experience);
                }
                map.put("last_update", sdf.format(date));
                db.executeUpdate(this.buildQuery("hs_stats", map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a query from a given HashMap. shouldn't be a need to alter this.
     * @param map
     * @return
     */
    private String buildQuery(String tableName, HashMap<String, String> map) {
        String query = "INSERT INTO " + tableName + " (";

        Object[] keys = map.keySet().toArray();
        Object[] values = map.values().toArray();

        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            query += "" + key + (i == keys.length-1 ? ") " : ", ");
        }

        query += "VALUES(";

        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            Object value = values[i];

            query += "'" + value + "'" + (i == keys.length-1 ? "" : ", ");
        }

        query += ") ON DUPLICATE KEY UPDATE ";

        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            Object value = values[i];

            query += "" + key + " = '" + value + "'" + (i == keys.length-1 ? "" : ", ");
        }

        return query;
    }

}
