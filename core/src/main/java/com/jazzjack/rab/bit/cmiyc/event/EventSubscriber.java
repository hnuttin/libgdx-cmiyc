package com.jazzjack.rab.bit.cmiyc.event;

public interface EventSubscriber<E extends Event> {

    void handleEvent(E event);
}
