package com.jazzjack.rab.bit.actor.enemy.route;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.common.Chance;

import java.util.List;

public class Route implements Chance {

    private final int percentage;
    private final List<Step> steps;

    Route(int percentage, List<Step> steps) {
        this.percentage = percentage;
        this.steps = steps;
    }

    public ImmutableList<Step> getSteps() {
        return ImmutableList.copyOf(steps);
    }

    public Step getLastStep() {
        return steps.get(steps.size() - 1);
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    protected boolean removeStepInternal(Step step) {
        return steps.remove(step);
    }

}
