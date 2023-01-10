package io.ruin.model.content.yak;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;


public class PakYak {
    static {
        ItemNPCAction.register(13307, 11389, (player, item, npc) -> player.dialogue(new MessageDialogue("<col=ff0000>Warning:</col> you are about to swap all your donator coins for " +
                        "blood money at a 10k:1 ratio. Are you sure you want to continue?").lineHeight(25),
                new OptionsDialogue(
                        new Option("Yes", () -> sendtoBank2(player)),
                        new Option("No")
                )));
    }

    private static void sendtoBank2(Player player) {
        player.dialogue(
                new OptionsDialogue("Exchange your bloody money for platinum tokens?",
                        new Option("Yes", () -> {
                            for (Item item : player.getInventory().getItems()) {
                                if (item != null) {
                                    player.getBank().remove(13307, 12500 * item.getAmount());
                                    player.getBank().add(item.getId(), item.getAmount());
                                    player.getInventory().remove(item.getId(), item.getAmount());
                                }
                                new Option("No", player::closeDialogue);
                            }
                        })));
    }
}
