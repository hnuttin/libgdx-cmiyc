package com.jazzjack.rab.bit;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class TiledMapCollisionDetector implements CollisionDetector {

    private final TacticalMap map;

    public TiledMapCollisionDetector(TacticalMap map) {
        this.map = map;
    }

    @Override
    public boolean collides(Rectangle rectangle) {
        TiledMapTileLayer mapLayer = map.getMapLayer();
        for (int x = 0; x < mapLayer.getWidth(); x++) {
            for (int y = 0; y < mapLayer.getHeight(); y++) {
                if (collidesWithCell(mapLayer, x, y, rectangle)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean collidesWithCell(TiledMapTileLayer mapLayer, int x, int y, Rectangle rectangle) {
        TiledMapTileLayer.Cell cell = mapLayer.getCell(x, y);
        MapObjects objects = cell.getTile().getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle collisionRectangle = new Rectangle(rectangleObject.getRectangle());
            collisionRectangle.setX(x * mapLayer.getTileWidth());
            collisionRectangle.setY(y * mapLayer.getTileHeight());
            if (collisionRectangle.overlaps(rectangle)) {
                return true;
            }
        }
        return false;
    }

}
