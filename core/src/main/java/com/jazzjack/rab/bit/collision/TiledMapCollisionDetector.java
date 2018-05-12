package com.jazzjack.rab.bit.collision;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.actor.Actor;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class TiledMapCollisionDetector implements CollisionDetector {

    private static final String PROPERTY_COLLISION = "collision";
    private final Level level;
    private final Set<Actor> actors;

    public TiledMapCollisionDetector(Level level) {
        this.level = level;
        this.actors = new HashSet<>();
    }

    public void addActor(Actor ...actor) {
        actors.addAll(asList(actor));
    }

    @Override
    public boolean collides(Collidable collidable) {
        return collidesWithMap(collidable) || collidesWithAnyActor(collidable);
    }

    private boolean collidesWithMap(Collidable rectangle) {
        TiledMapTileLayer mapLayer = level.getMapLayer();
        for (int cellX = 0; cellX < mapLayer.getWidth(); cellX++) {
            for (int cellY = 0; cellY < mapLayer.getHeight(); cellY++) {
                if (collidesWithCell(cellX, cellY, rectangle)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean collidesWithCell(int cellX, int cellY, Collidable collidable) {
        TiledMapTileLayer.Cell cell = level.getMapLayer().getCell(cellX, cellY);
        MapProperties properties = cell.getTile().getProperties();
        Boolean tileCollides = properties.get(PROPERTY_COLLISION, Boolean.class);
        return tileCollides != null && tileCollides && collidesWithTile(cellX, cellY, collidable);
    }

    private boolean collidesWithTile(int cellX, int cellY, Collidable collidable) {
        return collidable.collides(new Collidable() {
            @Override
            public float getX() {
                return cellX;
            }
            @Override
            public float getY() {
                return cellY;
            }
        });
    }

    private boolean collidesWithAnyActor(final Collidable collidable) {
        return actors.stream().anyMatch(actor -> actor.collides(collidable));
    }

}
