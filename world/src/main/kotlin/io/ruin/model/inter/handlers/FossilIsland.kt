package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Player
import io.ruin.model.inter.AccessMasks
import io.ruin.model.inter.InterfaceHandler
import io.ruin.model.inter.InterfaceType
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.map.`object`.GameObject
import io.ruin.model.map.`object`.actions.ObjectAction

object FossilIsland {

    const val INTERFACE_ID = 605

    init {

        ObjectAction.register(30987, "open") { player: Player, obj: GameObject? ->
            player.openInterface(InterfaceType.MAIN, INTERFACE_ID)
            player.packetSender.sendAccessMask(INTERFACE_ID, 11, 0, 29, AccessMasks.ClickOp1)
        }

        ObjectAction.register(30988, "open") { player: Player, obj: GameObject? ->
            player.openInterface(InterfaceType.MAIN, INTERFACE_ID)
        }


        InterfaceHandler.register(INTERFACE_ID) { h ->

            h.actions[4] = DefaultAction { p, _, _, _ ->
                //withdraw all
                p.sendMessage("You have to left click the item to withdraw the item!")
            }

            h.actions[6] = DefaultAction { p, _, _, _ ->
                //deposit all
                FossilIslandStorage.fossilstorage(p)
            }


            h.actions[11] = DefaultAction { p, _, slot, _ ->
                when (slot) {
                    0 -> {
                        FossilIslandStorage.withdrawStore(p, 21570)
                    }
                    1 -> {
                        FossilIslandStorage.withdrawStore(p, 21572)
                    }
                    2 -> {
                        FossilIslandStorage.withdrawStore(p, 21574)
                    }
                    3 -> {
                        FossilIslandStorage.withdrawStore(p, 21576)
                    }
                    4 -> {
                        FossilIslandStorage.withdrawStore(p, 21578)
                    }
                    5 -> {
                        FossilIslandStorage.withdrawStore(p, 21580)
                    }
                    6 -> {
                        FossilIslandStorage.withdrawStore(p, 21582)
                    }
                    7 -> {
                        FossilIslandStorage.withdrawStore(p, 21584)
                    }
                    8 -> {
                        FossilIslandStorage.withdrawStore(p, 21586)
                    }
                    9 -> {
                        FossilIslandStorage.withdrawStore(p, 21588)
                    }
                    10 -> {
                        FossilIslandStorage.withdrawStore(p, 21600)
                    }
                    11 -> {
                        FossilIslandStorage.withdrawStore(p, 21602)
                    }
                    12 -> {
                        FossilIslandStorage.withdrawStore(p, 21604)
                    }
                    13 -> {
                        FossilIslandStorage.withdrawStore(p, 21606)
                    }
                    14 -> {
                        FossilIslandStorage.withdrawStore(p, 21608)
                    }
                    15 -> {
                        FossilIslandStorage.withdrawStore(p, 21590)
                    }
                    16 -> {
                        FossilIslandStorage.withdrawStore(p, 21592)
                    }
                    17 -> {
                        FossilIslandStorage.withdrawStore(p, 21594)
                    }
                    18 -> {
                        FossilIslandStorage.withdrawStore(p, 21596)
                    }
                    19 -> {
                        FossilIslandStorage.withdrawStore(p, 21598)
                    }
                    20 -> {
                        FossilIslandStorage.withdrawStore(p, 21610)
                    }
                    21 -> {
                        FossilIslandStorage.withdrawStore(p, 21612)
                    }
                    22 -> {
                        FossilIslandStorage.withdrawStore(p, 21614)
                    }
                    23 -> {
                        FossilIslandStorage.withdrawStore(p, 21616)
                    }
                    24 -> {
                        FossilIslandStorage.withdrawStore(p, 21618)
                    }
                    25 -> {
                        FossilIslandStorage.withdrawStore(p, 21620)
                    }
                    26 -> {
                        FossilIslandStorage.withdrawStore(p, 21562)
                    }
                    27 -> {
                        FossilIslandStorage.withdrawStore(p, 21564)
                    }
                    28 -> {
                        FossilIslandStorage.withdrawStore(p, 21566)
                    }
                    29 -> {
                        FossilIslandStorage.withdrawStore(p, 21568)
                    }

                }
            }

        }

    }

}