package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.common.Direction;

class StepResult implements Collidable {

    private final float x;
    private final float y;
    private final float size;
    private final Direction direction;

    StepResult(float x, float y, float size, Direction direction) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.direction = direction;
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

    Direction getDirection() {
        return direction;
    }
}
