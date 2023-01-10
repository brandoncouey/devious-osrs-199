package io.ruin.model.inter.teleports

/**
 * @author Jire
 */
interface TeleportCategory {

    val childID: Int
    val name: String
    val childIDToDestination: Map<Int, TeleportDestination>

}