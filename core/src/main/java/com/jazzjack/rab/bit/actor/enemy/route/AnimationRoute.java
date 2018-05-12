package com.jazzjack.rab.bit.actor.enemy.route;

import java.util.ArrayList;

public class AnimationRoute extends Route {

    public AnimationRoute(Route route) {
        super(route.getPercentage(), new ArrayList<>(route.getSteps()));
    }

    public boolean removeStep(Step step) {
        return super.removeStepInternal(step);
    }
}
