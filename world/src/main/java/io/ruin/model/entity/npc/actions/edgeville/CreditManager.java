package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.shop.ShopManager;

public class CreditManager {

    static {
        NPCAction.register(2108, "Donator-Store", (player, npc) -> {
            if (player.getGameMode().isIronMan() || player.getGameMode().isHardcoreIronman() || player.getGameMode().isUltimateIronman() ||
                    player.getGameMode().isHardcoreGroupIronman() || player.getGameMode().isGroupIronman()) {
                ShopManager.openIfExists(player, "IRONMANDONATOR");
            } else {
                ShopManager.openIfExists(player, "1woleAXnpl2ZwTVj7hmvcHvgmSRiYGX3FHtr");
            }
        });
    }

    /**
     * Misc
     */
    public static SecondaryGroup getGroup(Player player) {
        if (player.storeAmountSpent >= 1000)
            return SecondaryGroup.DIAMOND;
        if (player.storeAmountSpent >= 500)
            return SecondaryGroup.RUBY;
        if (player.storeAmountSpent >= 250)
            return SecondaryGroup.EMERALD;
        if (player.storeAmountSpent >= 150)
            return SecondaryGroup.SAPPHIRE;
        if (player.storeAmountSpent >= 100)
            return SecondaryGroup.RED_TOPAZ;
        if (player.storeAmountSpent >= 50)
            return SecondaryGroup.JADE;
        if (player.storeAmountSpent >= 10)
            return SecondaryGroup.OPAL;
        return null;
    }

}