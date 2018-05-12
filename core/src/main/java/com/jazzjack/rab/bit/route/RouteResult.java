package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.route.Step;

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
