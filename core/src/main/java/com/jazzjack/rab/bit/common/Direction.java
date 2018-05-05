package com.jazzjack.rab.bit.common;

public enum Direction {

    LEFT("horizontal"), RIGHT("horizontal"), UP("vertical"), DOWN("vertical");

    private final String orientation;

    Direction(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }

    public Direction getOppositeDirection() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
        }
        throw new IllegalArgumentException(name());
    }
}
