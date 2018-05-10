package com.jazzjack.rab.bit.actor.enemy;

import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.route.Route;
import com.jazzjack.rab.bit.route.RouteGenerator;
import com.jazzjack.rab.bit.route.Step;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends SimpleActor {

    private final RouteGenerator routeGenerator;

    private List<Route> routes;

    public Enemy(RouteGenerator routeGenerator, float startX, float startY, float size) {
        super("enemy1", startX, startY, size);
        this.routeGenerator = routeGenerator;
        routes = new ArrayList<>();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void generateRoutes() {
        routes = routeGenerator.generateRoutes(this, 2, 4);
    }

    public Animation animateEnemy(Randomizer randomizer) {
        Animation animation = new EnemyRouteAnimation(this, chooseRoute(randomizer));
        routes.clear();
        return animation;
    }

    private Route chooseRoute(Randomizer randomizer) {
        return routes.get(randomizer.randomInteger(routes.size()));
    }

    void moveToStep(Step step) {
        super.moveToDirection(step.getDirection());
    }

}