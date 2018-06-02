package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.List;

public class CollisionDetectorCombiner implements CollisionDetector {

    private final List<? extends CollisionDetector> detectors;

    public CollisionDetectorCombiner(List<? extends CollisionDetector> detectors) {
        this.detectors = detectors;
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        return detectors.stream()
                .map(detector -> detector.collides(collidable, direction))
                .filter(CollisionResult::isCollision)
                .findFirst()
                .orElse(CollisionResult.noCollision());
    }
}
