package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorMovementContext;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationRegister;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

public class EnemyMovementContext extends ActorMovementContext {

    private final Randomizer randomizer;
    private final AnimationRegister animationRegister;

    public EnemyMovementContext(CollisionDetector collisionDetector, CollisionResolver collisionResolver, Randomizer randomizer, AnimationRegister animationRegister) {
        super(collisionDetector, collisionResolver);
        this.randomizer = randomizer;
        this.animationRegister = animationRegister;
    }

    Randomizer getRandomizer() {
        return randomizer;
    }

    AnimationRegister getAnimationRegister() {
        return animationRegister;
    }
}
