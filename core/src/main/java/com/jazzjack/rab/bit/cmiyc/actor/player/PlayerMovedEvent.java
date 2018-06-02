package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.event.Event;

public class PlayerMovedEvent implements Event {

    private final Player player;

    public PlayerMovedEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
