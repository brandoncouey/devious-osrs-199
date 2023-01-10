package io.ruin.content.activities.tournament.presets

import io.ruin.api.utils.Tuple
import io.ruin.content.activities.tournament.TournamentAttributes
import io.ruin.content.activities.tournament.TournamentPlaylist
import io.ruin.model.item.Item
import io.ruin.model.item.containers.Equipment
import io.ruin.model.skills.magic.SpellBook
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType

object VestaBridSetup : TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.VestaBrid
    }

    override fun spellbookLoadout(): SpellBook {
        return SpellBook.ANCIENT
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
            Tuple(Equipment.SLOT_HAT, Item(22625)),
            Tuple(Equipment.SLOT_CAPE, Item(21791)),
            Tuple(Equipment.SLOT_AMULET, Item(19720)),
            Tuple(Equipment.SLOT_WEAPON, Item(23617)),
            Tuple(Equipment.SLOT_CHEST, Item(22653)),
            Tuple(Equipment.SLOT_LEGS, Item(22656)),
            Tuple(Equipment.SLOT_HANDS, Item(7462)),
            Tuple(Equipment.SLOT_FEET, Item(13235)),
            Tuple(Equipment.SLOT_RING, Item(22975)),
            Tuple(Equipment.SLOT_SHIELD, Item(12825))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
            Tuple(index++, Item(22616)),
            Tuple(index++, Item(22619)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(12006)),
            Tuple(index++, Item(22322)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(21295)),
            Tuple(index++, Item(22641)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(10925)),
            Tuple(index++, Item(10925)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(10925)),
            Tuple(index++, Item(10925)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(6685)),
            Tuple(index++, Item(22613)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(13441)),
            Tuple(index++, Item(12695)),
            Tuple(index, Item(24607, 500))
        )
    }

    override fun skillLoadout(): Array<Tuple<StatType, Int>> {
        return arrayOf(
            Tuple(StatType.Attack, 99),
            Tuple(StatType.Defence, 99),
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
            Prayer.SMITE
        )
    }
}