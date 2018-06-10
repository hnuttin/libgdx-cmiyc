package com.jazzjack.rab.bit.cmiyc.render.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.LevelTiledMap;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;

class FogOfWarBuffer implements Disposable, PlayerMovedSubscriber {

    private static final float FOG_OF_WAR = 0f;

    private final Level level;
    private final Batch batch;
    private final GameAssetManager assetManager;
    private final FrameBuffer lightFrameBuffer;

    private boolean rebuffer = true;

    FogOfWarBuffer(Level level, Batch batch, GameAssetManager assetManager) {
        this.level = level;
        this.batch = batch;
        this.assetManager = assetManager;
        this.lightFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.level.getLevelTiledMap().getWidth(), this.level.getLevelTiledMap().getHeight(), false);
        GameEventBus.registerSubscriber(this);
    }

    @Override
    public void playerMoved(PlayerMovedEvent playerMovedEvent) {
        rebuffer = true;
    }

    void bufferSight() {
        if (rebuffer) {
            rebuffer = false;
            doBufferSight();
        }
    }

    private void doBufferSight() {
        lightFrameBuffer.begin();
        Gdx.gl.glClearColor(FOG_OF_WAR, FOG_OF_WAR, FOG_OF_WAR, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.begin();
        drawCellsVisited(level.getLevelTiledMap());
        Player player = level.getPlayer();
        batch.draw(
                assetManager.getSightTexture(),
                1f * player.getX() - player.getSight(),
                1f * player.getY() - player.getSight(),
                (player.getSight() * 2f) + 1f,
                (player.getSight() * 2f) + 1f);
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        lightFrameBuffer.end();
    }

    private void drawCellsVisited(LevelTiledMap levelTiledMap) {
        for (int x = 0; x < levelTiledMap.getWidth(); x++) {
            for (int y = 0; y < levelTiledMap.getHeight(); y++) {
                if (level.getLevelSight().isTileVisited(x, y) && !level.getLevelSight().isTileInSight(x, y)) {
                    drawCellVisited(x, y);
                }
            }
        }
    }

    private void drawCellVisited(float x, float y) {
        batch.draw(assetManager.getTileVisitedTexture(), x, y, 1f, 1f);
    }

    private void drawCellInSight(float x, float y) {
        batch.draw(assetManager.getSightTexture(), x, y, 1f, 1f);
    }

    void renderSight() {
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt());
        batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
        batch.begin();
        batch.draw(lightFrameBuffer.getColorBufferTexture(), -1, 1, 2, -2);
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose() {
        lightFrameBuffer.dispose();
    }
}
