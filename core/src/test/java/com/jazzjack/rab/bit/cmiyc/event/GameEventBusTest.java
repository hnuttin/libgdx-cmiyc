package com.jazzjack.rab.bit.cmiyc.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SuspiciousMethodCalls")
class GameEventBusTest {

    @Test
    void classWithOneEventSubscriber() {
        GameEventBus gameEventBus = new GameEventBus();
        ClassWithOneEventSubscriber eventSubscriber = new ClassWithOneEventSubscriber();

        gameEventBus.registerEventSubscriberInternal(eventSubscriber);
        assertThat(gameEventBus.eventSubscribersMap.get(Event1.class)).hasSize(1);

        gameEventBus.publishEventInternal(new Event1());
        assertThat(eventSubscriber.event1Published).isTrue();
    }

    @Test
    void classWithMultipleEventSubscribers() {
        GameEventBus gameEventBus = new GameEventBus();
        ClassWithMultipleEventSubscribers eventSubscriber = new ClassWithMultipleEventSubscribers();

        gameEventBus.registerEventSubscriberInternal(eventSubscriber);
        assertThat(gameEventBus.eventSubscribersMap.get(Event1.class)).hasSize(1);
        assertThat(gameEventBus.eventSubscribersMap.get(Event2.class)).hasSize(1);

        gameEventBus.publishEventInternal(new Event1());
        assertThat(eventSubscriber.event1Published).isTrue();
        gameEventBus.publishEventInternal(new Event2());
        assertThat(eventSubscriber.event2Published).isTrue();
    }

    @Test
    void registerMultipleSubscribersWithSameEventSubscriber() {
        GameEventBus gameEventBus = new GameEventBus();
        ClassWithOneEventSubscriber eventSubscriber1 = new ClassWithOneEventSubscriber();
        ClassWithMultipleEventSubscribers eventSubscriber2 = new ClassWithMultipleEventSubscribers();

        gameEventBus.registerEventSubscriberInternal(eventSubscriber1);
        gameEventBus.registerEventSubscriberInternal(eventSubscriber2);
        assertThat(gameEventBus.eventSubscribersMap.get(Event1.class)).hasSize(2);
        assertThat(gameEventBus.eventSubscribersMap.get(Event2.class)).hasSize(1);

        gameEventBus.publishEventInternal(new Event1());
        assertThat(eventSubscriber1.event1Published).isTrue();
        assertThat(eventSubscriber2.event1Published).isTrue();
        gameEventBus.publishEventInternal(new Event2());
        assertThat(eventSubscriber2.event2Published).isTrue();
    }

    private class Event1 implements Event {
    }

    private class Event2 implements Event {
    }

    private interface EventSubscriber1 extends EventSubscriber {
        void event1(Event1 event1);
    }

    private interface EventSubscriber2 extends EventSubscriber {
        void event2(Event2 integer);
    }

    private static class ClassWithOneEventSubscriber implements EventSubscriber1 {

        private boolean event1Published;

        @Override
        public void event1(Event1 event1) {
            event1Published = true;
        }

        public void otherMethod() {
            // do nothing
        }
    }

    private static class ClassWithMultipleEventSubscribers implements EventSubscriber1, EventSubscriber2 {

        private boolean event1Published;
        private boolean event2Published;

        @Override
        public void event1(Event1 event1) {
            event1Published = true;
        }

        @Override
        public void event2(Event2 integer) {
            event2Published = true;
        }

        public void otherMethod() {
            // do nothing
        }
    }

}