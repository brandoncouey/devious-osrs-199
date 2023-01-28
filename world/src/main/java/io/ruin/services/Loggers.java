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
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_raids_uniques", "player", "item", "raids_count"))) {
                statement.setString(1, player);
                statement.setString(2, itemName);
                statement.setInt(3, raidsCount);
            }
        });
    }

    public static void logRaidsCompletion(String[] players, String duration, int points) {
//        if (OfflineMode.enabled)
//            return;
        String playersList = String.join(",", players);
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_raids_completed", "players", "duration", "total_points"))) {
                statement.setString(1, playersList);
                statement.setString(2, duration);
                statement.setInt(3, points);
            }
        });
    }

    public static void logPublicChat(int userId, String userName, String userIp, String message, int type, int effects) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_public_chat", "user_id", "user_name", "user_ip", "message", "type", "effects", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setString(4, message);
                statement.setInt(5, type);
                statement.setInt(6, effects);
                statement.setInt(7, World.id);
                statement.setString(8, World.stage.name());
                statement.setString(9, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logClanChat(int userId, String userName, String userIp, String message) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_clan_chat", "user_id", "user_name", "user_ip", "message", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setString(4, message);
                statement.setInt(5, World.id);
                statement.setString(6, World.stage.name());
                statement.setString(7, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logYell(int userId, String userName, String userIp, String message) {
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_yell", "user_id", "user_name", "user_ip", "message"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setString(4, message);
                statement.execute();
            }
        });
    }

    public static void logPrivateChat(int userId, String userName, String userIp, String friendName, String message) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_private_chat", "user_id", "user_name", "user_ip", "friend_name", "message", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setString(4, friendName);
                statement.setString(5, message);
                statement.setInt(6, World.id);
                statement.setString(7, World.stage.name());
                statement.setString(8, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logCommand(int userId, String userName, String userIp, String commandQuery) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_commands", "user_id", "user_name", "user_ip", "cmd_query", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setString(4, commandQuery);
                statement.setInt(5, World.id);
                statement.setString(6, World.stage.name());
                statement.setString(7, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logLoyaltyChest(int userId, String userName, String userIp, int spree, Item... rewards) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_loyalty_chest", "user_id", "user_name", "user_ip", "spree", "rewards", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setInt(4, spree);
                statement.setString(5, toJson(rewards));
                statement.setInt(6, World.id);
                statement.setString(7, World.stage.name());
                statement.setString(8, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logTrade(int userId1, String userName1, String userIp1, int userId2, String userName2, String userIp2, Item[] userItems1_0, Item[] userItems2_0) {
//        if (OfflineMode.enabled)
//            return;
        Item[] userItems1 = userItems1_0.clone();
        Item[] userItems2 = userItems2_0.clone();
        //^clone required because the original array is cleared after a trade.
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_trades", "user_id1", "user_name1", "user_ip1", "user_id2", "user_name2", "user_ip2", "items1", "items2", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId1);
                statement.setString(2, userName1);
                statement.setString(3, userIp1);
                statement.setInt(4, userId2);
                statement.setString(5, userName2);
                statement.setString(6, userIp2);
                statement.setString(7, toJson(userItems1));
                statement.setString(8, toJson(userItems2));
                statement.setInt(9, World.id);
                statement.setString(10, World.stage.name());
                statement.setString(11, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logTrade(Player trader1, Player trader2, Item[] userItems1_0, Item[] userItems2_0) {
//        if (OfflineMode.enabled)
//            return;
        // Calculate values
        long value1 = 0;
        long value2 = 0;

        // Make copies, these can change immediately after submission
        Item[] offer1Items = userItems1_0.clone();
        Item[] offer2Items = userItems2_0.clone();

        // Calculate total price of first offer
        for (Item i : offer1Items) {
            if (i != null)
                value1 += getWealth(i);
        }

        // Calculate total of the second half
        for (Item i : offer2Items) {
            if (i != null)
                value2 += getWealth(i);
        }

        // Final values to use them in the anonymous subclass
        final long finalPrice1 = value1;
        final long finalPrice2 = value2;
        final int trader1Id = trader1.getUserId();
        final int trader2Id = trader2.getUserId();
        final String trader1Name = trader1.getName();
        final String trader2Name = trader2.getName();
        final String ip1 = trader1.getIp();
        final String ip2 = trader2.getIp();
        final Position tile = trader1.getPosition();


        if ((value1 >= 5_000_000 && value2 <= 50_000) || (value1 <= 50_000 & value2 >= 50_000_000)) {
            //notifyStaffTrade(trader1, value1, trader2, value2);
            if (!World.isDev()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("A Possible RWT has just happened!");
                eb.addField("Trader1: ", trader1Name, true);
                eb.addField("Trader1 Amount: ", String.valueOf(finalPrice1), true);
                eb.addField("Trader1 IP Address: ", trader1.getIp(), true);
                eb.addField("Trader2: ", trader2Name, true);
                eb.addField("Trader2 Amount: ", String.valueOf(finalPrice2), true);
                eb.addField("Trader2 IP Address: ", trader2.getIp(), true);
                eb.addField("Date: ", formatter.format(date), true);
                eb.setColor(new java.awt.Color(0xB00D03));
                //DiscordConnection.jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
            }
        }


        Server.gameDb.execute(con -> {
            try {
                PreparedStatement statement = con.prepareStatement("INSERT INTO trades" +
                        "(trader1_account_id, trader2_account_id, trader1_value, trader2_value, trader1_ip, trader2_ip, world, x, z, level, trader1_user, trader2_user) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                statement.setInt(1, trader1Id);
                statement.setInt(2, trader2Id);
                statement.setLong(3, finalPrice1);
                statement.setLong(4, finalPrice2);
                statement.setString(5, ip1);
                statement.setString(6, ip2);
                statement.setInt(7, World.id);
                statement.setInt(8, tile.getX());
                statement.setInt(9, tile.getY());
                statement.setInt(10, tile.getZ());
                statement.setString(11, trader1Name);
                statement.setString(12, trader2Name);
                statement.executeUpdate();


                statement = con.prepareStatement("INSERT INTO trade_items (trade_id, account_id, item_id, amount) VALUES (LAST_INSERT_ID(),?,?,?);");

                // Drill the offers into..
                for (Item item : offer1Items) {
                    if (item != null) {
                        statement.setInt(1, trader1Id);
                        statement.setInt(2, item.getId());
                        statement.setInt(3, item.getAmount());
                        statement.addBatch();
                    }
                }

                // Other half too..
                for (Item item : offer2Items) {
                    if (item != null) {
                        statement.setInt(1, trader2Id);
                        statement.setInt(2, item.getId());
                        statement.setInt(3, item.getAmount());
                        statement.addBatch();
                    }
                }

                statement.executeBatch();
            } catch (Exception e) {
                ServerWrapper.logError("Failed to log trade: " + trader1Name + " - " + trader2Name, e);
            }
        });
    }


    public static void logDuelStake(int userId1, String userName1, String userIp1, int userId2, String userName2, String userIp2, Item[] userItems1_0, Item[] userItems2_0, int winnerId) {
//        if (OfflineMode.enabled)
//            return;
        Item[] userItems = userItems1_0.clone();
        Item[] targetItems = userItems2_0.clone();
        //^clone required because the original array is cleared after a duel.
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_duel_stakes", "user_id1", "user_name1", "user_ip1", "user_id2", "user_name2", "user_ip2", "items1", "items2", "winner_id", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId1);
                statement.setString(2, userName1);
                statement.setString(3, userIp1);
                statement.setInt(4, userId2);
                statement.setString(5, userName2);
                statement.setString(6, userIp2);
                statement.setString(7, toJson(userItems));
                statement.setString(8, toJson(targetItems));
                statement.setInt(9, winnerId);
                statement.setInt(10, World.id);
                statement.setString(11, World.stage.name());
                statement.setString(12, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logDangerousDeath(int userId, String userName, String userIp, int killerId, String killerName, String killerIp, List<Item> itemsKept, List<Item> itemsLost) {
//        if (OfflineMode.enabled)
//            return;

        // Calculate values
        long value1 = 0;

        // Calculate total price of first offer
        for (Item i : itemsLost) {
            if (i != null)
                value1 += getWealth(i);
        }
        if (value1 >= 5_000_000) {
            for (Player p : World.players) {
                if (p.getName().equalsIgnoreCase(userName)) {
                    if (!World.isDev()) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("[DUPE WARDEN! CRITICAL WARNING!]");
                        eb.addField("UserID: ", String.valueOf(p.getUserId()), true);
                        eb.addField("Username: ", p.getName(), true);
                        eb.addField("UserIP: ", p.getIp(), true);
                        eb.addField("User POS: ", "X:" + p.getPosition().getX() + " Y:" + p.getPosition().getY() + " Z:" + p.getPosition().getZ(), true);
                        eb.addField("Amount: ", NumberFormat.getInstance().format(value1), true);
                        eb.addField("Date: ", formatter.format(date), true);
                        eb.setColor(new java.awt.Color(0xB00D03));
                        //DiscordConnection.jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
                    }
                }
            }
        }
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_dangerous_deaths", "user_id", "user_name", "user_ip", "killer_id", "killer_name", "killer_ip", "items_kept", "items_lost", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setInt(4, killerId);
                statement.setString(5, killerName);
                statement.setString(6, killerIp);
                statement.setString(7, toJson(itemsKept.toArray(new Item[0])));
                statement.setString(8, toJson(itemsLost.toArray(new Item[0])));
                statement.setInt(9, World.id);
                statement.setString(10, World.stage.name());
                statement.setString(11, World.type.name());
                statement.execute();
            }
        });
    }

    public static void notifyStaffDead(Player player, long amount) {
        if (player.isAdmin())
            return;
        for (Player p : World.players) {
            if (p != null && p.isStaff()) {
                p.sendMessage(Color.DARK_RED, "[DUPE WARDEN] CRITICAL WARNING! [Dangerous Death]");
                p.sendMessage(Color.ORANGE_RED, player.getName() + " died while having " + NumberFormat.getInstance().format(amount) + " Worth of GP!");
            }
        }
        if (!World.isDev()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("[DUPE WARDEN] CRITICAL WARNING! [Dangerous Death]");
            eb.addField("UserID: ", String.valueOf(player.getUserId()), true);
            eb.addField("Username: ", player.getName(), true);
            eb.addField("UserIP: ", player.getIp(), true);
            eb.addField("User POS: ", "X:" + player.getPosition().getX() + " Y:" + player.getPosition().getY() + " Z:" + player.getPosition().getZ(), true);
            eb.addField("Amount: ", NumberFormat.getInstance().format(amount), true);
            eb.addField("Date: ", formatter.format(date), true);
            eb.setColor(new java.awt.Color(0xB00D03));
            //DiscordConnection.jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
        }
    }

    public static void notifyStaffTrade(Player player, long amount, Player player2, long amount2) {
        if (player.isAdmin())
            return;
        for (Player p : World.players) {
            if (p != null && p.isStaff()) {
                p.sendMessage(Color.DARK_RED, "[DUPE WARDEN] CRITICAL WARNING! [TRADE]");
                p.sendMessage(Color.ORANGE_RED, player.getName() + " & " + player2.getName() + " have just traded each other an amount that doesn't add up");
                p.sendMessage(Color.DARK_RED, player.getName() + " Amount :" + NumberFormat.getInstance().format(amount) + " " + player2.getName() + " Amount :" + NumberFormat.getInstance().format(amount2));
            }
        }
        if (!World.isDev()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("[DUPE WARDEN] CRITICAL WARNING! [TRADE]");
            eb.addField("Player1ID: ", String.valueOf(player.getUserId()), true);
            eb.addField("Player1 Name: ", player.getName(), true);
            eb.addField("Player1IP: ", player.getIp(), true);
            eb.addField("Player1 POS: ", "X:" + player.getPosition().getX() + " Y:" + player.getPosition().getY() + " Z:" + player.getPosition().getZ(), true);
            eb.addField("Player1 Amount: ", NumberFormat.getInstance().format(amount), true);
            eb.addField("Player2ID: ", String.valueOf(player2.getUserId()), true);
            eb.addField("Player2 Name: ", player2.getName(), true);
            eb.addField("Player2IP: ", player2.getIp(), true);
            eb.addField("Player2 POS: ", "X:" + player2.getPosition().getX() + " Y:" + player2.getPosition().getY() + " Z:" + player2.getPosition().getZ(), true);
            eb.addField("Player2 Amount: ", NumberFormat.getInstance().format(amount2), true);
            eb.addField("Date: ", formatter.format(date), true);
            eb.setColor(new java.awt.Color(0xB00D03));
            //DiscordConnection.jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
        }
    }

    public static void notifyStaffDrop(Player player, long amount) {
        if (player.isAdmin())
            return;
        for (Player p : World.players) {
            if (p != null && p.isStaff()) {
                p.sendMessage(Color.DARK_RED, "[DUPE WARDEN] CRITICAL WARNING! [DROP]");
                p.sendMessage(Color.ORANGE_RED, player.getName() + " have just dropped an amount that doesn't add up");
                p.sendMessage(Color.DARK_RED, player.getName() + " Amount :" + NumberFormat.getInstance().format(amount));
            }
        }

        if (!World.isDev()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("[DUPE WARDEN] CRITICAL WARNING! [DROP]");
            eb.addField("UserID: ", String.valueOf(player.getUserId()), true);
            eb.addField("Username: ", player.getName(), true);
            eb.addField("UserIP: ", player.getIp(), true);
            eb.addField("User POS: ", "X:" + player.getPosition().getX() + " Y:" + player.getPosition().getY() + " Z:" + player.getPosition().getZ(), true);
            eb.addField("Amount: ", NumberFormat.getInstance().format(amount), true);
            eb.addField("Date: ", formatter.format(date), true);
            eb.setColor(new java.awt.Color(0xB00D03));
            //DiscordConnection.jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
        }
    }

    public static void notifyStaffPickup(Player player, long amount) {
        if (player.isAdmin())
            return;
        for (Player p : World.players) {
            if (p != null && p.isStaff()) {
                p.sendMessage(Color.DARK_RED, "[DUPE WARDEN] CRITICAL WARNING! [PICKUP]");
                p.sendMessage(Color.ORANGE_RED, player.getName() + " have just picked up an amount that doesn't add up");
                p.sendMessage(Color.DARK_RED, player.getName() + " Amount :" + NumberFormat.getInstance().format(amount));

            }
        }
        if (!World.isDev()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("[DUPE WARDEN] CRITICAL WARNING! [PICKUP]");
            eb.addField("UserID: ", String.valueOf(player.getUserId()), true);
            eb.addField("Username: ", player.getName(), true);
            eb.addField("UserIP: ", player.getIp(), true);
            eb.addField("User POS: ", "X:" + player.getPosition().getX() + " Y:" + player.getPosition().getY() + " Z:" + player.getPosition().getZ(), true);
            eb.addField("Amount: ", NumberFormat.getInstance().format(amount), true);
            eb.addField("Date: ", formatter.format(date), true);
            eb.setColor(new java.awt.Color(0xB00D03));
            //DiscordConnection.jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
        }
    }

    public static void logDrop(int userId, String userName, String userIp, int itemId, int itemAmount, int x, int y, int z) {
//        if (OfflineMode.enabled)
//            return;

        long value = 0;

        if (ItemDef.get(itemId).isNote()) {
            value += ((long) ItemDef.get(itemId).fromNote().highAlchValue * itemAmount);
        } else if (ItemDef.get(itemId).id == 995 || ItemDef.get(itemId).id == 13204 || ItemDef.get(itemId).id == 13307) {
            value += (itemAmount);
        } else {
            value += ((long) ItemDef.get(itemId).highAlchValue * itemAmount);
        }

        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_item_drops", "user_id", "user_name", "user_ip", "item_id", "item_name", "item_amount", "x", "y", "z", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setInt(4, itemId);
                statement.setString(5, ItemDef.get(itemId).name);
                statement.setInt(6, itemAmount);
                statement.setInt(7, x);
                statement.setInt(8, y);
                statement.setInt(9, z);
                statement.setInt(10, World.id);
                statement.setString(11, World.stage.name());
                statement.setString(12, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logPickup(int userId, String userName, String userIp, int itemId, int itemAmount, int x, int y, int z) {
//        if (OfflineMode.enabled)
//            return;

        long value = 0;

        if (ItemDef.get(itemId).isNote()) {
            value += ((long) ItemDef.get(itemId).fromNote().highAlchValue * itemAmount);
        } else if (ItemDef.get(itemId).id == 995 || ItemDef.get(itemId).id == 13204 || ItemDef.get(itemId).id == 13307) {
            value += (itemAmount);
        } else {
            value += ((long) ItemDef.get(itemId).highAlchValue * itemAmount);
        }


        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_item_pickups", "user_id", "user_name", "user_ip", "item_id", "item_name", "item_amount", "x", "y", "z", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setInt(4, itemId);
                statement.setString(5, ItemDef.get(itemId).name);
                statement.setInt(6, itemAmount);
                statement.setInt(7, x);
                statement.setInt(8, y);
                statement.setInt(9, z);
                statement.setInt(10, World.id);
                statement.setString(11, World.stage.name());
                statement.setString(12, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logDropTrade(int takerId, int dropperId, String takerIp, String dropperIp, String takerName, String dropperName, int itemId, int amount, int x, int y, int z, long timeDropped) {
//        if (!OfflineMode.enabled)
//            return;
        if (takerId == dropperId)
            return;
        long value = 0;

        if (ItemDef.get(itemId).isNote()) {
            value += ((long) ItemDef.get(itemId).fromNote().highAlchValue * amount);
        } else if (ItemDef.get(itemId).id == 995 || ItemDef.get(itemId).id == 13204 || ItemDef.get(itemId).id == 13307) {
            value += (amount);
        } else {
            value += ((long) ItemDef.get(itemId).highAlchValue * amount);
        }

        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_drop_trades", "taker_id", "dropper_id", "taker_ip", "dropper_ip",
                    "taker_name", "dropper_name", "id", "amount", "value", "x", "y", "z", "world", "time_dropped"))) {
                statement.setInt(1, takerId);
                statement.setInt(2, dropperId);
                statement.setString(3, takerIp);
                statement.setString(4, dropperIp);
                statement.setString(5, takerName);
                statement.setString(6, dropperName);
                statement.setInt(7, itemId);
                statement.setInt(8, amount);
                statement.setInt(9, ItemDef.get(itemId).protectValue * amount);
                statement.setInt(10, x);
                statement.setInt(11, y);
                statement.setInt(12, z);
                statement.setInt(13, World.id);
                statement.setTimestamp(14, new Timestamp(timeDropped));
                statement.execute();
            }
        });
    }

    public static void logShopBuy(int userId, String userName, String userIp, int itemId, int itemPrice, int buyAmount) {
//        if (OfflineMode.enabled)
//            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_shop_buys", "user_id", "user_name", "user_ip", "item_id", "item_name", "item_price", "buy_amount", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setString(3, userIp);
                statement.setInt(4, itemId);
                statement.setString(5, ItemDef.get(itemId).name);
                statement.setInt(6, itemPrice);
                statement.setInt(7, buyAmount);
                statement.setInt(8, World.id);
                statement.setString(9, World.stage.name());
                statement.setString(10, World.type.name());
                statement.execute();
            }
        });
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

    public static void addOnlinePlayer(int userId, String userName, int worldId, String userIp, boolean isHelper, boolean isModerator, boolean isAdministrator) {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("online_characters", "user_id", "user_name", "world_id", "ip", "is_helper", "is_moderator", "is_administrator"))) {
                statement.setInt(1, userId);
                statement.setString(2, userName);
                statement.setInt(3, worldId);
                statement.setString(4, userIp);
                statement.setBoolean(5, isHelper);
                statement.setBoolean(6, isModerator);
                statement.setBoolean(7, isAdministrator);
                statement.execute();
            }
        });
    }

    public static void removeOnlinePlayer(int userId, int worldId) {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            PreparedStatement statement = null;
            try {
                statement = con.prepareStatement("DELETE FROM online_characters WHERE user_id = ? AND world_id = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setInt(1, userId);
                statement.setInt(2, worldId);
                statement.execute();
            } finally {
                DatabaseUtils.close(statement);
            }
        });
    }

    public static void clearOnlinePlayers(int worldId) {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            PreparedStatement statement = null;
            try {
                statement = con.prepareStatement("DELETE FROM online_characters WHERE world_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setInt(1, worldId);
                statement.execute();
            } finally {
                DatabaseUtils.close(statement);
            }
        });
    }

    public static void updateItems(Player player) {
        if (OfflineMode.enabled)
            return;
        /* first delete all the items */
        Server.gameDb.execute(con -> {
            PreparedStatement statement = null;
            try {
                statement = con.prepareStatement("DELETE FROM items WHERE user_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setInt(1, player.getUserId());
                statement.execute();
            } finally {
                DatabaseUtils.close(statement);
            }

        });
        /* now all them all */
        Server.gameDb.execute(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO items (user_id, id, amount, container, slot) VALUES (?, ?, ?, ?, ?)");
            // Put all the required containers into the table.
            putItems(player.getUserId(), "inventory", player.getInventory(), statement);
            putItems(player.getUserId(), "equipment", player.getEquipment(), statement);
            putItems(player.getUserId(), "bank", player.getBank(), statement);
            putItems(player.getUserId(), "lootingbag", player.getLootingBag(), statement);

            // Mark the batch as done, commit it.
            statement.executeBatch();
        });
    }

    private static void putItems(int characterId, String name, ItemContainerG<? extends Item> container, PreparedStatement insert) throws SQLException {
        for (int slot = 0; slot < container.getItems().length; slot++) {
            Item item = container.get(slot);

            // Only put items into the table that are non-null, obviously :)
            if (item != null) {
                insert.setInt(1, characterId);
                insert.setInt(2, item.getId());
                insert.setInt(3, item.getAmount());
                insert.setString(4, name);
                insert.setInt(5, slot);

                // Mark the batch entry and continue.
                insert.addBatch();
            }
        }
    }


    /**
     * Player logging - Any data we find useful from a player basically.
     */

    public static void logPlayer(Player player) {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_sessions", "user_id", "user_name", "user_ip", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, player.getUserId());
                statement.setString(2, player.getName());
                statement.setString(3, player.getIp());
                statement.setInt(4, World.id);
                statement.setString(5, World.stage.name());
                statement.setString(6, World.type.name());
                statement.execute();
            }
        });
        Server.gameDb.execute(con -> {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.prepareStatement("SELECT * FROM logs_players WHERE user_id = ? AND world_stage = ? AND world_type = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setInt(1, player.getUserId());
                statement.setString(2, World.stage.name());
                statement.setString(3, World.type.name());
                resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    resultSet.moveToInsertRow();
                    updatePlayerLog(player, resultSet);
                    resultSet.insertRow();
                } else {
                    updatePlayerLog(player, resultSet);
                    resultSet.updateRow();
                }
            } finally {
                DatabaseUtils.close(statement, resultSet);
            }
        });
    }


    private static void updatePlayerLog(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateInt("user_id", player.getUserId());
        resultSet.updateString("user_name", player.getName());
        resultSet.updateString("world_stage", World.stage.name());
        resultSet.updateString("world_type", World.type.name());

        long totalWealth = 0;

        //Bank
        List<BankItem> bankItems = new ArrayList<>(player.getBank().getCount());
        if(player.isStaff()) {
            return;
        }
        for (BankItem item : player.getBank().getItems()) {
            if (item != null && item.getId() != Bank.BLANK_ID) {
                bankItems.add(item);
                totalWealth += getWealth(item);
            }
        }
        resultSet.updateString("bank", toJson(bankItems.toArray(new BankItem[0])));

        //Inventory
        List<Item> inventoryItems = new ArrayList<>(player.getInventory().getItems().length);
        for (Item item : player.getInventory().getItems()) {
            inventoryItems.add(item); //we want to show empty slots
            if (item != null)
                totalWealth += getWealth(item);
        }
        resultSet.updateString("inventory", toJson(inventoryItems.toArray(new Item[0])));

        //Equipment
        List<Item> equipmentItems = new ArrayList<>(player.getEquipment().getItems().length);
        for (Item item : player.getEquipment().getItems()) {
            equipmentItems.add(item); //we want to show empty slots
            if (item != null)
                totalWealth += getWealth(item);
        }
        resultSet.updateString("equipment", toJson(equipmentItems.toArray(new Item[0])));

        resultSet.updateLong("total_wealth", totalWealth);
        resultSet.updateLong("total_play_time", player.playTime * Server.tickMs());
        resultSet.updateInt("rights", player.getClientGroupId());
        resultSet.updateInt("icon", player.getClientGroup().clientImgId);
        resultSet.updateInt("ironmode", player.getGameMode().ordinal());
        resultSet.updateInt("x", player.getAbsX());
        resultSet.updateInt("z", player.getAbsY());
        resultSet.updateInt("level", player.getHeight());
        resultSet.updateInt("wilderness_points", player.wildernessPoints);
    }

    public static void logPvMInstance(int ownerId, String typeName, int cost, long timeCreated, long timeDestroyed) {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_instances", "user_id", "instance_type", "instance_cost", "time_created", "time_destroyed", "world_id", "world_stage", "world_type"))) {
                statement.setInt(1, ownerId);
                statement.setString(2, typeName);
                statement.setInt(3, cost);
                statement.setTimestamp(4, new Timestamp(timeCreated));
                statement.setTimestamp(5, new Timestamp(timeDestroyed));
                statement.setInt(6, World.id);
                statement.setString(7, World.stage.name());
                statement.setString(8, World.type.name());
                statement.execute();
            }
        });
    }

    public static void logStaffBountyKill(Player player, Player pKilled) {
        if (OfflineMode.enabled)
            return;
        Server.gameDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement(insertQuery("logs_staff_bounty_kills", "killer_name", "killed_name", "killed_group_id"))) {
                statement.setString(1, player.getName());
                statement.setString(2, pKilled.getName());
                statement.setInt(3, pKilled.getClientGroupId());
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

    private static long getWealth(Item item) {
        if (item.getId() == BLOOD_MONEY) {
            return item.getAmount();
        }
        if (item.getId() == COINS_995) {
            return item.getAmount();
        }
        if (item.getDef().isNote()) {
            ItemDef unnoted;
            unnoted = item.getDef().fromNote();
            long price = unnoted.highAlchValue;
            return item.getAmount() * price;
        }
        long price = item.getDef().highAlchValue;
        return item.getAmount() * price;
    }

}