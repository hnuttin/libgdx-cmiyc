package com.jazzjack.rab.bit.actor.enemy;

import com.jazzjack.rab.bit.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.level.Level;

public class EnemyMovementCollisionDetector extends LevelCollisionDetectorWithCollidables {

    public EnemyMovementCollisionDetector(Level level) {
        super(level);
        addCollidable(level.getPlayer());
    }
}
