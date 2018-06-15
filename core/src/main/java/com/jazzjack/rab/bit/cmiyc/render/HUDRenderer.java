package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.level.Level;

import static com.jazzjack.rab.bit.cmiyc.render.AlphaDrawer.alphaDrawer;

public class HUDRenderer implements Renderer {

    private static final float SCALE_TO_LEVEL = 2f;

    private final Level level;
    private final GameAssetManager assetManager;
    private final int numberOfHorizontalTilesToRender;
    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final GameCamera camera;
    private final PlayerApDrawer playerApDrawer;

    HUDRenderer(Level level, GameAssetManager assetManager, int numberOfHorizontalTilesToRender) {
        this.level = level;
        this.assetManager = assetManager;
        this.numberOfHorizontalTilesToRender = numberOfHorizontalTilesToRender;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = createCamera();
        this.playerApDrawer = new PlayerApDrawer(this.assetManager, this.camera, this.batch);
    }

    private GameCamera createCamera() {
        GameCamera gameCamera = new GameCamera();
        gameCamera.setToOrtho(false, calculateViewportWidth(), calculateViewportHeight(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        return gameCamera;
    }

    private float calculateViewportWidth() {
        return numberOfHorizontalTilesToRender * SCALE_TO_LEVEL;
    }

    private float calculateViewportHeight(int screenWidthInPixels, int screenHeightInPixels) {
        return calculateViewportWidth() * screenHeightInPixels / screenWidthInPixels;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = calculateViewportWidth();
        camera.viewportHeight = calculateViewportHeight(width, height);
        camera.position.set(camera.getViewportWidth() / 2f, camera.getViewportHeight() / 2f, 0f);
    }

    @Override
    public void render() {
        updateCamera();
        clearStatusBar();
        batch.begin();
        renderStatusBar();
        renderTurnCounter();
        batch.end();
    }

    private void updateCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    private void clearStatusBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0f, 0f, numberOfHorizontalTilesToRender * SCALE_TO_LEVEL, 1f);
        shapeRenderer.end();
    }

    private void renderStatusBar() {
        renderHp();
        renderPlayerItems();
        playerApDrawer.draw(level.getPlayer());
    }

    private void renderHp() {
        TextureAtlas.AtlasRegion hpFilledTexture = assetManager.getHpFilledTexture();
        for (float i = 0; i < level.getPlayer().getHp(); i++) {
            batch.draw(hpFilledTexture, i, 0f, 1f, 1f);
        }
        TextureAtlas.AtlasRegion hpEmptyTexture = assetManager.getHpEmptyTexture();
        for (float j = level.getPlayer().getHp(); j < level.getPlayer().getMaxHp(); j++) {
            batch.draw(hpEmptyTexture, j, 0f, 1f, 1f);
        }
    }

    private void renderPlayerItems() {
        for (Item item : level.getPlayer().getItems()) {
            batch.draw(assetManager.getTextureForName(item.getName()), 10, 0f, 1f, 1f);
        }
    }

    private void renderTurnCounter() {
        TextureAtlas.AtlasRegion turnsLeftTexture = assetManager.getTurnsLeftTexture(level.getTurnsLeft());
        alphaDrawer(batch)
                .withAlpha(0.7f)
                .draw(() -> batch.draw(turnsLeftTexture, 0, camera.viewportHeight - 2, 2, 2));
    }
}
