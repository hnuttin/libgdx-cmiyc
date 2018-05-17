package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;

class GameDrawer {

    private final Batch batch;
    private final float mapRegionYOffset;
    private final ShapeRenderer shapeRenderer;

    GameDrawer(Batch batch, float mapRegionYOffset) {
        this.batch = batch;
        this.mapRegionYOffset = mapRegionYOffset;
        this.shapeRenderer = new ShapeRenderer();
    }

    void drawInMapRegion(Texture texture, float x, float y, float width, float height) {
        batch.draw(texture, x, y + mapRegionYOffset, width, height);
    }

    void drawInStatusBarRegion(Texture texture, float x, float y) {
        batch.draw(texture, x, y, 1, 1);
    }

    void drawInMapRegion(TextureAtlas.AtlasRegion lightAtlasRegion, float x, float y, float width, float height) {
        batch.draw(lightAtlasRegion, x, y + mapRegionYOffset, width, height);
    }

    void drawCenteredTextInMapRegion(BitmapFont font, String text, float x, float y, float targetWidth, float alpha) {
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
        font.setUseIntegerPositions(false);
        font.getData().setScale(20f / Gdx.graphics.getHeight());
        font.draw(batch, text, x, y + mapRegionYOffset, targetWidth, Align.center, false);
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, 1f);
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

    void drawWithFlippedY(TextureAtlas.AtlasRegion atlasRegion, float x, float y, float width, float height) {
        batch.draw(
                atlasRegion,
                x,
                Gdx.graphics.getHeight() - y - height,
                width,
                height);
    }

    void begin() {
        batch.begin();
    }

    void end() {
        batch.end();
    }

    public void clearStatusBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0,0, 40, 1);
        shapeRenderer.end();
    }

    public void setProjectionMatrix(Matrix4 projectionMatrix) {
        batch.setProjectionMatrix(projectionMatrix);
        shapeRenderer.setProjectionMatrix(projectionMatrix);
    }
}
