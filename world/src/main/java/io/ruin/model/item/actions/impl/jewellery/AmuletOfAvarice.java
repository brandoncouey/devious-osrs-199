package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;

public class AmuletOfAvarice {

    private static void equip(Player player, Item avarice) {
        player.getEquipment().equip(avarice);
        player.startEvent(e -> {
            while (true) {
                Item item = player.getEquipment().get(Equipment.SLOT_AMULET);
                if (item == null || item.getId() != 22557)
                    break;
                e.delay(3);
                player.getCombat().skullNormal();
            }
        });
    }


    static {
        ItemAction.registerInventory(22557, "wear", (player, item) -> player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "The skull cannot be removed until the amulet is unequipped!", item, () -> {
                    equip(player, item);
                })
        ));
    }
}
