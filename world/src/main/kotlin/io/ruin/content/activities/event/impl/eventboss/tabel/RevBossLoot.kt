package io.ruin.content.activities.event.impl.eventboss.tabel

import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable
import io.ruin.utility.Broadcast

class RevBossLoot : LootTable() {

    init {
        guaranteedItems(
            LootItem(13307, 100_000, 250_000, 100), //coins
            LootItem(11943, 1, 1, 100) //coins
        )
        addTable(
            10,
            LootItem(4151, 1, 1, 30), //dboots
            LootItem(21818, 2, 10, 1).broadcast(Broadcast.GLOBAL), //vesta spear
            LootItem(12696, 5, 10, 40),
            LootItem(1305, 1, 1, 30), //D long
            LootItem(1215, 1, 1, 30), //D Dagger
            LootItem(4585, 1, 1, 30), //D Skirt
            LootItem(4087, 1, 1, 30),
            LootItem(13302, 1, 1, 30)
        )
        addTable(
            3,
            LootItem(22305, 1, 2, 1).broadcast(Broadcast.GLOBAL), //vesta spear

            LootItem(22547, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
            LootItem(22542, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
            LootItem(22552, 1, 1, 1).broadcast(Broadcast.GLOBAL), //vesta spear
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