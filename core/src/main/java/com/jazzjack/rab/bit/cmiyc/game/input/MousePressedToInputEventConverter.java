package com.jazzjack.rab.bit.cmiyc.game.input;

import com.jazzjack.rab.bit.cmiyc.event.Event;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.Optional;

public interface MousePressedToInputEventConverter {

    Optional<Event> convertToInputEvent(HasPosition position);
}
