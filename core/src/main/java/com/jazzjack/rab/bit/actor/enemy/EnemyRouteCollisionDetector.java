package com.jazzjack.rab.bit.actor.enemy;

import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.game.GameObjectProvider;

public class EnemyRouteCollisionDetector implements CollisionDetector {

    private final CollisionDetector collisionDetector;
    private final GameObjectProvider gameObjectProvider;

    public EnemyRouteCollisionDetector(CollisionDetector collisionDetector, GameObjectProvider gameObjectProvider) {
        this.collisionDetector = collisionDetector;
        this.gameObjectProvider = gameObjectProvider;
    }

    @Override
    public boolean collides(Collidable collidable) {
        return collidesWithEnemyRoutes(collidable) || collisionDetector.collides(collidable);
    }

    private boolean collidesWithEnemyRoutes(Collidable collidable) {
        return gameObjectProvider.getEnemies().stream()
                .flatMap(enemy -> enemy.getRoutes().stream())
                .flatMap(route -> route.getSteps().stream())
                .anyMatch(step -> step.collides(collidable));
    }
}
