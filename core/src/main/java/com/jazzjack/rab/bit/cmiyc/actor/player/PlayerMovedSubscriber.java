package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface PlayerMovedSubscriber extends EventSubscriber {

    void playerMoved(PlayerMovedEvent event);
}
