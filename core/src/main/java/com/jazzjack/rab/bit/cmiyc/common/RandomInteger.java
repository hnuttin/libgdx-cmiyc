package com.jazzjack.rab.bit.cmiyc.common;

import java.util.Random;

public class RandomInteger {

    private final Random random = new Random();

    public int randomInteger(int maxValue) {
        return random.nextInt(maxValue);
    }
}
