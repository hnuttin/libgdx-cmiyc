package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface EnemyAddedEventSubscriber extends EventSubscriber {

    void enemyAdded(EnemyAddedEvent event);
}
