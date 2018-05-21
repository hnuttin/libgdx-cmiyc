package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class LevelTiledMap {

    private static final String LAYER_MAP = "map";
    private static final String LAYER_MARKERS = "markers";

    private final TiledMap tiledMap;

    public LevelTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public TiledMapTileLayer getMapLayer() {
        return (TiledMapTileLayer) tiledMap.getLayers().get(LAYER_MAP);
    }

    public MapLayer getObjectsMapLayer() {
        return tiledMap.getLayers().get(LAYER_MARKERS);
    }

    public float getTilePixelSize() {
        return tiledMap.getProperties().get("tilewidth", Integer.class);
    }

    public int getWidth() {
        return tiledMap.getProperties().get("width", Integer.class);
    }

    public int getHeight() {
        return tiledMap.getProperties().get("height", Integer.class);
    }
}
