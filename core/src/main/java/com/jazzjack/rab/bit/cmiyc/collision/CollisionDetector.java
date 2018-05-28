package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public interface CollisionDetector {

    CollisionResult collides(Collidable collidable, Direction direction);
}
