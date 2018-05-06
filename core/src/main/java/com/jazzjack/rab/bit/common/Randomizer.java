package com.jazzjack.rab.bit.common;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Randomizer {

    public <T> T randomFromSet(Set<T> set) {
        Iterator<T> iterator = set.iterator();
        T randomFromSet = null;
        for (int i = 0; i < new Random().nextInt(set.size()) + 1; i++) {
            randomFromSet = iterator.next();
        }
        return randomFromSet;
    }
}
