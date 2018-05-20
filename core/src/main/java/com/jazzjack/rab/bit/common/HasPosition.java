package com.jazzjack.rab.bit.common;

public interface HasPosition {

    int getX();

    int getY();

    default boolean hasSamePositionAs(HasPosition hasPosition) {
        return getX() == hasPosition.getX() && getY() == hasPosition.getY();
    }
}
