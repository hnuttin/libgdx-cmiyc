package com.jazzjack.rab.bit.actor.enemy.route;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Position;

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
