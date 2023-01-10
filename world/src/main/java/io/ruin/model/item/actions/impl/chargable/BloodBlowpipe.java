package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.RangedData;
import io.ruin.model.combat.RangedWeapon;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.CHISEL;

public class BloodBlowpipe {

    private static final int SCALES = 12934;
    private static final int UNCHARGED = 30314, CHARGED = 30315;
    private static final int MAX_AMOUNT = 0x3fff;

    public enum Dart {

        NONE(-1, null),
        BRONZE(806, RangedWeapon.BRONZE_DART.data),
        IRON(807, RangedWeapon.IRON_DART.data),
        STEEL(808, RangedWeapon.STEEL_DART.data),
        BLACK(3093, RangedWeapon.BLACK_DART.data),
        MITHRIL(809, RangedWeapon.MITHRIL_DART.data),
        ADAMANT(810, RangedWeapon.ADAMANT_DART.data),
        RUNE(811, RangedWeapon.RUNE_DART.data),
        DRAGON(11230, RangedWeapon.DRAGON_DART.data),
        AMETHYST(25849, RangedWeapon.AMETHYST_DART.data);

        public final int id;

        public final RangedData rangedData;

        Dart(int id, RangedData rangedData) {
            this.id = id;
            this.rangedData = rangedData;
        }

    }

    /**
     * Dismantle (Uncharged)
     */

    private static void dismantle(Player player, Item blowpipe) {
        player.dialogue(
                new OptionsDialogue(
                        "Dismantle Toxic blowpipe (empty)",
                        new Option("Yes", () -> {
                            if(!player.getInventory().contains(blowpipe))
                                return;
                            blowpipe.remove();
                            player.getInventory().add(SCALES, 20000);
                            player.closeDialogue();
                        }),
                        new Option("No", player::closeDialogue)
                )
        );
    }

    /**
     * Check
     */

    private static void check(Player player, Item blowpipe) {
        String darts;
        Dart dart = getDart(blowpipe);
        if(dart == Dart.NONE)
            darts = "None";
        else
            darts = ItemDef.get(dart.id).name + " x " + getDartAmount(blowpipe);
        String scales;
        int scalesAmount = getScalesAmount(blowpipe);
        System.out.println("Scales: " + scalesAmount); //prints out 16380 here
        if(scalesAmount == 0)
            scales = "0.0%, 0 scales";
        else
            scales = NumberUtils.formatOnePlace(((double) scalesAmount / MAX_AMOUNT) * 100D) + "%, " + scalesAmount + " scales";
        player.sendMessage("Darts: <col=007f00>" + darts + "</col> Scales: <col=007f00>" + scales + "</col>");
    }

    /**
     * Load/Unload
     */

    private static void load(Player player, Item blowpipe, Item dartItem, Dart dart) {
        Dart loadedDart = getDart(blowpipe);
        if(loadedDart != Dart.NONE && loadedDart != dart) {
            player.sendMessage("The blowpipe currently contains a different sort of dart.");
            return;
        }
        int dartAmount = getDartAmount(blowpipe);
        int allowedAmount = MAX_AMOUNT - dartAmount;
        if(allowedAmount == 0) {
            player.sendMessage("The blowpipe can't hold any more darts.");
            return;
        }
        int addAmount = Math.min(allowedAmount, dartItem.getAmount());
        dartItem.incrementAmount(-addAmount);
        update(blowpipe, dart, dartAmount + addAmount, getScalesAmount(blowpipe));
        check(player, blowpipe);
    }

    private static void unload(Player player, Item bloodBlowpipe) {
        if(player.isLocked())
            return;
        Dart dart = getDart(bloodBlowpipe);
        if(dart == Dart.NONE) {
            player.sendMessage("The blowpipe has no darts in it.");
            return;
        }
        if(player.getInventory().add(dart.id, getDartAmount(bloodBlowpipe)) == 0) {
            player.sendMessage("You don't have space to do that.");
            return;
        }
        update(bloodBlowpipe, dart, 0, getScalesAmount(bloodBlowpipe));
        player.closeDialogue();
    }

    /**
     * Charge/Uncharge
     */

    private static void charge(Player player, Item bloodBlowpipe, Item scalesItem) {
        int scalesAmount = getScalesAmount(bloodBlowpipe);
        int allowedAmount = MAX_AMOUNT - scalesAmount;
        if (allowedAmount == 0) {
            player.sendMessage("The blowpipe can't hold any more scales.");
            return;
        }
        int addAmount = Math.min(allowedAmount, scalesItem.getAmount());
        scalesItem.incrementAmount(-addAmount);
        update(bloodBlowpipe, getDart(bloodBlowpipe), getDartAmount(bloodBlowpipe), scalesAmount + addAmount);
        check(player, bloodBlowpipe);
    }

    private static void uncharge(Player player, Item bloodBlowpipe) {
        int reqSlots = 0;
        Dart dart = getDart(bloodBlowpipe);
        int dartAmount = getDartAmount(bloodBlowpipe);
        if(dartAmount > 0) {
            if(!player.getInventory().hasId(dart.id))
                reqSlots++;
        }
        int scalesAmount = getScalesAmount(bloodBlowpipe);
        if(scalesAmount > 0) {
            if(!player.getInventory().hasId(SCALES))
                reqSlots++;
        }
        if(player.getInventory().getFreeSlots() < reqSlots) {
            player.sendMessage("You don't have enough inventory space to uncharge the blowpipe.");
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge the blowpipe, all scales and darts will fall out.", bloodBlowpipe, () -> {
            if(player.isLocked())
                return;
            player.lock();
            if(dartAmount > 0)
                player.getInventory().add(dart.id, dartAmount);
            if(scalesAmount > 0)
                player.getInventory().add(SCALES, scalesAmount);
            update(bloodBlowpipe, dart, 0, 0);
            player.unlock();
        }));
    }

    public static void update(Item bloodBlowpipe, Dart dart, int dartAmount, int scalesAmount) {
        if (dartAmount == 0)
            dart = Dart.NONE;
        int id = (dart != Dart.NONE || scalesAmount > 0) ? CHARGED : UNCHARGED;
        if(bloodBlowpipe.getId() != id)
            bloodBlowpipe.setId(id);
        AttributeExtensions.putAttribute(bloodBlowpipe, AttributeTypes.AMMO_ID, dart.ordinal());
        AttributeExtensions.putAttribute(bloodBlowpipe, AttributeTypes.AMMO_AMOUNT, dartAmount);
        AttributeExtensions.setCharges(bloodBlowpipe, scalesAmount);
    }

    public static Dart getDart(Item bloodBlowpipe) {
        return Dart.values()[bloodBlowpipe.getAttributeInt(AttributeTypes.AMMO_ID, 0)];
    }

    public static int getDartAmount(Item bloodBlowpipe) {
        return bloodBlowpipe.getAttributeInt(AttributeTypes.AMMO_AMOUNT, 0);
    }

    public static int getScalesAmount(Item bloodBlowpipe) {
        return AttributeExtensions.getCharges(bloodBlowpipe);
    }

    /**
     * Registering
     */

    static {

        ItemAction.registerInventory(UNCHARGED, "dismantle", BloodBlowpipe::dismantle);
        ItemAction.registerInventory(CHARGED, "check", BloodBlowpipe::check);
        ItemAction.registerEquipment(CHARGED, "check", BloodBlowpipe::check);
        ItemAction.registerInventory(CHARGED, "unload", BloodBlowpipe::unload);
        ItemAction.registerInventory(CHARGED, "uncharge", BloodBlowpipe::uncharge);
        for(Dart dart : Dart.values()) {
            if(dart != Dart.NONE) {
                ItemItemAction loadAction = (player, blowpipe, dartItem) ->
                        BloodBlowpipe.load(player, blowpipe, dartItem, dart);
                ItemItemAction.register(UNCHARGED, dart.id, loadAction);
                ItemItemAction.register(CHARGED, dart.id, loadAction);
            }
        }

        for(Dart dart : Dart.values()) {
            if(dart != Dart.NONE) {
                ItemItemAction loadAction = (player, blowpipe, dartItem) ->
                        BloodBlowpipe.load(player, blowpipe, dartItem, dart);
/*                ItemItemAction.register(UNCHARGED_MAGMA, dart.id, loadAction);
                ItemItemAction.register(CHARGED_MAGMA, dart.id, loadAction);*/
            }
        }

        ItemItemAction.register(UNCHARGED, SCALES, BloodBlowpipe::charge);
        ItemItemAction.register(CHARGED, SCALES, BloodBlowpipe::charge);


        ItemItemAction loadPoisonedAction = (player, primary, secondary) ->
                player.sendMessage("You can't use that kind of dart - the venom doesn't mix with other poisons.");
        ItemDef.forEach(def -> {
            if(def.name.toLowerCase().contains("dart(p")) {
                ItemItemAction.register(UNCHARGED, def.id, loadPoisonedAction);
                ItemItemAction.register(CHARGED, def.id, loadPoisonedAction);
    }
        });


    }

}