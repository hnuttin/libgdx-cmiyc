package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

public class CollisionResolver {

    public CollisionResult resolveCollision(CollisionResult collisionResult) {
        if (collisionResult.isUnresolved()) {
            if (collisionResult.getSourceCollidable() instanceof MovableActor && collisionResult.getTargetCollidable() instanceof Enemy) {
                return pushEnemy(
                        (MovableActor) collisionResult.getSourceCollidable(),
                        (Enemy) collisionResult.getTargetCollidable(),
                        collisionResult.getDirection());
            } else if (collisionResult.getSourceCollidable() instanceof Enemy && collisionResult.getTargetCollidable() instanceof Player) {
                return doDamageToPlayer(
                        (Enemy) collisionResult.getSourceCollidable(),
                        (Player) collisionResult.getTargetCollidable(),
                        collisionResult.getDirection());
            }
            return collisionResult;
        } else {
            return collisionResult;
        }
    }

    private CollisionResult pushEnemy(MovableActor player, Enemy enemy, Direction direction) {
        boolean pushSucceeded = enemy.pushToDirection(player, direction);
        if (pushSucceeded) {
            return CollisionResult.resolved(player, enemy, direction);
        } else {
            return CollisionResult.unresolved(player, enemy, direction);
        }
    }

    private CollisionResult doDamageToPlayer(Enemy enemy, Player player, Direction direction) {
        player.damageFromEnemy(enemy);
        return CollisionResult.resolved(enemy, player, direction);
    }
}
