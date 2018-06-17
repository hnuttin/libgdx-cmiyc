package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;

import java.util.Iterator;
import java.util.List;

public class RouteResult {

    private final List<Step> steps;

    RouteResult(List<Step> steps) {
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Step getEndingStep() {
        return steps.get(steps.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteResult that = (RouteResult) o;

        Iterator<Step> thatIterator = that.getSteps().iterator();
        for (Step step : steps) {
            if (!step.hasSamePositionAs(thatIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return steps.stream()
                .mapToInt(step -> (step.getX() + "" + step.getY()).hashCode())
                .sum();
    }
}
