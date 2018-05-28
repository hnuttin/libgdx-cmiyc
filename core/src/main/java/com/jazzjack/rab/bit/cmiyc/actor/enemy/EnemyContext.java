package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationRegister;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

public class EnemyContext extends ActorContext {

    private final Randomizer randomizer;
    private final AnimationRegister animationRegister;
    private final RouteGenerator routeGenerator;

    public EnemyContext(CollisionDetector collisionDetector, CollisionResolver collisionResolver, Randomizer randomizer, AnimationRegister animationRegister, RouteGenerator routeGenerator) {
        super(collisionDetector, collisionResolver);
        this.randomizer = randomizer;
        this.animationRegister = animationRegister;
        this.routeGenerator = routeGenerator;
    }

    Randomizer getRandomizer() {
        return randomizer;
    }

    AnimationRegister getAnimationRegister() {
        return animationRegister;
    }

    RouteGenerator getRouteGenerator() {
        return routeGenerator;
    }
}
