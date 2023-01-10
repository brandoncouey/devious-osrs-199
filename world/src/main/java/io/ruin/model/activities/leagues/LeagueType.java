package io.ruin.model.activities.leagues;

import lombok.Getter;

/**
 * @author Greco
 * @since 01/08/2022
 */
public enum LeagueType {

    NONE(0),

    TWISTED_LEAGUE(1),

    TRAILBLAZER_LEAGUE(2),

    SHATTERED_RELICS(3);

    @Getter
    int id;

    LeagueType(int id) {
        this.id = id;
    }

    public boolean isTwistedLeague() {
        return this == TWISTED_LEAGUE;
    }

    public boolean isTrailblazerLeague() {
        return this == TRAILBLAZER_LEAGUE;
    }

    public boolean isShatteredRelics() {
        return this == SHATTERED_RELICS;
    }

}
