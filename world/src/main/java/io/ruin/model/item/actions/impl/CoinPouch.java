package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.item.actions.ItemAction;

import static io.ruin.cache.ItemID.COINS_995;

public enum CoinPouch {

    SMALL(22521, 6, 12),
    MEDIUM(22522, 12, 16),
    LARGE(22523, 50, 125),
    GIANT(22524, 150, 400);

    private final int itemId;
    private final int minAmount;
    private final int maxAmount;

    CoinPouch(int itemId, int minAmt, int maxAmt) {
        this.itemId = itemId;
        this.minAmount = minAmt;
        this.maxAmount = maxAmt;
    }

    public static final int MAX_ALLOWED = 28;

    static {
        for (CoinPouch pouch : CoinPouch.values()) {
            ItemAction.registerInventory(pouch.itemId, "open-all", (player, item) -> {
                int amount = player.getInventory().count(item.getId());
                if (amount <= 0)
                    return;
                int coinReward = Random.get(pouch.minAmount, pouch.maxAmount) * amount;
                player.lock();
                item.remove(amount);
                player.sendMessage("You open the pouch to find " + coinReward + " coins.");
                player.getInventory().add(COINS_995, coinReward);
                player.unlock();
            });

            ItemAction.registerInventory(pouch.itemId, "open", (player, item) -> {
                int coinReward = Random.get(pouch.minAmount, pouch.maxAmount);
                player.lock();
                item.remove(1);
                player.sendMessage("You open the pouch to find " + coinReward + " coins.");
                player.getInventory().add(COINS_995, coinReward);
                player.unlock();
            });
        }
    }

}
