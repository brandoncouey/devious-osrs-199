package io.ruin.model.item.actions.impl.boxes;


import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;

/**
 * @author Greco
 * @since 12/29/2021
 */
public class LumberjackBox {

    public static int[] LUMBERJACK_SET = new int[]{10933, 10941, 10939, 10940};

    public static void giveLumberjackSet(Player player) {
        if (player.getBank().getFreeSlots() < 4) {
            player.sendMessage("<col=8B0000>You need at least 4 bank spaces to take these items.");
            return;
        }


        for (int i = 0; i < LUMBERJACK_SET.length; i++) {
            player.getBank().add(LUMBERJACK_SET[i], 1);
            player.getCollectionLog().add(LUMBERJACK_SET[i], 1);
            player.sendMessage("<col=8B0000>" + ItemDef.get(LUMBERJACK_SET[i]).name + " was added to your Bank and Collection log.");
        }

    }

    static {

    }


}
