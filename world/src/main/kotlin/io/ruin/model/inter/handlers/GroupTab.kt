package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Player
import io.ruin.model.inter.InterfaceHandler

object GroupTab {

    enum class GroupTabPanel(val panelID: Int, val childID: Int) {
        GROUP_IRON(723, 3),
        FRIENDS_CHAT(7, 4),
        CLAN_CHAT(701, 5),
        FORM_CLAN(702, 6),
        MINIGAMES(76, 7);

        fun Player.setGroupPanel() {
            packetSender.sendInterface(panelID, 727, 8, 1)
        }
    }

    init {
        InterfaceHandler.register(727) { h ->
            for (panel in GroupTabPanel.values()) panel.run {
                h.simpleAction(panel.childID) { it.setGroupPanel() }
            }
        }
    }

}