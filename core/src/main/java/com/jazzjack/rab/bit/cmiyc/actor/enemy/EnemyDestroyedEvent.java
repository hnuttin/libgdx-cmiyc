package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.game.Event;

public class EnemyDestroyedEvent implements Event {

    private final Enemy enemy;

    public EnemyDestroyedEvent(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
