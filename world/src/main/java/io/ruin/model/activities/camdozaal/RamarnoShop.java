package io.ruin.model.activities.camdozaal;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;

/**
 * @author Greco
 * @since 10/24/2021
 */
public class RamarnoShop {

    public static int BARRONITE_SHARDS = 25676;

    public static int INTERFACE_ID = 685;

    public static void openInterface(Player player) {
        player.openInterface(InterfaceType.MAIN, INTERFACE_ID);
        refreshVar(player);
    }

    public static void refreshVar(Player player) {
        if (player.getInventory().contains(BARRONITE_SHARDS)) {
            Config.BARRONITE_SHARDS_TOTAL.set(player, player.getInventory().getAmount(BARRONITE_SHARDS));
        } else {
            Config.BARRONITE_SHARDS_TOTAL.set(player, 0);
        }
    }

    enum Toggle {

        ACTIVE, INACTIVE;

        Toggle() {
        }
    }

    public static void toggle3(Player player) {
        Config.RAMARNO_TOGGLE_3.toggle(player);
    }

    public static void toggle4(Player player) {
        Config.RAMARNO_TOGGLE_4.toggle(player);
    }

    public static void toggle6(Player player) {
        Config.RAMARNO_TOGGLE_6.toggle(player);
    }

    public static void toggle8(Player player) {
        Config.RAMARNO_TOGGLE_8.toggle(player);
    }

    public static void toggle10(Player player) {
        Config.RAMARNO_TOGGLE_10.toggle(player);
    }

    public static void toggle12(Player player) {
        Config.RAMARNO_TOGGLE_12.toggle(player);
    }

    public static void setTimer12(Player player, int timeRemaining) {
        Config.RAMARNO_SHOP_TIMER_12.set(player, timeRemaining);
    }

    static {
        try {
            InterfaceHandler.register(INTERFACE_ID, h -> {
                h.actions[3] = (SimpleAction) p -> toggle3(p);
                h.actions[4] = (SimpleAction) p -> toggle4(p);
                h.actions[6] = (SimpleAction) p -> toggle6(p);
                h.actions[8] = (SimpleAction) p -> toggle8(p);
                h.actions[10] = (SimpleAction) p -> toggle10(p);
                h.actions[12] = (SimpleAction) p -> toggle12(p);
            });
        } catch (Throwable e) {
            System.out.println(e);
        }
    }

}

