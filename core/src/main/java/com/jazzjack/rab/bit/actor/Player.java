package com.jazzjack.rab.bit.actor;

import com.jazzjack.rab.bit.collision.CollisionDetector;

import java.util.function.Function;

public class Player extends SimpleActor {

    private static final int DEFAULT_SIGHT = 256;

    private int maxNumberOfMoves;
    private int movements;

    public Player(float startX, float startY, float size) {
        super("player", startX, startY, size);

        maxNumberOfMoves = 3;
    }

    public int getSight() {
        return DEFAULT_SIGHT;
    }

    @Override
    public boolean moveRight(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveRight);
    }

    @Override
    public boolean moveLeft(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveLeft);
    }

    @Override
    public boolean moveUp(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveUp);
    }

    @Override
    public boolean moveDown(CollisionDetector collisionDetector) {
        return doPlayerMove(collisionDetector, super::moveDown);
    }

    private boolean doPlayerMove(CollisionDetector collisionDetector, Function<CollisionDetector, Boolean> move) {
        if (hasMovementsLeft() && move.apply(collisionDetector)) {
            movements++;
            return true;
        }
        return false;
    }

    public boolean hasMovementsLeft() {
        return movements < maxNumberOfMoves;
    }

    public void resetMovements() {
        movements = 0;
    }
}
