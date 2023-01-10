package io.ruin.model.activities.gambling;

import io.ruin.model.entity.player.Player;

public class GambleQeueSpot {
    private Player playerOne;
    private Player playerTwo;

    public GambleQeueSpot(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }
}
