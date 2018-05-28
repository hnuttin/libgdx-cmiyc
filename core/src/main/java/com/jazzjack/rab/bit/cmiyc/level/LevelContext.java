package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.animation.AnimationRegister;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

public class LevelContext {

    private final CollisionResolver collisionResolver;
    private final Randomizer randomizer;
    private final AnimationRegister animationRegister;

    public LevelContext(CollisionResolver collisionResolver, Randomizer randomizer, AnimationRegister animationRegister) {
        this.collisionResolver = collisionResolver;
        this.randomizer = randomizer;
        this.animationRegister = animationRegister;
    }

    CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }

    Randomizer getRandomizer() {
        return randomizer;
    }

    AnimationRegister getAnimationRegister() {
        return animationRegister;
    }
}
