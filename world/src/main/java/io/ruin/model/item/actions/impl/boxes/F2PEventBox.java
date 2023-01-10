package io.ruin.model.item.actions.impl.boxes;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;

public class F2PEventBox {
    private static void gift(Player player, Item box) {
        int boxId = box.getId();
        player.stringInput("Enter player's display name:", name -> {
            if (!player.getInventory().hasId(boxId))
                return;
            name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
            name = name.substring(0, Math.min(name.length(), 12));
            if (name.isEmpty()) {
                player.retryStringInput("Invalid username, try again:");
                return;
            }
            if (name.equalsIgnoreCase(player.getName())) {
                player.retryStringInput("Cannot gift yourself, try again:");
                return;
            }
            Player target = World.getPlayer(name);
            if (target == null) {
                player.retryStringInput("Player cannot be found, try again:");
                return;
            }
            if (target.getGameMode().isIronMan()) {
                player.retryStringInput("That player is an ironman and can't receive gifts!");
                return;
            }
            player.stringInput("Enter a message for " + target.getName() + ":", message -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Gift your " + box.getDef().name + " to " + target.getName() + "?", box, () -> {
                    if (!player.getInventory().hasId(boxId))
                        return;
                    player.getInventory().remove(boxId, 1);
                    if (!target.getInventory().isFull())
                        target.getInventory().add(boxId, 1);
                    else
                        target.getBank().add(boxId, 1);
                    target.sendMessage("<img=91> " + Color.DARK_RED.wrap(player.getName() + " has just gifted you " + box.getDef().descriptiveName + "!"));
                    player.sendMessage("<img=91> " + Color.DARK_RED.wrap("You have successfully gifted your " + box.getDef().name + " to " + target.getName() + "."));
                    if (!message.isEmpty())
                        target.sendMessage("<img=91> " + Color.DARK_RED.wrap("[NOTE] " + message));
                }));
            });
        });
    }

    static {
        ItemAction.registerInventory(30347, "open", (player, item) -> {
            int freeSlots = player.getInventory().getFreeSlots();
            if (freeSlots <= 3) {
                player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                return;
            }
            if (!player.getEquipment().isEmpty()) {
                player.dialogue(new MessageDialogue("Please remove your equipment before we proceed with the event."));
                return;
            }
            player.lock();
            player.closeDialogue();
            item.remove(1);
            player.getBank().deposit(player.getInventory(), true, true);
            player.getBank().deposit(player.getEquipment(), true, true);
            player.getEquipment().set(Equipment.SLOT_HAT, new Item(1169));
            player.getEquipment().set(Equipment.SLOT_CAPE, new Item(23099));
            player.getEquipment().set(Equipment.SLOT_AMULET, new Item(1731));
            player.getEquipment().set(Equipment.SLOT_WEAPON, new Item(853));
            player.getEquipment().set(Equipment.SLOT_CHEST, new Item(12449));
            player.getEquipment().set(Equipment.SLOT_LEGS, new Item(12445));
            player.getEquipment().set(Equipment.SLOT_HANDS, new Item(1059));
            player.getEquipment().set(Equipment.SLOT_FEET, new Item(1061));
            player.getEquipment().set(Equipment.SLOT_AMMO, new Item(890, 5000));
            player.getInventory().add(1333, 1);
            player.getInventory().add(1319, 1);
            player.getInventory().add(2665, 1);
            player.getInventory().add(2661, 1);
            player.getInventory().add(113, 2);
            player.getInventory().add(2667, 1);
            player.getInventory().add(2663, 1);
            player.getInventory().add(2301, 4);
            player.getInventory().add(373, 17);
            player.getStats().restore(false);
            player.getMovement().restoreEnergy(100);
            player.cureVenom(0);
            player.getCombat().updateLevel();
            player.getAppearance().update();
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(30347, "gift", F2PEventBox::gift);
    }
}
