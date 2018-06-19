package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import java.util.Optional;

public class GameInputProcessor implements InputProcessor {

    private final KeyInputProcessor keyInputProcessor;
    private final MouseInputProcessor mouseInputProcessor;
    private final GameWorldCameraProvider gameWorldCameraProvider;

    public GameInputProcessor(KeyInputProcessor keyInputProcessor, MouseInputProcessor mouseInputProcessor, GameWorldCameraProvider gameWorldCameraProvider) {
        this.gameWorldCameraProvider = gameWorldCameraProvider;
        this.keyInputProcessor = keyInputProcessor;
        this.mouseInputProcessor = mouseInputProcessor;
    }

    @Override
    public boolean keyDown(int keycode) {
        return keyInputProcessor.keyPressed(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return translateToGamePosition(screenX, screenY)
                .map(mouseInputProcessor::mousePressed)
                .orElse(false);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private Optional<Position> translateToGamePosition(int screenX, int screenY) {
        return gameWorldCameraProvider.getGameWorldCamera().map(camera -> {
            Vector3 gameVector = camera.unproject(new Vector3(screenX, screenY, 0f));
            return new Position((int) gameVector.x, (int) gameVector.y);
        });
    }
}
