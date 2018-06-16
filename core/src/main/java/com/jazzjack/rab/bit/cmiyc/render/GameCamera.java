package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class GameCamera extends OrthographicCamera {

    public float getViewportWidth() {
        return super.viewportWidth;
    }

    public void setViewportWidth(float viewportWidth) {
        super.viewportWidth = viewportWidth;
    }

    public float getViewportHeight() {
        return viewportHeight;
    }

    public void setViewportHeight(float viewportHeight) {
        super.viewportHeight = viewportHeight;
    }

    public void setPosition(float x, float y) {
        super.position.set(x, y, 0f);
    }

    public Matrix4 getCombinedProjectionMatrix() {
        return super.combined;
    }
}
