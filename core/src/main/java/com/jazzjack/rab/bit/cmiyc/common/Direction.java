package com.jazzjack.rab.bit.cmiyc.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Direction {

    LEFT("horizontal"), RIGHT("horizontal"), UP("vertical"), DOWN("vertical");

    private static final Set<Direction> VALUES_AS_SET = new HashSet<>(Arrays.asList(values()));

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

    public static Set<Direction> valuesAsSet() {
        return VALUES_AS_SET;
    }
}
