package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.common.HasPosition;
import com.jazzjack.rab.bit.cmiyc.common.Position;
import com.jazzjack.rab.bit.cmiyc.common.Predictability;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class Level {

    private static final String LAYER_MAP = "map";
    private static final String LAYER_MARKERS = "markers";

    private static final String MARKER_END = "end";
    private static final String MARKER_START = "start";
    private static final String MARKER_ENEMY = "enemy";

    private static final String PROPERTY_ENEMY_NAME = "type";

    private final TiledMap tiledMap;
    private final ObjectTypeParser objectTypeParser;
    private final Player player;
    private final List<Enemy> enemies;

    public Level(TiledMap tiledMap, ObjectTypeParser objectTypeParser) {
        this.tiledMap = tiledMap;
        this.objectTypeParser = objectTypeParser;
        this.player = createPlayer();
        this.enemies = createEnemies();
    }

    private Player createPlayer() {
        return getMarkerObjectsByType(MARKER_START)
                .findFirst()
                .map(Player::new)
                .orElseThrow(() -> new InvalidLevelException("Could not find player start position"));
    }

    private List<Enemy> createEnemies() {
        return getMarkerObjectsByType(MARKER_ENEMY)
                .map(this::enemyFromMarkerObject)
                .collect(toList());
    }

    private Enemy enemyFromMarkerObject(MarkerObject markerObject) {
        String name = markerObject.getStringProperty(PROPERTY_ENEMY_NAME);
        Predictability predictability = objectTypeParser.getEnemyPredictability(name);
        return new Enemy(name, predictability, markerObject);
    }

    private Stream<MarkerObject> getMarkerObjectsByType(String type) {
        return StreamSupport.stream(getMarkerObjects().spliterator(), false)
                .filter(mapObject -> matchesType(mapObject, type))
                .map(mapObject -> new MarkerObject(mapObject, getTilePixelSize()));
    }

    private boolean matchesType(MapObject mapObject, String type) {
        String objectType = mapObject.getProperties().get("type", String.class);
        return objectType != null && objectType.startsWith(type);
    }

    private MapObjects getMarkerObjects() {
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            if (LAYER_MARKERS.equalsIgnoreCase(mapLayer.getName())) {
                return mapLayer.getObjects();
            }
        }
        throw new InvalidLevelException("Could not find markers layer");
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
        return getMarkerObjectsByType(MARKER_END)
                .findFirst()
                .map(Position::new)
                .orElseThrow(() -> new InvalidLevelException("Could not find end position"));
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
