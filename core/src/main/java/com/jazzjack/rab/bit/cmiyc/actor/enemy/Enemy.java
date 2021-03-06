package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.cmiyc.actor.HasPower;
import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.EnemyRouteAnimation;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class Enemy extends MovableActor implements HasPower {

    private final EnemyContext context;

    private final EnemyConfig config;
    private final List<Route> routes;

    private boolean triggered;
    private boolean marked;

    public Enemy(EnemyContext context, EnemyConfig config, HasPosition hasPosition) {
        super(context, config.getName(), hasPosition);

        this.context = context;

        this.config = config;
        this.routes = new ArrayList<>();

        this.triggered = false;
        this.marked = false;
    }

    public EnemyConfig getConfig() {
        return config;
    }

    public ImmutableList<Route> getRoutes() {
        return ImmutableList.copyOf(routes);
    }

    public void trigger() {
        triggered = true;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        marked = true;
    }

    public void generateRoutes() {
        routes.clear();
        routes.addAll(context.getRouteGenerator().generateRoutes(this));
        publishEvent(new NewEnemyRoutesEvent(this));
    }

    public CompletableFuture<Void> moveAlongRandomRoute() {
        if (routes.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        } else {
            Route routeToAnimate = chooseRoute();
            routes.clear();
            routes.add(routeToAnimate);
            EnemyRouteAnimation animation = new EnemyRouteAnimation(this, routeToAnimate);
            return context.getAnimationRegister().registerAnimation(animation);
        }
    }

    private Route chooseRoute() {
        return context.getRandomizer().chooseRandomChance(routes);
    }

    public CollisionResult moveToStep(Step step) {
        return moveToDirection(step.getDirection());
    }

    @Override
    public CollisionResult moveToDirection(Direction direction) {
        CollisionResult collisionResult = super.moveToDirection(direction);
        if (collisionResult.isNoCollision()) {
            publishEvent(new EnemyMovedEvent(this));
        }
        return collisionResult;
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public EnemyPushResult pushToDirection(HasPower hasPower, Direction direction) {
        CollisionResult collisionResult = moveToDirection(direction);
        if (collisionResult.isCollision()) {
            if (hasPower.getPower() >= getPower()) {
                publishEvent(new EnemyDestroyedEvent(this));
                return EnemyPushResult.DESTROYED;
            } else {
                return EnemyPushResult.FAILED;
            }
        } else {
            generateRoutes();
            return EnemyPushResult.PUSHED;
        }
    }

}
