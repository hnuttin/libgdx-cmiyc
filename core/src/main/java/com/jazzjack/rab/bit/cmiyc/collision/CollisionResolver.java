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
            }
            return collisionResult;
        } else {
            return collisionResult;
        }
    }

    private CollisionResult handlePlayerWithEnemyCollision(Player player, Enemy enemy, Direction direction) {
        enemy.pushByPlayer(player, direction);
        // TODO push result: enemy moved, or enemy killed?
        return player.moveToDirection(direction);
    }

    private CollisionResult handleEnemyToPlayerCollision(Enemy enemy, Player player, Direction direction) {
        player.damangeFromEnemy(enemy);
        return CollisionResult.resolved(enemy, player, direction);
    }
}
