package com.jazzjack.rab.bit.cmiyc.game;

import com.badlogic.gdx.graphics.Camera;

import java.util.Optional;

public interface GameWorldCameraProvider {

    Optional<Camera> getGameWorldCamera();
}
