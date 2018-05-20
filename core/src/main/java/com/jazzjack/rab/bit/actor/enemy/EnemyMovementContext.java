package com.jazzjack.rab.bit.actor.enemy;

import com.jazzjack.rab.bit.animation.AnimationRegister;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.common.Randomizer;

public class EnemyMovementContext {

    private final CollisionDetector collisionDetector;
    private final Randomizer randomizer;
    private final AnimationRegister animationRegister;

    public EnemyMovementContext(CollisionDetector collisionDetector, Randomizer randomizer, AnimationRegister animationRegister) {
        this.collisionDetector = collisionDetector;
        this.randomizer = randomizer;
        this.animationRegister = animationRegister;
    }

    CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    Randomizer getRandomizer() {
        return randomizer;
    }

    AnimationRegister getAnimationRegister() {
        return animationRegister;
    }
}
