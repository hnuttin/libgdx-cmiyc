package com.jazzjack.rab.bit.cmiyc.logic;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface GamePhaseEventSubscriber extends EventSubscriber {

    void newGamePhase(GamePhaseEvent event);
}
