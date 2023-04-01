package io.ruin.model.inter.presets;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.model.item.Item;
import io.ruin.model.item.attributes.AttributeExtensions;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class PresetItem {
    @Expose
    @Getter
    @Setter
    private int id;
    @Expose
    @Getter
    @Setter
    private int amount;
    @Expose
    @Getter
    @Setter
    private Map<String, String> attributes;

    public PresetItem(int id, int amount) {
        this.id = id;
        this.amount = amount;
        this.attributes = new HashMap<>();
    }
    public PresetItem(int id, int amount, Map<String, String> attributes) {
        this.id = id;
        this.amount = amount;
        this.attributes = attributes;
    }

    public static PresetItem[] itemsToPresetItems(Item[] items, int size) {
        PresetItem[] presetItems = new PresetItem[size];
        for (int index = 0; index < size; index++) {
            Item item = items[index];
            if (item != null) {
                presetItems[index] = new PresetItem(item.getId(), item.getAmount(), item.copyOfAttributes());
                continue;
            }
            presetItems[index] = new PresetItem(-1, 1, null);
        }
        return presetItems;
    }

    public static Item[] presetItemsToItems(PresetItem[] presetItems, int size) {
        Item[] items = new Item[size];
        for (int index = 0; index < size; index++) {
            PresetItem item = presetItems[index];
            if (item != null) {
                items[index] = new Item(item.getId(), item.getAmount(), item.copyOfAttributes());
                continue;
            }
            items[index] = new Item(-1, 1);
        }
        return items;
    }

    public Map<String, String> copyOfAttributes() {
        return attributes == null ? Maps.newHashMap() : Maps.newHashMap(attributes);
    }

    public boolean hasAttributes() {
        return AttributeExtensions.hashAttributes(attributes) != 0;
    }

    public int getAttributeInt(String key) {
        return getAttributeInt(key, -1);
    }

    public int getAttributeInt(String key, int defaultValue) {
        return Integer.parseInt(attributes.getOrDefault(key, String.valueOf(defaultValue)));
    }
}
