package io.ruin.content.areas.wilderness

import io.ruin.Configuration.WORLD
import io.ruin.api.*
import io.ruin.content.items.GroupHardcoreScroll
import io.ruin.event.GameEvent
import io.ruin.model.World
import io.ruin.model.entity.player.Player
import io.ruin.model.inter.utils.Config
import io.ruin.model.item.Item
import io.ruin.utility.Broadcast

/**
 * @author Heaven
 */
object DeadmanChestEvent {

    /**
     * A flag indicating if this event is enabled.
     */
    private var enabled = true

    /**
     * The last location where the chest has spawned.
     */
    private var lastLocation: DeadmanChestLocation? = null
    val location = DeadmanChestLocation.values().filter { it != lastLocation }.random()
    val chest = DeadmanChest(location)

    /**
     * The reference to the current [DeadmanChest].
     */
    var currentChest: DeadmanChest? = null

    /**
     * The execution interval for this event.
     */
    private const val DELAY = 3.times(60).times(60).times(1000).div(600)

    /**
     * The reference to the current [GameEvent].
     */
    private var currentEvent: GameEvent? = null

    init {
        whenObjClick(33117, 1) { player, _ ->
            currentChest?.loot(player)
        }
        process()
    }

    /**
     * Submits a new global scope event for this [DeadmanChestEvent].
     */
    private fun process() = globalEvent {
        currentEvent = this
        while (World.isPVPWorld()) {
                pause(DELAY)
                execute()
            }
            currentEvent?.kill()
            currentEvent = null
    }

    /**
     * Executes this event by creating a new chest with the respective attributes.
     */
    fun execute() {
            chest.spawn()
            currentChest = chest
            Broadcast.GLOBAL.sendNews("A Deadman Supply Chest has spawned ${location.hint} in " + World.id)
    }

    fun location(): String {
        return location.hint
    }
    fun Player.location() = event {
        player.messageBox("A Deadman Supply Chest has spawned ${location.hint} in " + World.id)
    }
    /**
     * Displays the remaining hours & minutes left until the next [DeadmanChest] spawns.
     */
    fun timeRemaining(): String {
        val ticks = currentEvent?.ticks ?: 0
        return if (ticks > 0) {
            val hours = ticks / 6000
            val minutes = (ticks - hours.times(6000)) / 100
            val display = StringBuilder()
            if (hours > 0) {
                display.append(hours.toString()).append(" ").append("hrs and").append(" ")
            }
            if (minutes > 0) {
                display.append(minutes.toString()).append(" ").append("mins")
            }

            display.toString()
        } else {
            "ACTIVE"
        }
    }
}