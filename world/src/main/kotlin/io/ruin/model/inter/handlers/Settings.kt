package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Player
import io.ruin.model.inter.AccessMasks
import io.ruin.model.inter.InterfaceHandler
import io.ruin.model.inter.InterfaceType
import io.ruin.model.inter.WidgetID
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.inter.dialogue.OptionsDialogue
import io.ruin.model.inter.utils.Config
import io.ruin.model.inter.utils.Option
import io.ruin.network.incoming.desktop.handlers.DisplayHandler
import java.util.function.BiConsumer

/**
 * @author Jire
 */
object Settings {

    const val INTERFACE_ID = WidgetID.SETTINGS_GROUP_ID

    @JvmStatic
    fun Player.openSettings() {
        if (isVisibleInterface(INTERFACE_ID)) return

        resetSettings()
        openInterface(InterfaceType.MAIN, INTERFACE_ID)

        packetSender.run {
            sendAccessMask(INTERFACE_ID, 23, 0, 7, AccessMasks.ClickOp1)
            sendAccessMask(INTERFACE_ID, 19, 0, 1000, AccessMasks.ClickOp1)
            sendAccessMask(INTERFACE_ID, 25, -1, -1, AccessMasks.ClickOp1)

            for (slot in 2..41 step 3)
                sendAccessMask(INTERFACE_ID, 28, slot, slot, AccessMasks.ClickOp1)
        }
    }

    @JvmStatic
    fun Player.resetSettings() {
        selectedSettingChild = -1
        restoreChatInput()
    }

    @JvmStatic
    fun Player.restoreChatInput() {
        packetSender.sendClientScript(2158)
        Config.SettingSearch.set(player, 0)
        Config.SettingSearch1.set(player, 0)
    }

    init {
        // settings_client_mode CS2 i, args [3998, 0]
        // settings_interface_scaling CS2 i, args [2358, 0]
        InterfaceHandler.register(INTERFACE_ID) { h ->
            h.closedAction = BiConsumer { p, _ -> p.resetSettings() }
            h.simpleAction(4) { p -> p.closeInterfaces() }
            h.simpleAction(25) { p ->
                p.selectedSettingChild = -1
            }

            h.actions[23] = DefaultAction { p, _, _, slot, _ ->
                p.selectedSettingMenu = slot
            }

            h.actions[19] = DefaultAction { p, _, _, slot, _ ->
                p.selectedSettingChild = slot
                when (p.selectedSettingMenu) {
                    3 -> {
                        when (slot) {
                            3 -> Config.CHAT_EFFECTS.toggle(p)
                            4 -> {
                                Config.SPLIT_PRIVATE_CHAT.toggle(p)
                                p.packetSender.sendClientScript(83, "")
                            }
                            5 -> {
                                if (Config.SHIFT_DROP[p] == 0) {
                                    Config.SHIFT_DROP[p] = 1
                                    p.Shift_Drop = true
                                } else {
                                    Config.SHIFT_DROP[p] = 0
                                    p.Shift_Drop = false
                                }
                            }
                            26 -> p.dialogue(
                                false, OptionsDialogue(
                                    Option("Use OSRS Keybinds", Runnable {
                                        for (c in Config.KEYBINDS) c.reset(p)
                                        Config.ESCAPE_CLOSES.reset(p)
                                    }),
                                    Option("Use Pre-EoC Keybinds", Runnable {
                                        for (i in Config.KEYBINDS.indices) {
                                            val c = Config.KEYBINDS[i]
                                            if (i == 0) c[p] = 5 else if (i in 3..6) c[p] = i - 2 else c[p] = 0
                                        }
                                        Config.ESCAPE_CLOSES.reset(p)
                                    }),
                                    Option("Keep Current Keybinds", p::closeDialogue)
                                )
                            )
                            28 -> {
                                if (Config.ESCAPE_CLOSES[p] == 0) {
                                    Config.ESCAPE_CLOSES[p] = 1
                                    p.ESC_Close = true
                                } else {
                                    Config.ESCAPE_CLOSES[p] = 0
                                    p.ESC_Close = false
                                }
                            }
                        }
                    }
                    4 -> {
                        when (slot) {
                            5 -> Config.ZOOMING_DISABLED.toggle(p)
                        }
                    }
                    6 -> {
                        when (slot) {
                            8 -> Config.DATA_ORBS.toggle(p)
                        }
                    }
                }
            }

            h.actions[28] = DefaultAction { p, _, _, slot, _ ->
                val dropSlot = (slot - 2) / 3
                when (p.selectedSettingMenu) {
                    3 -> {
                        when (p.selectedSettingChild) {
                            1 -> Config.PLAYER_ATTACK_OPTION[p] = dropSlot
                            2 -> Config.NPC_ATTACK_OPTION[p] = dropSlot
                            in 12..25 -> {
                                val configID = p.selectedSettingChild - 12
                                val selectedKeybindConfig = Config.KEYBINDS[configID]!!

                                if (dropSlot > 0) for (c in Config.KEYBINDS) {
                                    if (c !== selectedKeybindConfig && c[p] == dropSlot) {
                                        c[p] = 0
                                        break
                                    }
                                }
                                selectedKeybindConfig.set(p, dropSlot)
                            }
                        }
                    }
                    6 -> {
                        when (p.selectedSettingChild) {
                            1 -> DisplayHandler.setDisplayMode(p, dropSlot + 1)
                        }
                    }
                }

                p.selectedSettingChild = -1
            }

            h.simpleAction(26) { p ->
                when (p.selectedSettingMenu) {
                    3 -> {

                    }
                }
            }
        }
    }

}