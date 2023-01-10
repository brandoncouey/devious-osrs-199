package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalTab;
import io.ruin.model.inter.utils.Config;

public class KDOverlay extends JournalToggle {

    @Override
    public void handle(Player player) {
        Config.PVP_KD_OVERLAY.toggle(player);
        send(player);
    }

    @Override
    public JournalTab.TextField getText() {
        return player -> "Wilderness KD Overlay: " + get(player);
    }

    @Override
    public JournalTab.TabComponent getComponent() {
        return JournalTab.TabComponent.KD_OVERLAY;
    }

    private String get(Player player) {
        boolean enabled = Config.PVP_KD_OVERLAY.get(player) == 1;

        return enabled ? Color.GREEN.wrap("On") : Color.RED.wrap("Off");
    }

}