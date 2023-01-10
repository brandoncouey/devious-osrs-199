package io.ruin.model.activities.afkzone;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.TabStats;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class afkHerbs {

    private static final int herbpatch = 8143;

    static {
        ObjectAction.register(herbpatch, "pick", (player, obj) -> herbPatch(player));
        ObjectAction.register(herbpatch, "inspect", (player, obj) -> {
            player.dialogue(new MessageDialogue("A wonderful AFK Herb patch."));
        });
        ObjectAction.register(herbpatch, "guide", ((player, obj) -> {
            TabStats.openGuide(player, StatType.Farming, 6);
        }));
    }

    private static void herbPatch(Player p) {
        p.startEvent(e -> {
            while (true) {
                if (p.getStats().get(StatType.Farming).currentLevel >= 75 && p.getStats().get(StatType.Herblore).currentLevel >= 75) {
                    p.sendMessage("Your level as exceeded the allowed starter level of 75.");
                    return;
                }
                p.animate(2282);
                if (Random.rollPercent(10)) {
                    p.getInventory().addOrDrop(13307, 2);
                }
                Item rolled = rollRegular();
                p.getInventory().add(rolled);
                p.getStats().addXp(StatType.Farming, 3.0, true);
                p.getStats().addXp(StatType.Herblore, 3.0, true);
                e.delay(3);
            }
        });
    }

    private static final LootTable regularTable = new LootTable()
            .addTable(100,
                    new LootItem(250, 1, 35), // Guam leaf
                    new LootItem(252, 1, 30), // Marrentill leaf
                    new LootItem(254, 1, 27), // Tarromin leaf
                    new LootItem(256, 1, 25), // Harralander leaf
                    new LootItem(258, 1, 15), // Ranarr leaf

                    new LootItem(260, 1, 15), // Irit leaf
                    new LootItem(262, 1, 15), // Avatoe leaf
                    new LootItem(264, 1, 15), // Kwuarm leaf
                    new LootItem(266, 1, 15), // Cadantine leaf
                    new LootItem(268, 1, 15), // Dwarf leaf
                    new LootItem(270, 1, 10)  // Torstol leaf
            );

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

}
