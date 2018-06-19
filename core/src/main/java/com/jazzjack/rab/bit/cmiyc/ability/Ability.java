package com.jazzjack.rab.bit.cmiyc.ability;

import com.jazzjack.rab.bit.cmiyc.shared.HasCost;

public enum Ability implements HasCost {

    MARK(1);

    private final int cost;

    Ability(int cost) {
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public String getName() {
        return "ability-" + name().toLowerCase();
    }
}
