package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.MapObject;
import com.jazzjack.rab.bit.cmiyc.item.Item;

import java.util.Map;

public class ItemMarkerObject extends MarkerObject {

    ItemMarkerObject(MapObject mapObject, Map<String, String> objectTypeDefaults, float tilePixelSize) {
        super(mapObject, objectTypeDefaults, tilePixelSize);
    }

    public String getName() {
        return getType();
    }

    public Item getItem() {
        return Item.forName(getType());
    }
}
