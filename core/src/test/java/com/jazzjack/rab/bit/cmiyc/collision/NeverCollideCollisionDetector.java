package com.jazzjack.rab.bit.cmiyc.collision;

public class NeverCollideCollisionDetector implements CollisionDetector {

    public static final NeverCollideCollisionDetector TEST_INSTANCE = new NeverCollideCollisionDetector();

    @Override
    public CollisionResult collides(Collidable collidable) {
        return CollisionResult.noCollision();
    }
}
