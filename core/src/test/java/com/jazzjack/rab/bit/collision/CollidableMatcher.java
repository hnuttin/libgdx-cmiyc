package com.jazzjack.rab.bit.collision;

import org.mockito.ArgumentMatcher;

import static org.mockito.ArgumentMatchers.argThat;

public class CollidableMatcher implements ArgumentMatcher<Collidable> {

    private final float x;
    private final float y;

    private CollidableMatcher(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean matches(Collidable argument) {
        return argument != null && argument.getX() == x && argument.getY() == y;
    }

    public static Collidable matchesCollidable(float x, float y) {
        return argThat(new CollidableMatcher(x, y));
    }
}
