package io.ruin.model.activities.leagues.twisted;

import lombok.Getter;

/**
 * @author Greco
 * @since 01/08/2022
 */
public enum TwistedLeagueRelics {

    ABYSSAL_ACCUMULATOR(TwistedLeagueRelicTiers.TIER_1, 0),
    ENDLESS_ENDURANCE(TwistedLeagueRelicTiers.TIER_1, 0),
    DARK_ALTAR_DEVOTION(TwistedLeagueRelicTiers.TIER_1, 0),

    HARDCORE_HARVESTER(TwistedLeagueRelicTiers.TIER_2, 400),
    ARCANE_COURIER(TwistedLeagueRelicTiers.TIER_2, 400),
    UNNATURAL_SELECTION(TwistedLeagueRelicTiers.TIER_2, 400),

    EYE_OF_THE_ARTISAN(TwistedLeagueRelicTiers.TIER_3, 1200),
    GIFT_OF_THE_GATHERER(TwistedLeagueRelicTiers.TIER_3, 1200),
    WAY_OF_THE_WARRIOR(TwistedLeagueRelicTiers.TIER_3, 1200),

    SPIRIT_OF_DINH(TwistedLeagueRelicTiers.TIER_4, 2500),
    KONARS_BLESSING(TwistedLeagueRelicTiers.TIER_4, 2500),
    TREASURE_SEEKER(TwistedLeagueRelicTiers.TIER_4, 2500),

    XERICS_FOCUS(TwistedLeagueRelicTiers.TIER_5, 5000),
    XERICS_RESILIENCE(TwistedLeagueRelicTiers.TIER_5, 5000),
    XERICS_WISDOM(TwistedLeagueRelicTiers.TIER_5, 5000);

    @Getter
    TwistedLeagueRelicTiers relicTiers;
    int pointThreshold;

    TwistedLeagueRelics(TwistedLeagueRelicTiers relicTiers, int pointThreshold) {
        this.relicTiers = relicTiers;
        this.pointThreshold = pointThreshold;
    }
}
