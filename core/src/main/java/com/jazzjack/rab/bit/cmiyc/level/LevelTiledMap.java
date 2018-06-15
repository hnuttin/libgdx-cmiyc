package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

public class LevelTiledMap extends TiledMap {

    private static final String LAYER_MAP = "map";
    private static final String LAYER_MARKERS = "markers";

    private final TiledMap tiledMap;

    public LevelTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public TiledMapTileLayer getMapLayer() {
        MapLayer mapLayer = getLayers().get(LAYER_MAP);
        if (mapLayer == null) {
            throw new IllegalStateException("Map layer not found");
        } else {
            return (TiledMapTileLayer) mapLayer;
        }
    }

    public MapLayer getObjectsMapLayer() {
        return getLayers().get(LAYER_MARKERS);
    }

    public float getTilePixelSize() {
        return getProperties().get("tilewidth", Integer.class);
    }

    public int getWidth() {
        return getProperties().get("width", Integer.class);
    }

    public int getHeight() {
        return getProperties().get("height", Integer.class);
    }

    LevelCell getLevelCell(HasPosition position) {
        return getLevelCell(position.getX(), position.getY());
    }

    LevelCell getLevelCell(int x, int y) {
        return (LevelCell) getMapLayer().getCell(x, y);
    }

    /* TiledMap DELEGATE METHODS */

    @Override
    public TiledMapTileSets getTileSets() {
        return tiledMap.getTileSets();
    }

    @Override
    public void setOwnedResources(Array<? extends Disposable> resources) {
        tiledMap.setOwnedResources(resources);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    @Override
    public MapLayers getLayers() {
        return tiledMap.getLayers();
    }

    @Override
    public MapProperties getProperties() {
        return tiledMap.getProperties();
    }
}
