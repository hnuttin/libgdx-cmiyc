package com.jazzjack.rab.bit.collision;

import com.jazzjack.rab.bit.common.HasPosition;

public interface Collidable extends HasPosition {

    default boolean collidesWith(Collidable collidable) {
        return hasSamePositionAs(collidable);
    }

}
