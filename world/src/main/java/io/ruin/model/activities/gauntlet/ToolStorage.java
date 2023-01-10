/**
 *
 */
package io.ruin.model.activities.gauntlet;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Greco
 * @since 04/08/2021
 */
public class ToolStorage {

    public static void open(Player c) {

        OptionScroll.open(c, "Choose a tool",
                new Option(""),
                new Option("Crystal sceptre", () -> handleTool(c, 23861)),
                new Option("Crystal axe", () -> handleTool(c, 23862)),
                new Option("Crystal pickaxe", () -> handleTool(c, 23863)),
                new Option("Crystal harpoon", () -> handleTool(c, 23864)),
                new Option("Pestle and mortar", () -> handleTool(c, 23865))
        );

        return;
    }

    public static void handleTool(Player player, int item) {
        player.getInventory().add(item, 1);
    }

    static {
        ObjectAction.register(36074, actions -> {
            actions[1] = (player, obj) -> ToolStorage.open(player);
        });
    }

}
