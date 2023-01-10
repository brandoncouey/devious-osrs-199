package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Player
import io.ruin.model.inter.InterfaceHandler

object ClanTab {

    enum class QuestTabPanel(val panelID: Int, val childID: Int) {
        FRIENDS_CHAT(7, 3),
        CLAN_CHAT(701, 4),
        FORM_CLAN(702, 5),
        MINIGAMES(76, 6);

        fun Player.setClanPanel() {
            packetSender.sendInterface(panelID, 707, 7, 1)
        }
    }

    init {
        InterfaceHandler.register(707) { h ->
            for (panel in QuestTabPanel.values()) panel.run {
                h.simpleAction(panel.childID) { it.setClanPanel() }
            }
        }
    }

}