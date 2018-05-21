package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemies {

    private final RouteGenerator routeGenerator;
    private final EnemyMovementContext context;
    private final List<Enemy> listOfEnemies;

    public Enemies(RouteGenerator routeGenerator, EnemyMovementContext context, List<Enemy> listOfEnemies) {
        this.routeGenerator = routeGenerator;
        this.context = context;
        this.listOfEnemies = listOfEnemies;
    }

    public void generateRoutes() {
        listOfEnemies.forEach(enemy -> enemy.generateRoutes(routeGenerator));
    }

    public CompletableFuture<Void> moveAllEnemies() {
        CompletableFuture<Void> moveAllEnemiesFuture = null;
        for (Enemy enemy : listOfEnemies) {
            if (moveAllEnemiesFuture == null) {
                moveAllEnemiesFuture = enemy.moveAlongRandomRoute(context);
            } else {
                moveAllEnemiesFuture = moveAllEnemiesFuture.thenCompose(previousResult -> enemy.moveAlongRandomRoute(context));
            }
        }
        return moveAllEnemiesFuture;
    }

}
