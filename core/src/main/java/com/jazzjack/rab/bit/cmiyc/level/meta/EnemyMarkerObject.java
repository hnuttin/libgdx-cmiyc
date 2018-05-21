package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.MapObject;
import com.jazzjack.rab.bit.cmiyc.common.Predictability;

import java.util.Map;

public class EnemyMarkerObject extends MarkerObject {

    private static final String PROPERTY_PREDICTABILITY = "predictability";

    EnemyMarkerObject(MapObject mapObject, Map<String, String> objectTypeDefaults, float tilePixelSize) {
        super(mapObject, objectTypeDefaults, tilePixelSize);
    }

    public Predictability getPredictability() {
        String predictability = super.getStringProperty(PROPERTY_PREDICTABILITY);
        return Predictability.valueOf(predictability);
    }

}
