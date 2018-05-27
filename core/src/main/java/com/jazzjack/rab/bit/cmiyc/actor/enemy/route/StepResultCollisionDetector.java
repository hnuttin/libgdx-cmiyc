package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;

import java.util.ArrayList;
import java.util.List;

class StepResultCollisionDetector implements CollisionDetector {

    private final CollisionDetector collisionDetector;
    private final List<StepResult> stepResults;

    StepResultCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
        stepResults = new ArrayList<>();
    }

    @Override
    public CollisionResult collides(Collidable collidable) {
        CollisionResult collisionResult = collidesWithStepResults(collidable);
        if (collisionResult.isCollision()) {
            return collisionResult;
        } else {
            return collisionDetector.collides(collidable);
        }
    }

    private CollisionResult collidesWithStepResults(Collidable collidable) {
        return stepResults.stream()
                .filter(collidable::willCollideWith)
                .findFirst()
                .map((StepResult sourceCollidable) -> CollisionResult.unresolved(sourceCollidable, CollisionResult.direction))
                .orElse(CollisionResult.noCollision());
    }

    void addStepResult(StepResult stepResult) {
        stepResults.add(stepResult);
    }

}
