package com.jazzjack.rab.bit.cmiyc.render.level;

import com.badlogic.gdx.Gdx;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;

class LevelCamera extends GameCamera {

    private final Level level;
    private final int numberOfHorizontalTilesToRender;
    private final float cameraScale;

    LevelCamera(Level level, int numberOfHorizontalTilesToRender, float cameraScale) {
        this.level = level;
        this.numberOfHorizontalTilesToRender = numberOfHorizontalTilesToRender;
        this.cameraScale = cameraScale;
        setToOrtho(false, calculateViewportWidth(), calculateViewportHeight(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    float getCameraScale() {
        return cameraScale;
    }

    void resize(int width, int height) {
        setViewportWidth(calculateViewportWidth());
        setViewportHeight(calculateViewportHeight(width, height));
        update();
    }

    @Override
    public void update() {
        setPosition(calculateCameraX(), calculateCameraY());
        super.update();
    }

    private float calculateCameraX() {
        if (playerCameraX() < leftCameraLimit()) {
            return leftCameraLimit();
        } else if (playerCameraX() > rightCameraLimit()) {
            return rightCameraLimit();
        } else {
            return playerCameraX();
        }
    }

    private float calculateCameraY() {
        if (playerCameraY() < bottomCameraLimit()) {
            return bottomCameraLimit();
        } else if (playerCameraY() > topCameraLimit()) {
            return topCameraLimit();
        } else {
            return playerCameraY();
        }
    }

    private float topCameraLimit() {
        return level.getLevelTiledMap().getHeight() - getViewportHeight() / 2f;
    }

    private float bottomCameraLimit() {
        return getViewportHeight() / 2f;
    }

    private float rightCameraLimit() {
        return level.getLevelTiledMap().getWidth() - viewportWidth / 2f;
    }

    private float leftCameraLimit() {
        return getViewportWidth() / 2f;
    }

    private float playerCameraX() {
        return level.getPlayer().getX() + 1f;
    }

    private float playerCameraY() {
        return level.getPlayer().getY();
    }

    private float calculateViewportWidth() {
        return numberOfHorizontalTilesToRender;
    }

    private float calculateViewportHeight(int gameWidthInPixels, int gameHeightInPixels) {
        return calculateViewportWidth() * gameHeightInPixels / gameWidthInPixels;
    }
}
