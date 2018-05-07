package com.jazzjack.rab.bit.collision;

import org.mockito.ArgumentMatcher;

import static org.mockito.ArgumentMatchers.argThat;

public class CollidableMatcher implements ArgumentMatcher<Collidable> {

    private final float x;
    private final float y;
    private final float size;

    private CollidableMatcher(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public boolean matches(Collidable argument) {
        return argument != null && argument.getX() == x && argument.getY() == y && argument.getSize() == size;
    }

    public static Collidable matchesCollidable(float x, float y, float size) {
        return argThat(new CollidableMatcher(x, y, size));
    }
}
