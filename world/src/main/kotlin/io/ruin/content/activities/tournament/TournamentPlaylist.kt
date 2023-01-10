package io.ruin.content.activities.tournament

import io.ruin.content.activities.tournament.presets.*

/**
 * Different playlist types that a tournament session may use.
 * @author Heaven
 */

enum class TournamentPlaylist(val typeName: String, val attributes: TournamentAttributes) {
    PURE_RANGED_MELEE("Ranged/Melee (1 Def)", PureRangedMeleeTournamentAttributes),
    DMM_TRIBRID("DMM Tribrid", DmmTribridTournamentAttributes),
    DHT("Dharok/Range", dht),
    F2P("Ranged/Melee F2P (40 Def)", f2p),
    VESTA("Vesta Melee", VestaSetup),
    TorvaBrid("Torva Brid", TorvaBridSetup),
    VestaBrid("Vesta Brid", VestaBridSetup),
    NoArm("NoArm Melee", NoArmSetup),
    ZERK("Ranged/Melee Zerk", Zerk)
    ;

    companion object {
        val VALUES = values()
    }
}