package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;

class ShieldItemHandler extends ItemHandler {

    ShieldItemHandler(ItemMarkerObject itemMarkerObject) {
        super(itemMarkerObject);
    }

    @Override
    void handle(Player player) {
        player.pickupItem(getItemMarkerObject().getItem());
    }
}
