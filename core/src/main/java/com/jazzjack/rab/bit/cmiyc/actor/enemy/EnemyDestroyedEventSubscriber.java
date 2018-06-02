package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface EnemyDestroyedEventSubscriber extends EventSubscriber {

    void enemyDestroyed(EnemyDestroyedEvent event);
}
