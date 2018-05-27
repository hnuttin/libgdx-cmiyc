package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.actor.SimpleActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.HasPosition;

public class Player extends SimpleActor {

    private int actionPointsPerTurn;
    private int actionPointsConsumed;

    private int maxHp;
    private int hp;

    private int sight;
    private int power;

    public Player(HasPosition hasPosition) {
        super("player", hasPosition);

        this.actionPointsPerTurn = 5;
        this.actionPointsConsumed = 0;

        this.maxHp = 5;
        this.hp = 3;

        this.sight = 5;
        this.power = 1;
    }

    public int getSight() {
        return sight;
    }

    public int getPower() {
        return power;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void damangeFromEnemy(Enemy enemy) {
        hp = Math.max(0, hp - enemy.getDamageOutput());
    }

    public boolean isDead() {
        return hp == 0;
    }

    @Override
    public PlayerMovementResult moveToDirection(ActorMovementContext context, Direction direction) {
        if (hasActionPointsLeft()) {
            CollisionResult collisionResult = super.moveToDirection(context.getCollisionDetector(), direction);
            if (!collisionResult.isCollision()) {
                actionPointsConsumed++;
            } else {
                collisionResult = context.getColissionResolver().resolveCollisionForPlayer(collisionResult);
            }
            return PlayerMovementResult.fromCollisionResult(collisionResult);
        } else {
            return PlayerMovementResult.noMovementsLeftResult();
        }
    }

    public int getActionPointsPerTurn(){
        return actionPointsPerTurn;
    }

    public int getActionPointsConsumed() {
        return actionPointsConsumed;
    }

    public boolean hasActionPointsLeft() {
        return actionPointsConsumed < actionPointsPerTurn;
    }

    public void resetActionPoints() {
        actionPointsConsumed = 0;
    }
}
