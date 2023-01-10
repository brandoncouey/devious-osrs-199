package io.ruin.model.item.actions.impl.scrolls;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class RunePouch {

    private static final int RUNE_POUCH_NOTE = 24587;

    static {
        ItemAction.registerInventory(RUNE_POUCH_NOTE, "read", (player, item) -> {
            player.dialogue(new OptionsDialogue("Claim a rune pouch?",
                    new Option("Yes!", () -> {
                        item.remove(1);
                        player.getInventory().addOrDrop(12791, 1);
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have successfully claimed your rune pocuh!"));
                    }),
                    new Option("Nevermind")));
        });
    }
}
