package com.jazzjack.rab.bit.cmiyc.render.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.jazzjack.rab.bit.cmiyc.actor.Actor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.StepNames;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.Renderer;
import com.jazzjack.rab.bit.cmiyc.shared.position.Alignment;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import static com.jazzjack.rab.bit.cmiyc.render.AlphaDrawer.alphaDrawer;
import static com.jazzjack.rab.bit.cmiyc.shared.position.Alignment.BOTTOM;
import static com.jazzjack.rab.bit.cmiyc.shared.position.Alignment.TOP;

public class LevelRenderer extends OrthoCachedTiledMapRenderer implements Renderer {

    private static final float ROUTE_ALPHA = 0.7f;
    private static final float LEVEL_CAMERA_SCALE = 1.5f;

    private final Level level;
    private final GameAssetManager assetManager;
    private final LevelCamera camera;
    private final Batch batch;
    private final LevelTextDrawer textDrawer;
    private final FogOfWarRenderer fogOfWarRenderer;

    public LevelRenderer(Level level, GameAssetManager assetManager, int numberOfHorizontalTilesToRender) {
        super(level.getLevelTiledMap(), 1 / level.getLevelTiledMap().getTilePixelSize());
        this.level = level;
        this.assetManager = assetManager;
        this.camera = new LevelCamera(level, numberOfHorizontalTilesToRender, LEVEL_CAMERA_SCALE);
        this.batch = new SpriteBatch();
        this.textDrawer = new LevelTextDrawer(assetManager, this.batch, this.camera);
        this.fogOfWarRenderer = new FogOfWarRenderer(this.level, this.batch, assetManager);
    }

    public LevelCamera getCamera() {
        return camera;
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void render() {
        camera.update();
        setView(camera);
        batch.setProjectionMatrix(camera.combined);
        renderMap();
    }

    private void renderMap() {
        fogOfWarRenderer.buffer();
        renderLevel();
        batch.begin();
        renderEndPosition();
        renderItems();
        renderPlayer();
        renderEnemies();
        batch.end();
        fogOfWarRenderer.render();
    }

    private void renderLevel() {
        super.render();
    }

    private void renderPlayer() {
        Player player = level.getPlayer();
        drawActor(player);
        if (player.isShieldActive()) {
            drawTextureOnPosition(assetManager.getTextureForName(Item.SHIELD.getName()), player);
        }
    }

    private void renderEnemies() {
        level.getEnemies().forEach(this::drawEnemy);
    }

    private void drawEnemy(Enemy enemy) {
        if (level.getLevelPlayerSight().isEnemyInSight(enemy)) {
            drawActor(enemy);
            alphaDrawer(batch)
                    .withAlpha(ROUTE_ALPHA)
                    .draw(() -> drawEnemyRoutes(enemy));
            if (camera.getMouseGamePosition().hasSamePositionAs(enemy)) {
                drawTextureOnPosition(assetManager.getEnemyHoveredTexture(), enemy);
            }
        }
    }

    private void drawEnemyRoutes(Enemy enemy) {
        if (enemy.isMarked()) {
            enemy.getRoutes().forEach(this::drawEnemyRoute);
        }
    }

    private void drawEnemyRoute(Route route) {
        drawPercentage(route);
        route.getSteps().forEach(this::drawActor);
    }

    private void drawPercentage(Route route) {
        Step lastStep = route.getLastStep();
        textDrawer.drawTextOnTile(
                route.getPercentage() + "%",
                lastStep.getX(),
                lastStep.getY(),
                percentagePositionForStep(lastStep),
                level.getLevelTiledMap().getTilePixelSize());
    }

    private Alignment percentagePositionForStep(Step step) {
        return StepNames.ENDING_BOTTOM.equals(step.getName()) ? BOTTOM : TOP;
    }

    private void renderEndPosition() {
        drawTextureOnPosition(assetManager.getPlayerEndTexture(), level.getLevelEndPosition());
    }

    private void renderItems() {
        level.getItems().forEach(this::drawItem);
    }

    private void drawItem(ItemMarkerObject item) {
        drawTextureOnPosition(assetManager.getTextureForName(item.getName()), item);
    }

    private void drawActor(Actor actor) {
        drawTextureOnPosition(assetManager.getTextureForName(actor.getName()), actor);
    }

    private void drawTextureOnPosition(TextureRegion texture, HasPosition hasPosition) {
        batch.draw(texture, hasPosition.getX(), hasPosition.getY(), 1, 1);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        fogOfWarRenderer.dispose();
    }
}
