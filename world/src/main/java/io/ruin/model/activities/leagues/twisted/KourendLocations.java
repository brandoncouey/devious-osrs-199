package io.ruin.model.activities.leagues.twisted;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 * @since 01/10/2022
 */
public class KourendLocations {

    public static void openKourendLocations(Player player) {
        OptionScroll.open(player, "Kourend Locations",
                new Option("Kourend", () -> handleTeleport(player, 1)),
                new Option("Arceuus", () -> handleTeleport(player, 2)),
                new Option("Arceuus Library", () -> handleTeleport(player, 3)),
                new Option("Lovakengj", () -> handleTeleport(player, 4)),
                new Option("Blast Mine", () -> handleTeleport(player, 5)),
                new Option("Mount Karuulm", () -> handleTeleport(player, 6)),
                new Option("Mount Quidamortem", () -> handleTeleport(player, 7)),
                new Option("Molch", () -> handleTeleport(player, 8)),
                new Option("Shayzien", () -> handleTeleport(player, 9)),
                new Option("Woodcutting Guild", () -> handleTeleport(player, 10)),
                new Option("Hosidius", () -> handleTeleport(player, 11)),

                new Option("Cancel", () -> handleClose(player))
        );
    }

    public static void handleTeleport(Player player, int id) {
        switch (id) {
            case 1:
                player.getMovement().teleport(1642, 3674, 0);
                break;
            case 2:
                player.getMovement().teleport(1687, 3753, 0);
                break;
            case 3:
                player.getMovement().teleport(1629, 3807, 0);
                break;
            case 4:
                player.getMovement().teleport(1505, 3818, 0);
                break;
            case 5:
                player.getMovement().teleport(1502, 3837, 0);
                break;
            case 6:
                player.getMovement().teleport(1323, 3813, 0);
                break;
            case 7:
                player.getMovement().teleport(1254, 3561, 0);
                break;
            case 8:
                player.getMovement().teleport(1304, 3678, 0);
                break;
            case 9:
                player.getMovement().teleport(1497, 3586, 0);
                break;
            case 10:
                player.getMovement().teleport(1660, 3504, 0);
                break;
            case 11:
                player.getMovement().teleport(1758, 3591, 0);
                break;
        }
    }

    public static void handleClose(Player player) {
        player.closeInterface(InterfaceType.MAIN);
    }

    static {

    }
}
