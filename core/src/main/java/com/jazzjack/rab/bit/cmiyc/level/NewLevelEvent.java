package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.event.Event;

public class NewLevelEvent implements Event {

    private final Level level;

    public NewLevelEvent(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
