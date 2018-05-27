package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;

import static com.jazzjack.rab.bit.cmiyc.render.AlphaDrawer.alphaDrawer;

class PlayerApDrawer {

    private final GameAssetManager assetManager;
    private final GameCamera camera;
    private final Batch batch;

    PlayerApDrawer(GameAssetManager assetManager, GameCamera camera, Batch batch) {
        this.assetManager = assetManager;
        this.camera = camera;
        this.batch = batch;
    }

    void draw(Player player) {
        for (int apToRender = 0; apToRender < player.getActionPointsPerTurn(); apToRender++) {
            TextureAtlas.AtlasRegion apTexture = getApTextureForApToRender(player, apToRender);
            float alpha = getAlphaForApToRender(player, apToRender);
            float apX = camera.getViewportWidth() - player.getActionPointsPerTurn() + apToRender;
            alphaDrawer(batch)
                    .withAlpha(alpha)
                    .draw(() -> batch.draw(apTexture, apX, 0f, 1f, 1f));
        }
    }

    private TextureAtlas.AtlasRegion getApTextureForApToRender(Player player, int apToRender) {
        if (apToRender == 0) {
            return assetManager.getApStartTexture();
        } else if (apToRender == player.getActionPointsPerTurn() - 1) {
            return assetManager.getApEndTexture();
        } else {
            return assetManager.getApMiddleTexture();
        }
    }

    private float getAlphaForApToRender(Player player, int apToRender) {
        return apToRender < player.getActionPointsConsumed() ? 0.3f : 1f;
    }
}
