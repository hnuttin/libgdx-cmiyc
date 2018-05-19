package com.jazzjack.rab.bit.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.actor.enemy.route.AnimationRoute;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.actor.enemy.route.Step;
import com.jazzjack.rab.bit.animation.AnimationRegister;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.collision.CollisionResult;
import com.jazzjack.rab.bit.common.Predictability;
import com.jazzjack.rab.bit.common.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemy extends SimpleActor {

    private final AnimationRegister animationRegister;

    private final Predictability predictability;
    private final List<Route> routes;

    public Enemy(AnimationRegister animationRegister, float startX, float startY) {
        super("enemy1", startX, startY);
        this.animationRegister = animationRegister;
        this.predictability = Predictability.HIGH;
        this.routes = new ArrayList<>();
    }

    public ImmutableList<Route> getRoutes() {
        return ImmutableList.copyOf(routes);
    }

    public Predictability getPredictability() {
        return predictability;
    }

    public void generateRoutes(RouteGenerator routeGenerator) {
        routes.clear();
        routes.addAll(routeGenerator.generateRoutes(this, 2, 4));
    }

    public CompletableFuture<Void> moveAlongRandomRoute(CollisionDetector collisionDetector, Randomizer randomizer) {
        if (routes.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        } else {
            AnimationRoute routeToAnimate = new AnimationRoute(chooseRoute(randomizer));
            routes.clear();
            routes.add(routeToAnimate);
            EnemyRouteAnimation animation = new EnemyRouteAnimation(collisionDetector, this, routeToAnimate);
            return animationRegister.registerAnimation(animation);
        }
    }

    private Route chooseRoute(Randomizer randomizer) {
        return randomizer.chooseRandomChance(routes);
    }

    CollisionResult moveToStep(CollisionDetector collisionDetector, Step step) {
        CollisionResult collisionResult = super.moveToDirection(collisionDetector, step.getDirection());
        // TODO do player damage on collision
        return collisionResult;
    }

    void removeRoute(Route route) {
        routes.remove(route);
    }
}
