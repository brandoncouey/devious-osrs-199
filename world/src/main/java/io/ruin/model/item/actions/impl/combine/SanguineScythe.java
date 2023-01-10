package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class SanguineScythe {

    private static final int SANGUINE_DUST = 25744;
    private static final int SCYTHE = 22486;
    private static final int SANGSCYTHE = 25741;
    private static final int HOLYSCYTHE = 25738;
    private static final int HOLYKIT = 25742;
    private static final int RAPIER = 22324;
    private static final int HOLYRAPIER = 25734;

    static {
        ItemAction.registerInventory(SANGSCYTHE, "dismantle", (player, item) -> dismantle(player, item, SANGUINE_DUST, SCYTHE));
        ItemAction.registerInventory(HOLYSCYTHE, "dismantle", (player, item) -> dismantle(player, item, HOLYKIT, SCYTHE));
        ItemAction.registerInventory(HOLYRAPIER, "dismantle", (player, item) -> dismantle(player, item, HOLYKIT, RAPIER));

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
