package com.jazzjack.rab.bit.cmiyc.actor;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorMovementContext;
import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.HasPosition;

import java.util.HashMap;
import java.util.Map;

public class SimpleActor implements Actor {

    private static final Map<Direction, PositionMutator> POSITION_MUTATOR_MAP = new HashMap<>();

    static {
        POSITION_MUTATOR_MAP.put(Direction.LEFT, new LeftPositionMutator());
        POSITION_MUTATOR_MAP.put(Direction.RIGHT, new RightPositionMutator());
        POSITION_MUTATOR_MAP.put(Direction.UP, new UpPositionMutator());
        POSITION_MUTATOR_MAP.put(Direction.DOWN, new DownPositionMutator());
    }

    private final String name;

    private int x;
    private int y;

    public SimpleActor(String name, HasPosition hasPosition) {
        this.name = name;
        this.x = hasPosition.getX();
        this.y = hasPosition.getY();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public CollisionResult moveToDirection(ActorMovementContext context, Direction direction) {
        PositionMutator positionMutator = POSITION_MUTATOR_MAP.get(direction);
        if (positionMutator == null) {
            return CollisionResult.noCollision();
        } else {
            CollisionResult collisionResult = context.getCollisionDetector().collides(futureCollidable(positionMutator), direction);
            if (!collisionResult.isCollision()) {
                x = positionMutator.mutateX(x);
                y = positionMutator.mutateX(y);
            }
            return collisionResult;
        }
    }

    private Collidable futureCollidable(PositionMutator positionMutator) {
        return new Collidable() {
            @Override
            public int getX() {
                return positionMutator.mutateX(x);
            }

            @Override
            public int getY() {
                return positionMutator.mutateY(y);
            }
        };
    }

    private interface PositionMutator {
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
