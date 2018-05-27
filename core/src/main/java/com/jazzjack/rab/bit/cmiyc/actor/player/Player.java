package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.actor.SimpleActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.common.HasPosition;

import java.util.function.Function;

public class Player extends SimpleActor {

    private static final int DEFAULT_SIGHT = 5;

    private int actionPointsPerTurn;
    private int actionPointsConsumed;

    private int maxHp;
    private int hp;

    public Player(HasPosition hasPosition) {
        super("player", hasPosition);

        this.actionPointsPerTurn = 5;
        this.actionPointsConsumed = 0;
        this.maxHp = 5;
        this.hp = 3;
    }

    public int getSight() {
        return DEFAULT_SIGHT;
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
    public PlayerMovementResult moveRight(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveRight);
    }

    @Override
    public PlayerMovementResult moveLeft(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveLeft);
    }

    @Override
    public PlayerMovementResult moveUp(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveUp);
    }

    @Override
    public PlayerMovementResult moveDown(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveDown);
    }

    private PlayerMovementResult doPlayerMove(CollisionDetector collisionDetector, Function<CollisionDetector, CollisionResult> move) {
        if (hasActionPointsLeft()) {
            CollisionResult collisionResult = move.apply(collisionDetector);
            if (!collisionResult.isCollision()) {
                actionPointsConsumed++;
            }
            return PlayerMovementResult.fromCollisionResult(collisionResult);
        } else {
            return PlayerMovementResult.moMovementsLeft();
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
