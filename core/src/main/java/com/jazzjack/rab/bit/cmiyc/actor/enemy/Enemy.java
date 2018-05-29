package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.cmiyc.actor.HasPower;
import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.AnimationRoute;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Step;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.game.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemy extends MovableActor implements HasPower {

    private final EnemyContext context;

    private final Predictability predictability;
    private final List<Route> routes;

    private final int power;

    public Enemy(EnemyContext context, String name, Predictability predictability, HasPosition hasPosition) {
        super(context, name, hasPosition);
        this.context = context;
        this.predictability = predictability;
        this.routes = new ArrayList<>();
        this.power = 1;
    }

    public Predictability getPredictability() {
        return predictability;
    }

    public ImmutableList<Route> getRoutes() {
        return ImmutableList.copyOf(routes);
    }

    @Override
    public int getPower() {
        return power;
    }

    public void generateRoutes() {
        routes.clear();
        routes.addAll(context.getRouteGenerator().generateRoutes(this, 2, 4));
    }

    public CompletableFuture<Void> moveAlongRandomRoute() {
        if (routes.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        } else {
            AnimationRoute routeToAnimate = new AnimationRoute(chooseRoute(context.getRandomizer()));
            routes.clear();
            routes.add(routeToAnimate);
            EnemyRouteAnimation animation = new EnemyRouteAnimation(this, routeToAnimate);
            return context.getAnimationRegister().registerAnimation(animation);
        }
    }

    private Route chooseRoute(Randomizer randomizer) {
        return randomizer.chooseRandomChance(routes);
    }

    CollisionResult moveToStep(Step step) {
        return moveToDirection(step.getDirection());
    }

    void removeRoute(Route route) {
        routes.remove(route);
    }

    public boolean pushToDirection(HasPower hasPower, Direction direction) {
        CollisionResult collisionResult = moveToDirection(direction);
        if (collisionResult.isUnresolved()) {
            if (hasPower.getPower() >= this.getPower()) {
                GameEventBus.publishEvent(new EnemyDestroyedEvent(this));
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
