package com.jazzjack.rab.bit.cmiyc.game.input;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface EndPlayerTurnInputEventSubscriber extends EventSubscriber {

    void turnEvent(EndPlayerTurnInputEvent event);
}
