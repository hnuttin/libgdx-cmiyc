package com.jazzjack.rab.bit;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Level {

    private static final String MAP_LAYER = "map";

    private final TiledMap tiledMap;

    public Level(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public TiledMapTileLayer getMapLayer() {
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            if (MAP_LAYER.equalsIgnoreCase(mapLayer.getName()) && TiledMapTileLayer.class.isAssignableFrom(mapLayer.getClass())) {
                return (TiledMapTileLayer) mapLayer;
            }
        }
        throw new RuntimeException("Could not find map layer");
    }

    public void setMapOffset(float x, float y) {
        getMapLayer().setOffsetX(x);
        getMapLayer().setOffsetY(y);
    }

    public float getTileWidth() {
        return getMapLayer().getTileWidth();
    }

    public float getTileHeight() {
        return getMapLayer().getTileHeight();
    }

}
