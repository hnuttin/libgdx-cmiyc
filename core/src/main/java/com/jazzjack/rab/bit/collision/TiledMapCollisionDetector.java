package com.jazzjack.rab.bit.collision;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.actor.Actor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

public class TiledMapCollisionDetector implements CollisionDetector {

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
        MapObjects objects = cell.getTile().getObjects();
        return StreamSupport.stream(objects.getByType(RectangleMapObject.class).spliterator(), true)
                .anyMatch(rectangleObject -> collidesWithRectangleMapObject(rectangleObject, cellX, cellY, collidable));
    }

    private boolean collidesWithRectangleMapObject(RectangleMapObject rectangleObject, int cellX, int cellY, Collidable collidable) {
        TiledMapTileLayer mapLayer = level.getMapLayer();
        Rectangle collisionRectangle = new Rectangle(rectangleObject.getRectangle());
        collisionRectangle.setX(cellX * mapLayer.getTileWidth());
        collisionRectangle.setY(cellY * mapLayer.getTileHeight());
        return collidable.collides(collisionRectangle);
    }

    private boolean collidesWithAnyActor(final Collidable collidable) {
        return actors.stream().anyMatch(actor -> actor.collides(collidable));
    }

}
