package com.jazzjack.rab.bit.cmiyc.shared;

public interface HasPosition {

    int getX();

    int getY();

    default boolean hasSamePositionAs(HasPosition hasPosition) {
        return getX() == hasPosition.getX() && getY() == hasPosition.getY();
    }
}
