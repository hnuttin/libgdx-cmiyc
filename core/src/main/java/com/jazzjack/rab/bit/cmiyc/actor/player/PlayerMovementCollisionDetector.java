package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.cmiyc.level.Level;

public class PlayerMovementCollisionDetector extends LevelCollisionDetectorWithCollidables {

    public PlayerMovementCollisionDetector(Level level) {
        super(level);
        super.addCollidable(level.getEnemies());
    }
}
