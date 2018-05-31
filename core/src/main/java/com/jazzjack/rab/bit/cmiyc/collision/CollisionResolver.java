package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;

public class CollisionResolver {

    public CollisionResolvement resolveCollision(CollisionResult collisionResult) {
        if (collisionResult.isCollision()) {
            if (collisionResult.getSource() instanceof MovableActor && collisionResult.getTarget() instanceof Enemy) {
                return pushEnemy(
                        (MovableActor) collisionResult.getSource(),
                        (Enemy) collisionResult.getTarget(),
                        collisionResult);
            } else if (collisionResult.getSource() instanceof Enemy && collisionResult.getTarget() instanceof Player) {
                return doDamageToPlayer(
                        (Enemy) collisionResult.getSource(),
                        (Player) collisionResult.getTarget(),
                        collisionResult);
            }
            return CollisionResolvement.unresolved(collisionResult);
        } else {
            return CollisionResolvement.resolvedMovementAllowed(collisionResult);
        }
    }

    private CollisionResolvement pushEnemy(MovableActor player, Enemy enemy, CollisionResult collisionResult) {
        boolean pushSucceeded = enemy.pushToDirection(player, collisionResult.getDirection());
        if (pushSucceeded) {
            return CollisionResolvement.resolvedMovementAllowed(collisionResult);
        } else {
            return CollisionResolvement.resolvedMovementNotAllowed(collisionResult);
        }
    }

    private CollisionResolvement doDamageToPlayer(Enemy enemy, Player player, CollisionResult collisionResult) {
        player.damageFromEnemy(enemy);
        return CollisionResolvement.resolvedMovementNotAllowed(collisionResult);
    }
}
