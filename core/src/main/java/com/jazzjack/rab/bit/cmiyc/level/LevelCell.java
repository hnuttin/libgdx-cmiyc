package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class LevelCell extends TiledMapTileLayer.Cell {

    private static final String PROPERTY_VISITED = "visited";
    private static final String PROPERTY_IN_SIGHT = "inSight";

    private final MapProperties properties;

    LevelCell() {
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
    }

    void markInSight() {
        properties.put(PROPERTY_IN_SIGHT, true);
    }

    void markNotInSight() {
        properties.put(PROPERTY_IN_SIGHT, false);
    }
}
