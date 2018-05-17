package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jazzjack.rab.bit.Map;

class MapRenderer extends OrthogonalTiledMapRenderer {

    private final LightBufferProvider lightBufferProvider;

    MapRenderer(Batch batch) {
        super(null, 2, batch);

        lightBufferProvider = new LightBufferProvider(getViewBounds());
    }
}
