package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalTab;
import io.ruin.model.inter.utils.Config;

public class BountyHunterStreaks extends JournalToggle {

    @Override
    public void handle(Player player) {
        Config.BOUNTY_HUNTER_RECORD_OVERLAY.toggle(player);
        send(player);
    }

    @Override
    public JournalTab.TextField getText() {
        return player -> "Streaks Overlay: " + get(player);
    }

    @Override
    public JournalTab.TabComponent getComponent() {
        return JournalTab.TabComponent.BH_STREAKS;
    }

    private String get(Player player) {
        boolean enabled = Config.BOUNTY_HUNTER_RECORD_OVERLAY.get(player) == 0;

        if (enabled) {
            return Color.GREEN.wrap("On");
        }

        return Color.RED.wrap("Off");
    }

}