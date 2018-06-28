package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.event.Event;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class KeyboardAndMouseInputProcessor implements InputProcessor {

    private final Map<Integer, Function<Integer, Event>> keyToEventFactoryMapping;
    private final InputGamePositionProvider inputGamePositionProvider;
    private final MousePressedToInputEventConverter mousePressedToInputEventConverter;

    KeyboardAndMouseInputProcessor(Map<Integer, Function<Integer, Event>> keyToEventFactoryMapping, InputGamePositionProvider inputGamePositionProvider, MousePressedToInputEventConverter mousePressedToInputEventConverter) {
        this.keyToEventFactoryMapping = keyToEventFactoryMapping;
        this.inputGamePositionProvider = inputGamePositionProvider;
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
        Optional<Event> event = mousePressedToInputEventConverter.convertToInputEvent(inputGamePositionProvider.getGamePosition());
        if (event.isPresent()) {
            publishEvent(event.get());
            return true;
        } else {
            return false;
        }
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
}
