package com.jazzjack.rab.bit.cmiyc.render.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;

class HUDTextDrawer {

    private final GameAssetManager assetManager;
    private final Batch batch;
    private final GameCamera hudCamera;
    private final float pixelsPerUnit;

    HUDTextDrawer(GameAssetManager assetManager, Batch batch, GameCamera hudCamera, float pixelsPerUnit) {
        this.assetManager = assetManager;
        this.batch = batch;
        this.hudCamera = hudCamera;
        this.pixelsPerUnit = pixelsPerUnit;
    }

    void drawOverlayText(String text, float y, Color color) {
        BitmapFont percentageFont = assetManager.getFont();
        percentageFont.setColor(color);
        percentageFont.setUseIntegerPositions(false);
        percentageFont.getData().setScale(1f / pixelsPerUnit);
        batch.setShader(assetManager.getFontShaderProgram());
        batch.begin();
        percentageFont.draw(batch, text, 0, y, hudCamera.getViewportWidth(), Align.center, false);
        batch.end();
        batch.setShader(null);
    }

}
