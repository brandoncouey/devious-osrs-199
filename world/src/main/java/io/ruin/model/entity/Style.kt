package io.ruin.model.entity.player

import io.ruin.Server
import io.ruin.api.filestore.FileStore
import io.ruin.cache.IdentityKit
import io.ruin.cache.Varpbit
import kotlin.system.exitProcess

enum class Style(
    val appearanceIndex: Int,
    val childNext: Int, val childPrevious: Int
) {
    HAIR(0, 13, 12),

    // jaw doesnt exist for female
    JAW(1, 17, 16),
    TORSO(2, 20, 21),
    ARMS(3, 24, 25),
    HANDS(4, 29, 28),
    LEGS(5, 33, 32),
    FEET(6, 37, 36)
    ;

    fun bodyPart(male: Boolean): Int? {
        return if (male) {
            this.appearanceIndex
        } else {
            if (this == JAW) {
                return null
            }
            7 + this.appearanceIndex
        }
    }

    fun change(player: Player, increment: Int) {
        val currentIdentityKitIndex = this.selectedAvailableIdentityKitIndex(player) ?: return

        val availableIdentityKits = this.availableIdentityKits(player.appearance.isMale)

        var nextIdentityKitIndex = currentIdentityKitIndex + increment
        if (nextIdentityKitIndex >= availableIdentityKits.size - 1) {
            nextIdentityKitIndex = 0
        } else if (nextIdentityKitIndex < 0) {
            nextIdentityKitIndex = availableIdentityKits.size - 1
        }

        updateAppearance(player, nextIdentityKitIndex)
        player.appearance.update()
    }

    fun updateAppearance(player: Player, identityKitIndex: Int) {
        player.appearance.styles[appearanceIndex] =
            this.availableIdentityKits(player.appearance.isMale)[identityKitIndex].id
    }

    fun markAppearanceEmpty(player: Player) {
        player.appearance.styles[appearanceIndex] = 255
    }

    fun selectedAvailableIdentityKitIndex(player: Player): Int? {
        val male = player.appearance.isMale
        if (!this.isAvailable(male)) {
            return null
        }

        val availableIdentityKits = this.availableIdentityKits(male)
        if (availableIdentityKits.isEmpty()) {
            return null
        }

        val currentIdentityKitID = player.appearance.styles[appearanceIndex]
        // TODO: instead of storing the identity kit id on appearance, store the index only
        // so the search and lookup is not required here, and also when ids change of identity kits,
        // the index can be used as an actual order.
        val indexOfFirst = availableIdentityKits.indexOfFirst { it.id == currentIdentityKitID }
        if (indexOfFirst == -1) {
            return null
        }
        return indexOfFirst
    }

    fun availableIdentityKits(male: Boolean): List<IdentityKit> {
        val bodyPart = this.bodyPart(male) ?: return emptyList()
        val allAvailable = this.allAvailableKits(bodyPart)
        return allAvailable
    }

    fun allAvailableKits(bodyPart: Int): List<IdentityKit> {
        return ArrayList(bodyParts[bodyPart])
    }

    fun isAvailable(male: Boolean): Boolean {
        if (!male && this == JAW) {
            return false
        }
        return true
    }

    fun next(player: Player) = change(player, 1)
    fun previous(player: Player) = change(player, -1)

    companion object {
        val bodyParts by lazy {
            val result = Array(14) { mutableListOf<IdentityKit>() }
            for (kit in IdentityKit.LOADED) {
                if (!kit.nonSelectable) {
                    result[kit.bodyPartId].add(kit)
                }
            }
            return@lazy result
        }


        @JvmField
        val values = values()

        @JvmStatic
        fun main(args: Array<String>) {
            Server.fileStore = FileStore("Cache/")
            Varpbit.load()
            IdentityKit.load()
            exitProcess(0)
        }

        fun updateAll(player: Player) {
            val male = player.appearance.isMale
            for (value in values) {
                if (value.isAvailable(male)) {
                    // when switches male -> female available index might not be found
                    val index = value.selectedAvailableIdentityKitIndex(player) ?: 0
                    value.updateAppearance(player, index)
                } else {
                    value.markAppearanceEmpty(player)
                }
            }
        }
    }
}