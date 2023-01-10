package io.ruin.model.entity.npc.actions.edgeville.Itemexchange;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.wilderness.LavaMaze;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public enum ExchangeableItems {

    TWISTED_BOW(20997, 1000),
    DHCB(21012, 500),
    SANGUIN_STAFF(22481, 500),
    JUSTI_HEAD(22326, 250),
    JUSTI_CHEST(22327, 250),
    JUSTI_LEGS(22328, 250),
    AVERNIC_HILT(22322, 250),
    SCYTHE_OF_VITUR(22486, 1000);

    public final int id;

    public int exchangePrice;

    ExchangeableItems(int id, int exchangePrice) {
        this.id = id;
        this.exchangePrice = exchangePrice;
    }

    public static ExchangeableItems get(int id) {
        for (ExchangeableItems c : values()) {
            if (id == c.id)
                return c;
        }
        return null;
    }

    public static ExchangeableItems getPrice(int price) {
        for (ExchangeableItems c : values()) {
            if (price == c.exchangePrice)
                return c;
        }
        return null;
    }

}
