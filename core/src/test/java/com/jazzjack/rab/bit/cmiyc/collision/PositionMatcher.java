package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import org.mockito.ArgumentMatcher;

import static org.mockito.ArgumentMatchers.argThat;

public class PositionMatcher implements ArgumentMatcher<HasPosition> {

    private final float x;
    private final float y;

    private PositionMatcher(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean matches(HasPosition argument) {
        return argument != null && argument.getX() == x && argument.getY() == y;
    }

    public static HasPosition matchesPosition(HasPosition hasPosition) {
        return matchesPosition(hasPosition.getX(), hasPosition.getY());
    }

    public static HasPosition matchesPosition(float x, float y) {
        return argThat(new PositionMatcher(x, y));
    }
}
