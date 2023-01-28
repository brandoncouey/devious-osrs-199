package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.cache.Icon;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class PVPBox {

    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(6694, 15, 50), //Ancient Shards
            new LootItem(1620, 50, 30), //Uncut Rubies
            new LootItem(7945, 50, 30), //Raw Monk Fish
            new LootItem(1516, 50, 30), //Yew Logs
            new LootItem(7937, 50, 30), //Pure Essence
            new LootItem(2362, 50, 30), //Adamant Bars
            new LootItem(384, 50, 30), //Raw Sharks
            new LootItem(1514, 50, 30), //Magic Logs
            new LootItem(2364, 50, 30), //Rune Bars
            new LootItem(1618, 50, 30), //Uncut Diamonds
            new LootItem(12696, 10, 25), //10 Super combat potions
            new LootItem(13442, 20, 25), //20 Anglerfish
            new LootItem(22636, 150, 10), //Morrigan Jav
            new LootItem(22634, 150, 10), //Morrigan Throwing Axe
            new LootItem(22296, 1, 5).broadcast(Broadcast.GLOBAL), //Staff of Light
            new LootItem(21902, 1, 5).broadcast(Broadcast.GLOBAL), //Dragon Crossbow
            new LootItem(4151, 1, 5).broadcast(Broadcast.GLOBAL), //Whip
            new LootItem(22616, 1, 2).broadcast(Broadcast.GLOBAL), //Vesta Top
            new LootItem(22619, 1, 2).broadcast(Broadcast.GLOBAL), //Vesta Bottom
            new LootItem(22628, 1, 2).broadcast(Broadcast.GLOBAL), //Statius Top
            new LootItem(22622, 1, 2).broadcast(Broadcast.GLOBAL), //Statius Warhammer
            new LootItem(22631, 1, 2).broadcast(Broadcast.GLOBAL), //Statius Bottom
            new LootItem(22625, 1, 2).broadcast(Broadcast.GLOBAL), //Statius Helm
            new LootItem(22653, 1, 2).broadcast(Broadcast.GLOBAL), //Zuriel Top
            new LootItem(22656, 1, 2).broadcast(Broadcast.GLOBAL), //Zuriel Bottom
            new LootItem(22650, 1, 2).broadcast(Broadcast.GLOBAL), //Zuriel Hood
            new LootItem(22641, 1, 2).broadcast(Broadcast.GLOBAL), //Morrigans Top
            new LootItem(22644, 1, 2).broadcast(Broadcast.GLOBAL), //Morrigans Bottom
            new LootItem(22638, 1, 2).broadcast(Broadcast.GLOBAL), //Morrigans Coif
            new LootItem(22647, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriels Staff
            new LootItem(23613, 1, 1).broadcast(Broadcast.GLOBAL), //Vesta Long Sword
            new LootItem(22610, 1, 1).broadcast(Broadcast.GLOBAL) //Vesta Spear
    );


}
