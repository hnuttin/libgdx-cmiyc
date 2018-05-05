package com.jazzjack.rab.bit.actor;

import java.util.Set;

public class Route {

    private final Set<Actor> steps;

    public Route(Set<Actor> steps) {
        this.steps = steps;
    }

    public Set<Actor> getSteps() {
        return steps;
    }
}
