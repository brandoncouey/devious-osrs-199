package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;


public class HolyScytheOfVitur {

    public static final int SCYTHE = 25736;
    public static final int UNCHARGED_SYCTHE = 25738;
    private static final int BLOOD_RUNE = 565;
    private static final int BLOOD_PER_CHARGE = 3;
    private static final int MAX_CHARGE = 20000;

    static {
        ItemItemAction.register(UNCHARGED_SYCTHE, BLOOD_RUNE, HolyScytheOfVitur::charge);
        ItemAction.registerInventory(UNCHARGED_SYCTHE, "charge", HolyScytheOfVitur::charge);
        ItemItemAction.register(SCYTHE, BLOOD_RUNE, HolyScytheOfVitur::charge);
        ItemAction.registerInventory(SCYTHE, "check", HolyScytheOfVitur::check);
        ItemAction.registerInventory(SCYTHE, "uncharge", HolyScytheOfVitur::uncharge);
        ItemDef.get(SCYTHE).addPostTargetDefendListener(((player, item, hit, target) -> {
            int charges = AttributeExtensions.getCharges(item);
            if (--charges <= 0) {
                item.setId(UNCHARGED_SYCTHE);
                player.getEquipment().unequip(item);
            }
            item.putAttribute(AttributeTypes.CHARGES, charges - 1);
        }));

    }

    private static void drop(Player player, Item item) {
        player.dialogue(new OptionsDialogue("You cannot drop this item. Uncharge it instead?",
                new Option("Yes, uncharge it.", () -> uncharge(player, item)),
                new Option("No.")));
    }

    private static void uncharge(Player player, Item item) {
        int reqSlots = 0;
        int bloodAmount = AttributeExtensions.getCharges(item);
        if (bloodAmount > 0) {
            if(!player.getInventory().hasId(BLOOD_RUNE))
                reqSlots++;
        }
        if (player.getInventory().getFreeSlots() < reqSlots) {
            player.sendMessage("You dont have enough inventory space to uncharge the Scythe of vitur");
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "Your Scythe of Vitur will become uncharged.",

                item, () -> {
            if(bloodAmount > 0)
                player.getInventory().add(BLOOD_RUNE, bloodAmount);
            item.putAttribute(AttributeTypes.CHARGES, 0);
            item.setId(UNCHARGED_SYCTHE);
        }));
    }

    private static void check(Player player, Item item) {
        if (item.getId() == SCYTHE) {
            player.sendMessage("Your Scythe of Vitur " + AttributeExtensions.getCharges(item) + " charges remaining.");
        }
    }

    private static void charge(Player player, Item scythe, Item blood) {
        int currentCharges = AttributeExtensions.getCharges(scythe);
        int allowedAmount = MAX_CHARGE - currentCharges;
        if (allowedAmount == 0) {
            player.sendMessage("Your Scythe of Vitur is already full charged.");
            return;
        }
        int addAmount = Math.min(allowedAmount, blood.getAmount());
        blood.incrementAmount(-addAmount);
        scythe.putAttribute(AttributeTypes.CHARGES, currentCharges + (addAmount));
        scythe.setId(SCYTHE);
        check(player, scythe);
    }

    private static void charge(Player player, Item scythe) {
        int currentCharges = AttributeExtensions.getCharges(scythe);
        int allowedAmount = MAX_CHARGE - currentCharges;
        if (allowedAmount == 0) {
            player.sendMessage("Your Scythe of Vitur is already full charged.");
            return;
        }
        Item blood = player.getInventory().getItemWithId(565);
        int addAmount = Math.min(allowedAmount, blood.getAmount());
        blood.incrementAmount(-addAmount);
        scythe.putAttribute(AttributeTypes.CHARGES, currentCharges + (addAmount));
        scythe.setId(SCYTHE);
        check(player, scythe);
    }
}
