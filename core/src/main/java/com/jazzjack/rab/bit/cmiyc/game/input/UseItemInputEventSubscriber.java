package com.jazzjack.rab.bit.cmiyc.game.input;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface UseItemInputEventSubscriber extends EventSubscriber {

    void useItem(UseItemInputEvent event);
}
