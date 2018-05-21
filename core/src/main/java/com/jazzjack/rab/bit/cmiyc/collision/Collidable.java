package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.common.HasPosition;

public interface Collidable extends HasPosition {

    default boolean collidesWith(Collidable collidable) {
        return hasSamePositionAs(collidable);
    }

}
