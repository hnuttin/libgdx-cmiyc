package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.actor.HasPower;
import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

public class Player extends MovableActor implements HasPower {

    private final PlayerProfile playerProfile;
    private int actionPointsConsumed;

    public Player(ActorContext context, HasPosition hasPosition, PlayerProfile playerProfile) {
        super(context, "player", hasPosition);
        this.playerProfile = playerProfile;
        this.actionPointsConsumed = 0;
    }

    public void damageFromEnemy(Enemy enemy) {
        playerProfile.damage(enemy.getPower());
    }

    @Override
    public CollisionResult moveToDirection(Direction direction) {
        if (hasActionPointsLeft()) {
            CollisionResult collisionResult = super.moveToDirection(direction);
            if (collisionResult.isNoCollision()) {
                actionPointsConsumed++;
                GameEventBus.publishEvent(new PlayerMovedEvent(this));
            }
            return collisionResult;
        } else {
            return CollisionResult.noCollision();
        }
    }

    public int getActionPointsPerTurn() {
        return playerProfile.getActionPointsPerTurn();
    }

    public int getActionPointsConsumed() {
        return actionPointsConsumed;
    }

    public boolean hasActionPointsLeft() {
        return actionPointsConsumed < playerProfile.getActionPointsPerTurn();
    }

    public void resetActionPoints() {
        actionPointsConsumed = 0;
    }

    public void incrementHp() {
        playerProfile.incrementHp();
    }

    public int getSight() {
        return playerProfile.getSight();
    }

    public int getHp() {
        return playerProfile.getHp();
    }

    public int getMaxHp() {
        return playerProfile.getMaxHp();
    }
}
