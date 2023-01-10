package io.ruin.model.inter.handlers;

import io.ruin.model.item.Item;

class KeptOnDeathItem {
    public final ItemOnDeathItemKind kind;
    public final Item item;

    KeptOnDeathItem(ItemOnDeathItemKind kind, Item item) {
        this.kind = kind;
        this.item = item;
    }
}
