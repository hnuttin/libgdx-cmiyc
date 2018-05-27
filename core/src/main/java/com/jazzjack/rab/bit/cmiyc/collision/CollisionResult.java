package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class CollisionResult {

    private final Collidable sourceCollidable;
    private final Collidable targetCollidable;
    private final Direction direction;

    protected CollisionResult(Collidable sourceCollidable, Collidable targetCollidable, Direction direction) {
        this.sourceCollidable = sourceCollidable;
        this.targetCollidable = targetCollidable;
        this.direction = direction;
    }

    public boolean isCollision() {
        return targetCollidable != null;
    }

    public Collidable getSourceCollidable() {
        return sourceCollidable;
    }

    public Collidable getTargetCollidable() {
        return targetCollidable;
    }

    public Direction getDirection() {
        return direction;
    }

    public static CollisionResult unresolved(Collidable sourceCollidable, Collidable targetCollidable, Direction direction) {
        return new CollisionResult(sourceCollidable, targetCollidable, direction);
    }

    public static CollisionResult noCollision() {
        return new CollisionResult(null, null, null);
    }
}
