package com.jazzjack.rab.bit.animation;

public interface Animation {

    void continueAnimation(float deltaTime);

    boolean isFinished();
}
