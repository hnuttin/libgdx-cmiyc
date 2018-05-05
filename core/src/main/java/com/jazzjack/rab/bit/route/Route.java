package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.actor.Actor;

import java.util.LinkedHashSet;

public class Route {

    private final LinkedHashSet<Actor> steps;

    public Route(LinkedHashSet<Actor> steps) {
        this.steps = steps;
    }

    public LinkedHashSet<Actor> getSteps() {
        return steps;
    }
}
