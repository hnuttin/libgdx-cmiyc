package com.jazzjack.rab.bit.actor.enemy.route;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.common.Direction;

class StepResult implements Collidable {

    private final float x;
    private final float y;
    private final Direction direction;

    StepResult(float x, float y, Direction direction) {
        this.x = x;
        this.y = y;
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

    Direction getDirection() {
        return direction;
    }
}
