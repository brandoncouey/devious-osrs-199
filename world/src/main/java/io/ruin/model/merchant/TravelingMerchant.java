package io.ruin.model.merchant;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class TravelingMerchant {

    private static final int MAX_ITEMS = 5;
    @Getter
    private static final Map<Integer, MerchantItem> inventory = new HashMap<>();

    public static void sell(Player player, Item item) {
        if (!inventory.containsKey(item.getId())) {
            player.sendMessage("The merchant is not accepting that item at this time.");
            return;
        }
        MerchantItem merchant = inventory.get(item.getId());
        if (merchant.getStock() >= item.getAmount()) {
            player.sendMessage("The merchant is already full of stock for that item.");
            return;
        }
        if ((merchant.getStock() + item.getAmount()) > merchant.getAmount()) {
            final int remaining = merchant.getAmount() - merchant.getStock();
            sell(player, new Item(item.getId(), remaining));
            return;
        }
        player.getInventory().remove(item);
        inventory.get(item.getId()).setStock(inventory.get(item.getId()).getStock() + item.getAmount());
        //TODO check if player can hold the amount of incoming gold
        save();
    }

    public static int getAvailableStockForItem(final int itemId) {
        return inventory.containsKey(itemId) ? (inventory.get(itemId).getAmount() - inventory.get(itemId).getStock()) : 0;
    }

    public static void spawn() {
        inventory.clear();
        int filled = 0;
        while (filled < MAX_ITEMS) {
            final MerchantItem toAdd = RANDOM_ITEMS[Misc.random(0, RANDOM_ITEMS.length - 1)];
            if (inventory.containsKey(toAdd.getId()))
                continue;
            inventory.put(toAdd.getId(), toAdd);
            filled++;
        }
        new NPC(349).spawn(new Position(3084, 3502, 1));
        Broadcast.WORLD_NOTIFICATION.sendNews("The Traveling Merchant has just been spotted in Edgeville!");
    }

    public static void load() {

    }

    public static void save() {

    }

    private static final MerchantItem[] RANDOM_ITEMS = new MerchantItem[] {
            //Bones
            new MerchantItem(ItemID.BONES, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.BIG_BONES, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.BABY_DRAGON_BONE, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.DRAGON_BONES, Misc.random(2000, 3000)),
            //Logs
            new MerchantItem(ItemID.LOGS, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.WILLOW_LOGS, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.MAPLE_LOGS, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.YEW_LOGS, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.MAGIC_LOGS, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.TEAK_LOGS, Misc.random(2000, 3000)),
            new MerchantItem(ItemID.WILLOW_LOGS, Misc.random(2000, 3000)),
            //Herbs
            //Unf Potions
            //Herb Ingred
            //Uncooked fish
            //Smithing Bars
            //Ore
            //Hides
            //Leathers
            //Unstrung bows
            //Seeds
    };



}
