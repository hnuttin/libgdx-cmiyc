package com.jazzjack.rab.bit.cmiyc.common;

public enum Predictability {

    HIGH(35, 45), MEDIUM(20, 30), LOW(5, 15);

    private final int minPercentage;
    private final int maxPercentage;

    Predictability(int minPercentage, int maxPercentage) {
        this.minPercentage = minPercentage;
        this.maxPercentage = maxPercentage;
    }

    public int getMinPercentage() {
        return minPercentage;
    }

    public int getMaxPercentage() {
        return maxPercentage;
    }
}
