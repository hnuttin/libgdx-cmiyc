package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class CollisionResult {

    private final Collidable sourceCollidable;
    private final Collidable targetCollidable;
    private final Direction direction;

    private final boolean resolved;

    protected CollisionResult() {
        this(null, null, null, true);
    }

    protected CollisionResult(Collidable sourceCollidable, Collidable targetCollidable, Direction direction, boolean resolved) {
        this.sourceCollidable = sourceCollidable;
        this.targetCollidable = targetCollidable;
        this.direction = direction;
        this.resolved = resolved;
    }

    public boolean wasCollision() {
        return targetCollidable != null;
    }

    public boolean isUnresolved() {
        return targetCollidable != null && !resolved;
    }

    public boolean isResolved() {
        return targetCollidable == null || resolved;
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
        return new CollisionResult(sourceCollidable, targetCollidable, direction, false);
    }

    public static CollisionResult resolved(Collidable sourceCollidable, Collidable targetCollidable, Direction direction) {
        return new CollisionResult(sourceCollidable, targetCollidable, direction, true);
    }

    public static CollisionResult noCollision() {
        return new CollisionResult();
    }
}
