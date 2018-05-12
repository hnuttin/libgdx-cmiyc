package com.jazzjack.rab.bit.common;

import java.util.Random;

public class RandomInteger {

    private final Random random = new Random();

    public int randomInteger(int maxValue) {
        return random.nextInt(maxValue);
    }
}
