package io.ruin.model.activities.leagues;

/**
 * @author Greco
 * @since 01/08/2022
 */
public enum LeagueTypePoints {

    EASY(LeagueTaskDifficulty.EASY, 10),
    MEDIUM(LeagueTaskDifficulty.MEDIUM, 50),
    HARD(LeagueTaskDifficulty.HARD, 100),
    ELITE(LeagueTaskDifficulty.ELITE, 250),
    MASTER(LeagueTaskDifficulty.MASTER, 500);

    LeagueTaskDifficulty leagueTaskDifficulty;
    int points;

    LeagueTypePoints(LeagueTaskDifficulty leagueTaskDifficulty, int points) {
        this.leagueTaskDifficulty = leagueTaskDifficulty;
        this.points = points;
    }
}
