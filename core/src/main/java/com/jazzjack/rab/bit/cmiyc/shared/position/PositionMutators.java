package com.jazzjack.rab.bit.cmiyc.shared.position;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.HashMap;
import java.util.Map;

public class PositionMutators {

    private static final Map<Direction, PositionMutator> POSITION_MUTATOR_MAP = new HashMap<>();

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

    private static abstract class IdentityPositionMutator implements PositionMutator {

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
