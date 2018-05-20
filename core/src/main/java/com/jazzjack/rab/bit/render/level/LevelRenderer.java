package com.jazzjack.rab.bit.render.level;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.route.Route;
import com.jazzjack.rab.bit.actor.enemy.route.Step;
import com.jazzjack.rab.bit.actor.enemy.route.StepNames;
import com.jazzjack.rab.bit.render.GameAssetManager;
import com.jazzjack.rab.bit.render.Renderer;

import static com.jazzjack.rab.bit.render.level.TextDrawer.Position.*;

public class LevelRenderer extends OrthogonalTiledMapRenderer implements Renderer {

    private static final float ROUTE_ALPHA = 0.7f;
    private static final float LEVEL_CAMERA_SCALE = 1.5f;

    private final Level level;
    private final GameAssetManager assetManager;
    private final LevelCamera camera;
    private final TextDrawer textDrawer;

    public LevelRenderer(Level level, GameAssetManager assetManager) {
        super(null, 1 / level.getTilePixelSize());
        this.level = level;
        this.assetManager = assetManager;
        this.camera = new LevelCamera(level, LEVEL_CAMERA_SCALE);
        this.textDrawer = new TextDrawer(assetManager, batch, this.camera);
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
        batch.begin();
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
        Step lastStep = route.getLastStep();
        textDrawer.drawText(
                route.getPercentage() + "%",
                lastStep.getX(),
                lastStep.getY(),
                percentagePositionForStep(lastStep));
    }

    private TextDrawer.Position percentagePositionForStep(Step step) {
        return StepNames.ENDING_BOTTOM.equals(step.getName()) ? BOTTOM : TOP;
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
