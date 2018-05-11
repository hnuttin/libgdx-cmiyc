package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;

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
    public boolean collides(Collidable collidable) {
        return collidesWithStepResults(collidable) || collisionDetector.collides(collidable);
    }

    private boolean collidesWithStepResults(Collidable collidable) {
        return stepResults.stream().anyMatch(collidable::collides);
    }

    void addStepResult(StepResult stepResult) {
        stepResults.add(stepResult);
    }

}
