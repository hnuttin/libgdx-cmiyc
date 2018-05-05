package com.jazzjack.rab.bit.actor;

import com.jazzjack.rab.bit.route.Route;
import com.jazzjack.rab.bit.route.RouteGenerator;

import java.util.HashSet;
import java.util.Set;

public class Enemy extends SimpleActor {

    private final RouteGenerator routeGenerator;

    private Set<Route> routes;

    public Enemy(RouteGenerator routeGenerator, float startX, float startY, float size) {
        super("enemy1", startX, startY, size);
        this.routeGenerator = routeGenerator;
        routes = new HashSet<>();
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void generateRoutes(int amount, int maxLength) {
        routes = routeGenerator.generateRoutes(this, amount, maxLength);
    }
}
