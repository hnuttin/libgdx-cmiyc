package com.jazzjack.rab.bit.actor;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;
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
        if (!collisionDetector.collides(futureCollidable(x + 1, y))) {
            moveRight();
            return true;
        }
        return false;
    }

    private void moveRight() {
        x++;
    }

    public boolean moveLeft(CollisionDetector collisionDetector) {
        if (!collisionDetector.collides(futureCollidable(x - 1, y))) {
            moveLeft();
            return true;
        }
        return false;
    }

    private void moveLeft() {
        x--;
    }

    public boolean moveUp(CollisionDetector collisionDetector) {
        if (!collisionDetector.collides(futureCollidable(x, y + 1))) {
            moveUp();
            return true;
        }
        return false;
    }

    private void moveUp() {
        y++;
    }

    public boolean moveDown(CollisionDetector collisionDetector) {
        if (!collisionDetector.collides(futureCollidable(x, y - 1))) {
            moveDown();
            return true;
        }
        return false;
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
