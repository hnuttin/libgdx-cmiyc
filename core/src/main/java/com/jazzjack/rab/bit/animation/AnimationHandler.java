package com.jazzjack.rab.bit.animation;

import java.util.HashMap;
import java.util.Map;

public class AnimationHandler implements AnimationRegister {

    private final Map<Animation, Runnable> animations;

    public AnimationHandler() {
        this.animations = new HashMap<>();
    }

    @Override
    public void registerAnimation(Animation animation, Runnable onAnimationFinished) {
        animations.put(animation, onAnimationFinished);
    }

    public void continueAnimations(float deltaTime) {
        for (Map.Entry<Animation, Runnable> animation : animations.entrySet()) {
            animation.getKey().continueAnimation(deltaTime);
            if (animation.getKey().isFinished()) {
                animation.getValue().run();
                animations.remove(animation.getKey());
            }
        }
    }
}
