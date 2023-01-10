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
object HCReviveScroll {
    private const val HCIM_REVIVE_SCROLL = 25481

    init {
        whenInvClick(25481, 1) { player, item ->
            player.redeem(item)
        }
    }

    private fun Player.redeem(item: Item) = event {
        if (options("Redeem Scroll", "No, I don't want to do that") == 1) {
            if (!hcimdeath && item.id == HCIM_REVIVE_SCROLL
            ) {
                player.messageBox("You already are a HCIM OR you didn't die from a HCIM death")
                return@event
            }
            if (item.id == HCIM_REVIVE_SCROLL && player.stats.totalLevel <= 999 && hcimdeath
            ) {
                player.messageBox("You must have a total level of 1000 to redeem this scroll.")
                return@event
            }
            if (item.id == HCIM_REVIVE_SCROLL && !player.gameMode.isIronMan
            ) {
                player.messageBox("Seems you have De-Ironed. and can no longer use this scroll.")
                return@event
            }
            hcimdeath = false
            Config.IRONMAN_MODE.set(player, 3)
            player.animate(9159)
            player.messageBox("You have replenished your Hardcore Status!")
            player.graphics(1992)
        }
        item.remove()
    }
}