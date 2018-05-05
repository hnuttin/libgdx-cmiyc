package com.jazzjack.rab.bit.actor;

public class Player extends SimpleActor {

    private static final int DEFAULT_SIGHT = 256;

    public Player(float startX, float startY, float size) {
        super("player", startX, startY, size);
    }

    public int getSight() {
        return DEFAULT_SIGHT;
    }

}
