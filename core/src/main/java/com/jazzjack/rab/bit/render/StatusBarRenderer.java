package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jazzjack.rab.bit.actor.player.Player;

public class StatusBarRenderer implements Renderer {

    private final Player player;
    private final GameAssetManager assetManager;
    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final Camera camera;

    StatusBarRenderer(Player player, GameAssetManager assetManager) {
        this.player = player;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = createCamera();

    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 40f, 20f);
        camera.update();
        return camera;
    }

    @Override
    public void resize(int width, int height) {
       // resizeCamera(width, height);
        updateCamera();
    }

    private void resizeCamera(int width, int height) {
        camera.viewportWidth = 20f;
        camera.viewportHeight = 0.5f;
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
        shapeRenderer.rect(0f, 0f, 40f, 1f);
        shapeRenderer.end();
    }

    private void renderStatusBar() {
        batch.begin();
        TextureAtlas.AtlasRegion hpFilledTexture = assetManager.getHpFilledTexture();
        for (float i = 0; i < player.getHp(); i++) {
            batch.draw(hpFilledTexture, i,0f,1f,1f);
        }
        TextureAtlas.AtlasRegion hpEmptyTexture = assetManager.getHpEmptyTexture();
        for (float j = player.getHp(); j < player.getMaxHp(); j++) {
            batch.draw(hpEmptyTexture, j,0f,1f,1f);
        }
        batch.end();
    }
}
