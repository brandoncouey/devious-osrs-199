package io.ruin.model.item.actions.impl.boxes;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.stat.StatType;

/**
 * @author Greco
 * @since 01/11/2021
 */
public class SlayerBox {

    public static int SLAYER_BOX = -1;

    private static final LootTable tableEasy = new LootTable().addTable(1,
            new LootItem(249, 1, 25)
    );

    private static final LootTable tableMedium = new LootTable().addTable(1,
            new LootItem(249, 1, 25)
    );

    private static final LootTable tableHard = new LootTable().addTable(1,
            new LootItem(249, 1, 25)
    );

    public static void openEasy(Player player) {

        int slayBox = tableEasy.rollItem().getId();

        player.getInventory().add(slayBox, 1);

        //    slayerBox.remove();

        player.sendMessage("You open the Slayer box (easy) and find " + ItemDef.get(slayBox).name + ".");

    }

    public static void openMedium(Player player) {

        int slayBox = tableMedium.rollItem().getId();

        player.getInventory().add(slayBox, 1);

        //   slayerBox.remove();

        player.sendMessage("You open the Slayer box (medium) and find " + ItemDef.get(slayBox).name + ".");

    }

    public static void openHard(Player player) {

        int slayBox = tableHard.rollItem().getId();

        player.getInventory().add(slayBox, 1);

        // slayerBox.remove();

        player.sendMessage("You open the Slayer box (hard) and find " + ItemDef.get(slayBox).name + ".");

    }

    public static void addBoxWhileTrainingSlayer(Player player) {

        if (Random.get(1, 50) == 50) {
            if (player.getStats().check(StatType.Slayer, 50)) {
                openEasy(player);
            } else if (player.getStats().check(StatType.Slayer, 70)) {
                openMedium(player);
            } else if (player.getStats().check(StatType.Slayer, 90)) {
                openHard(player);
            }
        }
    }

    static {

//        ItemAction.registerInventory(SLAYER_BOX, 2, SlayerBox::open);

    }
}
