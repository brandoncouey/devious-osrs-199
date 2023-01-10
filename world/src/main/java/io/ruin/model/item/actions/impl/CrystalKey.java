package io.ruin.model.item.actions.impl;

import io.ruin.model.item.actions.ItemItemAction;

public class CrystalKey {

    private static final int CRYSTAL_LOOP = 987;
    private static final int CRYSTAL_TOOTH = 985;
    private static final int CRYSTAL_KEY = 989;

    static {
        ItemItemAction.register(CRYSTAL_LOOP, CRYSTAL_TOOTH, (player, primary, secondary) -> {
            primary.remove(1);
            secondary.remove(1);
            player.getInventory().add(CRYSTAL_KEY, 1);
            player.sendMessage("You join the two halves of the key together.");
        });
    }

}
