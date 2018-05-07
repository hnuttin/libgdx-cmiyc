package com.jazzjack.rab.bit.actor;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;

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

    public boolean moveRight(CollisionDetector collisionDetector) {
        float futureX = x + getSize();
        if (!collisionDetector.collides(futureCollidable(futureX, y))) {
            x = futureX;
            return true;
        }
        return false;
    }

    public boolean moveLeft(CollisionDetector collisionDetector) {
        float futureX = x - getSize();
        if (!collisionDetector.collides(futureCollidable(futureX, y))) {
            x = futureX;
            return true;
        }
        return false;
    }

    public boolean moveUp(CollisionDetector collisionDetector) {
        float futureY = y + getSize();
        if (!collisionDetector.collides(futureCollidable(x, futureY))) {
            y = futureY;
            return true;
        }
        return false;
    }

    public boolean moveDown(CollisionDetector collisionDetector) {
        float futureY = y - getSize();
        if (!collisionDetector.collides(futureCollidable(x, futureY))) {
            y = futureY;
            return true;
        }
        return false;
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
