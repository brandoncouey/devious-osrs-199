package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Style
import io.ruin.model.inter.InterfaceHandler
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.inter.utils.Config

/**
 * @author Luke
 */
object CharacterCreator {
    const val INTERFACE_ID = 679

    init {
        InterfaceHandler.register(INTERFACE_ID) { h ->
            for (value in Style.values) {
                h.simpleAction(value.childNext, value::next)
                h.simpleAction(value.childPrevious, value::previous)
            }
            var hairc = 0
            h.actions[43] = DefaultAction { p, _, _, _ ->
                if (hairc == 0) {
                    hairc = 25
                }
                p.appearance.colors[0] = hairc--
                p.appearance.update()
            }

            h.actions[44] = DefaultAction { p, _, _, _ ->
                if (hairc == 25) {
                    hairc = 0
                }
                p.appearance.colors[0] = hairc++
                p.appearance.update()

            }

            var torsoc = 0
            h.actions[47] = DefaultAction { p, _, _, _ ->
                if (torsoc == 0) {
                    torsoc = 29
                }
                p.appearance.colors[1] = torsoc--
                p.appearance.update()
            }

            h.actions[48] = DefaultAction { p, _, _, _ ->
                if (torsoc == 29) {
                    torsoc = 0
                }
                p.appearance.colors[1] = torsoc++
                p.appearance.update()
            }

            var legsc = 0
            h.actions[51] = DefaultAction { p, _, _, _ ->
                if (legsc == 0) {
                    legsc = 29
                }
                p.appearance.colors[2] = legsc--
                p.appearance.update()
            }

            h.actions[52] = DefaultAction { p, _, _, _ ->
                if (legsc == 29) {
                    legsc = 0
                }
                p.appearance.colors[2] = legsc++
                p.appearance.update()
            }

            var feetc = 0
            h.actions[55] = DefaultAction { p, _, _, _ ->
                if (feetc == 0) {
                    feetc = 7
                }
                p.appearance.colors[3] = feetc--
                p.appearance.update()
            }

            h.actions[56] = DefaultAction { p, _, _, _ ->
                if (feetc == 7) {
                    feetc = 0
                }
                p.appearance.colors[3] = feetc++
                p.appearance.update()
            }

            var skinc = 0
            h.actions[59] = DefaultAction { p, _, _, _ ->
                if (skinc == 0) {
                    skinc = 8
                }
                p.appearance.colors[4] = skinc--
                p.appearance.update()
            }

            h.actions[60] = DefaultAction { p, _, _, _ ->
                if (skinc == 8) {
                    skinc = 0
                }
                p.appearance.colors[4] = skinc++
                p.appearance.update()
            }

            h.actions[65] = DefaultAction { p, _, _, _ ->
                Config.GENDER_VAR.set(p, 0)
                Config.GENDER.set(p, 0)
                p.appearance.setGender(0)
                Style.updateAll(p)
                p.appearance.update()
            }

            h.actions[66] = DefaultAction { p, _, _, _ ->
                Config.GENDER_VAR.set(p, 1)
                Config.GENDER.set(p, 1)
                p.appearance.setGender(1)
                Style.updateAll(p)
                p.appearance.update()
            }

            h.actions[68] = DefaultAction { p, _, _, _ ->
                p.appearance.update()
                p.closeInterfaces()
            }
        }
    }


}