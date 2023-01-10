package io.ruin.content.activities.tournament.presets

import io.ruin.api.utils.Tuple
import io.ruin.content.activities.tournament.TournamentAttributes
import io.ruin.content.activities.tournament.TournamentPlaylist
import io.ruin.model.item.Item
import io.ruin.model.item.containers.Equipment
import io.ruin.model.skills.magic.SpellBook
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType

object Zerk : TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.ZERK
    }

    override fun spellbookLoadout(): SpellBook {
        return SpellBook.LUNAR
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
            Tuple(Equipment.SLOT_HAT, Item(3751)),
            Tuple(Equipment.SLOT_CAPE, Item(21295)),
            Tuple(Equipment.SLOT_AMULET, Item(12436)),
            Tuple(Equipment.SLOT_WEAPON, Item(12788)),
            Tuple(Equipment.SLOT_CHEST, Item(10551)),
            Tuple(Equipment.SLOT_LEGS, Item(12502)),
            Tuple(Equipment.SLOT_HANDS, Item(7462)),
            Tuple(Equipment.SLOT_FEET, Item(23389)),
            Tuple(Equipment.SLOT_RING, Item(20657)),
            Tuple(Equipment.SLOT_AMMO, Item(21326, 5000))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
            Tuple(index++, Item(12695)),
            Tuple(index++, Item(2444)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(10925)),
            Tuple(index++, Item(10925)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(3144)),
            Tuple(index++, Item(3144)),
            Tuple(index++, Item(3144)),
            Tuple(index++, Item(3144)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(20368)),
            Tuple(index++, Item(4153)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index, Item(24621, 5000))
        )
    }

    override fun skillLoadout(): Array<Tuple<StatType, Int>> {
        return arrayOf(
            Tuple(StatType.Attack, 99),
            Tuple(StatType.Defence, 45),
            Tuple(StatType.Strength, 99),
            Tuple(StatType.Hitpoints, 99),
            Tuple(StatType.Ranged, 99),
            Tuple(StatType.Prayer, 99),
            Tuple(StatType.Magic, 99)
        )
    }

    override fun restrictedPrayers(): Array<Prayer> {
        return arrayOf(
            Prayer.PROTECT_ITEM,
            Prayer.REDEMPTION,
            Prayer.SMITE,
            Prayer.PROTECT_FROM_MAGIC,
            Prayer.PROTECT_FROM_MISSILES,
            Prayer.PROTECT_FROM_MELEE
        )
    }
}