package com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step;

import com.jazzjack.rab.bit.cmiyc.actor.SimpleActor;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class Step extends SimpleActor {

    private final Direction direction;

    public Step(String name, StepResult stepResult) {
        super(name, stepResult);
        this.direction = stepResult.getDirection();
    }

    public Direction getDirection() {
        return direction;
    }
}
