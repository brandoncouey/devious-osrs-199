package io.ruin.model.entity.npc.actions.edgeville.DiceNPC;

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

public enum Currency {

    GOLD(995),
    BLOODMONEY(13307);

    public final int id;

    Currency(int id) {
        this.id = id;
    }

    public static Currency get(int currencyID) {
        for (Currency c : values()) {
            if (currencyID == c.id)
                return c;
        }
        return null;
    }

}
