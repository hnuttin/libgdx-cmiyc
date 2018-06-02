package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.event.Event;

public class EnemyAddedEvent implements Event {

    private final Enemy enemy;

    public EnemyAddedEvent(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
