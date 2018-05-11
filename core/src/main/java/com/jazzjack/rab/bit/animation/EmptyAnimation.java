package com.jazzjack.rab.bit.animation;

public class EmptyAnimation implements Animation {

    @Override
    public void continueAnimation(float deltaTime) {
        // do nothing
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
