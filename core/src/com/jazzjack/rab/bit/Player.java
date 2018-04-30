package com.jazzjack.rab.bit;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player {

    private static final int DEFAULT_MOVEMENT_SPEED = 24;
    private static final int DEFAULT_SIGHT = 200;
    public static final int DEFAULT_SIZE = 50;

    private final TextureAtlas.AtlasRegion playerTexture;

    private int x = 0;
    private int y = 0;

    public Player() {
        final TextureAtlas spritesAtlas = new TextureAtlas("sprites.txt");
        playerTexture = spritesAtlas.findRegion("hammer");
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

    public TextureAtlas.AtlasRegion getPlayerTexture() {
        return playerTexture;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void movePlayerRight() {
        x += getMovementSpeed();
    }

    public void movePlayerLeft() {
        x -= getMovementSpeed();
    }

    public void movePlayerUp() {
        y += getMovementSpeed();
    }

    public void movePlayerDown() {
        y -= getMovementSpeed();
    }
}
