package io.ruin.model.activities.leagues.shatteredrelics;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Greco
 * @since 01/08/2022
 */
public class WaystoneLocations {

    public static void openWaystoneLocations(Player player) {
        OptionScroll.open(player, "Waystone Locations",
                new Option("Lumbridge", () -> handleWaystone(player, 0)),
                new Option("Falador", () -> handleWaystone(player, 1)),
                new Option("Varrock", () -> handleWaystone(player, 2)),
                new Option("Al Kharid", () -> handleWaystone(player, 3)),
                new Option("Catherby", () -> handleWaystone(player, 4)),
                new Option("Ardougne", () -> handleWaystone(player, 5)),
                new Option("Brimhaven", () -> handleWaystone(player, 6)),
                new Option("Rellekka", () -> handleWaystone(player, 7)),
                new Option("Ferox Enclave", () -> handleWaystone(player, 8)),
                new Option("Canifis", () -> handleWaystone(player, 9)),
                new Option("Prifddinas", () -> handleWaystone(player, 10)),
                new Option("Kourend", () -> handleWaystone(player, 11)),
                new Option("Cancel", () -> handleClose(player))
        );
    }

    public static void handleWaystone(Player player, int option) {
        switch (option) {
            case 0:
                if (player.hasUnlockedLumbridgeWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(3222, 3218, 0);
                }
                break;
            case 1:
                if (player.hasUnlockedFaladorWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(2964, 3378, 0);
                }
                break;
            case 2:
                if (player.hasUnlockedVarrockWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(3211, 3424, 0);
                }
                break;
            case 3:
                if (player.hasUnlockedAlKharidWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(3293, 3175, 0);
                }
                break;
            case 4:
                if (player.hasUnlockedCatherbyWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(2808, 3436, 0);
                }
                break;
            case 5:
                if (player.hasUnlockedArdougneWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(2661, 3307, 0);
                }
                break;
            case 6:
                if (player.hasUnlockedBrimhavenWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(2757, 3178, 0);
                }
                break;
            case 7:
                if (player.hasUnlockedRellekkaWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(2643, 3678, 0);
                }
                break;
            case 8:
                if (player.hasUnlockedFeroxEnclaveWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(3150, 3634, 0);
                }
                break;
            case 9:
                if (player.hasUnlockedCanifisWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(3510, 3480, 0);
                }
                break;
            case 10:
                if (player.hasUnlockedPrifddinasWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(3200, 6080, 0);
                }
                break;
            case 11:
                if (player.hasUnlockedKourendWaystone == false) {
                    handleLockedOption(player);
                } else {
                    player.getMovement().teleport(1642, 3673, 0);
                }
                break;
        }
    }

    public static void unlockAllWaystones(Player player) {
        player.hasUnlockedLumbridgeWaystone = true;
        player.hasUnlockedFaladorWaystone = true;
        player.hasUnlockedVarrockWaystone = true;
        player.hasUnlockedAlKharidWaystone = true;
        player.hasUnlockedCatherbyWaystone = true;
        player.hasUnlockedArdougneWaystone = true;
        player.hasUnlockedBrimhavenWaystone = true;
        player.hasUnlockedRellekkaWaystone = true;
        player.hasUnlockedFeroxEnclaveWaystone = true;
        player.hasUnlockedCanifisWaystone = true;
        player.hasUnlockedPrifddinasWaystone = true;
        player.hasUnlockedKourendWaystone = true;
        player.sendMessage("<col=8B0000>You have unlocked all the Waystones!");
    }

    public static void unlockLumbridgeWaystone(Player player) {
        if (player.hasUnlockedLumbridgeWaystone == false) {
            player.hasUnlockedLumbridgeWaystone = true;
            player.sendMessage("You have unlocked the Lumbridge Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedLumbridgeWaystone == true) {
            player.sendMessage("You have already unlocked the Lumbridge Waystone.");
        }
    }

    public static void unlockFaladorWaystone(Player player) {
        if (player.hasUnlockedFaladorWaystone == false) {
            player.hasUnlockedFaladorWaystone = true;
            player.sendMessage("You have unlocked the Falador Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedFaladorWaystone == true) {
            player.sendMessage("You have already unlocked the Falador Waystone.");
        }
    }

    public static void unlockVarrockWaystone(Player player) {
        if (player.hasUnlockedVarrockWaystone == false) {
            player.hasUnlockedVarrockWaystone = true;
            player.sendMessage("You have unlocked the Varrock Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedVarrockWaystone == true) {
            player.sendMessage("You have already unlocked the Varrock Waystone.");
        }
    }

    public static void unlockAlKharidWaystone(Player player) {
        if (player.hasUnlockedAlKharidWaystone == false) {
            player.hasUnlockedAlKharidWaystone = true;
            player.sendMessage("You have unlocked the Al Kharid Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedAlKharidWaystone == true) {
            player.sendMessage("You have already unlocked the Al Kharid Waystone.");
        }
    }

    public static void unlockCatherbyWaystone(Player player) {
        if (player.hasUnlockedCatherbyWaystone == false) {
            player.hasUnlockedCatherbyWaystone = true;
            player.sendMessage("You have unlocked the Catherby Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedCatherbyWaystone == true) {
            player.sendMessage("You have already unlocked the Catherby Waystone.");
        }
    }

    public static void unlockArdougneWaystone(Player player) {
        if (player.hasUnlockedArdougneWaystone == false) {
            player.hasUnlockedArdougneWaystone = true;
            player.sendMessage("You have unlocked the Ardougne Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedArdougneWaystone == true) {
            player.sendMessage("You have already unlocked the Ardougne Waystone.");
        }
    }

    public static void unlockBrimhavenWaystone(Player player) {
        if (player.hasUnlockedBrimhavenWaystone == false) {
            player.hasUnlockedBrimhavenWaystone = true;
            player.sendMessage("You have unlocked the Brimhaven Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedBrimhavenWaystone == true) {
            player.sendMessage("You have already unlocked the Brimhaven Waystone.");
        }
    }

    public static void unlockRellekkaWaystone(Player player) {
        if (player.hasUnlockedRellekkaWaystone == false) {
            player.hasUnlockedRellekkaWaystone = true;
            player.sendMessage("You have unlocked the Rellekka Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedRellekkaWaystone == true) {
            player.sendMessage("You have already unlocked the Rellakka Waystone.");
        }
    }

    public static void unlockFeroxEnclaveWaystone(Player player) {
        if (player.hasUnlockedFeroxEnclaveWaystone == false) {
            player.hasUnlockedFeroxEnclaveWaystone = true;
            player.sendMessage("You have unlocked the Ferox Enclave Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedFeroxEnclaveWaystone == true) {
            player.sendMessage("You have already unlocked the Ferox Enclave Waystone.");
        }
    }

    public static void unlockCanifisWaystone(Player player) {
        if (player.hasUnlockedCanifisWaystone == false) {
            player.hasUnlockedCanifisWaystone = true;
            player.sendMessage("You have unlocked the Canifis Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedCanifisWaystone == true) {
            player.sendMessage("You have already unlocked the Canifis Waystone.");
        }
    }

    public static void unlockPrifddinasWaystone(Player player) {
        if (player.hasUnlockedPrifddinasWaystone == false) {
            player.hasUnlockedPrifddinasWaystone = true;
            player.sendMessage("You have unlocked the Prifdinnas Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedPrifddinasWaystone == true) {
            player.sendMessage("You have already unlocked the Prifdinnas Waystone.");
        }
    }

    public static void unlockKourendWaystone(Player player) {
        if (player.hasUnlockedKourendWaystone == false) {
            player.hasUnlockedKourendWaystone = true;
            player.sendMessage("You have unlocked the Kourend Waystone, you may now freely teleport here.");
        } else if (player.hasUnlockedKourendWaystone == true) {
            player.sendMessage("You have already unlocked the Kourend Waystone.");
        }
    }

    public static void handleLockedOption(Player player) {
        player.sendMessage("You need to unlock this Waystone first.");
    }

    public static void handleClose(Player player) {
        player.closeInterface(InterfaceType.MAIN);
    }

    static {

    }

}
