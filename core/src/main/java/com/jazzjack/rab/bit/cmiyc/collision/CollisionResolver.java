package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyPushResult;
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

    private CollisionResolvement pushEnemy(MovableActor movableActor, Enemy enemy, CollisionResult collisionResult) {
        EnemyPushResult pushSucceeded = enemy.pushToDirection(movableActor, collisionResult.getDirection());
        if (pushSucceeded.isSuccess()) {
            if (pushSucceeded == EnemyPushResult.DESTROYED && movableActor instanceof Player) {
                ((Player) movableActor).damageFromEnemy(enemy);
            }
            return CollisionResolvement.resolvedMovementAllowed(collisionResult);
        } else {
            return CollisionResolvement.unresolved(collisionResult);
        }
    }

    private CollisionResolvement doDamageToPlayer(Enemy enemy, Player player, CollisionResult collisionResult) {
        player.damageFromEnemy(enemy);
        return CollisionResolvement.resolvedMovementNotAllowed(collisionResult);
    }
}
