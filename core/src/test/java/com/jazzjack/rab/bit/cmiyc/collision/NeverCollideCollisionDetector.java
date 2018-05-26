package com.jazzjack.rab.bit.cmiyc.collision;

public class NeverCollideCollisionDetector implements CollisionDetector {
    @Override
    public CollisionResult collides(Collidable collidable) {
        return CollisionResult.noCollision();
    }
}
