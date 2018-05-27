package com.jazzjack.rab.bit.cmiyc.shared.position;

public interface HasPosition {

    int getX();

    int getY();

    default boolean hasSamePositionAs(HasPosition hasPosition) {
        return getX() == hasPosition.getX() && getY() == hasPosition.getY();
    }
}
