package io.ruin.model.entity.player;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.network.central.CentralSender;

public class DisplayName {

    public static void change(Player player) {
        if (!player.isDev())
            return;
        if (player.availableNameChanges < 1) {
            player.dialogue(new MessageDialogue("You don't have any name changes remaining!"));
            return;
        }

        StringBuilder displayName = new StringBuilder();

        player.stringInput("What would you like to change your name to?", answer -> {
            displayName.append(answer);
            CentralSender.sendUpdateDisplayName(player.getName(), answer);
            player.setName(answer);
            player.totalNameChanges++;
            player.availableNameChanges--;
        });


    }
}
