package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.Map;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.Step;
import com.jazzjack.rab.bit.actor.enemy.route.StepNames;
import com.jazzjack.rab.bit.game.GameObjectProvider;

import java.util.Optional;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    private static final int TILE_SIZE_IN_PIXELS = 32;
    private static final int UNITES_PER_TILE = 2;

    private static final int HORIZONTAL_TILES = 20;
    private static final int VERTICAL_TILES = 10;

    private static final int MAP_WIDTH_UNITES = HORIZONTAL_TILES * UNITES_PER_TILE;
    private static final int MAP_HEIGHT_UNITES = VERTICAL_TILES * UNITES_PER_TILE;
    private static final int STATUS_BAR_HEIGHT = 1;
    private static final int WIDTH_UNITS = MAP_WIDTH_UNITES;
    private static final int HEIGHT_UNITES = MAP_HEIGHT_UNITES + STATUS_BAR_HEIGHT;

    private static final float FOG_OF_WAR = 0f;
    private static final float ENDING_HEIGHT = 22f;
    private static final float ROUTE_ALPHA = 0.7f;

    private final GameObjectProvider gameObjectProvider;
    private final GameAssetManager assetManager;

    private final GameDrawer gameDrawer;

    private final OrthographicCamera camera;

    private final LightBufferProvider lightBufferProvider;

    private boolean rebufferPlayer = true;

    public GameRenderer(GameObjectProvider gameObjectProvider, GameAssetManager assetManager) {
        super(null, 1f / (TILE_SIZE_IN_PIXELS / UNITES_PER_TILE));

        this.gameObjectProvider = gameObjectProvider;
        this.assetManager = assetManager;
        this.gameDrawer = new GameDrawer(super.batch, STATUS_BAR_HEIGHT);
        this.lightBufferProvider = new LightBufferProvider(getViewBounds());

        Gdx.graphics.setWindowedMode(WIDTH_UNITS * TILE_SIZE_IN_PIXELS, HEIGHT_UNITES * TILE_SIZE_IN_PIXELS);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (float) WIDTH_UNITS, (float) HEIGHT_UNITES);
        camera.update();
    }

    private float getScaledTileWidth() {
        return UNITES_PER_TILE;
    }

    private float getScaledTileHeight() {
        return UNITES_PER_TILE;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (float) WIDTH_UNITS;
        camera.viewportHeight = (float) WIDTH_UNITS * height / width;
        camera.update();
    }

    @Override
    public void render() {
        updateCamera();
        //bufferSight();
        renderMap();
        //renderSight();
        renderStatusBar();
    }

    private void updateCamera() {
        camera.update();
        setView(camera);
        gameDrawer.setProjectionMatrix(camera.combined);
    }

    private void bufferSight() {
        Optional<Player> player = gameObjectProvider.getPlayer();
        Optional<Map> level = gameObjectProvider.getMap();
        if (rebufferPlayer && player.isPresent() && level.isPresent()) {
            rebufferPlayer = true;

            lightBufferProvider.getLightBuffer().begin();
            Gdx.gl.glClearColor(FOG_OF_WAR, FOG_OF_WAR, FOG_OF_WAR, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameDrawer.drawWithBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE, () -> drawSight(player.get()));
            lightBufferProvider.getLightBuffer().end();
        }
    }

    private void drawSight(Player player) {
        gameDrawer.drawWithFlippedY(
                assetManager.getLightAtlasRegion(),
                (player.getX() * getScaledTileWidth()) - (player.getSight() * getScaledTileWidth()),
                (player.getY() * getScaledTileHeight()) - (player.getSight() * getScaledTileHeight()),
                (player.getSight() * 2 + 1) * getScaledTileWidth(),
                (player.getSight() * 2 + 1) * getScaledTileHeight());
    }

    private void renderSight() {
        gameDrawer.drawWithBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR, () -> batch.draw(lightBufferProvider.getLightBuffer().getColorBufferTexture(), 0, STATUS_BAR_HEIGHT));
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
        gameObjectProvider.getMap().ifPresent(this::renderLevel);
    }

    private void renderLevel(Map map) {
        map.setMapOffset(0, STATUS_BAR_HEIGHT * TILE_SIZE_IN_PIXELS / 2 * -1);
        super.renderMapLayer(map.getMapLayer());
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
        return lastStep.getY() * getScaledTileHeight() + (ENDING_HEIGHT / (TILE_SIZE_IN_PIXELS / UNITES_PER_TILE)) + percentageFont.getData().lineHeight;
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
            Texture hpFilledTexture = assetManager.getHpFilledTexture();
            for (int i = 0; i < optionalPlayer.get().getHp(); i++) {
                gameDrawer.drawInStatusBarRegion(
                        hpFilledTexture,
                        i,
                        0);
            }
            Texture hpEmptyTexture = assetManager.getHpEmptyTexture();
            for (int j = optionalPlayer.get().getHp(); j < optionalPlayer.get().getMaxHp(); j++) {
                gameDrawer.drawInStatusBarRegion(
                        hpEmptyTexture,
                        j,
                        0);
            }
            gameDrawer.end();
        }
    }
}
