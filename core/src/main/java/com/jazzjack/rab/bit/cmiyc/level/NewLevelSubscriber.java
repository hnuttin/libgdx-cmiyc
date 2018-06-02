package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface NewLevelSubscriber extends EventSubscriber {

    void newLevel(NewLevelEvent newLevelEvent);
}
