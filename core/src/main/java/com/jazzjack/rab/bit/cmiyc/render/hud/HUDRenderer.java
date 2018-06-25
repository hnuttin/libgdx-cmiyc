package com.jazzjack.rab.bit.cmiyc.render.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jazzjack.rab.bit.cmiyc.ability.Ability;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;
import com.jazzjack.rab.bit.cmiyc.render.Renderer;

import static com.jazzjack.rab.bit.cmiyc.render.AlphaDrawer.alphaDrawer;

public class HUDRenderer implements Renderer {

    private static final int SCALE_TO_LEVEL = 2;
    private static final int ACTIONS_X_OFFSET = 10;

    private final Level level;
    private final GameAssetManager assetManager;
    private final int hudWidth;
    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final GameCamera hudCamera;
    private final PlayerApDrawer playerApDrawer;
    private final GamePhaseDrawer gamePhaseDrawer;

    public HUDRenderer(Level level, GameAssetManager assetManager, int numberOfHorizontalTilesToRender) {
        this.level = level;
        this.assetManager = assetManager;
        this.hudWidth = numberOfHorizontalTilesToRender * SCALE_TO_LEVEL;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.hudCamera = createHUDCamera();
        this.playerApDrawer = new PlayerApDrawer(this.assetManager, this.hudCamera, this.batch);
        this.gamePhaseDrawer = new GamePhaseDrawer(assetManager, batch, this.hudCamera, level.getLevelTiledMap().getTilePixelSize());
    }

    private GameCamera createHUDCamera() {
        GameCamera gameCamera = new GameCamera();
        gameCamera.setToOrtho(false, hudWidth, calculateViewportHeight(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        return gameCamera;
    }

    private float calculateViewportHeight(int screenWidthInPixels, int screenHeightInPixels) {
        return 1f * hudWidth * screenHeightInPixels / screenWidthInPixels;
    }

    @Override
    public void resize(int gameWidth, int gameHeight) {
        hudCamera.setViewportWidth(hudWidth);
        hudCamera.setViewportHeight(calculateViewportHeight(gameWidth, gameHeight));
        hudCamera.setPosition(hudCamera.getViewportWidth() / 2f, hudCamera.getViewportHeight() / 2f);
    }

    @Override
    public void render() {
        updateCamera();
        clearStatusBar();
        batch.begin();
        renderStatusBar();
        renderTurnCounter();
        batch.end();
        gamePhaseDrawer.drawGamePhaseOverlay();
    }

    private void updateCamera() {
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.getCombinedProjectionMatrix());
        shapeRenderer.setProjectionMatrix(hudCamera.getCombinedProjectionMatrix());
        gamePhaseDrawer.updateCamera();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    private void clearStatusBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0f, 0f, hudCamera.getViewportWidth(), 1f);
        shapeRenderer.end();
    }

    private void renderStatusBar() {
        renderHp();
        renderActions();
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

    private void renderActions() {
        int x = ACTIONS_X_OFFSET;
        for (Ability ability : level.getPlayer().getAbilities()) {
            batch.draw(assetManager.getTextureForName(ability.getName()), x, 0f, 1f, 1f);
            x++;
        }
        for (Item item : level.getPlayer().getItems()) {
            batch.draw(assetManager.getTextureForName(item.getName()), x, 0f, 1f, 1f);
            x++;
        }
    }

    private void renderTurnCounter() {
        TextureAtlas.AtlasRegion turnsLeftTexture = assetManager.getTurnsLeftTexture(level.getTurnsLeft());
        alphaDrawer(batch)
                .withAlpha(0.7f)
                .draw(() -> batch.draw(turnsLeftTexture, 0, hudCamera.getViewportHeight() - 2, 2, 2));
    }
}
