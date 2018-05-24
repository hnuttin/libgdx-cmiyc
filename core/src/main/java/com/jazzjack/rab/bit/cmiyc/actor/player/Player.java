package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.actor.SimpleActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.common.HasPosition;

import java.util.function.Function;

public class Player extends SimpleActor {

    private static final int DEFAULT_SIGHT = 5;

    private int maxNumberOfMoves;
    private int movements;

    private int maxHp;
    private int hp;

    public Player(HasPosition hasPosition) {
        super("player", hasPosition);

        maxNumberOfMoves = 5;
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
