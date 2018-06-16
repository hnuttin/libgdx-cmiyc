package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.event.EventSubscriber;

public interface NewEnemyRouteSubscriber extends EventSubscriber {

    void newEnemyRoutes(NewEnemyRoutesEvent event);
}
