package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.diaries.ardougne.ArdougneDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;

public class DragonKite {

    private static final int SLICE = 22100;
    private static final int SHARD = 22097;
    private static final int KITE = 21895;
    private static final int SQ = 1187;

    private static void make(Player player) {
        Item slice = player.getInventory().findItem(SLICE);
        Item shard = player.getInventory().findItem(SHARD);
        Item squa = player.getInventory().findItem(SQ);

        if (slice == null || shard == null || squa == null) {
            player.dialogue(new ItemDialogue().two(SLICE, SHARD, "You need all 3 pieces to forge the Dragon Kiteshield"));
            return;
        }

        Item hammer = player.getInventory().findItem(Tool.HAMMER);
        if (hammer == null) {
            player.sendMessage("You need a hammer to forge the shields.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the metal...");
            player.animate(898);
            event.delay(6);
            if (player.getInventory().hasId(SLICE) && player.getInventory().hasId(SHARD) && player.getInventory().hasId(SQ)) {
                player.getInventory().remove(SLICE, 1);
                player.getInventory().remove(SHARD, 1);
                player.getInventory().remove(SQ, 1);
                player.getInventory().add(KITE, 1);
                player.sendMessage("You forge the shield pieces together to complete it.");
            }
            player.unlock();
        });
    }

    static {
        ItemObjectAction.register(SLICE, "anvil", (player, item, obj) -> make(player));
        ItemObjectAction.register(SHARD, "anvil", (player, item, obj) -> make(player));
        ItemObjectAction.register(SQ, "anvil", (player, item, obj) -> make(player));
    }
}
