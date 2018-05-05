package com.jazzjack.rab.bit.actor;

import java.util.Random;

public enum Direction {

    LEFT, RIGHT, UP, DOWN;

    public static Direction random() {
        return Direction.values()[new Random().nextInt(4)];
    }
}
