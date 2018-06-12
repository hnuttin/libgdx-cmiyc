package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.registerSubscriber;
import static java.util.stream.Collectors.toList;

public class Items implements PlayerMovedSubscriber {

    private static final Map<Item, Function<ItemMarkerObject, BaseHandler>> HANDLER_FACTORIES;

    static {
        HANDLER_FACTORIES = new HashMap<>();
        HANDLER_FACTORIES.put(Item.HP, (HpHandler::new));
        HANDLER_FACTORIES.put(Item.SHIELD, (ShieldHandler::new));
    }

    private final List<BaseHandler> itemHandlers;

    public Items(List<ItemMarkerObject> items) {
        this.itemHandlers = items.stream().map(this::createHandler).collect(toList());
        registerSubscriber(this);
    }

    private BaseHandler createHandler(ItemMarkerObject itemMarkerObject) {
        return HANDLER_FACTORIES.get(itemMarkerObject.getItem()).apply(itemMarkerObject);
    }

    public List<ItemMarkerObject> getItems() {
        return itemHandlers.stream().map(baseHandler -> baseHandler.itemMarkerObject).collect(toList());
    }

    @Override
    public void playerMoved(PlayerMovedEvent event) {
        handleItemPickup(event.getPlayer());
    }

    private void handleItemPickup(Player player) {
        itemHandlers.stream()
                .filter(handler -> handler.hasSamePositionAs(player))
                .findFirst()
                .ifPresent(handler -> {
                    handler.handle(player);
                    itemHandlers.remove(handler);
                });
    }

    private static abstract class BaseHandler implements HasPosition {

        private final ItemMarkerObject itemMarkerObject;

        private BaseHandler(ItemMarkerObject itemMarkerObject) {
            this.itemMarkerObject = itemMarkerObject;
        }

        ItemMarkerObject getItemMarkerObject() {
            return itemMarkerObject;
        }

        protected abstract void handle(Player player);

        @Override
        public int getX() {
            return itemMarkerObject.getX();
        }

        @Override
        public int getY() {
            return itemMarkerObject.getY();
        }
    }

    private static class HpHandler extends BaseHandler {

        private HpHandler(ItemMarkerObject markerObject) {
            super(markerObject);
        }

        @Override
        protected void handle(Player player) {
            player.incrementHp();
        }
    }

    private static class ShieldHandler extends BaseHandler {

        private ShieldHandler(ItemMarkerObject itemMarkerObject) {
            super(itemMarkerObject);
        }

        @Override
        protected void handle(Player player) {
            player.pickupItem(getItemMarkerObject().getItem());
        }
    }
}
