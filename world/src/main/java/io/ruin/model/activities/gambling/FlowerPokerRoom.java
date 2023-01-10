package io.ruin.model.activities.gambling;

import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;

public class FlowerPokerRoom {
    private int totalSteps;
    private int usedSteps;
    private Position playerOnePosition;
    private Position playerTwoPosition;
    private Direction faceDirection;
    private boolean used;

    public FlowerPokerRoom(int totalSteps, Position playerOnePosition, Position playerTwoPosition, boolean used, Direction faceDirection) {
        this.totalSteps = totalSteps;
        this.usedSteps = 0;
        this.playerOnePosition = playerOnePosition;
        this.playerTwoPosition = playerTwoPosition;
        this.used = used;
        this.faceDirection = faceDirection;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Direction getFaceDirection() {
        return faceDirection;
    }

    public void setUsedSteps(int usedSteps) {
        this.usedSteps = usedSteps;
    }

    public void setFaceDirection(Direction faceDirection) {
        this.faceDirection = faceDirection;
    }

    public void increaseUsedSteps() {
        this.usedSteps++;
    }

    public int getUsedSteps() {
        return usedSteps;
    }
}
