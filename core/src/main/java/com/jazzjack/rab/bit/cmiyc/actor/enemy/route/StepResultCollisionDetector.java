package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

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
    public CollisionResult collides(Collidable collidable, Direction direction) {
        CollisionResult collisionResult = collidesWithStepResults(collidable, direction);
        if (collisionResult.isUnresolved()) {
            return collisionResult;
        } else {
            return collisionDetector.collides(collidable, direction);
        }
    }

    private CollisionResult collidesWithStepResults(Collidable collidable, Direction direction) {
        return stepResults.stream()
                .filter(collidable::willCollideWith)
                .findFirst()
                .map((stepResult) -> CollisionResult.unresolved(collidable, stepResult, direction))
                .orElse(CollisionResult.noCollision());
    }

    void addStepResult(StepResult stepResult) {
        stepResults.add(stepResult);
    }

}
