package io.ruin.model.activities.blastfurnace;

import io.ruin.model.entity.player.Player;

public class MeltingPot {

    public static boolean checkPot(Player player, int objectId) {
        if (objectId != BlastFurnace.MELTING_POT)
            return false;
        int totalCoalOreAmount = player.getBlastFurnaceCoalAmount();
        int totatSpecialOreAmount = player.getBlastFurnaceOres();

        player.sendMessage("You have a total of " + totalCoalOreAmount + " coal in the furnace.");
        if (totatSpecialOreAmount > 0)
            player.sendMessage("You have a total of " + totatSpecialOreAmount + " " + player.getCurrentBlastFurnaceOre().name().toLowerCase() + " ores in the furnace.");
        return true;
    }

}
