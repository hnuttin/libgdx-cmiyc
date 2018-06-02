package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.animation.Animation;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;

public class EnemyRouteAnimation implements Animation {

    private static final float ANIMATION_SPEED_IN_SECONDS = 0.2f;

    private final Enemy enemy;
    private final Route routeToAnimate;

    private float timeSinceLastStep;
    private boolean inProgress;

    public EnemyRouteAnimation(Enemy enemy, Route routeToAnimate) {
        this.enemy = enemy;
        this.routeToAnimate = routeToAnimate;

        this.timeSinceLastStep = 0;
        this.inProgress = true;

        moveEnemyToNextRouteStep();
    }

    @Override
    public void continueAnimation(float deltaTime) {
        if (inProgress) {
            timeSinceLastStep += deltaTime;
            if (timeSinceLastStep >= ANIMATION_SPEED_IN_SECONDS) {
                moveEnemyToNextRouteStep();
                timeSinceLastStep = 0;
            }
        }
    }

    private void moveEnemyToNextRouteStep() {
        Step step = routeToAnimate.getSteps().get(0);
        CollisionResult collisionResult = enemy.moveToStep(step);
        if (collisionResult.isCollision()) {
            endAnimation();
        } else {
            routeToAnimate.removeStep(step);
            if (routeToAnimate.getSteps().isEmpty()) {
                endAnimation();
            }
        }
    }

    private void endAnimation() {
        enemy.removeRoute(routeToAnimate);
        inProgress = false;
    }

    @Override
    public boolean isFinished() {
        return !inProgress;
    }
}
