package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.common.Chance;

import java.util.List;

public class Route implements Chance {

    private final int percentage;
    private final List<Step> steps;

    Route(int percentage, List<Step> steps) {
        this.percentage = percentage;
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }
}
