package com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class StepNames {

    private StepNames() {
        // never instantiate
    }

    public static final String HORIZONTAL = "route-horizontal";
    public static final String VERTICAL = "route-vertical";

    public static final String ENDING_TOP = "route-ending-top";
    public static final String ENDING_BOTTOM = "route-ending-bottom";
    public static final String ENDING_LEFT = "route-ending-left";
    public static final String ENDING_RIGHT = "route-ending-right";

    public static final String CORNER_TOP_RIGHT = "route-corner-top-right";
    public static final String CORNER_TOP_LEFT = "route-corner-top-left";
    public static final String CORNER_BOTTOM_RIGHT = "route-corner-bottom-right";
    public static final String CORNER_BOTTOM_LEFT = "route-corner-bottom-left";

    private static final EnumMap<Direction, String> DIRECTION_TO_ENDING_MAPPING = new EnumMap<>(Direction.class);
    private static final Map<TwoConsecutiveDirections, String> TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING = new HashMap<>();

    static {
        DIRECTION_TO_ENDING_MAPPING.put(Direction.RIGHT, ENDING_RIGHT);
        DIRECTION_TO_ENDING_MAPPING.put(Direction.LEFT, ENDING_LEFT);
        DIRECTION_TO_ENDING_MAPPING.put(Direction.UP, ENDING_TOP);
        DIRECTION_TO_ENDING_MAPPING.put(Direction.DOWN, ENDING_BOTTOM);

        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.UP, Direction.LEFT), CORNER_TOP_RIGHT);
        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.RIGHT, Direction.DOWN), CORNER_TOP_RIGHT);

        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.UP, Direction.RIGHT), CORNER_TOP_LEFT);
        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.LEFT, Direction.DOWN), CORNER_TOP_LEFT);

        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.DOWN, Direction.LEFT), CORNER_BOTTOM_RIGHT);
        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.RIGHT, Direction.UP), CORNER_BOTTOM_RIGHT);

        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.LEFT, Direction.UP), CORNER_BOTTOM_LEFT);
        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.DOWN, Direction.RIGHT), CORNER_BOTTOM_LEFT);

        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.RIGHT, Direction.RIGHT), HORIZONTAL);
        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.LEFT, Direction.LEFT), HORIZONTAL);

        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.UP, Direction.UP), VERTICAL);
        TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.put(new TwoConsecutiveDirections(Direction.DOWN, Direction.DOWN), VERTICAL);
    }

    public static String getEndingForDirection(Direction direction) {
        return DIRECTION_TO_ENDING_MAPPING.get(direction);
    }

    public static String getBasedOnTwoConsecutiveDirections(Direction direction, Direction nextDirection) {
        return TWO_CONSECUTIVE_DIRECTIONS_TO_STEPNAME_MAPPING.get(new TwoConsecutiveDirections(direction, nextDirection));
    }

    private static class TwoConsecutiveDirections {

        private final Direction direction;
        private final Direction nextDirection;

        private TwoConsecutiveDirections(Direction direction, Direction nextDirection) {
            this.direction = direction;
            this.nextDirection = nextDirection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TwoConsecutiveDirections that = (TwoConsecutiveDirections) o;

            if (direction != that.direction) return false;
            return nextDirection == that.nextDirection;
        }

        @Override
        public int hashCode() {
            int result = direction.hashCode();
            result = 31 * result + nextDirection.hashCode();
            return result;
        }
    }
}
