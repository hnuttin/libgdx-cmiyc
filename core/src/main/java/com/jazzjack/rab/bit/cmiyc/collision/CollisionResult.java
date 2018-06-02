package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class CollisionResult {

    private final Collidable source;
    private final Collidable target;
    private final Direction direction;

    private CollisionResult() {
        this(null, null, null);
    }

    private CollisionResult(Collidable source, Collidable target, Direction direction) {
        this.source = source;
        this.target = target;
        this.direction = direction;
    }

    public boolean isCollision() {
        return target != null;
    }

    public boolean isNoCollision() {
        return !isCollision();
    }

    public Collidable getSource() {
        return source;
    }

    public Collidable getTarget() {
        return target;
    }

    public Direction getDirection() {
        return direction;
    }

    public static CollisionResult collision(Collidable source, Collidable target, Direction direction) {
        return new CollisionResult(source, target, direction);
    }

    public static CollisionResult noCollision() {
        return new CollisionResult();
    }
}
