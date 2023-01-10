package io.ruin.model.item.actions.impl.sigils;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigilOfTheRuthlessRanger {
    //Sigil of the Ruthless Ranger
    public static final int attuned = 26071;
    public static final int unattuned = 26072;

    //public static boolean crippled = false; this is for Player Attributes
    // 50% bonus Ranged XP while active!
    // 10% chance to cripple the target's movement
    // The effect deals 5 damage over the next 6 seconds and drains a total of 30 run energy in that time.

    public static void attuneSigil(Player player, Item item) {
        if (Config.SIGIL_SLOT_TWO.get(player) >= 1) {
            player.sendMessage("Please unattune your current sigil before trying to attune.");
        } else {
            item.setId(attuned);
            player.SOTRR = true;
            player.sendMessage("you attune your sigil.");
            Config.SIGIL_SLOT_TWO.set(player, 27);
            player.animate(712);
            player.graphics(1970, 65, 0);
        }
    }

    static {
        ItemAction.registerInventory(attuned, "unattune", (player, item) -> {
            item.setId(unattuned);
            player.SOTRR = false;
            player.sendMessage("You unattune your sigil.");
            Config.SIGIL_SLOT_TWO.set(player, 0);
            player.animate(712);
            player.graphics(1970, 65, 0);
        });

        ItemAction.registerInventory(unattuned, 1, SigilOfTheRuthlessRanger::attuneSigil);

    }
}
