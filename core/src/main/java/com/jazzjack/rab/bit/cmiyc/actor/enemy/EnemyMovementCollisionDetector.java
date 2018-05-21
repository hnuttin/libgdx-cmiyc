package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.cmiyc.level.Level;

public class EnemyMovementCollisionDetector extends LevelCollisionDetectorWithCollidables {

    public EnemyMovementCollisionDetector(Level level) {
        super(level);
        addCollidable(level.getPlayer());
    }
}
