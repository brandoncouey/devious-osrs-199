package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalTab;
import io.ruin.utility.Misc;

public class BroadcastTournaments extends JournalToggle {

    @Override
    public void handle(Player player) {
        player.broadcastTournaments = !player.broadcastTournaments;
        if (player.broadcastTournaments)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Tournaments."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Tournaments."));
        send(player);
    }

    @Override
    public JournalTab.TextField getText() {
        return player -> "Tournaments: " + Misc.stateOf(player.broadcastTournaments, true);
    }

    @Override
    public JournalTab.TabComponent getComponent() {
        return JournalTab.TabComponent.BC_TOURNAMENTS;
    }

}
