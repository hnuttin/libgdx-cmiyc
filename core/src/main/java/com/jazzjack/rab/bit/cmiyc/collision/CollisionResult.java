package com.jazzjack.rab.bit.cmiyc.collision;

public class CollisionResult {

    private final Collidable collidable;

    protected CollisionResult(Collidable collidable) {
        this.collidable = collidable;
    }

    public boolean isCollision() {
        return collidable != null;
    }

    public Collidable getCollidable() {
        return collidable;
    }

    public static CollisionResult collision(Collidable collidable) {
        return new CollisionResult(collidable);
    }

    public static CollisionResult noCollision() {
        return new CollisionResult(null);
    }
}
