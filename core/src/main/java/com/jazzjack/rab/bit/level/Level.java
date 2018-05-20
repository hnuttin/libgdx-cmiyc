package com.jazzjack.rab.bit.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.common.HasPosition;
import com.jazzjack.rab.bit.common.Position;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class Level {

    private static final String LAYER_MAP = "map";
    private static final String LAYER_MARKERS = "markers";

    private static final String MARKER_END = "end";
    private static final String MARKER_START = "start";

    private final TiledMap tiledMap;
    private final Player player;
    private final List<Enemy> enemies;

    public Level(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.player = createPlayer();
        this.enemies = createEnemies();
    }

    private Player createPlayer() {
        return getMarkerObjectsByType(MARKER_START)
                .findFirst()
                .map(ellipse -> new Player(objectXToTileX(ellipse), objectYToTileY(ellipse)))
                .orElseThrow(() -> new InvalidLevelException("Could not find player start position"));
    }

    private List<Enemy> createEnemies() {
        return getMarkerObjectsByType("enemy1")
                .map(ellipse -> new Enemy(objectXToTileX(ellipse), objectYToTileY(ellipse)))
                .collect(toList());
    }

    private Stream<Ellipse> getMarkerObjectsByType(String type) {
        return StreamSupport.stream(getMarkerObjects().spliterator(), false)
                .filter(mapObject -> mapObject instanceof EllipseMapObject)
                .map(mapObject -> (EllipseMapObject) mapObject)
                .filter(ellipseMapObject -> type.equals(ellipseMapObject.getProperties().get("type")))
                .map(EllipseMapObject::getEllipse);
    }

    private MapObjects getMarkerObjects() {
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            if (LAYER_MARKERS.equalsIgnoreCase(mapLayer.getName())) {
                return mapLayer.getObjects();
            }
        }
        throw new InvalidLevelException("Could not find markers layer");
    }

    private int objectYToTileY(Ellipse ellipse) {
        return (int) (ellipse.y / getTilePixelSize());
    }

    private int objectXToTileX(Ellipse ellipse) {
        return (int) (ellipse.x / getTilePixelSize());
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
                .map(ellipse -> new Position(objectXToTileX(ellipse), objectYToTileY(ellipse)))
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
