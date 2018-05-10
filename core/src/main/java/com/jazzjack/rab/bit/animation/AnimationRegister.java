package com.jazzjack.rab.bit.animation;

public interface AnimationRegister {
    void registerAnimation(Animation animation, Runnable onAnimationFinished);
}
