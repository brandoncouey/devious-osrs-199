package io.ruin.model.item.actions.impl.tradepost;

import io.ruin.model.item.Item;

public class TradePostOffer {

    private final String username;
    private final Item item;
    private final int maxAmount;
    private int pricePerItem;
    private final long timestamp;
    private long adEnd;
    private boolean removed;
    private long embedId;

    public TradePostOffer(String username, Item item, int pricePerItem, long timestamp) {
        this.username = username;
        this.item = item;
        this.maxAmount = item.getAmount();
        this.pricePerItem = pricePerItem;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public Item getItem() {
        return item;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public void setPrice(int price) {
        this.pricePerItem = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getAdEnd() {
        return adEnd;
    }

    public void setAdEnd(long adEnd) {
        this.adEnd = adEnd;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public long getEmbedId() {
        return embedId;
    }

    public void setEmbedId(long embedId) {
        this.embedId = embedId;
    }

}