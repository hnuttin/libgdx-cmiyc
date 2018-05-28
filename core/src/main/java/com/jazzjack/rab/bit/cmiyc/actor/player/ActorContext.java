package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;

public class ActorContext {

    private final CollisionDetector collisionDetector;
    private final CollisionResolver collisionResolver;

    public ActorContext(CollisionDetector collisionDetector, CollisionResolver collisionResolver) {
        this.collisionDetector = collisionDetector;
        this.collisionResolver = collisionResolver;
    }

    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    public CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }
}
