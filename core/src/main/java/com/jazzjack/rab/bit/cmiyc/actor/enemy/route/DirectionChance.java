package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.shared.Chance;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class DirectionChance implements Chance {

    private final Direction direction;
    private final int percentage;

    DirectionChance(Direction direction, int percentage) {
        this.direction = direction;
        this.percentage = percentage;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }
}
