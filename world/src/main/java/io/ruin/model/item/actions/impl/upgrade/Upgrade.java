package io.ruin.model.item.actions.impl.upgrade;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

public abstract class Upgrade {

    /**
     * This repressents the item that they are 'upgrading to'
     * @return the item object of the upgradble item
     */
    public abstract Item getUpgradeItem();

    /**
     * This represents the materials needed to upgrade
     * @return the list of materials
     */
    public abstract Item[] getMaterials();

    /**
     * Represents the list of skills that need to be of a certain level in order to upgrade
     * @return the list of skill reqs.
     */
    public abstract UpgradeSkillRequirements[] getSkillRequirements();

    /**
     * Represents custom requirements needed for a specific upgrade
     * Adding a return message will make this method return false (as a boolean method)
     * Returning null will make the method return true - This is done like this to give the player a feedback 'status code' message
     * for errors while attempting to upgrade the item
     * @param player
     */
    public abstract String getAdditionalRequirements(final Player player);

    /**
     * Grabs the success chance of being able to successfully craft/upgrade this item
     * @param player
     * @return the success chance
     */
    public abstract int getSuccessChance(final Player player);

    /**
     * Method called upon actual purchase of the upgrade
     * @param player
     */
    public abstract void onPurchase(final Player player);

    /**
     * Method called upon successfully upgrading the item.
     * @param player
     */
    public abstract void onSuccessfulUpgrade(final Player player);
}
