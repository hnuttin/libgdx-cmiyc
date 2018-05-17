package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;

class LightBufferProvider {

    private final Rectangle viewBounds;

    private FrameBuffer lightBuffer;

    LightBufferProvider(Rectangle viewBounds) {
        this.viewBounds = viewBounds;
    }

    FrameBuffer getLightBuffer() {
        if (lightBuffer == null) {
            lightBuffer = createLightBuffer();
        }
        return lightBuffer;
    }

    private boolean viewBoundsChanged() {
        return viewBounds.getWidth() != lightBuffer.getWidth() || viewBounds.getHeight() != lightBuffer.getHeight();
    }

    private FrameBuffer createLightBuffer() {
        return new FrameBuffer(Pixmap.Format.RGBA8888, 40, 20, false);
    }
}
