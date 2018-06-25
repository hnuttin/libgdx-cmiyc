package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.event.Event;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;
import com.jazzjack.rab.bit.cmiyc.render.GameCamera;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class KeyboardAndMouseInputProcessor implements InputProcessor {

    private final Map<Integer, Function<Integer, Event>> keyToEventFactoryMapping;
    private final GameWorldCameraProvider gameWorldCameraProvider;
    private final MousePressedToInputEventConverter mousePressedToInputEventConverter;

    KeyboardAndMouseInputProcessor(Map<Integer, Function<Integer, Event>> keyToEventFactoryMapping, GameWorldCameraProvider gameWorldCameraProvider, MousePressedToInputEventConverter mousePressedToInputEventConverter) {
        this.keyToEventFactoryMapping = keyToEventFactoryMapping;
        this.gameWorldCameraProvider = gameWorldCameraProvider;
        this.mousePressedToInputEventConverter = mousePressedToInputEventConverter;
    }

    @Override
    public boolean keyDown(int keycode) {
        Function<Integer, Event> eventFactory = keyToEventFactoryMapping.get(keycode);
        if (eventFactory != null) {
            publishEvent(eventFactory.apply(keycode));
        }
        return true;
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
        getMouseGamePosition()
                .map(mousePressedToInputEventConverter::convertToInputEvent)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(GameEventBus::publishEvent);
        return true;
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
