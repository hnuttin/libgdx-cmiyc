package com.jazzjack.rab.bit.cmiyc.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameEventBus {

    private static final GameEventBus INSTANCE = new GameEventBus();

    public static void registerSubscriber(EventSubscriber eventSubscriber) {
        INSTANCE.registerEventSubscriberInternal(eventSubscriber);
    }

    public static void publishEvent(Event event) {
        INSTANCE.publishEventInternal(event);
    }

    final Map<Class<Event>, List<Consumer<Event>>> eventSubscribersMap;

    GameEventBus() {
        this.eventSubscribersMap = new HashMap<>();
    }

    void registerEventSubscriberInternal(EventSubscriber eventSubscriber) {
        for (Class<?> subscriberCandidate : eventSubscriber.getClass().getInterfaces()) {
            if (EventSubscriber.class.isAssignableFrom(subscriberCandidate)) {
                addSubscriberInterface(eventSubscriber, subscriberCandidate);
            }
        }
    }

    private void addSubscriberInterface(EventSubscriber eventSubscriber, Class<?> subscriberCandidate) {
        for (Method subscriberMethed : subscriberCandidate.getDeclaredMethods()) {
            if (subscriberMethed.getParameterCount() == 1) {
                if (Event.class.isAssignableFrom(subscriberMethed.getParameterTypes()[0])) {
                    //noinspection unchecked
                    addEventSubscriber(eventSubscriber, subscriberMethed, (Class<Event>) subscriberMethed.getParameterTypes()[0]);
                } else {
                    throw new IllegalArgumentException("Parameter type for declared event subscriber is not of type Event.class");
                }
            } else {
                throw new IllegalArgumentException("Declared event subscriber method has less or more than one parameter: one Event.class parameter is required");
            }
        }
    }

    private void addEventSubscriber(EventSubscriber eventSubscriber, Method subscriberMethed, Class<Event> eventClass) {
        List<Consumer<Event>> eventSubscribers = eventSubscribersMap.computeIfAbsent(eventClass, k -> new ArrayList<>());
        eventSubscribers.add(event -> {
            try {
                subscriberMethed.invoke(eventSubscriber, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Failed to invoke event subscriber", e);
            }
        });
    }

    void publishEventInternal(Event event) {
        //noinspection SuspiciousMethodCalls
        List<Consumer<Event>> eventSubscribers = eventSubscribersMap.get(event.getClass());
        if (eventSubscribers != null) {
            eventSubscribers.forEach(eventSubscriber -> eventSubscriber.accept(event));
        }
    }
}
