package com.jazzjack.rab.bit.cmiyc.collision;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.level.LevelTiledMap;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class LevelCollisionDetector implements CollisionDetector {

    private static final String PROPERTY_COLLISION = "collision";

    private final LevelTiledMap levelTiledMap;

    public LevelCollisionDetector(LevelTiledMap levelTiledMap) {
        this.levelTiledMap = levelTiledMap;
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        TiledMapTileLayer mapLayer = levelTiledMap.getMapLayer();
        for (int cellX = 0; cellX < mapLayer.getWidth(); cellX++) {
            for (int cellY = 0; cellY < mapLayer.getHeight(); cellY++) {
                if (collidesWithCell(cellX, cellY, collidable)) {
                    return CollisionResult.collision(collidable, collidableFromCell(cellX, cellY), direction);
                }
            }
        }
        return CollisionResult.noCollision();
    }

    private boolean collidesWithCell(int cellX, int cellY, Collidable collidable) {
        TiledMapTileLayer.Cell cell = levelTiledMap.getMapLayer().getCell(cellX, cellY);
        MapProperties properties = cell.getTile().getProperties();
        Boolean tileCollides = properties.get(PROPERTY_COLLISION, Boolean.class);
        return tileCollides != null && tileCollides && collidesWithTile(cellX, cellY, collidable);
    }

    private boolean collidesWithTile(int cellX, int cellY, Collidable collidable) {
        return collidable.willCollideWith(collidableFromCell(cellX, cellY));
    }

    private Collidable collidableFromCell(int cellX, int cellY) {
        return new Collidable() {
            @Override
            public int getX() {
                return cellX;
            }

            @Override
            public int getY() {
                return cellY;
            }
        };
    }

}
