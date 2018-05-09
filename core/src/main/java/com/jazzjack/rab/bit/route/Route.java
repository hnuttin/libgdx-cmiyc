package com.jazzjack.rab.bit.route;

import java.util.List;

public class Route {

    private final List<Step> steps;

    Route(List<Step> steps) {
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
