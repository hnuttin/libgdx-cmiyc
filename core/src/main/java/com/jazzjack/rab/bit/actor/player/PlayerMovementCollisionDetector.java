package com.jazzjack.rab.bit.actor.player;

import com.jazzjack.rab.bit.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.level.Level;

public class PlayerMovementCollisionDetector extends LevelCollisionDetectorWithCollidables {

    public PlayerMovementCollisionDetector(Level level) {
        super(level);
        super.addCollidable(level.getEnemies());
    }
}
