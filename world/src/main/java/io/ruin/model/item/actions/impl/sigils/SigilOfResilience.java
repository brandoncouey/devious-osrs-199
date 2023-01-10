package io.ruin.model.item.actions.impl.sigils;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class SigilOfResilience {

    //Sigil of Resilience
    public static final int attuned = 25990;
    public static final int unattuned = 25991;
public static void attuneSigil(Player player, Item item) {
    if (Config.SIGIL_SLOT_ONE.get(player) >= 1) {
        player.sendMessage("Please unattune your current sigil before trying to attune.");
    } else {
        item.setId(attuned);
        player.SOFR = true;
        player.sendMessage("you attune your sigil.");
        Config.SIGIL_SLOT_ONE.set(player, 53);
        player.animate(712);
        player.graphics(1970, 75, 0);
    }
}
    static {
        ItemAction.registerInventory(attuned, "unattune", (player, item) -> {
            item.setId(unattuned);
            player.SOFR = false;
            player.sendMessage("You unattune your sigil.");
            Config.SIGIL_SLOT_ONE.set(player, 0);
            player.animate(712);
            player.graphics(1970, 75, 0);
        });
// public static final Config SIGIL_SLOT_ONE = varpbit(13019, true);
        ItemAction.registerInventory(unattuned, 1, SigilOfResilience::attuneSigil);

    }
}