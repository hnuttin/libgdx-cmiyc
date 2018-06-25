package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class KeyboardAndMouseInputProcessor implements InputProcessor {

    private static final Map<Integer, Direction> KEY_TO_DIRECTION_MAPPING = new HashMap<>();

    static {
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.LEFT, Direction.LEFT);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.RIGHT, Direction.RIGHT);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.UP, Direction.UP);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.DOWN, Direction.DOWN);
    }

    private final GameWorldCameraProvider gameWorldCameraProvider;

    public KeyboardAndMouseInputProcessor(GameWorldCameraProvider gameWorldCameraProvider) {
        this.gameWorldCameraProvider = gameWorldCameraProvider;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.E:
                endPlayerTurn();
                return true;
            case Input.Keys.S:
                currentLevel.getPlayer().useItem(Item.SHIELD);
                endPlayerTurnIfNoActionPointsLeft();
                return true;
        }
        Direction direction = KEY_TO_DIRECTION_MAPPING.get(keycode);
        if (direction != null) {
            publishEvent(new PlayerMovementInputEvent(direction));
        }
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
