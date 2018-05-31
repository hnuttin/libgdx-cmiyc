package com.jazzjack.rab.bit.cmiyc.game;

public interface EventSubscriber<E extends Event> {

    void handleEvent(E event);
}
