package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.cache.ItemID.COINS_995;

public class BrimstoneChest {


    private static final LootTable BRIMSTONE_CHEST = new LootTable().addTable(1,

            new LootItem(COINS_995, 75000, 1000),
            new LootItem(1618, 25, 90),
            new LootItem(1620, 25, 90),
            new LootItem(454, 400, 85),
            new LootItem(445, 100, 80),
            new LootItem(11237, 150, 70),
            new LootItem(441, 350, 65),
            new LootItem(1164, 3, 50),
            new LootItem(1128, 2, 48),
            new LootItem(1080, 1, 46),
            new LootItem(360, 200, 45),
            new LootItem(378, 200, 42),
            new LootItem(372, 200, 40),
            new LootItem(384, 200, 35),
            new LootItem(396, 200, 35),
            new LootItem(390, 200, 30),
            new LootItem(452, 10, 25),
            new LootItem(2354, 300, 15),
            new LootItem(1514, 150, 12),
            new LootItem(11232, 85, 10),
            new LootItem(5289, 2, 9),
            new LootItem(5316, 2, 9),
            new LootItem(22869, 2, 8),
            new LootItem(22877, 2, 8),
            new LootItem(22871, 1, 8),
            new LootItem(5304, 3, 7),
            new LootItem(5300, 3, 7),
            new LootItem(5295, 3, 7),
            new LootItem(7937, 3000, 5),
            new LootItem(22731, 1, 1),
            new LootItem(23047, 1, 1),
            new LootItem(23050, 1, 1),
            new LootItem(23053, 1, 1),
            new LootItem(23056, 1, 1),
            new LootItem(23059, 1, 1)
    );

    static {
        ObjectAction.register(34660, "unlock", (player, obj) -> {
            Item crystalKey = player.getInventory().findItem(23083);
            if (crystalKey == null) {
                player.sendFilteredMessage("You need a brimstone key to open this chest.");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                crystalKey.remove(1);
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(34661);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                Item reward = BRIMSTONE_CHEST.rollItem();
                player.getInventory().addOrDrop(reward);
                event.delay(1);
                player.unlock();
            });
        });
    }
}
