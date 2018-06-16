package com.jazzjack.rab.bit.cmiyc.render.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.shared.position.Alignment;

class LevelTextDrawer {

    private static final Color COLOR_LEVEL_TEXT = new Color(0xffffffff);

    private final GameAssetManager assetManager;
    private final Batch batch;
    private final LevelCamera levelCamera;

    LevelTextDrawer(GameAssetManager assetManager, Batch batch, LevelCamera levelCamera) {
        this.assetManager = assetManager;
        this.batch = batch;
        this.levelCamera = levelCamera;
    }

    void drawTextOnTile(String text, float tileX, float tileY, Alignment alignment, float tilePixelSize) {
        BitmapFont percentageFont = assetManager.getPercentageFont();
        float x = calculateFontX(tileX, tilePixelSize);
        float y = calculateFontY(tileY, percentageFont, alignment, tilePixelSize);
        percentageFont.setColor(COLOR_LEVEL_TEXT);
        percentageFont.setUseIntegerPositions(false);
        percentageFont.getData().setScale(1f / tilePixelSize / levelCamera.getCameraScale() / 3);
        batch.setShader(assetManager.getFontShaderProgram());
        percentageFont.draw(batch, text, x, y, 1f, Align.center, false);
        batch.setShader(null);
    }

    private float calculateFontY(float tileY, BitmapFont percentageFont, Alignment alignment, float tilePixelSize) {
        return alignment == Alignment.BOTTOM ? underneathStep(tileY, percentageFont) : aboveStep(tileY, tilePixelSize);
    }

    private float calculateFontX(float tileX, float tilePixelSize) {
        return tileX + 1f / tilePixelSize / levelCamera.getCameraScale();
    }

    private float underneathStep(float tileY, BitmapFont percentageFont) {
        return tileY + percentageFont.getData().lineHeight;
    }

    private float aboveStep(float tileY, float tilePixelSize) {
        return tileY  + 1f - (5f / tilePixelSize / levelCamera.getCameraScale());
    }

}
