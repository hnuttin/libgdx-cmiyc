package com.jazzjack.rab.bit.cmiyc.shared;

public enum Predictability {

    HIGH(35, 45, 1), MEDIUM(20, 30, 2), LOW(5, 15, 3);

    private final int minPercentage;
    private final int maxPercentage;
    private final int numberOfRoutes;

    Predictability(int minPercentage, int maxPercentage, int numberOfRoutes) {
        this.minPercentage = minPercentage;
        this.maxPercentage = maxPercentage;
        this.numberOfRoutes = numberOfRoutes;
    }

    public int getMinPercentage() {
        return minPercentage;
    }

    public int getMaxPercentage() {
        return maxPercentage;
    }

    public int getNumberOfRoutes() {
        return numberOfRoutes;
    }
}
