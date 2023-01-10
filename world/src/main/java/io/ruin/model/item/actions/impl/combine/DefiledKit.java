package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;

public class DefiledKit {

    private static final int Defiled_Kit = 30350;
    private static final int FACEGUARD = 22326;
    private static final int CHESTPLATE = 22327;
    private static final int LEGGUARDS = 22328;
    private static final int DEFILEDFACEGUARD = 30341;
    private static final int DEFILEDCHESTPLATE = 30342;
    private static final int DEFILEDLEGGUARDS = 30343;

    private static void makeBlowPipe(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Justiciar Faceguard into a Defiled Faceguard, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(DEFILEDFACEGUARD);
                    secondary.remove();
                })
        );
    }

    private static void makeWhip(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Justiciar into a Defiled Chestplate, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(DEFILEDCHESTPLATE);
                    secondary.remove();
                })
        );
    }

    private static void makeScythe(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Justiciar into a Defiled legguards, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(DEFILEDLEGGUARDS);
                    secondary.remove();
                })
        );
    }

    static {
        ItemItemAction.register(Defiled_Kit, FACEGUARD, (player, primary, secondary) -> makeBlowPipe(player, primary, secondary, DEFILEDFACEGUARD));
        ItemItemAction.register(Defiled_Kit, CHESTPLATE, (player, primary, secondary) -> makeWhip(player, primary, secondary, DEFILEDCHESTPLATE));
        ItemItemAction.register(Defiled_Kit, LEGGUARDS, (player, primary, secondary) -> makeScythe(player, primary, secondary, DEFILEDLEGGUARDS));
    }

}