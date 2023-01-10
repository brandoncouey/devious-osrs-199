package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

public class AmuletOfBloodFury {

    private static final int FURY = 6585;
    private static final int BLOOD_SHARD = 24777;
    private static final int BLOOD_FURY = 24780;

    private static void makeBloodFury(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>Making the amulet of blood fury will consume your amulet of fury."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your amulet of fury will be destroyed.", result, 1, () -> {
                    primary.setId(BLOOD_FURY);
                    primary.putAttribute(AttributeTypes.CHARGES, 10000);
                    player.sendMessage(Color.GREEN, "You successfully add " + AttributeExtensions.getCharges(primary) + " charges to your amulet.");
                    secondary.remove();
                })
        );
    }

    private static void revertBloodFury(Player player, Item primary) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>Dissolving the amulet of blood fury does NOT return the blood shard, only the amulet of fury. Are you sure?"),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, you will only receive a amulet of fury.", FURY, 1, () -> {
                    primary.setId(FURY);
                    primary.putAttribute(AttributeTypes.CHARGES, 0);
                })
        );
    }

    private static void check(Player player, Item item) {
        player.sendFilteredMessage("<col=007f00>Your amulet currently has "
                + NumberUtils.formatNumber(AttributeExtensions.getCharges(item)) + " healing effect" + (AttributeExtensions.getCharges(item) > 1 ? "s" : "")
                + " remaining. The healing effect is currently "
                + (player.amuletOfBloodFuryEffect ? "enabled" : "disabled") + ".</col>");
    }

    static {
        ItemAction.registerInventory(BLOOD_FURY, "check", AmuletOfBloodFury::check);
        ItemAction.registerEquipment(BLOOD_FURY, "check", AmuletOfBloodFury::check);
        ItemAction.registerInventory(BLOOD_FURY, "revert", AmuletOfBloodFury::revertBloodFury);

        ItemAction.registerEquipment(BLOOD_FURY, "effect settings", (player, item) -> effectSettings(player));
        ItemAction.registerInventory(BLOOD_FURY, "effect settings", (player, item) -> effectSettings(player));
        ItemItemAction.register(FURY, BLOOD_SHARD, (player, primary, secondary) -> makeBloodFury(player, primary, secondary, 24780));

        ItemItemAction.register(BLOOD_FURY, BLOOD_SHARD, (player, primary, secondary) -> {
            int charges = AttributeExtensions.getCharges(primary);
            if ((charges + 10_000) >= 60_000) {
                player.sendMessage("Your Blood Fury cannot hold anymore charges right now.");
                return;
            }
            primary.putAttribute(AttributeTypes.CHARGES, 10000 + charges);
            secondary.remove(1);
            player.sendMessage(Color.GREEN, "You add an additional 10k charges to your " + primary.getDef().name + " You now have " + NumberUtils.formatNumber(AttributeExtensions.getCharges(primary)));
        });
    }

    private static void effectSettings(Player player) {
        player.dialogue(new OptionsDialogue(
                new Option("Toggle healing effect", () -> {
                    player.amuletOfBloodFuryEffect = !player.amuletOfBloodFuryEffect;
                    player.sendFilteredMessage("You " + (player.amuletOfBloodFuryEffect ? "enable" : "disable") + " the healing effect of your amulet.");
                }),
                new Option("Cancel", player::closeDialogue)
        ));
    }


}


