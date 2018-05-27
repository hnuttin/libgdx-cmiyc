package com.jazzjack.rab.bit.cmiyc.actor;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorMovementContext;
import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators.PositionMutator;

public class MovableActor extends SimpleActor {

    private final ActorMovementContext movementContext;

    public MovableActor(ActorMovementContext movementContext, String name, HasPosition hasPosition) {
        super(name, hasPosition);
        this.movementContext = movementContext;
    }

    public CollisionResult moveToDirection(Direction direction) {
        PositionMutator positionMutator = PositionMutators.forDirection(direction);
        if (positionMutator == null) {
            return CollisionResult.noCollision();
        } else {
            CollisionResult collisionResult = movementContext.getCollisionDetector().collides(futureCollidable(positionMutator), direction);
            if (collisionResult.isUnresolved()) {
                return movementContext.getCollisionResolver().resolveCollision(collisionResult);
            } else {
                setX(positionMutator.mutateX(getX()));
                setY(positionMutator.mutateX(getY()));
            }
            return collisionResult;
        }
    }

    private Collidable futureCollidable(PositionMutator positionMutator) {
        return new Collidable() {
            @Override
            public int getX() {
                return positionMutator.mutateX(MovableActor.this.getX());
            }

            @Override
            public int getY() {
                return positionMutator.mutateY(MovableActor.this.getY());
            }
        };
    }

}
