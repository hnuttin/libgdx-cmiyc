package com.jazzjack.rab.bit.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.common.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemies {

    private final CollisionDetector collisionDetector;
    private final Randomizer randomizer;
    private final List<Enemy> enemies;

    public Enemies(Randomizer randomizer, CollisionDetector collisionDetector) {
        this.randomizer = randomizer;
        this.collisionDetector = collisionDetector;
        this.enemies = new ArrayList<>();
    }

    public void add(Enemy enemy) {
        enemies.add(enemy);
    }

    public ImmutableList<Enemy> get() {
        return ImmutableList.copyOf(enemies);
    }

    public void generateRoutes() {
        enemies.forEach(Enemy::generateRoutes);
    }

    public CompletableFuture<Void> moveAllEnemies() {
        CompletableFuture<Void> moveAllEnemiesFuture = null;
        for (Enemy enemy : enemies) {
            if (moveAllEnemiesFuture == null) {
                moveAllEnemiesFuture = enemy.moveAlongRandomRoute(collisionDetector, randomizer);
            } else {
                moveAllEnemiesFuture = moveAllEnemiesFuture.thenCompose((r) -> enemy.moveAlongRandomRoute(collisionDetector, randomizer));
            }
        }
        return moveAllEnemiesFuture;
    }

}
