package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.shared.Chance;

import java.util.Collections;
import java.util.List;

public class Route implements Chance {

    private final int percentage;
    private final List<Step> steps;

    Route(int percentage, List<Step> steps) {
        this.percentage = percentage;
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public Step getLastStep() {
        return steps.get(steps.size() - 1);
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    public boolean removeStep(Step step) {
        return steps.remove(step);
    }

}
