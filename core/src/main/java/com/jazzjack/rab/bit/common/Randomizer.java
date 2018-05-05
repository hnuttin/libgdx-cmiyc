package com.jazzjack.rab.bit.common;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public <T> T randomFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
