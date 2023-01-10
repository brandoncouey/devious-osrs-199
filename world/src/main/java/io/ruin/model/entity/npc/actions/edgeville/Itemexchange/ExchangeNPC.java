package io.ruin.model.entity.npc.actions.edgeville.Itemexchange;


import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;

public class ExchangeNPC {

    private static final int ELDER_CHAOS_DRUID = 2376;

    static {
        for (ExchangeableItems item2 : ExchangeableItems.values()) {
            ItemNPCAction.register(item2.id, ELDER_CHAOS_DRUID, (player, npc, item) -> {
                promptForAmount(player, npc, item, item2);
            });
        }
    }

    /**
     * Elder Chaos Druid
     */

    private static void promptForAmount(Player player, Item item, NPC npc, ExchangeableItems items) {
            player.dialogue(new OptionsDialogue(
                    new Option("Exchange '" + item.getDef().name, () -> exchange(player, item, 1, npc, items)),
                    new Option("Exchange All", () -> exchange(player, item, Integer.MAX_VALUE, npc, items)),
                    new Option("Exchange X", () -> player.integerInput("How many would you like to exchange?", amt -> exchange(player, item, amt, npc, items)))
            ));
    }
    private static void exchange(Player player, Item item, int amt, NPC npc, ExchangeableItems items) {
            if (amt > item.getAmount())
                amt = item.getAmount();
            while (amt-- > 0) {
                item.remove(1);
                player.exchangePoints += items.exchangePrice;
                player.sendMessage("You now have " + player.exchangePoints + " exchange points.");
            }
            player.dialogue(new ItemDialogue().one(item.getDef().id, npc.getDef().name + " converts your items to points."));
        }
}
