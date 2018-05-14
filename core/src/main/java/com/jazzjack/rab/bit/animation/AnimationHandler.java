package com.jazzjack.rab.bit.animation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AnimationHandler implements AnimationRegister {

    private final Map<Animation, CompletableFuture<Void>> animations;

    public AnimationHandler() {
        this.animations = new HashMap<>();
    }

    @Override
    public CompletableFuture<Void> registerAnimation(Animation animation) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        animations.put(animation, completableFuture);
        return completableFuture;
    }

    public void continueAnimations(float deltaTime) {
        for (Map.Entry<Animation, CompletableFuture<Void>> animation : animations.entrySet()) {
            animation.getKey().continueAnimation(deltaTime);
            if (animation.getKey().isFinished()) {
                animations.remove(animation.getKey());
                animation.getValue().complete(null);
            }
        }
    }
}
