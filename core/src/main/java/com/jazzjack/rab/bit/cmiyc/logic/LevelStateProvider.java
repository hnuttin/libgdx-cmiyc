package com.jazzjack.rab.bit.cmiyc.logic;

import com.jazzjack.rab.bit.cmiyc.level.Level;

import java.util.Optional;

public interface LevelStateProvider {

    Optional<Level> getCurrentLevel();

    GamePhase getCurrentGamePhase();
}
