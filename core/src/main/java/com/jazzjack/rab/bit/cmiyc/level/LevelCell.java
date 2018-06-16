package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class LevelCell extends TiledMapTileLayer.Cell implements HasPosition {

    private static final String PROPERTY_VISITED = "visited";
    private static final String PROPERTY_IN_SIGHT = "inSight";

    private final int x;
    private final int y;
    private final MapProperties properties;

    LevelCell(int x, int y) {
        this.x = x;
        this.y = y;
        properties = new MapProperties();
    }

    public boolean isVisited() {
        return properties.get(PROPERTY_VISITED, false, Boolean.class);
    }

    public boolean isInSight() {
        return properties.get(PROPERTY_IN_SIGHT, false, Boolean.class);
    }

    void markVisited() {
        properties.put(PROPERTY_VISITED, true);
        publishEvent(LevelCellMarkedEvent.markedVisitedEvent(this));
    }

    void markInSight() {
        properties.put(PROPERTY_IN_SIGHT, true);
        publishEvent(LevelCellMarkedEvent.markedInSightEvent(this));
    }

    void markNotInSight() {
        properties.put(PROPERTY_IN_SIGHT, false);
        publishEvent(LevelCellMarkedEvent.markedInSightEvent(this));
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
