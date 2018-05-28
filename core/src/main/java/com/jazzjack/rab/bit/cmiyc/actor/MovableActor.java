package com.jazzjack.rab.bit.cmiyc.actor;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators.PositionMutator;

public class MovableActor extends SimpleActor {

    private final ActorContext context;

    public MovableActor(ActorContext context, String name, HasPosition hasPosition) {
        super(name, hasPosition);
        this.context = context;
    }

    public CollisionResult moveToDirection(Direction direction) {
        mutatePositionForDirection(direction);
        CollisionResult collisionResult = context.getCollisionDetector().collides(this, direction);
        if (collisionResult.isUnresolved()) {
            mutatePositionForDirection(direction.getOppositeDirection());
            return context.getCollisionResolver().resolveCollision(collisionResult);
        }
        return collisionResult;
    }

    private void mutatePositionForDirection(Direction direction) {
        PositionMutator positionMutator = PositionMutators.forDirection(direction);
        setX(positionMutator.mutateX(getX()));
        setY(positionMutator.mutateY(getY()));
    }

}
