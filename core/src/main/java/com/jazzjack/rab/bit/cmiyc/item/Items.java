package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.registerSubscriber;
import static java.util.stream.Collectors.toList;

public class Items implements PlayerMovedSubscriber {

    private static final EnumMap<Item, Function<ItemMarkerObject, ItemHandler>> HANDLER_FACTORIES;

    static {
        HANDLER_FACTORIES = new EnumMap<>(Item.class);
        HANDLER_FACTORIES.put(Item.HP, HpItemHandler::new);
        HANDLER_FACTORIES.put(Item.SHIELD, ShieldItemHandler::new);
    }

    private final List<ItemHandler> itemHandlers;

    public Items(List<ItemMarkerObject> items) {
        this.itemHandlers = items.stream().map(this::createHandler).collect(toList());
        registerSubscriber(this);
    }

    private ItemHandler createHandler(ItemMarkerObject itemMarkerObject) {
        return HANDLER_FACTORIES.get(itemMarkerObject.getItem()).apply(itemMarkerObject);
    }

    public List<ItemMarkerObject> getItems() {
        return itemHandlers.stream().map(ItemHandler::getItemMarkerObject).collect(toList());
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

}
