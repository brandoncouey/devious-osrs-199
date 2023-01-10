package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class HarmonisedStaff {

    private static final int HARMONISED_ORB = 24511;
    private static final int NIGHTMARE_STAFF = 24422;
    private static final int HARMONISED_STAFF = 24422;

    static {
        ItemAction.registerInventory(HARMONISED_STAFF, "dismantle", (player, item) -> dismantle(player, item, NIGHTMARE_STAFF, HARMONISED_ORB));

    }

    private static void dismantle(Player player, Item item, int resultOne, int resultTwo) {
        if (player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough free space to do this.");
            return;
        }

        item.remove();
        player.getInventory().add(resultOne, 1);
        player.getInventory().add(resultTwo, 1);
    }

}

