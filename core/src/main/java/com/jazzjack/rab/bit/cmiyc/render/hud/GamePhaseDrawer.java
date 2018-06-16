package com.jazzjack.rab.bit.cmiyc.render.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhase;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEvent;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEventSubscriber;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.registerSubscriber;

public class GamePhaseDrawer implements GamePhaseEventSubscriber {

    private static final float OVERLAY_THICKNESS = 0.2f;
    private static final Color COLOR_ENEMY_TURN = new Color(0xff9300cc);
    private static final float Y_OVERLAY_TEXT = 2f;

    private final GameCamera hudCamera;
    private final ShapeRenderer shapeRenderer;
    private final HUDTextDrawer hudTextDrawer;

    private GamePhase currentGamePhase;

    GamePhaseDrawer(GameAssetManager assetManager, Batch batch, GameCamera hudCamera, float pixelsPerUnit) {
        this.hudCamera = hudCamera;
        this.shapeRenderer = new ShapeRenderer();
        this.hudTextDrawer = new HUDTextDrawer(assetManager, batch, hudCamera, pixelsPerUnit);

        registerSubscriber(this);
    }

    void updateCamera() {
        shapeRenderer.setProjectionMatrix(hudCamera.getCombinedProjectionMatrix());
    }

    void drawGamePhaseOverlay() {
        if (currentGamePhase == GamePhase.ENEMY_TURN) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(COLOR_ENEMY_TURN);
            shapeRenderer.rect(0f, 0f, hudCamera.getViewportWidth(), OVERLAY_THICKNESS);
            shapeRenderer.rect(0f, hudCamera.getViewportHeight() - OVERLAY_THICKNESS, hudCamera.getViewportWidth(), OVERLAY_THICKNESS);
            shapeRenderer.rect(0f, OVERLAY_THICKNESS, OVERLAY_THICKNESS, hudCamera.getViewportHeight() - OVERLAY_THICKNESS * 2);
            shapeRenderer.rect(hudCamera.getViewportWidth() - OVERLAY_THICKNESS, OVERLAY_THICKNESS, OVERLAY_THICKNESS, hudCamera.getViewportHeight() - OVERLAY_THICKNESS * 2);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            hudTextDrawer.drawOverlayText("ENEMY TURN", hudCamera.getViewportHeight() - Y_OVERLAY_TEXT, COLOR_ENEMY_TURN);
        }
    }

    @Override
    public void newGamePhase(GamePhaseEvent event) {
        currentGamePhase = event.getGamePhase();
    }
}
