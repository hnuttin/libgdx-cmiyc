package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class CollisionResolver {

    public CollisionResult resolveCollision(CollisionResult collisionResult) {
        if (collisionResult.isUnresolved()) {
            if (collisionResult.getSourceCollidable() instanceof Player && collisionResult.getTargetCollidable() instanceof Enemy) {
                return handlePlayerWithEnemyCollision(
                        (Player) collisionResult.getSourceCollidable(),
                        (Enemy) collisionResult.getTargetCollidable(),
                        collisionResult.getDirection());
            } else if (collisionResult.getSourceCollidable() instanceof Enemy && collisionResult.getTargetCollidable() instanceof Player) {
                return handleEnemyToPlayerCollision(
                        (Enemy) collisionResult.getSourceCollidable(),
                        (Player) collisionResult.getTargetCollidable(),
                        collisionResult.getDirection());
            } else if (collisionResult.getSourceCollidable() instanceof Enemy && collisionResult.getTargetCollidable() instanceof Enemy) {
                return handleEnemhyithEnemyCollision(
                        (Enemy) collisionResult.getSourceCollidable(),
                        (Enemy) collisionResult.getTargetCollidable(),
                        collisionResult.getDirection());
            }
            return collisionResult;
        } else {
            return collisionResult;
        }
    }

    private CollisionResult handleEnemhyithEnemyCollision(Enemy enemy1, Enemy enemy2, Direction direction) {
        boolean pushSucceeded = enemy2.pushToDirection(enemy1, direction);
        if (pushSucceeded) {
            return enemy1.moveToDirection(direction);
        } else {
            return CollisionResult.unresolved(enemy1, enemy2, direction);
        }
    }

    private CollisionResult handlePlayerWithEnemyCollision(Player player, Enemy enemy, Direction direction) {
        boolean pushSucceeded = enemy.pushToDirection(player, direction);
        if (pushSucceeded) {
            return player.moveToDirection(direction);
        } else {
            return CollisionResult.unresolved(player, enemy, direction);
        }
    }

    private CollisionResult handleEnemyToPlayerCollision(Enemy enemy, Player player, Direction direction) {
        player.damangeFromEnemy(enemy);
        return CollisionResult.resolved(enemy, player, direction);
    }
}
