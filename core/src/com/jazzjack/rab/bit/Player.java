package com.jazzjack.rab.bit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private static final int DEFAULT_MOVEMENT_SPEED = 32;
    private static final int DEFAULT_SIGHT = 256;
    private static final int DEFAULT_SIZE = 32;

    private final Texture playerTexture;

    private final CollisionDetector collisionDetector;

    private float x;
    private float y;

    public Player(CollisionDetector collisionDetector, float x, float y) {
        this.collisionDetector = collisionDetector;
        this.x = x;
        this.y = y;
        playerTexture = new Texture("pixel-art/player.png");
    }

    public int getSight() {
        return DEFAULT_SIGHT;
    }

    public int getWidth() {
        return DEFAULT_SIZE;
    }

    public int getHeight() {
        return DEFAULT_SIZE;
    }

    private int getMovementSpeed() {
        return DEFAULT_MOVEMENT_SPEED;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public float getX() {
        return x;
    }

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
