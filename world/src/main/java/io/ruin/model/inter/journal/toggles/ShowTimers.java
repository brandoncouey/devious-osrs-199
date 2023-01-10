package io.ruin.model.inter.journal.toggles;

import io.ruin.model.entity.player.Player;
import io.ruin.utility.Misc;

import static io.ruin.model.inter.journal.JournalTab.TabComponent;
import static io.ruin.model.inter.journal.JournalTab.TextField;

public class ShowTimers extends JournalToggle {

    @Override
    public void handle(Player player) {
        player.showTimers = !player.showTimers;
        send(player);
    }

    @Override
    public TextField getText() {
        return player -> "Show Timers: " + Misc.stateOf(player.showTimers, true);
    }

    @Override
    public TabComponent getComponent() {
        return TabComponent.TIMER_TOGGLE;
    }

    @Override
    public void onSend(Player player) {
        player.getPacketSender().sendVarp(20004, player.showTimers ? 1 : 0);
    }

}
