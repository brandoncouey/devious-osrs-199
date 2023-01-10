package io.ruin.model.activities.leagues.trailblazer;

import lombok.Getter;

/**
 * @author Greco
 * @since 01/08/2022
 */
public enum TrailblazerLeagueRelicTiers {

    TIER_1(1),
    TIER_2(2),
    TIER_3(3),
    TIER_4(4),
    TIER_5(5),
    TIER_6(6);

    @Getter
    int tier;

    TrailblazerLeagueRelicTiers(int tier) {
        this.tier = tier;
    }
}
