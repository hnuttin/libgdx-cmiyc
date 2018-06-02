package com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step;

import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

public class StepResult extends Position implements Collidable {

    private final Direction direction;

    public StepResult(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
