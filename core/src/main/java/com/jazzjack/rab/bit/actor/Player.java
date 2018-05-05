package com.jazzjack.rab.bit.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.jazzjack.rab.bit.CollisionDetector;

public class Player implements Actor {

    private static final int DEFAULT_MOVEMENT_SPEED = 32;
    private static final int DEFAULT_SIGHT = 256;

    private final Texture texture;

    private final CollisionDetector collisionDetector;

    private float x;
    private float y;

    public Player(CollisionDetector collisionDetector, float x, float y) {
        this.collisionDetector = collisionDetector;
        this.x = x;
        this.y = y;
        texture = new Texture("pixel-art/player.png");
    }

    public int getSight() {
        return DEFAULT_SIGHT;
    }

    private int getMovementSpeed() {
        return DEFAULT_MOVEMENT_SPEED;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void movePlayerRight() {
        float futureX = x + getMovementSpeed();
        if (!collisionDetector.collides(playerRectangle(futureX, y))) {
            x = futureX;
        }
    }

    public void movePlayerLeft() {
        float futureX = x - getMovementSpeed();
        if (!collisionDetector.collides(playerRectangle(futureX, y))) {
            x = futureX;
        }
    }

    public void movePlayerUp() {
        float futureY = y + getMovementSpeed();
        if (!collisionDetector.collides(playerRectangle(x, futureY))) {
            y = futureY;
        }
    }

    public void movePlayerDown() {
        float futureY = y - getMovementSpeed();
        if (!collisionDetector.collides(playerRectangle(x, futureY))) {
            y = futureY;
        }
    }

    private Rectangle playerRectangle(float x, float y) {
        return new Rectangle(x, y, DEFAULT_MOVEMENT_SPEED, DEFAULT_MOVEMENT_SPEED);
    }
}
