package com.jazzjack.rab.bit.cmiyc.game.input;

import com.jazzjack.rab.bit.cmiyc.event.Event;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class PlayerMovementInputEvent implements Event {

    private final Direction direction;

    public PlayerMovementInputEvent(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
