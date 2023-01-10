package io.ruin.model.item.actions.impl;

import io.ruin.model.item.actions.ItemItemAction;

public class EnhancedKey {

    private static final int CRYSTAL_LOOP = 23962;
    private static final int CRYSTAL_TOOTH = 989;
    private static final int CRYSTAL_KEY = 23951;

    static {
        ItemItemAction.register(CRYSTAL_LOOP, CRYSTAL_TOOTH, (player, primary, secondary) -> {
            if (player.getInventory().count(23962)<20){
                player.sendMessage("You must have atleast 20 shards to perform this action");
                return;
            }
            player.getInventory().remove(CRYSTAL_LOOP, 20);
            player.getInventory().remove(CRYSTAL_TOOTH, 1);
            player.getInventory().add(CRYSTAL_KEY, 1);
            player.sendMessage("You join the ingredients to create an Enhanced Crystal Key.");

        });
    }

}
