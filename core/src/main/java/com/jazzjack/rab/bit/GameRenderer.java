package com.jazzjack.rab.bit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.Player;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.game.GameObjectProvider;
import com.jazzjack.rab.bit.route.Route;
import com.jazzjack.rab.bit.route.Step;

import java.util.Optional;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    private static final float FOG_OF_WAR = 1f;

    private final GameObjectProvider gameObjectProvider;
    private final GameAssetManager assetManager;

    private final FrameBuffer lightBuffer;

    private boolean rebufferPlayer = true;

    GameRenderer(GameObjectProvider gameObjectProvider, GameAssetManager assetManager) {
        super(null);
        super.unitScale = 2f;

        this.gameObjectProvider = gameObjectProvider;
        this.assetManager = assetManager;

        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override
    public void render() {
        bufferSight();
        renderMap();
        renderSight();
    }

    private void bufferSight() {
        Optional<Player> player = gameObjectProvider.getPlayer();
        Optional<Level> level = gameObjectProvider.getLevel();
        if (rebufferPlayer && player.isPresent() && level.isPresent()) {
            rebufferPlayer = true;

            lightBuffer.begin();
            Gdx.gl.glClearColor(FOG_OF_WAR, FOG_OF_WAR, FOG_OF_WAR, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            drawWithBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE, () -> drawSight(player.get()));
            lightBuffer.end();
        }
    }

    private void drawSight(Player player) {
        batch.draw(
                assetManager.getLightAtlasRegion(),
                player.getX() - (player.getSight() * getTileWidth()),
                player.getY() - (player.getSight() * getTileHeight()),
                player.getSight() * 2 + 1,
                player.getSight() * 2 + 1);
    }

    private void renderMap() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderMapLayer();
        drawPlayer();
        drawEnemies();
        batch.end();
    }

    private void renderMapLayer() {
        gameObjectProvider.getLevel().ifPresent(level1 -> renderMapLayer(level1.getMapLayer()));
    }

    private void drawPlayer() {
        gameObjectProvider.getPlayer().ifPresent(this::drawActor);
    }

    private void drawEnemies() {
        gameObjectProvider.getEnemies().forEach(this::drawEnemy);
    }

    private void drawEnemy(Enemy enemy) {
        drawActor(enemy);
        drawWithAlpha(0.5f, () -> drawEnemyRoutes(enemy));
    }

    private void drawEnemyRoutes(Enemy enemy) {
        enemy.getRoutes().forEach(this::drawEnemyRoute);
    }

    private void drawEnemyRoute(Route route) {
        BitmapFont percentageFont = assetManager.getPercentageFont();
        Step lastStep = route.getSteps().get(route.getSteps().size() - 1);
        float percentageX = lastStep.getX() * getTileWidth();
        float percentageY = lastStep.getY() * getTileHeight() + (22 * super.unitScale) + percentageFont.getData().lineHeight;
        percentageFont .draw(batch, String.valueOf(route.getPercentage()), percentageX, percentageY, getTileWidth(), Align.center, false);
        route.getSteps().forEach(this::drawActor);
    }

    private void drawWithAlpha(float alpha, Runnable runnable) {
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alpha);
        runnable.run();
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
    }

    private void drawActor(Actor actor) {
        batch.draw(assetManager.getTextureForActor(actor), actor.getX() * getTileWidth(), actor.getY() * getTileHeight(), getTileWidth(), getTileHeight());
    }

    private float getTileWidth() {
        if (gameObjectProvider.getLevel().isPresent()) {
            return gameObjectProvider.getLevel().get().getTileWidth() * super.unitScale;
        } else {
            return 0f;
        }
    }

    private float getTileHeight() {
        if (gameObjectProvider.getLevel().isPresent()) {
            return gameObjectProvider.getLevel().get().getTileHeight() * super.unitScale;
        } else {
            return 0f;
        }
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

}
