package io.ruin.model.inter.teleports

import com.google.gson.annotations.Expose
import io.ruin.model.map.Position

/**
 * @author Jire
 */
data class TeleportDestination(
    @Expose @JvmField val childID: Int,
    @Expose @JvmField val name: String,
    @Expose @JvmField val position: Position
)