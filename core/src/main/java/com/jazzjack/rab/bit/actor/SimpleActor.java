package com.jazzjack.rab.bit.actor;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.collision.CollisionResult;
import com.jazzjack.rab.bit.common.Direction;

public class SimpleActor implements Actor {

    private final String name;

    private float x;
    private float y;

    public SimpleActor(String name, float startX, float startY) {
        this.name = name;
        this.x = startX;
        this.y = startY;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    protected CollisionResult moveToDirection(CollisionDetector collisionDetector, Direction direction) {
        switch (direction) {
            case UP:
                return moveUp(collisionDetector);
            case DOWN:
                return moveDown(collisionDetector);
            case LEFT:
                return moveLeft(collisionDetector);
            case RIGHT:
                return moveRight(collisionDetector);
            default:
                throw new IllegalArgumentException(direction.name());
        }
    }

    public CollisionResult moveRight(CollisionDetector collisionDetector) {
        CollisionResult collisionResult = collisionDetector.collides(futureCollidable(x + 1, y));
        if (!collisionResult.isCollision()) {
            moveRight();
        }
        return collisionResult;
    }

    private void moveRight() {
        x++;
    }

    public CollisionResult moveLeft(CollisionDetector collisionDetector) {
        CollisionResult collisionResult = collisionDetector.collides(futureCollidable(x - 1, y));
        if (!collisionResult.isCollision()) {
            moveLeft();
        }
        return collisionResult;
    }

    private void moveLeft() {
        x--;
    }

    public CollisionResult moveUp(CollisionDetector collisionDetector) {
        CollisionResult collisionResult = collisionDetector.collides(futureCollidable(x, y + 1));
        if (!collisionResult.isCollision()) {
            moveUp();
        }
        return collisionResult;
    }

    private void moveUp() {
        y++;
    }

    public CollisionResult moveDown(CollisionDetector collisionDetector) {
        CollisionResult collisionResult = collisionDetector.collides(futureCollidable(x, y - 1));
        if (!collisionResult.isCollision()) {
            moveDown();
        }
        return collisionResult;
    }

    private void moveDown() {
        y--;
    }

    private Collidable futureCollidable(float x, float y) {
        return new Collidable() {
            @Override
            public float getX() {
                return x;
            }

            @Override
            public float getY() {
                return y;
            }
        };
    }
}
