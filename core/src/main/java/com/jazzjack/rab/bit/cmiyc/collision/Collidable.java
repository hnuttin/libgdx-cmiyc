package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.HasPosition;

public interface Collidable extends HasPosition {

    default boolean willCollideWith(Collidable collidable) {
        return hasSamePositionAs(collidable);
    }

}
