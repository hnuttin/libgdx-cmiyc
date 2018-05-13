package com.jazzjack.rab.bit.actor.player;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionResult;

public class PlayerMovementResult extends CollisionResult {

    private final boolean noMovementsLeft;

    private PlayerMovementResult(Collidable collidable, boolean noMovementsLeft) {
        super(collidable);
        this.noMovementsLeft = noMovementsLeft;
    }

    public boolean success() {
        return !noMovementsLeft && !super.isCollision();
    }

    public boolean noMovementsLeft() {
        return noMovementsLeft;
    }

    public static PlayerMovementResult moMovementsLeft() {
        return new PlayerMovementResult(null, true);
    }

    public static PlayerMovementResult fromCollisionResult(CollisionResult collisionResult) {
        return new PlayerMovementResult(collisionResult.getCollidable(), false);
    }
}
