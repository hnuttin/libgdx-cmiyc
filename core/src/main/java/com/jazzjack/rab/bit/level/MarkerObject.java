package com.jazzjack.rab.bit.level;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.jazzjack.rab.bit.common.HasPosition;
import com.jazzjack.rab.bit.common.Position;

class MarkerObject implements HasPosition {

    private final MapObject mapObject;
    private final Position position;

    MarkerObject(MapObject mapObject, float tilePixelSize) {
        this.mapObject = mapObject;
        this.position = positionForMapObject(mapObject, tilePixelSize);
    }

    private Position positionForMapObject(MapObject mapObject, float tilePixelSize) {
        if (mapObject instanceof EllipseMapObject) {
            EllipseMapObject ellipseMapObject = (EllipseMapObject) mapObject;
            return new Position(
                    (int) (ellipseMapObject.getEllipse().x / tilePixelSize),
                    (int) (ellipseMapObject.getEllipse().y / tilePixelSize));
        } else if (mapObject instanceof RectangleMapObject) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
            return new Position(
                    (int) (rectangleMapObject.getRectangle().x / tilePixelSize),
                    (int) (rectangleMapObject.getRectangle().y / tilePixelSize));
        } else {
            throw new InvalidLevelException("Unsupported marker map object " + mapObject.getClass().getSimpleName());
        }
    }

    public String getStringProperty(String propertyName) {
        return mapObject.getProperties().get(propertyName, String.class);
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public int getY() {
        return position.getY();
    }
}
