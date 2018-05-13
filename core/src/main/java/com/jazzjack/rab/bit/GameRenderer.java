package com.jazzjack.rab.bit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.Player;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.Step;
import com.jazzjack.rab.bit.actor.enemy.route.StepNames;
import com.jazzjack.rab.bit.game.GameObjectProvider;

import java.util.Optional;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    private static final float FOG_OF_WAR = 0f;
    private static final int ENDING_HEIGHT = 22;
    private static final float ROUTE_ALPHA = 0.7f;
    private static final int STATUS_BAR_HEIGHT = 32;

    private final GameObjectProvider gameObjectProvider;
    private final GameAssetManager assetManager;

    private final GameDrawer gameDrawer;

    private final FrameBuffer lightBuffer;

    private boolean rebufferPlayer = true;

    GameRenderer(GameObjectProvider gameObjectProvider, GameAssetManager assetManager, float scale) {
        super(null, scale);

        this.gameObjectProvider = gameObjectProvider;
        this.assetManager = assetManager;
        this.gameDrawer = new GameDrawer(super.batch, STATUS_BAR_HEIGHT);

        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - STATUS_BAR_HEIGHT, false);
    }

    public void setProjectionMatrix(Matrix4 projectionMatrix) {
        gameDrawer.setProjectionMatrix(projectionMatrix);
    }

    private float getScaledTileWidth() {
        return gameObjectProvider.getLevel().map(level -> level.getTileWidth() * getUnitScale()).orElse(0f);
    }

    private float getTileHeight() {
        return gameObjectProvider.getLevel().map(Level::getTileHeight).orElse(0f);
    }

    private float getScaledTileHeight() {
        return getTileHeight() * getUnitScale();
    }

    @Override
    public void render() {
        bufferSight();
        renderMap();
        renderSight();
        renderStatusBar();
    }

    private void bufferSight() {
        Optional<Player> player = gameObjectProvider.getPlayer();
        Optional<Level> level = gameObjectProvider.getLevel();
        if (rebufferPlayer && player.isPresent() && level.isPresent()) {
            rebufferPlayer = true;

            lightBuffer.begin();
            Gdx.gl.glClearColor(FOG_OF_WAR, FOG_OF_WAR, FOG_OF_WAR, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameDrawer.drawWithBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE, () -> drawSight(player.get()));
            lightBuffer.end();
        }
    }

    private void drawSight(Player player) {
        gameDrawer.drawWitthFlippedY(
                assetManager.getLightAtlasRegion(),
                (player.getX() * getScaledTileWidth()) - (player.getSight() * getScaledTileWidth()),
                (player.getY() * getScaledTileHeight()) - (player.getSight() * getScaledTileHeight()),
                (player.getSight() * 2 + 1) * getScaledTileWidth(),
                (player.getSight() * 2 + 1) * getScaledTileHeight());
    }

    private void renderSight() {
        gameDrawer.drawWithBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR, () -> batch.draw(lightBuffer.getColorBufferTexture(), 0, STATUS_BAR_HEIGHT));
    }

    private void renderMap() {
        gameDrawer.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderLevel();
        drawPlayer();
        drawEnemies();
        gameDrawer.end();
    }

    private void renderLevel() {
        gameObjectProvider.getLevel().ifPresent(this::renderLevel);
    }

    private void renderLevel(Level level) {
        level.setMapOffset(0, STATUS_BAR_HEIGHT * -1 / 2);
        super.renderMapLayer(level.getMapLayer());
    }

    private void drawPlayer() {
        gameObjectProvider.getPlayer().ifPresent(this::drawActor);
    }

    private void drawEnemies() {
        gameObjectProvider.getEnemies().forEach(this::drawEnemy);
    }

    private void drawEnemy(Enemy enemy) {
        drawActor(enemy);
        gameDrawer.drawWithAlpha(ROUTE_ALPHA, () -> drawEnemyRoutes(enemy));
    }

    private void drawEnemyRoutes(Enemy enemy) {
        enemy.getRoutes().forEach(this::drawEnemyRoute);
    }

    private void drawEnemyRoute(Route route) {
        drawPercentage(route);
        route.getSteps().forEach(this::drawActor);
    }

    private void drawPercentage(Route route) {
        BitmapFont percentageFont = assetManager.getPercentageFont();
        Step lastStep = route.getSteps().get(route.getSteps().size() - 1);
        float percentageX = lastStep.getX() * getScaledTileWidth();
        float percentageY = StepNames.ENDING_BOTTOM.equals(lastStep.getName()) ? underneathStep(percentageFont, lastStep) : aboveStep(percentageFont, lastStep);
        gameDrawer.drawCenteredTextInMapRegion(percentageFont, route.getPercentage() + "%", percentageX, percentageY, getScaledTileWidth(), Align.center);
    }

    private float underneathStep(BitmapFont percentageFont, Step lastStep) {
        return lastStep.getY() * getScaledTileHeight() + percentageFont.getData().lineHeight;
    }

    private float aboveStep(BitmapFont percentageFont, Step lastStep) {
        return lastStep.getY() * getScaledTileHeight() + (ENDING_HEIGHT * getUnitScale()) + percentageFont.getData().lineHeight;
    }

    private void drawActor(Actor actor) {
        gameDrawer.drawInMapRegion(
                assetManager.getTextureForActor(actor),
                actor.getX() * getScaledTileWidth(),
                actor.getY() * getScaledTileHeight(),
                getScaledTileWidth(),
                getScaledTileHeight());
    }

    private void renderStatusBar() {
        Optional<Player> optionalPlayer = gameObjectProvider.getPlayer();
        if (optionalPlayer.isPresent()) {
            gameDrawer.clearStatusBar();
            gameDrawer.begin();
            for (int i = 0; i < optionalPlayer.get().getHp(); i++) {
                gameDrawer.drawInStatusBarRegion(
                        assetManager.getHpFilledTexture(),
                        i * STATUS_BAR_HEIGHT,
                        0,
                        STATUS_BAR_HEIGHT,
                        STATUS_BAR_HEIGHT);
            }
            gameDrawer.end();
        }
    }
}
