package io.ruin.model.entity.npc.actions.karen;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class Karen {

    static {
        NPCAction.register(4249, "talk-to", (player, npc) -> {
            if (player.getInventory().contains(30257)) {
                player.dialogue(new NPCDialogue(npc, "Oh wonderful you found my key, thank you for bringing this back to me..."),
                        new PlayerDialogue("Oh this thing yeah I believe it's yours it was screaming at me on the way over here."),
                        new NPCDialogue(npc, "Well in return for bringing my key back I have a small thank you to give you."),
                        new ActionDialogue(() -> {
                            int amount = player.getInventory().count(30257);

                            player.getInventory().remove(30257, amount);
                            player.getInventory().add(995, amount * Random.get(50_000, 300_000));
                        }));
            } else {
                player.dialogue(new NPCDialogue(npc, "You don't have my key ? Where is your manager! I want to speak to your manager right now!"),
                        new PlayerDialogue("Woah karen chill your shit! I'll go get your fucking key!"),
                        new NPCDialogue(npc, "How dare you speak to me like that! I'm reporting you!"));
            }
        });
    }

}
