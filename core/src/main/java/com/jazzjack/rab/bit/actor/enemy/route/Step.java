package com.jazzjack.rab.bit.actor.enemy.route;

import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.common.Direction;

public class Step extends SimpleActor {

    private final Direction direction;

    Step(String name, StepResult stepResult) {
        super(name, stepResult.getX(), stepResult.getY());
        this.direction = stepResult.getDirection();
    }

    public Direction getDirection() {
        return direction;
    }
}
