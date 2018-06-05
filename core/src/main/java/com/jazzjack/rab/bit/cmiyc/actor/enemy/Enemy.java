package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.cmiyc.actor.HasPower;
import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.EnemyRouteAnimation;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemy extends MovableActor implements HasPower {

    private final EnemyContext context;

    private final Predictability predictability;
    private final List<Route> routes;
    private final Sense sense;

    public Enemy(EnemyContext context, String name, Predictability predictability, HasPosition hasPosition) {
        super(context, name, hasPosition);
        this.context = context;
        this.predictability = predictability;
        this.routes = new ArrayList<>();
        this.sense = Sense.MEDIUM;
    }

    public Predictability getPredictability() {
        return predictability;
    }

    public ImmutableList<Route> getRoutes() {
        return ImmutableList.copyOf(routes);
    }

    public void generateRoutes() {
        routes.clear();
        routes.addAll(context.getRouteGenerator().generateRoutes(this, 2, 4));
    }

    public CompletableFuture<Void> moveAlongRandomRoute() {
        if (routes.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        } else {
            Route routeToAnimate = chooseRoute(context.getRandomizer());
            routes.clear();
            routes.add(routeToAnimate);
            EnemyRouteAnimation animation = new EnemyRouteAnimation(this, routeToAnimate);
            return context.getAnimationRegister().registerAnimation(animation);
        }
    }

    private Route chooseRoute(Randomizer randomizer) {
        return randomizer.chooseRandomChance(routes);
    }

    public CollisionResult moveToStep(Step step) {
        return moveToDirection(step.getDirection());
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public EnemyPushResult pushToDirection(HasPower hasPower, Direction direction) {
        CollisionResult collisionResult = moveToDirection(direction);
        if (collisionResult.isCollision()) {
            if (hasPower.getPower() >= getPower()) {
                GameEventBus.publishEvent(new EnemyDestroyedEvent(this));
                return EnemyPushResult.DESTROYED;
            } else {
                return EnemyPushResult.FAILED;
            }
        } else {
            generateRoutes();
            return EnemyPushResult.PUSHED;
        }
    }

    public Sense getSense() {
        return sense;
    }
}
