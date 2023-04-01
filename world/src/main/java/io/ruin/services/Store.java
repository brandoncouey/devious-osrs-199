package io.ruin.services;

import io.ruin.api.utils.XenPost;
import io.ruin.cache.Color;
import io.ruin.cache.NpcID;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.utility.Broadcast;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Store implements Runnable {


    public static final String HOST = "147.135.36.141"; // website ip address
    public static final String USER = "maddox";
    public static final String PASS = "MyFam25!";
    public static final String DATABASE = "store";

    private final Player player;
    private Connection conn;
    private Statement stmt;

    /**
     * The constructor
     *
     * @param player
     */
    public Store(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (!connect(HOST, DATABASE, USER, PASS)) {
                return;
            }

            String name = player.getName().replace("_", " ");
            ResultSet rs = executeQuery("SELECT * FROM payments WHERE player_name='" + name + "' AND status='Completed' AND claimed=0");

            while (rs.next()) {
                int item_number = rs.getInt("item_number");
                int quantity = rs.getInt("quantity");
                int item_id = rs.getInt("quantity");
                int paid = rs.getInt("paid");
                switch (item_number) {// add products according to their ID in the ACP
                    case 1027: // 10 bond
                        player.getInventory().add(30250, quantity);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x 10$ Bond!");
                        break;
                    case 1028: // 25 bond
                        player.getInventory().add(30249, quantity);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x 25$ Bond!");
                        break;
                    case 1029: // 50 bond
                        player.getInventory().add(30251, quantity);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x 50$ Bond!");
                        break;
                    case 1030: // 100 bond
                        player.getInventory().add(30252, quantity);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x 100$ Bond!");
                        break;
                    case 1079: // 250 bond
                        player.getInventory().add(30253, quantity);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x 250$ Bond!");
                        break;
                    case 1080: // 500 bond
                        player.getInventory().add(30254, quantity);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x 500$ Bond!");
                        break;
                    case 1088: // Battle Pass
                        player.getBattlePass().purchaseBattlePass();
                        player.sendMessage("Thank you for donating, Your Battle Pass has been activated!");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased The Battle Pass!");
                        break;
                    case 1083: // 10 bond
                        player.getInventory().add(30307, paid);
                        player.amountDonated += 1;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x Membership Tokens!");
                        break;
                    case 1084: // scratch card
                        player.getInventory().add(30294, quantity);
                        player.getInventory().add(30307, paid);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased " + quantity + "x Scratch Cards!");
                        break;
                    case 1085: // scratch card
                        player.getInventory().add(6806, quantity);
                        player.getInventory().add(30307, paid);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1086: // scratch card
                        player.getInventory().add(6807, quantity);
                        player.getInventory().add(30307, paid);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1087: // scratch card //1089
                        player.getInventory().add(6808, quantity);
                        player.getInventory().add(30307, paid);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1089:
                        player.getBank().add(290, quantity);
                        player.getBank().add(30307, paid);
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1090:
                        player.getInventory().add(30308, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1091:
                        player.getInventory().add(25734, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1092:
                        player.getInventory().add(25733, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1093:
                        player.getInventory().add(25738, quantity);

                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1094:
                        player.getInventory().add(24517, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1095:
                        player.getInventory().add(24511, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1096:
                        player.getInventory().add(24514, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1097:
                        player.getInventory().add(23971, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1098:
                        player.getInventory().add(23975, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1099:
                        player.getInventory().add(23979, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1100:
                        player.getInventory().add(25741, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1101:
                        player.getInventory().add(24417, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1102:
                        player.getInventory().add(24419, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1103:
                        player.getInventory().add(24420, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1104:
                        player.getInventory().add(24421, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1105:
                        player.getInventory().add(24664, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1106:
                        player.getInventory().add(24666, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1107:
                        player.getInventory().add(24668, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");

                    case 1108:
                        player.getInventory().add(25471, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1115:
                        player.getInventory().add(21295, quantity);
                        player.getInventory().add(30307, paid);
                        player.amountDonated += paid;
                        player.sendMessage("Thank you for donating, Your items have been placed in your Inventory");
                        break;
                    case 1117:
                        player.amountDonated += paid;
                        player.getBattlePass().purchaseBattlePass();
                        player.getInventory().add(30307, paid);
                        player.sendMessage("Thank you for donating, Your Battle Pass has been activated!");
                        Broadcast.WORLD.sendNews("[News]" + player.getName() + " has purchased The Battle Pass!");
                        break;

                }
                amount -= paid;
                Broadcast.WORLD.sendNews(player.getName() + " Just Donated to Devious!, $" + amount + " left until Donator Boss Spawns!");
                if (paid > 100) {
                    for (int i = 0; i < (paid / 100); i++) {
                        World.startEvent(e -> {
                            new NPC(NpcID.AVATAR_OF_DESTRUCTION_10532).spawn(3811, 2870, 1, Direction.WEST, 1).getCombat().setAllowRespawn(false);
                            Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[DONATION BOSS] ") + "Avatar of destruction Has just spawned! use ::donboss to get there!</shad>");
                            Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[DONATION BOSS] ") + "Multiple donation bosses pending! 5 minutes until the next one spawns!</shad>");
                            amount = 100;
                            e.delay(500);
                        });
                    }
                } else {
                    if (amount <= 0) {
                        new NPC(NpcID.AVATAR_OF_DESTRUCTION_10532).spawn(3811, 2870, 1, Direction.WEST, 1).getCombat().setAllowRespawn(false);
                        Broadcast.GLOBAL.sendNews("<shad=000000>" + Color.BABY_BLUE.wrap("[DONATION BOSS] ") + "Avatar of destruction Has just spawned! use ::donboss to get there!</shad>");
                        amount = 100;
                    }

                    rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                    rs.updateRow();
                }
            }

            destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeForumsGroup(Player player, int mode) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", "donation");
            map.put("groupId", mode);
            XenPost.post("add_group", map);
        });
    }

    /**
     * @param host     the host ip address or url
     * @param database the name of the database
     * @param user     the user attached to the database
     * @param pass     the users password
     * @return true if connected
     */
    public boolean connect(String host, String database, String user, String pass) {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, pass);
            return true;
        } catch (SQLException e) {
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    /**
     * Disconnects from the MySQL server and destroy the connection
     * and statement instances
     */
    public void destroy() {
        try {
            conn.close();
            conn = null;
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes an update query on the database
     *
     * @param query
     * @see {@link Statement#executeUpdate}
     */
    public int executeUpdate(String query) {
        try {
            this.stmt = this.conn.createStatement(1005, 1008);
            int results = stmt.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Executres a query on the database
     *
     * @param query
     * @return the results, never null
     * @see {@link Statement#executeQuery(String)}
     */
    public ResultSet executeQuery(String query) {
        try {
            this.stmt = this.conn.createStatement(1005, 1008);
            ResultSet results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static int amount = 100;

    private static void spawnDonationboss() {
        new NPC(15032).spawn(3811, 2870, 1);
    }

}
