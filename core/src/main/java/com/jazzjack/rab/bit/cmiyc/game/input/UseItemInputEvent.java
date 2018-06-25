package com.jazzjack.rab.bit.cmiyc.game.input;

import com.jazzjack.rab.bit.cmiyc.event.Event;
import com.jazzjack.rab.bit.cmiyc.item.Item;

public class UseItemInputEvent implements Event {

    private final Item item;

    public UseItemInputEvent(Item item) {
        this.item = item;

    }

    public Item getItem() {
        return item;
    }
}
