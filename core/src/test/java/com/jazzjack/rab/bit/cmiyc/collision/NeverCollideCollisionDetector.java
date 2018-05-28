package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class NeverCollideCollisionDetector implements CollisionDetector {

    public static final NeverCollideCollisionDetector TEST_INSTANCE = new NeverCollideCollisionDetector();

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        return CollisionResult.noCollision();
    }
}
