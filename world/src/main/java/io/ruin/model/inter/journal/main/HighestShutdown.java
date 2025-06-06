package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class HighestShutdown extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Highest Shutdown", player.highestShutdown, Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.forceText("!" + Color.ORANGE_RED.wrap("HIGHEST SHUTDOWN:") + " " + player.highestShutdown);
    }

}