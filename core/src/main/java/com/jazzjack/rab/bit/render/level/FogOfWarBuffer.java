package com.jazzjack.rab.bit.render.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.game.GameEventBus;
import com.jazzjack.rab.bit.level.Level;
import com.jazzjack.rab.bit.render.GameAssetManager;

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
        this.lightFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.level.getWidth(), this.level.getHeight(), false);
        GameEventBus.registerSubscriber(this);
    }

    @Override
    public void playerMoved() {
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
        Player player = level.getPlayer();
        batch.draw(
                assetManager.getLightAtlasRegion(),
                player.getX() - player.getSight(),
                player.getY() - player.getSight(),
                (player.getSight() * 2) + 1,
                (player.getSight() * 2) + 1);
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        lightFrameBuffer.end();
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
