package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

public class BryophytaStaff {

    public static final int CHARGED = 22370;
    public static final int UNCHARGED = 22368;
    private static Item CHARGE_ITEMS;

    static {
        CHARGE_ITEMS = Rune.NATURE.toItem(1);
        ItemItemAction.register(CHARGED, 561, BryophytaStaff::charge);
        ItemItemAction.register(UNCHARGED, 561, BryophytaStaff::charge);
        ItemAction.registerInventory(CHARGED, "check", BryophytaStaff::check);
        ItemAction.registerEquipment(CHARGED, "check", BryophytaStaff::check);
        ItemAction.registerInventory(CHARGED, "uncharge", BryophytaStaff::uncharge);
        ItemDef.get(CHARGED).addPreTargetDefendListener(BryophytaStaff::consumeCharge);
        ItemAction.registerInventory(CHARGED, "drop", BryophytaStaff::drop);
        ItemAction.registerInventory(UNCHARGED, "dismantle", BryophytaStaff::dismantle);
        ItemItemAction.register(1391, 22372, BryophytaStaff::combine);
        ItemItemAction.register(BryophytaStaff.CHARGED, 22372, BryophytaStaff::wrongCombine);
    }

    private static void wrongCombine(Player player, Item item, Item item1) {
        player.sendMessage("You can only combine essence with an " + Color.RED.wrap("uncharged") + " bryophyta.");
    }

    private static void dismantle(Player player, Item item) {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You'll need at least 1 free inventory space to do that.");
            return;
        }
        item.setId(BryophytaStaff.UNCHARGED);
    }

    private static void combine(Player player, Item bryophyta, Item essence) {
        if (!player.getStats().check(StatType.Crafting, 65, "create bryophta staff")) {
            return;
        }
        bryophyta.setId(UNCHARGED);
        essence.remove();
        player.sendMessage("You combined Bryophyta's essence with a battlestaff!");
    }

    private static void consumeCharge(Player player, Item staff, Hit hit, Entity entity) {
        if (AttributeExtensions.getCharges(staff) > 0) {
            if (Random.rollDie(15,1)) {
                player.sendMessage("Your bryophyta staff saves you 1 charge!");
            } else {
                AttributeExtensions.deincrementCharges(staff, 1);
            }
        }
        if (AttributeExtensions.getCharges(staff) <= 0) {
            staff.setId(UNCHARGED);
            player.sendMessage(Color.RED.wrap("Your Bryophyta Staff has ran out of charges!"));
            player.getCombat().updateWeapon(false);
        }
    }

    private static void drop(Player player, Item item) {
        player.dialogue(new OptionsDialogue("You cannot drop this item. Uncharge it instead?",
                new Option("Yes, uncharge it.", () -> uncharge(player, item)),
                new Option("No.")));
    }

    public static void check(Player player, Item item) {
        player.sendMessage("Your Bryophyta Staff has " + AttributeExtensions.getCharges(item) + " charges remaining.");
    }

    public static void uncharge(Player player, Item item) {

        player.dialogue(new OptionsDialogue("You will NOT get the your nature runes back.",
                new Option("Okay. uncharge it.", () -> {
                    AttributeExtensions.setCharges(item, 0);
                    item.setId(UNCHARGED);
                }),
                new Option("No, don't uncharge it.")
        ));
    }

    public static void charge(Player p, Item bryophyta, Item other) {
        int currentCharges = bryophyta.getId() == 22370 ? 0 : AttributeExtensions.getCharges(bryophyta);
        if (currentCharges >= 1000) {
            p.sendMessage("Your bryophyta can't hold any more charges.");
            return;
        }
        int inventoryCharges = p.getInventory().getAmount(561);
        if (inventoryCharges == 0) {
            p.sendMessage("The staff requires 1 nature rune per charge until a maximum of 1000 charges!");
            return;
        }
        int chargesToAdd = Math.min(inventoryCharges, 1000 - currentCharges);
        p.getInventory().remove(561, chargesToAdd);
        p.animate(1979);
        p.graphics(1250, 25, 0);
        bryophyta.setId(CHARGED);
        AttributeExtensions.addCharges(bryophyta, chargesToAdd);
        p.dialogue(new ItemDialogue().one(CHARGED, "You charge your Bryophyta Staff. It now has " + NumberUtils.formatNumber(chargesToAdd+currentCharges) + " charges."));
    }

}
