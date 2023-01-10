package io.ruin.model.activities.afkzone;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TeleportConstants;

public class afkzone {

    private static final int Hespori = 11192;
    private static final int Herbi = 7785;
    private static final int Zalcano = 9050;
    private static final int SpiritPool = 10571;
    private static final int CrystalTree = 34918;
    private static final int Dummy = 2038;

    static {
        ObjectAction.register(34499, "enter", (player, obj) -> {
            player.getMovement().teleport(TeleportConstants.AFK_ZONE);
        });
        ObjectAction.register(CrystalTree, 1, (player, obj) -> CrystalTree(player));
        NPCAction.register(Hespori, 2, (player, npc) -> Hespori(player));
        NPCAction.register(Herbi, 1, (player, npc) -> Herbi(player));
        NPCAction.register(Zalcano, 1, (player, npc) -> Zalcano(player));
        NPCAction.register(SpiritPool, 1, (player, npc) -> SpiritPool(player));
        ObjectAction.register(Dummy, 1, (player, obj) -> Dummy(player));
    }

    private static void Herbi(Player p) {
        //Hunter & herblore
        p.startEvent(e -> {
            while (true) {
                if (p.getStats().get(StatType.Herblore).currentLevel >= 75 || p.getStats().get(StatType.Hunter).currentLevel >= 75) {
                    p.sendMessage("Your level as exceeded the allowed starter level of 75.");
                    return;
                }
                p.animate(2282);
                p.getInventory().addOrDrop(13307, 2); // 6k an hour
                Item rolled = rollHerbs();
                p.getInventory().add(rolled);
                p.getStats().addXp(StatType.Herblore, 3.0, true);
                p.getStats().addXp(StatType.Hunter, 3.0, true);
                e.delay(3);
            }
        });
    }

    private static void Hespori(Player p) {
        //Farming
        p.startEvent(e -> {
            while (true) {
                if (p.getStats().get(StatType.Farming).currentLevel >= 75) {
                    p.sendMessage("Your level as exceeded the allowed starter level of 75.");
                    return;
                }
                int secateurs = 0;
                if (p.getEquipment().hasId(7409) || p.getInventory().hasId(7409)) {
                    secateurs = 1;
                } else if (p.getInventory().hasId(5329)) {
                    secateurs = 0;
                }
                int anim = secateurs == 1 ? 3342 : 2279;
                p.animate(anim);
                p.getInventory().addOrDrop(13307, 2); // 6k an hour
                p.getStats().addXp(StatType.Farming, 3.0, true);
                e.delay(3);
            }
        });
    }

    private static void Zalcano(Player p) {
        // Mining & Runecrafting
        p.startEvent(e -> {
            while (true) {
                if (p.getStats().get(StatType.Mining).currentLevel >= 75 || p.getStats().get(StatType.Runecrafting).currentLevel >= 75) {
                    p.sendMessage("Your level as exceeded the allowed starter level of 75.");
                    return;
                }
                p.animate(624);
                p.getInventory().addOrDrop(13307, 2); // 6k an hour
                Item rolled = rollOres();
                p.getInventory().add(rolled);
                p.getStats().addXp(StatType.Mining, 2.0, true);
                p.getStats().addXp(StatType.Runecrafting, 3.0, true);
                e.delay(3);
            }
        });
    }

    private static void SpiritPool(Player p) {
        // Fishing
        p.startEvent(e -> {
            while (true) {
                if (p.getStats().get(StatType.Fishing).currentLevel >= 75) {
                    p.sendMessage("Your level as exceeded the allowed starter level of 75.");
                    return;
                }
                p.animate(618);
                p.getInventory().addOrDrop(13307, 2); // 6k an hour
                p.getStats().addXp(StatType.Fishing, 3.0, true);
                e.delay(3);
            }
        });
    }

    private static void CrystalTree(Player p) {
        // Woodcutting
        p.startEvent(e -> {
            while (true) {
                if (p.getStats().get(StatType.Woodcutting).currentLevel >= 75) {
                    p.sendMessage("Your level as exceeded the allowed starter level of 75.");
                    return;
                }
                p.animate(867);
                p.getInventory().addOrDrop(13307, 2); // 6k an hour
                p.getStats().addXp(StatType.Woodcutting, 3.0, true);
                e.delay(3);
            }
        });
    }

    private static void Dummy(Player p) {
        //Attack Str Def Training
        p.startEvent(e -> {
            while (true) {
                p.animate(422);
                p.getInventory().addOrDrop(13307, 2); // 6k an hour
                e.delay(3);
            }
        });
    }

    private static final LootTable oreTable = new LootTable()
            .addTable(100,
                    new LootItem(7937, 1, 62),
                    new LootItem(437, 1, 35),
                    new LootItem(439, 1, 30),
                    new LootItem(441, 1, 27),
                    new LootItem(443, 1, 25),
                    new LootItem(445, 1, 15),
                    new LootItem(448, 1, 15),
                    new LootItem(450, 1, 10),
                    new LootItem(452, 1, 5),
                    new LootItem(21348, 1, 5)
            );

    private static Item rollOres() {
        return oreTable.rollItem();
    }

    private static final LootTable herbTable = new LootTable()
            .addTable(100,
                    new LootItem(250, 1, 35), // Guam leaf
                    new LootItem(252, 1, 30), // Marrentill leaf
                    new LootItem(254, 1, 27), // Tarromin leaf
                    new LootItem(256, 1, 15), // Harralander leaf
                    new LootItem(258, 1, 5), // Ranarr leaf

                    new LootItem(260, 1, 1), // Irit leaf
                    new LootItem(262, 1, 1), // Avatoe leaf
                    new LootItem(264, 1, 1), // Kwuarm leaf
                    new LootItem(266, 1, 1), // Cadantine leaf
                    new LootItem(268, 1, 1), // Dwarf leaf
                    new LootItem(270, 1, 1)  // Torstol leaf
            );

    private static Item rollHerbs() {
        return herbTable.rollItem();
    }
}