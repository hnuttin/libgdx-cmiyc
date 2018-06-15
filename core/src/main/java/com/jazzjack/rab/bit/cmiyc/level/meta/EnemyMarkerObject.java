package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.MapObject;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;

import java.util.Map;

public class EnemyMarkerObject extends MarkerObject {

    private static final String PROPERTY_PREDICTABILITY = "predictability";
    private static final String PROPERTY_SENSE = "sense";

    EnemyMarkerObject(MapObject mapObject, Map<String, String> objectTypeDefaults, float tilePixelSize) {
        super(mapObject, objectTypeDefaults, tilePixelSize);
    }

    public String getName() {
        return getType();
    }

    public Predictability getPredictability() {
        String predictability = super.getStringProperty(PROPERTY_PREDICTABILITY);
        return Predictability.valueOf(predictability);
    }

    public Sense getSense() {
        String sense = super.getStringProperty(PROPERTY_SENSE);
        return Sense.valueOf(sense);
    }

}
