/**
 *
 */
package io.ruin.model.activities.gauntlet.crafting;


import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;

import java.util.Optional;

/**
 * @author Greco
 * @since 04/08/2021
 */


//TODO REDO THIS CLASS
public class GauntletCraftingAction {

    private final GauntletCraftable craftable;
    private int amount;

    public GauntletCraftingAction(Player attachment, Optional<GauntletCraftables> craftable, int amount) {
        this.craftable = craftable.orElse(null);
        this.amount = amount;
    }

    public void update(Player attachment) {
        if (!check(attachment)) {
            //	stop();
            return;
        }
    }

    public void execute(Player attachment) {
        if (!check(attachment)) {
            //	stop();
            return;
        }
        int equipSlot = -1;
        for (int[] item : craftable.getMaterials()) {
            if (attachment.getInventory().hasAtLeastOneOf(item[0])) {
                equipSlot = attachment.getInventory().remove(item[0], item[1]);
            } else
                attachment.getInventory().remove(item[0], item[1]);
        }
		
		/*if (equipSlot >= 0) {
			attachment.getEquipment()(craftable.getProduct(), 1, equipSlot);
		} else */
        attachment.getEquipment().add(craftable.getProduct(), 1);
        attachment.sendMessage("You successfully make a " + ItemDef.get(craftable.getProduct()) + ".");
        amount--;
    }

    public boolean check(Player attachment) {
        if (craftable == null) {
            attachment.sendMessage("Unable to find craftable item.");
            return false;
        }
        if (amount <= 0) {
            return false;
        }
//		if (!attachment.getInventory().hasFreeSlots()) {
//			attachment.sendMessage("You don't have enough inventory space to make that.");
//			return false;
//		}
        if (!attachment.getInventory().contains(craftable.getMaterials().length)) {
            attachment.sendMessage("You don't have the required materials to craft that.");
            return false;
        }
        return true;
    }

}
