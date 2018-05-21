package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jazzjack.rab.bit.cmiyc.level.Level;

public class StatusBarRenderer implements Renderer {

    private static final float SCALE_TO_LEVEL = 2f;
    private final Level level;
    private final GameAssetManager assetManager;
    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final Camera camera;

    StatusBarRenderer(Level level, GameAssetManager assetManager) {
        this.level = level;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = createCamera();

    }

    private OrthographicCamera createCamera() {
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, level.getWidth() * SCALE_TO_LEVEL, level.getHeight() * SCALE_TO_LEVEL);
        orthographicCamera.update();
        return orthographicCamera;
    }

    @Override
    public void resize(int width, int height) {
        updateCamera();
    }

    @Override
    public void render() {
        updateCamera();
        clearStatusBar();
        renderStatusBar();
    }

    private void updateCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    public void clearStatusBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0f, 0f, level.getWidth() * SCALE_TO_LEVEL, 1f);
        shapeRenderer.end();
    }

    private void renderStatusBar() {
        batch.begin();
        TextureAtlas.AtlasRegion hpFilledTexture = assetManager.getHpFilledTexture();
        for (float i = 0; i < level.getPlayer().getHp(); i++) {
            batch.draw(hpFilledTexture, i,0f,1f,1f);
        }
        TextureAtlas.AtlasRegion hpEmptyTexture = assetManager.getHpEmptyTexture();
        for (float j = level.getPlayer().getHp(); j < level.getPlayer().getMaxHp(); j++) {
            batch.draw(hpEmptyTexture, j,0f,1f,1f);
        }
        batch.end();
    }
}
