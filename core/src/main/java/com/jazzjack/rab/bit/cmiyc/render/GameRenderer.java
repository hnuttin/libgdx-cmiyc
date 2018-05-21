package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jazzjack.rab.bit.cmiyc.game.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.NewLevelSubscriber;
import com.jazzjack.rab.bit.cmiyc.render.level.LevelRenderer;

public class GameRenderer implements Renderer, NewLevelSubscriber {

    private final GameAssetManager assetManager;

    private LevelRenderer levelRenderer;
    private StatusBarRenderer statusBarRenderer;

    public GameRenderer(GameAssetManager assetManager) {
        this.assetManager = assetManager;
        GameEventBus.registerSubscriber(this);
    }

    @Override
    public void onNewLevel(Level newLevel) {
        levelRenderer = new LevelRenderer(newLevel, assetManager);
        statusBarRenderer = new StatusBarRenderer(newLevel, assetManager);
        Gdx.graphics.setWindowedMode(
                newLevel.getWidth() * (int) newLevel.getTilePixelSize() * 2,
                newLevel.getHeight() * (int) newLevel.getTilePixelSize() * 2);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (levelRenderer != null) {
            levelRenderer.render();
        }
        if (statusBarRenderer != null) {
            statusBarRenderer.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        if (levelRenderer != null) {
            levelRenderer.resize(width, height);
        }
        if (statusBarRenderer != null) {
            statusBarRenderer.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        if (levelRenderer != null) {
            levelRenderer.dispose();
        }
        if (statusBarRenderer != null) {
            statusBarRenderer.dispose();
        }
    }

}
