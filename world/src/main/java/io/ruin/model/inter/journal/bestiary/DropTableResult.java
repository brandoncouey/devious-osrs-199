package io.ruin.model.inter.journal.bestiary;

import io.ruin.cache.ItemDef;
import io.ruin.utility.Misc;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DropTableResult {
    private int id;
    private int min;
    private int max;
    private int chance;
    private int rollChance;

    public String get() {
        String name = ItemDef.get(id).name;
        String amount = min == max ? Misc.currency(min) : (Misc.currency(min) + "-" + Misc.currency(max));
        String tableChance = rollChance > 0 ? rollChance + " x " : "";
        String c = chance == 1 ? "Always" : tableChance + ("1 / " + (chance == 0 ? "?" : chance));
        return name + "|" + amount + "|" + c;
    }
}
