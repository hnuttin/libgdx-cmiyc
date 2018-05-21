package com.jazzjack.rab.bit.cmiyc.common;

public class Position implements HasPosition {

    private final int x;
    private final int y;

    public Position(HasPosition hasPosition) {
        this(hasPosition.getX(), hasPosition.getY());
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
