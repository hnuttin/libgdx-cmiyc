package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.common.HasPosition;
import com.jazzjack.rab.bit.cmiyc.level.meta.EnemyMarkerObject;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaData;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Level {

    private static final String LAYER_MAP = "map";

    private final TiledMap tiledMap;
    private final LevelMetaData levelMetaData;
    private final Player player;
    private final List<Enemy> enemies;

    public Level(TiledMap tiledMap, LevelMetaData levelMetaData) {
        this.tiledMap = tiledMap;
        this.levelMetaData = levelMetaData;
        this.player = createPlayer();
        this.enemies = createEnemies();
    }

    private Player createPlayer() {
        return new Player(levelMetaData.getStartPosition());
    }

    private List<Enemy> createEnemies() {
        return levelMetaData.getEnemies().stream().map(this::createEnemy).collect(toList());
    }

    private Enemy createEnemy(EnemyMarkerObject enemyMarkerObject) {
        return new Enemy(enemyMarkerObject.getName(), enemyMarkerObject.getPredictability(), enemyMarkerObject);
    }

    public TiledMapTileLayer getMapLayer() {
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            if (LAYER_MAP.equalsIgnoreCase(mapLayer.getName()) && TiledMapTileLayer.class.isAssignableFrom(mapLayer.getClass())) {
                return (TiledMapTileLayer) mapLayer;
            }
        }
        throw new InvalidLevelException("Could not find map layer");
    }

    public boolean hasPlayerReachedEnd() {
        return getEndPosition().hasSamePositionAs(player);
    }

    public HasPosition getEndPosition() {
        return levelMetaData.getEndPosition();
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

}
