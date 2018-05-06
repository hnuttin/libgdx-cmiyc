package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.common.Direction;

import java.util.HashMap;
import java.util.Map;

public class StepNames {

    public static final String HORIZONTAL = "horizontal";
    public static final String VERTICAL = "vertical";

    public static final String ENDING_TOP = "ending-top";
    public static final String ENDING_BOTTOM = "ending-bottom";
    public static final String ENDING_LEFT = "ending-left";
    public static final String ENDING_RIGHT = "ending-right";

    public static final String CORNER_TOP_RIGHT = "corner-top-right";
    public static final String CORNER_TOP_LEFT = "corner-top-left";
    public static final String CORNER_BOTTOM_RIGHT = "corner-bottom-right";
    public static final String CORNER_BOTTOM_LEFT = "corner-bottom-left";

    private static final Map<Direction, String> DIRECTION_TO_ENDING_MAPPING = new HashMap<>();
    private static final Map<DirectionAndNextDirection, String> DIRECTION_AND_NEXT_DIRECTION_MAPPING = new HashMap<>();

    static {
        DIRECTION_TO_ENDING_MAPPING.put(Direction.RIGHT, ENDING_RIGHT);
        DIRECTION_TO_ENDING_MAPPING.put(Direction.LEFT, ENDING_LEFT);
        DIRECTION_TO_ENDING_MAPPING.put(Direction.UP, ENDING_TOP);
        DIRECTION_TO_ENDING_MAPPING.put(Direction.DOWN, ENDING_BOTTOM);

        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.UP, Direction.LEFT), CORNER_TOP_RIGHT);
        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.RIGHT, Direction.DOWN), CORNER_TOP_RIGHT);

        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.UP, Direction.RIGHT), CORNER_TOP_LEFT);
        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.LEFT, Direction.DOWN), CORNER_TOP_LEFT);

        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.DOWN, Direction.LEFT), CORNER_BOTTOM_RIGHT);
        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.RIGHT, Direction.UP), CORNER_BOTTOM_RIGHT);

        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.LEFT, Direction.UP), CORNER_BOTTOM_LEFT);
        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.DOWN, Direction.RIGHT), CORNER_BOTTOM_LEFT);

        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.RIGHT, Direction.RIGHT), HORIZONTAL);
        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.LEFT, Direction.LEFT), HORIZONTAL);

        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.UP, Direction.UP), VERTICAL);
        DIRECTION_AND_NEXT_DIRECTION_MAPPING.put(new DirectionAndNextDirection(Direction.DOWN, Direction.DOWN), VERTICAL);
    }

    public static String getEndingForDirection(Direction direction) {
        return DIRECTION_TO_ENDING_MAPPING.get(direction);
    }

    public static String getForDirectionAndNextDirection(Direction direction, Direction nextDirection) {
        return DIRECTION_AND_NEXT_DIRECTION_MAPPING.get(new DirectionAndNextDirection(direction, nextDirection));
    }

    private static class DirectionAndNextDirection {

        private final Direction direction;
        private final Direction nextDirection;

        private DirectionAndNextDirection(Direction direction, Direction nextDirection) {
            this.direction = direction;
            this.nextDirection = nextDirection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DirectionAndNextDirection that = (DirectionAndNextDirection) o;

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
