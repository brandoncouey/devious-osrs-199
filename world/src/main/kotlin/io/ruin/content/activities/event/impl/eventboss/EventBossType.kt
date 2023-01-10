package io.ruin.content.activities.event.impl.eventboss

import io.ruin.content.activities.event.impl.eventboss.tabel.CorruptedNechryarchLoot
import io.ruin.content.activities.event.impl.eventboss.tabel.HunllefTable
import io.ruin.model.item.actions.impl.pets.Pets
import io.ruin.model.item.loot.LootTable
import io.ruin.model.map.Position

enum class EventBossType(
    val id: Int,
    val positions: MutableList<Position>,
    val hitpoints: Int,
    val message: MutableList<String>,
    val rolls: Int,
    val lootTable: LootTable,
    val embedUrl: String,
    val pet: Pets,
    val petChance: Int
) {

    WALKER(
        3358, mutableListOf(
            Position.of(3079, 4013, 0),
            Position.of(3134, 3970, 0),
            Position.of(3225, 3977, 0)
        ), 1200, mutableListOf(
            "Walker has spawned at wilderness level 62.",
            "Walker has spawned at wilderness level 57.",
            "Walker has spawned at wilderness level 58."
        ), 4, CorruptedNechryarchLoot(),
        "https://www.deviousps.com/discordimg/Walker.png", Pets.WALKER, 1000
    ),
    HUNLLEF(
        9021, mutableListOf(
            Position.of(3251, 3556, 0),
            Position.of(3079, 4020, 0),
            Position.of(3071, 3729, 0)
        ), 1600, mutableListOf(
            "Hunllef has spawned 5 Wilderness North of Varrock Ditch.",
            "Hunllef has spawned North of mage bank in 63 Wildernes.",
            "Hunllef has spawned near the wildernss godwars dungeon at 27 Wilderness."
        ), 4, HunllefTable(),
        "https://www.deviousps.com/discordimg/Hunllef.png", Pets.YOUNGLLEF, 1000
    )
}
