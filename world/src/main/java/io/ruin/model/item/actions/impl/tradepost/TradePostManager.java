package io.ruin.model.item.actions.impl.tradepost;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradePostManager {

    /**
     * The containers.
     */
    public static Map<String, TradePostContainer> CONTAINERS = new HashMap<>();

    /**
     * Loads the containers.
     */
    public static void load() {
        Server.siteDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;

            try {
                statement = connection.prepareStatement("SELECT * FROM tp_containers");
                rs = statement.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("player_name");
                    int slot = rs.getInt("slot");
                    int itemId = rs.getInt("item_id");
                    int amount = rs.getInt("item_amount");
                    int price = rs.getInt("price");
                    Timestamp ts = rs.getTimestamp("timestamp");
                    long adEnd = rs.getLong("ad_end");

                    TradePostContainer container = getContainer(username);

                    if (itemId != -1) {
                        TradePostOffer offer = new TradePostOffer(username, new Item(itemId, amount), price, ts.getTime());
                        offer.setAdEnd(adEnd);
                        container.set(slot, offer);
                    } else {
                        container.set(slot, null);
                    }

                    Server.siteDb.execute(connection1 -> {
                        PreparedStatement statement1 = null;
                        ResultSet rs1 = null;

                        try {
                            statement1 = connection1.prepareStatement("SELECT amount FROM tp_collect WHERE player_name = ? LIMIT 1", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            statement1.setString(1, username);
                            rs1 = statement1.executeQuery();
                            while (rs1.next()) {
                                container.setCoffer(rs1.getLong("amount"));
                            }
                        } finally {
                            DatabaseUtils.close(statement1, rs1);
                        }
                    });
                }

                System.out.println("Loaded: " + CONTAINERS.size() + " trading post containers!");
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    /**
     * Get's the player's history.
     *
     * @param player The player.
     * @return The history.
     */
    public static List<HistoryEntry> getHistory(Player player) {
        return getContainer(player).getHistory();
    }

    /**
     * Loads the history for the player.
     *
     * @param player     The player.
     * @param onComplete The action on complete.
     */
    public static void loadHistory(Player player, Runnable onComplete) {
        Server.siteDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;

            try {
                TradePostContainer container = getContainer(player);
                List<HistoryEntry> history = container.getHistory();
                statement = connection.prepareStatement("SELECT * FROM tp_history WHERE player_name = " + player.getName() + " ORDER BY timestamp");
                rs = statement.executeQuery();
                while (rs.next()) {
                    int playerId = rs.getInt("player_name");
                    int itemId = rs.getInt("item_id");
                    int amount = rs.getInt("item_amount");
                    int price = rs.getInt("price");
                    int type = rs.getInt("type");

                    history.add(new HistoryEntry(type, new Item(itemId, amount), price));
                }

                onComplete.run();
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }

    /**
     * Saves the player's container.
     *
     * @param container The container.
     */
    public static void save(TradePostContainer container) {
        Server.siteDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement("INSERT INTO tp_containers (player_name, slot, item_id, item_amount, price, timestamp, ad_end) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY update player_name=VALUES(player_name), item_id=VALUES(item_id), item_amount=VALUES(item_amount), price=VALUES(price),timestamp=VALUES(timestamp),ad_end=VALUES(ad_end)")) {

                TradePostOffer[] offers = container.getOffers();

                for (int i = 0; i < offers.length; i++) {
                    statement.setString(1, container.getUsername());
                    statement.setInt(2, i);
                    statement.setInt(3, offers[i] == null ? -1 : offers[i].getItem().getId());
                    statement.setInt(4, offers[i] == null ? -1 : offers[i].getItem().getAmount());
                    statement.setInt(5, offers[i] == null ? -1 : offers[i].getPricePerItem());
                    statement.setTimestamp(6, offers[i] == null ? null : new Timestamp(offers[i].getTimestamp()));
                    statement.setLong(7, offers[i] == null ? 0 : offers[i].getAdEnd());
                    statement.addBatch();
                }

                statement.executeBatch();
            }
        });

        Server.siteDb.execute(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tp_collect (player_name, amount) VALUES (?, ?) ON DUPLICATE KEY UPDATE player_name = VALUES(player_name), amount = VALUES(amount)");
            statement.setString(1, container.getUsername());
            statement.setLong(2, container.getCoffer());
            statement.executeUpdate();
        });
    }

    public static void saveHistory(int offerType, Item item, int price, TradePostContainer container) {
        List<HistoryEntry> history = container.getHistory();
        history.add(new HistoryEntry(offerType, item, price));
        Server.siteDb.execute(con -> {
            try (PreparedStatement statement = con.prepareStatement("INSERT INTO tp_history (player_name, item_id, item_amount, price, type) VALUES (?, ?, ?, ?, ?)")) {
                statement.setString(1, container.getUsername());
                statement.setInt(2, item.getId());
                statement.setInt(3, item.getAmount());
                statement.setInt(4, price);
                statement.setInt(5, offerType);
                statement.executeUpdate();
            }
        });
    }

    public static TradePostContainer forId(String name) {
        return CONTAINERS.get(name);
    }

    /**
     * Returns the container for the specified player.
     *
     * @param player The player.
     * @return The container.
     */
    public static TradePostContainer getContainer(Player player) {
        return getContainer(player.getName());
    }

    /**
     * Returns the container for the specified user id.
     *
     * @return The container.
     */
    private static TradePostContainer getContainer(String username) {
        CONTAINERS.putIfAbsent(username, new TradePostContainer(username));

        return CONTAINERS.get(username);
    }

    static {
        load();
    }

}
