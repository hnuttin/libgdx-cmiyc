package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jazzjack.rab.bit.actor.player.Player;

public class StatusBarRenderer implements Renderer {

    private final Player player;
    private final GameAssetManager assetManager;
    private final Batch batch;
    private final ShapeRenderer shapeRenderer;

    StatusBarRenderer(Player player, GameAssetManager assetManager, Batch batch) {
        this.player = player;
        this.assetManager = assetManager;
        this.batch = batch;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        clearStatusBar();
        renderStatusBar();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public void updateCamera(Camera camera) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void clearStatusBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, 20, 0.5f);
        shapeRenderer.end();
    }

    private void renderStatusBar() {
        batch.begin();
        Texture hpFilledTexture = assetManager.getHpFilledTexture();
        for (int i = 0; i < player.getHp(); i++) {
            batch.draw(
                    hpFilledTexture,
                    i,
                    0, 0.5f, 0.5f);
        }
        Texture hpEmptyTexture = assetManager.getHpEmptyTexture();
        for (int j = player.getHp(); j < player.getMaxHp(); j++) {
            batch.draw(
                    hpEmptyTexture,
                    j,
                    0, 0.5f, 0.5f);
        }
        batch.end();
    }
}
