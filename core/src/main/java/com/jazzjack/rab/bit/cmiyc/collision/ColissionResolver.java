package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;

public class ColissionResolver {

    public CollisionResult resolveCollisionForPlayer(CollisionResult collisionResult) {
        if (collisionResult.getTargetCollidable() instanceof Enemy) {
            Enemy enemy = (Enemy) collisionResult.getTargetCollidable();
            enemy.pushByPlayer(player, null);
            return player.moveToDirection(null, null);
        }
        return collisionResult;
    }
}
