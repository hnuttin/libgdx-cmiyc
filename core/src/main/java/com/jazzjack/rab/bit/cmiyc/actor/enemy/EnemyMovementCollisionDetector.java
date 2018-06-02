package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollidablesCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetectorCombiner;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.LevelCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import static java.util.Arrays.asList;

public class EnemyMovementCollisionDetector implements CollisionDetector, EnemyAddedEventSubscriber, EnemyDestroyedEventSubscriber {

    private final CollidablesCollisionDetector collisionDetectorWithEnemiesAndPlayer;
    private final CollisionDetectorCombiner collisionDetectorCombiner;

    public EnemyMovementCollisionDetector(Player player, LevelCollisionDetector levelCollisionDetector) {
        this.collisionDetectorWithEnemiesAndPlayer = new CollidablesCollisionDetector();
        this.collisionDetectorWithEnemiesAndPlayer.addCollidable(player);
        this.collisionDetectorCombiner = new CollisionDetectorCombiner(asList(levelCollisionDetector, collisionDetectorWithEnemiesAndPlayer));

        GameEventBus.registerSubscriber(this);
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        return collisionDetectorCombiner.collides(collidable, direction);
    }

    @Override
    public void enemyAdded(EnemyAddedEvent event) {
        collisionDetectorWithEnemiesAndPlayer.addCollidable(event.getEnemy());
    }

    @Override
    public void enemyDestroyed(EnemyDestroyedEvent event) {
        collisionDetectorWithEnemiesAndPlayer.removeCollidable(event.getEnemy());
    }
}
