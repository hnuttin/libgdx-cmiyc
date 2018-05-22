package com.jazzjack.rab.bit.cmiyc.render.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;

class TextDrawer {

    private final GameAssetManager assetManager;
    private final Batch batch;
    private final LevelCamera levelCamera;

    TextDrawer(GameAssetManager assetManager, Batch batch, LevelCamera levelCamera) {
        this.assetManager = assetManager;
        this.batch = batch;
        this.levelCamera = levelCamera;
    }

    void drawText(String text, float tileX, float tileY, Position positionOnTile, float tilePixelSize) {
        BitmapFont percentageFont = assetManager.getPercentageFont();
        float percentageX = calculateFontX(tileX, tilePixelSize);
        float percentageY = calculateFontY(tileY, percentageFont, positionOnTile, tilePixelSize);
        percentageFont.setColor(percentageFont.getColor().r, percentageFont.getColor().g, percentageFont.getColor().b, 1f);
        percentageFont.setUseIntegerPositions(false);
        percentageFont.getData().setScale(1f / tilePixelSize / levelCamera.getCameraScale() / 3);
        batch.setShader(assetManager.getFontShaderProgram());
        percentageFont.draw(batch, text, percentageX, percentageY, (float) 1, Align.center, false);
        batch.setShader(null);
    }

    private float calculateFontY(float tileY, BitmapFont percentageFont, Position position, float tilePixelSize) {
        return position == Position.BOTTOM ? underneathStep(tileY, percentageFont) : aboveStep(tileY, tilePixelSize);
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

    enum Position {
        TOP, BOTTOM
    }

}
