package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface LevelCellMarkedEventSubscriber extends EventSubscriber {

    void levelCellMarked(LevelCellMarkedEvent event);
}
