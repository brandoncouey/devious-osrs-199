package io.ruin.content.activities.tournament

import io.ruin.api.globalEvent
import io.ruin.model.entity.shared.listeners.SpawnListener

object Lisa {

    private const val ID = 7316
    private val NPC = SpawnListener.find(ID)[0]


    init {
        pulse()
    }

    private fun pulse() = globalEvent {
        while (true) {
            pause(15)
            val currentQueueSize = TournamentManager.Lobby.players.size
            if (currentQueueSize < 4) {
                NPC.forceText("A tournament is running, we currently need ${4 - currentQueueSize} more participants!")
            } else {
                NPC.forceText("A tournament is starting shortly!")
            }
        }
    }
}
