package com.jazzjack.rab.bit.cmiyc.logic;

import com.jazzjack.rab.bit.cmiyc.event.Event;

public class GamePhaseEvent implements Event {

    private final GamePhase gamePhase;

    public GamePhaseEvent(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }
}
