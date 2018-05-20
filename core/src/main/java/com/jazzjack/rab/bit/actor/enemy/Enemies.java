package com.jazzjack.rab.bit.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.actor.enemy.route.RouteGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemies {

    private final RouteGenerator routeGenerator;
    private final EnemyMovementContext context;
    private final List<Enemy> enemies;

    public Enemies(RouteGenerator routeGenerator, EnemyMovementContext context, List<Enemy> enemies) {
        this.routeGenerator = routeGenerator;
        this.context = context;
        this.enemies = enemies;
    }

    public ImmutableList<Enemy> get() {
        return ImmutableList.copyOf(enemies);
    }

    public void generateRoutes() {
        enemies.forEach(enemy -> enemy.generateRoutes(routeGenerator));
    }

    public CompletableFuture<Void> moveAllEnemies() {
        CompletableFuture<Void> moveAllEnemiesFuture = null;
        for (Enemy enemy : enemies) {
            if (moveAllEnemiesFuture == null) {
                moveAllEnemiesFuture = enemy.moveAlongRandomRoute(context);
            } else {
                moveAllEnemiesFuture = moveAllEnemiesFuture.thenCompose((r) -> enemy.moveAlongRandomRoute(context));
            }
        }
        return moveAllEnemiesFuture;
    }

}
