package io.ruin.model.inter.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;

@FunctionalInterface
public interface OptionAction extends InterfaceAction {

    void handle(Player player, int option);

    @Override
    default void handleClick(Player player, int childId, int option, int slot, int itemId) {
        handle(player, option);
    }

}
