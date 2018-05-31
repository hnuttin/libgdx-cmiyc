package com.jazzjack.rab.bit.cmiyc.actor;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators.PositionMutator;

public class MovableActor extends SimpleActor implements HasPower {

    private final ActorContext context;

    private int power;

    public MovableActor(ActorContext context, String name, HasPosition hasPosition) {
        super(name, hasPosition);
        this.context = context;
        this.power = 1;
    }

    @Override
    public int getPower() {
        return power;
    }

    public CollisionResult moveToDirection(Direction direction) {
        mutatePositionForDirection(direction);
        CollisionResult collisionResult = context.getCollisionDetector().collides(this, direction);
        if (collisionResult.isUnresolved()) {
            CollisionResult collisionResultAfterResolvement = context.getCollisionResolver().resolveCollision(collisionResult);
            if (collisionResultAfterResolvement.isUnresolved()) {
                mutatePositionForDirection(direction.getOppositeDirection());
            }
            return collisionResultAfterResolvement;
        }
        return collisionResult;
    }

    private void mutatePositionForDirection(Direction direction) {
        PositionMutator positionMutator = PositionMutators.forDirection(direction);
        setX(positionMutator.mutateX(getX()));
        setY(positionMutator.mutateY(getY()));
    }

}
