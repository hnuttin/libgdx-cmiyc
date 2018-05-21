package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import java.util.List;

public class RouteResult {

    private final List<Step> steps;

    RouteResult(List<Step> steps) {
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
