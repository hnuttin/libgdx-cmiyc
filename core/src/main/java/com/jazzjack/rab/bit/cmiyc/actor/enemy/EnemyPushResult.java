package com.jazzjack.rab.bit.cmiyc.actor.enemy;

public enum EnemyPushResult {

    PUSHED(true), FAILED(false), DESTROYED(true);

    private final boolean success;

    EnemyPushResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
