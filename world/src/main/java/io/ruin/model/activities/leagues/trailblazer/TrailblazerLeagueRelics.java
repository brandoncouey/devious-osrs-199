package io.ruin.model.activities.leagues.trailblazer;

import lombok.Getter;

/**
 * @author Greco
 * @since 01/08/2022
 */
public enum TrailblazerLeagueRelics {

    ENDLESS_HARVEST(TrailblazerLeagueRelicTiers.TIER_1, 0),
    PRODUCTION_MASTER(TrailblazerLeagueRelicTiers.TIER_1, 0),
    SKILLING_PRODIGY(TrailblazerLeagueRelicTiers.TIER_1, 0),

    FAIRYS_FLIGHT(TrailblazerLeagueRelicTiers.TIER_2, 500),
    ETERNAL_JEWELLER(TrailblazerLeagueRelicTiers.TIER_2, 500),
    LAST_RECALL(TrailblazerLeagueRelicTiers.TIER_2, 500),

    QUICK_SHOT(TrailblazerLeagueRelicTiers.TIER_3, 2000),
    FLUID_STRIKES(TrailblazerLeagueRelicTiers.TIER_3, 2000),
    DOUBLE_CAST(TrailblazerLeagueRelicTiers.TIER_3, 2000),

    TREASURE_SEEKER(TrailblazerLeagueRelicTiers.TIER_4, 4000),
    UNNATURAL_SELECTION(TrailblazerLeagueRelicTiers.TIER_4, 4000),

    THE_BOTANIST(TrailblazerLeagueRelicTiers.TIER_5, 7500),
    INFERNAL_GATHERING(TrailblazerLeagueRelicTiers.TIER_5, 7500),
    EQUILIBRIUM(TrailblazerLeagueRelicTiers.TIER_5, 7500),

    DRAINING_STRIKES(TrailblazerLeagueRelicTiers.TIER_6, 15000),
    EXPLODING_ATTACKS(TrailblazerLeagueRelicTiers.TIER_6, 15000),
    WEAPON_SPECALIST(TrailblazerLeagueRelicTiers.TIER_6, 15000);

    @Getter
    TrailblazerLeagueRelicTiers relicTiers;
    int pointThreshold;

    TrailblazerLeagueRelics(TrailblazerLeagueRelicTiers relicTiers, int pointThreshold) {
        this.relicTiers = relicTiers;
        this.pointThreshold = pointThreshold;
    }
}
