package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.collision.ColissionResolver;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;

public class ActorMovementContext {

    private final CollisionDetector collisionDetector;
    private final ColissionResolver colissionResolver;

    public ActorMovementContext(CollisionDetector collisionDetector, ColissionResolver colissionResolver) {
        this.collisionDetector = collisionDetector;
        this.colissionResolver = colissionResolver;
    }

    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    public ColissionResolver getColissionResolver() {
        return colissionResolver;
    }
}
