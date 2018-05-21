package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.actor.SimpleActor;
import com.jazzjack.rab.bit.cmiyc.common.Direction;

public class Step extends SimpleActor {

    private final Direction direction;

    Step(String name, StepResult stepResult) {
        super(name, stepResult);
        this.direction = stepResult.getDirection();
    }

    public Direction getDirection() {
        return direction;
    }
}
