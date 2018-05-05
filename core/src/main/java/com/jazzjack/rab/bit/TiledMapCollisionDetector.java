package com.jazzjack.rab.bit;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
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
    public boolean collides(Rectangle rectangle) {
        return collidesWithMap(rectangle) || collidesWithAnyActor(rectangle);
    }

    private boolean collidesWithMap(Rectangle rectangle) {
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

    private boolean collidesWithCell(int cellX, int cellY, Rectangle rectangle) {
        TiledMapTileLayer.Cell cell = level.getMapLayer().getCell(cellX, cellY);
        MapObjects objects = cell.getTile().getObjects();
        return StreamSupport.stream(objects.getByType(RectangleMapObject.class).spliterator(), true)
                .anyMatch(rectangleObject -> collidesWithRectangleMapObject(rectangleObject, cellX, cellY, rectangle));
    }

    private boolean collidesWithRectangleMapObject(RectangleMapObject rectangleObject, int cellX, int cellY, Rectangle rectangle) {
        TiledMapTileLayer mapLayer = level.getMapLayer();
        Rectangle collisionRectangle = new Rectangle(rectangleObject.getRectangle());
        collisionRectangle.setX(cellX * mapLayer.getTileWidth());
        collisionRectangle.setY(cellY * mapLayer.getTileHeight());
        return collisionRectangle.overlaps(rectangle);
    }

    private boolean collidesWithAnyActor(final Rectangle rectangle) {
        return actors.stream().anyMatch(actor -> collidesWithActor(actor, rectangle));
    }

    private boolean collidesWithActor(Actor actor, Rectangle rectangle) {
        Rectangle actorRectangle = new Rectangle(
                actor.getX(),
                actor.getY(),
                level.getTileWidth(),
                level.getTileHeight());
        return actorRectangle.overlaps(rectangle);
    }

}
