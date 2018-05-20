package com.jazzjack.rab.bit.actor.enemy;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.collision.CollisionResult;

import java.util.List;

public class EnemyRouteCollisionDetector implements CollisionDetector {

    private final CollisionDetector collisionDetector;
    private final List<Enemy> enemies;

    public EnemyRouteCollisionDetector(CollisionDetector collisionDetector, List<Enemy> enemies) {
        this.collisionDetector = collisionDetector;
        this.enemies = enemies;
    }

    @Override
    public CollisionResult collides(Collidable collidable) {
        CollisionResult collisionResult = collidesWithEnemyRoutes(collidable);
        if (collisionResult.isCollision()) {
            return collisionResult;
        } else {
            return collisionDetector.collides(collidable);
        }
    }

    private CollisionResult collidesWithEnemyRoutes(Collidable collidable) {
        return enemies.stream()
                .flatMap(enemy -> enemy.getRoutes().stream())
                .flatMap(route -> route.getSteps().stream())
                .filter(step -> step.collidesWith(collidable))
                .findFirst()
                .map(CollisionResult::collision)
                .orElse(CollisionResult.noCollision());
    }
}
