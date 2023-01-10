package io.ruin.model.activities.leagues.twisted;

import lombok.Getter;

/**
 * @author Greco
 * @since 01/08/2022
 */
public enum TwistedLeagueRelicTiers {

    TIER_1(1),
    TIER_2(2),
    TIER_3(3),
    TIER_4(4),
    TIER_5(5);

    @Getter
    int tier;

    TwistedLeagueRelicTiers(int tier) {
        this.tier = tier;
    }

}
