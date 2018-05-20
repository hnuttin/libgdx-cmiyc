package com.jazzjack.rab.bit.render.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.actor.enemy.route.StepNames;
import com.jazzjack.rab.bit.render.GameAssetManager;

class TextDrawer {

    private final GameAssetManager assetManager;
    private final Batch batch;
    private final LevelCamera levelCamera;

    TextDrawer(GameAssetManager assetManager, Batch batch, LevelCamera levelCamera) {
        this.assetManager = assetManager;
        this.batch = batch;
        this.levelCamera = levelCamera;
    }

    public void drawText(String text, float tileX, float tileY, Position position) {
        BitmapFont percentageFont = assetManager.getPercentageFont();
        float percentageX = calculateFontX(tileX);
        float percentageY = calculateFontY(tileY, percentageFont, position);
        percentageFont.setColor(percentageFont.getColor().r, percentageFont.getColor().g, percentageFont.getColor().b, 1f);
        percentageFont.setUseIntegerPositions(false);
        percentageFont.getData().setScale(1f / levelCamera.getLevel().getTilePixelSize() / levelCamera.getCameraScale() / 3);
        batch.setShader(assetManager.getFontShaderProgram());
        percentageFont.draw(batch, text, percentageX, percentageY, (float) 1, Align.center, false);
        batch.setShader(null);
    }

    private float calculateFontY(float tileY, BitmapFont percentageFont, Position position) {
        return position == Position.BOTTOM ? underneathStep(tileY, percentageFont) : aboveStep(tileY);
    }

    private float calculateFontX(float tileX) {
        return tileX + 1f / levelCamera.getLevel().getTilePixelSize() / levelCamera.getCameraScale();
    }

    private float underneathStep(float tileY, BitmapFont percentageFont) {
        return tileY + percentageFont.getData().lineHeight;
    }

    private float aboveStep(float tileY) {
        return tileY  + 1f - (5f / levelCamera.getLevel().getTilePixelSize() / levelCamera.getCameraScale());
    }

    enum Position {
        TOP, BOTTOM
    }

}
