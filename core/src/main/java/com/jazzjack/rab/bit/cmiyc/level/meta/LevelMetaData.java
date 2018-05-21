package com.jazzjack.rab.bit.cmiyc.level.meta;

import java.util.List;

public class LevelMetaData {

    private final MarkerObject startPosition;
    private final MarkerObject endPosition;
    private final List<EnemyMarkerObject> enemies;

    LevelMetaData(MarkerObject startPosition, MarkerObject endPosition, List<EnemyMarkerObject> enemies) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.enemies = enemies;
    }

    public MarkerObject getStartPosition() {
        return startPosition;
    }

    public MarkerObject getEndPosition() {
        return endPosition;
    }

    public List<EnemyMarkerObject> getEnemies() {
        return enemies;
    }
}
