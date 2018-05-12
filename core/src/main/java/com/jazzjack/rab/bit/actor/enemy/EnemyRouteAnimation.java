package com.jazzjack.rab.bit.actor.enemy;

import com.jazzjack.rab.bit.actor.enemy.route.AnimationRoute;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.Step;

class EnemyRouteAnimation implements Animation {

    private static final float ANIMATION_SPEED_IN_SECONDS = 0.5f;

    private final Enemy enemy;
    private final AnimationRoute routeToAnimate;

    private float timeSinceLastStep;
    private boolean inProgress;

    EnemyRouteAnimation(Enemy enemy, AnimationRoute routeToAnimate) {
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
        enemy.moveToStep(step);
        routeToAnimate.removeStep(step);

        if (routeToAnimate.getSteps().isEmpty()) {
            enemy.removeRoute(routeToAnimate);
            inProgress = false;
        }
    }

    @Override
    public boolean isFinished() {
        return !inProgress;
    }
}
