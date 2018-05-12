package com.jazzjack.rab.bit;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Align;

class GameDrawer {

    private final Batch batch;
    private final float mapRegionYOffset;

    GameDrawer(Batch batch, float mapRegionYOffset) {
        this.batch = batch;
        this.mapRegionYOffset = mapRegionYOffset;
    }

    void drawInMapRegion(Texture texture, float x, float y, float width, float height) {
        batch.draw(texture, x, y + mapRegionYOffset, width, height);
    }

    void drawInMapRegion(TextureAtlas.AtlasRegion lightAtlasRegion, float x, float y, float width, float height) {
        batch.draw(lightAtlasRegion, x, y + mapRegionYOffset, width, height);
    }

    void drawTextInMapRegion(BitmapFont font, String text, float x, float y, float targetWidth, int hAlign) {
        font.draw(batch, text, x, y + mapRegionYOffset, targetWidth, Align.center, false);
    }

    void drawWithAlpha(float alpha, Runnable runnable) {
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alpha);
        runnable.run();
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
    }

    void drawWithBlendFunction(int srcFunc, int destFunc, Runnable runnable) {
        batch.setBlendFunction(srcFunc, destFunc);
        batch.begin();
        runnable.run();
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    void begin() {
        batch.begin();
    }

    void end() {
        batch.end();
    }
}
