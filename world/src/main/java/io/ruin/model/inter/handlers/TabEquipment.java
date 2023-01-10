package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;

public class TabEquipment {

    static {
        InterfaceHandler.register(Interface.EQUIPMENT, h -> {
            h.actions[15] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_HAT);
            h.actions[16] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_CAPE);
            h.actions[17] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_AMULET);
            h.actions[18] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_WEAPON);
            h.actions[19] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_CHEST);
            h.actions[20] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_SHIELD);
            h.actions[21] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_LEGS);
            h.actions[22] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_HANDS);
            h.actions[23] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_FEET);
            h.actions[24] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_RING);
            h.actions[25] = (OptionAction) (player, option) -> itemAction(player, option, Equipment.SLOT_AMMO);
            h.actions[26] = (SimpleAction) p -> {
                p.openInterface(InterfaceType.MAIN, 719);
            };
            h.actions[1] = (SimpleAction) EquipmentStats::open;
            //   h.actions[3] = (SimpleAction) p -> p.sendMessage("This feature will be added with the release of the Grand Exchange!");
            h.actions[5] = (SimpleAction) IKOD::open;
            h.actions[7] = (SimpleAction) p -> {
                if (p.pet == null) {
                    p.sendMessage("You don't have a follower.");
                }
                p.callPet = true;
            };
        });
    }

    public static void itemAction(Player player, int option, int slot) {
        if (player.isLocked())
            return;
        Item item = player.getEquipment().get(slot);
        if (item == null)
            return;
        if (option == 10) {
            item.examine(player);
            return;
        }
        int i = option - 1;
        if (i < 0 || i >= 6)
            return;
        ItemAction[] actions = item.getDef().equipmentActions;
        if (actions != null) {
            ItemAction action = actions[i];
            if (action != null) {
                action.handle(player, item);
                return;
            }
        }
        if (option == 1) {
            player.closeChatbox(false);
            player.getEquipment().unequip(item);
        }
    }

}