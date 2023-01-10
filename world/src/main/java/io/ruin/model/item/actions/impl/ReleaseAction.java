package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class ReleaseAction {

    private static final int GRAY_CHINCHOMPA = 10033;
    private static final int RED_CHINCHOMPA = 10034;
    private static final int BLACK_CHINCHOMPA = 11959;

    public static void releaseChinchompa(Player player, Item chinchompa) {
        int chinCount = player.getInventory().count(chinchompa.getId());
        player.dialogue(
                new YesNoDialogue("Are you sure you want to release all these " + chinchompa.getDef().name + "?", "Warning: This action cannot be undone.",
                        chinchompa,
                        () -> {
                            if (!player.getInventory().contains(chinchompa))
                                return;
                            player.getInventory().remove(chinchompa.getId(), chinCount);
                            player.closeDialogue();
                        }));
    }


    static {
        ItemAction.registerInventory(GRAY_CHINCHOMPA, "release", ReleaseAction::releaseChinchompa);
        ItemAction.registerInventory(RED_CHINCHOMPA, "release", ReleaseAction::releaseChinchompa);
        ItemAction.registerInventory(BLACK_CHINCHOMPA, "release", ReleaseAction::releaseChinchompa);
    }
}
