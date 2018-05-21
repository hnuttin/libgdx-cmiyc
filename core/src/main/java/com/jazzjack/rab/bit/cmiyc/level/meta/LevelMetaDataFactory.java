package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class LevelMetaDataFactory {

    private static final String LAYER_MARKERS = "markers";

    private static final String MARKER_END = "end";
    private static final String MARKER_START = "start";
    private static final String MARKER_ENEMY = "enemy";

    private static final String PROPERTY_ENEMY_NAME = "type";

    private final ObjectTypeParser objectTypeParser;

    public LevelMetaDataFactory(ObjectTypeParser objectTypeParser) {
        this.objectTypeParser = objectTypeParser;
    }

    public LevelMetaData create(TiledMap tiledMap) {
        return null;
    }

//    private Enemy enemyFromMarkerObject(MarkerObject markerObject) {
//        String name = markerObject.getStringProperty(PROPERTY_ENEMY_NAME);
//        Predictability predictability = objectTypeParser.getEnemyPredictability(name);
//        return new Enemy(name, predictability, markerObject);
//    }
//
//    private Stream<MarkerObject> getMarkerObjectsByType(String type) {
//        return StreamSupport.stream(getMarkerObjects().spliterator(), false)
//                .filter(mapObject -> matchesType(mapObject, type))
//                .map(mapObject -> new MarkerObject(mapObject, getTilePixelSize()));
//    }
//
//    private boolean matchesType(MapObject mapObject, String type) {
//        String objectType = mapObject.getProperties().get("type", String.class);
//        return objectType != null && objectType.startsWith(type);
//    }
//
//    private MapObjects getMarkerObjects() {
//        for (MapLayer mapLayer : tiledMap.getLayers()) {
//            if (LAYER_MARKERS.equalsIgnoreCase(mapLayer.getName())) {
//                return mapLayer.getObjects();
//            }
//        }
//        throw new InvalidLevelException("Could not find markers layer");
//    }
}
