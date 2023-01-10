package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class BraceletOfSlaughter {

    public static final int BRACELET_OF_SLAUGHTER = 21183;

    static {
        ItemAction.registerInventory(BRACELET_OF_SLAUGHTER, "break", (player, item) -> {
            if (player.braceletSaves == 40) {
                player.dialogue(new ItemDialogue().one(BRACELET_OF_SLAUGHTER, "The bracelet is fully charged.<br>There would be no point in breaking it."));
                return;
            }
            player.dialogue(
                    new OptionsDialogue(
                            "Status: " + player.braceletSaves + " slayer kill saves left.",
                            new Option("Break the bracelet.", () -> {
                                item.remove();
                                rechargeBracelet(player);
                                player.dialogue(new ItemDialogue().one(BRACELET_OF_SLAUGHTER, "The bracelet shatters. Your next bracelet of slaughter will start<br>afresh from 30 charges."));
                            }),
                            new Option("Cancel")
                    )
            );
        });
        ItemAction.registerInventory(BRACELET_OF_SLAUGHTER, "check", (player, item) -> {
            if (player.braceletSaves == 1)
                player.sendMessage("You can save 1 more kill before the bracelet shatters");
            else
                player.sendMessage("You can save " + player.braceletSaves + " more kills before a bracelet will shatter.");
        });
        ItemAction.registerEquipment(BRACELET_OF_SLAUGHTER, "check", (player, item) -> {
            if (player.braceletSaves == 1)
                player.sendMessage("You can save 1 more kill before the bracelet shatters");
            else
                player.sendMessage("You can save " + player.braceletSaves + " more kills before a bracelet will shatter.");
        });
    }

    public static void rechargeBracelet(Player player) {
        player.braceletSaves = 30;
    }

    public static void checkStatus(Player player) {
        if (check(player)) {
            if (player.braceletSaves == 0) {
                rechargeBracelet(player);
                player.getEquipment().remove(BRACELET_OF_SLAUGHTER, 1);
            }
        }
    }

    public static void removeCharge(Player player) {
        player.braceletSaves -= 1;
        player.sendMessage("Your Bracelet of slaughter activates, extending your task by one.");
        checkStatus(player);
    }


    public static boolean check(Player player) {
        return player.getEquipment().hasId(BRACELET_OF_SLAUGHTER);
    }


}
