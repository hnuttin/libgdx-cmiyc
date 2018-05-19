package com.jazzjack.rab.bit.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Align;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.Step;
import com.jazzjack.rab.bit.actor.enemy.route.StepNames;

class LevelRenderer extends OrthogonalTiledMapRenderer implements Renderer {

    private static final float ENDING_HEIGHT = 22f;
    private static final float ROUTE_ALPHA = 0.7f;

    private final Level level;
    private final GameAssetManager assetManager;
    private final OrthographicCamera camera;

    LevelRenderer(Level level, GameAssetManager assetManager, Batch batch) {
        super(null, determineUnitScale(level), batch);
        this.level = level;
        this.assetManager = assetManager;
        this.camera = createCamera();
    }

    private static float determineUnitScale(Level level) {
        return 1 / level.getTileSize();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, calculateViewportWidth(), calculateViewportHeight(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        camera.update();
        return camera;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = calculateViewportWidth();
        camera.viewportHeight = calculateViewportHeight(width, height);
        updateCameraPosition();
        camera.update();
    }

    @Override
    public void render() {
        updateCamera();
        renderMap();
    }

    public Camera getCamera() {
        return camera;
    }

    private void updateCameraPosition() {
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    private int calculateViewportWidth() {
        return level.getWidth();
    }

    private int calculateViewportHeight(int gameWidthInPixels, int gameHeightInPixels) {
        return level.getWidth() * gameHeightInPixels / gameWidthInPixels;
    }

    private void updateCamera() {
        updateCameraPosition();
        camera.update();
        setView(camera);
    }

    private void renderMap() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderLevel();
        drawPlayer();
        drawEnemies();
        batch.end();
    }

    private void renderLevel() {
        super.renderMapLayer(level.getMapLayer());
    }

    private void drawPlayer() {
        drawActor(level.getPlayer());
    }

    private void drawEnemies() {
        level.getEnemies().forEach(this::drawEnemy);
    }

    private void drawEnemy(Enemy enemy) {
        drawActor(enemy);
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, ROUTE_ALPHA);
        drawEnemyRoutes(enemy);
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
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
        float percentageX = lastStep.getX();
        float percentageY = StepNames.ENDING_BOTTOM.equals(lastStep.getName()) ? underneathStep(percentageFont, lastStep) : aboveStep(percentageFont, lastStep);
        drawCenteredTextInMapRegion(percentageFont, route.getPercentage() + "%", percentageX, percentageY, 1, Align.center);
    }

    private void drawCenteredTextInMapRegion(BitmapFont font, String text, float x, float y, float targetWidth, float alpha) {
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
        font.setUseIntegerPositions(false);
        font.getData().setScale(20f / Gdx.graphics.getHeight());
        font.draw(batch, text, x, y, targetWidth, Align.center, false);
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, 1f);
    }

    private float underneathStep(BitmapFont percentageFont, Step lastStep) {
        return lastStep.getY() + percentageFont.getData().lineHeight;
    }

    private float aboveStep(BitmapFont percentageFont, Step lastStep) {
        return lastStep.getY() + (ENDING_HEIGHT / level.getTileSize()) + percentageFont.getData().lineHeight;
    }

    private void drawActor(Actor actor) {
        batch.draw(
                assetManager.getTextureForActor(actor),
                actor.getX(),
                actor.getY(),
                1,
                1);
    }
}
