package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

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

    public HasPosition getMouseGamePosition() {
        Vector3 gameVector = unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f));
        return new Position((int) gameVector.x, (int) gameVector.y);
    }
}
