package io.ruin.model.activities.gambling;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;

public class GambleSpot {
    private Position playerOnePosition;
    private Position playerTwoPosition;
    private Direction faceDirection;
    private boolean used;

    public GambleSpot(Position playerOnePosition, Position playerTwoPosition, Direction faceDirection, boolean used) {
        this.playerOnePosition = playerOnePosition;
        this.playerTwoPosition = playerTwoPosition;
        this.faceDirection = faceDirection;
        this.used = used;
    }

    public Position getPlayerOnePosition() {
        return playerOnePosition;
    }

    public void setPlayerOnePosition(Position playerOnePosition) {
        this.playerOnePosition = playerOnePosition;
    }

    public Position getPlayerTwoPosition() {
        return playerTwoPosition;
    }

    public void setPlayerTwoPosition(Position playerTwoPosition) {
        this.playerTwoPosition = playerTwoPosition;
    }

    public Direction getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(Direction faceDirection) {
        this.faceDirection = faceDirection;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
