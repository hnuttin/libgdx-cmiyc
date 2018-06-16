package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.event.Event;

public class LevelCellMarkedEvent implements Event {

    private final LevelCell levelCell;
    private final boolean markedVisited;
    private final boolean markedInSight;

    private LevelCellMarkedEvent(LevelCell levelCell, boolean markedVisited, boolean markedInSight) {
        this.levelCell = levelCell;
        this.markedVisited = markedVisited;
        this.markedInSight = markedInSight;
    }

    public LevelCell getLevelCell() {
        return levelCell;
    }

    public boolean isMarkedVisited() {
        return markedVisited;
    }

    public boolean isMarkedInSight() {
        return markedInSight;
    }

    public static LevelCellMarkedEvent markedVisitedEvent(LevelCell levelCell) {
        return new LevelCellMarkedEvent(levelCell, true, false);
    }

    public static LevelCellMarkedEvent markedInSightEvent(LevelCell levelCell) {
        return new LevelCellMarkedEvent(levelCell, false, true);
    }
}
