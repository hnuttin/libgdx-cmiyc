package com.jazzjack.rab.bit.cmiyc.render.level;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jazzjack.rab.bit.cmiyc.actor.Actor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Step;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames;
import com.jazzjack.rab.bit.cmiyc.common.HasPosition;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.Renderer;

import static com.jazzjack.rab.bit.cmiyc.render.level.TextDrawer.Position.BOTTOM;
import static com.jazzjack.rab.bit.cmiyc.render.level.TextDrawer.Position.TOP;

public class LevelRenderer extends OrthogonalTiledMapRenderer implements Renderer {

    private static final float ROUTE_ALPHA = 0.7f;
    private static final float LEVEL_CAMERA_SCALE = 1.5f;

    private final Level level;
    private final GameAssetManager assetManager;
    private final LevelCamera camera;
    private final TextDrawer textDrawer;
    private final FogOfWarBuffer fogOfWarBuffer;

    public LevelRenderer(Level level, GameAssetManager assetManager, int numberOfHorizontalTilesToRender) {
        super(null, 1 / level.getTiledMap().getTilePixelSize());
        this.level = level;
        this.assetManager = assetManager;
        this.camera = new LevelCamera(level, numberOfHorizontalTilesToRender, LEVEL_CAMERA_SCALE);
        this.textDrawer = new TextDrawer(assetManager, super.batch, this.camera);
        this.fogOfWarBuffer = new FogOfWarBuffer(this.level, super.batch, assetManager);
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void render() {
        camera.update();
        setView(camera);
        renderMap();
    }

    private void renderMap() {
        fogOfWarBuffer.bufferSight();
        batch.begin();
        renderLevel();
        renderPlayer();
        renderEnemies();
        renderEndPosition();
        batch.end();
        fogOfWarBuffer.renderSight();
    }

    private void renderLevel() {
        super.renderMapLayer(level.getTiledMap().getMapLayer());
    }

    private void renderPlayer() {
        drawActor(level.getPlayer());
    }

    private void renderEnemies() {
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
        Step lastStep = route.getLastStep();
        textDrawer.drawText(
                route.getPercentage() + "%",
                lastStep.getX(),
                lastStep.getY(),
                percentagePositionForStep(lastStep),
                level.getTiledMap().getTilePixelSize());
    }

    private TextDrawer.Position percentagePositionForStep(Step step) {
        return StepNames.ENDING_BOTTOM.equals(step.getName()) ? BOTTOM : TOP;
    }

    private void renderEndPosition() {
        HasPosition endPosition = level.getLevelMetaData().getEndPosition();
        batch.draw(
                assetManager.getPlayerEndTexture(),
                endPosition.getX(),
                endPosition.getY(),
                1,
                1);
    }

    private void drawActor(Actor actor) {
        batch.draw(
                assetManager.getTextureForActor(actor),
                actor.getX(),
                actor.getY(),
                1,
                1);
    }

    @Override
    public void dispose() {
        super.dispose();
        fogOfWarBuffer.dispose();
    }
}
