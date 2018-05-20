package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.game.GameObjectProvider;
import com.jazzjack.rab.bit.render.level.LevelRenderer;

import java.util.Optional;

public class GameRenderer implements Renderer {

    private static final float FOG_OF_WAR = 0f;

    private final GameObjectProvider gameObjectProvider;
    private final GameAssetManager assetManager;

    private LevelRenderer levelRenderer;
    private StatusBarRenderer statusBarRenderer;

//    private final LightBufferProvider lightBufferProvider;
//
//    private boolean rebufferPlayer = true;

    public GameRenderer(GameObjectProvider gameObjectProvider, GameAssetManager assetManager) {
        this.gameObjectProvider = gameObjectProvider;
        this.assetManager = assetManager;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Optional<Level> levelOptional = gameObjectProvider.getMap();
        if (levelOptional.isPresent()) {
            Level level = levelOptional.get();
            if (levelRenderer == null) {
                levelRenderer = new LevelRenderer(level, assetManager);
                statusBarRenderer = new StatusBarRenderer(level.getPlayer(), assetManager);
                Gdx.graphics.setWindowedMode(level.getWidth() * (int) level.getTilePixelSize() * 2, level.getHeight() * (int) level.getTilePixelSize() * 2);
            }
            levelRenderer.render();
            statusBarRenderer.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        if (levelRenderer != null) {
            levelRenderer.resize(width, height);
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

//    private void bufferSight() {
//        Optional<Player> player = gameObjectProvider.getPlayer();
//        Optional<Level> level = gameObjectProvider.getMap();
//        if (rebufferPlayer && player.isPresent() && level.isPresent()) {
//            rebufferPlayer = true;
//
//            lightBufferProvider.getLightBuffer().begin();
//            Gdx.gl.glClearColor(FOG_OF_WAR, FOG_OF_WAR, FOG_OF_WAR, 1f);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//            gameDrawer.drawWithBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE, () -> drawSight(player.get()));
//            lightBufferProvider.getLightBuffer().end();
//        }
//    }
//
//    private void drawSight(Player player) {
//        gameDrawer.drawWithFlippedY(
//                assetManager.getLightAtlasRegion(),
//                player.getX() - player.getSight(),
//                player.getY() - player.getSight(),
//                (player.getSight() * 2) + 1,
//                (player.getSight() * 2) + 1);
//    }
//
//    private void renderSight() {
//        gameDrawer.drawWithBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR, () -> batch.draw(lightBufferProvider.getLightBuffer().getColorBufferTexture(), 0, STATUS_BAR_HEIGHT));
//    }
//
//void drawWithBlendFunction(int srcFunc, int destFunc, Runnable runnable) {
//    batch.setBlendFunction(srcFunc, destFunc);
//    batch.begin();
//    runnable.run();
//    batch.end();
//    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//}
//
//    void drawWithFlippedY(TextureAtlas.AtlasRegion atlasRegion, float x, float y, float width, float height) {
//        batch.draw(
//                atlasRegion,
//                x,
//                Gdx.graphics.getHeight() - y - height,
//                width,
//                height);
//    }
}
