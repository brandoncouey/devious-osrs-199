package io.ruin.content.activities.tournament.presets

import io.ruin.api.utils.Tuple
import io.ruin.content.activities.tournament.TournamentAttributes
import io.ruin.content.activities.tournament.TournamentPlaylist
import io.ruin.model.item.Item
import io.ruin.model.item.containers.Equipment
import io.ruin.model.skills.prayer.Prayer
import io.ruin.model.stat.StatType

object f2p : TournamentAttributes {

    override fun playlist(): TournamentPlaylist {
        return TournamentPlaylist.F2P
    }

    override fun equipmentLoadout(): Array<Tuple<Int, Item>> {
        return arrayOf(
            Tuple(Equipment.SLOT_HAT, Item(3486)),
            Tuple(Equipment.SLOT_CAPE, Item(1027)),
            Tuple(Equipment.SLOT_AMULET, Item(1731)),
            Tuple(Equipment.SLOT_WEAPON, Item(853)),
            Tuple(Equipment.SLOT_CHEST, Item(1135)),
            Tuple(Equipment.SLOT_LEGS, Item(23267)),
            Tuple(Equipment.SLOT_HANDS, Item(23261)),
            Tuple(Equipment.SLOT_FEET, Item(25171)),
            Tuple(Equipment.SLOT_RING, Item(2550)),
            Tuple(Equipment.SLOT_AMMO, Item(890, 5000))
        )
    }

    override fun inventoryLoadout(): Array<Tuple<Int, Item>> {
        var index = 0
        return arrayOf(
            Tuple(index++, Item(113)),
            Tuple(index++, Item(2434)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(1319)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index++, Item(373)),
            Tuple(index, Item(373))
        )
    }

    override fun skillLoadout(): Array<Tuple<StatType, Int>> {
        return arrayOf(
            Tuple(StatType.Attack, 99),
            Tuple(StatType.Defence, 45),
            Tuple(StatType.Strength, 99),
            Tuple(StatType.Hitpoints, 99),
            Tuple(StatType.Ranged, 99),
            Tuple(StatType.Prayer, 45),
            Tuple(StatType.Magic, 99)
        )
    }

    override fun restrictedPrayers(): Array<Prayer> {
        return arrayOf(Prayer.PROTECT_ITEM)
    }
}