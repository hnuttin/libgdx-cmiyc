package com.jazzjack.rab.bit.cmiyc.shared;

public enum Sense {

    HIGH(40), MEDIUM(30), LOW(10);

    private final int percentageWeight;

    public int getPercentageWeight() {
        return percentageWeight;
    }

    Sense(int percentageWeight) {
        this.percentageWeight = percentageWeight;
    }
}
