package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.InputProcessor;

public interface InputProcessorFactory {

    InputProcessor createInputProcessor(MousePressedEventFactory mousePressedEventFactory);
}
