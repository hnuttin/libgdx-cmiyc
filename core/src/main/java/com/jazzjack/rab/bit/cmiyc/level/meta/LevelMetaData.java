package com.jazzjack.rab.bit.cmiyc.level.meta;

import java.util.List;

public class LevelMetaData {

    private final MarkerObject startPosition;
    private final MarkerObject endPosition;
    private final List<EnemyMarkerObject> enemies;
    private final List<MarkerObject> items;

    LevelMetaData(MarkerObject startPosition, MarkerObject endPosition, List<EnemyMarkerObject> enemies, List<MarkerObject> items) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.enemies = enemies;
        this.items = items;
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

    public List<MarkerObject> getItems() {
        return items;
    }
}
