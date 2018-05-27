package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;

public class PlayerMovementResult extends CollisionResult {

    private final boolean noMovementsLeft;

    private PlayerMovementResult(Collidable collidable, boolean noMovementsLeft) {
        super(sourceCollidable, collidable, direction);
        this.noMovementsLeft = noMovementsLeft;
    }

    public boolean success() {
        return !noMovementsLeft && !super.isCollision();
    }

    public boolean noMovementsLeft() {
        return noMovementsLeft;
    }

    public static PlayerMovementResult noMovementsLeftResult() {
        return new PlayerMovementResult(null, true);
    }

    public static PlayerMovementResult fromCollisionResult(CollisionResult collisionResult) {
        return new PlayerMovementResult(collisionResult.getTargetCollidable(), false);
    }
}
