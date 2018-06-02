package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.ArrayList;
import java.util.List;

public class EnemyRouteCollisionDetector implements CollisionDetector {

    private final CollisionDetector collisionDetector;
    private final List<Enemy> enemies;

    public EnemyRouteCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
        this.enemies = new ArrayList<>();
    }

    public void addEnemies(List<Enemy> enemies) {
        this.enemies.addAll(enemies);
    }

    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        CollisionResult collisionResult = collidesWithEnemyRoutes(collidable, direction);
        if (collisionResult.isCollision()) {
            return collisionResult;
        } else {
            return collisionDetector.collides(collidable, direction);
        }
    }

    private CollisionResult collidesWithEnemyRoutes(Collidable collidable, Direction direction) {
        return enemies.stream()
                .flatMap(enemy -> enemy.getRoutes().stream())
                .flatMap(route -> route.getSteps().stream())
                .filter(step -> step.willCollideWith(collidable))
                .findFirst()
                .map(step -> CollisionResult.collision(collidable, step, direction))
                .orElse(CollisionResult.noCollision());
    }
}
