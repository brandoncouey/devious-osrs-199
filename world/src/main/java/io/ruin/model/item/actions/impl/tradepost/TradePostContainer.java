package io.ruin.model.item.actions.impl.tradepost;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class TradePostContainer {


    @Getter
    private final String username;

    @Getter
    private final TradePostOffer[] offers = new TradePostOffer[6];

    @Getter
    private final List<HistoryEntry> history = new ArrayList<>();

    @Getter
    @Setter
    public long coffer;

    /**
     * Constructs a new {@link TradePostContainer}.
     *
     * @param username The user name.
     */
    public TradePostContainer(String username) {
        this.username = username;
    }

    /**
     * Adds an offer into this container.
     *
     * @param offer The offer to add.
     */
    public boolean add(TradePostOffer offer) {
        int slot = getFreeSlot();

        if (slot == -1) {
            return false;
        }
        offers[slot] = offer;
        onAdd(offer);
        return true;
    }

    /**
     * Removes an offer from this container.
     *
     * @param offer The offer to remove.
     */
    public boolean remove(TradePostOffer offer) {
        int slot = getSlot(offer);

        if (slot != -1) {
            onRemove(offers[slot]);
            offers[slot] = null;
            return true;
        }

        return false;
    }

    private void onAdd(TradePostOffer offer) {
        TradePostManager.save(this);
    }

    private void onRemove(TradePostOffer offer) {
        TradePostManager.save(this);
        offer.setRemoved(true);
    }

    /**
     * Sets an offer in the specified slot.
     *
     * @param slot  The slot.
     * @param offer The offer.
     */
    public void set(int slot, TradePostOffer offer) {
        offers[slot] = offer;
    }

    /**
     * Returns an offer from the specified slot.
     *
     * @param slot The slot.
     * @return The offer.
     */
    public TradePostOffer get(int slot) {
        if (slot < 0 || slot >= offers.length) {
            return null;
        }

        return offers[slot];
    }

    /**
     * Gets the slot of the specified offer.
     *
     * @param offer The offer.
     * @return The slot.
     */
    public int getSlot(TradePostOffer offer) {
        for (int i = 0; i < offers.length; i++) {
            if (offer == offers[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Gets the next free slot if available.
     *
     * @return The slot.
     */
    public int getFreeSlot() {
        for (int i = 0; i < offers.length; i++) {
            if (offers[i] == null) {
                return i;
            }
        }

        return -1;
    }

    public void addToCoffer(int amount) {
        coffer += amount;
        TradePostManager.save(this);
    }

    public void removeFromCoffer(int amount) {
        coffer -= amount;
        TradePostManager.save(this);
    }

    public Optional<TradePostOffer> find(int id, int amount) {
        return getValidOffers().stream().filter(o -> o.getItem().getId() == id && o.getItem().getAmount() >= amount).findFirst();
    }

    /**
     * Returns an advertised offer if present.
     *
     * @return The advertised offer.
     */
    public Optional<TradePostOffer> advOffer() {
        return getValidOffers().stream().filter(offer -> offer.getAdEnd() > 0).findFirst();
    }

    /**
     * Returns a list of valid (non-null) offers.
     *
     * @return The list of offers.
     */
    public List<TradePostOffer> getValidOffers() {
        return Arrays.stream(offers).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Returns the size of this container.
     *
     * @return The size.
     */
    public int size() {
        return getValidOffers().size();
    }

}
