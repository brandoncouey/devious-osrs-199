package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalTab;

/**
 * @author ReverendDread on 7/21/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class ExperienceLock extends JournalToggle {

    @Override
    public void handle(Player player) {
        player.experienceLock = !player.experienceLock;
        player.sendMessage("Your experience has been " + (player.experienceLock ? "locked" : "unlocked") + ".");

    }

    @Override
    public JournalTab.TextField getText() {
        return player -> "EXP. Lock: " + get(player);
    }

    @Override
    public JournalTab.TabComponent getComponent() {
        return JournalTab.TabComponent.XP_MODE;
    }

    private String get(Player player) {
        boolean enabled = player.experienceLock = true;

        if (enabled) {
            return Color.GREEN.wrap("On");
        }

        return Color.RED.wrap("Off");
    }

}
