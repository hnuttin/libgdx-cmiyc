package com.jazzjack.rab.bit.cmiyc.game;

import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.NewLevelSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEventBus {

    private GameEventBus() {
        // never instantiate
    }

    private static final Map<Class<Event>, List<EventSubscriber<Event>>> eventSubscribersMap = new HashMap<>();

    public static <E extends Event> void registerEventSubscriber(EventSubscriber<E> eventSubscriber, Class<E> eventClass) {
        List<EventSubscriber<Event>> eventSubscribers = GameEventBus.eventSubscribersMap.get(eventClass);
        if (eventSubscribers == null) {
            eventSubscribers = new ArrayList<>();
            eventSubscribersMap.put((Class<Event>) eventClass, eventSubscribers);
        }
        eventSubscribers.add((EventSubscriber<Event>) eventSubscriber);
    }

    public static void publishEvent(Event event) {
        List<EventSubscriber<Event>> eventSubscribers = eventSubscribersMap.get(event.getClass());
        eventSubscribers.forEach(eventSubscriber -> eventSubscriber.handleEvent(event));
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
