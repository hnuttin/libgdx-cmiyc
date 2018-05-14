package com.jazzjack.rab.bit.actor.enemy;

import com.google.common.collect.ImmutableList;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.animation.AnimationRegister;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.common.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class Enemies {

    private final CollisionDetector collisionDetector;
    private final Randomizer randomizer;
    private final AnimationRegister animationRegister;
    private final List<Enemy> enemies;

    private Runnable onMovementsFinished;
    private int enemyIndexToAnimate;

    public Enemies(Randomizer randomizer, CollisionDetector collisionDetector, AnimationRegister animationRegister) {
        this.randomizer = randomizer;
        this.animationRegister = animationRegister;
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

    public void moveAllEnemies(Runnable onMovementsFinished) {
        this.onMovementsFinished = onMovementsFinished;
        enemyIndexToAnimate = 0;
        animateEnemyForIndex();
    }

    private void animateEnemyForIndex() {
        if (enemyIndexToAnimate < enemies.size()) {
            Enemy enemyToAnimate = enemies.get(enemyIndexToAnimate);
            Animation animation = enemyToAnimate.createAnimation(collisionDetector, randomizer);
            enemyIndexToAnimate++;
            animationRegister.registerAnimation(animation, this::animateEnemyForIndex);
        } else {
            onMovementsFinished.run();
        }
    }
}
