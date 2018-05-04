package com.jazzjack.rab.bit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    private final FrameBuffer lightBuffer;
    private final TextureAtlas.AtlasRegion lightTexture;
    private final Player player;

    private boolean rebufferPlayer = true;

    public GameRenderer(TacticalMap map, Player player) {
        super(map.getTiledMap());

        this.player = player;

        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        final TextureAtlas lightsAtlas = new TextureAtlas("lights.atlas");
        lightTexture = lightsAtlas.findRegion("light");
    }

    public void rebufferPlayer() {
        rebufferPlayer = true;
    }

    @Override
    public void render() {
        bufferPlayer();
        renderMap();
        renderPlayer();
    }

    private void bufferPlayer() {
        if (rebufferPlayer) {
            rebufferPlayer = false;

            lightBuffer.begin();

            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.draw(lightTexture, player.getX() - (player.getSight() / 2) + (player.getWidth() / 2), player.getY() - (player.getSight() / 2) + (player.getHeight() / 2), player.getSight(), player.getSight());
            batch.end();

            lightBuffer.end();
        }
    }

    private void renderMap() {
        beginRender();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        for (MapLayer layer : map.getLayers()) {
            renderMapLayer(layer);
        }
        batch.draw(player.getPlayerTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        endRender();
    }

    private void renderPlayer() {
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt());
        batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
        batch.begin();
        batch.draw(lightBuffer.getColorBufferTexture(), -1, 1, 2, -2);
        batch.end();
    }
}
