package io.ruin.content.activities.event.impl.eventboss.tabel

import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable
import io.ruin.utility.Broadcast


/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/2/2020
 */
class HunllefTable : LootTable() {
    init {
        guaranteedItems(
            LootItem(13307, 50_000, 100_000, 100), //coins
            LootItem(995, 5_000_000, 10_000_000, 100), //coins
            LootItem(23962, 100, 500, 100) //coins
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
            LootItem(1748, 40, 100, 30),
            LootItem(23951, 1, 10, 40) //Enhanced Crystal Key
        )
        addTable(
            5,
            LootItem(22330, 1, 3, 15).broadcast(Broadcast.GLOBAL), //PVM Box
            LootItem(23971, 1, 1, 7).broadcast(Broadcast.GLOBAL), //Crystal helm
            LootItem(23975, 1, 1, 7).broadcast(Broadcast.GLOBAL), //Crystal body
            LootItem(23979, 1, 1, 7).broadcast(Broadcast.GLOBAL), //Crystal legs
            LootItem(25894, 1, 1, 2).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25888, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25886, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25884, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25890, 1, 1, 2).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25867, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25896, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25892, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Bow of faedhinen
            LootItem(25874, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(25872, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(25870, 1, 1, 2).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(25876, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(24551, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(25882, 1, 1, 2).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(25880, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(25878, 1, 1, 1).broadcast(Broadcast.GLOBAL), //Blade of Saldor
            LootItem(30307, 1, 10, 1).broadcast(Broadcast.GLOBAL) //membership token
        )
    }
}