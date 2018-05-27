package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemies {

    private final RouteGenerator routeGenerator;
    private final List<Enemy> listOfEnemies;

    public Enemies(RouteGenerator routeGenerator, List<Enemy> listOfEnemies) {
        this.routeGenerator = routeGenerator;
        this.listOfEnemies = listOfEnemies;
    }

    public void generateRoutes() {
        listOfEnemies.forEach(enemy -> enemy.generateRoutes(routeGenerator));
    }

    public CompletableFuture<Void> moveAllEnemies() {
        CompletableFuture<Void> moveAllEnemiesFuture = null;
        for (Enemy enemy : listOfEnemies) {
            if (moveAllEnemiesFuture == null) {
                moveAllEnemiesFuture = enemy.moveAlongRandomRoute();
            } else {
                moveAllEnemiesFuture = moveAllEnemiesFuture.thenCompose(previousResult -> enemy.moveAlongRandomRoute());
            }
        }
        return moveAllEnemiesFuture;
    }

}
