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

    private CompletableFuture<Void> moveAllEnemiesFuture;
    private int enemyIndexToAnimate;

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
        moveAllEnemiesFuture = new CompletableFuture<>();
        enemyIndexToAnimate = 0;
        animateEnemyForIndex();
        return moveAllEnemiesFuture;
    }

    private void animateEnemyForIndex() {
        if (enemyIndexToAnimate < enemies.size()) {
            Enemy enemyToAnimate = enemies.get(enemyIndexToAnimate);
            enemyIndexToAnimate++;
            enemyToAnimate.moveAlongRandomRoute(collisionDetector, randomizer)
                    .thenRun(this::animateEnemyForIndex);
        } else {
            moveAllEnemiesFuture.complete(null);
        }
    }
}
