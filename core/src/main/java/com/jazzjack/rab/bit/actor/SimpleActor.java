package com.jazzjack.rab.bit.actor;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.common.Direction;

public class SimpleActor implements Actor {

    private final String name;
    private final float size;

    private float x;
    private float y;

    public SimpleActor(String name, float startX, float startY, float size) {
        this.name = name;
        this.size = size;
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

    @Override
    public float getSize() {
        return size;
    }

    protected void moveToDirection(Direction direction) {
        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    public boolean moveRight(CollisionDetector collisionDetector) {
        float futureX = x + getSize();
        if (!collisionDetector.collides(futureCollidable(futureX, y))) {
            moveRight();
            return true;
        }
        return false;
    }

    private void moveRight() {
        x += getSize();
    }

    public boolean moveLeft(CollisionDetector collisionDetector) {
        float futureX = x - getSize();
        if (!collisionDetector.collides(futureCollidable(futureX, y))) {
            moveLeft();
            return true;
        }
        return false;
    }

    private void moveLeft() {
        x -= getSize();
    }

    public boolean moveUp(CollisionDetector collisionDetector) {
        float futureY = y + getSize();
        if (!collisionDetector.collides(futureCollidable(x, futureY))) {
            moveUp();
            return true;
        }
        return false;
    }

    private void moveUp() {
        y += getSize();
    }

    public boolean moveDown(CollisionDetector collisionDetector) {
        float futureY = y - getSize();
        if (!collisionDetector.collides(futureCollidable(x, futureY))) {
            moveDown();
            return true;
        }
        return false;
    }

    private void moveDown() {
        y -= getSize();
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

            @Override
            public float getSize() {
                return size;
            }
        };
    }
}
