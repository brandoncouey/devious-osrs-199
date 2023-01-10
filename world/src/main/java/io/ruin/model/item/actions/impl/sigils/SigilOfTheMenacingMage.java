package io.ruin.model.item.actions.impl.sigils;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class SigilOfTheMenacingMage {

    //Sigil of the Menacing Mage
    public static final int attuned = 26077;
    public static final int unattuned = 26078;

    public static void attuneSigil(Player player, Item item) {
        if (Config.SIGIL_SLOT_TWO.get(player) >= 1) {
            player.sendMessage("Please unattune your current sigil before trying to attune.");
        } else {
            item.setId(attuned);
            player.SOTMM = true;
            player.sendMessage("you attune your sigil.");
            Config.SIGIL_SLOT_TWO.set(player, 29);
            player.animate(712);
            player.graphics(1970, 75, 0);
        }
    }
    static {
        ItemAction.registerInventory(attuned, "unattune", (player, item) -> {
            item.setId(unattuned);
            player.SOTMM = false;
            player.sendMessage("You unattune your sigil.");
            Config.SIGIL_SLOT_TWO.set(player, 0);
            player.animate(712);
            player.graphics(1970, 75, 0);
        });

        ItemAction.registerInventory(unattuned, 1, SigilOfTheMenacingMage::attuneSigil);

    }
}
