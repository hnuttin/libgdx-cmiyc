package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Enemies implements Supplier<List<Enemy>>, EnemyAddedEventSubscriber, EnemyDestroyedEventSubscriber {

    private final List<Enemy> listOfEnemies;

    public Enemies(List<Enemy> listOfEnemies) {
        this.listOfEnemies = listOfEnemies;
        GameEventBus.registerSubscriber(this);
    }

    @Override
    public List<Enemy> get() {
        return listOfEnemies;
    }

    public void generateRoutes() {
        listOfEnemies.forEach(Enemy::generateRoutes);
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

    @Override
    public void enemyAdded(EnemyAddedEvent event) {
        listOfEnemies.add(event.getEnemy());
    }

    @Override
    public void enemyDestroyed(EnemyDestroyedEvent event) {
        listOfEnemies.remove(event.getEnemy());
    }
}
