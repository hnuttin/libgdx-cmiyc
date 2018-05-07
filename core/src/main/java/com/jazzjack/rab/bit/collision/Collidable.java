package com.jazzjack.rab.bit.collision;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {

    float getX();

    float getY();

    float getSize();

    default boolean collides(Collidable collidable) {
        return collides(rectangleFromCollidable(collidable));
    }

    default boolean collides(Rectangle rectangle) {
        return rectangleFromCollidable(this).overlaps(rectangle);
    }

    static Rectangle rectangleFromCollidable(Collidable collidable) {
        return new Rectangle(collidable.getX(), collidable.getY(), collidable.getSize(), collidable.getSize());
    }
}
