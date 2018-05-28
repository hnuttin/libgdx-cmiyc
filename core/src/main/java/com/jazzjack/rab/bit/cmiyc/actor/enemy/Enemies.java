package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Enemies {

    private final List<Enemy> listOfEnemies;

    public Enemies(List<Enemy> listOfEnemies) {
        this.listOfEnemies = listOfEnemies;
    }

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

}
