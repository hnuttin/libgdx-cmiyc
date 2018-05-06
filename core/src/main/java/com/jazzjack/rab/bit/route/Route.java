package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.actor.Actor;

import java.util.List;

public class Route {

    private final List<Actor> steps;

    public Route(List<Actor> steps) {
        this.steps = steps;
    }

    public List<Actor> getSteps() {
        return steps;
    }
}
