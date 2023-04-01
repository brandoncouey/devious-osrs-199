package io.ruin.model.entity.npc.actions.taverly;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class TaverlyDungeon {

    static {
        NPCAction.register(4925, 1, (p, n) -> {
            if (!p.getInventory().contains(1590)) {
                p.dialogue(new NPCDialogue(n, "Here is ya a dusty key."));
                p.getInventory().addOrDrop(1590, 1);
            } else {
                p.sendMessage("You already have a dusty key.");
            }
        });
    }
}
