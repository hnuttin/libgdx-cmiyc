package com.jazzjack.rab.bit.cmiyc.game;

import com.jazzjack.rab.bit.cmiyc.render.GameCamera;

import java.util.Optional;

public interface GameWorldCameraProvider {

    Optional<GameCamera> getGameWorldCamera();
}
