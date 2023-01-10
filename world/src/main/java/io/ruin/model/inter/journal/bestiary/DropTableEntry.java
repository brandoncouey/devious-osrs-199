package io.ruin.model.inter.journal.bestiary;

import io.ruin.cache.NPCDef;
import io.ruin.model.item.loot.LootTable;

public class DropTableEntry {

    public int id;
    public String name;
    public LootTable table;

    public DropTableEntry(int npcId) {
        this.id = npcId;
        this.name = NPCDef.get(npcId).name.replaceAll("<col=\\w{6}>|</col>", "");
    }

    public DropTableEntry(String name, LootTable table) {
        this.name = name;
        this.table = table;
    }

}
