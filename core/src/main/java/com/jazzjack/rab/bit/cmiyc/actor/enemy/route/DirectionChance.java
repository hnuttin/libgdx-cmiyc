package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.shared.Chance;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class DirectionChance implements Chance {

    private final Direction direction;
    private final int percentage;

    public DirectionChance(Direction direction, int percentage) {
        this.direction = direction;
        this.percentage = percentage;
    }

    @Override
    public int getPercentage() {
        return 0;
    }
}
