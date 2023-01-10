package io.ruin.content.activities.event.impl.eventboss.tabel

import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable
import io.ruin.utility.Broadcast

class CorruptedNechryarchLoot : LootTable() {

    init {
        guaranteedItems(
            LootItem(995, 100_000, 250_000, 100), //coins
            LootItem(11943, 1, 1, 100) //coins
        )
        addTable(
            25,
            LootItem(554, 250, 500, 60),
            LootItem(868, 2, 2, 60),
            LootItem(9193, 40, 70, 50),
            LootItem(560, 60, 120, 50),
            LootItem(9194, 3, 25, 30),
            LootItem(556, 75, 75, 30),
            LootItem(563, 10, 10, 30),
            LootItem(565, 15, 15, 10),
            LootItem(19582, 10, 100, 10)
        )
        addTable(
            15,
            LootItem(8783, 150, 1250, 70),
            LootItem(2362, 80, 120, 60),
            LootItem(452, 30, 50, 50),
            LootItem(2364, 30, 50, 50),
            LootItem(1514, 25, 60, 50),
            LootItem(1632, 10, 20, 50),
            LootItem(5316, 5, 9, 50),
            LootItem(6571, 1, 2, 50),
            LootItem(3025, 6, 12, 40),
            LootItem(12696, 5, 10, 40),
            LootItem(6686, 5, 12, 40),
            LootItem(392, 150, 250, 40),
            LootItem(1748, 40, 100, 30)
        )
        addTable(
            10,
            LootItem(4151, 1, 1, 30), //dboots
            LootItem(1305, 1, 1, 30), //D long
            LootItem(1215, 1, 1, 30), //D Dagger
            LootItem(4585, 1, 1, 30), //D Skirt
            LootItem(4087, 1, 1, 30), //D Legs
            LootItem(1347, 1, 1, 60), //Rune wh
            LootItem(1201, 1, 1, 60), //Rune Kite
            LootItem(1079, 1, 1, 60), //Rune platelegs
            LootItem(1127, 1, 1, 60), //Rune platebody
            LootItem(1163, 1, 1, 60) //Rune full helm
        )
        addTable(
            3,
            LootItem(22610, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
            LootItem(22613, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta sword
            LootItem(22622, 1, 1, 1).broadcast(Broadcast.GLOBAL), //statius warhammer
            LootItem(22634, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigans axe
            LootItem(22636, 1, 1, 1).broadcast(Broadcast.GLOBAL), //morrigans javelin
            LootItem(22647, 1, 1, 1).broadcast(Broadcast.GLOBAL), //zuriels staff
            LootItem(30250, 1, 1, 1).broadcast(Broadcast.GLOBAL), //$10 bond
            LootItem(30290, 1, 10, 1).broadcast(Broadcast.GLOBAL), //PVP Box
            LootItem(22650, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriel Hood
            LootItem(22653, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriel Robe top
            LootItem(22656, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Zuriel Robe bottom
            LootItem(22625, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Statius full helm
            LootItem(22628, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Statius platebody
            LootItem(22631, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Statius platelegs
            LootItem(22616, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Vesta Chainbody
            LootItem(22619, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Vesta chainskirt
            LootItem(22638, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigan Coif
            LootItem(22641, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Morrigan Leather Body
            LootItem(22644, 1, 1, 1).broadcast(Broadcast.GLOBAL) //Morrigan leather chaps
        )
    }

}