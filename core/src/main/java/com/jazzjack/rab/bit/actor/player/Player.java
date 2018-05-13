package com.jazzjack.rab.bit.actor.player;

import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.collision.CollisionResult;

import java.util.function.Function;

public class Player extends SimpleActor {

    private static final int DEFAULT_SIGHT = 4;

    private int maxNumberOfMoves;
    private int movements;

    private int maxHp;
    private int hp;

    public Player(float startX, float startY) {
        super("player", startX, startY);

        maxNumberOfMoves = 4;
        maxHp = 5;
        hp = 3;
    }

    public int getSight() {
        return DEFAULT_SIGHT;
    }

    public int getMovements() {
        return movements;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void doDamage(int amount) {
        hp = Math.min(0, hp - amount);
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
        if (hasMovementsLeft()) {
            CollisionResult collisionResult = move.apply(collisionDetector);
            if (!collisionResult.isCollision()) {
                movements++;
            }
            return PlayerMovementResult.fromCollisionResult(collisionResult);
        } else {
            return PlayerMovementResult.moMovementsLeft();
        }
    }

    public boolean hasMovementsLeft() {
        return movements < maxNumberOfMoves;
    }

    public void resetMovements() {
        movements = 0;
    }
}
