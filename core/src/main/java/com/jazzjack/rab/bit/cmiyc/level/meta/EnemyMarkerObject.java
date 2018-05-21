package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.MapObject;
import com.jazzjack.rab.bit.cmiyc.common.Predictability;

import java.util.Map;

public class EnemyMarkerObject extends MarkerObject {

    EnemyMarkerObject(MapObject mapObject, Map<String, String> objectTypeDefaults, float tilePixelSize) {
        super(mapObject, objectTypeDefaults, tilePixelSize);
    }

    public Predictability getPredictability() {
        String predictability = super.getStringProperty("predictability");
        return Predictability.valueOf(predictability);
    }
}
