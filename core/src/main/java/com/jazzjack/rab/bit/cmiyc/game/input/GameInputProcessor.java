package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

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
        return getMouseGamePosition()
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

    private Optional<HasPosition> getMouseGamePosition() {
        return gameWorldCameraProvider.getGameWorldCamera().map(GameCamera::getMouseGamePosition);
    }
}
