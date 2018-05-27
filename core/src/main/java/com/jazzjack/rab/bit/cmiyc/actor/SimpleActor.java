package com.jazzjack.rab.bit.cmiyc.actor;

import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

public abstract class SimpleActor implements Actor {

    private final String name;
    private int x;
    private int y;

    protected SimpleActor(String name, HasPosition hasPosition) {
        this.name = name;
        this.y = hasPosition.getY();
        this.x = hasPosition.getX();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }
}
