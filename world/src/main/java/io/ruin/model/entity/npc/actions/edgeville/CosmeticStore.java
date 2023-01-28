package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.shop.ShopManager;


public class CosmeticStore {


    private static final int Cosmetitian = 5792;
    private static final String CosmeticStore1 = "1b594438-0c10-11ec-9a03-0242ac130003";
    private static final String CosmeticStore2 = "b1efd1e7-2be0-4319-88ac-30b1c23126da";
    private static final String CosmeticStore3 = "77c620c0-109c-11ec-82a8-0242ac130003";

    public static int getPrice(Player player, Item item) {
        ItemDef def = item.getDef();
        if (item.getId() == BraceletOfEthereum.REVENANT_ETHER)
            return 0;
        if (def.isNote())
            def = def.fromNote();
        if (def.protectValue != 0)
            return def.protectValue;
        return player.getGameMode().isIronMan() ? def.lowAlchValue : def.highAlchValue;
    }

    static {
        //Tournament reach hoe
        //SpawnListener.register(7317, npc -> npc.skipReachCheck = p -> p.equals(3407, 3180));

        SpawnListener.register(Cosmetitian, npc -> npc.skipReachCheck = p -> p.equals(3093, 3511));

        NPCAction.register(Cosmetitian, "buy-cosmetics 1", (player, npc) -> {
            if (!player.isADonator()) {
                player.dialogue(new MessageDialogue("You must be a Member to buy cosmetics from this store"));
                return;

            } else ShopManager.openIfExists(player, CosmeticStore1);
        });
        NPCAction.register(Cosmetitian, "buy-cosmetics 2", (player, npc) -> {
            if (!player.isADonator()) {
                player.dialogue(new MessageDialogue("You must be a Member to buy cosmetics from this store"));
                return;

            } else ShopManager.openIfExists(player, CosmeticStore2);
        });
        NPCAction.register(Cosmetitian, "buy-cosmetics 3", (player, npc) -> {
            if (!player.isADonator()) {
                player.dialogue(new MessageDialogue("You must be a Member to buy cosmetics from this store"));
                return;

            } else ShopManager.openIfExists(player, CosmeticStore3);
        });
    }
}
