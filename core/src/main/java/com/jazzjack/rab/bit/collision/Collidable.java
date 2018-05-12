package com.jazzjack.rab.bit.collision;

public interface Collidable {

    float getX();

    float getY();

    default boolean collides(Collidable collidable) {
        return getX() == collidable.getX() && getY() == collidable.getY();
    }

}
