package io.ruin.model.activities.pvp.unrated;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;

public class BattleGroundsQueue {

    private static int lobbySize() {
        return players.size();
    }

    public TickDelay gameStarts = new TickDelay();

    public BattleGrounds game;

    private static final int MINIMUM_PARTY_SIZE = 2;

    private final BattleGroundsSettings settings;

    private static final ArrayList<Player> players = new ArrayList<>();

    /**
     * Constructs a new {@link BattleGroundsQueue} instance with the respective properties.
     *
     * @param settings
     */
    BattleGroundsQueue(BattleGroundsSettings settings) {
        this.settings = settings;
        gameStarts.delay(1000 * 60 * 6 / 600);
        World.startEvent(e -> {
            while (true) {
                e.delay(5);
                pulse();
            }
        });
    }

    /**
     * Processes all actions upon the player entering the queue, if applicable.
     *
     * @param player
     * @return
     */
    public static void joinQueue(Player player) {
        if (players.stream().anyMatch(p -> p == player)) {
            return;
        }
        if (player.getCombat().getLevel() < 126) {
            player.dialogue(new MessageDialogue("You must be combat level 126 to enter Battlegrounds."));
            return;
        }
        players.add(player);
        updateOverlay();
        player.openInterface(InterfaceType.TARGET_OVERLAY, 822);
        player.getPacketSender().sendString(822, 134, "    Unrated Battlegrounds");
        player.getPacketSender().sendString(822, 135, +lobbySize() + "/10");
        player.sendMessage("You have joined the queue for: 10v10 Unrated Battlegrounds");
    }

    private void pulse() {
        if (lobbySize() >= MINIMUM_PARTY_SIZE) {
            if (lobbySize() < MINIMUM_PARTY_SIZE) {
                gameStarts.delay(1000 * 60 * 2 / 600);
            } else {
                if (game == null) {
                    startGame();
                    gameStarts.delay(1000 * 60 * 6 / 600);
                }
            }
        }
        updateOverlay();
    }

    /**
     * Starts a new {@link BattleGrounds} session and inherits the specified difficulty.
     */
    private void startGame() {
        game = new BattleGrounds(settings);
        game.start(players);
        players.clear();
    }

    private static void updateOverlay() {
        if (lobbySize() >= MINIMUM_PARTY_SIZE) {

        }
        players.forEach(p -> {
            if (lobbySize() < MINIMUM_PARTY_SIZE) {
                int needed = MINIMUM_PARTY_SIZE - lobbySize();
                p.getPacketSender().sendString(822, 135, +lobbySize() + "/10");
            } else {
                p.getPacketSender().sendString(822, 135, +lobbySize() + "/10");
            }
        });
    }

}