package com.jazzjack.rab.bit.cmiyc.event;

import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.NewLevelSubscriber;

import java.util.ArrayList;
import java.util.List;

public class GameEventBus {

    private GameEventBus() {
        // never instantiate
    }

    private static final List<NewLevelSubscriber> NEW_LEVEL_SUBSCRIBERS = new ArrayList<>();
    public static void registerSubscriber(NewLevelSubscriber newLevelSubscriber) {
        NEW_LEVEL_SUBSCRIBERS.add(newLevelSubscriber);
    }
    public static void publishNewLevelEvent(Level newLevel) {
        NEW_LEVEL_SUBSCRIBERS.forEach(listener -> listener.onNewLevel(newLevel));
    }

    private static final List<PlayerMovedSubscriber> PLAYER_MOVED_SUBSCRIBERS = new ArrayList<>();
    public static void registerSubscriber(PlayerMovedSubscriber playerMovedSubscriber) {
        PLAYER_MOVED_SUBSCRIBERS.add(playerMovedSubscriber);
    }
    public static void publishPlayerMovedEvent() {
        PLAYER_MOVED_SUBSCRIBERS.forEach(PlayerMovedSubscriber::playerMoved);
    }
}
