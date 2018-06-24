package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.MapObject;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyConfig;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;

import java.util.Map;

public class EnemyMarkerObject extends MarkerObject {

    private static final String PROPERTY_PREDICTABILITY = "predictability";
    private static final String PROPERTY_SENSE = "sense";
    private static final String NUMBER_OF_ROUTES_TO_GENERATE = "numberOfRoutesToGenerate";
    private static final String MAX_ROUTE_LENGTH = "maxRouteLength";

    EnemyMarkerObject(MapObject mapObject, Map<String, String> objectTypeDefaults, float tilePixelSize) {
        super(mapObject, objectTypeDefaults, tilePixelSize);
    }

    public EnemyConfig getEnemyConfig() {
        return EnemyConfig.enemyConfig(getName())
                .withPredictability(getPredictability())
                .withNumberOfRoutesToGenerate(getNumberOfRoutesToGenerate())
                .withMaxRouteLength(getMaxRouteLength())
                .withSense(getSense())
                .build();
    }

    private String getName() {
        return getType();
    }

    private Predictability getPredictability() {
        String predictability = getStringProperty(PROPERTY_PREDICTABILITY);
        return Predictability.valueOf(predictability);
    }

    private int getNumberOfRoutesToGenerate() {
        return getIntProperty(NUMBER_OF_ROUTES_TO_GENERATE);
    }

    private int getMaxRouteLength() {
        return getIntProperty(MAX_ROUTE_LENGTH);
    }

    private Sense getSense() {
        String sense = getStringProperty(PROPERTY_SENSE);
        return Sense.valueOf(sense);
    }
}
