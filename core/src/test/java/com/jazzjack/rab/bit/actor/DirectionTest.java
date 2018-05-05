package com.jazzjack.rab.bit.actor;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class DirectionTest {

    @Test
    public void random() {
        IntStream.range(0, 100).forEach(i -> Direction.random());
    }
}
