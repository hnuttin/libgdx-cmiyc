package com.jazzjack.rab.bit.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.player.Player;

import java.util.List;

public class Level {

    private static final String MAP_LAYER = "map";

    private final TiledMap tiledMap;
    private final Player player;
    private final List<Enemy> enemies;

    public Level(TiledMap tiledMap, Player player, List<Enemy> enemies) {
        this.tiledMap = tiledMap;
        this.player = player;
        this.enemies = enemies;
    }

    public TiledMapTileLayer getMapLayer() {
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            if (MAP_LAYER.equalsIgnoreCase(mapLayer.getName()) && TiledMapTileLayer.class.isAssignableFrom(mapLayer.getClass())) {
                return (TiledMapTileLayer) mapLayer;
            }
        }
        throw new RuntimeException("Could not find map layer");
    }

    public float getTilePixelSize() {
        return getMapLayer().getTileWidth();
    }

    public int getWidth() {
        return getMapLayer().getWidth();
    }

    public int getHeight() {
        return getMapLayer().getHeight();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setMapOffset(float x, float y) {
        getMapLayer().setOffsetX(x);
        getMapLayer().setOffsetY(y);
    }

}
