package com.jazzjack.rab.bit.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.actor.enemy.route.AnimationRoute;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.animation.EmptyAnimation;
import com.jazzjack.rab.bit.common.Predictability;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.actor.enemy.route.Step;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends SimpleActor {

    private final RouteGenerator routeGenerator;

    private final Predictability predictability;
    private final List<Route> routes;

    public Enemy(RouteGenerator routeGenerator, float startX, float startY) {
        super("enemy1", startX, startY);
        this.routeGenerator = routeGenerator;
        this.predictability = Predictability.HIGH;
        this.routes = new ArrayList<>();
    }

    public ImmutableList<Route> getRoutes() {
        return ImmutableList.copyOf(routes);
    }

    public Predictability getPredictability() {
        return predictability;
    }

    public void generateRoutes() {
        routes.clear();
        routes.addAll(routeGenerator.generateRoutes(this, 2, 4));
    }

    public Animation createAnimation(Randomizer randomizer) {
        if (routes.isEmpty()) {
            return new EmptyAnimation();
        } else {
            AnimationRoute routeToAnimate = new AnimationRoute(chooseRoute(randomizer));
            routes.clear();
            routes.add(routeToAnimate);
            return new EnemyRouteAnimation(this, routeToAnimate);
        }
    }

    private Route chooseRoute(Randomizer randomizer) {
        return randomizer.chooseRandomChance(routes);
    }

    void moveToStep(Step step) {
        super.moveToDirection(step.getDirection());
    }

    void removeRoute(Route route) {
        routes.remove(route);
    }
}
