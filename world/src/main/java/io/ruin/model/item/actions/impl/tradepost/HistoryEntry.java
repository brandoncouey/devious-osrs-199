package io.ruin.model.item.actions.impl.tradepost;

import io.ruin.model.item.Item;
import lombok.Data;
import lombok.Value;

@Data
@Value
public class HistoryEntry {
    private int offerType;
    private Item item;
    private int price;
}
