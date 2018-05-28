package com.jazzjack.rab.bit.cmiyc.shared.position;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.EnumMap;

public class PositionMutators {

    private static final EnumMap<Direction, PositionMutator> POSITION_MUTATOR_MAP = new EnumMap<>(Direction.class);

    static {
        POSITION_MUTATOR_MAP.put(Direction.LEFT, new LeftPositionMutator());
        POSITION_MUTATOR_MAP.put(Direction.RIGHT, new RightPositionMutator());
        POSITION_MUTATOR_MAP.put(Direction.UP, new UpPositionMutator());
        POSITION_MUTATOR_MAP.put(Direction.DOWN, new DownPositionMutator());
    }

    private PositionMutators() {}

    public static PositionMutator forDirection(Direction direction) {
        return POSITION_MUTATOR_MAP.get(direction);
    }

    public interface PositionMutator {
        int mutateX(int x);
        int mutateY(int y);
    }

    private static class IdentityPositionMutator implements PositionMutator {

        @Override
        public int mutateX(int x) {
            return x;
        }

        @Override
        public int mutateY(int y) {
            return y;
        }
    }

    private static class LeftPositionMutator extends IdentityPositionMutator {

        @Override
        public int mutateX(int x) {
            return --x;
        }
    }

    private static class RightPositionMutator extends IdentityPositionMutator {
        @Override
        public int mutateX(int x) {
            return ++x;
        }
    }

    private static class UpPositionMutator extends IdentityPositionMutator {
        @Override
        public int mutateY(int y) {
            return ++y;
        }
    }

    private static class DownPositionMutator extends IdentityPositionMutator {
        @Override
        public int mutateY(int y) {
            return --y;
        }
    }
}
