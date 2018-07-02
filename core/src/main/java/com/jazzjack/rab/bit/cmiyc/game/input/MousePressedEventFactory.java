package com.jazzjack.rab.bit.cmiyc.game.input;

import com.jazzjack.rab.bit.cmiyc.event.Event;

import java.util.Optional;

public interface MousePressedEventFactory {

    Optional<Event> createMousePressedEvent();
}
