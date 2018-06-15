package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;

class HpItemHandler extends ItemHandler {

    HpItemHandler(ItemMarkerObject markerObject) {
        super(markerObject);
    }

    @Override
    void handle(Player player) {
        player.incrementHp();
    }
}
