package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;

public class PhoenixPet {

    /**
     * Phoenix
     */
    private static final int PHOENIX = 20693;

    /**
     * Fire lighters
     */
    private static final int GREEN_FIRE_LIGHTER = 7330;
    private static final int BLUE_FIRE_LIGHTER = 7331;
    private static final int PURPLE_FIRE_LIGHTER = 10326;
    private static final int WHITE_FIRE_LIGHTER = 10327;

    /**
     * Colored Phoenixs
     */
    private static final int GREEN_PHOENIX = 24483;
    private static final int BLUE_PHOENIX = 24484;
    private static final int PURPLE_PHOENIX = 24486;
    private static final int WHITE_PHOENIX = 24485;

    private static void combine(Player player, Item itemOne, Item itemTwo, int result) {
        itemOne.remove();
        itemTwo.remove();
        player.getInventory().add(result, 1);
    }


    static {
        /**
         * Combining
         */
        ItemItemAction.register(PHOENIX, GREEN_FIRE_LIGHTER, (player, primary, secondary) -> combine(player, primary, secondary, GREEN_PHOENIX));
        ItemItemAction.register(PHOENIX, BLUE_FIRE_LIGHTER, (player, primary, secondary) -> combine(player, primary, secondary, BLUE_PHOENIX));
        ItemItemAction.register(PHOENIX, PURPLE_FIRE_LIGHTER, (player, primary, secondary) -> combine(player, primary, secondary, PURPLE_PHOENIX));
        ItemItemAction.register(PHOENIX, WHITE_FIRE_LIGHTER, (player, primary, secondary) -> combine(player, primary, secondary, WHITE_PHOENIX));

    }
}
