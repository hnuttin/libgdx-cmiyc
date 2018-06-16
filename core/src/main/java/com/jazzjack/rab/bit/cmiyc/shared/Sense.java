package com.jazzjack.rab.bit.cmiyc.shared;

public enum Sense {

    HIGH(45), MEDIUM(35), LOW(25), NONE(0);

    private final int percentageWeight;

    Sense(int percentageWeight) {
        this.percentageWeight = percentageWeight;
    }

    public int getPercentageWeight() {
        return percentageWeight;
    }
}
