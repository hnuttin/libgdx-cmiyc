package com.jazzjack.rab.bit.cmiyc.game.input;

import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.game.GameWorldCameraProvider;

public interface InputProcessorFactory {

    public InputProcessor create(GameWorldCameraProvider gameWorldCameraProvider);
}
