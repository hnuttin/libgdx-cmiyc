package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.common.Direction;
import com.jazzjack.rab.bit.cmiyc.common.Position;

class StepResult extends Position implements Collidable {

    private final Direction direction;

    StepResult(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    Direction getDirection() {
        return direction;
    }
}
