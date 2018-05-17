package com.jazzjack.rab.bit.collision;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.Map;

public class LevelCollisionDetector implements CollisionDetector {

    private static final String PROPERTY_COLLISION = "collision";

    private final Map map;

    public LevelCollisionDetector(Map map) {
        this.map = map;
    }

    @Override
    public CollisionResult collides(Collidable collidable) {
        TiledMapTileLayer mapLayer = map.getMapLayer();
        for (int cellX = 0; cellX < mapLayer.getWidth(); cellX++) {
            for (int cellY = 0; cellY < mapLayer.getHeight(); cellY++) {
                if (collidesWithCell(cellX, cellY, collidable)) {
                    return CollisionResult.collision(collidableFromCell(cellX, cellY));
                }
            }
        }
        return CollisionResult.noCollision();
    }

    private boolean collidesWithCell(int cellX, int cellY, Collidable collidable) {
        TiledMapTileLayer.Cell cell = map.getMapLayer().getCell(cellX, cellY);
        MapProperties properties = cell.getTile().getProperties();
        Boolean tileCollides = properties.get(PROPERTY_COLLISION, Boolean.class);
        return tileCollides != null && tileCollides && collidesWithTile(cellX, cellY, collidable);
    }

    private boolean collidesWithTile(int cellX, int cellY, Collidable collidable) {
        return collidable.collidesWith(collidableFromCell(cellX, cellY));
    }

    private Collidable collidableFromCell(int cellX, int cellY) {
        return new Collidable() {
            @Override
            public float getX() {
                return cellX;
            }

            @Override
            public float getY() {
                return cellY;
            }
        };
    }

}
