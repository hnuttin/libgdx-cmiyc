package com.jazzjack.rab.bit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.Enemy;
import com.jazzjack.rab.bit.actor.Player;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    private final TacticalMap tacticalMap;
    private final Player player;
    private final Enemy enemy;

    private final FrameBuffer lightBuffer;
    private final TextureAtlas.AtlasRegion lightTexture;

    private boolean rebufferPlayer = true;

    public GameRenderer(TacticalMap tacticalMap, Player player, Enemy enemy) {
        super(tacticalMap.getTiledMap());

        this.tacticalMap = tacticalMap;
        this.player = player;
        this.enemy = enemy;

        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        final TextureAtlas lightsAtlas = new TextureAtlas("lights.atlas");
        lightTexture = lightsAtlas.findRegion("light");
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

            batch.begin();
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.draw(
                    lightTexture,
                    player.getX() - (player.getSight() / 2) + (tacticalMap.getTileWidth() / 2),
                    player.getY() - (player.getSight() / 2) + (tacticalMap.getTileHeight() / 2),
                    player.getSight(),
                    player.getSight());
            batch.end();

            lightBuffer.end();
        }
    }

    private void renderMap() {
        beginRender();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderMapLayer(tacticalMap.getMapLayer());
        drawActor(player);
        drawActor(enemy);
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
        endRender();
    }

    private void drawActor(Actor actor) {
        batch.draw(
                actor.getTexture(),
                actor.getX(),
                actor.getY(),
                tacticalMap.getTileWidth(),
                tacticalMap.getTileHeight());
    }

    private void renderSight() {
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt());
        batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
        batch.begin();
        batch.draw(lightBuffer.getColorBufferTexture(), -1, 1, 2, -2);
        batch.end();
    }
}
