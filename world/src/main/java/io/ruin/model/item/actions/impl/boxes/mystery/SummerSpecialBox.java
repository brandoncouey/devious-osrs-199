package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.api.utils.Random;
import io.ruin.cache.Icon;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class SummerSpecialBox {

    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(995, Random.get(1_500_000, 2_000_000), 40), //COINS CUNT
            new LootItem(995, Random.get(1_500_000, 3_000_000), 30), //COINS CUNT
            new LootItem(995, Random.get(1_500_000, 5_000_000), 25), //COINS CUNT
            new LootItem(12696, 100, 20), //100 Super combat potions
            new LootItem(13442, 250, 20), //250 Anglerfish
            new LootItem(537, 150, 20),      // Dragon Bones
            new LootItem(20360, 8, 20).broadcast(Broadcast.WORLD), //Medium Clue geode x15
            new LootItem(990, 50, 20),     // Crystal Key
            new LootItem(30256, 20, 20),      // Vote Multipass x20
            new LootItem(20360, 15, 18).broadcast(Broadcast.WORLD), //Medium Clue geode x15
            new LootItem(22330, 20, 15).broadcast(Broadcast.WORLD), //Pvm Box
            new LootItem(6831, 20, 15).broadcast(Broadcast.WORLD), //Slayer Box
            new LootItem(30290, 10, 15).broadcast(Broadcast.WORLD), //Pvp Box
            new LootItem(290, 2, 10).broadcast(Broadcast.WORLD), //Lux Box
            new LootItem(30294, 10, 10).broadcast(Broadcast.WORLD), //Scatch Card
            new LootItem(30307, 1, 10).broadcast(Broadcast.WORLD), //$10 Bond
            new LootItem(1037, 1, 3).broadcast(Broadcast.WORLD), //Bunny Ears
            new LootItem(1961, 1, 3).broadcast(Broadcast.WORLD) //Easter egg
    );

    static {
        ItemAction.registerInventory(30306, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            Item reward;
            reward = MYSTERY_BOX_TABLE.rollItem();
            item.remove(1);
            if (reward.getAmount() > player.getInventory().getFreeSlots()) {
                player.getBank().add(reward.getId(), reward.getAmount());
            } else {
                player.getInventory().addOrDrop(reward);
            }
            if (reward.lootBroadcast != null)
                Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "Seasonal Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

    }
}
