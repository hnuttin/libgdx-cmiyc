package com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step;

import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollidablesCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetectorCombiner;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import static java.util.Arrays.asList;

public class StepResultCollisionDetector implements CollisionDetector {

    private final CollidablesCollisionDetector collisionDetectorWithStepResults;
    private final CollisionDetectorCombiner collisionDetectorCombiner;

    public StepResultCollisionDetector(CollisionDetector levelcollisiondetector) {
        this.collisionDetectorWithStepResults = new CollidablesCollisionDetector();
        this.collisionDetectorCombiner = new CollisionDetectorCombiner(asList(levelcollisiondetector, collisionDetectorWithStepResults));
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        return collisionDetectorCombiner.collides(collidable, direction);
    }

    public void addStepResult(Collidable collidable) {
        collisionDetectorWithStepResults.addCollidable(collidable);
    }

    public void clearStepResults() {
        collisionDetectorWithStepResults.clearCollidables();
    }
}
