package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface EnemyMovedSubscriber extends EventSubscriber {

    void enemyMoved(EnemyMovedEvent event);
}
