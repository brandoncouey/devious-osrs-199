package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;

public class BloodScroll {

    private static final int BLOOD_SCROLL = 786;
    private static final int BLOWPIPE = 12924;
    private static final int TENTWHIP = 12006;
    private static final int SANGSCYTHE = 25741;
    private static final int BLOODBLOWPIPE = 30314;
    private static final int BLOODTENT = 30276;
    private static final int BLOODSCYTHE = 30339;
    private static final int INFERNAL_LANCE = 30354;
    private static final int DRAGON_LANCE = 22978;
    private static final int KATANA = 30416;
    private static final int BLOOD_KATANA = 30416;
    private static final int TBOW = 20997;
    private static final int BLOOD_TBOW = 30420;

    private static void makeBlowPipe(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your toxic blowpipe into a toxic blood blowpipe, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(BLOODBLOWPIPE);
                    secondary.remove();
                })
        );
    }

    private static void makeLance(Player player, Item primary, Item secondary,int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Dragon hunter lance into an Infernal Lance, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(INFERNAL_LANCE);
                    secondary.remove();
                    player.getInventory().remove(21295, 1);
                })
        );
    }

    private static void makeWhip(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Tentacle Whip into a bloody tentacle whip, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(BLOODTENT);
                    secondary.remove();
                })
        );
    }

    private static void makeScythe(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Sanguine scythe of vitur into a bloody scythe of vitur, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(BLOODSCYTHE);
                    secondary.remove();
                })
        );
    }

    private static void makeKatana(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Sanguine scythe of vitur into a bloody scythe of vitur, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(BLOOD_KATANA);
                    secondary.remove(2);
                })
        );
    }

    private static void makeTbow(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>This will convert your Sanguine scythe of vitur into a bloody scythe of vitur, this cannot be undone."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your scroll will be destroyed.", result, 1, () -> {
                    primary.setId(BLOOD_TBOW);
                    secondary.remove(2);
                })
        );
    }

    static {
        ItemItemAction.register(BLOOD_SCROLL, BLOWPIPE, (player, primary, secondary) -> makeBlowPipe(player, primary, secondary, BLOODBLOWPIPE));
        ItemItemAction.register(BLOOD_SCROLL, TENTWHIP, (player, primary, secondary) -> makeWhip(player, primary, secondary, BLOODTENT));
        ItemItemAction.register(BLOOD_SCROLL, SANGSCYTHE, (player, primary, secondary) -> makeScythe(player, primary, secondary, BLOODSCYTHE));
        ItemItemAction.register(BLOOD_SCROLL, DRAGON_LANCE, (player, primary, secondary) -> makeLance(player, primary, secondary, INFERNAL_LANCE));
        ItemItemAction.register(KATANA, BLOOD_SCROLL, (player, primary, secondary) -> makeKatana(player, primary, secondary, BLOOD_KATANA));
        ItemItemAction.register(BLOOD_SCROLL, TBOW, (player, primary, secondary) -> makeTbow(player, primary, secondary, BLOOD_TBOW));
    }

}