package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class VolatileStaff {

    private static final int VOLATILE_ORB = 24511;
    private static final int NIGHTMARE_STAFF = 24422;
    private static final int VOLATILE_STAFF = 24423;

    static {
        ItemAction.registerInventory(VOLATILE_STAFF, "dismantle", (player, item) -> dismantle(player, item, NIGHTMARE_STAFF, VOLATILE_ORB));
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

