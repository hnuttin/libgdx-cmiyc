package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.event.Event;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class KeyboardAndMouseInputProcessorFactory implements InputProcessorFactory {

    private static final Map<Integer, Direction> KEY_TO_DIRECTION_MAPPING = new HashMap<>();

    static {
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.LEFT, Direction.LEFT);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.RIGHT, Direction.RIGHT);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.UP, Direction.UP);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.DOWN, Direction.DOWN);
    }

    @Override
    public InputProcessor create(GameWorldCameraProvider gameWorldCameraProvider) {
        return new KeyboardAndMouseInputProcessor(createKeyToEventFactoryMapping(), gameWorldCameraProvider, mousePressedToInputEventConverter);
    }

    private Map<Integer, Function<Integer, Event>> createKeyToEventFactoryMapping() {
        Map<Integer, Function<Integer, Event>> keyToEventFactoryMapping = new HashMap<>();

        keyToEventFactoryMapping.put(Input.Keys.LEFT, this::playerMovementInputEventFactory);
        keyToEventFactoryMapping.put(Input.Keys.RIGHT, this::playerMovementInputEventFactory);
        keyToEventFactoryMapping.put(Input.Keys.UP, this::playerMovementInputEventFactory);
        keyToEventFactoryMapping.put(Input.Keys.DOWN, this::playerMovementInputEventFactory);

        keyToEventFactoryMapping.put(Input.Keys.S, keycode -> new UseItemInputEvent(Item.SHIELD));
        keyToEventFactoryMapping.put(Input.Keys.E, keycode -> new EndPlayerTurnInputEvent());

        return keyToEventFactoryMapping;
    }

    private Event playerMovementInputEventFactory(Integer keycode) {
        return new PlayerMovementInputEvent(KEY_TO_DIRECTION_MAPPING.get(keycode));
    }
}
