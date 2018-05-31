package com.jazzjack.rab.bit.cmiyc.collision;

public class CollisionResolvement {

    private final CollisionResult collisionResult;
    private final boolean resolved;
    private final boolean movementAllowed;

    private CollisionResolvement(CollisionResult collisionResult, boolean resolved, boolean movementAllowed) {
        this.collisionResult = collisionResult;
        this.resolved = resolved;
        this.movementAllowed = movementAllowed;
    }

    public CollisionResult getCollisionResult() {
        return collisionResult;
    }

    public boolean isMovementAllowed() {
        return movementAllowed;
    }

    public boolean isMovementNotAllowed() {
        return !isMovementAllowed();
    }

    public boolean isResolved() {
        return resolved;
    }

    static CollisionResolvement resolvedMovementAllowed(CollisionResult collisionResult) {
        return new CollisionResolvement(collisionResult, true, true);
    }

    static CollisionResolvement resolvedMovementNotAllowed(CollisionResult collisionResult) {
        return new CollisionResolvement(collisionResult, true, false);
    }

    static CollisionResolvement unresolved(CollisionResult collisionResult) {
        return new CollisionResolvement(collisionResult, false, false);
    }
}
