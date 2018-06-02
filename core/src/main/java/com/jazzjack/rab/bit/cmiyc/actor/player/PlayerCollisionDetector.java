package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyAddedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyAddedEventSubscriber;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyDestroyedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyDestroyedEventSubscriber;
import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollidablesCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetectorCombiner;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.LevelCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import static java.util.Arrays.asList;

public class PlayerCollisionDetector implements CollisionDetector, EnemyAddedEventSubscriber, EnemyDestroyedEventSubscriber {

    private final CollidablesCollisionDetector collisionDetectorWithEnemies;
    private final CollisionDetectorCombiner collisionDetectorCombiner;

    public PlayerCollisionDetector(LevelCollisionDetector levelCollisionDetector) {
        this.collisionDetectorWithEnemies = new CollidablesCollisionDetector();
        this.collisionDetectorCombiner = new CollisionDetectorCombiner(asList(levelCollisionDetector, collisionDetectorWithEnemies));

        GameEventBus.registerSubscriber(this);
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        return collisionDetectorCombiner.collides(collidable, direction);
    }

    @Override
    public void enemyAdded(EnemyAddedEvent event) {
        collisionDetectorWithEnemies.addCollidable(event.getEnemy());
    }

    @Override
    public void enemyDestroyed(EnemyDestroyedEvent event) {
        collisionDetectorWithEnemies.removeCollidable(event.getEnemy());
    }
}
