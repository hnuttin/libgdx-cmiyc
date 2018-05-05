package com.jazzjack.rab.bit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.Enemy;
import com.jazzjack.rab.bit.actor.Player;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    private final TacticalMap tacticalMap;
    private final GameAssetManager assetManager;
    private final Player player;
    private final Enemy enemy;

    private final FrameBuffer lightBuffer;

    private boolean rebufferPlayer = true;

    public GameRenderer(TacticalMap tacticalMap, GameAssetManager assetManager, Player player, Enemy enemy) {
        super(tacticalMap.getTiledMap());

        this.tacticalMap = tacticalMap;
        this.assetManager = assetManager;
        this.player = player;
        this.enemy = enemy;

        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void rebufferPlayer() {
        rebufferPlayer = true;
    }

    @Override
    public void render() {
        bufferSight();
        renderMap();
        renderSight();
    }

    private void bufferSight() {
        if (rebufferPlayer) {
            rebufferPlayer = false;

            lightBuffer.begin();
            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            drawWithBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE, this::drawSight);
            lightBuffer.end();
        }
    }

    private void drawSight() {
        batch.draw(
                assetManager.getLightAtlasRegion(),
                player.getX() - (player.getSight() / 2) + (tacticalMap.getTileWidth() / 2),
                player.getY() - (player.getSight() / 2) + (tacticalMap.getTileHeight() / 2),
                player.getSight(),
                player.getSight());
    }

    private void renderMap() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderMapLayer(tacticalMap.getMapLayer());
        drawActor(player);
        drawActor(enemy);
        drawWithAlpha(0.5f, this::drawEnemyRoute);
        batch.end();
    }

    private void drawEnemyRoute() {
        batch.draw(
                new Texture("pixel-art/enemy/route-vertical.png"),
                enemy.getX(),
                enemy.getY() - tacticalMap.getTileHeight(),
                tacticalMap.getTileWidth(),
                tacticalMap.getTileHeight());
        batch.draw(
                new Texture("pixel-art/enemy/route-corner-upper-right.png"),
                enemy.getX(),
                enemy.getY() - (tacticalMap.getTileHeight() * 2),
                tacticalMap.getTileWidth(),
                tacticalMap.getTileHeight());
        batch.draw(
                new Texture("pixel-art/enemy/route-horizontal.png"),
                enemy.getX() + tacticalMap.getTileWidth(),
                enemy.getY() - (tacticalMap.getTileHeight() * 2),
                tacticalMap.getTileWidth(),
                tacticalMap.getTileHeight());
        batch.draw(
                new Texture("pixel-art/enemy/route-stop-right.png"),
                enemy.getX() + (tacticalMap.getTileWidth() * 2),
                enemy.getY() - (tacticalMap.getTileHeight() * 2),
                tacticalMap.getTileWidth(),
                tacticalMap.getTileHeight());
    }

    private void drawWithAlpha(float alpha, Runnable runnable) {
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alpha);
        runnable.run();
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
    }

    private void drawActor(Actor actor) {
        batch.draw(
                assetManager.getTextureForActor(actor),
                actor.getX(),
                actor.getY(),
                tacticalMap.getTileWidth(),
                tacticalMap.getTileHeight());
    }

    private void renderSight() {
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt());
        drawWithBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR, () -> batch.draw(lightBuffer.getColorBufferTexture(), -1, 1, 2, -2));
    }

    private void drawWithBlendFunction(int srcFunc, int destFunc, Runnable runnable) {
        batch.setBlendFunction(srcFunc, destFunc);
        batch.begin();
        runnable.run();
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
