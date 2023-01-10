package io.ruin.model.item.actions.impl.upgrade;


import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.utility.Misc;

/**
 * This is the default manager that allows a system to easily implement methods to create an upgrading system.
 */
public class UpgradeManager {

    /**
     * Checks if the player has the requirements to upgrade
     * @param player
     * @param upgrade
     * @return has requirements
     */
    public static boolean hasRequirements(Player player, final Upgrade upgrade) {
        if (upgrade.getMaterials() != null) {
            if (!player.getInventory().containsAll(upgrade.getMaterials())) {
                player.sendMessage("@red@ You do not have all the required materials to upgrade to a " + upgrade.getUpgradeItem().getDef().name + ".");
                return false;
            }
        }
        if (upgrade.getSkillRequirements() != null) {
            for (final UpgradeSkillRequirements sreq : upgrade.getSkillRequirements()) {
                if (player.getStats().get(sreq.getSkill().clientId).currentLevel < sreq.getLevel()) {
                    player.sendMessage("@red@ You need a level of at least " + sreq.getLevel() + Misc.formatStringFormal(sreq.getSkill().name()) + " to upgrade to a " + upgrade.getUpgradeItem().getDef().name + ".");
                    return false;
                }
            }
        }
        final String statusMessage = upgrade.getAdditionalRequirements(player);
        if (statusMessage != null) {
            player.sendMessage("@red" + statusMessage);
            return false;
        }
        if (upgrade.getUpgradeItem() != null) {
            if (player.getInventory().getFreeSlots() == 0 && player.getBank().getFreeSlots() == 0) {
                player.sendMessage("@red@You have no free slots in your inventory or bank to complete this upgrade!");
                return false;
            }
        }
        return true;
    }

    /**
     * Attempts the actual upgrading of the item
     * @param player
     * @param upgrade
     */
    public static void attemptUpgrade(Player player, final Upgrade upgrade) {
        if (!hasRequirements(player, upgrade)) {
            return;
        }
        if (upgrade.getMaterials() != null) {
            for (final Item material : upgrade.getMaterials()) {
                player.getInventory().remove(material);
            }
        }
        upgrade.onPurchase(player);
        final int chance = upgrade.getSuccessChance(player);
        if (chance >= Misc.random(0, 100)) {
            if (upgrade.getUpgradeItem() != null) {
                if (player.getInventory().hasFreeSlots(1)) {
                    player.getInventory().add(upgrade.getUpgradeItem());
                } else if (player.getBank().hasFreeSlots(1)) {
                    player.getBank().add(upgrade.getUpgradeItem().getId(), upgrade.getUpgradeItem().getAmount());
                } else {
                    //GroundItemManager.spawnGroundItem(player, new GroundItem(upgrade.getUpgradeItem(), player.getPosition().copy(), player.getUsername(), false, 150, false, 2000));
                    //player.sendMessage("@red@You had no bank or inventory space for this upgrade and it has appeared beneath you!");
                }
            }
            upgrade.onSuccessfulUpgrade(player);
        }
    }
}
