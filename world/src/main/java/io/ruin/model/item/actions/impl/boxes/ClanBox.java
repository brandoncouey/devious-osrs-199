package io.ruin.model.item.actions.impl.boxes;

import io.ruin.model.entity.player.Player;

/**
 * @author Greco
 * @since 12/23/2021
 */
public class ClanBox {

    public static int[] PRAYER_ITEMS = new int[]{21047, 21034, 21079};

    public static int[] CLAN_BOX_ITEMS = new int[]{6570, 4712, 4714, 4736, 4738, 4720, 4716, 4722, 4718, 1712, 11840, 6889, 21791, 22296, 12954, 12791, 19564, 4151, 7462, 10551, 6737};

    public static void giveClanLeaderPrayers(Player player) {
        if (player.getBank().getFreeSlots() < 3) {
            player.sendMessage("<col=8B0000>You need at least 3 bank spaces to take these prayer scrolls.");
            return;
        }

        for (int i = 0; i < PRAYER_ITEMS.length; i++) {
            player.getBank().add(PRAYER_ITEMS[i], 1);
        }

        player.sendMessage("<col=8B0000>Your clan was given an Arcane prayer scroll & a Dexterous prayer scroll. Enjoy your time on Edgeville!");

    }

    public static void addClanBoxItems(Player player) {
        if (player.getBank().getFreeSlots() < 21) {
            player.sendMessage("<col=8B0000>You need at least 21 bank spaces to accept the clan items.");
            return;
        }

        for (int i = 0; i < CLAN_BOX_ITEMS.length; i++) {
            player.getBank().add(CLAN_BOX_ITEMS[i], 1);
        }

        player.sendMessage("<col=8B0000>You are given a clan box and it's items are sent to your bank. Enjoy your time on Edgeville!");
    }

    static {

    }
}