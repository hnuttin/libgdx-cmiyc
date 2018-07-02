package com.jazzjack.rab.bit.cmiyc.render.hud.action;

import com.jazzjack.rab.bit.cmiyc.game.input.UseItemInputEvent;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

class ItemHUDAction extends HUDActionBase {

    private final Item item;

    ItemHUDAction(HasPosition hasPosition, Item item) {
        super(hasPosition);
        this.item = item;
    }

    @Override
    public void execute() {
        publishEvent(new UseItemInputEvent(item));
    }
}
