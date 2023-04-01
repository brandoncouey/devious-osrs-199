package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.model.map.Position;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.utility.OfflineMode;
import net.dv8tion.jda.api.EmbedBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class Loggers extends DatabaseUtils {
    public static void logRaidsUnique(String player, String itemName, int raidsCount) {
//        if (OfflineMode.enabled)
//            return;
        /*Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_raids_uniques", "player", "item", "raids_count"))) {
                statement.setString(1, player);
                statement.setString(2, itemName);
                statement.setInt(3, raidsCount);
            }
        });*/
    }

    public static void logRaidsCompletion(String[] players, String duration, int points) {
//        if (OfflineMode.enabled)
//            return;
        String playersList = String.join(",", players);
        /*Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_raids_completed", "players", "duration", "total_points"))) {
                statement.setString(1, playersList);
                statement.setString(2, duration);
                statement.setInt(3, points);
            }
        });*/
    }








    public static void logSigmund(int userId, int itemId, int amount) {
//        if (OfflineMode.enabled)
//            return;

        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("sigmund_sales", "user_id", "item_id", "amount"))) {
                statement.setInt(1, userId);
                statement.setInt(2, itemId);
                statement.setInt(3, amount);
                statement.execute();
            }
        });
    }

    public static void logTournamentResults(int userId, String userName, String userIp, int place) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_tournament_results", "user_id", "user_name", "user_ip", "place", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setInt(4, place);
                statement.setInt(5, World.id);
                statement.setString(6, World.stage.name());
                statement.setString(7, World.type.name());
                statement.execute();
            }
        });
    }

    /**
     * Utils
     */

    private static String toJson(Item... items) {
        //Be sure this method is never called on the main thread lol
        String json = JsonUtils.GSON_EXPOSE.toJson(items);
        if (items != null) {
            for (Item item : items) {
                if (item != null)
                    json = json.replace("\"id\":" + item.getId() + ",", "\"id\":" + item.getId() + ",\"name\":\"" + item.getDef().name + "\",");
            }
            json = json.replace(",\"uniqueValue\":0", ""); //save some room in the db?!
        }
        return json;
    }


}