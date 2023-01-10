package io.ruin.content.items

import io.ruin.api.event
import io.ruin.api.messageBox
import io.ruin.api.options
import io.ruin.api.whenInvClick
import io.ruin.model.entity.player.Player
import io.ruin.model.inter.utils.Config
import io.ruin.model.item.Item

/**
 * @author Leviticus
 */
object GroupHardcoreScroll {
    private const val HCGIM_REVIVE_SCROLL = 25478

    init {
        whenInvClick(25478, 1) { player, item ->
            player.redeem(item)
        }
    }

    private fun Player.redeem(item: Item) = event {
        if (options("Redeem Scroll", "No, I don't want to do that") == 1) {
            if (!hcgimdeath && item.id == HCGIM_REVIVE_SCROLL
            ) {
                player.messageBox("You already are a HCGIM OR you didn't die from a HCGIM death")
                return@event
            }
            if (item.id == HCGIM_REVIVE_SCROLL && player.stats.totalLevel <= 999 && hcgimdeath
            ) {
                player.messageBox("You must have a total level of 1000 to redeem this scroll.")
                return@event
            }
            if (item.id == HCGIM_REVIVE_SCROLL && !player.isGroupIronman
            ) {
                player.messageBox("Seems you have De-Ironed. and can no longer use this scroll.")
                return@event
            }
            hcgimdeath = false
            Config.IRONMAN_MODE.set(player, 5)
            player.animate(9159)
            player.messageBox("You have replenished your Hardcore Status!")
            player.graphics(1992)
        }
        item.remove()
    }
}