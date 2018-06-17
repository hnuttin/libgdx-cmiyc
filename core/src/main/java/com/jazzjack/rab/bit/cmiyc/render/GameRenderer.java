package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.NewLevelEvent;
import com.jazzjack.rab.bit.cmiyc.level.NewLevelSubscriber;
import com.jazzjack.rab.bit.cmiyc.render.hud.HUDRenderer;
import com.jazzjack.rab.bit.cmiyc.render.level.LevelRenderer;

import java.util.Optional;

public class GameRenderer implements Renderer, NewLevelSubscriber, GameWorldCameraProvider {

    private static final int NUMBER_OF_HORIZONTAL_TILES_TO_RENDER = 20;
    private static final int NUMBER_OF_VERTICAL_TILES_TO_RENDER = 10;
    private final GameAssetManager assetManager;

    private LevelRenderer levelRenderer;
    private HUDRenderer hudRenderer;

    public GameRenderer(GameAssetManager assetManager) {
        this.assetManager = assetManager;
        GameEventBus.registerSubscriber(this);
    }

    @Override
    public Optional<Camera> getGameWorldCamera() {
        return levelRenderer != null ? Optional.of(levelRenderer.getCamera()) : Optional.empty();
    }

    @Override
    public void newLevel(NewLevelEvent newLevelEvent) {
        Level level = newLevelEvent.getLevel();
        levelRenderer = new LevelRenderer(level, assetManager, NUMBER_OF_HORIZONTAL_TILES_TO_RENDER);
        hudRenderer = new HUDRenderer(level, assetManager, NUMBER_OF_HORIZONTAL_TILES_TO_RENDER);
        Gdx.graphics.setWindowedMode(
                NUMBER_OF_HORIZONTAL_TILES_TO_RENDER * (int) level.getLevelTiledMap().getTilePixelSize() * 2,
                NUMBER_OF_VERTICAL_TILES_TO_RENDER * (int) level.getLevelTiledMap().getTilePixelSize() * 2);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (levelRenderer != null) {
            levelRenderer.render();
        }
        if (hudRenderer != null) {
            hudRenderer.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        if (levelRenderer != null) {
            levelRenderer.resize(width, height);
        }
        if (hudRenderer != null) {
            hudRenderer.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        if (levelRenderer != null) {
            levelRenderer.dispose();
        }
        if (hudRenderer != null) {
            hudRenderer.dispose();
        }
    }

}
