package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

abstract class ItemHandler implements HasPosition {

    private final ItemMarkerObject itemMarkerObject;

    ItemHandler(ItemMarkerObject itemMarkerObject) {
        this.itemMarkerObject = itemMarkerObject;
    }

    ItemMarkerObject getItemMarkerObject() {
        return itemMarkerObject;
    }

    abstract void handle(Player player);

    @Override
    public int getX() {
        return itemMarkerObject.getX();
    }

    @Override
    public int getY() {
        return itemMarkerObject.getY();
    }
}
