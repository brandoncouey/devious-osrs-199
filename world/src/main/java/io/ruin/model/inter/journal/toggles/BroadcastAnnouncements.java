package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalTab;
import io.ruin.utility.Misc;

public class BroadcastAnnouncements extends JournalToggle {

    @Override
    public void handle(Player player) {
        player.broadcastAnnouncements = !player.broadcastAnnouncements;
        if (player.broadcastAnnouncements)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Announcements."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Announcements."));
        send(player);
    }

    @Override
    public JournalTab.TextField getText() {
        return player -> "Announcements: " + Misc.stateOf(player.broadcastAnnouncements, true);
    }

    @Override
    public JournalTab.TabComponent getComponent() {
        return JournalTab.TabComponent.BC_ANNOUNCEMENTS;
    }

}
