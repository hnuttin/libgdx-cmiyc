package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.MapObject;
import com.jazzjack.rab.bit.cmiyc.level.InvalidLevelException;
import com.jazzjack.rab.bit.cmiyc.level.LevelTiledMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class LevelMetaDataFactory {

    private static final String MARKER_START = "start";
    private static final String MARKER_END = "end";
    private static final String MARKER_ENEMY = "enemy";

    private static final String PROPERTY_TYPE = "type";

    private final ObjectTypeParser objectTypeParser;

    public LevelMetaDataFactory(ObjectTypeParser objectTypeParser) {
        this.objectTypeParser = objectTypeParser;
    }

    public LevelMetaData create(LevelTiledMap tiledMap) {
        MarkerObject startPosition = createSingleMarkerObject(tiledMap, MARKER_START, markerObjectFactory(tiledMap.getTilePixelSize()));
        MarkerObject endPosition = createSingleMarkerObject(tiledMap, MARKER_END, markerObjectFactory(tiledMap.getTilePixelSize()));
        List<EnemyMarkerObject> enemies = createListMarkerObject(tiledMap, MARKER_ENEMY, enemyMarkerObjectFactory(tiledMap.getTilePixelSize()));
        return new LevelMetaData(startPosition, endPosition, enemies);
    }

    private <T extends MarkerObject> T createSingleMarkerObject(LevelTiledMap tiledMap, String markerType, Function<MapObject, T> markerObjectFactory) {
        return createMarkerObjects(tiledMap, markerType, markerObjectFactory)
                .findFirst()
                .orElseThrow(() -> new InvalidLevelException("Could not find marker:"));
    }

    private <T extends MarkerObject> List<T> createListMarkerObject(LevelTiledMap tiledMap, String markerType, Function<MapObject, T> markerObjectFactory) {
        return createMarkerObjects(tiledMap, markerType, markerObjectFactory)
                .collect(toList());
    }

    private <T extends MarkerObject> Stream<T> createMarkerObjects(LevelTiledMap tiledMap, String markerType, Function<MapObject, T> markerObjectFactory) {
        return StreamSupport.stream(tiledMap.getObjectsMapLayer().getObjects().spliterator(), false)
                .filter(mapObject -> matchesType(mapObject, markerType))
                .map(markerObjectFactory);
    }

    private boolean matchesType(MapObject mapObject, String type) {
        String objectType = mapObject.getProperties().get(PROPERTY_TYPE, String.class);
        return objectType != null && objectType.startsWith(type);
    }

    private Function<MapObject, MarkerObject> markerObjectFactory(float tilePixelSize) {
        return mapObject -> new MarkerObject(mapObject, createDefaultProperties(mapObject), tilePixelSize);
    }

    private Function<MapObject, EnemyMarkerObject> enemyMarkerObjectFactory(float tilePixelSize) {
        return mapObject -> new EnemyMarkerObject(mapObject, createDefaultProperties(mapObject), tilePixelSize);
    }

    private Map<String, String> createDefaultProperties(MapObject mapObject) {
        return objectTypeParser.getDefaultProperties(mapObject.getProperties().get(PROPERTY_TYPE, String.class));
    }

}
